package com.antiy.asset.cache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.antiy.asset.dao.AssetGroupDao;
import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.common.utils.LogUtils;

/**
 * 资产组缓存数据
 * @Author: lvliang
 * @Date: 2020/4/8 14:45
 */
@Component
public class AssetGroupCache {
    private Logger                      log    = LogUtils.get(this.getClass());

    /**
     * 缓存数据
     */
    private static Map<Integer, String> caches = new ConcurrentHashMap<>();

    @Resource
    private AssetGroupDao               assetGroupDao;

    public String getAllName(String[] split) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(split).forEach(s -> {
            stringBuilder.append(caches.get(DataTypeUtils.stringToInteger(s)));
        });
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    @PostConstruct
    private void init() throws Exception {
        log.info("初始化资产组数据......");
        List<AssetGroup> assetGroups = assetGroupDao.getAll();
        if (CollectionUtils.isNotEmpty(assetGroups)) {
            caches.putAll(assetGroups.stream().collect(Collectors.toMap(AssetGroup::getId, AssetGroup::getName)));
        }
    }

    public void put(AssetGroup assetGroup) {
        if (!caches.containsKey(assetGroup.getId())) {
            caches.put(assetGroup.getId(), assetGroup.getName());
        }
    }

    public String get(Integer id) {
        if (caches.containsKey(id)) {
            return caches.get(id);
        }
        return null;
    }

    public void remove(Integer id) {
        if (caches.containsKey(id)) {
            caches.remove(id);
        }
    }
}
