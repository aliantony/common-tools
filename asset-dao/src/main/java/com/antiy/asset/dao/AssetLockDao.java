package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLock;
import com.antiy.common.base.IBaseDao;

public interface AssetLockDao extends IBaseDao<AssetLock> {

    AssetLock getByAssetId(Integer assetId);
    void deleteByAssetId(Integer assetId);
}
