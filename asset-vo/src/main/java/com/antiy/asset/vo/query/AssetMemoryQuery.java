package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

/**
 * <p> AssetMemory 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMemoryQuery extends ObjectQuery implements ObjectValidator {
    /**
     * 资产主键
     */
    private Integer assetId;
    /**
     * 内存容量
     */
    private Integer capacity;
    /**
     * 内存主频(MHz)
     */
    private Integer frequency;
    /**
     * 插槽类型:0-SDRAM,1-SIMM,2-DIMM,3-RIMM
     */
    private Integer slotType;
    /**
     * 是否带散热片:0-不带，1-带
     */
    private Boolean isHeatsink;
    /**
     * 针脚数
     */
    private Integer stitch;

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

    @Override
    public void validate() throws RequestParamValidateException {

    }
}