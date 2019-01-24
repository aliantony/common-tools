package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetHardDiskResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetHardDiskResponse extends BaseResponse {
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  assetId;
    /**
     * 硬盘品牌
     */
    @ApiModelProperty("硬盘品牌")
    private String  brand;
    /**
     * 硬盘型号
     */
    @ApiModelProperty("硬盘型号")
    private String  model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String  serial;
    /**
     * 接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道
     */
    @ApiModelProperty("接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道")
    private Integer interfaceType;
    /**
     * 容量 (MB)
     */
    @ApiModelProperty("容量 (MB)")
    private Integer capacity;
    /**
     * 磁盘类型,1 HDD,2,SSD
     */
    @ApiModelProperty("磁盘类型,1 HDD,2,SSD")
    private Integer diskType;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long    buyDate;

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

    @Override
    public String toString() {
        return "AssetHardDiskResponse{" +
                "assetId='" + assetId + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", serial='" + serial + '\'' +
                ", interfaceType=" + interfaceType +
                ", capacity=" + capacity +
                ", diskType=" + diskType +
                ", buyDate=" + buyDate +
                '}';
    }
}