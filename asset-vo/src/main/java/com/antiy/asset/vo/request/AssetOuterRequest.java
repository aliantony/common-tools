package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.Valid;

import com.antiy.common.base.BasicRequest;

import io.swagger.annotations.ApiModelProperty;

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
    @Valid
    @ApiModelProperty(value = "资产主表信息")
    private AssetRequest                       asset;

    /**
     * 主板
     */
    @ApiModelProperty(value = "主板")
    @Valid
    private List<AssetMainboradRequest>        mainboard;

    /**
     * 内存
     */
    @ApiModelProperty(value = "内存")
    @Valid
    private List<AssetMemoryRequest>           memory;

    /**
     * 硬盘
     */
    @ApiModelProperty(value = "硬盘")
    @Valid
    private List<AssetHardDiskRequest>         hardDisk;

    /**
     * cpu
     */
    @ApiModelProperty(value = "cpu")
    @Valid
    private List<AssetCpuRequest>              cpu;
    /**
     * 网卡
     */
    @ApiModelProperty(value = "网卡")
    @Valid
    private List<AssetNetworkCardRequest>      networkCard;
    /**
     * 网络设备
     */
    @ApiModelProperty(value = "网络设备")
    @Valid
    private AssetNetworkEquipmentRequest       networkEquipment;
    /**
     * 安全设备
     */
    @ApiModelProperty(value = "安全设备")
    @Valid
    private AssetSafetyEquipmentRequest        safetyEquipment;
    /**
     * 存储介质
     */
    @ApiModelProperty(value = "存储介质")
    @Valid
    private AssetStorageMediumRequest          assetStorageMedium;
    /**
     * 资产软件关系表
     */
    @ApiModelProperty(value = "资产软件关系表")
    @Valid
    private List<AssetSoftwareRelationRequest> assetSoftwareRelationList;

    @ApiModelProperty(value = "流程数据")
    @Valid
    ManualStartActivityRequest                 activityRequest;

    /**
     * 存储介质
     */
    @ApiModelProperty(value = "其他设备")
    @Valid
    private AssetOthersRequest                 assetOthersRequest;

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

    public AssetOthersRequest getAssetOthersRequest() {
        return assetOthersRequest;
    }

    public void setAssetOthersRequest(AssetOthersRequest assetOthersRequest) {
        this.assetOthersRequest = assetOthersRequest;
    }
}
