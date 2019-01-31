package com.ocean.clouduser.controller;




import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.ocean.cloudcommon.utils.R;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.query.UserQuery;
import com.ocean.clouduser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 * @author bootdo
 */
@RequestMapping("/v1/user")
@RestController
@Api(tags="用户服务接口")
public class UserController extends BaseController {
    @Autowired
	UserService userService;

	@GetMapping("/view")
	@ApiOperation(value = "根据id获得用户")
	@ApiImplicitParam(name="id",value = "用户id",required = true)
    public User getUser(@RequestParam("id")Long id){
		return userService.getById(id);
	}




	@GetMapping("/view2")
	@ApiOperation(value = "获得用户实体")
	@ResponseBody
	public Object getUser2(@RequestParam("id")String id){
		User user = new User();
		user.setCity("梅州");
		user.setBirth(new Date());
		user.setMobile("1316655");
		Map<String,Object> map = new HashMap<>();
		map.put("name","abc");
		map.put("user",user);
		return JSON.toJSON(map);
	}
	@GetMapping("/list")
	@ApiOperation(value = "用户分页")
	public Page getUserPage(UserQuery query){
		return userService.pageByMap(query);
	}



}
