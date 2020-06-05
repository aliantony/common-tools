package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.asset.vo.enums.DataTypeEnum;

public class AssetUserEntity {

    /**
     * 姓名
     */
    @ExcelField(value = "name", align = 1, title = "姓名(必填)", type = 0, required = true)
    private String  name;
    /**
     * 部门名
     */
    @ExcelField(value = "departmentName", align = 1, title = "所属组织(必填)", type = 0, required = true, defaultDataMethod = "queryAllDepartment", defaultDataBeanName = "assetTemplateServiceImpl")
    private String departmentName;
    /**
     * 电子邮箱
     */
    @ExcelField(value = "email", align = 1, title = "电子邮箱", type = 0, dataType = DataTypeEnum.EMAIL)
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
    @ExcelField(value = "mobile", align = 1, title = "手机号", type = 0, dataType = DataTypeEnum.TEL)
    private String  mobile;

    /**
     * 住址
     */
    @ExcelField(value = "address", align = 1, title = "住址", type = 0, length = 100, defaultDataMethod = "queryAllArea", defaultDataBeanName = "assetTemplateServiceImpl")
    private String  address;
    /**
     * 详细地址
     */
    @ExcelField(value = "detailAddress", align = 1, title = "详细地址", type = 0, length = 100)
    private String detailAddress;
    /**
     * 职位
     */
    @ExcelField(value = "position", align = 1, title = "职位", type = 0)
    private String position;


    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
