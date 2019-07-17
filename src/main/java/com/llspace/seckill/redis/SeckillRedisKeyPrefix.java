package com.llspace.seckill.redis;

/**
 * <p>@filename GoodsRedisKeyPrefix</p>
 * <p>
 * <p>@description 商品模块Redis缓存的key实现</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/9 14:50
 **/
public class SeckillRedisKeyPrefix extends BaseRedisKeyPrefix{

    public SeckillRedisKeyPrefix(int expire, String prefix) {
        super(expire, prefix);
    }

    public static SeckillRedisKeyPrefix seckillPath = new SeckillRedisKeyPrefix(60, "seckill_path");

    public static RedisKeyPrefix getSeckillVerifyCode = new SeckillRedisKeyPrefix(60, "verify_code");
}
