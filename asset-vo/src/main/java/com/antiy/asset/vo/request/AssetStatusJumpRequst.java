package com.antiy.asset.vo.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 13:42
 * @description:
 */
@ApiModel(value = "待配置变更资产状态")
public class AssetStatusJumpRequst extends BasicRequest {

    @ApiModelProperty(value = "方案信息")
    @Valid
    private SchemeRequest   schemeRequest;

    /**
     * 资产主键
     */
    @Encode
    @ApiModelProperty("资产主键")
    @NotBlank(message = "资产主键不能为空")
    private String          assetId;
    /**
     * 软件主键
     */
    @Encode
    @ApiModelProperty("软件主键")
    private String          softId;

    /**
     * 同意/拒绝
     */
    @ApiModelProperty(value = "同意/拒绝", allowableValues = "true/false", notes = "验证时必填")
    private Boolean         agree;

    /**
     * 资产状态
     */
    @NotNull(message = "资产状态不能为空")
    @ApiModelProperty(value = "资产状态")
    private AssetStatusEnum assetStatusEnum;

    public AssetStatusEnum getAssetStatusEnum() {
        return assetStatusEnum;
    }

    public void setAssetStatusEnum(AssetStatusEnum assetStatusEnum) {
        this.assetStatusEnum = assetStatusEnum;
    }

    public SchemeRequest getSchemeRequest() {
        return schemeRequest;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public void setSchemeRequest(SchemeRequest schemeRequest) {
        this.schemeRequest = schemeRequest;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getSoftId() {
        return softId;
    }

    public void setSoftId(String softId) {
        this.softId = softId;
    }
}
