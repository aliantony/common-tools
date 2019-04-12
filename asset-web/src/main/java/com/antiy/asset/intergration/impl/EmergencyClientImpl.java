package com.antiy.asset.intergration.impl;

import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.common.base.ActionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资产告警数量获取
 * @author lvliang
 */
@Service
public class EmergencyClientImpl implements EmergencyClient {
    @Value("${emergencyClientUrl}")
    private String     emergencyClientUrl;

    @Resource
    private BaseClient baseClient;

    @Override
    public ActionResponse queryEmergencyCount(List<Integer> assetIds) {
        return (ActionResponse) baseClient.post(assetIds, new ParameterizedTypeReference<ActionResponse>() {
        }, emergencyClientUrl);
    }
}
