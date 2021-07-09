package com.train.hdfsclouddisk.storage.service.impl;

import com.train.hdfsclouddisk.storage.backendstorage.FileBackEndStorage;
import com.train.hdfsclouddisk.storage.backendstorage.impl.FileBackEndHDFSImpl;
import com.train.hdfsclouddisk.storage.service.FileStorage;
import com.train.hdfsclouddisk.user.model.CloudFile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/6 10:19
 */
@Service
public class FileStorageImpl implements FileStorage {
    @Override
    public void uploadFile(CloudFile cloudFile) {

        FileBackEndStorage fbes = new FileBackEndHDFSImpl();
        try {
            fbes.uploadFile(cloudFile);
        } catch (IOException e) {
            System.out.println("文件存储异常");
            e.printStackTrace();
        }


    }

    @Override
    public void createDir(String objectKey) {
        FileBackEndStorage fbes = new FileBackEndHDFSImpl();
        try {
            fbes.mkdir(objectKey);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public List<CloudFile> listFiles(String parentKey) {
        FileBackEndStorage fbes = new FileBackEndHDFSImpl();
        return fbes.listFiles(parentKey);
    }

    @Override
    public InputStream downloadFile(CloudFile cloudFile) {
        FileBackEndStorage fbes = new FileBackEndHDFSImpl();
        InputStream is = null;
        try {
            is = fbes.download(cloudFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    public void deleteFile(String objectKey) {
        FileBackEndStorage fbes = new FileBackEndHDFSImpl();
        try {
            fbes.rm(objectKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
