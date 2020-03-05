package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>安全厂商表</p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */

public class AssetManufacture extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商名称
     */
    private String            supplier;

    /**
     * 供应商名称
     */
    private String            uniqueId;
    /**
     * 业务主键
     */
    private String            businessId;
    /**
     * CPE名称
     */
    private String            productName;
    /**
     * 资产名称
     */
    private String            name;
    /**
     * 资产编号
     */
    private String            number;
    /**
     * 资产状态
     */
    private String            netStatus;
    /**
     * 资产主键
     */
    private String            assetId;
    /**
     * 版本
     */
    private String            version;
    /**
     * 数量
     */
    private Integer           amount;
    /**
     * 父级业务主键
     */
    private String            pid;
    /**
     * 创建时间
     */
    private Long              gmtCreate;
    /**
     * 更新时间
     */
    private Long              gmtModified;
    /**
     * 创建人
     */
    private Integer           createUser;
    /**
     * 修改人
     */
    private Integer           modifyUser;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getBusinessId() {
        return businessId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    @Override
    public String toString() {
        return "AssetManufacture{" + ", supplier=" + supplier + ", businessId=" + businessId + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + ", createUser=" + createUser + ", modifyUser="
               + modifyUser + "}";
    }
}