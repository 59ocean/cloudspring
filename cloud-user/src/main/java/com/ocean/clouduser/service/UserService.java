package com.ocean.clouduser.service;



import com.ocean.clouduser.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService extends BaseService<User> {
    List<User> listByProperties(Map<String, Object> params);



	/*List<User> list(Map<String, Object> map);

	int count(Map<String, Object> map);*/


}
