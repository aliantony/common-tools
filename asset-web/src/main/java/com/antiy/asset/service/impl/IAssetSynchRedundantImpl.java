package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.service.IAssetSynchRedundant;
import com.antiy.asset.vo.query.AssetSynchCpeQuery;
import com.antiy.common.exception.BusinessException;

/**
 * @author zhangyajun
 * @create 2019-10-14 16:45
 **/
@Service
public class IAssetSynchRedundantImpl implements IAssetSynchRedundant {
    @Resource
    private AssetHardSoftLibDao hardSoftLibDao;

    @Resource
    private AssetDao            assetDao;

    @Override
    public Integer synchRedundantAsset(AssetSynchCpeQuery query) throws Exception {
        Long start = query.getStartStamp();
        Long end = query.getEndStamp();
        if (start == null || end == null) {
            throw new BusinessException("参数不能为空, 请检查参数");
        }
        if (start > end) {
            throw new BusinessException("开始时间应小于结束时间");
        }

        // 更新名称、厂商、操作系统字段
        List<AssetHardSoftLib> hardSoftLibList = hardSoftLibDao.getCpeByTime(query);
        if (CollectionUtils.isNotEmpty(hardSoftLibList)) {
            return assetDao.updateRedundantFiled(hardSoftLibList);
        }
        return null;
    }
}