package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.service.IAssetTopologyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
}
