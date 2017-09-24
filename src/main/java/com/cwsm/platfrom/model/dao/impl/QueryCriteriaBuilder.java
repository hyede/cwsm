package com.cwsm.platfrom.model.dao.impl;

import com.cwsm.platfrom.model.dao.QueryCriteria;

import com.cwsm.platfrom.model.entity.EntityObject;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 5/8/17.
 */
public class QueryCriteriaBuilder {

    private QueryCriteria queryCriteria;

    private List<BooleanExpression> expressions = new ArrayList<>();

    public <T extends EntityObject> QueryCriteriaBuilder(Class<T> targetClass) {
        this.queryCriteria = new QueryCriteria(targetClass);
    }

    public QueryCriteriaBuilder addCondition(BooleanExpression expression) {
        this.expressions.add(expression);
        return this;
    }

    public QueryCriteriaBuilder setPageStart(int pageStart) {
        queryCriteria.setPageStart(pageStart);
        return this;
    }

    public QueryCriteriaBuilder setPageSize(int pageSize) {
        queryCriteria.setPageSize(pageSize);
        return this;
    }

    public QueryCriteriaBuilder setOrder(String order) {
        queryCriteria.setOrder(Order.valueOf(order.toUpperCase()));
        return this;
    }

    public QueryCriteriaBuilder setOrder(Order order) {
        queryCriteria.setOrder(order);
        return this;
    }

    public QueryCriteriaBuilder setOrderBy(Expression orderBy) {
        queryCriteria.setOrderBy(orderBy);
        return this;
    }

    public QueryCriteria build() {
        BooleanExpression result = null;
        for(BooleanExpression expression : expressions) {
            if(result == null) {
                result = expression;
            } else {
                result = result.and(expression);
            }
        }
        queryCriteria.setCriteria(result);
        return queryCriteria;
    }

    public QueryCriteria buildOR() {
        BooleanExpression result = null;
        for(BooleanExpression expression : expressions) {
            if(result == null) {
                result = expression;
            } else {
                result = result.or(expression);
            }
        }
        queryCriteria.setCriteria(result);
        return queryCriteria;
    }

}
