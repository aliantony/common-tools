package com.antiy.asset.vo.request;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetHardDiskRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetHardDiskRequest extends BasicRequest implements ObjectValidator, Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String  id;
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
    // @NotBlank(message = "硬盘品牌不能为空")
    @Size(message = "硬盘品牌长度不能超过60位", max = 60)
    private String  brand;
    /**
     * 硬盘型号
     */
    @ApiModelProperty("硬盘型号")
    @Size(message = "硬盘品牌长度不能超过60位", max = 60)
    private String  model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "硬盘序列号长度不能超过30位", max = 30)
    private String  serial;
    /**
     * 接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道
     */
    @ApiModelProperty("接口类型:1SATA、2IDE、3ATA、4SCSI、5光纤通道")
    @Max(value = 5, message = "接口类型不能大于5")
    @Min(value = 1, message = "接口类型不能小于1")
    private Integer interfaceType;
    /**
     * 容量 (MB)
     */
    @ApiModelProperty("容量 (MB)")
    @NotNull(message = "硬盘容量不能为空")
    @Max(value = 9999999, message = "硬盘容量不超过9999999")
    private Integer capacity;
    /**
     * 磁盘类型,1 HDD,2,SSD
     */
    @ApiModelProperty("磁盘类型,1 HDD,2,SSD")
    @NotNull(message = "磁盘类型不能为空")
    @Max(value = 2, message = "磁盘类型不能大于2")
    @Min(value = 1, message = "磁盘类型不能小于1")
    private Integer diskType;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long    buyDate;
    /**
     * 使用次数
     */
    @ApiModelProperty("使用次数")
    @Max(value = 9999999, message = "使用次数不超过9999999")
    private Integer useTimes;
    /**
     * 累计小时
     */
    @ApiModelProperty("累计小时")
    @Max(value = 9999999, message = "累计小时不超过9999999")
    private Integer cumulativeHour;
    /**
     * Request
     */
    private String  memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    @Override
    public String toString() {
        return "AssetHardDiskRequest{" + "assetId='" + assetId + '\'' + ", brand='" + brand + '\'' + ", model='" + model
               + '\'' + ", serial='" + serial + '\'' + ", interfaceType=" + interfaceType + ", capacity=" + capacity
               + ", diskType=" + diskType + ", buyDate=" + buyDate + ", useTimes=" + useTimes + ", cumulativeHour="
               + cumulativeHour + ", memo='" + memo + '\'' + '}';
    }

}