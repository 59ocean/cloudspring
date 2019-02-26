package com.ocean.cloudcms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: chenhy
 * @Date: 2019/2/13 10:53
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "fastfsfile")
public class FileUploadProperty {
    private String maxFileSize;
    private String maxRequestSize;

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }
}
