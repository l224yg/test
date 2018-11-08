package com.li.seckill.util;

import java.util.UUID;

/**
 * @Auther Liyg
 * @Date 2018/10/31
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","" );
    }
}
