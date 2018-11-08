package com.li.seckill.redis;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class GoodsKey extends BasePrefix{

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsList= new GoodsKey(60,"gl");

}
