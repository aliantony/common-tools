package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> 安全设备详情表 </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */

public class AssetSafetyEquipment extends BaseEntity {

    /**
     * 资产主键
     */
    private String assetId;
    /**
     * MAC地址
     */
    @ApiModelProperty("MAC地址")
    private String mac;
    /**
     * 特征库版本
     */
    private String  featureLibrary;
    /**
     * 策略配置
     */
    private String  strategy;
    /**
     * 软件版本
     */
    private String  softwareVersion;
    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String  ip;
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
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    private Long    gmtModified;
    /**
     * 状态,1未删除,0已删除
     */
    private Integer status;

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getFeatureLibrary() {
        return featureLibrary;
    }

    public void setFeatureLibrary(String featureLibrary) {
        this.featureLibrary = featureLibrary;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
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

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return System.currentTimeMillis();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "AssetSafetyEquipment{" +
                "assetId='" + assetId + '\'' +
                ", featureLibrary='" + featureLibrary + '\'' +
                ", strategy='" + strategy + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", ip='" + ip + '\'' +
                ", memo='" + memo + '\'' +
                ", createUser=" + createUser +
                ", modifyUser=" + modifyUser +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", status=" + status +
                '}';
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}