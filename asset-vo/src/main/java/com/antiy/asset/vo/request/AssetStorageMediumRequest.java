package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import io.swagger.annotations.ApiModelProperty;
import com.antiy.common.validation.ObjectValidator;
import com.antiy.common.exception.RequestParamValidateException;
/**
 * <p>
 * AssetStorageMediumRequest 请求对象
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetStorageMediumRequest extends BasicRequest implements ObjectValidator{

    /**
     *  资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer assetId;
    /**
     *  最大存储量
     */
    @ApiModelProperty("最大存储量")
    private String maximumStorage;
    /**
     *  单机磁盘数
     */
    @ApiModelProperty("单机磁盘数")
    private Integer diskNumber;
    /**
     *  高速缓存
     */
    @ApiModelProperty("高速缓存")
    private String highCache;
    /**
     *  内置接口
     */
    @ApiModelProperty("内置接口")
    private String innerInterface;
    /**
     *  RAID支持
     */
    @ApiModelProperty("RAID支持")
    private String raidSupport;
    /**
     *  平均传输率
     */
    @ApiModelProperty("平均传输率")
    private String averageTransferRate;
    /**
     *  驱动器数量
     */
    @ApiModelProperty("驱动器数量")
    private Integer driverNumber;
    /**
     *  固件
     */
    @ApiModelProperty("固件")
    private String firmware;
    /**
     *  OS版本
     */
    @ApiModelProperty("OS版本")
    private String osVersion;
    /**
     *  创建时间
     */
    @ApiModelProperty("创建时间")
    private Long gmtCreate;
    /**
     *  修改时间
     */
    @ApiModelProperty("修改时间")
    private Long gmtModified;
    /**
     *  备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     *  创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     *  修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     *  状态,1 未删除,0已删除
     */
    @ApiModelProperty("状态,1 未删除,0已删除")
    private Integer status;



    public Integer getAssetId() {
    return assetId;
    }

public void setAssetId(Integer assetId) {
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
    public void validate() throws RequestParamValidateException {

    }

}