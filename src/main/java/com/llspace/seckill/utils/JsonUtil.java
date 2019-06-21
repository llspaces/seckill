package com.llspace.seckill.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.llspace.seckill.entity.User;

import java.util.*;

/**
 * <p>@filename JsonUtil</p>
 * <p>
 * <p>@description json常用方法封装工具类（fastjson）</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/20 14:11
 **/
public class JsonUtil {

    /**
     * jsonstring to javabean
     */
    public static <T> T toBean(String json, Class<T> beanClass){
        return JSONObject.parseObject(json, beanClass);
    }

    /**
     * jsonstring to list
     */
    public static <T> List<T> toList(String json, Class<T> beanClass){
        return JSONArray.parseArray(json, beanClass);
    }

    /**
     * javabean to jsonstring
     */
    public static <T> String toJSONString(T t){
        String json = "";
        if(t instanceof List){
            json = JSONArray.toJSONString((List<T>)t, true);
        }else {
            json = JSONObject.toJSONString(t);
        }
        return json;
    }

}
