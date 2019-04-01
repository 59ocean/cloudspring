package com.ocean.fastdfs.service;

import java.io.InputStream;
import java.util.Map;

import com.ocean.fastdfs.config.RequestImageMarkConfig;
import com.ocean.fastdfs.config.RequestImageScaleConfig;

/***
 * 常用的文件方法
 * 
 * @author Administrator
 *
 */
public interface IFastdfsClient extends IGenerateClient {
	/**
	 * 上传图片根据配置参数生成图片
	 */
	public String uploadImage(InputStream inputStream, String filename, Map<String, String> descriptions,
							  RequestImageMarkConfig markConfig, RequestImageScaleConfig scaleConfig);
}
