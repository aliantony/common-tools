package com.antiy.asset.vo.response;

import java.util.List;

/**
 * <p> AssetGroupDetailResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetGroupDetailResponse {
    private String              assetGroupName;
    private String              assetGroupMemo;
    private List<AssetResponse> assetResponseList;

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getAssetGroupMemo() {
        return assetGroupMemo;
    }

    public void setAssetGroupMemo(String assetGroupMemo) {
        this.assetGroupMemo = assetGroupMemo;
    }

    public List<AssetResponse> getAssetResponseList() {
        return assetResponseList;
    }

    public void setAssetResponseList(List<AssetResponse> assetResponseList) {
        this.assetResponseList = assetResponseList;
    }

    @Override
    public String toString() {
        return "AssetGroupDetailResponse{" +
                "assetGroupName='" + assetGroupName + '\'' +
                ", assetGroupMemo='" + assetGroupMemo + '\'' +
                ", assetResponseList=" + assetResponseList +
                '}';
    }
}