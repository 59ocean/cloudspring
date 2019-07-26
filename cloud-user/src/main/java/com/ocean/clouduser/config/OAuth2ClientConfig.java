package com.ocean.clouduser.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client //开启OAuth2Client
@EnableConfigurationProperties
@Configuration
public class OAuth2ClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")//获取Bean的配置属性
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        //配置受保护资源的信息
        return new ClientCredentialsResourceDetails();
    }

    /**
     *  @EnableOAuth2Client.
     * 1.oauth2ClientContextFilter
     * 2.AccessTokenRequest
     */
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        //配置一个过滤器，存储当前请求和上下文
        //在request域内创建 AccessTokenRequest 类型的Bean。
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        //向Uaa服务请求的
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }
}