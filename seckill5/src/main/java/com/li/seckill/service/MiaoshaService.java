package com.li.seckill.service;

import com.li.seckill.dao.GoodsDao;
import com.li.seckill.domain.Goods;
import com.li.seckill.domain.OrderInfo;
import com.li.seckill.domain.User;
import com.li.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther Liyg
 * @Date 2018/11/2
 */
@Service
public class MiaoshaService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goods) {
        //减少库存
        goodsService.reduceStock(goods);
        //下订单 order+ orderInfo
        return orderService.createOrder(user,goods);

    }
}
