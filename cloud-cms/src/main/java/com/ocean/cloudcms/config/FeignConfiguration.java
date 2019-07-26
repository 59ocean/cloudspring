package com.ocean.cloudcms.config;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * @author chenhy
 * @date @time 2019/7/23 14:22
 */
//@Configuration
public class FeignConfiguration {
	/*@Bean
	@ConditionalOnProperty("security.oauth2.client")
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
	                                                        OAuth2ProtectedResourceDetails resource) {
		return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, resource);
	}*/
}
