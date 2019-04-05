/**
 * 项目名：caibab
 * 包名：com.framework.util.fastdfs
 * 文件名：TrackerServerPool.java
 * 版本信息：
 * 日期：2018-3-22-下午5:18:02
 * Copyright (c) 2018广东材巴巴信息科技有限公司
 * 
 */
package com.ocean.fastdfs.conn;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.fastdfs.TrackerServer;

/**
 * tracker 服务器线程池
 * 
 * @author Administrator
 *
 */
public class TrackerServerPool {

	private GenericObjectPool<TrackerServer> pool;

	public TrackerServerPool(TrackerServerFactory factory, GenericObjectPoolConfig config) {
		pool = new GenericObjectPool<TrackerServer>(factory, config);
	}

	public TrackerServer borrowObject() throws Exception {
		return pool.borrowObject();
	}

	public void returnObject(TrackerServer obj) {
		pool.returnObject(obj);
	}

}
