package com.llspace.seckill.utils;

import java.util.UUID;

/**
 * <p>@filename UUIDUtil</p>
 * <p>
 * <p>@description uuid工具类</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 17:04
 **/
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
