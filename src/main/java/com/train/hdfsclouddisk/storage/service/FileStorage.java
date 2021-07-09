package com.train.hdfsclouddisk.storage.service;

import com.train.hdfsclouddisk.user.model.CloudFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/6 10:17
 */
public interface FileStorage {

    public void uploadFile(CloudFile cloudFile);


    public  void createDir(String objectKey);

    public List<CloudFile> listFiles(String parentKey);

    public InputStream downloadFile(CloudFile cloudFile);

    public void deleteFile(String objectKey);

}
