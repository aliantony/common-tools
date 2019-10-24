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
import com.antiy.asset.vo.request.BaselineAssetRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.common.base.ActionResponse;

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
