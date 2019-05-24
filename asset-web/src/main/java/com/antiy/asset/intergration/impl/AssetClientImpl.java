package com.antiy.asset.intergration.impl;

import com.antiy.asset.intergration.AssetClient;
import com.antiy.asset.util.BaseClient;
import com.antiy.asset.vo.request.AssetExternalRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.common.base.ActionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资产对外接口实现类
 * @Author: lvliang
 * @Date: 2019/5/16 13:35
 */
@Component
public class AssetClientImpl implements AssetClient {
    @Resource
    private BaseClient baseClient;

    @Value("${issueAssetUrl}")
    private String     issueAssetUrl;

    @Value("${issueSoftUrl}")
    private String     issueSoftUrl;

    @Override
    public ActionResponse issueAssetData(List<AssetExternalRequest> assetExternalRequestList) {
        return (ActionResponse) baseClient.post(assetExternalRequestList,
            new ParameterizedTypeReference<ActionResponse>() {
            }, issueAssetUrl);
    }

    @Override
    public ActionResponse issueSoftData(List<AssetSoftwareRequest> assetSoftwareRequestList) {
        return (ActionResponse) baseClient.post(assetSoftwareRequestList,
            new ParameterizedTypeReference<ActionResponse>() {
            }, issueSoftUrl);
    }
}
