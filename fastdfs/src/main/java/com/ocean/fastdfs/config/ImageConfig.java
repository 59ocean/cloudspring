package com.ocean.fastdfs.config;

/***
 * 图片配置类
 * 
 * @author Administrator
 *
 */
public class ImageConfig {

	public ImageConfig(ImageMarkConfig mark, ImageScaleConfig scale,ImageLargeConfig large) {
		this.mark = mark;
		this.scale = scale;
		this.large=large;
	}
	//水印配置文件
	private ImageMarkConfig mark;
	//压缩图片设置
	private ImageScaleConfig scale;
	//页面大图显示
	private ImageLargeConfig large;

	public ImageMarkConfig getMark() {
		return mark;
	}

	public void setMark(ImageMarkConfig mark) {
		this.mark = mark;
	}

	public ImageScaleConfig getScale() {
		return scale;
	}

	public void setScale(ImageScaleConfig scale) {
		this.scale = scale;
	}

	public ImageLargeConfig getLarge() {
		return large;
	}

	public void setLarge(ImageLargeConfig large) {
		this.large = large;
	}

	
}
