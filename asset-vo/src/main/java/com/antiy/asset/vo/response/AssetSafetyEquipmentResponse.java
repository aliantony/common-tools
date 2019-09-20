package com.antiy.asset.vo.response;

import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetSafetyEquipmentResponse 响应对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class AssetSafetyEquipmentResponse extends BaseResponse {

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
    private String featureLibrary;

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

    @ApiModelProperty("命令与控制通道检测引擎版本号")
    private String commandControlChannel;
    @ApiModelProperty("是否纳入管理")
    private String isManage;
    @ApiModelProperty("软件版本")
    private String newVersion;

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

    public String getCommandControlChannel() {
        return commandControlChannel;
    }

    public void setCommandControlChannel(String commandControlChannel) {
        this.commandControlChannel = commandControlChannel;
    }

    public String getIsManage() {
        return isManage;
    }

    public void setIsManage(String isManage) {
        this.isManage = isManage;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    @Override
    public String toString() {
        return "AssetSafetyEquipmentResponse{" +
                "assetId='" + assetId + '\'' +
                ", featureLibrary='" + featureLibrary + '\'' +
                ", strategy='" + strategy + '\'' +
                ", memo='" + memo + '\'' +
                ", commandControlChannel='" + commandControlChannel + '\'' +
                ", isManage='" + isManage + '\'' +
                ", newVersion='" + newVersion + '\'' +
                '}';
    }
}