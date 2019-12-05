package com.antiy.asset.vo.query;

import com.antiy.common.base.ObjectQuery;
import com.antiy.common.encoder.Encode;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.validation.ObjectValidator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liulusheng
 * @since 2019/11/15
 */
public class AssetBaselinTemplateQuery extends ObjectQuery implements ObjectValidator {
    @Encode
    List<String> assetId;
    String baselineTemplateId;
    Long gmtModify;
    String modifyUser;

    public List<String> getAssetId() {
        return assetId;
    }

    public void setAssetId(List<String> assetId) {
        this.assetId = assetId;
    }

    public List<String> getBaselineTemplateId() {
        return assetId.stream().map(v -> baselineTemplateId).collect(Collectors.toList());
    }

    public void setBaselineTemplateId(String baselineTemplateId) {
        this.baselineTemplateId = baselineTemplateId;
    }

    public Long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public void validate() throws RequestParamValidateException {
        boolean compliance = true;
        if (assetId == null || assetId.isEmpty()) {
            compliance = false;
        }
        if (baselineTemplateId == null || baselineTemplateId.isEmpty()) {
            compliance = false;
        }
        if (!compliance) {
            throw new RequestParamValidateException("请确保资产和基准模板参数正确");
        }
    }
}
