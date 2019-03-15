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
    private String  assetId;
    /**
     * 特征库版本
     */
    @ApiModelProperty("特征库版本")
    private String  featureLibrary;
    /**
     * mac
     */
    @ApiModelProperty("mac")
    private String  mac;
    /**
     * 策略配置
     */
    @ApiModelProperty("策略配置")
    private String  strategy;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String  memo;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;
    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private Integer modifyUser;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Long    gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Long    gmtModified;

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

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}