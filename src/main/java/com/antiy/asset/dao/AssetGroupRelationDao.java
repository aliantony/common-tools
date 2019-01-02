package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.dto.AssetGroupRelationDTO;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;

/**
 * <p>
 * 资产与资产组关系表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetGroupRelationDao extends IBaseDao<AssetGroupRelation> {

    List<AssetGroupRelationDTO> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;
}
