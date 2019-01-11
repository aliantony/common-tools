package com.antiy.asset.vo.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCpuResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCpuResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer           id;
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    private Integer           assetId;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String            serial;
    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    private String            brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String            model;
    /**
     * CPU主频
     */
    @ApiModelProperty("CPU主频")
    private Float             mainFrequency;
    /**
     * 线程数
     */
    @ApiModelProperty("线程数")
    private Integer           threadSize;
    /**
     * 核心数
     */
    @ApiModelProperty("核心数")
    private Integer           coreSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public Float getMainFrequency() {
        return mainFrequency;
    }

    public void setMainFrequency(Float mainFrequency) {
        this.mainFrequency = mainFrequency;
    }

    public Integer getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(Integer threadSize) {
        this.threadSize = threadSize;
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }
}