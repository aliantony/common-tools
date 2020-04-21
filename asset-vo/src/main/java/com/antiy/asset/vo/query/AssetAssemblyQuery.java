package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetAssembly 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetAssemblyQuery extends ObjectQuery {
    @ApiModelProperty("组件类型")
    private String type;
    @ApiModelProperty("厂商")
    private String supplier;
    @ApiModelProperty("名称")
    private String productName;
    @ApiModelProperty("资产id")
    @Encode
    private String assetId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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