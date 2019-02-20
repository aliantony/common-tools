package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 网卡信息表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetNetworkCard extends BaseEntity {

    /**
     * 资产主键
     */
    private String  assetId;
    /**
     * 品牌
     */
    @ApiModelProperty("pinp")
    private String  brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String  model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String  serial;
    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String  ipAddress;
    /**
     * MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String  macAddress;
    /**
     * 默认网关
     */
    @ApiModelProperty("默认网关")
    private String  defaultGateway;
    /**
     * 网络地址
     */
    private String  networkAddress;
    /**
     * 子网掩码
     */
    @ApiModelProperty("子网掩码")
    private String  subnetMask;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 备注
     */
    private String  memo;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer modifyUser;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDefaultGateway() {
        return defaultGateway;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetNetworkCard{" + ", assetId=" + assetId + ", brand=" + brand + ", model=" + model + ", serial="
               + serial + ", ipAddress=" + ipAddress + ", macAddress=" + macAddress + ", defaultGateway="
               + defaultGateway + ", networkAddress=" + networkAddress + ", subnetMask=" + subnetMask + ", gmtCreate="
               + gmtCreate + ", gmtModified=" + gmtModified + ", memo=" + memo + ", createUser=" + createUser
               + ", modifyUser=" + modifyUser + ", status=" + status + "}";
    }
}