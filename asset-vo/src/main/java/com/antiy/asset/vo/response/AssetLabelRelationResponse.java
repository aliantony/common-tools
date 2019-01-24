package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetLabelRelationResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetLabelRelationResponse {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  assetId;
    /**
     * 标签主键
     */
    @ApiModelProperty("标签主键")
    @Encode
    private Integer assetLabelId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getAssetLabelId() {
        return assetLabelId;
    }

    public void setAssetLabelId(Integer assetLabelId) {
        this.assetLabelId = assetLabelId;
    }
}