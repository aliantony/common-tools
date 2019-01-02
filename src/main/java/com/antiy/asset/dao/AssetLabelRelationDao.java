package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.dto.AssetLabelRelationDTO;
import com.antiy.asset.vo.query.AssetLabelRelationQuery;

/**
 * <p>
 * 资产标签关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetLabelRelationDao extends IBaseDao<AssetLabelRelation> {

    List<AssetLabelRelationDTO> findListAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;
}
