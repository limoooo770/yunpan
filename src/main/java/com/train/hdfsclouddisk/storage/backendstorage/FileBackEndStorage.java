package com.train.hdfsclouddisk.storage.backendstorage;

import com.train.hdfsclouddisk.user.model.CloudFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/6 10:21
 */
public interface FileBackEndStorage {

    public void uploadFile(CloudFile cloudFile) throws IOException;


    public void mkdir(String objectKey) throws Exception;

    public List<CloudFile> listFiles(String objectKey);

    public InputStream download(CloudFile cloudFile) throws IOException;
    public void rm(String objectKey) throws IOException;

}
