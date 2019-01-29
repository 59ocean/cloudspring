package com.ocean.clouduser.dao;

import com.ocean.clouduser.entity.CmsFile;

public interface CmsFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CmsFile record);

    int insertSelective(CmsFile record);

    CmsFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmsFile record);

    int updateByPrimaryKey(CmsFile record);
}