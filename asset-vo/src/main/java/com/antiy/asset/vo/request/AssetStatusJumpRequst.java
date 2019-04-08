package com.antiy.asset.vo.request;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 13:42
 * @description:
 */
@ApiModel(value = "待配置变更资产状态")
public class AssetStatusJumpRequst extends BasicRequest   {

    @ApiModelProperty(value = "方案信息")
    @Valid
    private SchemeRequest              schemeRequest;

    /**
     * 资产主键
     */
    @Encode
    @ApiModelProperty("资产主键")
    @NotBlank(message = "资产主键不能为空")
    private String                     assetId;



    public SchemeRequest getSchemeRequest() {
        return schemeRequest;
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



}
