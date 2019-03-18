package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.vo.query.AssetMemoryQuery;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 内存表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetMemoryDao extends IBaseDao<AssetMemory> {

    List<AssetMemory> findListAssetMemory(AssetMemoryQuery query) throws Exception;

    Integer updateBatch(@Param(value = "list") List<AssetMemory> assetMemoryList) throws Exception;

    Integer insertBatch(List<AssetMemory> assetMemoryList);

    Integer deleteByAssetId(Integer id);

    List<AssetMemory> findMemoryByAssetId(Integer assestId);
}
