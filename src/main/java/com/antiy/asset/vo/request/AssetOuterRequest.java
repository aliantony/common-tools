package com.antiy.asset.vo.request;

import com.antiy.asset.entity.*;
import com.antiy.common.base.BaseEntity;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * AssetRequest 请求对象
 * </p>
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetOuterRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Asset asset;

    private AssetMainborad mainboard;

    private List<AssetMemory> memory;

    private List<AssetHardDisk> hardDisk;

    private List<AssetCpu> cpu;

    private List<AssetNetworkCard> networkCard;

    private List<AssetSoftware> software;

    private AssetNetworkEquipment networkEquipment;

    private AssetSafetyEquipment safetyEquipment;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public AssetMainborad getMainboard() {
        return mainboard;
    }

    public void setMainboard(AssetMainborad mainboard) {
        this.mainboard = mainboard;
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

    public AssetNetworkEquipment getNetworkEquipment() {
        return networkEquipment;
    }

    public void setNetworkEquipment(AssetNetworkEquipment networkEquipment) {
        this.networkEquipment = networkEquipment;
    }

    public AssetSafetyEquipment getSafetyEquipment() {
        return safetyEquipment;
    }

    public void setSafetyEquipment(AssetSafetyEquipment safetyEquipment) {
        this.safetyEquipment = safetyEquipment;
    }

    public List<AssetSoftware> getSoftware() {
        return software;
    }

    public void setSoftware(List<AssetSoftware> software) {
        this.software = software;
    }
}
