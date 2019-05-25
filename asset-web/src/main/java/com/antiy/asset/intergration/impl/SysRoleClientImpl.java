package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.intergration.SysUserClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;

/**
 * @author zhangyajun
 * @create 2019-02-21 15:52
 **/
@Component
public class SysRoleClientImpl implements SysUserClient {
    @Resource
    private BaseClient baseClient;

    @Value("${userRoleUrl}")
    private String     userRoleUrl;

    @Override
    public ActionResponse queryUserRoleById(String id) {
        return (ActionResponse) baseClient.post(null, new ParameterizedTypeReference<ActionResponse>() {
        }, userRoleUrl + id);
    }

    @Override
    public Object getInvokeResult(String id) {
        ActionResponse actionResponse = this.queryUserRoleById(id);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        }
        return actionResponse.getBody();
    }
}
