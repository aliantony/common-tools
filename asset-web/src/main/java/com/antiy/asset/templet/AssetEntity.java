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
    private String categoryModelName;
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
    private String assetGroup;

    @ApiModelProperty(value = "重要程度")
    private String importanceDegree;

    /**
     * 使用者名称
     */
    @ApiModelProperty("使用者")
    private String responsibleUserName;

    @ApiModelProperty("上报方式")
    private String assetSource;

    /**
     * 操作系统名
     */
    @ApiModelProperty("操作系统名")
    private String operationSystemName;

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
     * 首次入网时间
     */
    @ApiModelProperty(value = "首次入网时间")
    private String firstEnterNett;

    /**
     * 使用到期时间
     */
    @ApiModelProperty("到期时间")
    private String serviceLife;

    public String getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(String importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }

    public String getOperationSystemName() {
        return operationSystemName;
    }

    public void setOperationSystemName(String operationSystemName) {
        this.operationSystemName = operationSystemName;
    }

    public String getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(String firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(String serviceLife) {
        this.serviceLife = serviceLife;
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

    public String getCategoryModelName() {
        return categoryModelName;
    }

    public void setCategoryModelName(String categoryModelName) {
        this.categoryModelName = categoryModelName;
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

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
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
}
