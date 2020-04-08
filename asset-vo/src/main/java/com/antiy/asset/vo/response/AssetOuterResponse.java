package com.antiy.asset.vo.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class AssetOuterResponse {

    /**
     * 资产主表
     */
    @ApiModelProperty("资产主表")
    private AssetResponse                 asset;

    /**
     * 网络设备
     */
    @ApiModelProperty("网络设备")
    private AssetNetworkEquipmentResponse assetNetworkEquipment;

    /**
     * 存储介质
     */
    @ApiModelProperty("存储介质")
    private AssetStorageMediumResponse    assetStorageMedium;

    public AssetResponse getAsset() {
        return asset;
    }

    public void setAsset(AssetResponse asset) {
        this.asset = asset;
    }

    public AssetNetworkEquipmentResponse getAssetNetworkEquipment() {
        return assetNetworkEquipment;
    }

    public void setAssetNetworkEquipment(AssetNetworkEquipmentResponse assetNetworkEquipment) {
        this.assetNetworkEquipment = assetNetworkEquipment;
    }

    public AssetStorageMediumResponse getAssetStorageMedium() {
        return assetStorageMedium;
    }

    public void setAssetStorageMedium(AssetStorageMediumResponse assetStorageMedium) {
        this.assetStorageMedium = assetStorageMedium;
    }

}
