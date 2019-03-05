package com.ocean.clouduser.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
    * mybatis-plus配置     
    *     
    */    
    @Configuration
    public class MybatisPlusConfig {
        /**         
        * 分页插件         
        */        
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            PaginationInterceptor page = new PaginationInterceptor();             
            page.setDialectType("mysql");             
            return page;        
        }    
    }