package com.ocean.cloudorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@MapperScan("com.ocean.cloudorder.dao")
@EnableEurekaClient
@SpringBootApplication
public class CloudOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudOrderApplication.class, args);
	}

}
