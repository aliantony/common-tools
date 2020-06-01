package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 存储介质
 */
public class StorageDeviceEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号(必填)", required = true)
    private String  number;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商(必填) 格式由:小写英文,数字,符号等组成", length = 80, required = true)
    private String  manufacturer;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称(必填)  备注:填写该厂商下的名称 ", length = 128, required = true)
    private String  name;
    /**
     * 资产名称
     */
    @ExcelField(value = "code", align = 1, title = "国资码(必填)", length = 255, required = true)
    private String  code;

    /**
     * 资产名称
     */
    @ExcelField(value = "isSecrecy", align = 1, title = "是否涉密(必填)", required = true, defaultDataMethod = "yesNo", defaultDataBeanName = "assetTemplateServiceImpl")
    private String  isSecrecy;



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
     * 最大存储量
     */
    @ExcelField(value = "capacity", align = 1, title = "最大存储量(必填)", required = true, length = 7)
    private String  capacity;

    /**
     * 到期时间
     */
    @ExcelField(value = "dueDate", align = 1, title = "到期时间(必填)", isDate = true, required = true)
    private Long    dueDate;
    /**
     * 购买时间
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买时间", isDate = true)
    private Long    buyDate;
    /**
     * 使用到期时间
     */
    @ExcelField(value = "expirationReminder", align = 1, title = "到期提醒", isDate = true, required = false)
    private Long    expirationReminder;
    /**
     * 使用到期时间
     */
    @ExcelField(value = "installDate", align = 1, title = "装机时间", isDate = true, required = false)
    private Long    installDate;
    /**
     * 使用到期时间
     */
    @ExcelField(value = "activiateDate", align = 1, title = "启用时间", isDate = true, required = false)
    private Long    activiateDate;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String  serial;
    /**
     * 机房位置
     */
    @ExcelField(value = "houseLocation", align = 1, title = "机房位置")
    private String  houseLocation;
    /**
     * 单机磁盘数
     */
    @ExcelField(value = "hardDiskNum", align = 1, title = "单机磁盘数", length = 7)
    private Integer hardDiskNum;
    /**
     * 内部接口
     */
    @ExcelField(value = "innerInterface", align = 1, title = "内部接口")
    private String  innerInterface;
    /**
     * RAID支持
     */
    @ExcelField(value = "raidSupport", align = 1, title = "RAID支持", dictType = "yesorno")
    private Integer raidSupport;
    /**
     * high_cache
     */
    @ExcelField(value = "highCache", align = 1, title = "高速缓存")
    private String  highCache;
    /**
     * 平均传输率
     */
    @ExcelField(value = "averageTransmissionRate ", align = 1, title = "平均传输率")
    private String  averageTransmissionRate;
    /**
     * 固件
     */
    @ExcelField(value = "firmwareVersion", align = 1, title = "固件版本")
    private String  firmwareVersion;
    /**
     * OS版本
     */
    @ExcelField(value = "slotType", align = 1, title = "OS版本")
    private String  slotType;
    /**
     * 驱动器数量
     */
    @ExcelField(value = "driveNum", align = 1, title = "驱动器数量", length = 7)
    private Integer driveNum;

    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期")
    private String  warranty;

    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述", length = 300)
    private String  memo;

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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getInnerInterface() {
        return innerInterface;
    }

    public void setInnerInterface(String innerInterface) {
        this.innerInterface = innerInterface;
    }

    public Integer getRaidSupport() {
        return raidSupport;
    }

    public void setRaidSupport(Integer raidSupport) {
        this.raidSupport = raidSupport;
    }

    public String getAverageTransmissionRate() {
        return averageTransmissionRate;
    }

    public void setAverageTransmissionRate(String averageTransmissionRate) {
        this.averageTransmissionRate = averageTransmissionRate;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public Integer getDriveNum() {
        return driveNum;
    }

    public void setDriveNum(Integer driveNum) {
        this.driveNum = driveNum;
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

    public void setHardDiskNum(Integer hardDiskNum) {
        this.hardDiskNum = hardDiskNum;
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

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public Integer getHardDiskNum() {
        return hardDiskNum;
    }

    public String getHighCache() {
        return highCache;
    }

    public void setHighCache(String highCache) {
        this.highCache = highCache;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
