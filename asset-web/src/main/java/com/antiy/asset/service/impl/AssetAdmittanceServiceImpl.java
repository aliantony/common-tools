package com.antiy.asset.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.AssetAdmittanceService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.BaselineCategoryModelResponse;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * @Author: lvliang
 * @Date: 2019/7/24 15:08
 */
@Service
public class AssetAdmittanceServiceImpl extends BaseServiceImpl<Asset> implements AssetAdmittanceService {
    private Logger                              logger = LogUtils.get(this.getClass());
    @Resource
    private AssetDao                            assetDao;
    @Resource
    private BaseConverter<Asset, AssetResponse> responseConverter;
    @Resource
    private AssetGroupRelationDao               assetGroupRelationDao;
    @Resource
    private RedisUtil                           redisUtil;
    @Resource
    private IRedisService                       redisService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetResponse> findListAsset(AssetQuery query) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().map(DataTypeUtils::integerToString).collect(Collectors.toList())));
        }

        // 查询资产信息
        List<Asset> assetList = assetDao.findListAsset(query);

        if (CollectionUtils.isNotEmpty(assetList)) {
            assetList.stream().forEach(a -> {
                try {
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                        DataTypeUtils.stringToInteger(a.getAreaId()));
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    a.setAreaName(sysArea.getFullName());
                } catch (Exception e) {
                    logger.warn("获取资产区域名称失败", e);
                }
            });
        }
        List<AssetResponse> objects = responseConverter.convert(assetList, AssetResponse.class);

        return objects;

    }

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
        // 是否资产组关联资产查询
        if (null != query.getAssociateGroup()) {
            ParamterExceptionUtils.isBlank(query.getGroupId(), "资产组ID不能为空");
            List<String> associateAssetIdList = assetGroupRelationDao.findAssetIdByAssetGroupId(query.getGroupId());
            if (CollectionUtils.isNotEmpty(associateAssetIdList)) {
                query.setExistAssociateIds(associateAssetIdList);
            }
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().map(DataTypeUtils::integerToString).collect(Collectors.toList())));
        }

        int count = 0;
        // 如果count为0 直接返回结果即可
        if (count <= 0) {
            if (query.getAreaIds() != null && query.getAreaIds().length <= 0) {
                query.setAreaIds(
                    DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().map(DataTypeUtils::integerToString).collect(Collectors.toList())));
            }
            count = this.findCountAsset(query);
        }

        if (count <= 0) {
            if (query.getEnterControl()) {
                // 如果是工作台进来的但是有没有存在当前状态的待办任务，则把当前状态的资产全部查询出来
                query.setEnterControl(false);
                return new PageResult<>(query.getPageSize(), this.findCountAsset(query), query.getCurrentPage(),
                    this.findListAsset(query));
            }
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
        }

        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), this.findListAsset(query));
    }

    public Integer findCountAsset(AssetQuery query) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().map(DataTypeUtils::integerToString).collect(Collectors.toList())));
        }
        return assetDao.findCount(query);
    }
}
