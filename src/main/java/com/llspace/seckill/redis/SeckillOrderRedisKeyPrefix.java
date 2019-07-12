package com.llspace.seckill.redis;

/**
 * <p>@filename SeckillOrderRedisKeyPrefix</p>
 * <p>
 * <p>@description 秒杀订单Redis缓存的key实现</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/7/11 15:50
 **/
public class SeckillOrderRedisKeyPrefix extends BaseRedisKeyPrefix{

    public SeckillOrderRedisKeyPrefix(String prefix){
        super(prefix);
    }

    public static SeckillOrderRedisKeyPrefix getSeckillOrderByUidGid = new SeckillOrderRedisKeyPrefix("seckill_order_uid_gid");
}
