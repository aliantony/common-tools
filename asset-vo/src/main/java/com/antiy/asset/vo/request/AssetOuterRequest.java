package com.antiy.asset.vo.request;

import java.util.List;

import com.antiy.common.base.BasicRequest;

import javax.validation.Valid;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOuterRequest extends BasicRequest {

    /**
     * 资产主表信息
     */
    private AssetRequest                       asset;

    /**
     * 主板
     */
    private AssetMainboradRequest              mainboard;

    /**
     * 内存
     */
    private List<AssetMemoryRequest>           memory;

    /**
     * 硬盘
     */
    private List<AssetHardDiskRequest>         hardDisk;

    /**
     * cpu
     */
    private List<AssetCpuRequest>              cpu;
    /**
     * 网卡
     */
    private List<AssetNetworkCardRequest>      networkCard;
    /**
     * 网络设备
     */
    private AssetNetworkEquipmentRequest       networkEquipment;
    /**
     * 安全设备
     */
    private AssetSafetyEquipmentRequest        safetyEquipment;
    /**
     * 存储介质
     */
    private AssetStorageMediumRequest          assetStorageMedium;
    /**
     * 资产软件关系表
     */
    private List<AssetSoftwareRelationRequest> assetSoftwareRelationList;

    public AssetRequest getAsset() {
        return asset;
    }

    public void setAsset(AssetRequest asset) {
        this.asset = asset;
    }

    public AssetMainboradRequest getMainboard() {
        return mainboard;
    }

    public void setMainboard(AssetMainboradRequest mainboard) {
        this.mainboard = mainboard;
    }

    public List<AssetMemoryRequest> getMemory() {
        return memory;
    }

    public void setMemory(List<AssetMemoryRequest> memory) {
        this.memory = memory;
    }

    public List<AssetHardDiskRequest> getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(List<AssetHardDiskRequest> hardDisk) {
        this.hardDisk = hardDisk;
    }

    public List<AssetCpuRequest> getCpu() {
        return cpu;
    }

    public void setCpu(List<AssetCpuRequest> cpu) {
        this.cpu = cpu;
    }

    public List<AssetNetworkCardRequest> getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(List<AssetNetworkCardRequest> networkCard) {
        this.networkCard = networkCard;
    }

    public AssetNetworkEquipmentRequest getNetworkEquipment() {
        return networkEquipment;
    }

    public void setNetworkEquipment(AssetNetworkEquipmentRequest networkEquipment) {
        this.networkEquipment = networkEquipment;
    }

    public AssetSafetyEquipmentRequest getSafetyEquipment() {
        return safetyEquipment;
    }

    public void setSafetyEquipment(AssetSafetyEquipmentRequest safetyEquipment) {
        this.safetyEquipment = safetyEquipment;
    }

    public AssetStorageMediumRequest getAssetStorageMedium() {
        return assetStorageMedium;
    }

    public void setAssetStorageMedium(AssetStorageMediumRequest assetStorageMedium) {
        this.assetStorageMedium = assetStorageMedium;
    }

    public List<AssetSoftwareRelationRequest> getAssetSoftwareRelationList() {
        return assetSoftwareRelationList;
    }

    public void setAssetSoftwareRelationList(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        this.assetSoftwareRelationList = assetSoftwareRelationList;
    }
}
