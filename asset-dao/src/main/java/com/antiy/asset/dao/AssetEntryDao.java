package com.antiy.asset.dao;

import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.common.base.IBaseDao;

    public interface AssetEntryDao extends IBaseDao<AssetEntryResponse> {
       Integer updateEntryStatus(AssetEntryRequest request);
}
