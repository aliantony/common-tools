package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetEntryDao;
import com.antiy.asset.entity.AssetEntryRecord;
import com.antiy.asset.service.iAssetEntryService;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.common.base.PageResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liulusheng
 * @since 2020/2/14
 */
public class AssetEntryServiceImpl implements iAssetEntryService {
    @Resource
    AssetEntryDao assetEntryDao;
    @Override
    public PageResult<AssetEntryResponse> queryPage(AssetEntryQuery query) throws Exception {
        assetEntryDao.findCount(query);
        assetEntryDao.findQuery(query);
//        return new PageResult<>()
        return null;
    }

    @Override
    public String updateEntryStatus(AssetEntryRequest request) {
        assetEntryDao.updateEntryStatus(request);
        return null;
    }

    @Override
    public List<AssetEntryRecord> queryRecord(AssetEntryQuery query) {
        return null;
    }
}
