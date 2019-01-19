package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产与资产组关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetGroupRelationDao extends IBaseDao<AssetGroupRelation> {

    List<AssetGroupRelation> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;

    /**
     * 查询资产所有的资产组
     * @param id
     * @return
     */
    List<AssetGroup> queryByAssetId(Integer id);

    /**
     * 通过资产组ID查询资产信息
     * @param id
     * @return
     */
    List<String> findAssetByAssetGroupId(Integer id);

}
