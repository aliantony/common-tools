package com.antiy.asset.vo.request;

import io.swagger.annotations.ApiModelProperty;

public class UserInfoRequest {
    @ApiModelProperty("申请人")
    private String userName;

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

    @Override
    public String toString() {
        return "UserInfoRequest{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
