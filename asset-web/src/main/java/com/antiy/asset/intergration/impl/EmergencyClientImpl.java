package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import com.antiy.asset.vo.query.AlarmAssetIdQuery;
import com.antiy.common.base.RespBasicCode;
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
import com.antiy.common.base.PageResult;

import java.util.LinkedHashMap;
import java.util.List;

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
    public ActionResponse<PageResult<IdCount>> queryEmergencyCount(ObjectQuery objectQuery) {
        return (ActionResponse) baseClient.post(objectQuery,
            new ParameterizedTypeReference<ActionResponse<PageResult<IdCount>>>() {
            }, emergencyClientUrl);
    }

    @Override
    @AssetLog(description = "远程调用获取资产告警数量")
    public PageResult<IdCount> queryInvokeEmergencyCount(ObjectQuery objectQuery) {
        ActionResponse<PageResult<IdCount>> actionResponse = this.queryEmergencyCount(objectQuery);
        if (null == actionResponse || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return null;
        }
        return actionResponse.getBody();
    }

    @Override
    @AssetLog(description = "查询告警资产的总个数")
    public ActionResponse<AlarmAssetIdResponse> queryEmergecyAllCount() {
        return (ActionResponse) baseClient.post(null,
            new ParameterizedTypeReference<ActionResponse<AlarmAssetIdResponse>>() {
            }, emergencyCountClientUrl);
    }
}
