package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.result.Result;
import com.li.seckill.service.UserService;
import com.li.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @ResponseBody
    @RequestMapping("/do_login")
    public Result doLogin(HttpServletResponse response,@Valid LoginVo vo){
        log.info(vo.toString());

        userService.login(response,vo);
        return Result.success(true);
    }

    @RequestMapping("/is_login")
    @ResponseBody
    public Result isLogin(Model model, User user){

        if(user!=null){
            return Result.success(true);
        }
        return Result.NOTLOGIN;
    }

}
