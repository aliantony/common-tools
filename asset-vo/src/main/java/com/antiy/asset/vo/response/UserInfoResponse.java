package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

public class UserInfoResponse {
    @ApiModelProperty("申请人")
    private String userName;

    @ApiModelProperty("申请人ID")
    private String userId;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("组织")
    private String department;

    @ApiModelProperty("组织id")
    private String departmentId;

    @ApiModelProperty("key")
    private String key;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
