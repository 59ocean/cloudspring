package com.ocean.clouduser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ocean.clouduser.entity.Role;

import java.util.List;

/**
 * @author chenhy
 * @date @time 2019/7/18 15:02
 */
public interface IRoleService extends IService<Role> {
	List<Role> getRolesByUserId (Long userId);
}
