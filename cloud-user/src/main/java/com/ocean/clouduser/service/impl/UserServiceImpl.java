package com.ocean.clouduser.service.impl;



import com.baomidou.mybatisplus.plugins.Page;
import com.ocean.clouduser.dao.UserDao;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.query.UserQuery;
import com.ocean.clouduser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author bootdo 1992lcg@163.com
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao,User> implements UserService {
	@Autowired
	private UserDao userDao;


	private static final Logger logger = LoggerFactory.getLogger(UserService.class);




	@Override
	public List<User> listByProperties(Map<String, Object> params){
		return userDao.selectByMap(params);
	}

	@Override
	public Page<User> pageByMap(UserQuery query){
		Page<User> page = new Page<>(query.getPageNo(),query.getPageSize());
	/*	Page<UserVo> page = new Page<>(query.getPageNo(),query.getPageSize());
		EntityWrapper entityWrapper = new EntityWrapper();
		if(StringUtils.isNotBlank(query.getName())){
			entityWrapper.like("name","%"+query.getName()+"%");
		}
		if(StringUtils.isNotBlank(query.getUsername())){
			entityWrapper.like("username","%"+query.getUsername()+"%");
		}
		page.setRecords(userDao.selectPage(page,entityWrapper));*/
		return page.setRecords(userDao.getList(page,query));
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
