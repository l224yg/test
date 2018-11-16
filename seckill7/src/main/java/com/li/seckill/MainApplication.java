package com.li.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 20:57
 */
@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(MainApplication.class); //启动类字节码文件
    }
}
