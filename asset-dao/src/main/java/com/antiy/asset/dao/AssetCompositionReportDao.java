package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetCompositionReport;
import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.asset.vo.response.AssetCompositionReportResponse;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> Mapper 接口 </p>
 *
 * @author why
 * @since 2020-02-24
 */
public interface AssetCompositionReportDao extends IBaseDao<AssetCompositionReport> {

    List<AssetCompositionReportResponse> findAll(AssetCompositionReportQuery query);

    Integer findCountByName(@Param("name") String name, @Param("id") Integer id);

    void updateBatch(List<AssetCompositionReport> report);
}
