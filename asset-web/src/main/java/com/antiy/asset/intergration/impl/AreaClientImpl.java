package com.antiy.asset.intergration.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhangyajun
 * @create 2019-02-21 15:52
 **/
@Component
public class AreaClientImpl implements AreaClient {
    @Resource
    private BaseClient baseClient;

    @Value("${areaUrl}")
    private String     areaUrl;
    @Value("${cdeAndAreaId}")
    private String     cdeAndAreaId;
    @Value("${verifyIP}")
    private String     verifyIP;
    @Value("${TypeAreaId}")
    private String     TypeAreaId;


    @Override
    public ActionResponse queryAreaById(String id) {
        return (ActionResponse) baseClient.post(null, new ParameterizedTypeReference<ActionResponse>() {
        }, areaUrl + id);
    }



    @Override
    public ActionResponse queryCdeAndAreaId(String code) {
        JSONObject object = new JSONObject();
        object.put("roleCode", code);
        return (ActionResponse) baseClient.post(object, new ParameterizedTypeReference<ActionResponse>() {
        }, cdeAndAreaId);
    }

    @Override
    public ActionResponse queryByArea(String type, String areaId) {
        JSONObject object = new JSONObject();
        object.put("tag", type);
        object.put("areaId", areaId);
        return (ActionResponse) baseClient.post(object, new ParameterizedTypeReference<ActionResponse>() {
        }, TypeAreaId);
    }

    @Override
    public ActionResponse getIP(String areaId, String ip, String typeId) {
        JSONObject object = new JSONObject();
        object.put("areaId", areaId);
        object.put("ip", ip);
        object.put("typeId", typeId);
        return (ActionResponse) baseClient.post(object, new ParameterizedTypeReference<ActionResponse>() {
        }, verifyIP);
    }

    @Override
    public Object getInvokeResult(String id) {
        ActionResponse actionResponse = this.queryAreaById(id);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        }
        return actionResponse.getBody();
    }
}
