package com.li.seckill.redis;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/30 16:47
 */
public interface KeyPrefix {
    int expireSeconds();
    String getPrefix();
}
