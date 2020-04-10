package com.antiy.asset.vo.response;

import com.antiy.common.base.BaseResponse;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetAssemblyResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyResponse extends BaseResponse {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产组件")
    @Encode
    private String  assetId;
    /**
     * 组件数量
     */
    @ApiModelProperty("组件数量")

    private Integer amount;
    /**
     * 组件主键
     */
    @ApiModelProperty("组件主键")
    private String  businessId;
    /**
     * 组件类型
     */
    @ApiModelProperty("组件类型")
    private String  type;
    /**
     * 产品名
     */
    @ApiModelProperty("产品名")
    private String  productName;
    /**
     * 供应商
     */
    @ApiModelProperty("供应商")
    private String  supplier;
    /**
     * 拆除 1 未 2 已
     */
    @ApiModelProperty("产品名")

    private Integer remove;
    /**
     * 消磁 1 未 2 已
     */
    @ApiModelProperty("消磁 1  未  2 已")
    private Integer demagnetization;
    /**
     * 粉碎 1 未粉碎 2 已粉碎
     */
    @ApiModelProperty("粉碎  1 未粉碎 2 已粉碎")
    private Integer smash;
    /**
     * 唯一键
     */
    @ApiModelProperty("唯一键")
    private Long    uniqueId;
    /**
     * 1 未报废 2 已报废
     */
    @ApiModelProperty("1 未报废 2 已报废")
    private Integer scrap;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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
        return "AssetAssemblyResponse{" + "assetId=" + assetId + ", amount=" + amount + ", businessId=" + businessId
               + ", type='" + type + '\'' + ", productName='" + productName + '\'' + ", supplier='" + supplier + '\''
               + '}';
    }
}