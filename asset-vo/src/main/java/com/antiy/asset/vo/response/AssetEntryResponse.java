package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
@ApiModel(value = "准入管理资产响应实体")
public class AssetEntryResponse extends BaseResponse{

    @ApiModelProperty("资产名称")
    private String name;
    @ApiModelProperty("资产组名称")
    private String assetGroupName;
    @ApiModelProperty("资产编号")
    private String number;
    @ApiModelProperty("ip地址")
    private String ip;
    @ApiModelProperty("mac地址")
    private String mac;
    @ApiModelProperty("准入状态：1已允许，2已禁止")
    private int entryStatus;
    @ApiModelProperty("是否可以准入操作：true可以，false不可以")
    private boolean isEntryOperation;
    @ApiModelProperty("资产状态")
    private Integer assetStatus;


    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(int entryStatus) {
        this.entryStatus = entryStatus;
    }

    public boolean isEntryOperation() {
        return isEntryOperation;
    }

    public void setEntryOperation(boolean entryOperation) {
        isEntryOperation = entryOperation;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }
}
