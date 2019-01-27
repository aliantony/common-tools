package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p> 软件许可表 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public class AssetSoftwareLicense extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 许可名称
     */
    private String            name;
    /**
     * 厂商名称
     */
    private String            manufacturer;
    /**
     * 软件主键
     */
    private String           softwareId;
    /**
     * 购买日期
     */
    private Long              buyDate;
    /**
     * 有效期限
     */
    private Long              expiryDate;
    /**
     * 许可密钥
     */
    private String            licenseSecretKey;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer           status;

    /**
     * 软件名称
     */
    private String            softwareName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
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

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
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

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    @Override
    public String toString() {
        return "AssetSoftwareLicense{" +
                "name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", softwareId=" + softwareId +
                ", buyDate=" + buyDate +
                ", expiryDate=" + expiryDate +
                ", licenseSecretKey='" + licenseSecretKey + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", memo='" + memo + '\'' +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", status=" + status +
                ", softwareName='" + softwareName + '\'' +
                '}';
    }
}