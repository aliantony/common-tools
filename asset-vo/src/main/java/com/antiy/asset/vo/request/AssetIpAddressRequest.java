package com.antiy.asset.vo.request;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author chenhuasheng
 * @description
 * @create 2019/5/23 11:08
 */
public class AssetIpAddressRequest {
    @ApiModelProperty("资产Id")
    @Encode
    private String assetId;
    @ApiModelProperty("是否可用,true表示可用的资产IP,false表示全部IP,默认为true")
    private Boolean enable;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
