package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <p> AssetHardDisk 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetHardDiskQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 资产主键
     */
    @ApiModelProperty(value = "资产主键")
    @Encode
    private String  assetId;
    /**
     * 硬盘品牌
     */
    @ApiModelProperty(value = "硬盘品牌")
    private String  brand;
    /**
     * 硬盘型号
     */
    @ApiModelProperty(value = "硬盘型号")
    private String  model;
    /**
     * 序列号
     */
    @ApiModelProperty(value = "序列号")
    private String  serial;
    /**
     * 接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道
     */
    @ApiModelProperty(value = "接口类型")
    private Integer interfaceType;
    /**
     * 容量
     */
    @ApiModelProperty(value = "容量")
    private Integer capacity;
    /**
     * 磁盘类型,1 HDD,2,SSD
     */
    @ApiModelProperty("磁盘类型,1 HDD,2,SSD")
    private Integer diskType;

    public Integer getDiskType() {
        return diskType;
    }

    public void setDiskType(Integer diskType) {
        this.diskType = diskType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public String toString() {
        return "AssetHardDiskQuery{" +
                "assetId='" + assetId + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", serial='" + serial + '\'' +
                ", interfaceType=" + interfaceType +
                '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}