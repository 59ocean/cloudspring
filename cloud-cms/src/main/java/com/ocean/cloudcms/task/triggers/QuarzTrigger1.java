package com.ocean.cloudcms.task.triggers;

import com.caibab.common.base.entity.BaseObject;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenhy
 * @Date: 2019/2/19 14:09
 * @Version 1.0
 */
public class QuarzTrigger1 extends CronTriggerFactoryBean implements Serializable {


    public static void main(String[] args) {
        BaseObject object = new BaseObject();
        object.setCreateDt(new Date());
        System.out.println(object);
    }
}
