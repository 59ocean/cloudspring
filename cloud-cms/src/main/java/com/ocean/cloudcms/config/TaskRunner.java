package com.ocean.cloudcms.config;


import com.ocean.cloudcms.entity.JobAndTrigger;
import com.ocean.cloudcms.utils.SensitiveWordInit;
import com.ocean.cloudcms.utils.SensitivewordEngine;
import com.ocean.cloudcms.utils.Testgul;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author: Bob Simon
 * @Description: 初始化一个测试Demo任务
 * @Date: Created in 9:35 2018\4\29 0029
 */
@Component
public class TaskRunner implements ApplicationRunner {
    
	private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);


	/*@Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;*/
	@Autowired
	@Qualifier("SchedulerFactory")
	private SchedulerFactoryBean schedulerFactoryBean;
	
    @SuppressWarnings({ "rawtypes","unchecked" })
	@Override
    public void run(ApplicationArguments var) throws Exception{


    	/*Scheduler scheduler = schedulerFactoryBean.getScheduler();

    		LOGGER.info("初始化测试任务");
    		JobAndTrigger quartz = new JobAndTrigger();
    		quartz.setJobName("ocean");
    		quartz.setJobGroup("ocean");
    		quartz.setDescription("测试任务ocean");
    		quartz.setJobClassName("com.ocean.cloudcms.task.job.QuarzJob1");
    		quartz.setCronExpression("0/20 * * * * ?");
   	        Class cls = Class.forName(quartz.getJobClassName()) ;
   	        cls.newInstance();

   	        *//**构建job信息*//*
   	        JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
   	        		quartz.getJobGroup())
   	        		.withDescription(quartz.getDescription()).build();

   	        *//**触发时间点*//*
   	        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
   	        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+quartz.getJobName(),quartz.getJobGroup())
   	                .startNow().withSchedule(cronScheduleBuilder).build();	

   	        *//**交由Scheduler安排触发*//*
   	        scheduler.scheduleJob(job, trigger);*/


    }

}