package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 存储介质
 */
public class HardDiskEntity {
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称")
    private String name;
    /**
     * 品类
     */
    @ExcelField(value = "category_model", align = 1, title = "资产品类")
    private String categoryModel;

    /**
     * 资产型号
     */
    @ExcelField(value = "number", align = 1, title = "编号")
    private String number;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String serial;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String manufacturer;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String user;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "使用者联系电话")
    private String telephone;
    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "使用者邮箱")
    private String email;
    /**
     * 资产组
     */
    @ExcelField(value = "asset_group", align = 1, title = "资产组")
    private String assetGroup;
    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String location;
    /**
     * 容量
     */
    @ExcelField(value = "capacity", align = 1, title = "容量")
    private String capacity;
    /**
     * 接口类型
     */
    @ExcelField(value = "interfaceType", align = 1, title = "接口类型",dictType = "interface_type")
    private String interfaceType;
    /**
     * 磁盘类型
     */
    @ExcelField(value = "disk_type", align = 1, title = "磁盘类型")
    private String diskType;
    /**
     * 内存类型
     */
    @ExcelField(value = "memory_type", align = 1, title = "内存类型")
    private String memoryType;
    /**
     * 内存主频
     */
    @ExcelField(value = "memory_master_frequency", align = 1, title = "内存主频")
    private String memoryMasterFrequency;
    /**
     * 插槽类型
     */
    @ExcelField(value = "slot_type", align = 1, title = "插槽类型")
    private String slotType;
    /**
     * 是否带散热
     */
    @ExcelField(value = "heat_dissipation", align = 1, title = "是否带散热")
    private String heatDissipation;
    /**
     * 针脚数
     */
    @ExcelField(value = "pin_count", align = 1, title = "针脚数")
    private Integer pinCount;
    /**
     * 购买日期
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买时间", isDate = true)
    private Long buy_date;
    /**
     * 到期时间
     */
    @ExcelField(value = "dueTime", align = 1, title = "到期时间", isDate = true)
    private Long due_time;
    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期", isDate = true)
    private Long warranty;

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

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getMemoryMasterFrequency() {
        return memoryMasterFrequency;
    }

    public void setMemoryMasterFrequency(String memoryMasterFrequency) {
        this.memoryMasterFrequency = memoryMasterFrequency;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getHeatDissipation() {
        return heatDissipation;
    }

    public void setHeatDissipation(String heatDissipation) {
        this.heatDissipation = heatDissipation;
    }

    public Integer getPinCount() {
        return pinCount;
    }

    public void setPinCount(Integer pinCount) {
        this.pinCount = pinCount;
    }

    public Long getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(Long buy_date) {
        this.buy_date = buy_date;
    }

    public Long getDue_time() {
        return due_time;
    }

    public void setDue_time(Long due_time) {
        this.due_time = due_time;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }
}
