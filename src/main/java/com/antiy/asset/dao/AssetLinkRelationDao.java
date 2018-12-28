package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.entity.vo.response.AssetLinkRelationResponse;

/**
 * <p>
 * 通联关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface AssetLinkRelationDao extends IBaseDao<AssetLinkRelation> {

    List<AssetLinkRelationResponse> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;
}
