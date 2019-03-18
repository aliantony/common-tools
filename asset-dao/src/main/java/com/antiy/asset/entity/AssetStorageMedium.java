package com.antiy.asset.entity;

import com.antiy.common.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p></p>
 *
 * @author lvliang
 * @since 2019-01-17
 */

public class AssetStorageMedium extends BaseEntity {

    /**
     * 资产主键
     */
    private String  assetId;
    /**
     * 最大存储量
     */
    @ApiModelProperty("最大存储量")
    private String  maximumStorage;
    /**
     * 单机磁盘数
     */
    private Integer diskNumber;
    /**
     * 高速缓存
     */
    private String  highCache;
    /**
     * 内置接口
     */
    private String  innerInterface;
    /**
     * RAID支持
     */
    private String  raidSupport;
    /**
     * 平均传输率
     */
    private String  averageTransferRate;
    /**
     * 驱动器数量
     */
    private Integer driverNumber;
    /**
     * 固件
     */
    private String  firmware;
    /**
     * OS版本
     */
    private String  osVersion;
    /**
     * 创建时间
     */
    private Long    gmtCreate;
    /**
     * 修改时间
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
     * 状态,1 未删除,0已删除
     */
    private Integer status;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getMaximumStorage() {
        return maximumStorage;
    }

    public void setMaximumStorage(String maximumStorage) {
        this.maximumStorage = maximumStorage;
    }

    public Integer getDiskNumber() {
        return diskNumber;
    }

    public void setDiskNumber(Integer diskNumber) {
        this.diskNumber = diskNumber;
    }

    public String getHighCache() {
        return highCache;
    }

    public void setHighCache(String highCache) {
        this.highCache = highCache;
    }

    public String getInnerInterface() {
        return innerInterface;
    }

    public void setInnerInterface(String innerInterface) {
        this.innerInterface = innerInterface;
    }

    public String getRaidSupport() {
        return raidSupport;
    }

    public void setRaidSupport(String raidSupport) {
        this.raidSupport = raidSupport;
    }

    public String getAverageTransferRate() {
        return averageTransferRate;
    }

    public void setAverageTransferRate(String averageTransferRate) {
        this.averageTransferRate = averageTransferRate;
    }

    public Integer getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(Integer driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
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
        return "AssetStorageMedium{" + ", assetId=" + assetId + ", maximumStorage=" + maximumStorage + ", diskNumber="
               + diskNumber + ", highCache=" + highCache + ", innerInterface=" + innerInterface + ", raidSupport="
               + raidSupport + ", averageTransferRate=" + averageTransferRate + ", driverNumber=" + driverNumber
               + ", firmware=" + firmware + ", osVersion=" + osVersion + ", gmtCreate=" + gmtCreate + ", gmtModified="
               + gmtModified + ", memo=" + memo + ", createUser=" + createUser + ", modifyUser=" + modifyUser
               + ", status=" + status + "}";
    }
}