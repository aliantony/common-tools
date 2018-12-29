package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.vo.query.AssetGroupQuery;
import com.antiy.asset.entity.vo.response.AssetGroupResponse;

/**
 * <p>
 * 资产组表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetGroupDao extends IBaseDao<AssetGroup> {

    List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception;
}
