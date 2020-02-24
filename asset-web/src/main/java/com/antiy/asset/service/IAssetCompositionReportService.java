package com.antiy.asset.service;

import com.antiy.asset.entity.AssetCompositionReport;
import com.antiy.asset.vo.query.AssetCompositionReportQuery;
import com.antiy.asset.vo.request.AssetCompositionReportRequest;
import com.antiy.asset.vo.response.AssetCompositionReportResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

/**
 * <p> 服务类 </p>
 *
 * @author why
 * @since 2020-02-24
 */
public interface IAssetCompositionReportService extends IBaseService<AssetCompositionReport> {

    /**
     * 保存
     * @param request
     * @return
     */
    Integer saveAssetCompositionReport(AssetCompositionReportRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    Integer updateAssetCompositionReport(AssetCompositionReportRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetCompositionReportResponse> findListAssetCompositionReport(AssetCompositionReportQuery query) throws Exception;

    /**
     * 批量查询
     * @param query
     * @return
     */
    PageResult<AssetCompositionReportResponse> findPageAssetCompositionReport(AssetCompositionReportQuery query) throws Exception;

}
