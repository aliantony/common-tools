package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseRequest;

import java.util.List;

public class AssetNetToCorrectRequest  extends BaseRequest {

    /**
     *
     */
    private List<AssetBusinessRelationRequest> assetInfoList;

    public List<AssetBusinessRelationRequest> getAssetInfoList() {
        return assetInfoList;
    }

    public void setAssetInfoList(List<AssetBusinessRelationRequest> assetInfoList) {
        this.assetInfoList = assetInfoList;
    }
}
