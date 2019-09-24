package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * <p> AssetAssembly 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyQuery extends ObjectQuery {

    @ApiModelProperty("资产id")
    @Encode
    @NotBlank(message = "资产id不能为空")
    private String assetId;

    @ApiModelProperty("厂商")
    private String            supplier;

    @ApiModelProperty("名称")
    private String            productName;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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
}