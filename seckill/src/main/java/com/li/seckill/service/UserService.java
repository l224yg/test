package com.li.seckill.service;

import com.li.seckill.dao.UserDao;
import com.li.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 22:50
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public void dbTx() {
        User user1=new User();
        user1.setId(3);
        user1.setName("xiaoli");
        userDao.insert(user1);

        User user2=new User();
        user2.setId(4);
        user2.setName("boss");
        userDao.insert(user2);

    }
}
