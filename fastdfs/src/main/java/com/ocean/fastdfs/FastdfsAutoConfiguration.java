package com.ocean.fastdfs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.ocean.fastdfs.config.ImageConfig;
import com.ocean.fastdfs.config.ImageLargeConfig;
import com.ocean.fastdfs.config.ImageMarkConfig;
import com.ocean.fastdfs.config.ImageScaleConfig;
import com.ocean.fastdfs.conn.FastdfsProperties;
import com.ocean.fastdfs.conn.TrackerServerFactory;
import com.ocean.fastdfs.conn.TrackerServerPool;
import com.ocean.fastdfs.service.FastdfsClientImpl;
import com.ocean.fastdfs.service.GenerateClientImpl;
import com.ocean.fastdfs.service.IFastdfsClient;
import com.ocean.fastdfs.service.IGenerateClient;
import com.ocean.fastdfs.web.FastdfsAjaxUploadAct;
import com.ocean.fastdfs.web.FastdfsDownLoadAct;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;



@Configuration
@EnableConfigurationProperties({ FastdfsProperties.class, ImageMarkConfig.class, ImageScaleConfig.class,
		ImageLargeConfig.class })
@ConditionalOnProperty(prefix = "fastdfs", value = "enable", matchIfMissing = true)
public class FastdfsAutoConfiguration {
	private static Logger logger = LoggerFactory.getLogger(FastdfsAutoConfiguration.class);

	@Bean
	@ConditionalOnMissingBean(ImageConfig.class)
	public ImageConfig imageConfig(ImageMarkConfig markConfig, ImageScaleConfig scaleConfig, ImageLargeConfig large) {
		logger.debug("init bean imageConfig ");
		return new ImageConfig(markConfig, scaleConfig, large);
	}

	@Bean
	@ConditionalOnMissingBean(TrackerServerPool.class)
	public TrackerServerPool trackerServerPool(FastdfsProperties fastdfsProperties, ImageMarkConfig c) {
		init(fastdfsProperties);
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		TrackerServerPool pool = new TrackerServerPool(new TrackerServerFactory(), config);
		return pool;
	}

	/***
	 * 从配置类读入Properties类中
	 * 
	 * @param prop
	 * @param key
	 * @param value
	 */
	private void setProperty(Properties prop, String key, String value) {
		if (!StringUtils.isEmpty(value)) {
			prop.setProperty(key, value);
		}
	}

	// 初始化配置文件信息
	private void init(FastdfsProperties fastdfsProperties) {
		logger.debug("init:");
			Properties prop = new Properties();
			ClientGlobal.setG_charset(fastdfsProperties.getCharset());
			ClientGlobal.setG_connect_timeout(fastdfsProperties.getConnectTimeout());
			ClientGlobal.setG_anti_steal_token(fastdfsProperties.isHttpAntiStealToken() ? true : false);
			ClientGlobal.setG_secret_key(fastdfsProperties.getHttpSecretKey());
			ClientGlobal.setG_tracker_http_port(fastdfsProperties.getHttpTrackerHttpPort());
			ClientGlobal.setG_network_timeout(fastdfsProperties.getNetworkTimeout());
		    InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		   tracker_servers[0] = new InetSocketAddress(fastdfsProperties.getTrackerServers(),fastdfsProperties.getPort());
		    ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
		/*try {
			ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
			ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}*/
		if (logger.isDebugEnabled()) {
			logger.debug("config info" + ClientGlobal.DEFAULT_CONNECT_TIMEOUT);
		}
	}

	@Bean
	@ConditionalOnMissingBean(IGenerateClient.class)
	public IGenerateClient generateClient(TrackerServerPool trackerServerPool) {
		GenerateClientImpl client = new GenerateClientImpl();
		client.setTrackerServerPool(trackerServerPool);
		logger.debug("init bean generateClient ");
		return client;

	}

	/***
	 * 注入fastdfs客户端
	 * 
	 * @param trackerServerPool
	 * @param imageConfig
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(IFastdfsClient.class)
	public IFastdfsClient fastdfsClient(TrackerServerPool trackerServerPool, ImageConfig imageConfig) {
		FastdfsClientImpl client = new FastdfsClientImpl();
		client.setTrackerServerPool(trackerServerPool);
		client.setImageConfig(imageConfig);
		logger.debug("init bean fastDFSClient ");
		return client;

	}

	/***
	 * 注入ajax上次的默认control
	 * 
	 * @param fastdfsClient
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(FastdfsAjaxUploadAct.class)
	public FastdfsAjaxUploadAct fastdfsAjaxUploadAct(IFastdfsClient fastdfsClient) {
		FastdfsAjaxUploadAct upload = new FastdfsAjaxUploadAct();
		upload.setFastdfsClient(fastdfsClient);
		logger.debug("init bean FileUploadAct ");
		return upload;

	}

	/***
	 * 注入下载control
	 * 
	 * @param fastdfsClient
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(FastdfsDownLoadAct.class)
	public FastdfsDownLoadAct fastdfsDownLoadAct(IFastdfsClient fastdfsClient) {
		FastdfsDownLoadAct upload = new FastdfsDownLoadAct();
		upload.setFastdfsClient(fastdfsClient);
		logger.debug("init bean FastdfsDownLoadAct ");
		return upload;

	}
}
