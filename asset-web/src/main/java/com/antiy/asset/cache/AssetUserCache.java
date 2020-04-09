package com.antiy.asset.cache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.AssetUser;
import com.antiy.common.utils.LogUtils;
import com.google.common.collect.Maps;

/**
 * @Author: lvliang
 * @Date: 2020/4/8 14:45
 */
@Component
public class AssetUserCache {
    private Logger                         log    = LogUtils.get(this.getClass());

    @Resource
    private AssetUserDao                   assetUserDao;
    /**
     * 缓存数据
     */
    private static Map<Integer, AssetUser> caches = Maps.newConcurrentMap();

    /**
     * 初始化数据
     * @throws Exception
     */
    @PostConstruct
    private void init() throws Exception {
        log.info("初始化人员信息数据......");
        List<AssetUser> assetUsers = assetUserDao.getAll();
        if (CollectionUtils.isNotEmpty(assetUsers)) {
            caches.putAll(assetUsers.stream().collect(Collectors.toMap(AssetUser::getId, Function.identity())));
        }
    }

    public void put(AssetUser assetUser) {
        if (!caches.containsKey(assetUser.getId())) {
            caches.put(assetUser.getId(), assetUser);
        }
    }

    public AssetUser get(Integer id) {
        if (caches.containsKey(id)) {
            return caches.get(id);
        }
        return null;
    }

    public String getName(Integer id) {
        if (caches.containsKey(id)) {
            return caches.get(id).getName();
        }
        return null;
    }

    public void remove(Integer id) {
        if (caches.containsKey(id)) {
            caches.remove(id);
        }
    }
}
