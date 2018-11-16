package com.li.seckill.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther Liyg
 * @Date 2018/11/14
 */
@Slf4j
@Service
public class MQSender {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMiaoshaMessage(MiaoshaMessage message) {
        try {
            String msg=mapper.writeValueAsString(message);

            amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
            log.info("send message:"+message);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
