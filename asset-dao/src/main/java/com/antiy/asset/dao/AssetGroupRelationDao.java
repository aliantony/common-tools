package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 资产与资产组关系表 Mapper 接口
 * </p>
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


    /**
     * 删除资产的资产组关系
     * @param id
     * @return
     */
    Integer deleteByAssetId(Integer id);
    /**
     * 通过资产组ID删除关系
     * @param id
     * @return
     */
    Integer deleteByAssetGroupId(Integer id);

    /**
     * 插入资产
     * @param assetGroupRelations
     * @return
     */
    Integer insertBatch(List<AssetGroupRelation> assetGroupRelations);
}
