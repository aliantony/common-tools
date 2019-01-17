package com.antiy.asset.templet;

import io.swagger.annotations.ApiModelProperty;

public class AssetEntity {

    /**
     * 资产名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 资产型号
     */
    @ApiModelProperty("编号")
    private String number;
    /**
     * 品类型号
     */
    @ApiModelProperty("品类型号")
    private String categoryModel;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String manufacturer;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ip;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    private String mac;
    /**
     * mac
     */
    @ApiModelProperty("资产组")
    private String assetGroups;
    /**
     * mac
     */
    @ApiModelProperty("状态")
    private String assetStatus;
    /**
     * 首次发现时间
     */
    @ApiModelProperty("首次发现时间")
    private String gmtCreate;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String serial;
    /**
     * 资产来源
     */
    @ApiModelProperty("资产来源")
    private String assetSource;
    /**
     * 是否入网
     */
    @ApiModelProperty("是否入网")
    private String isInnet;

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

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getAssetGroups() {
        return assetGroups;
    }

    public void setAssetGroups(String assetGroups) {
        this.assetGroups = assetGroups;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }

    public String getIsInnet() {
        return isInnet;
    }

    public void setIsInnet(String isInnet) {
        this.isInnet = isInnet;
    }
}
