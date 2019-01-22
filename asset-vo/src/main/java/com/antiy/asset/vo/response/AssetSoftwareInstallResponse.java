package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

public class AssetSoftwareInstallResponse {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String  id;
    /**
     * 资产ID
     */
    @ApiModelProperty("资产ID")
    @Encode
    private String  assetId;
    /**
     * 软件ID
     */
    @ApiModelProperty("软件ID")
    @Encode
    private String  softwareId;
    /**
     * 资产编号
     */
    @ApiModelProperty("资产编号")
    private String  number;
    /**
     * 资产名称
     */
    @ApiModelProperty("资产名称")
    private String  name;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    private Integer userId;
    /**
     * 责任人
     */
    @ApiModelProperty("责任人")
    private String  userName;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String  ip;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    private String  mac;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String  serial;

    /**
     * 品类名称
     */
    @ApiModelProperty("品类名称")
    private String  categoryModelName;
    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    private String  manufacturer;

    /**
     * 安装类型
     */
    @ApiModelProperty("安装类型")
    private Integer installType;
    /**
     * 安装状态
     */
    @ApiModelProperty("安装状态")
    private Integer installStatus;

    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    private Integer installTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public Integer getInstallType() {
        return installType;
    }

    public void setInstallType(Integer installType) {
        this.installType = installType;
    }

    public Integer getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Integer installStatus) {
        this.installStatus = installStatus;
    }

    public Integer getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Integer installTime) {
        this.installTime = installTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }
}
