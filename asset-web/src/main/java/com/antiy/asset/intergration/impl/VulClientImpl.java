package com.antiy.asset.intergration.impl;

import com.antiy.asset.intergration.VulClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.request.AssetIdRequest;
import com.antiy.asset.vo.response.AssetVulPatchResponse;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class VulClientImpl implements VulClient {
    @Resource
    private BaseClient baseClient;

    @Value("${getPatchVulCountUrl}")
    private String     getPatchVulCountUrl;

    @Override
    public AssetVulPatchResponse getVulPatchCount(AssetIdRequest assetIdRequest, String token) {
        ActionResponse<AssetVulPatchResponse> actionResponse = (ActionResponse) baseClient.post(assetIdRequest,
            new ParameterizedTypeReference<ActionResponse<AssetVulPatchResponse>>() {
            }, getPatchVulCountUrl, token);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            throw new BusinessException("调用远程接口失败");
        }
        return actionResponse.getBody();
    }
}
