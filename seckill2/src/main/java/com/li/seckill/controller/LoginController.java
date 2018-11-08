package com.li.seckill.controller;

import com.li.seckill.result.Result;
import com.li.seckill.service.UserService;
import com.li.seckill.util.ValidatorUtil;
import com.li.seckill.vo.LoginVo;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    private static Logger log= LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    public Result doLogin(HttpServletResponse response,@Valid LoginVo vo){
        log.info(vo.toString());

        userService.login(response,vo);
        return Result.success(true);
    }

}
