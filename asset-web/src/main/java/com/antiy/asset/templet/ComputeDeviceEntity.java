package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 计算设备
 */
public class ComputeDeviceEntity {
    /**
     * 序号
     */
    @ExcelField(value = "id", align = 1, title = "序号")
    private Integer id;

    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称")
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
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String user;

    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话")
    private String  telephone;


    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱")
    private String  email;

    /**
     * 资产组
     */
    @ExcelField(value = "asset_group", align = 1, title = "资产组")
    private String  assetGroup;


    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String  location;

    /**
     * 机房位置
     */
    @ExcelField(value = "house_location", align = 1, title = "机房位置")
    private String  houseLocation;

    /**
     * 固件版本
     */
    @ExcelField(value = "firmware_version", align = 1, title = "固件版本")
    private String  firmwareVersion;


    /**
     * 操作系统
     */
    @ExcelField(value = "operation_system", align = 1, title = "操作系统")
    private String  operatioSystem;

    /**
     * 重要程度
     */
    @ExcelField(value = "importance_degree", align = 1, title = "重要程度",dictType = "major_type")
    private String  importanceDegree;

    /**
     * 购买时间
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买时间", isDate = true)
    private String  buyDate;

    /**
     * 购买时间
     */
    @ExcelField(value = "due_time", align = 1, title = "到期时间", isDate = true)
    private String  dueTime;
    /**
     * 描述
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期")
    private Long    warranty;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "description", align = 1, title = "描述", isDate = true)
    private String    description;
    /**
     * 内存数量
     */
    @ExcelField(value = "memory_num", align = 1, title = "内存数量")
    private String  memoryNum;
    /**
     * 内存品牌
     */
    @ExcelField(value = "memory_brand", align = 1, title = "内存品牌")
    private String  memoryBrand;

    /**
     * 内存序列号
     */
    @ExcelField(value = "memory_serial", align = 1, title = "内存序列号")
    private String  memorySerial;
    /**
     * 内存容量
     */
    @ExcelField(value = "memory_capacity", align = 1, title = "内存容量")
    private String  memoryCapacity;

    /**
     * 内存频率
     */
    @ExcelField(value = "memory_type", align = 1, title = "内存频率")
    private String  memoryType;
    /**
     * 内存主频
     */
    @ExcelField(value = "memory_frequency", align = 1, title = "内存主频")
    private String  memoryFrequency;

    /**
     * 内存插槽类型
     */
    @ExcelField(value = "slot_type", align = 1, title = "内存插槽类型",dictType = "slot_type")
    private String  slotType;

    /**
     * 是否带散热
     */
    @ExcelField(value = "cpu_is_heatsink", align = 1, title = "内存是否带散热",dictType = "yesorno")
    private String  cpuIsHeatsink;
    /**
     * 针脚数
     */
    @ExcelField(value = "cpu_stitch", align = 1, title = "内存针脚数")
    private String  cpuStitch;
    /**
     * 硬盘数量
     */
    @ExcelField(value = "hard_disk_num", align = 1, title = "硬盘数量")
    private String hardDiskNum;
    /**
     * 硬盘品牌
     */
    @ExcelField(value = "hard_disk_brand", align = 1, title = "硬盘品牌")
    private String hardDiskBrand;
    /**
     * 硬盘型号
     */
    @ExcelField(value = "hard_disk_model", align = 1, title = "硬盘型号")
    private String hardDiskModel;

    /**
     * 硬盘序列号
     */
    @ExcelField(value = "hard_disk_serial", align = 1, title = "硬盘序列号")
    private String hardDiskSerial;

