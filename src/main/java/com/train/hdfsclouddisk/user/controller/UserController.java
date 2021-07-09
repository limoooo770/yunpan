package com.train.hdfsclouddisk.user.controller;

import com.train.hdfsclouddisk.user.model.UserInfo;
import com.train.hdfsclouddisk.user.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/3 10:57
 */

@RestController
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping("login")
    public String doLogin(String username, String pwd, HttpSession session, HttpServletRequest request){

        System.out.println(username);
        System.out.println(pwd);

        UserInfo userInfo = userService.login(username);


        if(pwd.equals(userInfo.getPwd())){
            session.setAttribute("loginUser",userInfo);

            String parentKey = "/"+userInfo.getUsername();
            session.setAttribute("parentKey",parentKey);
            return "index";
        }
        return "error";
    }

    @PostMapping("register")
    public String doregister(String username, String pwd, HttpSession session, HttpServletRequest request){

        UserInfo userInfo = userService.login(username);


        return "error";
    }


}
