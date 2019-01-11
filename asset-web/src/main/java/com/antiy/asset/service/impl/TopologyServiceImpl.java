package com.antiy.asset.service.impl;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetTopologyDao;
import com.antiy.asset.entity.AssetTopology;
import com.antiy.asset.entity.Topology;
import com.antiy.asset.service.ITopologyService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.TopologyRequest;
import com.antiy.asset.vo.response.TopologyResponse;
import com.antiy.common.utils.LogUtils;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/2 14:24
 * @Description: 网络拓扑
 */
@Service
public class TopologyServiceImpl implements ITopologyService {
    @Resource
    private AssetDao            assetDao;
    @Resource
    private AssetTopologyDao    assetTopologyDao;

    static Lock                 reenLock = new ReentrantLock();
    private static final Logger logger   = LogUtils.get();

    @Override
    public List<TopologyResponse> queryTopologyInit() throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        List<Integer> assetStartsList = new ArrayList<>();
        assetStartsList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        assetStartsList.add(AssetStatusEnum.NET_IN.getCode());
        assetQuery.setAssetStatusList(assetStartsList);
        return hanldTopology(assetDao.findTopologyList(assetQuery));
    }

    /**
     * 处理网络拓扑数据
     * @param topologies
     * @return
     */
    private List<TopologyResponse> hanldTopology(List<Topology> topologies) {
        if (CollectionUtils.isNotEmpty(topologies)) {
            Map<Integer, TopologyResponse> rootTopology = new LinkedHashMap<Integer, TopologyResponse>();
            topologies.forEach(topology -> {
                TopologyResponse topologyResponse = new TopologyResponse();
                topologyResponse.setRoot(topology.getParentId() == 0 ? "0" : "1");
                topologyResponse.setValue(topology.getId() + "");
                topologyResponse.setType(topology.getType() + "");

                List<String> ids = new ArrayList<>();
                if (topology.getParentId() != 0) {
                    ids.add(topology.getParentId() + "");
                }
                topologyResponse.setJoin_node(ids);

                rootTopology.put(topology.getId(), topologyResponse);
            });
            topologies.forEach(topology -> {
                if (topology.getParentId() != 0 && rootTopology.containsKey(topology.getParentId())) {
                    TopologyResponse topologyResponse = rootTopology.get(topology.getParentId());
                    List<String> ids = rootTopology.get(topology.getParentId()).getJoin_node();
                    ids.add(topology.getId() + "");
                    topologyResponse.setJoin_node(ids);
                    rootTopology.put(topology.getParentId(), topologyResponse);
                }
            });
            return new ArrayList<TopologyResponse>(rootTopology.values());
        }
        return null;
    }

    @Override
    public String queryTopology(Integer topologyType) throws Exception {
        String result = getTopologyCache();

        // step1 判断缓存是否存在
        if (StringUtils.isEmpty(result)) {
            if (reenLock.tryLock()) { // 如果获取锁则从cache获取网络拓扑数据
                try {
                    // step2 从数据库获取数据
                    result = getTopologyDB(topologyType);

                    // TODO step3 数据缓存起来
                } finally {
                    reenLock.unlock();
                }
            } else {
                result = getTopologyCache();
                if (StringUtils.isEmpty(result)) {
                    // doNothing
                    logger.error("获取网络拓扑失败" + result);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public int saveTopology(TopologyRequest topologyRequest) throws Exception {
        AssetTopology assetTopology = new AssetTopology();
        assetTopology.setGmtCreate(System.currentTimeMillis());
        assetTopology.setRelation(topologyRequest.getDataJson());
        assetTopology.setTopologyType(topologyRequest.getTopologyType().getCode());
        int resultCount = assetTopologyDao.insert(assetTopology);
        // TODO 保存到缓存中
        return resultCount;
    }

    /**
     * 获取缓存的网络拓扑数据
     * @return
     */
    private String getTopologyCache() {
        return null;
    }

    /**
     * 通过类型获取网络拓扑信息
     * @param topologyType
     * @return
     */
    private String getTopologyDB(Integer topologyType) throws Exception {
        HashMap<String, Object> params = new HashMap<>();
        params.put("topologyType", topologyType);
        List<AssetTopology> topologyList = assetTopologyDao.getByWhere(params);
        return CollectionUtils.isNotEmpty(topologyList) ? topologyList.get(0).getRelation() : null;
    }
}
