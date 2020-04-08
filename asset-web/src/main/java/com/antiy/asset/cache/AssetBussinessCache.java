package com.antiy.asset.cache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.antiy.asset.dao.AssetBusinessDao;
import com.antiy.asset.entity.AssetBusiness;
import com.antiy.asset.util.DataTypeUtils;
import com.google.common.collect.Maps;

/**
 * @Author: lvliang
 * @Date: 2020/4/8 14:45
 */
@Component
public class AssetBussinessCache {
    @Resource
    private AssetBusinessDao            assetBusiness;
    /**
     * 缓存数据
     */
    private static Map<Integer, String> caches = Maps.newConcurrentMap();

    /**
     * 初始化数据
     * @throws Exception
     */
    @PostConstruct
    private void init() throws Exception {
        List<AssetBusiness> assetBusinesses = assetBusiness.getAll();
        if (CollectionUtils.isNotEmpty(assetBusinesses)) {
            caches.putAll(
                assetBusinesses.stream().collect(Collectors.toMap(AssetBusiness::getId, AssetBusiness::getName)));
        }
    }

    public static String getAllName(String[] split) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(split).forEach(s -> {
            stringBuilder.append(caches.get(DataTypeUtils.stringToInteger(s)));
        });
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public static void put(AssetBusiness assetBusiness) {
        if (!caches.containsKey(assetBusiness.getId())) {
            caches.put(assetBusiness.getId(), assetBusiness.getName());
        }
    }

    public static String get(Integer id) {
        if (caches.containsKey(id)) {
            return caches.get(id);
        }
        return null;
    }

    public static void remove(Integer id) {
        if (caches.containsKey(id)) {
            caches.remove(id);
        }
    }
}
