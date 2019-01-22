package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetMainboradResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMainboradResponse extends BaseResponse {

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String assetId;
    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    private String brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    private String model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    private String serial;
    /**
     * BIOS版本
     */
    @ApiModelProperty("BIOS版本")
    private String biosVersion;
    /**
     * BIOS日期
     */
    @ApiModelProperty("BIOS日期")
    private Long   biosDate;

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

    public String getBiosVersion() {
        return biosVersion;
    }

    public void setBiosVersion(String biosVersion) {
        this.biosVersion = biosVersion;
    }

    public Long getBiosDate() {
        return biosDate;
    }

    public void setBiosDate(Long biosDate) {
        this.biosDate = biosDate;
    }
}