package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 通联关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetLinkRelationDao extends IBaseDao<AssetLinkRelation> {

    List<AssetLinkRelation> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;
}
