package com.antiy.asset.vo.query;

/**
 * @Filename: ActivityCodeAndAreaIdQuery Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2019/3/21
 */
public class ActivityCodeAndAreaIdQuery {
    /**
     * 当前登录用户token
     */
    private String Authorization;
    /**
     * 角色code标识
     */
    private String roleCode;

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
