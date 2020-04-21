package com.antiy.asset.cache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.common.base.BaseEntity;
import com.antiy.common.utils.LogUtils;

/**
 * 资产基础数据本地缓存(asset_user,asset_department,asset_business,asset_group,asset_category_model)
 * @Author: lvliang
 * @Date: 2020/4/9 9:53
 */
@Component
public class AssetBaseDataCache<T extends BaseEntity> {
    public static final String                              ASSET_BUSINESS       = "asset_business";
    public static final String                              ASSET_USER           = "asset_user";
    public static final String                              ASSET_DEPARTMENT     = "asset_department";
    public static final String                              ASSET_GROUP          = "asset_group";
    public static final String                              ASSET_CATEGORY_MODEL = "asset_category_model";

    private static Integer                                  DEFAULT_SIZE         = 16;
    private Logger                                          log                  = LogUtils.get(this.getClass());

    private Map<String, Map<Integer, ? extends BaseEntity>> caches               = new ConcurrentHashMap<>();
    @Resource
    private AssetBusinessDao                                assetBusinessDao;
    @Resource
    private AssetGroupDao                                   assetGroupDao;
    @Resource
    private AssetUserDao                                    assetUserDao;
    @Resource
    private AssetDepartmentDao                              assetDepartmentDao;
    @Resource
    private AssetCategoryModelDao                           assetCategoryModelDao;

    /**
     * 刷新缓存:10分钟刷新一次
     */
    @Scheduled(cron = "0 */10 * * * ?")
    private void refreshTask() {
        log.info("定时刷新缓存开始");
        init();
        log.info("定时刷新缓存结束");
    }

    @PostConstruct
    private void init() {
        log.info("初始化本地缓存数据......");
        try {
            List<AssetBusiness> assetBusinesses = assetBusinessDao.getAll();
            if (CollectionUtils.isNotEmpty(assetBusinesses)) {
                caches.put(ASSET_BUSINESS,
                    assetBusinesses.stream().collect(Collectors.toMap(AssetBusiness::getId, Function.identity())));
            } else {
                caches.put(ASSET_BUSINESS, new ConcurrentHashMap<>(DEFAULT_SIZE));
            }
            List<AssetGroup> assetGroups = assetGroupDao.getAll();
            if (CollectionUtils.isNotEmpty(assetGroups)) {
                caches.put(ASSET_GROUP,
                    assetGroups.stream().collect(Collectors.toMap(AssetGroup::getId, Function.identity())));
            } else {
                caches.put(ASSET_GROUP, new ConcurrentHashMap<>(DEFAULT_SIZE));
            }
            List<AssetUser> assetUsers = assetUserDao.getAll();
            if (CollectionUtils.isNotEmpty(assetUsers)) {
                caches.put(ASSET_USER,
                    assetUsers.stream().collect(Collectors.toMap(AssetUser::getId, Function.identity())));
            } else {
                caches.put(ASSET_USER, new ConcurrentHashMap<>(DEFAULT_SIZE));
            }
            List<AssetDepartment> assetDepartments = assetDepartmentDao.getAll();
            if (CollectionUtils.isNotEmpty(assetDepartments)) {
                caches.put(ASSET_DEPARTMENT,
                    assetDepartments.stream().collect(Collectors.toMap(AssetDepartment::getId, Function.identity())));
            } else {
                caches.put(ASSET_DEPARTMENT, new ConcurrentHashMap<>(DEFAULT_SIZE));
            }
            List<AssetCategoryModel> assetCategoryModels = assetCategoryModelDao.getAll();
            if (CollectionUtils.isNotEmpty(assetCategoryModels)) {
                caches.put(ASSET_CATEGORY_MODEL, assetCategoryModels.stream()
                    .collect(Collectors.toMap(AssetCategoryModel::getId, Function.identity())));
            } else {
                caches.put(ASSET_CATEGORY_MODEL, new ConcurrentHashMap<>(DEFAULT_SIZE));
            }
        } catch (Exception e) {
            log.error("缓存数据初始化报错:{}", e.getMessage());
        }
    }

    /**
     * 放入缓存
     * @param type
     * @param data
     */
    public void put(String type, T data) {
        Map cache = caches.get(type);
        cache.put(data.getId(), data);
    }

    /**
     * 批量放入缓存
     * @param type
     * @param data
     */
    public void put(String type, List<T> data) {
        Map cache = caches.get(type);
        data.stream().forEach(d -> {
            cache.put(d.getId(), d);
        });
    }

    /**
     * 获取数据
     * @param type
     * @param id
     * @return
     */
    public T get(String type, Integer id) {
        Map cache = caches.get(type);
        if (MapUtils.isNotEmpty(cache) && cache.containsKey(id)) {
            return (T) cache.get(id);
        }
        return null;
    }

    /**
     * 移除缓存
     * @param type
     * @param id
     */
    public void remove(String type, Integer id) {
        Map cache = caches.get(type);
        if (MapUtils.isNotEmpty(cache) && cache.containsKey(id)) {
            cache.remove(id);
        }
    }

    /**
     * 移除缓存
     * @param type
     * @param ids
     */
    public void remove(String type, List<Integer> ids) {
        Map cache = caches.get(type);
        if (MapUtils.isNotEmpty(cache)) {
            ids.stream().forEach(id -> {
                cache.remove(ids);
            });
        }
    }

    /**
     * 更新缓存
     * @param type
     * @param data
     */
    public void update(String type, T data) {
        Map cache = caches.get(type);
        if (MapUtils.isNotEmpty(cache)) {
            if (cache.containsKey(data.getId())) {
                cache.remove(data.getId());
            }
            cache.put(data.getId(), data);
        }
    }

    /**
     * 查询多个
     * @param type
     * @param ids
     * @return
     */
    public List<T> getAll(String type, Integer[] ids) {
        List<T> list = Lists.newArrayList();
        Map cache = caches.get(type);
        if (ids != null && ids.length > 0) {
            Arrays.stream(ids).forEach(id -> {
                list.add((T) cache.get(id));
            });
        }
        return list;
    }
    /**
     * 获取某个缓存的全部数据
     * @param type
     * @return
     */
    public List<T> getAll(String type) {
        Map cache = caches.get(type);
        return (List<T>) cache.values();
    }
}
