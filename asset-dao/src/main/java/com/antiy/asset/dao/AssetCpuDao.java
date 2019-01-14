package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.vo.query.AssetCpuQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 处理器表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetCpuDao extends IBaseDao<AssetCpu> {

    List<AssetCpu> findListAssetCpu(AssetCpuQuery query) throws Exception;

    void updateBatch(List<AssetCpu> assetCpuList);
}
