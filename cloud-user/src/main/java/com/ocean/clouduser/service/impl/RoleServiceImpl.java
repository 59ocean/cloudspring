package com.ocean.clouduser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.clouduser.dao.RoleMapper;
import com.ocean.clouduser.entity.Role;
import com.ocean.clouduser.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenhy
 * @date @time 2019/7/18 15:03
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Override
	public List<Role> getRolesByUserId(Long userId){
		return roleMapper.selectByUserId(userId);
	}

}
