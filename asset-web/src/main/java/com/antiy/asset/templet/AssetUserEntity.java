package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class AssetUserEntity {

    /**
     * 姓名
     */
    @ExcelField(value = "name", align = 1, title = "姓名", type = 0)
    private String  name;

    /**
     * 部门主键
     */
    @ExcelField(value = "departmentId", align = 1, title = "所属部门", type = 0)
    private Integer departmentId;

    /**
     * 电子邮箱
     */
    @ExcelField(value = "email", align = 1, title = "电子邮箱", type = 0)
    private String  email;

    /**
     * qq号
     */
    @ExcelField(value = "qq", align = 1, title = "qq号", type = 0)
    private String  qq;

    /**
     * 微信
     */
    @ExcelField(value = "weixin", align = 1, title = "微信号", type = 0)
    private String  weixin;

    /**
     * 手机号
     */
    @ExcelField(value = "mobile", align = 1, title = "手机号", type = 0)

    private String  mobile;

    /**
     * 住址
     */
    @ExcelField(value = "address", align = 1, title = "地址", type = 0)
    private String  address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
