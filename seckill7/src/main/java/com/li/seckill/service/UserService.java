package com.li.seckill.service;

import com.li.seckill.dao.UserDao;
import com.li.seckill.domain.MiaoshaUser;
import com.li.seckill.domain.User;
import com.li.seckill.exception.GlobalException;
import com.li.seckill.exception.GlobleExceptionHandler;
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

    /**
     * 通过id获取用户  attach缓存
     * @param id
     * @return
     */
    public User getById(long id){
        //取缓存
        User user = redisService.get(SeckillUserKey.getById, ""+id, User.class);

        if(user!=null){
            return user;
        }
        //查数据库
        user= userDao.getById(id);
        if(user!=null){
           redisService.set(SeckillUserKey.getById, ""+id, user);
        }
        return user;
    }

    public boolean updatePassword(String token,long id, String formPass){
        //获取user
        User user = getById(id);
        if(user==null){
            throw new GlobalException(Result.NOTEXIST);
        }
        //更新数据库
        User toBeUpdated= new User();
        toBeUpdated.setId(id);
        toBeUpdated.setPassword(MD5Utils.formPassToDBPass(formPass, user.getSalt()));
        userDao.update(toBeUpdated);
        //处理缓存,对象
        redisService.delete(SeckillUserKey.getById,""+id);
        user.setPassword(toBeUpdated.getPassword());
        redisService.set(SeckillUserKey.token, token, user);
        return true;
    }


    @Transactional
    public void dbTx() {
    }

    /**
     * 登录操作
     * @param response
     * @param vo
     * @return
     */
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

    /**
     * 添加cookie
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response,String token,User user) {
        //生成新的cookie, 第一次生成 或 替换老的cookie,延长有效期

        redisService.set(SeckillUserKey.token, token, user);
        Cookie cookie=new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 通过token获取用户
     * @param response
     * @param token
     * @return
     */
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
