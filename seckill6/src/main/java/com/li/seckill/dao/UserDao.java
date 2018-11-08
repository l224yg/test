package com.li.seckill.dao;

import com.li.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Desciption
 * @Auther Liyg
 * @Date Created in 2018/10/29 22:45
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id}")
    public User getById(@Param("id") long id);

    @Insert("insert into user(id,name) values(#{id},#{name})")
    public int insert(User user);
}
