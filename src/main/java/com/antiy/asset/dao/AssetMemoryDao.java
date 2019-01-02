package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetMemoryDTO;
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
 * @since 2018-12-29
 */
public interface AssetMemoryDao extends IBaseDao<AssetMemory> {

    List<AssetMemoryDTO> findListAssetMemory(AssetMemoryQuery query) throws Exception;
}
