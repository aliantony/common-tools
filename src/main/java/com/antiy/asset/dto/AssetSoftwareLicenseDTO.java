package com.antiy.asset.dto;


import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 软件许可表 数据对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetSoftwareLicenseDTO extends BaseEntity {


    private static final long serialVersionUID = 1L;

    /**
     * 软件主键
     */
    @ApiModelProperty("软件主键")
    private Integer softwareId;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long busyDate;
    /**
     * 有效期限
     */
    @ApiModelProperty("有效期限")
    private Long expiryDate;
    /**
     * 许可密钥
     */
    @ApiModelProperty("许可密钥")
    private String licenseSecretKey;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Long gmtModified;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    @ApiModelProperty("状态,1未删除,0已删除")
    private Integer status;


    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }


    public Long getBusyDate() {
        return busyDate;
    }

    public void setBusyDate(Long busyDate) {
        this.busyDate = busyDate;
    }


    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }


    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
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


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "AssetSoftwareLicense{" +
                ", softwareId=" + softwareId +
                ", busyDate=" + busyDate +
                ", expiryDate=" + expiryDate +
                ", licenseSecretKey=" + licenseSecretKey +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo=" + memo +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                "}";
    }
}