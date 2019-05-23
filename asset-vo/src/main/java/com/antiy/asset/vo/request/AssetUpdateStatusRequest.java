package com.antiy.asset.vo.request;

import javax.validation.constraints.NotBlank;

import com.antiy.common.base.BaseRequest;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p> AssetStatusTaskRequest 请求对象 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetUpdateStatusRequest extends BaseRequest implements ObjectValidator {
    /**
     * 资产主键
     */
    @ApiModelProperty("资产主键")
    @NotBlank
    @Encode
    private String assetId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    @Override
    public void validate() throws RequestParamValidateException {

    }

}