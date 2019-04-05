package com.ocean.fastdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * 系统级别的水印配置
 * @author Administrator
 *
 */
@ConfigurationProperties(prefix = "fastdfs.image.mark")
public class ImageMarkConfig {
	/**默认true，加水印*/
	public boolean enable = true;
	public boolean saveOrigin=true;
	public String iconPath;
	public float alpha = 0.6f;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public boolean isSaveOrigin() {
		return saveOrigin;
	}

	public void setSaveOrigin(boolean saveOrigin) {
		this.saveOrigin = saveOrigin;
	}

	

}
