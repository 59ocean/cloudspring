package com.ocean.clouduser.query.baseQuery;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseQuery implements Serializable {
    @ApiModelProperty(name="pageNo",value="当前页码")
    private int pageNo =1;
    @ApiModelProperty(name="pageSize",value="限制数量")
    private int pageSize =10;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
