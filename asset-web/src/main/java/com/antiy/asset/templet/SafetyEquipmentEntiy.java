package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class SafetyEquipmentEntiy {
    /**
     * 序号
     */
    @ExcelField(value = "number", align = 1, title = "编号", type = 1)
    private String number;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称", type = 0)
    private String name;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商", type = 0)
    private String manufacturer;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号", type = 0)
    private String serial;
    /**
     * 所属区域
     */
    @ExcelField(value = "area", align = 1, title = "所属区域", type = 1)
    private String area;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者",type = 1)
    private String user;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话",type = 1)
    private String telephone;
    /**
     * 使用者邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱",type = 1)
    private String email;
    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String location;
    /**
     * 机房位置
     */
    @ExcelField(value = "house_location", align = 1, title = "机房位置")
    private String houseLocation;
    /**
     * 固件版本
     */
    @ExcelField(value = "firmware_version", align = 1, title = "固件版本")
    private String firmwareVersion;
    /**
     * 软件版本
     */
    @ExcelField(value = "software_version", align = 1, title = "软件版本")
    private String softwareVersion;

    /**
     * ip地址
     */
    @ExcelField(value = "ip", align = 1, title = "ip地址")
    private String ip;

    /**
     * 购买日期
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买日期",isDate = true)
    private Long            buyDate;
    /**
     * 到期时间
     */
    @ExcelField(value = "due_date", align = 1, title = "到期时间",isDate = true)
    private Long            dueDate;
    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期",isDate = true)
    private Long            warranty;
    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述")
    private String            memo;

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
