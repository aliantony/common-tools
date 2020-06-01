package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.asset.vo.enums.DataTypeEnum;

/**
 * @author chenchaowu
 */
public class OtherDeviceEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号(必填) ", required = true)
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
     * 国资码
     */
    @ExcelField(value = "code", align = 1, title = "国资码(必填) ", length = 255, required = true)
    private String code;

    /**
     * 网络类型
     */
    @ExcelField(value = "netType", align = 1, title = "网络类型(必填)", required = true, defaultDataMethod = "getNetType", defaultDataBeanName = "assetTemplateServiceImpl")
    private String netType;

    /**
     * 是否涉密
     */
    @ExcelField(value = "isSecrecy", align = 1, title = "是否涉密(必填)", required = true, defaultDataMethod = "yesNo", defaultDataBeanName = "assetTemplateServiceImpl")
    private String isSecrecy;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者(必填)", required = true, defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String  user;

    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域(必填)", required = true, defaultDataMethod = "queryAllAreaYeZi", defaultDataBeanName = "assetTemplateServiceImpl")
    private String  area;

    /**
     * 重要程度
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度(必填)", dictType = "major_type", required = true)
    private String  importanceDegree;

    /**
     * ip地址
     */
    @ExcelField(value = "ip", align = 1, title = "ip地址", required = false, dataType = DataTypeEnum.IP)
    private String ip;

    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址", required = false, dataType = DataTypeEnum.MAC)
    private String mac;

    /**
     * 到期时间
     */
    @ExcelField(value = "dueDate", align = 1, title = "到期时间", isDate = true)
    private Long   dueDate;

    /**
     * 到期提醒
     */
    @ExcelField(value = "expirationReminder", align = 1, title = "到期提醒", isDate = true)
    private Long expirationReminder;

    /**
     * 装机时间
     */
    @ExcelField(value = "installDate", align = 1, title = "装机时间", isDate = true)
    private Long installDate;

    /**
     * 启用时间
     */
    @ExcelField(value = "activiateDate", align = 1, title = "启用时间", isDate = true)
    private Long activiateDate;

    /**
     * 购买日期
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买日期", isDate = true)
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
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期")
    private String   warranty;

    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述", length = 300)
    private String memo;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(String importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public String getIsSecrecy() {
        return isSecrecy;
    }

    public void setIsSecrecy(String isSecrecy) {
        this.isSecrecy = isSecrecy;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
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

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
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
}
