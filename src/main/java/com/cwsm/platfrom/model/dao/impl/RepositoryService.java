package com.cwsm.platfrom.model.dao.impl;

import com.cwsm.platfrom.model.bean.PageBean;
import com.cwsm.platfrom.model.dao.GenericDao;
import com.cwsm.platfrom.model.dao.IRepositoryService;
import com.cwsm.platfrom.model.dao.JpaGenericDao;
import com.cwsm.platfrom.model.dao.QueryCriteria;

import com.cwsm.platfrom.model.entity.EntityObject;
import com.querydsl.core.types.OrderSpecifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 3/4/17.
 */
@Service
public class RepositoryService implements IRepositoryService {

    private Repositories repositories;

    @Autowired
    private JpaGenericDao jpaGenericDao;

    @Autowired
    private ApplicationContext applicationContext;

    private <T extends EntityObject<K>, K extends Serializable> GenericDao<T, K> getRepository(Class<T> clazz) {
        if(repositories == null) {
            repositories = new Repositories(applicationContext);
        }
        GenericDao<T, K> dao = (GenericDao<T, K>) repositories.getRepositoryFor(clazz);
        if(dao == null) {
            throw new NullPointerException("Repository is not found for class: " + clazz);
        }
        return dao;
    }

    @Override
    @Transactional
    public <T extends EntityObject<K>, K extends Serializable> T save(T object) {
        GenericDao<T, K> dao = (GenericDao<T, K>) getRepository(object.getClass());
        return dao.save(object);
    }

    @Override
    @Transactional
    public <T extends EntityObject<K>, K extends Serializable> void delete(T object) {
        GenericDao<T, K> dao = (GenericDao<T, K>) getRepository(object.getClass());
        dao.delete(object);
    }

    @Override
    @Transactional
    public <T extends EntityObject<K>, K extends Serializable> void delete(Class<T> clazz, K id) {
        GenericDao<T, K> dao = getRepository(clazz);
        dao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends EntityObject<K>, K extends Serializable> T getById(Class<T> clazz, K id) {
        GenericDao<T, K> dao = getRepository(clazz);
        return dao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends EntityObject<K>, K extends Serializable> List<T> listAll(Class<T> clazz) {
        GenericDao<T, K> dao = getRepository(clazz);
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends EntityObject> PageBean<T> query(QueryCriteria<T> criteria) {
        GenericDao dao = getRepository(criteria.getTargetClass());
        OrderSpecifier orderSpecifier = new OrderSpecifier(criteria.getOrder(), criteria.getOrderBy(), OrderSpecifier.NullHandling.NullsLast);
        // page of PageRequest starts from 0 not 1. However since page in QueryCriteria starts from 1, we need to minus 1 here to follow the rule.
        QPageRequest pageRequest = new QPageRequest(criteria.getPageStart() - 1, criteria.getPageSize(), orderSpecifier);
        Page<T> page = dao.findAll(criteria.getCriteria(), pageRequest);
        PageBean<T> pageBean = new PageBean<>();
        pageBean.setResult(page.getContent());
        pageBean.setTotal(page.getTotalElements());
        pageBean.setTotalPages(page.getTotalPages());
        pageBean.setPageStart(pageRequest.getPageNumber() + 1);
        pageBean.setPageSize(pageRequest.getPageSize());
        return pageBean;
    }

    @Override
    public <T extends EntityObject> long count(QueryCriteria<T> criteria) {
        GenericDao dao = getRepository(criteria.getTargetClass());
        long count = dao.count(criteria.getCriteria());
        return count;
    }

    @Override
    public <T extends EntityObject> boolean exist(QueryCriteria<T> criteria) {
        GenericDao dao = getRepository(criteria.getTargetClass());
        boolean exist = dao.exists(criteria.getCriteria());
        return exist;
    }

    @Override
    @Transactional(readOnly = true)
    public List executeSql(String sql, Object... params) {
        return jpaGenericDao.executeSql(sql, params);
    }

    @Override
    @Transactional(readOnly = true)
    public List executeNativeSql(String sql, Object... params) {
        return jpaGenericDao.executeNativeSql(sql, params);
    }
}
