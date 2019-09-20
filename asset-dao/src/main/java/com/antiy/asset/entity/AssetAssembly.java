package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

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
    private Integer           assetId;
    /**
     * 组件数量
     */
    private Integer           amount;
    /**
     * 组件主键
     */
    private Integer           businessId;
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
     * 状态：1-未删除,0-已删除
     */
    private Integer           status;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
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
        return "AssetAssembly{" + "assetId=" + assetId + ", amount=" + amount + ", businessId=" + businessId
               + ", type='" + type + '\'' + ", productName='" + productName + '\'' + ", supplier='" + supplier + '\''
               + ", status=" + status + '}';
    }
}