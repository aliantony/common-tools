package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseEntity;
import com.antiy.common.base.BasicRequest;

import java.util.List;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOuterRequest extends BasicRequest {

    private static final long             serialVersionUID = 1L;

    private AssetRequest                  asset;

    private AssetMainboradRequest         mainboard;

    private List<AssetMemoryRequest>      memory;

    private List<AssetHardDiskRequest>    hardDisk;

    private List<AssetCpuRequest>         cpu;

    private List<AssetNetworkCardRequest> networkCard;

    private List<AssetSoftwareRequest>    software;

    private AssetNetworkEquipmentRequest  networkEquipment;

    private AssetSafetyEquipmentRequest   safetyEquipment;

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

    public List<AssetSoftwareRequest> getSoftware() {
        return software;
    }

    public void setSoftware(List<AssetSoftwareRequest> software) {
        this.software = software;
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
}
