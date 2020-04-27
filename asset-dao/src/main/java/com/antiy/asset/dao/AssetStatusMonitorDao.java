package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetStatusMonitor;
import com.antiy.asset.vo.query.AssetStatusMonitorQuery;
import com.antiy.asset.vo.response.AssetStatusMonitorResponse;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2020-03-06
 */
public interface AssetStatusMonitorDao extends IBaseDao<AssetStatusMonitor> {
    /**
     * 获取资产基本监控信息及其规则
     * @param primaryKey
     * @return
     */
    AssetStatusMonitorResponse queryBasePerformance(String primaryKey);

    int countMonitor(AssetStatusMonitorQuery assetStatusMonitorQuery);

    /**
     * 获取资产监控信息 （进程/服务/软件）
     * @param assetStatusMonitorQuery
     * @return
     */

    List<AssetStatusMonitorResponse> queryMonitor(AssetStatusMonitorQuery assetStatusMonitorQuery);

    /**
     * 获取指定id 资产最近一次监控时间
     * @param assetId
     * @return
     */

    Long maxMonitorGmtCreate(AssetStatusMonitorQuery assetId);

}
