package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetGroupDTO;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 资产组表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetGroupDao extends IBaseDao<AssetGroup> {

    List<AssetGroupDTO> findListAssetGroup(AssetGroupQuery query) throws Exception;
}
