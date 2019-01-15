package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 计算设备
 */
public class ComputeDeviceEntity extends AssetEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 资产分组
     */
    @ExcelField(value = "asset_group", align = 1, title = "所属分组", type = 0)
    private String            assetGroup;

    /**
     * 联系电话
     */
    @ExcelField(value = "telephone", align = 1, title = "联系电话", type = 0)
    private String            telephone;

    /**
     * 邮箱
     */
    @ExcelField(value = "email", align = 1, title = "邮箱", type = 0)
    private String            email;

    /**
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置", type = 0)
    private String            location;

    /**
     * 使用者
     */
    @ExcelField(value = "responsible_user_id", align = 1, title = "使用者", type = 0)
    private Integer           responsibleUserId;

    /**
     * 资产来源
     */
    @ExcelField(value = "asset_source", align = 1, title = "资产来源", type = 0)
    private String            assetSource;

    /**
     * 固件版本
     */
    @ExcelField(value = "asset_status", align = 1, title = "资产状态", type = 1)
    private String            assetStatus;

    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    @ExcelField(value = "outer_ip", align = 1, title = "IP地址", type = 1)
    private String            outerIp;
    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    @ExcelField(value = "inner_ip", align = 1, title = "IP地址", type = 1)
    private String            innerIp;
    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址", type = 0)
    private String            mac;
    /**
     * 子网掩码
     */
    @ExcelField(value = "subnet_mask", align = 1, title = "mac地址", type = 0)
    private String            subnetMask;
    /**
     * 预计带宽
     */
    @ExcelField(value = "expect_bandwidth", align = 1, title = "预计带宽", type = 0)
    private String            expectBandwidth;

    /**
     * 配置寄存器
     */
    @ExcelField(value = "register", align = 1, title = "预计带宽", type = 0)
    private String            register;

    /**
     * cpu
     */
    @ExcelField(value = "cpu", align = 1, title = "预计带宽", type = 0)
    private String            cpu;

    /**
     * cpu版本
     */
    @ExcelField(value = "cpu_version", align = 1, title = "cpu版本", type = 0)
    private String            cpuVersion;

    /**
     * dram大小
     */
    @ExcelField(value = "dram_size", align = 1, title = "cpu版本", type = 0)
    private String            dramSize;

    /**
     * flash大小
     */
    @ExcelField(value = "flash_size", align = 1, title = "cpu版本", type = 0)
    private String            flashSize;


    /**
     * NCRM大小
     */
    @ExcelField(value = "ncrm_size", align = 1, title = "cpu版本", type = 0)
    private String            ncrmSize;
    /**
     * 备注
     */
    @ExcelField(value = "memo", align = 1, title = "备注", type = 0)
    private String            memo;

    public String getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(String assetGroup) {
        this.assetGroup = assetGroup;
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

    public Integer getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(Integer responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
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

    public String getExpectBandwidth() {
        return expectBandwidth;
    }

    public void setExpectBandwidth(String expectBandwidth) {
        this.expectBandwidth = expectBandwidth;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCpuVersion() {
        return cpuVersion;
    }

    public void setCpuVersion(String cpuVersion) {
        this.cpuVersion = cpuVersion;
    }

    public String getDramSize() {
        return dramSize;
    }

    public void setDramSize(String dramSize) {
        this.dramSize = dramSize;
    }

    public String getFlashSize() {
        return flashSize;
    }

    public void setFlashSize(String flashSize) {
        this.flashSize = flashSize;
    }

    public String getNcrmSize() {
        return ncrmSize;
    }

    public void setNcrmSize(String ncrmSize) {
        this.ncrmSize = ncrmSize;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
