package com.li.seckill.controller;

import com.li.seckill.domain.MiaoshaUser;
import com.li.seckill.domain.OrderInfo;
import com.li.seckill.domain.User;
import com.li.seckill.redis.RedisService;
import com.li.seckill.result.CodeMsg;
import com.li.seckill.result.Result;
import com.li.seckill.service.GoodsService;
import com.li.seckill.service.OrderService;
import com.li.seckill.service.UserService;
import com.li.seckill.vo.GoodsVo;
import com.li.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther Liyg
 * @Date 2018/11/16
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, User user,
                                      @RequestParam("orderId") long orderId) {
        if(user == null) {
            return Result.SESSION_ERROR;
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return Result.ORDER_NOT_EXIST;
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }
}
