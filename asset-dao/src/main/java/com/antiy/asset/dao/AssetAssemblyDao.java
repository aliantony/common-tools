package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.vo.request.AssetAssemblyRequest;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 资产组件关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetAssemblyDao extends IBaseDao<AssetAssembly> {
    /**
     * 删除资产与组件关系
     * @param assetId
     */
    void deleteAssemblyRelation(@Param("assetId") String assetId);

    /**
     * 批量插入资产与组件关系
     * @param assetAssemblyList
     */
    void insertBatch(@Param("assetAssemblyList") List<AssetAssembly> assetAssemblyList);

    /**
     * 通过资产id查询已关联的组件id
     * @param assetId 资产id
     * @return
     */
    List<String> findAssemblyIds(String assetId);

    /**
     * 通过Bussessid查询的组件
     * @param businessId 资产id
     * @return
     */
    Integer findAssemblyByBusiness(String businessId);

    /**
     * 通过资产id查询组件
     * @param assetId
     * @param type
     * @return
     */
    List<AssetAssemblyRequest> findAssemblyByAssetId(@Param("assetId") String assetId, @Param("type") String type);

    Integer updateByUniqueId(AssetAssemblyRequest assetAssemblyRequest);
}
