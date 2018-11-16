package com.li.seckill.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/30 15:32
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String host;
    private int port;
    private int timeout; //单位:秒
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait; //单位:秒

}
