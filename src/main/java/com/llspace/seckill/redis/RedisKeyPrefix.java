package com.llspace.seckill.redis;

/**
 * <p>@filename RedisKey</p>
 * <p>
 * <p>@description 定义系统使用Redis时key的接口</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:38
 **/
public interface RedisKeyPrefix {

    public int expire();

    public String prefix();

    public String realKey(String key);
}
