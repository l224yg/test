package com.li.seckill.redis;

/**
 * @Auther Liyg
 * @Date 2018/11/16
 */
public class AccessKey extends BasePrefix {

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public AccessKey(String prefix) {
        super(prefix);
    }

    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds,"access");
    }
}
