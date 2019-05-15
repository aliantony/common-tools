package com.antiy.asset.vo.query;

import com.antiy.asset.vo.enums.AssetTypeEnum;
import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> Scheme 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */
public class SchemeQuery extends ObjectQuery implements ObjectValidator {

    /**
     * 类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）
     */
    @ApiModelProperty("类型（1.准入实施、2.效果检查、3.制定待退役方案、4.验证退役方案、5.实施退役方案）")
    private Integer type;

    /**
     * 实施人
     */
    @ApiModelProperty("实施人")
    private String  putintoUser;
    /**
     * 实施人
     */
    @Encode
    @ApiModelProperty("资产id")
    private String assetId;

    @ApiModelProperty("资产状态")
    private Integer       assetStatus;

    @ApiModelProperty("类型")
    private AssetTypeEnum assetTypeEnum;

    @ApiModelProperty(value = "操作时间", hidden = true)
    private Long          operationTime;

    @Override
    public void validate() throws RequestParamValidateException {

    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public AssetTypeEnum getAssetTypeEnum() {
        return assetTypeEnum;
    }

    public void setAssetTypeEnum(AssetTypeEnum assetTypeEnum) {
        this.assetTypeEnum = assetTypeEnum;
    }

    public Integer getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(Integer assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getPutintoUser() {
        return putintoUser;
    }

    public void setPutintoUser(String putintoUser) {
        this.putintoUser = putintoUser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SchemeQuery{" + "type=" + type + ", putintoUser='" + putintoUser + '\'' + ", assetId='" + assetId + '\''
               + ", assetStatus=" + assetStatus + ", assetTypeEnum=" + assetTypeEnum + '}';
    }
}