    /**
     * 硬盘容量
     */
    @ExcelField(value = "hard_disk_capacity", align = 1, title = "硬盘容量")
    private String hardDisCapacityl;
    /**
     * 硬盘接口类型
     */
    @ExcelField(value = "hard_disk_interface_type", align = 1, title = "硬盘接口类型",dictType = "interface_type")
    private String hardDiskInterfaceType;
    /**
     * 硬盘磁盘类型
     */
    @ExcelField(value = "hard_disk_type", align = 1, title = "硬盘磁盘类型",dictType = "disk_type")
    private String hardDiskType;
    /**
     * 硬盘购买时间
     */
    @ExcelField(value = "hard_disk_buy_date", align = 1, title = "硬盘购买时间",isDate = true)
    private Long hardDiskBuyDate;
    /**
     * 主板品牌
     */
    @ExcelField(value = "mainborad_brand", align = 1, title = "主板品牌")
    private String mainboradBrand;
    /**
     * 主板型号
     */
    @ExcelField(value = "mainborad_model", align = 1, title = "主板型号")
    private String mainboradModel;
    /**
     * 主板序列号
     */
    @ExcelField(value = "mainborad_serial", align = 1, title = "主板序列号")
    private String mainboradSerial;
    /**
     * 主板bios版本
     */
    @ExcelField(value = "mainborad_bios_version", align = 1, title = "主板bios版本")
    private String mainboradBiosVersion;
    /**
     * 主板bios日期
     */
    @ExcelField(value = "mainborad_bios_date", align = 1, title = "主板bios日期")
    private String mainboradBiosDate;
    /**
     * cpu数量
     */
    @ExcelField(value = "cpu_num", align = 1, title = "cpu数量")
    private String cpuNum;
    /**
     * cpu品牌
     */
    @ExcelField(value = "cpu_brand", align = 1, title = "cpu品牌")
    private String cpuBrand;
    /**
     * cpu型号
     */
    @ExcelField(value = "cpu_model", align = 1, title = "cpu型号")
    private String cpuModel;
    /**
     * cpu序列号
     */
    @ExcelField(value = "cpu_serial", align = 1, title = "cpu序列号")
    private String cpuSerial;
    /**
     * cpu主频
     */
    @ExcelField(value = "cpu_main_frequency", align = 1, title = "cpu主频")
    private String cpuMainFrequency;
    /**
     * cpu线程数
     */
    @ExcelField(value = "cpu_thread_size", align = 1, title = "cpu线程数")
    private String cpuThreadSize;
    /**
     * cpu核心数
     */
    @ExcelField(value = "cpu_core_size", align = 1, title = "cpu核心数")
    private String cpuCoreSize;
    /**
     * 网卡数量
     */
    @ExcelField(value = "network_num", align = 1, title = "网卡数量")
    private String networkNum;
    /**
     * 网卡品牌
     */
    @ExcelField(value = "network_brand", align = 1, title = "网卡品牌")
    private String networkBrand;
    /**
     * 网卡型号
     */
    @ExcelField(value = "network_model", align = 1, title = "网卡型号")
    private String networkModel;
    /**
     * 网卡序列号
     */
    @ExcelField(value = "network_serial", align = 1, title = "网卡序列号")
    private String networkSerial;
    /**
     * 网卡ip地址
     */
    @ExcelField(value = "network_ip_address", align = 1, title = "网卡ip地址")
    private String networkIpAddress;
    /**
     * 网卡mac地址
     */
    @ExcelField(value = "network_mac_address", align = 1, title = "网卡mac地址")
    private String networkMacAddress;
    /**
     * 网卡子网掩码
     */
    @ExcelField(value = "network_subnet_mask", align = 1, title = "网卡子网掩码")
    private String networkSubnetMask;
    /**
     * 网卡默认网关
     */
    @ExcelField(value = "network_default_gateway", align = 1, title = "网卡默认网关")
    private String networkDefaultGateway;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOperatioSystem() {
        return operatioSystem;
    }

    public void setOperatioSystem(String operatioSystem) {
        this.operatioSystem = operatioSystem;
    }

    public String getImportanceDegree() {
        return importanceDegree;
    }

