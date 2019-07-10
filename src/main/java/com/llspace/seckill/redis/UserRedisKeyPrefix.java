package com.llspace.seckill.redis;

import com.llspace.seckill.utils.SeckillConstant;

/**
 * <p>@filename UserRedisKey</p>
 * <p>
 * <p>@description 用户模块Redis缓存的key实现</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:42
 **/
public class UserRedisKeyPrefix extends BaseRedisKeyPrefix {

    public UserRedisKeyPrefix(int expire, String prefix) {
        super(expire, prefix);
    }

    public static UserRedisKeyPrefix token = new UserRedisKeyPrefix(SeckillConstant.TOKEN_EXPIRE, "token");

    public static UserRedisKeyPrefix selectByPrimaryKey = new UserRedisKeyPrefix(0, "id");

    public static UserRedisKeyPrefix getById = new UserRedisKeyPrefix(0, "id");

}
