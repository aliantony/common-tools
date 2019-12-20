package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLock;

public interface AssetLockDao  {

    AssetLock getByAssetId(Integer assetId);
    void deleteByAssetId(Integer assetId);
    void insert(AssetLock assetLock);
}
