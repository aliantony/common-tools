package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 资产软件关系信息 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSoftwareRelationDao extends IBaseDao<AssetSoftwareRelation> {

    List<AssetSoftwareRelation> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;
}
