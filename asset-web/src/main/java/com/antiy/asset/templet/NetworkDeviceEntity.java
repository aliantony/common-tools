package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;
import com.antiy.asset.vo.enums.DataTypeEnum;

/**
 * 网络设备
 */

public class NetworkDeviceEntity {

    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号(必填)", required = true)
    private String  number;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商(必填)", length = 80, required = true)
    private String  manufacturer;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称(必填)", required = true)
    private String  name;

    /**
     * 版本
     */
    @ExcelField(value = "version", align = 1, title = "版本(必填)", required = true)
    private String  version;
    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者(必填)", required = true, defaultDataMethod = "getAllUser", defaultDataBeanName = "assetTemplateServiceImpl")
    private String  user;

    /**
     * area
     */
    @ExcelField(value = "area", align = 1, title = "所属区域(必填)", required = true, defaultDataMethod = "queryAllArea", defaultDataBeanName = "assetTemplateServiceImpl")
    private String  area;

    /**
     * 重要程度
     */
    @ExcelField(value = "importanceDegree", align = 1, title = "重要程度(必填)", dictType = "major_type", required = true)
    private String  importanceDegree;
    /**
     * 数目
     */
    @ExcelField(value = "portSize", align = 1, title = "网口数目(必填)", required = true, length = 2)
    private Integer portSize;

    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac(必填)", dataType = DataTypeEnum.MAC, required = true)
    private String  mac;
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
     * 接口数目
     */
    @ExcelField(value = "interfaceSize", align = 1, title = "接口数目", length = 7)
    private Integer interfaceSize;
    /**
     * IOS
     */
    @ExcelField(value = "ios", align = 1, title = "IOS")
    private String  ios;

    /**
     * 固件版本
     */
    @ExcelField(value = "firmwareVersion", align = 1, title = "固件版本")
    private String  firmwareVersion;
    /**
     * 是否无线
     */
    @ExcelField(value = "isWireless", align = 1, title = "是否无线", dictType = "yesorno")
    private Integer isWireless;

    /**
     * 外网IP
     */
    @ExcelField(value = "outerIp", align = 1, title = "外网IP", dataType = DataTypeEnum.IP, required = false)
    private String  outerIp;

    /**
     * 子网掩码
     */
    @ExcelField(value = "subnetMask", align = 1, title = "子网掩码", dataType = DataTypeEnum.IP)
    private String  subnetMask;
    /**
     * 预计带宽
     */
    @ExcelField(value = "expectBandwidth", align = 1, title = "预计带宽", length = 7)
    private Integer expectBandwidth;

    /**
     * 配置寄存器
     */
    @ExcelField(value = "register", align = 1, title = "配置寄存器", length = 7)
    private Integer register;

    /**
     * cpu大小
     */
    @ExcelField(value = "cpuSize", align = 1, title = "cpu大小", length = 7)
    private Integer cpuSize;

    /**
     * cpu版本
     */
    @ExcelField(value = "cpuVersion", align = 1, title = "cpu版本")
    private String  cpuVersion;

    /**
     * dram大小
     */
    @ExcelField(value = "dramSize", align = 1, title = "dram大小", length = 7)
    private Float   dramSize;

    /**
     * flash大小
     */
    @ExcelField(value = "flashSize", align = 1, title = "flash大小", length = 7)
    private Float   flashSize;

    /**
     * NCRM大小
     */
    @ExcelField(value = "ncrmSize", align = 1, title = "NCRM大小", length = 7)
    private Float   ncrmSize;
    /**
     * 购买日期
     */
    @ExcelField(value = "butDate", align = 1, title = "购买日期", isDate = true)
    private Long    butDate;
    /**
     * 到期时间
     */
    @ExcelField(value = "dueDate", align = 1, title = "到期时间(必填)", isDate = true, required = true)
    private Long    dueDate;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public Integer getPortSize() {
        return portSize;
    }

    public void setPortSize(Integer portSize) {
        this.portSize = portSize;
    }

    public Integer getInterfaceSize() {
        return interfaceSize;
    }

    public void setInterfaceSize(Integer interfaceSize) {
        this.interfaceSize = interfaceSize;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Integer getIsWireless() {
        return isWireless;
    }

    public void setIsWireless(Integer isWireless) {
        this.isWireless = isWireless;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public Integer getExpectBandwidth() {
        return expectBandwidth;
    }

    public void setExpectBandwidth(Integer expectBandwidth) {
        this.expectBandwidth = expectBandwidth;
    }

    public Integer getRegister() {
        return register;
    }

    public void setRegister(Integer register) {
        this.register = register;
    }

    public Integer getCpuSize() {
        return cpuSize;
    }

    public void setCpuSize(Integer cpuSize) {
        this.cpuSize = cpuSize;
    }

    public String getCpuVersion() {
        return cpuVersion;
    }

    public void setCpuVersion(String cpuVersion) {
        this.cpuVersion = cpuVersion;
    }

    public Float getDramSize() {
        return dramSize;
    }

    public void setDramSize(Float dramSize) {
        this.dramSize = dramSize;
    }

    public Float getFlashSize() {
        return flashSize;
    }

    public void setFlashSize(Float flashSize) {
        this.flashSize = flashSize;
    }

    public Float getNcrmSize() {
        return ncrmSize;
    }

    public void setNcrmSize(Float ncrmSize) {
        this.ncrmSize = ncrmSize;
    }

    public Long getButDate() {
        return butDate;
    }

    public void setButDate(Long butDate) {
        this.butDate = butDate;
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
}
