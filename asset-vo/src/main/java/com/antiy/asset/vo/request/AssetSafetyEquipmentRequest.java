package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * <p> AssetSafetyEquipmentRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSafetyEquipmentRequest extends BasicRequest implements ObjectValidator {

    @Encode
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @Encode
    private String assetId;

    /**
     * 特征库版本
     */
    @ApiModelProperty("特征库版本")
    @Pattern(regexp = "^[+]?\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$", message = "特征库版本格式不正确")
    @Size(message = "特征库版本不能大于30个字符", max = 30)
    private String featureLibrary;

    @ApiModelProperty("软件版本")
    @Size(message = "软件版本不能大于30个字符", max = 30)
    @Pattern(regexp = "^[+]?\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$", message = "软件版本格式不正确")
    private String newVersion;

    /**
     * 策略配置
     */
    @ApiModelProperty("策略配置")
    private String strategy;


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

    public String getFeatureLibrary() {
        return featureLibrary;
    }

    public void setFeatureLibrary(String featureLibrary) {
        this.featureLibrary = featureLibrary;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }


    @Override
    public void validate() throws RequestParamValidateException {

    }

    @Override
    public String toString() {
        return "AssetSafetyEquipmentRequest{" + "id='" + id + '\'' + ", assetId='" + assetId + '\''
               + ", featureLibrary='" + featureLibrary + '\'' + ", newVersion='" + newVersion + '\'' + ", strategy='"
               + strategy + '\'' + ", memo='" + +'\'' + '}';
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
}