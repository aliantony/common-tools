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

@Component
public class OperatingSystemClientImpl implements OperatingSystemClient {
    @Resource
    private BaseClient baseClient;

    /**
     * 获取操作系统列表url
     */
    @Value("${checkOperatingSystemUrl}")
    private String     getOperatingSystemUrl;

    @Override
    public ActionResponse getOperatingSystem(String checkStr) {
        CategoryByNameQuery categoryByNameQuery=new CategoryByNameQuery();
        categoryByNameQuery.setName(checkStr);
        return (ActionResponse) baseClient.post(categoryByNameQuery, new ParameterizedTypeReference<ActionResponse>() {
        }, getOperatingSystemUrl);
    }

    @Override
    public BaselineCategoryModelNodeResponse getInvokeOperatingSystem(String checkStr) {
        ActionResponse actionResponse = this.getOperatingSystem(checkStr);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return null;
        }
        return (BaselineCategoryModelNodeResponse) actionResponse.getBody();
    }
}
