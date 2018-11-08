package com.li.seckill.redis;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
