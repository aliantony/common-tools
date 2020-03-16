package com.antiy.asset.intergration.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.request.BaselineAssetRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
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

    @Value("${baseline.check}")
    private String     baselineCheckUrl;
    @Value("${baseline.checkNoUUID}")
    private String     baselineCheckUrlNoUUID;

    @Value("${baselineWaitingConfigUrl}")
    private String     baselineWaitingConfigUrl;
    @Value("${scanUrl}")
    private String     scanUrl;
    @Value("${baselineTemplateUrl}")
    private String     baselineTemplateUrl;

    @Resource
    private BaseClient baseClient;

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
    @AssetLog(description = "漏洞扫描", operationType = AssetLogOperationType.ADD)
    public ActionResponse scan(String assetId) {
        JSONObject param = new JSONObject();
        param.put("assetId", assetId);
        return (ActionResponse) baseClient.post(param, new ParameterizedTypeReference<ActionResponse>() {
        }, scanUrl);
    }

    @Override
    @AssetLog(description = "获取基准模板", operationType = AssetLogOperationType.ADD)
    public ActionResponse getBaselineTemplate(String assetId) {
        JSONObject param = new JSONObject();
        param.put("primaryKey", assetId);
        return (ActionResponse) baseClient.post(param, new ParameterizedTypeReference<ActionResponse>() {
        }, baselineTemplateUrl);
    }

    @Override
    public ActionResponse baselineConfig(List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList) {
        return (ActionResponse) baseClient.post(baselineWaitingConfigRequestList,
            new ParameterizedTypeReference<ActionResponse>() {
            }, baselineWaitingConfigUrl);
    }

    @Override
    public ActionResponse removeAsset(List<Integer> assetIdList) {
        return null;
    }

    @Override
    public ActionResponse situationOfVul(String primaryKey) {

        return null;
    }

}
