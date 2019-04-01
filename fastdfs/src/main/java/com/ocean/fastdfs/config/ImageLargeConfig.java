package com.ocean.fastdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * 大图，也就是一般网站默认的大图
 * @author Administrator
 *
 */
@ConfigurationProperties(prefix = "fastdfs.image.large")
public class ImageLargeConfig {
	public boolean enable = false;
	protected int width=600;
	protected int height=600;
	public float alpha = 0.8f;
	//压缩文件的后缀名,默认是_large
	protected String prefix="_large";

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
