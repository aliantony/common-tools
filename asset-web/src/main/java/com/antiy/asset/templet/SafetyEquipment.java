package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class SafetyEquipment {
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称", type = 0)
    private String name;
    /**
     * 品类
     */
    @ExcelField(value = "category_model", align = 1, title = "资产品类", type = 0)
    private String categoryModel;

    /**
     * 编号
     */
    @ExcelField(value = "number", align = 1, title = "编号", type = 0)
    private String number;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号", type = 0)
    private String serial;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商", type = 0)
    private String manufacturer;
    /**
     * ip地址
     */
    @ExcelField(value = "ip", align = 1, title = "ip地址", type = 0)
    private String ip;
    /**
     * 固件版本
     */
    @ExcelField(value = "firmware_version", align = 1, title = "固件版本", type = 0)
    private String firmwareVersion;
    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String location;
    /**
     * 所属分组
     */
    @ExcelField(value = "asset_group", align = 1, title = "所属分组")
    private String assetGroup;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String user;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话")
    private String telephone;
    /**
     * 使用者邮箱
     */
    @ExcelField(value = "email", align = 1, title = "使用者邮箱")
    private String email;
    /**
     * 资产来源
     */
    @ExcelField(value = "asset_source", align = 1, title = "资产来源",dictType = "asset_source")
    private String assetSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(String categoryModel) {
        this.categoryModel = categoryModel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }
}
