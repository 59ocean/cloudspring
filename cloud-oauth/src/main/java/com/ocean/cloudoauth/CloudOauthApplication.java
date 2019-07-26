package com.ocean.cloudoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2
public class CloudOauthApplication {

	public static void main (String[] args) {
		SpringApplication.run(CloudOauthApplication.class, args);
	}

}
