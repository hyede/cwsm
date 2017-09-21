package com.cwsm.platfrom.model.dao.impl;


import com.cwsm.platfrom.model.dao.JpaGenericDao;
import org.springframework.stereotype.Repository;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Ming on 5/16/17.
 */
@Repository
public class JpaGenericDaoImpl implements JpaGenericDao {

    @PersistenceContext
    private EntityManager em;

    public JpaGenericDaoImpl() {
    }

    @Override
    public List executeSql(String sql, Object... params) {
        Query query = em.createQuery(sql);
        for(int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }

    @Override
    public List executeNativeSql(String sql, Object... params) {
        Query query = em.createNativeQuery(sql);
        for(int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }
}
