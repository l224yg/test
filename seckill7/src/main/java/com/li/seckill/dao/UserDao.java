package com.li.seckill.dao;

import com.li.seckill.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 22:45
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id}")
    User getById(@Param("id") long id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    int insert(User user);

    @Update("update user set password=#{password} where id=#{id}")
    void update(User toBeUpdated);
}
