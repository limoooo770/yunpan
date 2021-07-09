package com.train.hdfsclouddisk.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/3 10:52
 */

@Data
public class UserInfo {

    private int   id  		;
    private String   username   ;
    private String   pwd        ;
    private int   gender     ;
    private String   mobile     ;
    private String   email      ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    private Date   lastLogin  ;
    private int   status     ;
    private int   isAdmin    ;
    private String   portrait   ;
    private int   diskMaxSize;

}





