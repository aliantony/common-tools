package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zhangbing
 * @date: 2019/4/14 13:29
 * @description:
 */
public class AssetRelationSoftRequest extends BaseRequest {

    @ApiModelProperty(value = "硬件资产Id", required = true)
    @NotBlank(message = "硬件资产Id不能能为空")
    @Encode(message = "硬件资产Id解密失败")
    private String assetId;

    @ApiModelProperty(value = "软件资产Id")
    @NotBlank(message = "软件资产Id不能为空")
    @Encode(message = "软件资产Id解密失败")
    private String softId;

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
