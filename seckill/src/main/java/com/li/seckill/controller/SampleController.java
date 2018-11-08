package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.redis.RedisService;
import com.li.seckill.redis.UserKey;
import com.li.seckill.result.Result;
import com.li.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 21:00
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 响应页面模板
     * @param model
     * @return
     */
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "laowang");
        return "hello";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("success");
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result helloError(){
        return Result.SERVER_ERROR;
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<String> dbTx(){
        try {
            userService.dbTx();
            return Result.success("success");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.SERVER_ERROR;
        }
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,"1",User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet(){

        User user= new User();
        user.setId(1);
        user.setName("boss");
        //UserKey:id1
        redisService.set(UserKey.getById,"1", user);
        return Result.success("success");
    }

    @RequestMapping("/redis/exit")
    @ResponseBody
    public Result<String> exists(){

        User user= new User();
        user.setId(1);
        user.setName("boss");
        //UserKey:id1
        redisService.set(UserKey.getById,"1", user);
        return Result.success("success");
    }
}
