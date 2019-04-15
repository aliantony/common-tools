package com.antiy.asset.intergration.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.response.AlarmAssetIdResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.ObjectQuery;

/**
 * 资产告警数量获取
 * @author lvliang
 */
@Component
public class EmergencyClientImpl implements EmergencyClient {
    @Value("${emergencyClientUrl}")
    private String     emergencyClientUrl;

    @Value("${emergencyCountClientUrl}")
    private String     emergencyCountClientUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    @AssetLog(description = "远程调用获取资产告警数量")
    public ActionResponse<List<IdCount>> queryEmergencyCount(ObjectQuery objectQuery) {
        return (ActionResponse) baseClient.post(objectQuery, new ParameterizedTypeReference<ActionResponse>() {
        }, emergencyClientUrl);
    }

    @Override
    @AssetLog(description = "查询告警资产的总个数")
    public ActionResponse<AlarmAssetIdResponse> queryEmergecyAllCount() {
        return (ActionResponse) baseClient.post(null, new ParameterizedTypeReference<ActionResponse>() {
        }, emergencyClientUrl);
    }
}
