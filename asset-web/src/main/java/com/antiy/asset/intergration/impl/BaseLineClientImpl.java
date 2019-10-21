package com.antiy.asset.intergration.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.request.BaselineAssetRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.asset.vo.request.UpdateAssetVerifyRequest;
import com.antiy.common.base.ActionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Value("${baseline.check}")
    private String     baselineCheckUrl;
    @Value("${baseline.checkNoUUID}")
    private String     baselineCheckUrlNoUUID;

    @Value("${baselineWaitingConfigUrl}")
    private String     baselineWaitingConfigUrl;
    @Value("${scanUrl}")
    private String     scanUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    @AssetLog(description = "登记硬件配置", operationType = AssetLogOperationType.ADD)
    public ActionResponse configRegister(List<ConfigRegisterRequest> request) {
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, configRegisterUrl);
    }

    @Override
    @AssetLog(description = "登记安全检查", operationType = AssetLogOperationType.ADD)
    public ActionResponse baselineCheck(BaselineAssetRegisterRequest request) {
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, baselineCheckUrl);
    }

    @Override
    @AssetLog(description = "登记安全检查 安全检查. 没有 uuid", operationType = AssetLogOperationType.ADD)
    public ActionResponse baselineCheckNoUUID(BaselineAssetRegisterRequest request) {
        return (ActionResponse) baseClient.post(request, new ParameterizedTypeReference<ActionResponse>() {
        }, baselineCheckUrlNoUUID);
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
    public ActionResponse distributeBaseline(String assetId) {
        JSONObject param = new JSONObject();
        param.put("stringId", assetId);
        return (ActionResponse) baseClient.post(param, new ParameterizedTypeReference<ActionResponse>() {
        }, distributeBaselineUrl);
    }

    @Override
    public ActionResponse scan(String assetId) {
        JSONObject param = new JSONObject();
        param.put("assetId", assetId);
        return (ActionResponse) baseClient.post(param, new ParameterizedTypeReference<ActionResponse>() {
        }, scanUrl);
    }

    @Override
    public ActionResponse baselineConfig(List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList) {
        return (ActionResponse) baseClient.post(baselineWaitingConfigRequestList,
            new ParameterizedTypeReference<ActionResponse>() {
            }, baselineWaitingConfigUrl);
    }

}