    public void setImportanceDegree(String importanceDegree) {
        this.importanceDegree = importanceDegree;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemoryNum() {
        return memoryNum;
    }

    public void setMemoryNum(String memoryNum) {
        this.memoryNum = memoryNum;
    }

    public String getMemoryBrand() {
        return memoryBrand;
    }

    public void setMemoryBrand(String memoryBrand) {
        this.memoryBrand = memoryBrand;
    }

    public String getMemorySerial() {
        return memorySerial;
    }

    public void setMemorySerial(String memorySerial) {
        this.memorySerial = memorySerial;
    }

    public String getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(String memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getMemoryFrequency() {
        return memoryFrequency;
    }

    public void setMemoryFrequency(String memoryFrequency) {
        this.memoryFrequency = memoryFrequency;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public String getCpuIsHeatsink() {
        return cpuIsHeatsink;
    }

    public void setCpuIsHeatsink(String cpuIsHeatsink) {
        this.cpuIsHeatsink = cpuIsHeatsink;
    }

    public String getCpuStitch() {
        return cpuStitch;
    }

    public void setCpuStitch(String cpuStitch) {
        this.cpuStitch = cpuStitch;
    }

    public String getHardDiskNum() {
        return hardDiskNum;
    }

    public void setHardDiskNum(String hardDiskNum) {
        this.hardDiskNum = hardDiskNum;
    }

    public String getHardDiskBrand() {
        return hardDiskBrand;
    }

    public void setHardDiskBrand(String hardDiskBrand) {
        this.hardDiskBrand = hardDiskBrand;
    }

    public String getHardDiskModel() {
        return hardDiskModel;
    }

    public void setHardDiskModel(String hardDiskModel) {
        this.hardDiskModel = hardDiskModel;
    }

    public String getHardDiskSerial() {
        return hardDiskSerial;
    }

    public void setHardDiskSerial(String hardDiskSerial) {
        this.hardDiskSerial = hardDiskSerial;
    }

    public String getHardDisCapacityl() {
        return hardDisCapacityl;
    }

    public void setHardDisCapacityl(String hardDisCapacityl) {
        this.hardDisCapacityl = hardDisCapacityl;
    }

    public String getHardDiskInterfaceType() {
        return hardDiskInterfaceType;
    }

    public void setHardDiskInterfaceType(String hardDiskInterfaceType) {
        this.hardDiskInterfaceType = hardDiskInterfaceType;
    }

    public String getHardDiskType() {
        return hardDiskType;
    }

    public void setHardDiskType(String hardDiskType) {
        this.hardDiskType = hardDiskType;
    }

    public Long getHardDiskBuyDate() {
        return hardDiskBuyDate;
    }

    public void setHardDiskBuyDate(Long hardDiskBuyDate) {
        this.hardDiskBuyDate = hardDiskBuyDate;
    }

    public String getMainboradBrand() {
        return mainboradBrand;
    }

    public void setMainboradBrand(String mainboradBrand) {
        this.mainboradBrand = mainboradBrand;
    }

    public String getMainboradModel() {
        return mainboradModel;
    }

    public void setMainboradModel(String mainboradModel) {
        this.mainboradModel = mainboradModel;
    }

    public String getMainboradSerial() {
        return mainboradSerial;
    }

    public void setMainboradSerial(String mainboradSerial) {
        this.mainboradSerial = mainboradSerial;
    }

    public String getMainboradBiosVersion() {
        return mainboradBiosVersion;
    }

    public void setMainboradBiosVersion(String mainboradBiosVersion) {
        this.mainboradBiosVersion = mainboradBiosVersion;
    }

    public String getMainboradBiosDate() {
        return mainboradBiosDate;
    }

    public void setMainboradBiosDate(String mainboradBiosDate) {
        this.mainboradBiosDate = mainboradBiosDate;
    }

    public String getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    public String getCpuBrand() {
        return cpuBrand;
    }

    public void setCpuBrand(String cpuBrand) {
        this.cpuBrand = cpuBrand;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getCpuSerial() {
        return cpuSerial;
    }

    public void setCpuSerial(String cpuSerial) {
        this.cpuSerial = cpuSerial;
    }

    public String getCpuMainFrequency() {
        return cpuMainFrequency;
    }

    public void setCpuMainFrequency(String cpuMainFrequency) {
        this.cpuMainFrequency = cpuMainFrequency;
    }

    public String getCpuThreadSize() {
        return cpuThreadSize;
    }

    public void setCpuThreadSize(String cpuThreadSize) {
        this.cpuThreadSize = cpuThreadSize;
    }

    public String getCpuCoreSize() {
        return cpuCoreSize;
    }

    public void setCpuCoreSize(String cpuCoreSize) {
        this.cpuCoreSize = cpuCoreSize;
    }

    public String getNetworkNum() {
        return networkNum;
    }

    public void setNetworkNum(String networkNum) {
        this.networkNum = networkNum;
    }

    public String getNetworkBrand() {
        return networkBrand;
    }

    public void setNetworkBrand(String networkBrand) {
        this.networkBrand = networkBrand;
    }

    public String getNetworkModel() {
        return networkModel;
    }

    public void setNetworkModel(String networkModel) {
        this.networkModel = networkModel;
    }

    public String getNetworkSerial() {
        return networkSerial;
    }

    public void setNetworkSerial(String networkSerial) {
        this.networkSerial = networkSerial;
    }

    public String getNetworkIpAddress() {
        return networkIpAddress;
    }

    public void setNetworkIpAddress(String networkIpAddress) {
        this.networkIpAddress = networkIpAddress;
    }

    public String getNetworkMacAddress() {
        return networkMacAddress;
    }

    public void setNetworkMacAddress(String networkMacAddress) {
        this.networkMacAddress = networkMacAddress;
    }

    public String getNetworkSubnetMask() {
        return networkSubnetMask;
    }

    public void setNetworkSubnetMask(String networkSubnetMask) {
        this.networkSubnetMask = networkSubnetMask;
    }

    public String getNetworkDefaultGateway() {
        return networkDefaultGateway;
    }

    public void setNetworkDefaultGateway(String networkDefaultGateway) {
        this.networkDefaultGateway = networkDefaultGateway;
    }
}
