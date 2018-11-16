package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(Model model, User user){
        return Result.success(user);
    }


}
