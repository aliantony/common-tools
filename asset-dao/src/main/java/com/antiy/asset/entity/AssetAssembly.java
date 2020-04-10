package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

/**
 * <p>资产组件关系表</p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */

public class AssetAssembly extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    private String           assetId;
    /**
     * 组件主键
     */
    private String           businessId;
    /**
     * 组件类型
     */
    private String            type;
    /**
     * 产品名
     */
    private String            productName;
    /**
     * 供应商
     */
    private String            supplier;

    /**
     *  拆除 1 未  2 已
     */
    private Integer remove;
    /**
     *  消磁 1  未  2 已
     */
    private Integer demagnetization;
    /**
     *  粉碎  1 未粉碎 2 已粉碎
     */
    private Integer smash;
    /**
     *  唯一键
     */
    private Long uniqueId;
    /**
     *  1 未报废 2 已报废
     */
    private Integer scrap;
    /**
     * 状态：1-未删除,0-已删除
     */
    private Integer           status;

    public Integer getRemove() {
        return remove;
    }

    public void setRemove(Integer remove) {
        this.remove = remove;
    }

    public Integer getDemagnetization() {
        return demagnetization;
    }

    public void setDemagnetization(Integer demagnetization) {
        this.demagnetization = demagnetization;
    }

    public Integer getSmash() {
        return smash;
    }

    public void setSmash(Integer smash) {
        this.smash = smash;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getScrap() {
        return scrap;
    }

    public void setScrap(Integer scrap) {
        this.scrap = scrap;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "AssetAssembly{" +
                "assetId='" + assetId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", type='" + type + '\'' +
                ", productName='" + productName + '\'' +
                ", supplier='" + supplier + '\'' +
                ", remove=" + remove +
                ", demagnetization=" + demagnetization +
                ", smash=" + smash +
                ", uniqueId=" + uniqueId +
                ", scrap=" + scrap +
                ", status=" + status +
                '}';
    }
}