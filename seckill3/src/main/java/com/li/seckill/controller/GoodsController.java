package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.redis.RedisService;
import com.li.seckill.result.Result;
import com.li.seckill.service.GoodsService;
import com.li.seckill.service.UserService;
import com.li.seckill.util.ValidatorUtil;
import com.li.seckill.vo.GoodsVo;
import com.li.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    private static Logger log= LoggerFactory.getLogger(GoodsController.class);


    //通过UserArgumentResolver将传来的信息封装到User中,作为参数传入方法
//    @RequestMapping("/toList")
//    public String toList(Model model,
//                          @CookieValue(value=UserService.COOKIE_NAME,required=false) String cookieToken,
//                          @RequestParam(value=UserService.COOKIE_NAME,required=false) String paramToken){
//        if(StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
//            return "login";
//        }
//        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//
//        User user=userService.getByToken(token);
//        model.addAttribute("user", user);
//        return "goods_list";
//    }

    @RequestMapping("/toList")
    public String toList(Model model,User user){
//        if(user==null){
//            return "login";
//        }
        model.addAttribute("user", user);

        //查询商品列表
        List<GoodsVo> goodsList=goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, User user, @PathVariable("goodsId") long goodsId){
        model.addAttribute("user", user);

        GoodsVo goods= goodsService.GetGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();

        long now = System.currentTimeMillis();

        //秒杀活动状态: 0=未开始,1=正在进行,2=已结束
        int miaoshaStatus=0;
        //距离秒杀开始还剩多少时间  未开始=计算开始时间-当前时间,正在进行=0,已结束=-1,
        int remainSeconds=0;

        if(startAt>now) {//秒杀还未开始
            miaoshaStatus=0;
            remainSeconds=(int)(startAt-now)/1000;
        }
        else if(endAt<now) {//秒杀已结束
            miaoshaStatus=2;
            remainSeconds=-1;
        }
        else{//秒杀正在进行
            miaoshaStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }


}
