package com.ocean.clouduser.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.ocean.clouduser.entity.Dept;
import com.ocean.clouduser.entity.User;
import com.ocean.clouduser.query.UserQuery;
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
public interface UserDao extends BaseMapper<User>{

	User get(Long userId);

	List<User> getList(Pagination page,UserQuery query);

	List<Dept> getDeptWithUser();

	List<Map<String,Object>> getDeptWithUser2();
	List<String> getDeptNames();
}
