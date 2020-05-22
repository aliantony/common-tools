package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.common.base.BaseRequest;

import java.util.List;

public class AssetStatusJudgeRequest extends BaseRequest {

    private List<String> assetIds;

    private AssetFlowEnum assetFlowEnum;

    public List<String> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
        this.assetIds = assetIds;
    }

    public AssetFlowEnum getAssetFlowEnum() {
        return assetFlowEnum;
    }

    public void setAssetFlowEnum(AssetFlowEnum assetFlowEnum) {
        this.assetFlowEnum = assetFlowEnum;
    }
}
