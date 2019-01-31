package com.ocean.clouduser.controller;

import com.ocean.cloudcommon.constants.CommonConstants;
import com.ocean.cloudcommon.dto.UserTokenDto;
import com.ocean.cloudcommon.utils.JwtUtils;
import com.ocean.cloudcommon.utils.R;
import com.ocean.clouduser.dto.LoginDTO;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.entity.UserToken;
import com.ocean.clouduser.service.UserService;
import com.ocean.clouduser.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/v1/user")
@RestController
@Api(tags="用户登陆接口")
@Validated
public class LoginController extends BaseController {
    @Autowired
    UserService userService;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public R login(@RequestBody LoginDTO loginDTO){
        String username = loginDTO.getUsername().trim();
        String password = loginDTO.getPwd().trim();
        password = MD5Utils.encrypt(password);
        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        List<User> users = userService.listByProperties(param);
        if(users.size()<=0){
            return R.error("用户或密码错误");
        }
        User user = users.get(0);
        if(user==null || !user.getPassword().equals(password)){
            return R.error("用户或密码错误");
        }
        UserTokenDto userTokenDto = new UserTokenDto(user.getUsername(),user.getId().toString(),user.getName());
        String token = "";
        try {
            token = JwtUtils.generateToken(userTokenDto,2*60*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("登陆成功！")
                .put("user",user)
                .put("token",token);
    }


}