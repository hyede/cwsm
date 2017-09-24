package com.cwsm.platfrom.model.bean;

/**
 * Created by Ming on 5/17/17.
 */
public class QueryBean extends GenericObject {

    public static final String ASCENDING_ORDER = "asc";
    public static final String DESCENDING_ORDER = "desc";
    private static final String DEFAULT_ORDER = DESCENDING_ORDER;
    private static final String DEFAULT_ORDER_BY = "id";
    private static final int DEFAULT_PAGE_START = 1;
    private static final int DEFAULT_PAGE_SIZE = 2;
    public static final int MAX_PAGE_SIZE = 999999;

    private String orderBy = DEFAULT_ORDER_BY;
    private String order = DEFAULT_ORDER;
    private int pageStart = DEFAULT_PAGE_START;
    private int pageSize = DEFAULT_PAGE_SIZE;

    public QueryBean() {
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
