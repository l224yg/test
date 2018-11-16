package com.li.seckill.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther Liyg
 * @Date 2018/11/14
 */
@Configuration
public class MQConfig {

    //秒杀queue
    public static final String MIAOSHA_QUEUE="miaosha.queue";

    //direct交换机模式   设置消息队列的名称
    public static final String QUEUE="queue";

    //topic交换机模式
    public static final String TOPIC_QUEUE1="topic.queue1";
    public static final String TOPIC_QUEUE2="topic.queue2";

    public static final String TOPIC_EXCHANGE="topicExchange";
    public static final String ROUTING_KEY1="topic.key1";
    //支持通配符,*代表一个单词,#代表0个或多个单词
    public static final String ROUTING_KEY2="topic.#";

    public static final String FANOUT_EXCHANGE= "fanoutExchange";

    public static final String HEADER_EXCHANGE= "headerExchange";
    public static final String HEADER_QUEUE="headerqueue";

    @Bean
    public Queue queue(){
        return new Queue(MIAOSHA_QUEUE,true);
    }

//    @Bean
//    public Queue queue(){
//        return new Queue(QUEUE,true);
//    }
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }

    /**
     * Fanout模式 广播
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding FanoutBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding FanoutBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    /**
     * header模式
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADER_EXCHANGE);
    }
    @Bean
    public Queue headerQueue(){
        return new Queue(HEADER_QUEUE,true);
    }
    @Bean
    public Binding headerBinding(){

        Map<String,Object> map= new HashMap<>();
        //指定需要的头部信息
        map.put("header1", "value1");
        map.put("header2", "value2");

        return BindingBuilder.bind(headerQueue()).to(headersExchange())
                .whereAll(map).match();
    }

}
