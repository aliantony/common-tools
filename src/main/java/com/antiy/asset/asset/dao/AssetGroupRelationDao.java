package com.antiy.asset.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.asset.entity.AssetGroupRelation;
import com.antiy.asset.asset.entity.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.asset.entity.vo.response.AssetGroupRelationResponse;

/**
 * <p>
 * 资产与资产组关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetGroupRelationDao extends IBaseDao<AssetGroupRelation> {

    List<AssetGroupRelationResponse> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;
}
