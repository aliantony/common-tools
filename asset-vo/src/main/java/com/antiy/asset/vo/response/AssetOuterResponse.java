package com.antiy.asset.vo.response;

import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AssetOuterResponse {

    /**
     * 资产主表
     */
    @ApiModelProperty("资产主表")
    private AssetResponse                  asset;
    /**
     * CPU
     */
    @ApiModelProperty("CPU")
    private List<AssetCpuResponse>         assetCpu;
    /**
     * 网卡
     */
    @ApiModelProperty("网卡")
    private List<AssetNetworkCardResponse> assetNetworkCard;
    /**
     * 内存
     */
    @ApiModelProperty("内存")
    private List<AssetMemoryResponse>      assetMemory;
    /**
     * 主板
     */
    @ApiModelProperty("主板")
    private List<AssetMainboradResponse>   assetMainborad;
    /**
     * 硬盘
     */
    @ApiModelProperty("硬盘")
    private List<AssetHardDiskResponse>    assetHardDisk;
    /**
     * 网络设备
     */
    @ApiModelProperty("网络设备")
    private AssetNetworkEquipmentResponse  assetNetworkEquipment;
    /**
     * 安全设备
     */
    @ApiModelProperty("安全设备")
    private AssetSafetyEquipmentResponse   assetSafetyEquipment;
    /**
     * 存储介质
     */
    @ApiModelProperty("存储介质")
    private AssetStorageMediumResponse      assetStorageMedium;
    /**
     * 软件信息
     */
    @ApiModelProperty("软件信息")
    private List<AssetSoftwareResponse>    assetSoftware;
    /**
     * 资产软件关系
     */
    @ApiModelProperty("资产软件关系")
    private List<AssetSoftwareRelationResponse> assetSoftwareRelationList;

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

    public List<AssetSoftwareResponse> getAssetSoftware() {
        return assetSoftware;
    }

    public void setAssetSoftware(List<AssetSoftwareResponse> assetSoftware) {
        this.assetSoftware = assetSoftware;
    }

    public AssetNetworkEquipmentResponse getAssetNetworkEquipment() {
        return assetNetworkEquipment;
    }

    public void setAssetNetworkEquipment(AssetNetworkEquipmentResponse assetNetworkEquipment) {
        this.assetNetworkEquipment = assetNetworkEquipment;
    }

    public AssetSafetyEquipmentResponse getAssetSafetyEquipment() {
        return assetSafetyEquipment;
    }

    public void setAssetSafetyEquipment(AssetSafetyEquipmentResponse assetSafetyEquipment) {
        this.assetSafetyEquipment = assetSafetyEquipment;
    }

    public AssetStorageMediumResponse getAssetStorageMedium() {
        return assetStorageMedium;
    }

    public void setAssetStorageMedium(AssetStorageMediumResponse assetStorageMedium) {
        this.assetStorageMedium = assetStorageMedium;
    }

    public List<AssetSoftwareRelationResponse> getAssetSoftwareRelationList() {
        return assetSoftwareRelationList;
    }

    public void setAssetSoftwareRelationList(List<AssetSoftwareRelationResponse> assetSoftwareRelationList) {
        this.assetSoftwareRelationList = assetSoftwareRelationList;
    }
}
