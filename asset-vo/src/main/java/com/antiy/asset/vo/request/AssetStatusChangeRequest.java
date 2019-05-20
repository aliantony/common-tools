package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.antiy.common.base.BasicRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther: zhangyajun
 * @date: 2019/5/20 15:16
 * @description:
 */
@ApiModel(value = "硬件资产不予登记")
public class AssetStatusChangeRequest extends BasicRequest implements ObjectValidator {

    @ApiModelProperty(value = "是否为软件,false为硬件，true为软件，默认false", allowableValues = "false,true")
    @NotNull(message = "软件或者硬件判断条件不能为空")
    private Boolean software = false;

    /**
     * 资产主键
     */
    @Encode
    @ApiModelProperty("资产主键")
    @NotBlank(message = "资产主键不能为空")
    private String  assetId;

    @ApiModelProperty("状态")
    @NotBlank(message = "状态不能为空")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSoftware() {
        return software;
    }

    public void setSoftware(Boolean software) {
        this.software = software;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetId() {
        return assetId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }
}
