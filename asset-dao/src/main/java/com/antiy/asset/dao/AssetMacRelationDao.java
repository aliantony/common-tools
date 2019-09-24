package com.antiy.asset.dao;

import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetMacRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 资产-MAC关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetMacRelationDao extends IBaseDao<AssetMacRelation> {
    /**
     * 删除旧的资产mac关系
     * @param assetId
     */
    void deleteByAssetId(@Param("assetId") String assetId);

    /**
     * 批量保存资产mac关系
     * @param assetMacRelationList
     */
    void insertBatch(@Param("assetMacRelationList") List<AssetMacRelation> assetMacRelationList);
}