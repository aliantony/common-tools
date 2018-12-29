package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.dto.AssetLinkRelationDTO;
import com.antiy.asset.entity.vo.query.AssetLinkRelationQuery;

/**
 * <p>
 * 通联关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetLinkRelationDao extends IBaseDao<AssetLinkRelation> {

    List<AssetLinkRelationDTO> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;
}
