package com.li.seckill.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.seckill.domain.MiaoshaOrder;
import com.li.seckill.domain.User;
import com.li.seckill.redis.RedisService;
import com.li.seckill.service.GoodsService;
import com.li.seckill.service.MiaoshaService;
import com.li.seckill.service.OrderService;
import com.li.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Auther Liyg
 * @Date 2018/11/14
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){

        log.info("miaosha.queue receive message"+ message);

        try {
            //获取消息内容,
            MiaoshaMessage msg = mapper.readValue(message, MiaoshaMessage.class);

            User user = msg.getUser();
            long goodsId=msg.getGoodsId();

            //查询数据库商品库存
            GoodsVo goods= goodsService.getGoodsVoByGoodsId(goodsId);

            int stock = goods.getStockCount();

            //判断库存是否充足
            if(stock<=0){
                return;
            }
            //判断是否已经秒杀到
            MiaoshaOrder order= orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);

            if(order!=null){
                return;
            }

            //减库存,生成订单,写入秒杀订单
            miaoshaService.miaosha(user, goods);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
