package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.List;

/**
 * <p> AssetHardDiskRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetHardDiskRequest extends BasicRequest implements ObjectValidator {

    private static final long serialVersionUID = 1L;

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer           assetId;
    /**
     * 硬盘品牌
     */
    @ApiModelProperty("硬盘品牌")
    private String            brand;
    /**
     * 硬盘型号
     */
    @ApiModelProperty("硬盘型号")
    private String            model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String            serial;
    /**
     * 接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道
     */
    @ApiModelProperty("接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道")
    private Integer           interfaceType;
    /**
     * 容量 (MB)
     */
    @ApiModelProperty("容量 (MB)")
    private Integer           capacity;
    /**
     * 磁盘类型,1 HDD,2,SSD
     */
    @ApiModelProperty("磁盘类型,1 HDD,2,SSD")
    private Integer           diskType;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long              buyDate;
    /**
     * 使用次数
     */
    @ApiModelProperty("使用次数")
    private Integer           useTimes;
    /**
     * 累计小时
     */
    @ApiModelProperty("累计小时")
    private Integer           cumulativeHour;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
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

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getDiskType() {
        return diskType;
    }

    public void setDiskType(Integer diskType) {
        this.diskType = diskType;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public Integer getCumulativeHour() {
        return cumulativeHour;
    }

    public void setCumulativeHour(Integer cumulativeHour) {
        this.cumulativeHour = cumulativeHour;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}