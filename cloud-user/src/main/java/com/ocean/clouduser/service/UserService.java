package com.ocean.clouduser.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.query.UserQuery;

import java.util.List;
import java.util.Map;

public interface UserService  extends IService<User> {
    List<User> listByProperties(Map<String, Object> params);

    User findOneByUsername (String username);

    Page<User> pageByMap(UserQuery query);



	/*List<User> list(Map<String, Object> map);

	int count(Map<String, Object> map);*/


}
