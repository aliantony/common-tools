package com.antiy.asset.intergration.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.intergration.SysUserClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.user.UserStatus;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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

    @Value("${loginUserInfoUrl}")
    private String     loginUserInfoUrl;

    @Value("${getUsersOfHaveRight}")
    private String getUsersOfHaveRight;
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

    @Override
    public UserStatus getLoginUserInfo(String userName) {
        return (UserStatus) baseClient.get(userName, new ParameterizedTypeReference<UserStatus>() {
        }, loginUserInfoUrl + userName);
    }

    @Override
    public ActionResponse<List<HashMap<String,String>>> getUsersOfHaveRight(List<String> tag) {
        JSONObject param = new JSONObject();
        param.put("qxTags", tag);
        return (ActionResponse<List<HashMap<String,String>>>) baseClient.post(param, new ParameterizedTypeReference<ActionResponse<List<HashMap<String,String>>>>() {
        }, getUsersOfHaveRight );

    }


}
