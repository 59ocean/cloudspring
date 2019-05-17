package com.ocean.cloudcms;

import com.ocean.cloudcms.config.FileUploadProperty;
import com.ocean.cloudcms.dto.SensitiveBean;
import com.ocean.cloudcms.utils.SensitivewordEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@Configuration
@MapperScan("com.ocean.cloudcms.dao")
@EnableConfigurationProperties({ FileUploadProperty.class})
@EnableFeignClients
public class CloudCmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudCmsApplication.class, args);
	}

	/**f
	 * 文件上传配置
	 * @return
	 */
	/*@Bean
	public MultipartConfigElement multipartConfigElement(FileUploadProperty fileUploadProperty){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(fileUploadProperty.getMaxFileSize());//文件最大
		factory.setMaxRequestSize(fileUploadProperty.getMaxRequestSize());//设置总上传数据总大小
		return factory.createMultipartConfig();
	}*/

}

