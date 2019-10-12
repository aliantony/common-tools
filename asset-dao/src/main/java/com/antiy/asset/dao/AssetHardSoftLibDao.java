package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.common.base.IBaseDao;
import com.antiy.common.base.ObjectQuery;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * <p> CPE表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetHardSoftLibDao extends IBaseDao<AssetHardSoftLib> {

    /**
     * 操作系统（下拉项）
     *
     * @return
     */
    List<OsSelectResponse> pullDownOs(OsQuery query);

    List<String> pulldownSupplier(AssetPulldownQuery query);

    List<String> pulldownName(AssetPulldownQuery query);

    List<AssetHardSoftLib> queryHardSoftLibByVersion(AssetPulldownQuery query);

    int countByWhere(HashMap<String, String> map);

    /**
     * 排除已存在关系的资产id
     *
     * @param businessId
     * @param sourceType
     * @return
     */
    List<Long> exceptIds(@Param("businessId") String businessId, @Param("sourceType") String sourceType,
                         @Param("assetType") String assetType);

    Integer queryHardSoftLibCount(AssetHardSoftLibQuery query);

    /**
     * 软硬件查询
     *
     * @param query
     * @return
     */
    List<AssetHardSoftLib> queryHardSoftLibList(AssetHardSoftLibQuery query);

    /**
     * cpe信息查询-软硬操作系统
     *
     * @param query 查询条件
     * @return 数据
     */
    List<AssetHardSoftLib> queryAssetList(AssetHardSoftOperQuery query);

    /**
     * cpe信息查询-软硬操作系统
     * @param query 查询条件
     * @return 数量
     */
    Integer queryAssetListCount(AssetHardSoftOperQuery query);
    Integer queryCountSoftWares(ObjectQuery query);

    /**
     * 软件列表
     *
     * @return
     */
    List<AssetHardSoftLib> querySoftWares(ObjectQuery query);

    List<AssetHardSoftLib> querySoftsRelations(@Param("templateId") String templateId);

    AssetHardSoftLib getByBusinessId(@Param("businessId") String businessId);
}
