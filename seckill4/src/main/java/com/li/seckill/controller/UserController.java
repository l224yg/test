package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.result.Result;
import com.li.seckill.service.GoodsService;
import com.li.seckill.service.UserService;
import com.li.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger log= LoggerFactory.getLogger(UserController.class);


    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(Model model, User user){

        return Result.success(user);
    }


}
