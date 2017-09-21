package com.cwsm.platfrom.model.dao;



import com.cwsm.platfrom.model.bean.PageBean;
import com.cwsm.platfrom.model.entity.EntityObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 3/4/17.
 */
public interface IRepositoryService {

    <T extends EntityObject<K>, K extends Serializable> T save(T object);
    <T extends EntityObject<K>, K extends Serializable> void delete(T object);
    <T extends EntityObject<K>, K extends Serializable> void delete(Class<T> clazz, K id);
    <T extends EntityObject<K>, K extends Serializable> T getById(Class<T> clazz, K id);
    <T extends EntityObject<K>, K extends Serializable> List<T> listAll(Class<T> clazz);
    <T extends EntityObject> PageBean<T> query(QueryCriteria<T> criteria);
    <T extends EntityObject> long count(QueryCriteria<T> criteria);
    <T extends EntityObject> boolean exist(QueryCriteria<T> criteria);
    List executeSql(String sql, Object... params);
    List executeNativeSql(String sql, Object... params);
}
