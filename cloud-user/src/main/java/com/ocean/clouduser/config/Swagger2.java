package com.ocean.clouduser.config;

import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

	private String accessTokenUri ="http://localhost:8090/oauth/token";



    @Bean
    public Docket createRestApi() {
	    ParameterBuilder tokenParam = new ParameterBuilder();
	    List<Parameter> parameters = new ArrayList<>();
	    tokenParam.name("Authorization")
			    .defaultValue("token")
			    .description("token令牌")
			    .modelRef(new ModelRef("string"))
			    .parameterType("header")
			    .required(true)
			    .build();
	    parameters.add(tokenParam.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ocean"))
                .paths(PathSelectors.any())
                .build()
		        //.securitySchemes(Collections.singletonList(securityScheme()))
		        //.securityContexts(Collections.singletonList(securityContext()));
		        .securitySchemes(securitySchemes())
		        .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("程序猿DD")
                .version("2.0")
                .build();
    }

	private SecurityScheme securityScheme() {
		return new OAuthBuilder()
				.name("OAuth2")
				.grantTypes(grantTypes())
				.scopes(Arrays.asList(scopes()))
				.build();
	}

	/**
	 * 设置 swagger2 认证的安全上下文
	 * */
	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(Collections.singletonList(new SecurityReference("oauth2", scopes())))
				.forPaths(PathSelectors.any())
				.build();
	}

	/**
	 * 设置认证的scope
	 * @return*/

	private AuthorizationScope[] scopes() {
		AuthorizationScope[] scopes = {

				new AuthorizationScope("server", "Access server API") };
		return scopes;
	}

    /**
	 *  使用密码模式
	 */
	@Bean
	List<GrantType> grantTypes() {
		List<GrantType> grantTypes = new ArrayList<>();
		GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8090/oauth/token");
		grantTypes.add(grantType );
		return grantTypes;
	}

	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeyList= new ArrayList();
		apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
		return apiKeyList;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts=new ArrayList<>();
		securityContexts.add(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(PathSelectors.regex("^(?!auth).*$"))
						.build());
		return securityContexts;
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences=new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}



}