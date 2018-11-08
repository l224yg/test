package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.redis.RedisService;
import com.li.seckill.result.Result;
import com.li.seckill.service.UserService;
import com.li.seckill.util.ValidatorUtil;
import com.li.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;

    private static Logger log= LoggerFactory.getLogger(GoodsController.class);

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

        if(user==null){
            return "login";
        }
        model.addAttribute("user", user);
        return "goods_list";
    }

}
