package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 计算设备
 */
public class ComputeDeviceEntity {

    /**
     * 资产名称
     */
    @ExcelField(value = "name", align = 1, title = "资产名称")
    private String  name;
    /**
     * 品类
     */
    @ExcelField(value = "category_model", align = 1, title = "资产品类")
    private String  categoryModel;

    /**
     * 资产型号
     */
    @ExcelField(value = "number", align = 1, title = "编号")
    private String  number;
    /**
     * 序列号
     */
    @ExcelField(value = "serial", align = 1, title = "序列号")
    private String  serial;
    /**
     * 厂商
     */
    @ExcelField(value = "manufacturer", align = 1, title = "厂商")
    private String  manufacturer;
    /**
     * 资产分组
     */
    @ExcelField(value = "asset_group", align = 1, title = "资产分组")
    private String  assetGroup;

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
     * 物理位置
     */
    @ExcelField(value = "location", align = 1, title = "物理位置")
    private String  location;

    /**
     * 使用者
     */
    @ExcelField(value = "responsible_user_id", align = 1, title = "使用者")
    private Integer responsibleUserId;

    /**
     * 资产状态
     */
    @ExcelField(value = "asset_status", align = 1, title = "资产状态")
    private String  assetStatus;

    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    @ExcelField(value = "ip", align = 1, title = "IP地址")
    private String  ip;

    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址")
    private String  mac;

    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    @ExcelField(value = "is_innet", align = 1, title = "是否入网", dictType = "major_type")
    private Boolean isInnet;

    /**
     * 网卡JSON数据{ID:1,name:intel,speed:1900M}
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买时间", isDate = true)
    private String  networkCard;

    /**
     * 描述
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期")
    private Long    warranty;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "service_life", align = 1, title = "使用到期时间", isDate = true)
    private Long    serviceLife;

    /**
     * 制造日期
     */
    @ExcelField(value = "buyDate", align = 1, title = "制造日期", isDate = true)
    private Long    buyDate;
    /**
     * 制造日期
     */
    @ExcelField(value = "port", align = 1, title = "端口")
    private Long    port;
    /**
     * 制造日期
     */
    @ExcelField(value = "first_enter_nett", align = 1, title = "制造日期", isDate = true)
    private Long    firstEnterNett;
    /**
     * 备注
     */
    @ExcelField(value = "memo", align = 1, title = "备注")
    private String  memo;

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

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
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

    public Boolean getInnet() {
        return isInnet;
    }

    public void setInnet(Boolean innet) {
        isInnet = innet;
    }

    public String getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(String networkCard) {
        this.networkCard = networkCard;
    }

    public Long getWarranty() {
        return warranty;
    }

    public void setWarranty(Long warranty) {
        this.warranty = warranty;
    }

    public Long getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(Long serviceLife) {
        this.serviceLife = serviceLife;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public Long getFirstEnterNett() {
        return firstEnterNett;
    }

    public void setFirstEnterNett(Long firstEnterNett) {
        this.firstEnterNett = firstEnterNett;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

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
}
