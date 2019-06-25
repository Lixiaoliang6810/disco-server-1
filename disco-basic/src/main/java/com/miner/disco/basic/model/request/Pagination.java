package com.miner.disco.basic.model.request;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/10/31
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = -6918683184646606870L;

    private Integer offset=1;

    private Integer limit=20;

    private String order = "DESC";

    private String property = "create_date";

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return (this.offset <= 1) ? 0 : (offset - 1) * limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
