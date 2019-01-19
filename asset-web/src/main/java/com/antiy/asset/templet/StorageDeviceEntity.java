package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 存储介质
 */
public class StorageDeviceEntity {
    /**
     * 序号
     */
    @ExcelField(value = "order_num", align = 1, title = "序号", type = 1)
    private String  orderNum;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称")
    private String  name;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String  manufacturer;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String  serial;
    /**
     * 归属区域
     */
    @ExcelField(value = "area", align = 1, title = "归属区域",type = 1)
    private String  area;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者",type = 1)
    private String  user;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话",type = 1)
    private String  telephone;
    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱",type = 1)
    private String  email;
    /**
     * 资产组
     */
    @ExcelField(value = "asset_group", align = 1, title = "资产组",type = 1)
    private String  assetGroup;
    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String  location;
    /**
     * 机房位置
     */
    @ExcelField( align = 1, title = "机房位置")
    private String  houseLocation;
    /**
     * 最大存储量
     */
    @ExcelField(value = "capacity", align = 1, title = "最大存储量")
    private String  capacity;
    /**
     * 单机磁盘数
     */
    @ExcelField(value = "hard_disk_num", align = 1, title = "单机磁盘数")
    private Integer hardDiskNum;
    /**
     * 内部接口
     */
    @ExcelField(value = "inner_interface", align = 1, title = "内部接口")
    private String  innerInterface;
    /**
     * RAID支持
     */
    @ExcelField(value = "raid_support", align = 1, title = "RAID支持")
    private String  raidSupport;
    /**
     * high_cache
     */
    @ExcelField(value = "high_cache", align = 1, title = "RAID支持")
    private String  highCache;
    /**
     * 平均传输率
     */
    @ExcelField(value = "average_transmission_rate ", align = 1, title = "平均传输率")
    private String  averageTransmissionRate;
    /**
     * 固件
     */
    @ExcelField(value = "firmware", align = 1, title = "固件")
    private String  firmware;
    /**
     * OS版本
     */
    @ExcelField(value = "os_version", align = 1, title = "OS版本")
    private String  slotType;
    /**
     * 驱动器数量
     */
    @ExcelField(value = "drive_num", align = 1, title = "驱动器数量")
    private Integer driveNum;
    /**
     * 购买时间
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买时间", isDate = true)
    private Long    buyDate;
    /**
     * 到期时间
     */
    @ExcelField(value = "due_date", align = 1, title = "到期时间", isDate = true)
    private Long    dueDate;

    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期", isDate = true)
    private Long    warranty;

    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述")
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

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getRaidSupport() {
        return raidSupport;
    }

    public void setRaidSupport(String raidSupport) {
        this.raidSupport = raidSupport;
    }

    public String getAverageTransmissionRate() {
        return averageTransmissionRate;
    }

    public void setAverageTransmissionRate(String averageTransmissionRate) {
        this.averageTransmissionRate = averageTransmissionRate;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
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

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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
}
