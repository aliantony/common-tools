package com.antiy.asset.vo.request;

import com.antiy.asset.entity.*;
import com.antiy.common.base.BaseEntity;

import java.util.List;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author why
 * @since 2018-12-27
 */

public class AssetPCRequest extends BaseEntity {

    private static final long      serialVersionUID = 1L;

    private Asset                  asset;

    private List<AssetMainborad>   mainboard;

    private List<AssetMemory>      memory;

    private List<AssetHardDisk>    hardDisk;

    private List<AssetCpu>         cpu;

    private List<AssetNetworkCard> networkCard;

    private List<AssetSoftware>    software;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public List<AssetMemory> getMemory() {
        return memory;
    }

    public void setMemory(List<AssetMemory> memory) {
        this.memory = memory;
    }

    public List<AssetHardDisk> getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(List<AssetHardDisk> hardDisk) {
        this.hardDisk = hardDisk;
    }

    public List<AssetCpu> getCpu() {
        return cpu;
    }

    public void setCpu(List<AssetCpu> cpu) {
        this.cpu = cpu;
    }

    public List<AssetNetworkCard> getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(List<AssetNetworkCard> networkCard) {
        this.networkCard = networkCard;
    }

    public List<AssetSoftware> getSoftware() {
        return software;
    }

    public void setSoftware(List<AssetSoftware> software) {
        this.software = software;
    }

    public List<AssetMainborad> getMainboard() {
        return mainboard;
    }

    public void setMainboard(List<AssetMainborad> mainboard) {
        this.mainboard = mainboard;
    }
}
