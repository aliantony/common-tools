package com.antiy.asset.vo.request;

import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;

public class AssetCorrectRequest extends BasicRequest {
    @ApiModelProperty("标志")
    private AssetFlowEnum assetFlowEnum;
    @ApiModelProperty("流程参数")
    @Valid
    private ActivityHandleRequest activityHandleRequest;

    public AssetFlowEnum getAssetFlowEnum() {
        return assetFlowEnum;
    }

    public void setAssetFlowEnum(AssetFlowEnum assetFlowEnum) {
        this.assetFlowEnum = assetFlowEnum;
    }

    public ActivityHandleRequest getActivityHandleRequest() {
        return activityHandleRequest;
    }

    public void setActivityHandleRequest(ActivityHandleRequest activityHandleRequest) {
        this.activityHandleRequest = activityHandleRequest;
    }
}
