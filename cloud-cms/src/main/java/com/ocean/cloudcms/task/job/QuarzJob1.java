package com.ocean.cloudcms.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.sound.midi.Soundbank;

/**
 * @Author: chenhy
 * @Date: 2019/2/19 14:01
 * @Version 1.0
 */
public class QuarzJob1 implements Job {




    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("===QuarzJob1 执行==="+context.getJobDetail().getDescription());
    }
}
