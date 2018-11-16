package com.li.seckill.controller;

import com.li.seckill.domain.User;
import com.li.seckill.rabbitmq.MQSender;
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


//    @Autowired
//    MQSender sender;
//    @RequestMapping("/mq")
//    @ResponseBody
//    public String mq(){
//        sender.send("hello,rabbitmq!");
//        return "done";
//    }
//
//    @RequestMapping("/topic")
//    @ResponseBody
//    public String topic(){
//        sender.sendTopic("hello,rabbitmq!");
//        return "done";
//    }
//
//    @RequestMapping("/fanout")
//    @ResponseBody
//    public String fanout(){
//        sender.sendFanout("hello,rabbitmq!");
//        return "done";
//    }
//
//    @RequestMapping("/header")
//    @ResponseBody
//    public String header(){
//        sender.sendHeader("hello,rabbitmq!");
//        return "done";
//    }
}
