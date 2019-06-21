package com.llspace.seckill.redis;

import com.llspace.seckill.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>@filename RedisService</p>
 * <p>
 * <p>@description 封装Redis常用方法</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/20 11:13
 **/
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public <T> boolean set(RedisKeyPrefix prefix, String key, T value){
        boolean flag = false;
        try{
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String realKey = prefix.realKey(key);
            operations.set(realKey, JsonUtil.toJSONString(value));
            //expire <= 0 表示无限期, 不设置
            if(prefix.expire() > 0){
                redisTemplate.expire(realKey, prefix.expire(), TimeUnit.SECONDS);
            }
            flag = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean exists(RedisKeyPrefix prefix, String key){
        return redisTemplate.hasKey(prefix.realKey(key));
    }

    public void delete(RedisKeyPrefix prefix, String key){
        if(exists(prefix, key)){
            redisTemplate.delete(prefix.realKey(key));
        }
    }

    public void deletePattern(String pattern){
        Set<String> keys = redisTemplate.keys(pattern);
        if(keys != null && keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }

    public <T> T getBean(RedisKeyPrefix prefix, String key, Class<T> clazz){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = operations.get(prefix.realKey(key));
        return JsonUtil.toBean(value, clazz);
    }

    public <T> List<T> getList(RedisKeyPrefix prefix, String key, Class<T> clazz){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value = operations.get(prefix.realKey(key));
        return JsonUtil.toList(value, clazz);
    }

    public Long incr(RedisKeyPrefix prefix, String key){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.increment(prefix.realKey(key));
    }

    public Long decr(RedisKeyPrefix prefix, String key){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.decrement(prefix.realKey(key));
    }

}
