package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产软件关系信息 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSoftwareRelationDao extends IBaseDao<AssetSoftwareRelation> {

    /**
     * 通过资产ID查询关联软件信息
     *
     * @param assetId
     * @return
     */
    List<AssetSoftware> getSoftByAssetId(Integer assetId);

    /**
     * 通过软件ID统计资产数量
     *
     * @param id
     * @return
     */
    Integer countAssetBySoftId(Integer id);

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return
     */
    List<String> findOS() throws Exception;

    /**
     * 查询软件关联的硬件
     * @param softwareIds
     * @return
     */
    List<Map<String, Object>> countSoftwareRelAsset(@Param("softwareIds") List<Integer> softwareIds);

    /**
     * 
     * @param assetId
     * @param softwareId
     * @return
     */
    Integer deleteSoftwareRelAsset(@Param("assetId") Integer assetId, @Param("softwareId") Integer softwareId);
}
