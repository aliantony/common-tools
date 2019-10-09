package com.antiy.asset.intergration.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.request.BaseLineTemplateRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.asset.vo.request.UpdateAssetVerifyRequest;
import com.antiy.common.base.ActionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author: zhangbing
 * @date: 2019/4/4 16:23
 * @description:
 */
@Component
public class BaseLineClientImpl implements BaseLineClient {

    @Value("${configRegisterUrl}")
    private String     configRegisterUrl;

    @Value("${updateAssetVerifyUrl}")
    private String     updateAssetVerifyUrl;

    @Value("${distribute.baseline}")
    private String     distributeBaselineUrl;
    @Value("${baseline.template}")
    private String     getTemplateUrl;

    @Value("${baselineWaitingConfigUrl}")
    private String     baselineWaitingConfigUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    @AssetLog(description = "登记硬件配置", operationType = AssetLogOperationType.ADD)
    public ActionResponse configRegister(List<ConfigRegisterRequest> request) {
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, configRegisterUrl);
    }

    @Override
    @AssetLog(description = "修改资产到待验证")
    public ActionResponse updateAssetVerify(String assetId) {
        UpdateAssetVerifyRequest updateAssetVerifyRequest = new UpdateAssetVerifyRequest();
        updateAssetVerifyRequest.setAssetId(assetId);
        return (ActionResponse) baseClient.post(updateAssetVerifyRequest,
            new ParameterizedTypeReference<ActionResponse>() {
            }, updateAssetVerifyUrl);
    }

    @Override
    @AssetLog(description = "下载基准模板")
    public File getTemplate(List<String> assetIdList) {
        BaseLineTemplateRequest baseLineTemplateRequest = new BaseLineTemplateRequest();
        baseLineTemplateRequest.setCheckType(2);
        baseLineTemplateRequest.setAssetIdList(assetIdList);
        return (File) baseClient.get(baseLineTemplateRequest, new ParameterizedTypeReference<ActionResponse>() {
        }, getTemplateUrl);
    }

    @Override
    public ActionResponse distributeBaseline(String assetId) {
        JSONObject param = new JSONObject();
        param.put("stringId", assetId);
        return (ActionResponse) baseClient.post(param, new ParameterizedTypeReference<ActionResponse>() {
        }, distributeBaselineUrl);
    }

    @Override
    public ActionResponse baselineConfig(BaselineWaitingConfigRequest baselineWaitingConfigRequest) {
        return (ActionResponse) baseClient.post(baselineWaitingConfigRequest,
            new ParameterizedTypeReference<ActionResponse>() {
            }, baselineWaitingConfigUrl);
    }

}
