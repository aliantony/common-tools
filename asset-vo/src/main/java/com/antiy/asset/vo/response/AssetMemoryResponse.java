package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMemoryResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMemoryResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private int               id;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer           assetId;
    /**
     * 内存容量
     */
    @ApiModelProperty("内存容量")
    private Integer           capacity;
    /**
     * 内存主频(MHz)
     */
    @ApiModelProperty("内存主频(MHz)")
    private Integer           frequency;
    /**
     * 插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM
     */
    @ApiModelProperty("插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM")
    private Integer           slotType;
    /**
     * 是否带散热片:0-不带，1-带
     */
    @ApiModelProperty("是否带散热片:0-不带，1-带")
    private Boolean           isHeatsink;
    /**
     * 针脚数
     */
    @ApiModelProperty("针脚数")
    private Integer           stitch;
    /**
     * 购买日期
     */
    @ApiModelProperty("购买日期")
    private Long              buyDate;
    /**
     * 保修期
     */
    @ApiModelProperty("保修期")
    private Long              warrantyDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
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

    public void setHeatsink(Boolean heatsink) {
        isHeatsink = heatsink;
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
}