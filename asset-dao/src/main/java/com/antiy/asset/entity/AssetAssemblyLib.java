package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>组件表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetAssemblyLib extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务主键
     */
    private Long              businessId;
    /**
     * 组件类型
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
     * 系统版本
     */
    private String            sysVersion;
    /**
     * 语言
     */
    private String            language;
    /**
     * 其他
     */
    private String            other;
    /**
     * 备注
     */
    private String            memo;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 修改时间
     */
    private Long              gmtModified;
    /**
     * 创建人
     */
    private String            createUser;
    /**
     * 修改人
     */
    private String            modifiedUser;
    /**
     * 是否入库：1已入库、2未入库
     */
    private Integer           isStorage;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getIsStorage() {
        return isStorage;
    }

    public void setIsStorage(Integer isStorage) {
        this.isStorage = isStorage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetAssemblyLib{" + ", businessId=" + businessId + ", type=" + type + ", supplier=" + supplier
               + ", productName=" + productName + ", version=" + version + ", sysVersion=" + sysVersion + ", language="
               + language + ", other=" + other + ", memo=" + memo + ", gmtCreate=" + gmtCreate + ", gmtModified="
               + gmtModified + ", createUser=" + createUser + ", modifiedUser=" + modifiedUser + ", isStorage="
               + isStorage + ", status=" + status + "}";
    }
}