package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.service.IAssetTopologyService;

/**
 * 资产拓扑管理
 * @author zhangxin
 * @date 2019/4/23 11:21
 */
@Service
public class AssetTopologyServiceImpl implements IAssetTopologyService {

    @Resource
    private AssetLinkRelationDao assetLinkRelationDao;

}
