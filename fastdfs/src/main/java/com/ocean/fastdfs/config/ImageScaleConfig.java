package com.ocean.fastdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastdfs.image.scale")
public class ImageScaleConfig {
	/**默认true,是否开启压缩,大小默认100X100*/
	public boolean enable = true;
	protected int width=100;
	protected int height=100;
	//压缩文件的后缀名
	protected String prefix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * 生成前缀如:_150x150
	 */
	public String getPrefixName() {
		if (prefix == null) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("_").append(width).append("x").append(height);
			prefix = new String(buffer);
		}
		return prefix;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
