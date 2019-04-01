package com.ocean.cloudcms.message.service;

import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: chenhy
 * @Date: 2019/2/22 14:07
 * @Version 1.0
 */
@Component
public class Mqservice {
    private Lock lock = new ReentrantLock();
    public void test(Map<String,Object> params){

        lock.lock();
        System.out.println("==========11========");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==========2==========");
        lock.unlock();
    }
}
