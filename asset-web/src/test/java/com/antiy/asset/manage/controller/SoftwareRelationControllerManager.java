package com.antiy.asset.manage.controller;

import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author zhouye
 * @date 2019-04-11
 * 提供SoftworeRelation Controller用到的相关vo初始化
 * 注：业务上关心的字段传参，不关心的字段给默认值。
 */
@Component
public class SoftwareRelationControllerManager {
    /**
     * 初始化软件何资产关联vo对象
     *
     * @param assetId     资产id
     * @param softId      软件id
     * @param installType 安装方式
     * @return vo对象
     */
    public AssetSoftwareRelationRequest initRelationRequest(String assetId, String softId, Integer installType) {
        AssetSoftwareRelationRequest relationRequest = new AssetSoftwareRelationRequest();
        relationRequest.setAssetId(assetId);
        relationRequest.setSoftwareId(softId);
        relationRequest.setInstallType(installType);
        relationRequest.setInstallTime(System.currentTimeMillis());
        return relationRequest;
    }

    /**
     * @param assetId 资产id
     * @param softId  软件id
     * @param status  软件资产状态
     * @return vo对象
     */
    public AssetSoftwareRelationQuery initSoftwareRelationQuery(String assetId, String softId, Integer status) {
        AssetSoftwareRelationQuery relationQuery = new AssetSoftwareRelationQuery();
        relationQuery.setAssetId(assetId);
        relationQuery.setSoftwareId(softId);
        relationQuery.setSoftwareStatus(status);
        return relationQuery;
    }

    /**
     * @param softwareId  软件id
     * @param installType 安装类型
     * @return 关系列表vo
     */
    public AssetSoftwareRelationList initRelationList(String softwareId, Integer installType) {
        AssetSoftwareRelationList relationList = new AssetSoftwareRelationList();
        relationList.setSoftwareId(softwareId);
        relationList.setInstallType(installType);
        relationList.setAssetInstallRequestList(new ArrayList<>());
        return relationList;
    }
}
