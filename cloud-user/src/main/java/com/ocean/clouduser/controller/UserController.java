package com.ocean.clouduser.controller;




import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.ocean.cloudcommon.utils.R;
import com.ocean.clouduser.dao.UserDao;
import com.ocean.clouduser.entity.Dept;
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

	@Autowired
    UserDao userDao;

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

	@GetMapping("/deptlist")
	@ApiOperation(value = "获得部门实体")
	@ResponseBody
	public List<Dept> getDeptWithUser(@RequestParam("id")String id){
		System.out.println("获得部门尸体444");
		List<Dept> list =userDao.getDeptWithUser();
		return list;
	}
	@GetMapping("/deptlist2")
	@ApiOperation(value = "获得部门实体")
	@ResponseBody
	public List<Map<String,Object>> getDeptWithUser2(){
		List<Map<String,Object>> list =userDao.getDeptWithUser2();
		return list;
	}
	@GetMapping("/getDeptNames")
	@ApiOperation(value = "获得部门实体名称")
	@ResponseBody
	public List<String> getDeptName(){
		List<String> list =userDao.getDeptNames();
		return list;
	}


}
