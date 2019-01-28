package com.ocean.clouduser.service;



import com.ocean.clouduser.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface UserService {
	User get(Long id);

	/*List<User> list(Map<String, Object> map);

	int count(Map<String, Object> map);*/


}
