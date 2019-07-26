package com.ocean.clouduser.feign;

import com.ocean.cloudcommon.utils.ApiResponse;
import com.ocean.clouduser.config.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chenhy
 * @date @time 2019/7/23 15:02
 */
@FeignClient(value="cms-service",configuration = FeignRequestInterceptor.class)
public interface CmsServiceFeign {

	@GetMapping("/test/oauthfeign")
	ApiResponse oauthfeign ();
}
