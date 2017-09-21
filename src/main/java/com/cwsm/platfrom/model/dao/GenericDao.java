package com.cwsm.platfrom.model.dao;


import com.cwsm.platfrom.model.entity.EntityObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by Ming on 5/16/17.
 */
@NoRepositoryBean
public interface GenericDao<T extends EntityObject<K>, K extends Serializable> extends JpaRepository<T, K>, QueryDslPredicateExecutor<T> {
}
