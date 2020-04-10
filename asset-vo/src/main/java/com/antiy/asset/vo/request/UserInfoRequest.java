package com.antiy.asset.vo.request;

public class UserInfoRequest {
    //申请人
    private String userName;

    //申请人ID
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
