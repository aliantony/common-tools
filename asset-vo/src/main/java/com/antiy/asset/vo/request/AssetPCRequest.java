package com.antiy.asset.vo.request;

import com.antiy.common.base.BaseEntity;

import java.util.List;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author why
 * @since 2018-12-27
 */

public class AssetPCRequest extends BaseEntity {

    private AssetRequest                  asset;

    private List<AssetMainboradRequest>   mainboard;

    private List<AssetMemoryRequest>      memory;

    private List<AssetHardDiskRequest>    hardDisk;

    private List<AssetCpuRequest>         cpu;

    private List<AssetNetworkCardRequest> networkCard;

    private List<AssetSoftwaComputerReques> computerReques;

    private String                        softwareids;

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

    public String getSoftwareids() {
        return softwareids;
    }

    public void setSoftwareids(String softwareids) {
        this.softwareids = softwareids;
    }
}
