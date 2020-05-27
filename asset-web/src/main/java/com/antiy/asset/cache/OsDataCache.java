package com.antiy.asset.cache;

import com.antiy.asset.service.IAssetCpeTreeService;
import com.antiy.asset.vo.response.AssetCpeTreeResponse;
import com.antiy.common.base.BaseEntity;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作系统列表本地缓存
 *
 * @Author: chenchaowu
 * @Date: 2020/4/9 9:53
 */
@Component
public class OsDataCache<T extends BaseEntity> {

    public static final String ASSET_OS_TREE = "asset_os_tree";

    private Logger log = LogUtils.get(this.getClass());

    private Map<String, List<AssetCpeTreeResponse>> cachesTree = new ConcurrentHashMap<>();

    @Resource
    private IAssetCpeTreeService iAssetCpeTreeService;

    /**
     * 刷新缓存:6小时刷新一次
     */
    @Scheduled(cron = "0 0 0/6 * * ?")
    private void refreshTask() {
        log.info("定时刷新缓存开始");
        initTree();
        log.info("定时刷新缓存结束");
    }

    @PostConstruct
    private void initTree() {
        try {
            log.info("-----刷新操作系统树状图数据-----");
            List<AssetCpeTreeResponse> treeList = iAssetCpeTreeService.queryAssetOs();
            if (CollectionUtils.isNotEmpty(treeList)) {
                cachesTree.put(ASSET_OS_TREE, treeList);
            } else {
                cachesTree.put(ASSET_OS_TREE, new ArrayList<>());
            }
        } catch (Exception e) {
            log.error("缓存数据初始化报错:{}", e.getMessage());
        }

    }

    /**
     * 获取数据
     *
     * @param type
     * @return
     */
    public List<AssetCpeTreeResponse> getTree(String type) {
        List<AssetCpeTreeResponse> cacheTree = cachesTree.get(type);
        if (CollectionUtils.isNotEmpty(cacheTree)) {
            return cacheTree;
        }
        return null;
    }

    public void startInit() {
        log.info("启动刷新缓存开始");
        initTree();
        log.info("启动刷新缓存结束");
    }

}
