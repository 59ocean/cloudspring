package com.ocean.clouduser.service;



import com.baomidou.mybatisplus.plugins.Page;
import com.ocean.cloudcommon.utils.Pager;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.query.UserQuery;
import com.ocean.clouduser.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService extends BaseService<User> {
    List<User> listByProperties(Map<String, Object> params);

    Page<UserVo> pageByMap(UserQuery query);



	/*List<User> list(Map<String, Object> map);

	int count(Map<String, Object> map);*/


}
