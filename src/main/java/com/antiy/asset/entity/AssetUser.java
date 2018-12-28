package com.antiy.asset.entity;


import java.util.Date;
import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
/**
 * <p>
 * 资产用户信息
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */

public class AssetUser extends BaseEntity {


private static final long serialVersionUID = 1L;

        /**
    *  姓名
    */
        @ApiModelProperty("姓名")
    private String name;
        /**
    *  部门主键
    */
        @ApiModelProperty("部门主键")
    private Integer departmentId;
        /**
    *  电子邮箱
    */
        @ApiModelProperty("电子邮箱")
    private String email;
        /**
    *  qq号
    */
        @ApiModelProperty("qq号")
    private String qq;
        /**
    *  微信
    */
        @ApiModelProperty("微信")
    private String weixin;
        /**
    *  手机号
    */
        @ApiModelProperty("手机号")
    private String mobile;
        /**
    *  住址
    */
        @ApiModelProperty("住址")
    private String address;
        /**
    *  创建人
    */
        @ApiModelProperty("创建人")
    private Integer createUser;
        /**
    *  修改人
    */
        @ApiModelProperty("修改人")
    private Integer modifyUser;
        /**
    *  创建时间
    */
        @ApiModelProperty("创建时间")
    private Long gmtCreate;
        /**
    *  更新时间
    */
        @ApiModelProperty("更新时间")
    private Long gmtModified;
        /**
    *  状态,0 未删除,1已删除
    */
        @ApiModelProperty("状态,0 未删除,1已删除")
    private Integer status;

                    
                                    
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
    
                                    
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    
                                    
    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }
    
                                    
    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    
                                    
    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
    
                                    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
        @Override
    public String toString() {
            return "AssetUser{" +
                                                                                            ", name=" + name +
                                                                                        ", departmentId=" + departmentId +
                                                                                        ", email=" + email +
                                                                                        ", qq=" + qq +
                                                                                        ", weixin=" + weixin +
                                                                                        ", mobile=" + mobile +
                                                                                        ", address=" + address +
                                                                                        ", createUser=" + createUser +
                                                                                        ", modifyUser=" + modifyUser +
                                                                                        ", gmtCreate=" + gmtCreate +
                                                                                        ", gmtModified=" + gmtModified +
                                                                                        ", status=" + status +
                                                "}";
    }
    }