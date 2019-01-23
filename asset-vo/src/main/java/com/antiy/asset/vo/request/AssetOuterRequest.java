package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.List;

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
    @Valid()
    private AssetRequest                       asset;

    /**
     * 主板
     */
    private List<AssetMainboradRequest>        mainboard;

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
    @ApiModelProperty(value = "流程数据")
    ManualStartActivityRequest activityRequest;

    public ManualStartActivityRequest getActivityRequest() {
        return activityRequest;
    }

    public void setActivityRequest(ManualStartActivityRequest activityRequest) {
        this.activityRequest = activityRequest;
    }

    public AssetRequest getAsset() {
        return asset;
    }

    public void setAsset(AssetRequest asset) {
        this.asset = asset;
    }

    public List<AssetMainboradRequest> getMainboard() {
        return mainboard;
    }

    public void setMainboard(List<AssetMainboradRequest> mainboard) {
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
