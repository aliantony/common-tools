package com.antiy.asset.entity;

import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModelProperty;

public class AssetSoftwareInstall {
    /**
     * 主键
     */

    private String  id;
    /**
     * 资产ID
     */

    private String  assetId;
    /**
     * 软件ID
     */

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
     * 配置方式
     */
    @ApiModelProperty("配置方式")
    private String  configureStatus;
    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    private Long installTime;
    /**
     * 责任人主键
     */
    @ApiModelProperty("责任人主键")
    @Encode
    private String userId;
    /**
     * 责任人
     */
    @ApiModelProperty("责任人")
    private String  userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Long installTime) {
        this.installTime = installTime;
    }

    public String getConfigureStatus() {
        return configureStatus;
    }

    public void setConfigureStatus(String configureStatus) {
        this.configureStatus = configureStatus;
    }

    @Override
    public String toString() {
        return "AssetSoftwareInstall{" +
                "id='" + id + '\'' +
                ", assetId='" + assetId + '\'' +
                ", softwareId='" + softwareId + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", serial='" + serial + '\'' +
                ", categoryModelName='" + categoryModelName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", installType=" + installType +
                ", installStatus=" + installStatus +
                ", configureStatus='" + configureStatus + '\'' +
                ", installTime=" + installTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
