package com.ocean.clouduser.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocean.clouduser.entity.Role;
import org.jsoup.Connection;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByUserId(Long UserId);
}