package com.llspace.seckill.interceptor;

import com.alibaba.fastjson.JSON;
import com.llspace.seckill.entity.User;
import com.llspace.seckill.service.UserService;
import com.llspace.seckill.utils.CodeMsg;
import com.llspace.seckill.utils.Result;
import com.llspace.seckill.utils.SeckillConstant;
import com.llspace.seckill.utils.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * <p>@filename LoginInterceptor</p>
 * <p>
 * <p>@description 登录拦截器</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/21 10:34
 **/
@Service
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            String method = ((HandlerMethod) handler).getMethod().getName();
            if("doLogin".equals(method)){
                return true;
            }
            //登录判断,先根据token去redis获取用户信息
            User user = getUser(request, response);
            if(user == null){
                //未登录
                render(response, CodeMsg.NOT_LOGIN);
                return false;
            }
            //将用户信息设置到UserContext，便于后续使用
            UserContext.setUser(user);
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg codeMsg)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(Result.error(codeMsg));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(SeckillConstant.COOKIE_TOKEN_NAME);
        String cookieToken = getCookieValue(request.getCookies(), SeckillConstant.COOKIE_TOKEN_NAME);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(Cookie[] cookies, String cookieName) {
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        String value = null;
        for(Cookie cookie : cookies) {
            if(cookieName.equals(cookie.getName())) {
                value = cookie.getValue();
            }
        }
        return value;
    }

}
