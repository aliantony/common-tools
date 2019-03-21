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
    @ExcelField(value = "number", align = 1, title = "资产编号")
    private String  number;

    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称(必填)", required = true)
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
    @ExcelField(value = "user", align = 1, title = "使用者(必填)", required = true)
    private String  user;

    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域(必填)", required = true)
    private String  area;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话", type = 0)
    private String  telephone;

    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱", type = 0)
    private String  email;

    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置(必填)", required = true)
    private String  location;

    /**
     * 机房位置
     */
    @ExcelField(value = "houseLocation", align = 1, title = "机房位置")
    private String  houseLocation;

    /**
     * 固件版本
     */
    @ExcelField(value = "firmwareVersion", align = 1, title = "固件版本")
    private String  firmwareVersion;

    /**
     * 操作系统
     */
    @ExcelField(value = "operationSystem", align = 1, title = "操作系统")
    private String  operationSystem;

    /**
     * 重要程度
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度(必填)", dictType = "major_type", required = true)
    private String  importanceDegree;

    /**
     * 购买时间
     */
    @ExcelField(value = "buyDate", align = 1, title = "购买时间", isDate = true)
    private Long    buyDate;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "dueTime", align = 1, title = "到期时间(必填)", isDate = true, required = true)
    private Long    dueTime;
    /**
     * 保修
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期", isDate = true)
    private Long    warranty;

    /**
     * memo
     */
    @ExcelField(value = "description", align = 1, title = "描述")
    private String  description;
    /**
     * 内存数量
     */
    @ExcelField(value = "memoryNum", align = 1, title = "内存数量")
    private Integer memoryNum;
    /**
     * 内存品牌
     */
    @ExcelField(value = "memoryBrand", align = 1, title = "内存品牌")
    private String  memoryBrand;

    /**
     * 内存序列号
     */
    @ExcelField(value = "memorySerial", align = 1, title = "内存序列号")
    private String  memorySerial;
    /**
     * 内存容量
     */
    @ExcelField(value = "memoryCapacity", align = 1, title = "内存容量")
    private Integer memoryCapacity;

    /**
     * 内存类型
     */
    @ExcelField(value = "transferType", align = 1, title = "内存类型", dictType = "transfer_type")
    private Integer transferType;
    /**
     * 内存主频
     */
    @ExcelField(value = "memoryFrequency", align = 1, title = "内存主频")
    private Double  memoryFrequency;

    /**
     * 内存插槽类型
     */
    @ExcelField(value = "slotType", align = 1, title = "内存插槽类型", dictType = "slot_type")
    private Integer slotType;

    /**
     * 是否带散热
     */
    @ExcelField(value = "heatsink", align = 1, title = "内存是否带散热", dictType = "yesorno")
    private Integer heatsink;
    /**
     * 针脚数
     */
    @ExcelField(value = "stitch", align = 1, title = "内存针脚数")
    private Integer stitch;
    /**
     * 硬盘数量
     */
    @ExcelField(value = "hardDiskNum", align = 1, title = "硬盘数量")
    private Integer hardDiskNum;
    /**
     * 硬盘品牌
     */
    @ExcelField(value = "hardDiskBrand", align = 1, title = "硬盘品牌")
    private String  hardDiskBrand;
    /**
     * 硬盘型号
     */
    @ExcelField(value = "hardDiskModel", align = 1, title = "硬盘型号")
    private String  hardDiskModel;

    /**
     * 硬盘序列号
     */
    @ExcelField(value = "hardDiskSerial", align = 1, title = "硬盘序列号")
    private String  hardDiskSerial;

    /**
     * 硬盘容量
     */
    @ExcelField(value = "hardDisCapacityl", align = 1, title = "硬盘容量")
    private Integer hardDisCapacityl;
    /**
     * 硬盘接口类型
     */
    @ExcelField(value = "hardDiskInterfaceType", align = 1, title = "硬盘接口类型", dictType = "interface_type")
    private Integer hardDiskInterfaceType;
    /**
     * 硬盘磁盘类型
     */
    @ExcelField(value = "hardDiskType", align = 1, title = "硬盘磁盘类型", dictType = "disk_type")
    private Integer hardDiskType;
    /**
     * 硬盘购买时间
     */
    @ExcelField(value = "hardDiskBuyDate", align = 1, title = "硬盘购买时间", isDate = true)
    private Long    hardDiskBuyDate;
    /**
     * 主板数量
     */
    @ExcelField(value = "mainboradNum", align = 1, title = "主板数量")
    private Integer mainboradNum;
    /**
     * 主板品牌
     */
    @ExcelField(value = "mainboradBrand", align = 1, title = "主板品牌")
    private String  mainboradBrand;
    /**
     * 主板型号
     */
    @ExcelField(value = "mainboradModel", align = 1, title = "主板型号")
    private String  mainboradModel;
    /**
     * 主板序列号
     */
    @ExcelField(value = "mainboradSerial", align = 1, title = "主板序列号")
    private String  mainboradSerial;
    /**
     * 主板bios版本
     */
    @ExcelField(value = "mainboradBiosVersion", align = 1, title = "主板bios版本")
    private String  mainboradBiosVersion;
    /**
     * 主板bios日期
     */
    @ExcelField(value = "mainboradBiosDate", align = 1, title = "主板bios日期", isDate = true)
    private Long    mainboradBiosDate;
    /**
     * cpu数量
     */
    @ExcelField(value = "cpuNum", align = 1, title = "cpu数量")
    private Integer cpuNum;
    /**
     * cpu品牌
     */
    @ExcelField(value = "cpuBrand", align = 1, title = "cpu品牌")
    private String  cpuBrand;
    /**
     * cpu型号
     */
    @ExcelField(value = "cpuModel", align = 1, title = "cpu型号")
    private String  cpuModel;
    /**
     * cpu序列号
     */
    @ExcelField(value = "cpuSerial", align = 1, title = "cpu序列号")
    private String  cpuSerial;
    /**
     * cpu主频
     */
    @ExcelField(value = "cpuMainFrequency", align = 1, title = "cpu主频")
    private Float   cpuMainFrequency;
    /**
     * cpu线程数
     */
    @ExcelField(value = "cpuThreadSize", align = 1, title = "cpu线程数")
    private Integer cpuThreadSize;
    /**
     * cpu核心数
     */
    @ExcelField(value = "cpuCoreSize", align = 1, title = "cpu核心数")
    private Integer cpuCoreSize;
    // /**
    // * 网卡数量
    // */
    // @ExcelField(value = "network_num", align = 1, title = "网卡数量")
    // private Integer networkNum;
    /**
     * 网卡品牌
     */
    @ExcelField(value = "networkBrand", align = 1, title = "网卡品牌")
    private String  networkBrand;
    /**
     * 网卡型号
     */
    @ExcelField(value = "networkModel", align = 1, title = "网卡型号")
    private String  networkModel;
    /**
     * 网卡序列号
     */
    @ExcelField(value = "networkSerial", align = 1, title = "网卡序列号")
    private String  networkSerial;
    /**
     * 网卡ip地址
     */
    @ExcelField(value = "networkIpAddress", align = 1, title = "网卡ip地址")
    private String  networkIpAddress;
    /**
     * 网卡mac地址
     */
    @ExcelField(value = "networkMacAddress", align = 1, title = "网卡mac地址")
    private String  networkMacAddress;
    /**
     * 网卡子网掩码
     */
    @ExcelField(value = "networkSubnetMask", align = 1, title = "网卡子网掩码")
    private String  networkSubnetMask;
    /**
     * 网卡默认网关
     */
    @ExcelField(value = "networkDefaultGateway", align = 1, title = "网卡默认网关")
    private String  networkDefaultGateway;

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

    public Integer getMemoryNum() {
        return memoryNum;
    }

    public void setMemoryNum(Integer memoryNum) {
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

    public Integer getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(Integer memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public Double getMemoryFrequency() {
        return memoryFrequency;
    }

    public void setMemoryFrequency(Double memoryFrequency) {
        this.memoryFrequency = memoryFrequency;
    }

    public Integer getSlotType() {
        return slotType;
    }

    public void setSlotType(Integer slotType) {
        this.slotType = slotType;
    }

    public Integer getHeatsink() {
        return heatsink;
    }

    public void setHeatsink(Integer heatsink) {
        this.heatsink = heatsink;
    }

    public Integer getStitch() {
        return stitch;
    }

    public void setStitch(Integer stitch) {
        this.stitch = stitch;
    }

    public Integer getHardDiskNum() {
        return hardDiskNum;
    }

    public void setHardDiskNum(Integer hardDiskNum) {
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

    public Integer getHardDisCapacityl() {
        return hardDisCapacityl;
    }

    public void setHardDisCapacityl(Integer hardDisCapacityl) {
        this.hardDisCapacityl = hardDisCapacityl;
    }

    public Integer getHardDiskInterfaceType() {
        return hardDiskInterfaceType;
    }

    public void setHardDiskInterfaceType(Integer hardDiskInterfaceType) {
        this.hardDiskInterfaceType = hardDiskInterfaceType;
    }

    public Integer getHardDiskType() {
        return hardDiskType;
    }

    public void setHardDiskType(Integer hardDiskType) {
        this.hardDiskType = hardDiskType;
    }

    public Long getHardDiskBuyDate() {
        return hardDiskBuyDate;
    }

    public void setHardDiskBuyDate(Long hardDiskBuyDate) {
        this.hardDiskBuyDate = hardDiskBuyDate;
    }

    public Integer getMainboradNum() {
        return mainboradNum;
    }

    public void setMainboradNum(Integer mainboradNum) {
        this.mainboradNum = mainboradNum;
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

    public Long getMainboradBiosDate() {
        return mainboradBiosDate;
    }

    public void setMainboradBiosDate(Long mainboradBiosDate) {
        this.mainboradBiosDate = mainboradBiosDate;
    }

    public Integer getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(Integer cpuNum) {
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

    public Float getCpuMainFrequency() {
        return cpuMainFrequency;
    }

    public void setCpuMainFrequency(Float cpuMainFrequency) {
        this.cpuMainFrequency = cpuMainFrequency;
    }

    public Integer getCpuThreadSize() {
        return cpuThreadSize;
    }

    public void setCpuThreadSize(Integer cpuThreadSize) {
        this.cpuThreadSize = cpuThreadSize;
    }

    public Integer getCpuCoreSize() {
        return cpuCoreSize;
    }

    public void setCpuCoreSize(Integer cpuCoreSize) {
        this.cpuCoreSize = cpuCoreSize;
    }

    // public Integer getNetworkNum() {
    // return networkNum;
    // }
    //
    // public void setNetworkNum(Integer networkNum) {
    // this.networkNum = networkNum;
    // }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
