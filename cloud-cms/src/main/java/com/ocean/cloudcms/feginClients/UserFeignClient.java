package com.ocean.cloudcms.feginClients;

import com.ocean.cloudcms.vo.UserVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenhy
 * @date @time 2019/5/17 10:41
 */
@FeignClient("user-service")
public interface UserFeignClient {
	//根据id获得用户
	@GetMapping("/v1/user/view")
	public UserVo getUser(@RequestParam("id")Long id);
}
