package com.train.hdfsclouddisk.storage.backendstorage.impl;

import com.train.hdfsclouddisk.storage.backendstorage.FileBackEndStorage;
import com.train.hdfsclouddisk.user.model.CloudFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Author:lixiuzhong
 * @Date:2021/7/6 10:22
 */
public class FileBackEndHDFSImpl implements FileBackEndStorage {

    private static final String HADOOP_SERVER = "hdfs://192.168.49.131:8020";
    private static final String USER_NAME = "root";
    private static FileSystem fileSystem;

    public FileBackEndHDFSImpl() {

        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");

        try {
            fileSystem = FileSystem.get(new URI(HADOOP_SERVER),conf,USER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uploadFile(CloudFile cloudFile) throws IOException {

        Path descPath = new Path(cloudFile.getObjectKey());
        InputStream fileContent = cloudFile.getContext();
        FSDataOutputStream fsDataOutputStream =  fileSystem.create(descPath);
        IOUtils.copyBytes(fileContent,fsDataOutputStream,4069,true);
        fsDataOutputStream.flush();
        fsDataOutputStream.close();
    }

    @Override
    public void mkdir(String objectKey) throws Exception {
        Path dirPath = new Path(objectKey);
        if(fileSystem.exists(dirPath)){
            throw new Exception("文件夹已存在");
        }else {
            fileSystem.mkdirs(dirPath);
        }


    }

    @Override
    public List<CloudFile> listFiles(String objectKey) {

        FileStatus[] statuses = null;
        CloudFile cloudFile = null;
        List<CloudFile> cloudFileList = new ArrayList<CloudFile>();
        try {
            statuses = fileSystem.listStatus(new Path(objectKey));
            for (FileStatus fileStatus : statuses) {
                cloudFile = new CloudFile();
                cloudFile.setDir(fileStatus.isDirectory());
                cloudFile.setCreateTime(new Date(fileStatus.getModificationTime()));
                cloudFile.setSize(fileStatus.getLen());
                cloudFile.setFileName(fileStatus.getPath().getName());
                cloudFile.setObjectKey(fileStatus.getPath().toString().replaceFirst(HADOOP_SERVER,""));

                cloudFileList.add(cloudFile);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cloudFileList;
    }

    @Override
    public InputStream download(CloudFile cloudFile) throws IOException {
        Path descPath = new Path(cloudFile.getObjectKey());
        InputStream is = fileSystem.open(descPath);


        return is;
    }

    public void rm(String objectKey) throws IOException {
        Path delPath = new Path(objectKey);
        fileSystem.delete(delPath,true);

    }


}
