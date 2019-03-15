package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 网络设备
 */

public class NetworkDeviceEntity {
    /**
     * 序号
     */
    @ExcelField(value = "order_number", align = 1, title = "序号")
    private Integer orderNumber;
    /**
     * 资产编号
     */
    @ExcelField(value = "number", align = 1, title = "资产编号")
    private String                  number;
    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "名称")
    private String name;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String manufacturer;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String serial;

    /**
     * 使用者
     */
    @ExcelField(value = "user", align = 1, title = "使用者")
    private String user;

    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话", type = 0)
    private String telephone;

    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱", type = 0)
    private String email;

    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String location;
    /**
     * 机房位置
     */
    @ExcelField(value = "house_location", align = 1, title = "机房位置")
    private String houseLocation;
    /**
     * 端口数目
     */
    @ExcelField(value = "port_size", align = 1, title = "端口数目")
    private Integer portSize;
    /**
     * 接口数目
     */
    @ExcelField(value = "interface_size", align = 1, title = "接口数目")
    private Integer interfaceSize;
    /**
     * IOS
     */
    @ExcelField(value = "IOS", align = 1, title = "IOS")
    private String ios;

    /**
     * 固件版本
     */
    @ExcelField(value = "firmware_version", align = 1, title = "固件版本")
    private String firmwareVersion;
    /**
     * 是否无线
     */
    @ExcelField(value = "is_wireless", align = 1, title = "是否无线", dictType = "yesorno")
    private Integer isWireless;
    /**
     * 内网IP
     */
    @ExcelField(value = "inner_ip", align = 1, title = "内网IP")
    private String innerIp;
    /**
     * 外网IP
     */
    @ExcelField(value = "outer_ip", align = 1, title = "外网IP")
    private String outerIp;
    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址")
    private String mac;
    /**
     * 子网掩码
     */
    @ExcelField(value = "subnet_mask", align = 1, title = "子网掩码")
    private String subnetMask;
    /**
     * 预计带宽
     */
    @ExcelField(value = "expect_bandwidth", align = 1, title = "预计带宽")
    private Integer expectBandwidth;

    /**
     * 配置寄存器
     */
    @ExcelField(value = "register", align = 1, title = "配置寄存器")
    private Integer register;

    /**
     * cpu大小
     */
    @ExcelField(value = "cpu", align = 1, title = "cpu大小")
    private Integer cpuSize;

    /**
     * cpu版本
     */
    @ExcelField(value = "cpu_version", align = 1, title = "cpu版本")
    private String cpuVersion;

    /**
     * dram大小
     */
    @ExcelField(value = "dram_size", align = 1, title = "dram大小")
    private Float dramSize;

    /**
     * flash大小
     */
    @ExcelField(value = "flash_size", align = 1, title = "flash大小")
    private Float flashSize;

    /**
     * NCRM大小
     */
    @ExcelField(value = "ncrm_size", align = 1, title = "NCRM大小")
    private Float ncrmSize;
    /**
     * 购买日期
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买日期", isDate = true)
    private Long butDate;
    /**
     * 到期时间
     */
    @ExcelField(value = "due_date", align = 1, title = "到期时间", isDate = true)
    private Long dueDate;
    /**
     * 保修期
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期", isDate = true)
    private Long warranty;
    /**
     * 描述
     */
    @ExcelField(value = "memo", align = 1, title = "描述")
    private String memo;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
