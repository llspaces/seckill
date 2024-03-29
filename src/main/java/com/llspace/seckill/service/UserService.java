package com.llspace.seckill.service;

import com.llspace.seckill.dao.UserMapper;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.entity.vo.LoginVO;
import com.llspace.seckill.exception.GlobalException;
import com.llspace.seckill.redis.RedisService;
import com.llspace.seckill.redis.UserRedisKeyPrefix;
import com.llspace.seckill.utils.CodeMsg;
import com.llspace.seckill.utils.MD5Util;
import com.llspace.seckill.utils.SeckillConstant;
import com.llspace.seckill.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * <p>@filename UserService</p>
 * <p>
 * <p>@description 用户模块service层实现</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:50
 **/

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;


    /**
     * 用户登录
     *
     * @param response
     * @param loginVO
     * @return
     */
    public String login(HttpServletResponse response, @Valid LoginVO loginVO) {
        if(loginVO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String formPass = loginVO.getPassword();
        //判断手机号是否存在
        User user = userMapper.selectByPrimaryKey(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * redis中根据token获取User信息
     *
     * @param response
     * @param token
     * @return
     */
    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.getBean(UserRedisKeyPrefix.token, token, User.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserRedisKeyPrefix.token, token, user);
        Cookie cookie = new Cookie(SeckillConstant.COOKIE_TOKEN_NAME, token);
        cookie.setMaxAge(UserRedisKeyPrefix.token.expire());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 通过id获取User信息（对象缓存）
     *
     * @param id
     * @return
     */
    public User getById(long id) {
        //取缓存
        User user = redisService.getBean(UserRedisKeyPrefix.getById, ""+id, User.class);
        if(user != null) {
            return user;
        }
        //取数据库
        user = userMapper.selectByPrimaryKey(id);
        if(user != null) {
            redisService.set(UserRedisKeyPrefix.getById, ""+id, user);
        }
        return user;
    }

    /**
     * 更新用户密码（先更新数据库，在清空缓存）
     *
     * @param token
     * @param id
     * @param formPass
     * @return
     */
    //Cache Aside Pattern : http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        User user = getById(id);
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //更新数据库
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        userMapper.updateByPrimaryKey(toBeUpdate);

        //处理缓存
        redisService.delete(UserRedisKeyPrefix.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserRedisKeyPrefix.token, token, user);
        return true;
    }

    /**
     * 登出功能，删除redis对应key的user
     *
     * @param token
     */
    public void logout(String token) {
        redisService.delete(UserRedisKeyPrefix.token, token);
    }
}
