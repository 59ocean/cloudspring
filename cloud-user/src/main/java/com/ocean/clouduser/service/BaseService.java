package com.ocean.clouduser.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {
    boolean save(T entity);

    boolean updateById(T entity);

    T getById(Serializable id);

    List<T> listByIds(List<? extends Serializable> idList);

    List<T> listByMap(Map<String, Object> columnMap);

    boolean delete(Long id);

    boolean deleteByListId(List<? extends Serializable> idList);
}
