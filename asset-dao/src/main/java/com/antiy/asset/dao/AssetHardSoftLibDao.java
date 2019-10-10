package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetPulldownQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.common.base.IBaseDao;
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

    Integer queryCountSoftWares(AssetSoftwareQuery query);

    /**
     * 软件列表
     *
     * @return
     */
    List<AssetHardSoftLib> querySoftWares(AssetSoftwareQuery query);

    List<AssetHardSoftLib> querySoftsRelations(String templateId);

    AssetHardSoftLib getByBusinessId(@Param("businessId") String businessId);
}
