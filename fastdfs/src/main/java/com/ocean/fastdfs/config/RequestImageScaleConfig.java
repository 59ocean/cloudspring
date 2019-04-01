package com.ocean.fastdfs.config;

public class RequestImageScaleConfig extends ImageScaleConfig {
	public RequestImageScaleConfig() {

	}

	public RequestImageScaleConfig(boolean enable) {
		this.enable = enable;
	}

	public RequestImageScaleConfig(boolean enable, int width, int height) {
		this.enable = enable;
		this.width = width;
		this.height = height;
	}

	public RequestImageScaleConfig(boolean enable, int width, int height, String prefix) {
		this.enable = enable;
		this.width = width;
		this.height = height;
		this.prefix = prefix;
	}
}
