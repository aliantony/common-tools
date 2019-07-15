package com.antiy.asset.intergration.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.request.UpdateAssetVerifyRequest;
import com.antiy.common.base.ActionResponse;

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
        return (ActionResponse) baseClient.post(updateAssetVerifyRequest, new ParameterizedTypeReference<ActionResponse>() {
        }, updateAssetVerifyUrl);
    }

    @Override
    public ActionResponse distributeBaseline(String assetId) {
        return (ActionResponse) baseClient.post(new JSONObject().put("stringId", assetId),
            new ParameterizedTypeReference<ActionResponse>() {
        }, distributeBaselineUrl);
    }

}
