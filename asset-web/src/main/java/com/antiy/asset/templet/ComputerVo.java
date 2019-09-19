package com.antiy.asset.templet;

import com.antiy.asset.entity.*;

import java.util.List;

/**
 * @Filename: ComputerVo
 * Description:
 * @Version: 1.0
 * @Author: why
 * @Date: 2019/3/20
 */
public class ComputerVo {
    private Asset asset;
    private List<AssetHardDisk> assetHardDisks;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }



    public List<AssetHardDisk> getAssetHardDisks() {
        return assetHardDisks;
    }

    public void setAssetHardDisks(List<AssetHardDisk> assetHardDisks) {
        this.assetHardDisks = assetHardDisks;
    }
}
