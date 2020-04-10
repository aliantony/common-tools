package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

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
        return "AssetAssemblyRequest{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", type='" + type + '\'' +
                ", supplier='" + supplier + '\'' +
                ", assetId='" + assetId + '\'' +
                ", businessId='" + businessId + '\'' +
                '}';
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}