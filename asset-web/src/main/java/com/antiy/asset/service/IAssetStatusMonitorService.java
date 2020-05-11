package com.antiy.asset.service;

import com.antiy.asset.entity.AssetStatusMonitor;
import com.antiy.asset.vo.query.AssetStatusMonitorQuery;
import com.antiy.asset.vo.request.AssetStatusMonitorRequest;
import com.antiy.asset.vo.response.AssetStatusMonitorCountResponse;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-03-06
 */
public interface IAssetStatusMonitorService extends IBaseService<AssetStatusMonitor> {

        /**
         * 保存
         * @param request
         * @return
         */
        String saveAssetStatusMonitor(AssetStatusMonitorRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        String updateAssetStatusMonitor(AssetStatusMonitorRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetStatusMonitorResponse> queryListAssetStatusMonitor(AssetStatusMonitorQuery query) throws Exception;

        /**
         * 分页查询
         * @param query
         * @return
         */
        PageResult<AssetStatusMonitorResponse> queryPageAssetStatusMonitor(AssetStatusMonitorQuery query) throws Exception;

        /**
         * 通过ID查询
         * @param queryCondition
         * @return
         */
        AssetStatusMonitorResponse queryAssetStatusMonitorById(QueryCondition queryCondition) throws Exception;

        /**
         * 通过ID删除
         * @param baseRequest
         * @return
         */
        String deleteAssetStatusMonitorById(BaseRequest baseRequest) throws Exception;

        AssetStatusMonitorResponse queryBasePerformance(String primaryKey);

        PageResult<AssetStatusMonitorResponse> queryMonitor(AssetStatusMonitorQuery assetStatusMonitorQuery);

    AssetStatusMonitorCountResponse queryCount(AssetStatusMonitorQuery assetStatusMonitorQuery);
}
