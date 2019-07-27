package com.ocean.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.reactive.DispatcherHandler;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class CloudGatewayApplication {

	@Bean
	public ServerCodecConfigurer serverCodecConfigurer(){
		return new DefaultServerCodecConfigurer();
	}


	public static void main (String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}


}
