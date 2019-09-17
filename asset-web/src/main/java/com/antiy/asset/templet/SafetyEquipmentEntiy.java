package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.asset.vo.enums.DataTypeEnum;

public class SafetyEquipmentEntiy {

    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号(必填)",required = true)
    private String                  number;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称(必填)", type = 0,required = true)
    private String name;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商(必填)", length = 80, required = true)
    private String manufacturer;

    /**
     * 版本
     */
    @ExcelField(value = "version", align = 1, title = "版本(必填)", required = true)
    private String version;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者(必填)",required = true, defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String user;

    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域(必填)",required = true, defaultDataMethod = "queryAllArea", defaultDataBeanName = "assetTemplateServiceImpl")
    private String                  area;
    /**
     * 操作系统
     */
    @ExcelField(value = "operationSystem", align = 1, title = "操作系统(必填)", required = true, defaultDataMethod = "getAllSystemOs", defaultDataBeanName = "assetTemplateServiceImpl")
    private String operationSystem;

    /**
     * 重要程度
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度(必填)", dictType = "major_type", required = true)
    private String importanceDegree;
    /**
     * ip地址
     */
    @ExcelField(value = "ip", align = 1, title = "ip地址(必填)", required = true, dataType = DataTypeEnum.IP)
    private String ip;
    /**
     * ip地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址(必填)", required = true, dataType = DataTypeEnum.MAC)
    private String mac;
    /**
     * 到期时间
     */
    @ExcelField(value = "dueDate", align = 1, title = "到期时间(必填)", isDate = true, required = true)
    private Long   dueDate;

    /**
     * 购买日期
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买日期", isDate = true)
    private Long   buyDate;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号", type = 0)
    private String serial;

    /**
     * 机房位置
     */
    @ExcelField(value = "houseLocation", align = 1, title = "机房位置")
    private String houseLocation;


    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期")
    private String            warranty;
    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述", length = 300)
    private String            memo;



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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
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

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
}
