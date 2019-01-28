package com.ocean.clouduser.dao;

import com.ocean.clouduser.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
@Service
public interface UserDao {

	User get(Long userId);
	


}
