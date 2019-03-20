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
    private AssetNetworkCard assetNetworkCard;
    private List<AssetMemory> assetMemoryList;
    private List<AssetHardDisk> assetHardDisks;
    private List<AssetMainborad> assetMainborads;
    private List<AssetCpu> assetCpus;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public AssetNetworkCard getAssetNetworkCard() {
        return assetNetworkCard;
    }

    public void setAssetNetworkCard(AssetNetworkCard assetNetworkCard) {
        this.assetNetworkCard = assetNetworkCard;
    }

    public List<AssetMemory> getAssetMemoryList() {
        return assetMemoryList;
    }

    public void setAssetMemoryList(List<AssetMemory> assetMemoryList) {
        this.assetMemoryList = assetMemoryList;
    }

    public List<AssetHardDisk> getAssetHardDisks() {
        return assetHardDisks;
    }

    public void setAssetHardDisks(List<AssetHardDisk> assetHardDisks) {
        this.assetHardDisks = assetHardDisks;
    }

    public List<AssetMainborad> getAssetMainborads() {
        return assetMainborads;
    }

    public void setAssetMainborads(List<AssetMainborad> assetMainborads) {
        this.assetMainborads = assetMainborads;
    }

    public List<AssetCpu> getAssetCpus() {
        return assetCpus;
    }

    public void setAssetCpus(List<AssetCpu> assetCpus) {
        this.assetCpus = assetCpus;
    }
}
