package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * <p> AssetMainboradRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMainboradRequest extends BasicRequest implements ObjectValidator {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Encode
    private String id;
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
//    @NotBlank(message = "主板品牌不能为空")
    @Size(message = "主板品牌长度不能超过32位",max = 32)
    private String brand;
    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @Size(message = "主板型号长度不能超过32位",max = 32)
    private String model;
    /**
     * 序列号
     */
    @ApiModelProperty("序列号")
    @Size(message = "主板序列号长度不能超过32位",max = 32)
    private String serial;
    /**
     * BIOS版本
     */
    @ApiModelProperty("BIOS版本")
    @Size(message = "主板BIOS版本长度不能超过20位",max = 20)
    private String biosVersion;
    /**
     * BIOS日期
     */
    @ApiModelProperty("BIOS日期")
    private Long   biosDate;

    /**
     * 备注
     */
    private String memo;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    @Override
    public String toString() {
        return "AssetMainborad{" + ", assetId=" + assetId + ", brand=" + brand + ", model=" + model + ", serial="
               + serial + ", biosVersion=" + biosVersion + ", biosDate=" + biosDate + ", memo=" + memo + "}";
    }

}