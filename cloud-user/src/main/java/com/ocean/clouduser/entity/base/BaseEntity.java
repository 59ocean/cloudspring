package com.ocean.clouduser.entity.base;

import java.util.Date;

public class BaseEntity {
    private Date createDate;
    private String creator;
    private Date updateDate;
    private String updator;

    public Date getCreateDate () {
        return createDate;
    }

    public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator () {
        return creator;
    }

    public void setCreator (String creator) {
        this.creator = creator;
    }

    public Date getUpdateDate () {
        return updateDate;
    }

    public void setUpdateDate (Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdator () {
        return updator;
    }

    public void setUpdator (String updator) {
        this.updator = updator;
    }
}
