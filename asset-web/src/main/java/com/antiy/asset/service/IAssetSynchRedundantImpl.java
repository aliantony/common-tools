package com.antiy.asset.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.entity.AssetHardSoftLib;
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
    public void synchRedundantAsset(AssetSynchCpeQuery query) throws Exception {
        Long start = query.getStart();
        Long end = query.getEnd();
        if (start == null || end == null) {
            throw new BusinessException("参数不能为空, 请检查参数");
        }
        if (start > end) {
            throw new BusinessException("开始时间应小于结束时间");
        }

        // 处理业务
        List<AssetHardSoftLib> hardSoftLibList = hardSoftLibDao.getCpeByTime(query);

        if (CollectionUtils.isNotEmpty(hardSoftLibList)) {
            assetDao.updateRedundantFiled(hardSoftLibList);
        }
    }
}
