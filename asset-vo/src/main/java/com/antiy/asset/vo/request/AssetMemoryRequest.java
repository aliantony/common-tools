package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p> AssetMemoryRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMemoryRequest extends BasicRequest implements ObjectValidator, Serializable {
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
     * 内存品牌
     */
    @ApiModelProperty("内存品牌")
    // @NotBlank(message = "内存品牌不能为空")
    @Size(message = "内存品牌长度不能超过60位", max = 60)
    private String  brand;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "序列号长度不能超过30位", max = 30)
    private String  serial;
    /**
     * 内存类型：1未知，2-ddr2,3-ddr3,4-ddr4
     */
    // @NotNull(message = "内存类型不能为空")
    @ApiModelProperty("内存类型：1未知，2-ddr2,3-ddr3,4-ddr4")
    @Max(message = "内存类型不能大于4", value = 4)
    @Min(message = "内存类型不能小于1", value = 1)
    private Integer transferType;
    /**
     * 内存容量
     */
    @ApiModelProperty("内存容量")
    @NotNull(message = "内存容量不能为空")
    @Max(value = 9999999, message = "内存容量不超过9999999")
    private Integer capacity;
    /**
     * 内存主频(MHz)
     */
    @ApiModelProperty("内存主频(MHz)")
    // @NotNull(message = "内存主频不能为空")
    @Max(value = 99999999, message = "内存主频不超过99999999")
    private Double  frequency;
    /**
     * 插槽类型:1-SDRAM,2-SIMM,3-DIMM,4-RIMM
     */
    @ApiModelProperty("插槽类型:1-SDRAM,2-SIMM,3-DIMM,4-RIMM")
    @Max(message = "内存类型不能大于4", value = 4)
    @Min(message = "内存类型不能小于1", value = 1)
    private Integer slotType;
    /**
     * 是否带散热片:2-不带，1-带
     */
    @ApiModelProperty("是否带散热片:2-不带，1-带")
    private Integer heatsink;
    /**
     * 针脚数
     */
    @ApiModelProperty("针脚数")
    @Max(value = 9999999, message = "针脚数不超过9999999")
    private Integer stitch;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long    buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    @Max(value = 9999999999999L, message = "时间超出范围")
    private Long    warrantyDate;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String  telephone;
    /**
     * 备注
     */
    private String  memo;

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Integer getSlotType() {
        return slotType;
    }

    public void setSlotType(Integer slotType) {
        this.slotType = slotType;
    }

    public Integer getHeatsink() {
        return heatsink;
    }

    public void setHeatsink(Integer heatsink) {
        this.heatsink = heatsink;
    }

    public Integer getStitch() {
        return stitch;
    }

    public void setStitch(Integer stitch) {
        this.stitch = stitch;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Long getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Long warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "AssetMemoryRequest{" + "assetId='" + assetId + '\'' + ", brand='" + brand + '\'' + ", transferType="
               + transferType + ", capacity=" + capacity + ", frequency=" + frequency + ", slotType=" + slotType
               + ", heatsink=" + heatsink + ", stitch=" + stitch + ", buyDate=" + buyDate + ", warrantyDate="
               + warrantyDate + ", telephone='" + telephone + '\'' + ", memo='" + memo + '\'' + '}';
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

}