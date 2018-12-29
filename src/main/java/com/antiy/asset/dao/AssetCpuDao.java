package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.entity.vo.query.AssetCpuQuery;
import com.antiy.asset.entity.vo.response.AssetCpuResponse;

/**
 * <p>
 * 处理器表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetCpuDao extends IBaseDao<AssetCpu> {

    List<AssetCpuResponse> findListAssetCpu(AssetCpuQuery query) throws Exception;
}
