package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetNodeInfoResponse;

import com.antiy.common.base.ActionResponse;

import java.util.List;
import java.util.Map;

import java.util.Map;

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
}
