package com.li.seckill.service;

import com.li.seckill.dao.OrderDao;
import com.li.seckill.domain.MiaoshaOrder;
import com.li.seckill.domain.OrderInfo;
import com.li.seckill.domain.User;
import com.li.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Auther Liyg
 * @Date 2018/11/2
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;


    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {
        //1.生成orderInfo
        OrderInfo orderInfo= new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        //写入数据库
        long orderId=orderDao.insert(orderInfo);

        //2.生成MiaoshaOrder
        MiaoshaOrder miaoshaOrder=new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        //写入数据库
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        //将orderInfo返回,用于页面展示
        return orderInfo;

    }
}
