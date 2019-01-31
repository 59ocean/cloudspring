package com.ocean.clouduser.service.impl;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.ocean.clouduser.service.BaseService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BaseServiceImpl<M extends BaseMapper<T>,T> implements BaseService<T> {
    @Autowired
    protected M baseMapper;
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }
    public BaseServiceImpl() {
    }
    @Override
    public boolean save(T entity) {
        return retBool(this.baseMapper.insert(entity));
    }
    @Override
    public boolean updateById(T entity) {
        return retBool(this.baseMapper.updateById(entity));
    }
    @Override
    public T getById(Serializable id) {
        return this.baseMapper.selectById(id);
    }
    @Override
    public List<T> listByIds(List<? extends Serializable> idList) {
        return this.baseMapper.selectBatchIds(idList);
    }
    @Override
    public List<T> listByMap(Map<String, Object> columnMap) {
        return this.baseMapper.selectByMap(columnMap);
    }
    @Override
    public boolean delete(Long id){
        return retBool(this.baseMapper.deleteById(id));
    }
    @Override
    public boolean deleteByListId(List<? extends  Serializable> idList){
        return retBool(this.baseMapper.deleteBatchIds(idList));
    }

}
