package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import io.swagger.annotations.ApiModelProperty;

/**
 * 关联软件查询
 */
public class InstallQuery extends ObjectQuery implements ObjectValidator {

    @ApiModelProperty(value = "资产id", required = true)
    @Encode
    private String  assetId;
    /**
     * 配置基准模板
     */
    @ApiModelProperty(value = "基准模板id", required = true)
    @Encode
    private String  baselineTemplateId;
    /**
     * 是否是批量操作
     */
    @ApiModelProperty(value = "是否是批量操作", required = true)
    private boolean isBatch;

    public boolean getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(boolean batch) {
        isBatch = batch;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getBaselineTemplateId() {
        return baselineTemplateId;
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public void setBatch(boolean batch) {
        isBatch = batch;
    }

    @Override
    public void validate() throws RequestParamValidateException {
    }
}
