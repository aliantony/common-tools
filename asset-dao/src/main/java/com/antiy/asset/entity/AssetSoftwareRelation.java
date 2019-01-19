package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 资产软件关系信息 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetSoftwareRelation extends BaseEntity {

    /**
     * 资产主键
     */
    private Integer assetId;
    /**
     * 软件主键
     */
    private Integer softwareId;
    /**
     * 软件资产状态：1待登记，2不予登记，3待配置，4待验证，5待入网，6已入网，7待退役，8已退役
     */
    private Integer softwareStatus;
    /**
     * 协议
     */
    private String  protocol;
    /**
     * 端口
     */
    private String  port;
    /**
     * 许可密钥
     */
    private String licenseSecretKey;
    /**
     * 安装方式1人工2自动
     */
    private Integer installType;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Integer softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getSoftwareStatus() {
        return softwareStatus;
    }

    public void setSoftwareStatus(Integer softwareStatus) {
        this.softwareStatus = softwareStatus;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public void setPort(String port) {
        this.port = port;
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

    public String getLicenseSecretKey() {
        return licenseSecretKey;
    }

    public void setLicenseSecretKey(String licenseSecretKey) {
        this.licenseSecretKey = licenseSecretKey;
    }

    @Override
    public String toString() {
        return "AssetSoftwareRelation{" + ", assetId=" + assetId + ", softwareId=" + softwareId + ", softwareStatus="
               + softwareStatus + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo
               + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}