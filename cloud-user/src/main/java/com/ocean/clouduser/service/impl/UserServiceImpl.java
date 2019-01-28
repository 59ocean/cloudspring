package com.ocean.clouduser.service.impl;



import com.ocean.clouduser.dao.UserDao;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Author bootdo 1992lcg@163.com
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userMapper;


	private static final Logger logger = LoggerFactory.getLogger(UserService.class);


	@Override
	public User get(Long id) {
		return userMapper.get(id);
	}

	/*@Override
	public List<User> list(Map<String, Object> map) {
		return userMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}
*/





}
