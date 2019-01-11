package com.antiy.asset.vo.response;

import java.util.List;

public class AssetOuterResponse {

    private static final long serialVersionUID = 1L;

    private AssetResponse asset;

    private List<AssetCpuResponse> assetCpu;

    private List<AssetNetworkCardResponse> assetNetworkCard;

    private List<AssetMemoryResponse> assetMemory;

    private List<AssetMainboradResponse> assetMainborad;

    private List<AssetHardDiskResponse> assetHardDisk;

    public AssetResponse getAsset() {
        return asset;
    }

    public void setAsset(AssetResponse asset) {
        this.asset = asset;
    }

    public List<AssetCpuResponse> getAssetCpu() {
        return assetCpu;
    }

    public void setAssetCpu(List<AssetCpuResponse> assetCpu) {
        this.assetCpu = assetCpu;
    }

    public List<AssetNetworkCardResponse> getAssetNetworkCard() {
        return assetNetworkCard;
    }

    public void setAssetNetworkCard(List<AssetNetworkCardResponse> assetNetworkCard) {
        this.assetNetworkCard = assetNetworkCard;
    }

    public List<AssetMemoryResponse> getAssetMemory() {
        return assetMemory;
    }

    public void setAssetMemory(List<AssetMemoryResponse> assetMemory) {
        this.assetMemory = assetMemory;
    }

    public List<AssetMainboradResponse> getAssetMainborad() {
        return assetMainborad;
    }

    public void setAssetMainborad(List<AssetMainboradResponse> assetMainborad) {
        this.assetMainborad = assetMainborad;
    }

    public List<AssetHardDiskResponse> getAssetHardDisk() {
        return assetHardDisk;
    }

    public void setAssetHardDisk(List<AssetHardDiskResponse> assetHardDisk) {
        this.assetHardDisk = assetHardDisk;
    }
}
