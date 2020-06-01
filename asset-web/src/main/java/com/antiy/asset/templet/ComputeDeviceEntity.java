package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.asset.vo.enums.DataTypeEnum;

/**
 * 计算设备
 */
public class ComputeDeviceEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号(必填)", required = true)
    private String number;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商(必填) 格式由:小写英文,数字,符号等组成", length = 80, required = true)
    private String manufacturer;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称(必填)  备注:填写该厂商下的名称 ", length = 128, required = true)
    private String name;
    /**
     * 资产名称
     */
    @ExcelField(value = "code", align = 1, title = "国资码(必填)", length = 255, required = true)
    private String code;
    /**
     * 资产名称
     */
    @ExcelField(value = "netType", align = 1, title = "网络类型(必填)", required = true, defaultDataMethod = "getNetType", defaultDataBeanName = "assetTemplateServiceImpl")
    private String netType;
    /**
     * 资产名称
     */
    @ExcelField(value = "isSecrecy", align = 1, title = "是否涉密(必填)", required = true, defaultDataMethod = "yesNo", defaultDataBeanName = "assetTemplateServiceImpl")
    private String isSecrecy;
    /**
     * 是否孤岛
     */
    @ExcelField(value = "isOrphan", align = 1, title = "是否孤岛设备", defaultDataMethod = "yesNo", defaultDataBeanName = "assetTemplateServiceImpl")
    private String isOrphan;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者(必填)", required = true, defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String user;
    /**
     * 操作系统
     */
    @ExcelField(value = "operationSystem", align = 1, title = "操作系统(必填)", required = true, defaultDataMethod = "getAllSystemOs", defaultDataBeanName = "assetTemplateServiceImpl", length = 128)
    private String operationSystem;
    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域(必填)", required = true, defaultDataMethod = "queryAllAreaYeZi", defaultDataBeanName = "assetTemplateServiceImpl")
    private String area;

    /**
     * 重要程度
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度(必填)", dictType = "major_type", required = true)
    private String importanceDegree;
    /**
     * p地址
     */
    @ExcelField(value = "ip", align = 1, title = "ip(必填)", dataType = DataTypeEnum.IP, required = true)
    private String ip;
    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac(必填)", dataType = DataTypeEnum.MAC, required = true)
    private String mac;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "dueTime", align = 1, title = "到期时间(必填)", isDate = true, required = true)
    private Long   dueTime;
    /**
     * 使用到期时间
     */
    @ExcelField(value = "expirationReminder", align = 1, title = "到期提醒", isDate = true, required = false)
    private Long   expirationReminder;
    /**
     * 使用到期时间
     */
    @ExcelField(value = "installDate", align = 1, title = "装机时间", isDate = true, required = false)
    private Long   installDate;
    /**
     * 使用到期时间
     */
    @ExcelField(value = "activiateDate", align = 1, title = "启用时间", isDate = true, required = false)
    private Long   activiateDate;
    /**
     * 购买时间
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买时间", isDate = true)
    private Long   buyDate;

    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String serial;
    /**
     * 机房位置
     */
    @ExcelField(value = "houseLocation", align = 1, title = "机房位置")
    private String houseLocation;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(String isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public Long getExpirationReminder() {
        return expirationReminder;
    }

    public void setExpirationReminder(Long expirationReminder) {
        this.expirationReminder = expirationReminder;
    }

    public Long getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Long installDate) {
        this.installDate = installDate;
    }

    public Long getActiviateDate() {
        return activiateDate;
    }

    public void setActiviateDate(Long activiateDate) {
        this.activiateDate = activiateDate;
    }

    public String getIsOrphan() {
        return isOrphan;
    }

    public void setIsOrphan(String isOrphan) {
        this.isOrphan = isOrphan;
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
