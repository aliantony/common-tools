package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import lombok.Data;

/**
 * 计算设备
 */
@Data
public class ComputeDeviceEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号(必填)", required = true)
    private String number;

    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称(必填)", required = true)
    private String name;

    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商", length = 80)
    private String manufacturer;

    /**
     * 版本
     */
    @ExcelField(value = "version", align = 1, title = "版本")
    private String version;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String serial;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者(必填)", required = true, defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String user;
    /**
     * 操作系统
     */
    @ExcelField(value = "operationSystem", align = 1, title = "操作系统(必填)", required = true, defaultDataMethod = "getAllSystemOs", defaultDataBeanName = "assetTemplateServiceImpl")
    private String operationSystem;
    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域(必填)", required = true, defaultDataMethod = "queryAllArea", defaultDataBeanName = "assetTemplateServiceImpl")
    private String area;

    /**
     * 重要程度
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度(必填)", dictType = "major_type", required = true)
    private String importanceDegree;
    /**
     * 机房位置
     */
    @ExcelField(value = "houseLocation", align = 1, title = "机房位置")
    private String houseLocation;

    /**
     * 购买时间
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买时间", isDate = true)
    private Long   buyDate;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "dueTime", align = 1, title = "到期时间(必填)", isDate = true, required = true)
    private Long   dueTime;
    /**
     * 保修
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期")
    private String warranty;

    /**
     * description
     */
    @ExcelField(value = "description", align = 1, title = "描述", length = 300)
    private String description;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(String importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getDueTime() {
        return dueTime;
    }

    public void setDueTime(Long dueTime) {
        this.dueTime = dueTime;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
