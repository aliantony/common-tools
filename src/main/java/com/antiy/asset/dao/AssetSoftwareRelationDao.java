package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.entity.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.entity.vo.response.AssetSoftwareRelationResponse;

/**
 * <p>
 * 资产软件关系信息 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface AssetSoftwareRelationDao extends IBaseDao<AssetSoftwareRelation> {

    List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;
}
