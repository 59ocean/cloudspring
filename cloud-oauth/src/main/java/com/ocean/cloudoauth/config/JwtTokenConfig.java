package com.ocean.cloudoauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtTokenConfig{

//        @Bean
//        public TokenStore jwtTokenStore(){
//            return new JwtTokenStore(jwtAccessTokenConverter());
//        }

    /**
     * token生成处理：指定签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("ocean");
        System.out.println("=====指定签名");
        return accessTokenConverter;
    }

    @Bean
    public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
    }
}