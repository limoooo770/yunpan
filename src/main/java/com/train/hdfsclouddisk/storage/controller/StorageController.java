package com.train.hdfsclouddisk.storage.controller;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.train.hdfsclouddisk.storage.service.FileStorage;
import com.train.hdfsclouddisk.user.model.CloudFile;
import com.train.hdfsclouddisk.user.model.UserInfo;
import org.apache.avro.data.Json;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/6 9:48
 */

@RestController
@RequestMapping(value = "storage",method = RequestMethod.POST)
public class StorageController {

    @Autowired
    FileStorage fileStorage;

    @RequestMapping(value = "home",method = RequestMethod.POST, produces = "application/json")
    public String getMyDiskFiles(String parentKey,HttpSession session, HttpServletRequest request){

        UserInfo loginUser = (UserInfo)session.getAttribute("loginUser");

        if(parentKey == null){
            parentKey = (String)session.getAttribute("parentKey");
            if(parentKey == null){
                parentKey = "/"+loginUser.getUsername();
            }
        }
        List<CloudFile> cloudFileList = fileStorage.listFiles(parentKey);
        System.out.println(JSONArray.toJSONString(cloudFileList));
        session.setAttribute("parentKey",parentKey);
        request.setAttribute("cloudfiles",cloudFileList);

        return JSONArray.toJSONString(cloudFileList);
    }

    @RequestMapping(value = "uploadfile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "myfile") MultipartFile myfile, String parentKey, HttpSession session){

        System.out.println("uploadFile now......");

        UserInfo loginUser = (UserInfo)session.getAttribute("loginUser");

        System.out.println(loginUser.getUsername());
        String filename = myfile.getOriginalFilename();
        System.out.println(myfile.getOriginalFilename());
        System.out.println(myfile.getSize());

        if(parentKey == null){
            parentKey = "/"+loginUser.getUsername();
        }
        String objectKey =parentKey+"/"+filename;


        CloudFile cloudFile =new CloudFile();
        cloudFile.setObjectKey(objectKey);
        cloudFile.setOnwer(loginUser);

        cloudFile.setFileName(filename);
        cloudFile.setSize(myfile.getSize());
        try {
            cloudFile.setContext(myfile.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传文件

        fileStorage.uploadFile(cloudFile);
        session.setAttribute("parentKey",parentKey);
        return "uploadfilesuccess";
    }

    @RequestMapping(value = "createdir",method = RequestMethod.POST)
    public String creatdir(@RequestParam(value = "dirname")String dirname,HttpSession session){

        System.out.println("create dir...");

        UserInfo loginUser = (UserInfo)session.getAttribute("loginUser");

        String objectKey = "/"+loginUser.getUsername()+"/"+dirname;// 相当于 /jerry+/+文件夹名

        fileStorage.createDir(objectKey);

        return "createdirsuccess";
    }


    @RequestMapping("downloadfile")
    public void downloadFile(String objectKey, HttpServletResponse response){

        System.out.println("ojk:"+objectKey);

        CloudFile cloudFile = new CloudFile();
        cloudFile.setObjectKey(objectKey);

        String downloadfileName = objectKey.substring(objectKey.lastIndexOf("/"),objectKey.length());
        cloudFile.setFileName(downloadfileName);


        response.setContentType("application/octet-stream");
        response.setHeader("content-type","application/octet-stream");
        try {
            downloadfileName = URLEncoder.encode(downloadfileName,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition","attachment;filename="+downloadfileName);

        InputStream is = fileStorage.downloadFile(cloudFile);
        byte[] buff = new byte[4096];
        int length = 0;

        OutputStream os = null;

        try {
            os = response.getOutputStream();
            length = is.read(buff);
            while(length !=-1){
                os.write(buff);
                length = is.read(buff);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping("deletefile")
    public String deletefile(String objectKey,HttpSession session){

        fileStorage.deleteFile(objectKey);
        return "deletefilesuccess";
    }

}




