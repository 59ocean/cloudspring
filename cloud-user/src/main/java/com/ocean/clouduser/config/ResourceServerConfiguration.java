package com.ocean.clouduser.config;

import com.ocean.cloudcommon.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2资源服务器配置
 *
 * @author: chenhy
 * @date: 2018/10/23 10:31
 * @description:
 */
@Slf4j
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-resources/**","/v2/api-docs","/swagger-resources","/configuration/security", "/oauth/**","/v1/user/findByUsername","/webjars/**","/swagger-ui.html","/v1/user/view","/v1/user/test3").permitAll()
                // 监控端点内部放行
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutUrl("logout")
		        .permitAll()
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                //.accessDeniedHandler(new OpenAccessDeniedHandler())
                //.authenticationEntryPoint(new OpenAuthenticationEntryPoint())
                .and()
                .csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    	super.configure(resources);
	   /* resources.accessDeniedHandler((request,response,e)->{
		    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		    response.getWriter().write(new ObjectMapper().writeValueAsString(R.error("授权失败")));
	    });
	    resources.authenticationEntryPoint((request,response,e)->{
		    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		    response.getWriter().write(new ObjectMapper().writeValueAsString(R.error("token失败")));
	    });*/

    }
}

