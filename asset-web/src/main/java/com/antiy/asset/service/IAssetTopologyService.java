package com.antiy.asset.service;

import java.util.List;
import java.util.Map;

import com.antiy.asset.entity.AssetTopology;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.PageResult;

/**
 * 资产拓扑管理
 * @author zhangxin
 * @date 2019/4/23 11:18
 */
public interface IAssetTopologyService {

    List<String> queryCategoryModels();

    /**
     * 查询节点信息
     * @param assetId
     * @return
     */
    TopologyAssetResponse queryAssetNodeInfo(String assetId) throws Exception;

    /**
     * 统计总资产/已管控拓扑管理的资产数量 (totalNum,2),(inControlNum,3)
     * @return
     * @throws Exception 查询异常
     */
    CountTopologyResponse countAssetTopology() throws Exception;

    /**
     * 已管控拓扑管理的资产的资产组信息(下拉)
     * @return
     */
    List<SelectResponse> queryGroupList();

    /**
     * 获取拓扑资产列表
     * @param query
     * @return
     * @throws Exception
     */
    TopologyListResponse getTopologyList(AssetQuery query) throws Exception;

    TopologyCategoryCountResponse countTopologyCategory() throws Exception;

    TopologyOsCountResponse countTopologyOs() throws Exception;

    /**
     * 获取拓扑图
     * @return
     * @throws Exception
     */
    AssetTopologyNodeResponse getTopologyGraph() throws Exception;



    /**
     * 
     */
    AssetTopologyIpSearchResposne queryListByIp(AssetQuery query) throws Exception;

    AssetTopologyAlarmResponse getAlarmTopology() throws Exception;

}
