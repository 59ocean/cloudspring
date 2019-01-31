package com.ocean.clouduser.query.baseQuery;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseQuery implements Serializable {
    @ApiModelProperty(name="pageNumber",value="当前页码")
    private int pageNumber =1;
    @ApiModelProperty(name="limit",value="限制数量")
    private int limit =10;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
