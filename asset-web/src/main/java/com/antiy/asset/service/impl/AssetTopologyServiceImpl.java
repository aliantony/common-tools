package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.service.IAssetTopologyService;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.response.AssetNodeInfoResponse;

/**
 * 资产拓扑管理
 *
 * @author zhangxin
 * @date 2019/4/23 11:21
 */
@Service
public class AssetTopologyServiceImpl implements IAssetTopologyService {

    @Resource
    private AssetLinkRelationDao assetLinkRelationDao;

    @Override
    public List<String> queryCategoryModels() {
        return assetLinkRelationDao.queryCategoryModes();
    }

    @Override
    public AssetNodeInfoResponse queryAssetNodeInfo(String assetId) {
        AssetNodeInfoResponse assetNodeInfoResponse = assetLinkRelationDao.queryAssetNodeInfo(assetId);
        assetNodeInfoResponse
            .setInstallTypeName(InstallType.getInstallTypeByCode(assetNodeInfoResponse.getInstallType()).getStatus());
        return assetNodeInfoResponse;
    }
}
