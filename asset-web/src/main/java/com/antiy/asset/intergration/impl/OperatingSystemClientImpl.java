package com.antiy.asset.intergration.impl;

import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.query.BaselineCategoryModelQuery;
import com.antiy.asset.vo.query.CategoryByNameQuery;
import com.antiy.asset.vo.redis.CategoryOsResponse;
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
    @Value("${getOperatingSystemListUrl}")
    private String     getOperatingSystemListUrl;

    @Value("${getOperatingSystemTreeUrl}")
    private String     getOperatingSystemTreeUrl;

    /**
     * 获取操作系统列表，非树
     * @return
     */
    @Override
    @AssetLog(description = "获取操作系统列表", operationType = AssetLogOperationType.QUERY)
    public ActionResponse<List<CategoryOsResponse>> getOperatingSystem() {
        return (ActionResponse) baseClient.post(null, new ParameterizedTypeReference<ActionResponse<List<CategoryOsResponse>>>() {
        }, getOperatingSystemListUrl);
    }

    @Override
    @AssetLog(description = "获取操作系统列表", operationType = AssetLogOperationType.QUERY)
    public List<CategoryOsResponse> getInvokeOperatingSystem() {
        ActionResponse<List<CategoryOsResponse>> actionResponse = this.getOperatingSystem();
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return null;
        }
        return  actionResponse.getBody();
    }

    /**
     * 获取操作系统列表，树
     * @return
     */
    @Override
    @AssetLog(description = "获取操作系统树", operationType = AssetLogOperationType.QUERY)
    public ActionResponse<List<BaselineCategoryModelNodeResponse>> getOperatingSystemTree() {
        BaselineCategoryModelQuery baselineCategoryModelQuery = new BaselineCategoryModelQuery();
        baselineCategoryModelQuery.setType(2);
        return (ActionResponse) baseClient.post(baselineCategoryModelQuery,
            new ParameterizedTypeReference<ActionResponse<List<BaselineCategoryModelNodeResponse>>>() {
            }, getOperatingSystemTreeUrl);
    }

    @Override
    @AssetLog(description = "获取操作系统树", operationType = AssetLogOperationType.QUERY)
    public List<BaselineCategoryModelNodeResponse> getInvokeOperatingSystemTree() {
        ActionResponse actionResponse = this.getOperatingSystemTree();
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return null;
        }
        return (List<BaselineCategoryModelNodeResponse>) actionResponse.getBody();
    }

}
