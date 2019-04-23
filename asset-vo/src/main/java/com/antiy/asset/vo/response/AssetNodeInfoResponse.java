package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IntelliJ IDEA. User: 钊钊 Date: 2019/4/23 Time: 13:24 Description: No Description
 */
public class AssetNodeInfoResponse extends BaseResponse {

    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String  number;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String  ip;
    /**
     * 操作系统名
     */
    @ApiModelProperty("操作系统名")
    private String  operationSystemName;
    /**
     * 物理位置
     */
    @ApiModelProperty("物理位置")
    private String  location;
    /**
     * 机房位置
     */
    @ApiModelProperty("机房位置")
    private String  houseLocation;
    /**
     * 联系方式
     */
    @ApiModelProperty("联系电话")
    private String  contactTel;
    /**
     * 安装方式1人工2自动
     */
    @ApiModelProperty("安装方式1人工2自动")
    private Integer installType;
    /**
     * 安装方式1人工2自动
     */
    @ApiModelProperty("安装方式1人工2自动")
    private String  installTypeName;
    /**
     * 资产组
     */
    @ApiModelProperty("资产组")
    private String  assetGroup;

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

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public String getInstallTypeName() {
        return installTypeName;
    }

    public void setInstallTypeName(String installTypeName) {
        this.installTypeName = installTypeName;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }
}
