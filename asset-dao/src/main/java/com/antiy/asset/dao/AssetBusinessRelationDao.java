package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.asset.vo.response.AssetBusinessResponse;
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

    AssetBusinessRelation getByUniqueIdAndAssetId(@Param("uniqueId") String uniqueId, @Param("assetId") Integer assetId);

    void deleteByUniqueIdAndAssetId(AssetBusinessRequest request);

    void updateBatch(@Param("editAsset") List<AssetBusinessRelationRequest> editAsset, @Param("uniqueId")String uniqueId);

    void deleteByUniqueId(String uniqueId);

    void updateSourceByassetId(String primaryKey);

    int deleteByAssetIdAndUniqueId(@Param("uniqueIds") List<String> uniqueIds, @Param("assetId") String assetId);

    int updateBatchInfluenceByAssetId(@Param("relationRequests") List<AssetBusinessRelationRequest> relationRequests,@Param("assetId") String assetId);

    int insertBatchRelation(List<AssetBusinessRelation> assetRelationList);

    void deleteByAssetId(List<String> assetIdList);
}
