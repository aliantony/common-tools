package com.antiy.asset.intergration.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.antiy.asset.aop.AssetLog;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.enums.AssetLogOperationType;
import com.antiy.asset.vo.query.BaselineCategoryModelQuery;
import com.antiy.asset.vo.response.BaselineCategoryModelNodeResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

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

    @Resource
    private AesEncoder aesEncoder;

    /**
     * 获取操作系统列表，非树
     * @return
     */
    @Override
    @AssetLog(description = "获取操作系统列表", operationType = AssetLogOperationType.QUERY)
    public ActionResponse<List<LinkedHashMap>> getOperatingSystem() {
        return decode((ActionResponse) baseClient.post(null,
            new ParameterizedTypeReference<ActionResponse<List<LinkedHashMap>>>() {
            }, getOperatingSystemListUrl));
    }

    private ActionResponse<List<LinkedHashMap>> decode(ActionResponse<List<LinkedHashMap>> oldValue) {
        if (oldValue != null && oldValue.getBody() != null) {
            List<LinkedHashMap> newList = new ArrayList<LinkedHashMap>();
            for (LinkedHashMap linkedHashMap : oldValue.getBody()) {
                LinkedHashMap newMap = linkedHashMap;
                if (linkedHashMap.get("id") != null && StringUtils.isNotBlank(linkedHashMap.get("id").toString())) {
                    newMap.put("id", aesEncoder.decode(linkedHashMap.get("id").toString(),
                        LoginUserUtil.getLoginUser().getUsername()));
                }
                newList.add(newMap);
            }
        }
        return oldValue;
    }

    @Override
    @AssetLog(description = "获取操作系统列表", operationType = AssetLogOperationType.QUERY)
    public List<LinkedHashMap> getInvokeOperatingSystem() {
        ActionResponse<List<LinkedHashMap>> actionResponse = this.getOperatingSystem();
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return null;
        }
        return actionResponse.getBody();
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
