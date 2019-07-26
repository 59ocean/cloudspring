package com.ocean.clouduser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableTransactionManagement
@MapperScan("com.ocean.clouduser.dao")
@EnableSwagger2
@EnableFeignClients
@SpringCloudApplication
@EnableHystrix
public class CloudUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(CloudUserApplication.class, args);
	}


}

