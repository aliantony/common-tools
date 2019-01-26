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
    private String assetGroup;
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
     * 使用者
     */
    @ApiModelProperty("使用者")
    private String responsibleUserName;
    /**
     * 重要程度
     */
    @ApiModelProperty("重要程度")
    private String importanceDegree;
    /**
     * 重要程度
     */
    @ApiModelProperty("操作系统")
    private String operationSystem;
    /**
     * 首次入网时间
     */
    @ApiModelProperty("首次入网时间")
    private String firstEnterNett;
    /**
     * 是否准入
     */
    @ApiModelProperty("是否准入")
    private String admittanceStatus;
    /**
     * 使用到期时间
     */
    @ApiModelProperty("使用到期时间")
    private String serviceLife;

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

    public String getResponsibleUserName() {
        return responsibleUserName;
    }

    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }

    public String getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(String importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(String firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getAdmittanceStatus() {
        return admittanceStatus;
    }

    public void setAdmittanceStatus(String admittanceStatus) {
        this.admittanceStatus = admittanceStatus;
    }

    public String getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(String serviceLife) {
        this.serviceLife = serviceLife;
    }
}
