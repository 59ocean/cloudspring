package com.ocean.cloudcms.dto;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenhy
 * @Date: 2019/3/5 10:21
 * @Version 1.0
 */
@Component
public class SensitiveBean {

    private static SensitiveBean instance = null;

    private List<String> sensitiveWords;

    public static SensitiveBean getInstance() {
        return instance;
    }

    public static void setInstance(SensitiveBean instance) {
        SensitiveBean.instance = instance;
    }

    public List<String> getSensitiveWords() {
        return sensitiveWords;
    }

    public void setSensitiveWords(List<String> sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }

    private SensitiveBean() {
    }

    public static synchronized SensitiveBean GetInstance(){
        if(instance==null) {
            synchronized (SensitiveBean.class){
                if(instance == null){
                    instance = new SensitiveBean();
                }
            }
        }
        return instance;
    }

}
