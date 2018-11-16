package com.li.seckill.access;

import com.alibaba.fastjson.JSON;
import com.li.seckill.domain.User;
import com.li.seckill.redis.AccessKey;
import com.li.seckill.redis.RedisService;
import com.li.seckill.result.Result;
import com.li.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Auther Liyg
 * @Date 2018/11/16
 */
public class AccessInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            User user= getUser(request,response);

            UserContext.setUser(user);
            HandlerMethod hm= (HandlerMethod) handler;

            //获取请求方法上的AccessLimit注解
            AccessLimit accessLimit= hm.getMethodAnnotation(AccessLimit.class);

            if(accessLimit==null){
                return true;
            }

            //获取注解的参数
            int seconds= accessLimit.seconds();
            int maxCount=accessLimit.maxCount();
            boolean needLogin= accessLimit.needLogin();
            String key=request.getRequestURI();

            //如果注解设置为needLogin=true, 那么用户必须先登录
            if(needLogin){
                if(user==null){
                    //用response返回错误信息
                    render(response, Result.NOTLOGIN);
                }
                key+= "_"+user.getId();
            }
            else{
                //todo
            }

            //进行请求频率限制 在seconds时间内,只允许maxCount次请求.
            AccessKey ak= AccessKey.withExpire(seconds);
            Integer count=redisService.get(ak, key, Integer.class);
            if(count ==null){
                redisService.set(ak, key, 1);
            }
            else if(count< maxCount){
                redisService.incr(ak,key);
            }
            else{
                render(response,Result.ILLEGAL_REQUEST);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, Result result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();

        String str= JSON.toJSONString(result);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private User getUser(HttpServletRequest request,HttpServletResponse response){
        String paramToken= request.getParameter(UserService.COOKIE_NAME);
        String cookieToken= getCookieValue(request,UserService.COOKIE_NAME);

        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token= StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request,String cookieName){
        Cookie[] cookies= request.getCookies();
        if(cookies ==null || cookies.length<=0){
            return null;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
