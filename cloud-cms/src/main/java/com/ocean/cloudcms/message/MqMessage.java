package com.ocean.cloudcms.message;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/22 13:53
 * @Version 1.0
 */
public class MqMessage implements Serializable{
    private String messageId;
    private String serviceName;
    private String serviceMethod;
    private Map<String,Object> params;

    public MqMessage(String serviceName, String serviceMethod, Map<String, Object> params) {
        this.serviceName = serviceName;
        this.serviceMethod = serviceMethod;
        this.params = params;
    }
    public MqMessage() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
