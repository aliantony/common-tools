package com.antiy.asset.intergration.impl;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.common.base.ActionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 资产告警数量获取
 * @author lvliang
 */
@Component
public class EmergencyClientImpl implements EmergencyClient {
    @Value("${emergencyClientUrl}")
    private String     emergencyClientUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    @AssetLog(description = "远程调用获取资产告警数量")
    public ActionResponse<List<IdCount>> queryEmergencyCount(List<Integer> assetIds) {
        return (ActionResponse) baseClient.post(assetIds, new ParameterizedTypeReference<ActionResponse>() {
        }, emergencyClientUrl);
    }
}
