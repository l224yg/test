package com.li.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/30 15:43
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取单个对象
     * @param prefix 封装key中的前缀
     * @param key 封装key中的后段内容
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            String str = jedis.get(realKey);
            T value = stringToBean(str, clazz);
            return value;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> T stringToBean(String str,Class<T> clazz){
        if(str==null || str.length()<=0){
            return null;
        }
        else if(clazz==int.class ||clazz==Integer.class){
            return (T)Integer.valueOf(str);
        }
        else if(clazz==String.class){
            return (T)str;
        }
        else if(clazz==long.class ||clazz==Long.class){
            return (T) Long.valueOf(str);
        }
        else {
            T value = JSON.parseObject(str, clazz);
            return value;
        }
    }

    /**
     * 设置单个键值对
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String str= beanToString(value);
            if(str==null){
                return false;
            }
            String realKey=prefix.getPrefix()+key;
            int seconds=prefix.expireSeconds();
            if(seconds<=0) {
                jedis.set(realKey, str);
            }
            else{
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }


    private <T> String beanToString(T value){
        if(value==null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz==int.class || clazz==Integer.class){
            return value+"";
        }
        else if(clazz==String.class){
            return (String) value;
        }
        else if(clazz==long.class || clazz==Long.class){
            return value+"";
        }
        else {
            return JSON.toJSONString(value);
        }
    }


    private void returnToPool(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }

    /**
     * 查看key是否存在
     * @param prefix
     * @param key
     * @return
     */
    public boolean exists(KeyPrefix prefix,String key){
        Jedis jedis=null;

        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.exists(realKey);
        } finally {
            jedis.close();
        }
    }

    /**
     * 某个键对应的值做+1操作
     * @param prefix
     * @param key
     * @return
     */
    public Long incr(KeyPrefix prefix,String key){
        Jedis jedis=null;

        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.incr(realKey);
        } finally {
            jedis.close();
        }
    }

    /**
     * 某个键对应的值进行-1操作
     * @param prefix
     * @param key
     * @return
     */
    public Long decr(KeyPrefix prefix,String key){
        Jedis jedis=null;

        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.decr(realKey);
        } finally {
            jedis.close();
        }
    }

}
