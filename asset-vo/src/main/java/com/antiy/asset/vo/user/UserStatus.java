package com.antiy.asset.vo.user;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program csom
 * @description 用户状态响应
 * @author wangqian created on 2019-01-17
 * @version 1.0.0
 */
public class UserStatus implements Serializable {
    private static final long       serialVersionUID = 4726000698516962946L;

    @ApiModelProperty("用户id")
    private Integer                 id;

    @ApiModelProperty("用户名")
    private String                  username;

    @ApiModelProperty("锁定剩余分钟数")
    private Long                    leftTime;

    @ApiModelProperty("用户当前状态")
    private Integer                 status;

    /** 真实姓名 */
    @ApiModelProperty("真实姓名")
    private String                  name;
    /** 用户职能 */
    @ApiModelProperty("用户职能")
    private String                  duty;
    /** 所在部门 */
    @ApiModelProperty("所在部门")
    private String                  department;
    /** 手机号 */
    @ApiModelProperty("手机号")
    private String                  phone;
    /** 电子邮箱 */
    @ApiModelProperty("电子邮箱")
    private String                  email;

    @ApiModelProperty("角色信息")
    private List<OauthRoleResponse> sysRoles;

    @ApiModelProperty("菜单信息")
    private List<OauthMenuResponse> menus;

    @ApiModelProperty("最后一次登陆时间")
    private Long                    lastLoginTime;

    @ApiModelProperty("是否必须修改密码")
    private Boolean                 mustModifyPassword;

    private String                  stringId;

    @ApiModelProperty("license版本")
    private Integer                 licenseVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Long leftTime) {
        this.leftTime = leftTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OauthRoleResponse> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<OauthRoleResponse> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public List<OauthMenuResponse> getMenus() {
        return menus;
    }

    public void setMenus(List<OauthMenuResponse> menus) {
        this.menus = menus;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean isMustModifyPassword() {
        return mustModifyPassword;
    }

    public void setMustModifyPassword(Boolean mustModifyPassword) {
        this.mustModifyPassword = mustModifyPassword;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public Integer getLicenseVersion() {
        return licenseVersion;
    }

    public void setLicenseVersion(Integer licenseVersion) {
        this.licenseVersion = licenseVersion;
    }
}
