package com.antiy.asset.service;

import java.util.Map;

/**
 * 资产拓扑管理
 * @author zhangxin
 * @date 2019/4/23 11:18
 */
public interface IAssetTopologyService {

    /**
     * 统计总资产/已管控拓扑管理的资产数量 (totalNum,2),(inControlNum,3)
     * @return
     * @throws Exception 查询异常
     */
    Map<String, Integer> countAssetTopology() throws Exception;
}
