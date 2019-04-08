package com.antiy.asset.intergration.impl;

import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.query.CategoryByNameQuery;
import com.antiy.asset.vo.response.BaselineCategoryModelNodeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class OperatingSystemClientImpl implements OperatingSystemClient {
    @Resource
    private BaseClient baseClient;

    /**
     * 获取操作系统列表url
     */
    @Value("${getOperatingSystemUrl}")
    private String     getOperatingSystemUrl;

    @Override
    public ActionResponse getOperatingSystem() {
        return (ActionResponse) baseClient.post(null, new ParameterizedTypeReference<ActionResponse>() {
        }, getOperatingSystemUrl);
    }

    @Override
    public List<Map> getInvokeOperatingSystem() {
        ActionResponse actionResponse = this.getOperatingSystem();
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return null;
        }
        return (List<Map>) actionResponse.getBody();
    }
}
