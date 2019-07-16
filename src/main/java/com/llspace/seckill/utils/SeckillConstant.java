package com.llspace.seckill.utils;

/**
 * <p>@filename SeckillConstant</p>
 * <p>
 * <p>@description 系统常量定义</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/19 15:43
 **/
public class SeckillConstant {
    //系统token有效期 60分钟
    public static final int TOKEN_EXPIRE = 36000;
    //cookie中token name
    public static final String COOKIE_TOKEN_NAME = "token";
    //md5固定salt
    public static final String MD5_SALT = "1a2b3c4d";
    //error path
    public static final String ERROR_PATH = "/error";
    //消息队列名称
    public static final String QUEUE_NAME = "seckill_queue";


}
