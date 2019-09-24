package com.antiy.asset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetAssembly;
import com.antiy.common.base.IBaseDao;

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
}
