package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>安装记录表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetInstallHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Integer           assetId;
    /**
     * 安装包ID
     */
    private Integer           packageId;
    /**
     * 类型：1、升级包，2、特征库
     */
    private Integer           type;
    /**
     * 升级方式，MANUAL(人工），AUTO_MATIC（自动)
     */
    private String            upgradeType;
    /**
     * 升级状态:SUCCESS(成功），FAIL(失败)
     */
    private String            upgradeStatus;
    /**
     * 版本
     */
    private String            version;
    /**
     * 升级时间
     */
    private Long              upgradeDate;
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
     * 状态,1 未删除,0已删除
     */
    private Integer           status;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(String upgradeType) {
        this.upgradeType = upgradeType;
    }

    public String getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(String upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getUpgradeDate() {
        return upgradeDate;
    }

    public void setUpgradeDate(Long upgradeDate) {
        this.upgradeDate = upgradeDate;
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
        return "AssetInstallHistory{" + ", assetId=" + assetId + ", packageId=" + packageId + ", type=" + type
               + ", upgradeType=" + upgradeType + ", upgradeStatus=" + upgradeStatus + ", version=" + version
               + ", upgradeDate=" + upgradeDate + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
               + ", memo=" + memo + ", createUser=" + createUser + ", modifyUser=" + modifyUser + ", status=" + status
               + "}";
    }
}