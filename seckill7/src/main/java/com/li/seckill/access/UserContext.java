package com.li.seckill.access;

import com.li.seckill.domain.User;

/**
 * @Auther Liyg
 * @Date 2018/11/16
 */
public class UserContext {

    private static ThreadLocal<User> userHolder= new ThreadLocal<>();

    public static void setUser(User user){
        userHolder.set(user);
    }

    public static User getUser(){
        return userHolder.get();
    }
}
