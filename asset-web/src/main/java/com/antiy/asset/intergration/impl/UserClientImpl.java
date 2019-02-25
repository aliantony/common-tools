package com.antiy.asset.intergration.impl;

import javax.annotation.Resource;

import com.antiy.asset.intergration.UserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;

/**
 * @author zhangyajun
 * @create 2019-02-21 15:52
 **/
@Component
public class UserClientImpl implements UserClient {
    @Resource
    private BaseClient baseClient;

    @Value("${userUrl}")
    private String     userUrl;

    @Override
    public ActionResponse queryUserById(String id) {
        return (ActionResponse) baseClient.get(null, new ParameterizedTypeReference<ActionResponse>() {
        }, userUrl + id);
    }

    @Override
    public Object getInvokeResult(String id) {
        ActionResponse actionResponse = this.queryUserById(id);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        }
        return actionResponse.getBody();
    }
}
