package com.llspace.seckill.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

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
     * json string to java bean
     */
    public static <T> T toBean(String json, Class<T> beanClass) {
        if (beanClass.equals(String.class)) {
            return (T) json;
        }
        return JSONObject.parseObject(json, beanClass);
    }

    /**
     * json string to list
     */
    public static <T> List<T> toList(String json, Class<T> beanClass) {
        return JSONArray.parseArray(json, beanClass);
    }

    /**
     * java bean to json string
     */
    public static <T> String toJSONString(T t) {
        String json = "";
        if (t instanceof List) {
            json = JSONArray.toJSONString((List<T>) t, true);
        } else {
            json = JSONObject.toJSONString(t);
        }
        return json;
    }

}
