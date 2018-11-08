package com.li.seckill.controller;

import com.li.seckill.domain.MiaoshaOrder;
import com.li.seckill.domain.OrderInfo;
import com.li.seckill.domain.User;
import com.li.seckill.result.Result;
import com.li.seckill.service.GoodsService;
import com.li.seckill.service.MiaoshaService;
import com.li.seckill.service.OrderService;
import com.li.seckill.service.UserService;
import com.li.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    private static Logger log= LoggerFactory.getLogger(MiaoshaController.class);



    @RequestMapping("/do_miaosha/{goodsId}")
    public String doMiaosha(Model model,User user,@PathVariable("goodsId") Long goodsId){
        if(user==null){
            return "login";
        }
        model.addAttribute("user", user);

        //判断库存
        GoodsVo goods = goodsService.GetGoodsVoByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        //如果库存到0,返回错误页面
        if(stockCount<=0){
            model.addAttribute("errmsg", Result.MIAOSHAOVER_ERROR.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀成功过
        MiaoshaOrder order= orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order!=null){
            //如果已秒杀成功过一次,不允许再次秒杀,返回错误页面
            model.addAttribute("errmsg", Result.REPEATED_ERROR.getMsg());
            return "miaosha_fail";
        }

        //可以下秒杀订单,返回订单信息
        OrderInfo orderInfo= miaoshaService.miaosha(user,goods);

        //订单信息展示到页面
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }

}
