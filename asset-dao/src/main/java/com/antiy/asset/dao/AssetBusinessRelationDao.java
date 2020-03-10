package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
public interface AssetBusinessRelationDao extends IBaseDao<AssetBusinessRelation> {

    AssetBusinessRelation getByAssetId(Integer assetId);

    void insertBatch(@Param("assetRelationList") List<AssetBusinessRelation> assetRelationList);
   List<AssetBusinessResponse> getBusinessInfoByAssetId(@Param("assetId") String assetId);

    List<AssetBusinessRelationResponse> queryAssetByBusinessId(AssetAddOfBusinessQuery assetAddOfBusinessQuery);

    Integer countAssetByBusinessId(AssetAddOfBusinessQuery assetAddOfBusinessQuery);

    AssetBusinessRelation getByUniqueIdAndAssetId(String uniqueId, Integer assetId);

    void deleteByUniqueIdAndAssetId(@Param("deleteAssetList") List<AssetBusinessRelationRequest> deleteAssetList,@Param("uniqueId")String uniqueId);

    void updateBatch(@Param("editAsset") List<AssetBusinessRelationRequest> editAsset, @Param("uniqueId")String uniqueId);

    void deleteByUniqueId(String uniqueId);

    void updateSourceByassetId(String primaryKey);
}
