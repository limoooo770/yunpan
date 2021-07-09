package com.train.hdfsclouddisk.user.dao;

import com.train.hdfsclouddisk.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/3 11:37
 */

@Mapper
@Repository
public interface UserDao {

    @Select("select * from userInfo where username=#{username}")
    public UserInfo getUser(String username);



}
