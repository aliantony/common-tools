package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

public class ServerEntity {
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
     * 编号
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
     * ip地址
     */
    @ExcelField(value = "ip", align = 1, title = "ip地址")
    private String ip;
    /**
     * MAC地址
     */
    @ExcelField(value = "mac", align = 1, title = "MAC地址")
    private String mac;
    /**
     * 操作系统
     */
    @ExcelField(value = "os", align = 1, title = "操作系统")
    private String os;
    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String location;
    /**
     * 所属分组
     */
    @ExcelField(value = "asset_group", align = 1, title = "所属分组")
    private String assetGroup;
    /**
     * 责任人
     */
    @ExcelField(value = "responsible_user", align = 1, title = "责任人")
    private String responsibleUser;
    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话")
    private String telephone;
    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱")
    private String email;
    /**
     * 主板
     */
    @ExcelField(value = "mainboard_brand", align = 1, title = "主板品牌")
    private String mainboardBrand;
    /**
     * 处理器个数
     */
    @ExcelField(value = "cpu_number", align = 1, title = "处理器个数")
    private String cpuNumber;
    /**
     * 处理器名称
     */
    @ExcelField(value = "server_number", align = 1, title = "处理器名称")
    private String serverNumber;
    /**
     * 处理器速度
     */
    @ExcelField(value = "cpu_speed", align = 1, title = "处理器速度")
    private String cpuSpeed;

    /**
     * 内存大小
     */
    @ExcelField(value = "memory_size", align = 1, title = "内存大小")
    private String memorySize;
    /**
     * 硬盘大小
     */
    @ExcelField(value = "hard_disk_size", align = 1, title = "硬盘大小")
    private String hardDiskSize;
    /**
     * 网口
     */
    @ExcelField(value = "mesh_port", align = 1, title = "网口")
    private String meshPort;
    /**
     * 串口
     */
    @ExcelField(value = "serial_port", align = 1, title = "串口")
    private String serialPort;
    /**
     * 资产来源
     */
    @ExcelField(value = "asset_source", align = 1, title = "资产来源",dictType = "asset_source")
    private String assetSource;

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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
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

    public String getMainboardBrand() {
        return mainboardBrand;
    }

    public void setMainboardBrand(String mainboardBrand) {
        this.mainboardBrand = mainboardBrand;
    }

    public String getCpuNumber() {
        return cpuNumber;
    }

    public void setCpuNumber(String cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    public String getServerNumber() {
        return serverNumber;
    }

    public void setServerNumber(String serverNumber) {
        this.serverNumber = serverNumber;
    }

    public String getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(String cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getHardDiskSize() {
        return hardDiskSize;
    }

    public void setHardDiskSize(String hardDiskSize) {
        this.hardDiskSize = hardDiskSize;
    }

    public String getMeshPort() {
        return meshPort;
    }

    public void setMeshPort(String meshPort) {
        this.meshPort = meshPort;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }
}
