package com.ocean.cloudcms.conn;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * TrackerServer 对象池
 * <p>
 *
 * @author xiang.wei
 * @version 1.0
 */
public class TrackerServerPool {
    /**
     * org.slf4j.Logger
     */
    private static Logger logger = LoggerFactory.getLogger(TrackerServerPool.class);

    /**
     * TrackerServer 配置文件路径
     */
        private static final String FASTDFS_CONFIG_PATH = "fdfs_client.conf";

    /**
     * 最大连接数 default 8.
     * 需要在application.properties中配置
     */
    @Value("${max_storage_connection}")
    private static int maxStorageConnection;

    /**
     * TrackerServer 对象池.
     * GenericObjectPool 没有无参构造
     */
    private static GenericObjectPool<TrackerServer> trackerServerPool;

    private TrackerServerPool(){};

    private static synchronized GenericObjectPool<TrackerServer> getObjectPool(){
        if(trackerServerPool == null){
            try {
                // 加载配置文件
                ClassPathResource resource = new ClassPathResource(FASTDFS_CONFIG_PATH);
                ClientGlobal.init(resource.getClassLoader().getResource(FASTDFS_CONFIG_PATH).getPath());

            } catch (IOException e) {
                logger.info("加载fastDFS配置文件出错={}",e.getMessage());
            } catch (Exception e) {
                logger.info("加载fastDFS配置文件出错={}",e.getMessage());
            }
            // Pool配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(2);
            if(maxStorageConnection > 0){
                poolConfig.setMaxTotal(maxStorageConnection);
            }

            trackerServerPool = new GenericObjectPool<>(new TrackerServerFactory(), poolConfig);
        }
        return trackerServerPool;
    }

    /**
     * 获取 TrackerServer
     * @return TrackerServer
     *
     */
    public static TrackerServer borrowObject(){
        TrackerServer trackerServer = null;
        try {
            trackerServer = getObjectPool().borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackerServer;
    }

    /**
     * 回收 TrackerServer
     * @param trackerServer 需要回收的 TrackerServer
     */
    public static void returnObject(TrackerServer trackerServer){
        getObjectPool().returnObject(trackerServer);
    }
}
