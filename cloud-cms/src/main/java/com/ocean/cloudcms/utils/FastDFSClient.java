package com.ocean.cloudcms.utils;

import com.ocean.cloudcms.dto.FastDFSFileEntity;
import com.ocean.cloudcms.conn.TrackerServerPool;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: chenhy
 * @Date: 2018/2/1 0029 9:30
 * @Description: FastDFS 操作类
 */
 public class FastDFSClient {

     private TrackerServerPool trackerServerPool;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    //双重守护单例
    private static volatile FastDFSClient mInstance;

    /**
     * 加载配置信息
     **/
    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            logger.error("FastDFS Client Init Fail!", e);
        }
    }

    private FastDFSClient() {

    }

    public static FastDFSClient getInstance() {
        if (mInstance == null) {
            synchronized (FastDFSClient.class) {
                if (mInstance == null) {
                    mInstance = new FastDFSClient();
                }
            }
        }
        return mInstance;
    }

    /**
     * @return java.lang.String[]
     * @Author maoqitian
     * @Description 上传文件
     * @Date 2018/10/29 0029 9:42
     * @Param [fastDFSFileEntity]
     **/
    public String[] upload(FastDFSFileEntity file) {
        logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

        NameValuePair[] metalist = new NameValuePair[1];

        metalist[0] = new NameValuePair("author", file.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        StorageClient storageClient = null;
        try {

            storageClient = getTrackerClient();
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), metalist);
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file:" + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
        }
        logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");
        if (uploadResults == null && storageClient != null) {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        return uploadResults;
    }


    public FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileByte);
            return ins;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }
    public byte[] downFile2(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            return fileByte;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    /**
     * @return int -1 失败 0成功
     * @Author maoqitian
     * @Description
     * @Date 2018/10/31 0031 11:19
     * @Param [remoteFileName]
     **/
    public int deleteFile(String remoteFileName)
            throws Exception {
        StorageClient storageClient = getTrackerClient();
        int i = storageClient.delete_file("group1", remoteFileName);
        logger.info("delete file successfully!!!" + i);
        return i;
    }

    public StorageServer[] getStoreStorages(String groupName)
            throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public ServerInfo[] getFetchStorages(String groupName,
                                         String remoteFileName) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public String getTrackerUrl() throws IOException {
        return "http://" + getTrackerServer().getInetSocketAddress().getHostString() + ":" + ClientGlobal.getG_tracker_http_port() + "/";
    }

    /**
     * @return org.csource.fastdfs.StorageClient
     * @Author maoqitian
     * @Description 获取 StorageClient
     * @Date 2018/10/29 0029 10:33
     * @Param []
     **/
    private StorageClient getTrackerClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

    /**
     * @return org.csource.fastdfs.TrackerServer
     * @Author maoqitian
     * @Description 获取 TrackerServer
     * @Date 2018/10/29 0029 10:34
     * @Param []
     **/
    private TrackerServer getTrackerServer() throws IOException {
       /* TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();*/
        return trackerServerPool.borrowObject();
    }

}
