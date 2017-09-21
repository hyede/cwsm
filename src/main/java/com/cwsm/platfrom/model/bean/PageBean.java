package com.cwsm.platfrom.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 10/27/16.
 */
public class PageBean<T> extends GenericObject {

    private List<T> result = new ArrayList<>();
    private long total = 0;
    private int totalPages = 0;
    private int pageStart = 1;
    private int pageSize = 0;

    public PageBean() {
    }

    public PageBean(List<T> result, long total, int totalPages, int pageStart, int pageSize) {
        this.result = result;
        this.total = total;
        this.totalPages = totalPages;
        this.pageStart = pageStart;
        this.pageSize = pageSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
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

    public static <T> PageBean<T> copy(PageBean another, List<T> result) {
        PageBean<T> pageBean = new PageBean<>();
        pageBean.setResult(result);
        pageBean.setTotal(another.getTotal());
        pageBean.setTotalPages(another.getTotalPages());
        pageBean.setPageStart(another.getPageStart());
        pageBean.setPageSize(another.getPageSize());
        return pageBean;
    }
}
