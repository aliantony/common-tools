package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.asset.entity.vo.query.AssetMemoryQuery;
import com.antiy.asset.asset.entity.vo.response.AssetMemoryResponse;

/**
 * <p>
 * 内存表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetMemoryDao extends IBaseDao<AssetMemory> {

    List<AssetMemoryResponse> findListAssetMemory(AssetMemoryQuery query) throws Exception;
}
