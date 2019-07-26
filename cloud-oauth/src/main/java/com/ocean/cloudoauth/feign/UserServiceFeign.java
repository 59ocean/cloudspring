package com.ocean.cloudoauth.feign;

import com.ocean.cloudcommon.utils.ApiResponse;
import com.ocean.cloudoauth.vo.UserVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenhy
 * @date @time 2019/7/18 14:44
 */
@FeignClient("user-service")
public interface UserServiceFeign {

	@RequestMapping(value = "/v1/user/findByUsername",method = RequestMethod.GET)
	public UserVo getUserByUsername(@RequestParam("username")String username);


	@GetMapping("/v1/user/view")
	public UserVo getUser(@RequestParam("id")Long id);

	@RequestMapping(value="/v1/user/test2",method = RequestMethod.GET)
	public ApiResponse test2();
}
