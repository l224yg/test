package com.li.seckill.redis;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class SeckillUserKey extends BasePrefix{
    private static int DEFAULT_EXPIRE=3600*24*2;

    private SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public SeckillUserKey(String prefix) {
        super(prefix);
    }

    public static SeckillUserKey token= new SeckillUserKey(DEFAULT_EXPIRE,"tk");

}
