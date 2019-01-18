package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> AssetMemoryRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMemoryRequest extends BasicRequest implements ObjectValidator {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String  assetId;
    /**
     * 硬盘品牌
     */
    @ApiModelProperty("内存品牌")
    @NotBlank
    private String  brand;
    /**
     * 内存容量
     */
    @ApiModelProperty("内存容量")
    @NotNull
    private Integer capacity;
    /**
     * 内存主频(MHz)
     */
    @ApiModelProperty("内存主频(MHz)")
    @NotNull
    private Integer frequency;
    /**
     * 插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM
     */
    @ApiModelProperty("插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM")
    private Integer slotType;
    /**
     * 是否带散热片:0-不带，1-带
     */
    @ApiModelProperty("是否带散热片:0-不带，1-带")
    private Boolean isHeatsink;
    /**
     * 针脚数
     */
    @ApiModelProperty("针脚数")
    private Integer stitch;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long    buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private Long    warrantyDate;
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String  telephone;

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

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getSlotType() {
        return slotType;
    }

    public void setSlotType(Integer slotType) {
        this.slotType = slotType;
    }

    public Boolean getHeatsink() {
        return isHeatsink;
    }

    public void setHeatsink(Boolean isHeatsink) {
        this.isHeatsink = isHeatsink;
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

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}