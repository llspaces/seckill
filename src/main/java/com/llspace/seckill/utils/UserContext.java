package com.llspace.seckill.utils;

import com.llspace.seckill.entity.User;

/**
 * <p>@filename UserContext</p>
 * <p>
 * <p>@description 存储登录用户信息</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/21 16:17
 **/
public class UserContext {

    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }
}
