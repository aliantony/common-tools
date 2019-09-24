package com.antiy.asset.templet;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetIpRelation;
import com.antiy.asset.entity.AssetMacRelation;

/**
 * @Filename: ComputerVo Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2019/3/20
 */
public class ComputerVo {
    private Asset            asset;
    private AssetMacRelation assetMacRelation;
    private AssetIpRelation  assetIpRelation;

    public AssetMacRelation getAssetMacRelation() {
        return assetMacRelation;
    }

    public void setAssetMacRelation(AssetMacRelation assetMacRelation) {
        this.assetMacRelation = assetMacRelation;
    }

    public AssetIpRelation getAssetIpRelation() {
        return assetIpRelation;
    }

    public void setAssetIpRelation(AssetIpRelation assetIpRelation) {
        this.assetIpRelation = assetIpRelation;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

}
