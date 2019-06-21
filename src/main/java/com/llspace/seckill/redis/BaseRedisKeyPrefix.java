package com.llspace.seckill.redis;

/**
 * <p>@filename BaseRedisKey</p>
 * <p>
 * <p>@description RedisKey基础实现</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:37
 **/
public abstract class BaseRedisKeyPrefix implements RedisKeyPrefix {

    //有效期
    private int expire;

    //key的前缀
    private String prefix;

    public BaseRedisKeyPrefix(String prefix) {//0代表永不过期
        this(0, prefix);
    }

    public BaseRedisKeyPrefix(int expire, String prefix) {
        this.expire = expire;
        this.prefix = prefix;
    }

    @Override
    public int expire() {//默认0代表永不过期
        return expire;
    }

    @Override
    public String prefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

    @Override public String realKey(String key) {
        return this.prefix() + key;
    }
}
