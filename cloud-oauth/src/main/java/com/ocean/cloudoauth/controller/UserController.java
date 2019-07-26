package com.ocean.cloudoauth.controller;

import com.ocean.cloudcommon.utils.ApiResponse;
import com.ocean.cloudcommon.utils.R;
import com.ocean.cloudoauth.feign.UserServiceFeign;
import com.ocean.cloudoauth.vo.UserVo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>必须要有，做验证</p>
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:10:43
 * ProjectName:Mirco-Service-Skeleton
 */
@RestController
@RequestMapping("/users")
@Api(tags = "oathu")
public class UserController {


	@Autowired
	private UserServiceFeign userServiceFeign;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    //暴露Remote Token Services接口
    //如果其它服务需要验证Token，则需要远程调用授权服务暴露的验证Token的API接口
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    @ApiOperation(value = "获取token",notes = "获取当前token")
    public Principal getUser(Principal principal) {

	    System.out.println("====获取token");
    	return principal;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "获取token",notes = "获取当前token")
    public ApiResponse test() {
    	//UserVo userVo = userServiceFeign.getUser(1L);

    	return userServiceFeign.test2();
    }

    @DeleteMapping("/logout")
    public R removeAccessToken(String access_token) {
	    System.out.println("============移除token===============");
        return R.operate(consumerTokenServices.revokeToken(access_token));
    }

    public static void main (String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("cloud"));
    }



}
