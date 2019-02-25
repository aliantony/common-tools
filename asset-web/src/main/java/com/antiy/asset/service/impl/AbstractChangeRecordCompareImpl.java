package com.antiy.asset.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.service.IChangeValCompare;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.common.base.BaseRequest;

/**
 * @author zhangyajun
 * @create 2019-02-25 9:04
 **/
public abstract class AbstractChangeRecordCompareImpl implements IChangeValCompare {

    @Resource
    public  AssetChangeRecordDao assetChangeRecordDao;

    // 通用信息、业务信息
    abstract List<Map<String, Object>> compareCommonBusinessInfo(Integer businessId) throws Exception;

    // 部件信息
    protected abstract List<? extends BaseRequest> compareComponentInfo();

    @Override
    public List<String> getTwoRecentChangeVal(Integer businessId, Integer type) {
        AssetChangeRecordQuery assetChangeRecordQuery = new AssetChangeRecordQuery();
        assetChangeRecordQuery.setBusinessId(businessId);
        assetChangeRecordQuery.setType(type);
        return assetChangeRecordDao.findChangeValByBusinessId(assetChangeRecordQuery);
    }
}
