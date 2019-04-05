package com.ocean.clouduser.query;

import com.ocean.clouduser.query.baseQuery.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

public class UserQuery extends BaseQuery {

    @ApiModelProperty(name="username",value="用户名")
    private String username;
    @ApiModelProperty(name="name",value="真实姓名")
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
