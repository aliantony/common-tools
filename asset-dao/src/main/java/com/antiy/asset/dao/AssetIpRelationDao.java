package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetIpRelation;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 资产-IP关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetIpRelationDao extends IBaseDao<AssetIpRelation> {
    /**
     * 删除资产ip关系
     * @param assetId
     */
    void deleteByAssetId(String assetId);

    /**
     * 批量保存资产ip关系
     * @param assetIpRelationList
     */
    void insertBatch(@Param("assetIpRelationList") List<AssetIpRelation> assetIpRelationList);
}
