package com.antiy.asset.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.asset.entity.AssetLabelRelation;
import com.antiy.asset.asset.entity.vo.query.AssetLabelRelationQuery;
import com.antiy.asset.asset.entity.vo.response.AssetLabelRelationResponse;

/**
 * <p>
 * 资产标签关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetLabelRelationDao extends IBaseDao<AssetLabelRelation> {

    List<AssetLabelRelationResponse> findListAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;
}
