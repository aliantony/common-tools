package com.antiy.asset.service;

import com.antiy.asset.vo.request.TopologyRequest;
import com.antiy.asset.vo.response.TopologyResponse;

import java.util.List;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/2 14:22
 * @Description: 网络拓扑service接口
 */
public interface ITopologyService {

    /**
     * 初始化网络拓扑
     * @return
     */
    List<TopologyResponse> queryTopologyInit();

    /**
     * 查询网络拓扑
     * @return
     */
    String queryTopology();

    /**
     * 保存网络拓扑
     * @return
     */
    int saveTopology(TopologyRequest topologyRequest);
}
