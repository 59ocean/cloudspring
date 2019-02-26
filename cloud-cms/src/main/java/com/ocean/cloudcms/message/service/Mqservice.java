package com.ocean.cloudcms.message.service;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: chenhy
 * @Date: 2019/2/22 14:07
 * @Version 1.0
 */
@Component
public class Mqservice {

    public void test(Map<String,Object> params){

        System.out.println(params.get("id"));
    }
}
