package com.train.hdfsclouddisk.user.service.impl;

import com.train.hdfsclouddisk.user.dao.UserDao;
import com.train.hdfsclouddisk.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/3 11:25
 */

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserDao userDao;


    @Override
    public UserInfo login(String username) {
        return userDao.getUser(username);
    }
}
