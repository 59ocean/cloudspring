package com.ocean.cloudoauth.config;

import com.ocean.cloudoauth.error.BootOAuth2WebResponseExceptionTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台认证服务器配置
 *
 * @author chenhy
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * 1认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    /**
     * 2.用户信息
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Value("${access_token.store-jwt:fasle}")
    private boolean jwtTokenStore;

    /**
     * 令牌存放
     *3.token的存储管理
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        if(jwtTokenStore){
            System.out.println("===使用的是JwtTokenstore");
            System.out.println(jwtAccessTokenConverter);
            return new JwtTokenStore(jwtAccessTokenConverter);
        }
        System.out.println("===使用的是RedisTokenStore");
        return new RedisTokenStore(redisConnectionFactory);
    }

//    @Bean
//    protected JwtAccessTokenConverter jwtTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("ocean");
//        return converter;
//    }


    /**
     * 授权store
     *
     * @return
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }



    /**
     * 授权码
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 客户端配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
	   /* //配置两个客户端,一个用于password认证一个用于client认证
	    clients.inMemory()
			    .withClient("client_2")
			    .authorizedGrantTypes("password", "refresh_token")
			    .scopes("server")
			    .authorities("ROLE_server")
			    .secret(new BCryptPasswordEncoder().encode("123456"))
			    .accessTokenValiditySeconds(60 * 30)
			    .refreshTokenValiditySeconds(60 * 60);*/


    }
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator(){
        return new BootOAuth2WebResponseExceptionTranslator();
    }

    /**
     * 服务端端点配置 在服务器端点配置主要配置三点：1.认证管理器 2.用户信息 3.token的存储管理
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore())//Token的存储方式为内存
                .userDetailsService(userDetailsService)//读取用户的验证信息
                .authenticationManager(authenticationManager);//WebSecurity配置好的
        //endpoints.tokenServices(defaultTokenServices());
        System.out.println("使用jwt token");
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        endpoints
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter);

        endpoints.exceptionTranslator(webResponseExceptionTranslator());//认证异常翻译
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setAccessTokenValiditySeconds(60*60*12); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }

    /**
     * 服务端安全配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //对获取Token的请求不再拦截
        security.tokenKeyAccess("permitAll()");
        //验证获取Token的验证信息
        security .checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();
    }

    /**
     * jwt token增强，添加额外信息
     * @return
     */
    /*@Bean
    public TokenEnhancer tokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

                // 添加额外信息的map
                final Map<String, Object> additionMessage = new HashMap<>(2);
                // 获取当前登录的用户
                User user = (User) oAuth2Authentication.getUserAuthentication().getPrincipal();
                // 登录日志记录
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                System.out.println("======"+user);
               // log.info("当前用户为：{}", user);
                // 如果用户不为空 则把id放入jwt token中
                if (user != null) {

                    additionMessage.put("username", user.getUsername());
                }
                ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(additionMessage);
                return oAuth2AccessToken;
            }
        };
    }*/

}
