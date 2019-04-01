package com.ocean.fastdfs.config;

/***
 * 请求时的水印配置，如果有值会覆盖系统级别的配置
 * @author Administrator
 *
 */
public class RequestImageMarkConfig extends ImageMarkConfig {
	public RequestImageMarkConfig() {

	}

	public RequestImageMarkConfig(boolean enable) {
		this.enable = enable;
	}

	public RequestImageMarkConfig(boolean enable, String iconPath) {
		this.enable = enable;
		this.iconPath = iconPath;
	}
}
