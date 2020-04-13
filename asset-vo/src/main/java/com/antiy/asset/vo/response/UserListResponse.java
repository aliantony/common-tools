package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class UserListResponse {
    //申请人
    @ApiModelProperty("申请人")
    private String userName;

    //申请人ID
    @ApiModelProperty("申请人ID")
    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
