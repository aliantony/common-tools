package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
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
     * IP
     */
    @ApiModelProperty("IP")
    @NotBlank(message = "不能为空")
    @Size(message = "ip不能大于30个字符",  max = 30)
    private String ip;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    @NotBlank(message = "mac不能为空")
    @Size(message = "mac不能大于30个字符",  max = 30)
    private String mac;
    /**
     * 特征库版本
     */
    @ApiModelProperty("特征库版本")
    @Pattern(regexp = "^[+]?\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$", message = "特征库版本格式不正确")
    @Size(message = "特征库版本不能大于30个字符",  max = 30)
    private String featureLibrary;

    @ApiModelProperty("软件版本")
    @Size(message = "软件版本不能大于30个字符",  max = 30)
    @Pattern(regexp = "^[+]?\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$", message = "软件版本格式不正确")
    private String newVersion;

    /**
     * 策略配置
     */
    @ApiModelProperty("策略配置")
    private String strategy;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AssetSafetyEquipmentRequest{" + "id='" + id + '\'' + ", assetId='" + assetId + '\'' + ", ip='" + ip
               + '\'' + ", mac='" + mac + '\'' + ", featureLibrary='" + featureLibrary + '\'' + ", strategy='"
               + strategy + '\'' + ", memo='" + memo + '\'' + '}';
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
}