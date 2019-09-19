package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>CPE表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetHardSoftLib extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务主键
     */
    private Long              businessId;
    /**
     * 编号
     */
    private Integer           number;
    /**
     * 类型：a-应用软件 h-硬件 o-操作系统
     */
    private String            type;
    /**
     * 供应商
     */
    private String            supplier;
    /**
     * 产品名
     */
    private String            productName;
    /**
     * 版本号
     */
    private String            version;
    /**
     * 更新信息
     */
    private String            upgradeMsg;
    /**
     * 系统版本
     */
    private String            sysVersion;
    /**
     * 语言
     */
    private String            language;
    /**
     * 软件版本
     */
    private String            softVersion;
    /**
     * 软件平台
     */
    private String            softPlatform;
    /**
     * 硬件平台
     */
    private String            hardPlatform;
    /**
     * 其他
     */
    private String            other;
    /**
     * 数据来源：1-CPE，2-MANUAL
     */
    private Integer           dataSource;
    /**
     * 是否入库：1-已入库、2-未入库
     */
    private Integer           isStorage;
    /**
     * cpe路径
     */
    private String            cpeUri;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;
    /**
     * 状态：1-正常，0-删除
     */
    private Integer           status;

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpgradeMsg() {
        return upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getSoftPlatform() {
        return softPlatform;
    }

    public void setSoftPlatform(String softPlatform) {
        this.softPlatform = softPlatform;
    }

    public String getHardPlatform() {
        return hardPlatform;
    }

    public void setHardPlatform(String hardPlatform) {
        this.hardPlatform = hardPlatform;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public String getCpeUri() {
        return cpeUri;
    }

    public void setCpeUri(String cpeUri) {
        this.cpeUri = cpeUri;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetHardSoftLib{" + ", businessId=" + businessId + ", number=" + number + ", type=" + type
               + ", supplier=" + supplier + ", productName=" + productName + ", version=" + version + ", upgradeMsg="
               + upgradeMsg + ", sysVersion=" + sysVersion + ", language=" + language + ", softVersion=" + softVersion
               + ", softPlatform=" + softPlatform + ", hardPlatform=" + hardPlatform + ", other=" + other
               + ", dataSource=" + dataSource + ", isStorage=" + isStorage + ", cpeUri=" + cpeUri + ", memo=" + memo
               + ", gmtModified=" + gmtModified + ", gmtCreate=" + gmtCreate + ", createUser=" + createUser
               + ", modifiedUser=" + modifiedUser + ", status=" + status + "}";
    }
}