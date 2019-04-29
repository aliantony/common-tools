package com.antiy.asset.service;

import java.util.List;
import java.util.Map;

import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetNodeInfoResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.SelectResponse;
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
    AssetNodeInfoResponse queryAssetNodeInfo(String assetId);

    /**
     * 统计总资产/已管控拓扑管理的资产数量 (totalNum,2),(inControlNum,3)
     * @return
     * @throws Exception 查询异常
     */
    Map<String, Integer> countAssetTopology() throws Exception;

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
    PageResult<AssetResponse> getTopologyList(AssetQuery query) throws Exception;

}