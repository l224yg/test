package com.li.seckill.service;

import com.li.seckill.dao.UserDao;
import com.li.seckill.domain.User;
import com.li.seckill.exception.GlobalException;
import com.li.seckill.redis.RedisService;
import com.li.seckill.redis.SeckillUserKey;
import com.li.seckill.result.Result;
import com.li.seckill.util.MD5Utils;
import com.li.seckill.util.UUIDUtil;
import com.li.seckill.vo.LoginVo;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 22:50
 */
@Service
public class UserService {

    public static final String COOKIE_NAME="token";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    public User getById(long id){
        return userDao.getById(id);
    }


    @Transactional
    public void dbTx() {
    }

    public boolean login(HttpServletResponse response,LoginVo vo) {
        if(vo==null){
            throw new GlobalException(Result.SERVER_ERROR) ;
        }
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        User user = getById(Long.parseLong(mobile));

        if(user==null){
            throw new GlobalException(Result.MOBILE_NOT_EXIST);
        }
        //验证密码
        String calcPass = MD5Utils.formPassToDBPass(password, user.getSalt());
        if(!calcPass.equals(user.getPassword())){
            throw new GlobalException(Result.PASSWORD_ERROR);
        }

        String token= UUIDUtil.uuid();
        addCookie(response,token,user);

        return true;
    }

    private void addCookie(HttpServletResponse response,String token,User user) {
        //生成新的cookie, 第一次生成 或 替换老的cookie,延长有效期

        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie=new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public User getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }

        User user = redisService.get(SeckillUserKey.token, token, User.class);
        //先延长有效期
        if(user!=null) {
            addCookie(response,token, user);
        }

        return user;
    }
}
