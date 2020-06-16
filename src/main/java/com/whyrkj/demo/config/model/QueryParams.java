package com.whyrkj.demo.config.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParams implements Serializable {

    private static final long serialVersionUID = -506101351959889180L;
    private Map query = new HashMap();
    private Integer page = 1;
    private Integer pageSize = 10;
    private List<Order> orders = new ArrayList();

    public Integer getPage() {
        return page;
    }

    public void put(Object key, Object value) {
        this.getQuery().put(key, value);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setQuery(Map query) {
        this.query = query;
    }

    public Map getQuery() {
        return query;
    }

    public String getQueryString(Object key) {
        Object value = query.get(key);
        if (value == null || "".equals(value)) {
            return null;
        }
        return value.toString();
    }

    public Integer getQueryInteger(Object key) {
        Object value = query.get(key);
        if (value == null || "".equals(value)) {
            return null;
        }
        return Integer.valueOf(value.toString());
    }

    public Integer getStarRow() {
        return (page - 1) * pageSize;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
