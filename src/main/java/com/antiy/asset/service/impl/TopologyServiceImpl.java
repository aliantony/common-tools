package com.antiy.asset.service.impl;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.service.ITopologyService;
import com.antiy.asset.vo.request.TopologyRequest;
import com.antiy.asset.vo.response.TopologyResponse;
import com.antiy.common.utils.LogUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: zhangbing
 * @Date: 2019/1/2 14:24
 * @Description: 网络拓扑
 */
@Service
@Slf4j
public class TopologyServiceImpl implements ITopologyService {
    static Lock                 reenLock = new ReentrantLock();
    private static final Logger logger   = LogUtils.get();

    @Override
    public List<TopologyResponse> queryTopologyInit() {
        return null;
    }

    @Override
    public String queryTopology() {
        String result = getTopologyCache();

        // step1 判断缓存是否存在
        if (StringUtils.isEmpty(result)) {
            if (reenLock.tryLock()) { // 如果获取锁则从cache获取网络拓扑数据
                try {
                    // step2 从数据库获取数据
                    result = getTopologyDB();

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
    public int saveTopology(TopologyRequest topologyRequest) {

        return 0;
    }

    private String getTopologyCache() {
        return null;
    }

    private String getTopologyDB() {
        return null;
    }
}
