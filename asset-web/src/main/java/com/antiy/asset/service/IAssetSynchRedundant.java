package com.antiy.asset.service;

import com.antiy.asset.vo.query.AssetSynchCpeQuery;

/**
 * @author zhangyajun
 * @create 2019-10-14 16:44
 **/
public interface IAssetSynchRedundant {
    /**
     * 同步更新冗余字段
     * @param query
     */
    void synchRedundantAsset(AssetSynchCpeQuery query) throws Exception;
}
