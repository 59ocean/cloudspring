package com.ocean.cloudcms.config;

import com.ocean.cloudcommon.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

import javax.servlet.http.HttpServletResponse;

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
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-resources/**","/v2/api-docs","/swagger-resources","/configuration/security", "/oauth/**","/v1/user/findByUsername","/webjars/**","/swagger-ui.html").permitAll()
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
	   /* http
			    .headers().frameOptions().disable()
			    .and()
			    .csrf().disable()
			    .exceptionHandling()
			    .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			    .and()
			    .authorizeRequests()
			    .antMatchers("/swagger-resources/**","/v2/api-docs","/swagger-resources","/configuration/security", "/oauth/**","/v1/user/findByUsername","/webjars/**","/swagger-ui.html").permitAll()
			    .anyRequest().authenticated();*/
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	    super.configure(resources);

    }
}

