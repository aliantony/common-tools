package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.vo.query.AssetMemoryQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 内存表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetMemoryDao extends IBaseDao<AssetMemory> {

    List<AssetMemory> findListAssetMemory(AssetMemoryQuery query) throws Exception;

    void updateBatch(List<AssetMemory> assetMemoryList);
}
