package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.vo.query.AssetLabelRelationQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 资产标签关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetLabelRelationDao extends IBaseDao<AssetLabelRelation> {

    List<AssetLabelRelation> findListAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;
}
