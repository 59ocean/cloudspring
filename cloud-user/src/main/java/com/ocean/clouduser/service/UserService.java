package com.ocean.clouduser.service;



import com.baomidou.mybatisplus.plugins.Page;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.query.UserQuery;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User> {
    List<User> listByProperties(Map<String, Object> params);

    Page<User> pageByMap(UserQuery query);



	/*List<User> list(Map<String, Object> map);

	int count(Map<String, Object> map);*/


}
