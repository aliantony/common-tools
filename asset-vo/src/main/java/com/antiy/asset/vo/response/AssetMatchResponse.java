package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/3/27
 */
@ApiModel(description = "适用于日志告警")
public class AssetMatchResponse extends BaseResponse {
    @ApiModelProperty("ip&mac")
    private String keyId;
    @ApiModelProperty("资产名称")
    private String assetName;
    @ApiModelProperty("资产编号")
    private String assetNumber;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }
}
