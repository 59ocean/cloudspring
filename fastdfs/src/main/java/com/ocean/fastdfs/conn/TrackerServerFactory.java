/**
 * 项目名：caibab
 * 包名：com.framework.util.fastdfs
 * 文件名：TrackerServerFactory.java
 * 版本信息：
 * 日期：2018-3-23-上午9:37:59
 * Copyright (c) 2018广东材巴巴信息科技有限公司
 * 
 */
package com.ocean.fastdfs.conn;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * @类描述：
 * 
 * @创建人：tanw @修改时间：2018-3-23 上午9:37:59 @修改备注：
 * @version 1.0.0
 */
public class TrackerServerFactory extends BasePooledObjectFactory<TrackerServer> {
	@Override
	public TrackerServer create() throws Exception {
		TrackerClient trackerClient = new TrackerClient();
		return trackerClient.getConnection();
	}

	@Override
	public PooledObject<TrackerServer> wrap(TrackerServer obj) {
		return new DefaultPooledObject<TrackerServer>(obj);
	}

	@Override
	public void destroyObject(PooledObject<TrackerServer> p) throws Exception {
		p.getObject().close();
	}

}
