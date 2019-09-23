package com.antiy.asset.vo.query;

import javax.validation.constraints.NotNull;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

public class InstallQuery extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty(value = "软件id", required = true)
    @NotNull
    @Encode
    private String assetId;
    /**
     * 配置基准模板
     */
    @ApiModelProperty(value = "基准模板id", required = true)
    @Encode
    private String installTemplateId;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getInstallTemplateId() {
        return installTemplateId;
    }

    public void setInstallTemplateId(String installTemplateId) {
        this.installTemplateId = installTemplateId;
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }
}
