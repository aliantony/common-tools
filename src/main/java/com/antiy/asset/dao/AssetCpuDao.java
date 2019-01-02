package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetCpuDTO;
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
 * @since 2018-12-29
 */
public interface AssetCpuDao extends IBaseDao<AssetCpu> {

    List<AssetCpuDTO> findListAssetCpu(AssetCpuQuery query) throws Exception;
}
