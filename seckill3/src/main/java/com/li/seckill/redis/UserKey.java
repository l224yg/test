package com.li.seckill.redis;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class UserKey extends BasePrefix{

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById= new UserKey("id");
    public static UserKey getName=new UserKey("name");
}
