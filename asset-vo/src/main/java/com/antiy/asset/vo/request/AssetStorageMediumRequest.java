package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetStorageMediumRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetStorageMediumRequest extends BasicRequest implements ObjectValidator {
    @Encode
    @ApiModelProperty(value = "主键")
    private String  id;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String assetId;
    /**
     * 最大存储量
     */
    @ApiModelProperty("最大存储量")
    private String  maximumStorage;
    /**
     * 单机磁盘数
     */
    @ApiModelProperty("单机磁盘数")
    private Integer diskNumber;
    /**
     * 高速缓存
     */
    @ApiModelProperty("高速缓存")
    private String  highCache;
    /**
     * 内置接口
     */
    @ApiModelProperty("内置接口")
    private String  innerInterface;
    /**
     * RAID支持
     */
    @ApiModelProperty("RAID支持")
    private String  raidSupport;
    /**
     * 平均传输率
     */
    @ApiModelProperty("平均传输率")
    private String  averageTransferRate;
    /**
     * 驱动器数量
     */
    @ApiModelProperty("驱动器数量")
    private Integer driverNumber;
    /**
     * 固件
     */
    @ApiModelProperty("固件")
    private String  firmware;
    /**
     * OS版本
     */
    @ApiModelProperty("OS版本")
    private String  osVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public void validate() throws RequestParamValidateException {

    }

}