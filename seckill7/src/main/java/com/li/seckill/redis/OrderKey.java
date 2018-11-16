package com.li.seckill.redis;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix){
        super(prefix);
    }
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid= new OrderKey("moug");
}
