package com.llspace.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>@filename MD5Util</p>
 * <p>
 * <p>@description MD5加密工具类</p>
 *
 * 前端http传递：formpass = md5(明文+固定salt)
 * 后端存数据库：dbpass = md5(formpass+随机salt)  随机salt一并存入数据库中
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/18 16:05
 **/
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass) {
        String salt = SeckillConstant.MD5_SALT;
        String str = salt(inputPass, salt);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt(formPass, salt);;
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String salt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    private static String salt(String pass, String salt){
        return "" + salt.charAt(0) + salt.charAt(2) + pass + salt.charAt(4) + salt.charAt(6);
    }
}
