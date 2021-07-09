import com.train.hdfsclouddisk.user.model.CloudFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:lixiuzhong
 * @Date:2021/7/7 9:36
 */
public class HDFSTest {

    private static final String HADOOP_SERVER = "hdfs://192.168.153.80:8020";
    private static final String USER_NAME = "root";
    private static FileSystem fileSystem;

    @Before
    public void prepare(){
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

    @After
    public void destroy(){
        fileSystem = null;
    }

    @Test
    public void listFiles(){

        FileStatus[] statuses = null;
        CloudFile cloudFile = null;
        List<CloudFile> cloudFileList = new ArrayList<CloudFile>();
        try {
            statuses = fileSystem.listStatus(new Path("/jerry"));
            for (FileStatus fileStatus : statuses) {
                cloudFile = new CloudFile();
                cloudFile.setDir(fileStatus.isDirectory());
                cloudFile.setCreateTime(new Date(fileStatus.getModificationTime()));
                cloudFile.setSize(fileStatus.getLen());
                cloudFile.setFileName(fileStatus.getPath().getName());
                //System.out.println(fileStatus.toString());
                cloudFile.setObjectKey(fileStatus.getPath().toString().replaceFirst(HADOOP_SERVER,""));

                cloudFileList.add(cloudFile);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
@Test
    public void rm(){
        Path filePath = new Path("/jerry/携程酒店订单.png");
    try {
        fileSystem.delete(filePath,true);
    } catch (IOException e) {
        e.printStackTrace();
    }


}
@Test
    public void rename(){
    Path sourcePath = new Path("/jerry/住宿说明.docx");
    Path descPath = new Path("/jerry/住宿说明222.docx");
    try {
        fileSystem.rename(sourcePath,descPath);
    } catch (IOException e) {
        e.printStackTrace();
    }


}




}
