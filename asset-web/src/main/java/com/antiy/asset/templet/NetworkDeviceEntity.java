package com.antiy.asset.templet;

import com.antiy.asset.annotation.ExcelField;

/**
 * 网络设备
 */
public class NetworkDeviceEntity extends AssetEntity{
    /**
     * 资产分组
     */
    @ExcelField(value = "asset_group", align = 1, title = "资产分组", type = 0)
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
     * 固件版本
     */
    @ExcelField(value = "asset_status", align = 1, title = "资产状态", type = 1)
    private String            assetStatus;

    /**
     * 内存JSON数据{ID:1,name:Kingston,rom:8GB}
     */
    @ExcelField(value = "ip", align = 1, title = "IP地址", type = 1)
    private String            ip;

    /**
     * mac地址
     */
    @ExcelField(value = "mac", align = 1, title = "mac地址", type = 0)
    private String            mac;

    /**
     * 0-不重要(not_major),1- 一般(general),3-重要(major),
     */
    @ExcelField(value = "is_innet", align = 1, title = "是否入网", type = 0, dictType = "major_type")
    private Boolean           isInnet;

    /**
     * 网卡JSON数据{ID:1,name:intel,speed:1900M}
     */
    @ExcelField(value = "buy_date", align = 1, title = "购买时间", type = 1)
    private String            networkCard;

    /**
     * 描述
     */
    @ExcelField(value = "warranty", align = 1, title = "保修期", type = 0)
    private Long              warranty;

    /**
     * 使用到期时间
     */
    @ExcelField(value = "service_life", align = 1, title = "使用到期时间", type = 0, isDate = true)
    private Long              serviceLife;

    /**
     * 制造日期
     */
    @ExcelField(value = "buyDate", align = 1, title = "制造日期", type = 0, isDate = true)
    private Long              buyDate;
    /**
     * 制造日期
     */
    @ExcelField(value = "port", align = 1, title = "端口", type = 0)
    private Long              port;
    /**
     * 制造日期
     */
    @ExcelField(value = "first_enter_nett", align = 1, title = "制造日期", type = 0, isDate = true)
    private Long              firstEnterNett;
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
}