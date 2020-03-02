package com.antiy.asset.dao;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.vo.response.AssetImportancePie;

/**
 * <p> 首页 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetHomePageDao {
    Integer countIncludeManage() throws Exception;

    Integer countUnincludeManage() throws Exception;

    Integer assetOnlineChart(@Param("startTime") Long startTime, @Param("endTime") Long endTime) throws Exception;

    AssetImportancePie assetImportanceDegreePie(Integer importanceDegree) throws Exception;
}
