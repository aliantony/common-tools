package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetCpuRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetCpuRequest extends BasicRequest implements ObjectValidator {

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
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "序列号长度不能超过32位", max = 32)
    private String  serial;
    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    @NotBlank(message = "品牌不能为空")
    @Size(message = "品牌长度不能超过32位", max = 32)
    private String  brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @Size(message = "型号长度不能超过32位", max = 32)
    private String  model;
    /**
     * CPU主频
     */
    @ApiModelProperty("CPU主频")
    @NotNull(message = "CPU主频不能为空")
    private Float   mainFrequency;
    /**
     * 线程数
     */
    @ApiModelProperty("线程数")
    private Integer threadSize;
    /**
     * 核心数
     */
    @ApiModelProperty("核心数")
    private Integer coreSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Override
    public String toString() {
        return "AssetCpuRequest{" + "id='" + id + '\'' + ", assetId='" + assetId + '\'' + ", serial='" + serial + '\''
               + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", mainFrequency=" + mainFrequency
               + ", threadSize=" + threadSize + ", coreSize=" + coreSize + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}