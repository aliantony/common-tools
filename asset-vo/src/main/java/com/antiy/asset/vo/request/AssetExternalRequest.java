package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.Valid;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * 下发智甲的资产数据
 *
 * @author lvliang
 * @since 2018-12-27
 */

public class AssetExternalRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产主表信息
     */
    @Valid
    @ApiModelProperty(value = "资产主表信息")
    private AssetRequest                  asset;

    /**
     * 主板
     */
    @ApiModelProperty(value = "主板")
    @Valid
    private List<AssetMainboradRequest>   mainboard;

    /**
     * 内存
     */
    @ApiModelProperty(value = "内存")
    @Valid
    private List<AssetMemoryRequest>      memory;

    /**
     * 硬盘
     */
    @ApiModelProperty(value = "硬盘")
    @Valid
    private List<AssetHardDiskRequest>    hardDisk;

    /**
     * cpu
     */
    @ApiModelProperty(value = "cpu")
    @Valid
    private List<AssetCpuRequest>         cpu;
    /**
     * 网卡
     */
    @ApiModelProperty(value = "网卡")
    @Valid
    private List<AssetNetworkCardRequest> networkCard;
    /**
     * 资产上的软件
     */
    private List<AssetSoftwareRequest>    assetSoftwareRequestList;

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

    public List<AssetSoftwareRequest> getAssetSoftwareRequestList() {
        return assetSoftwareRequestList;
    }

    public void setAssetSoftwareRequestList(List<AssetSoftwareRequest> assetSoftwareRequestList) {
        this.assetSoftwareRequestList = assetSoftwareRequestList;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        // ParamterExceptionUtils.isTrue(!(manualStartActivityRequest == null && activityHandleRequest == null),
        // "流程数据不能为空");
    }
}
