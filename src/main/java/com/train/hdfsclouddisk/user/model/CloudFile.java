package com.train.hdfsclouddisk.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.InputStream;
import java.util.Date;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/6 10:02
 */

@Data
public class CloudFile {

    private String objectKey;//实际上就是/用户名/路径/文件名
    private UserInfo onwer;
    private String fileName;
    private long size;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String extName;
    private boolean isDir;
    private InputStream context;


}
