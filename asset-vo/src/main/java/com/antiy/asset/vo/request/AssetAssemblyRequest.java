package com.antiy.asset.vo.request;

import java.io.Serializable;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCpuRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyRequest extends BasicRequest implements ObjectValidator, Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String  id;
    @ApiModelProperty("组件名称")
    private String  productName;
    @ApiModelProperty("组件类型")
    private String  type;
    @ApiModelProperty("厂商")
    private String  supplier;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  assetId;
    /**
     * 组件主键
     */
    @ApiModelProperty("组件主键")
    private String  businessId;
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

    /**
     * 拆除 1 未 2 已
     */
    @ApiModelProperty("拆除 1 未  2 已")
    private Integer remove;

    public Integer getScrap() {
        return scrap;
    }

    public void setScrap(Integer scrap) {
        this.scrap = scrap;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "AssetAssemblyRequest{" + "id='" + id + '\'' + ", productName='" + productName + '\'' + ", type='" + type
               + '\'' + ", supplier='" + supplier + '\'' + ", assetId='" + assetId + '\'' + ", businessId='"
               + businessId + '\'' + '}';
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}