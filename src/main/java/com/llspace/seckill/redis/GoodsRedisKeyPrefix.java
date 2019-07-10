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
public class GoodsRedisKeyPrefix extends BaseRedisKeyPrefix{

    public GoodsRedisKeyPrefix(int expire, String prefix) {
        super(expire, prefix);
    }

    public static GoodsRedisKeyPrefix getGoodsList = new GoodsRedisKeyPrefix(60, "goods_list");
}
