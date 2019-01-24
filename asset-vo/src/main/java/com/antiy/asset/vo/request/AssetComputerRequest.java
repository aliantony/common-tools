package com.antiy.asset.vo.request;

import java.util.List;

import javax.validation.Valid;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetRequest 请求对象 </p>
 *
 * @author why
 * @since 2018-12-27
 */

public class AssetComputerRequest extends BasicRequest implements ObjectValidator {
    @Valid
    @ApiModelProperty(value = "资产信息")
    private AssetRequest                            asset;
    @Valid
    @ApiModelProperty(value = "主板")
    private List<AssetMainboradRequest>             mainboard;
    @Valid
    @ApiModelProperty(value = "内存")
    private List<AssetMemoryRequest>                memory;
    @Valid
    @ApiModelProperty(value = "硬盘")
    private List<AssetHardDiskRequest>              hardDisk;
    @Valid
    @ApiModelProperty(value = "CPU")
    private List<AssetCpuRequest>                   cpu;
    @Valid
    @ApiModelProperty(value = "网卡")
    private List<AssetNetworkCardRequest>           networkCard;
    @Valid
    @ApiModelProperty(value = "关联软件信息")
    private List<AssetSoftwaComputerRelationReques> computerReques;

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

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public List<AssetSoftwaComputerRelationReques> getComputerReques() {
        return computerReques;
    }

    public void setComputerReques(List<AssetSoftwaComputerRelationReques> computerReques) {
        this.computerReques = computerReques;
    }

    @Override
    public String toString() {
        return "AssetComputerRequest{" +
                "asset=" + asset +
                ", mainboard=" + mainboard +
                ", memory=" + memory +
                ", hardDisk=" + hardDisk +
                ", cpu=" + cpu +
                ", networkCard=" + networkCard +
                ", computerReques=" + computerReques +
                '}';
    }
}
