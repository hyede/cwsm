package com.cwsm.platfrom.model.dao;

import com.cwsm.platfrom.entity.EntityObject;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;


/**
 * Created by Ming on 5/8/17.
 */
public class QueryCriteria<T extends EntityObject> {

    public static int DEFAULT_PAGE_SIZE = 20;
    public static int DEFAULT_PAGE_START = 1;
    public static Expression DEFAULT_ORDER_BY = new PathBuilder(Long.class, "id");
    public static Order DEFAULT_ORDER = Order.DESC;

    private Class<T> targetClass;
    private BooleanExpression criteria;
    private Expression orderBy = DEFAULT_ORDER_BY;
    private Order order = DEFAULT_ORDER;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageStart = DEFAULT_PAGE_START;

    public QueryCriteria(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public BooleanExpression getCriteria() {
        return criteria;
    }

    public void setCriteria(BooleanExpression criteria) {
        this.criteria = criteria;
    }

    public Expression getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Expression orderBy) {
        this.orderBy = orderBy;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    @Override
    public String toString() {
        return "QueryCriteria {" +
                " \n targetClass = " + targetClass.getSimpleName() +
                ", \n criteria = " + criteria.toString() +
                ",\n orderBy = " + orderBy +
                ",\n order = " + order +
                ",\n pageSize = " + pageSize +
                ",\n pageStart = " + pageStart +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QueryCriteria<?> that = (QueryCriteria<?>) o;

        if (pageSize != that.pageSize) {
            return false;
        }
        if (pageStart != that.pageStart) {
            return false;
        }
        if (targetClass != null ? !targetClass.equals(that.targetClass) : that.targetClass != null) {
            return false;
        }
        if (criteria != null ? !criteria.toString().equals(that.criteria.toString()) : that.criteria != null) {
            return false;
        }
        if (orderBy != null ? !orderBy.toString().equals(that.orderBy.toString()) : that.orderBy != null) {
            return false;
        }
        return order == that.order;
    }

    @Override
    public int hashCode() {
        int result = targetClass != null ? targetClass.hashCode() : 0;
        result = 31 * result + (criteria != null ? criteria.toString().hashCode() : 0);
        result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + pageSize;
        result = 31 * result + pageStart;
        return result;
    }
}
