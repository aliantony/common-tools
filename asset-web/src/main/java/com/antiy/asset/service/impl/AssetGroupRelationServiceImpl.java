package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> 资产与资产组关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetGroupRelationServiceImpl extends BaseServiceImpl<AssetGroupRelation> implements
                                                                                      IAssetGroupRelationService {
    @Resource
    private AssetGroupRelationDao assetGroupRelationDao;

    @Override
    public Integer hasRealtionAsset(String primaryKey) {
        return assetGroupRelationDao.hasRealtionAsset(primaryKey);
    }
}
