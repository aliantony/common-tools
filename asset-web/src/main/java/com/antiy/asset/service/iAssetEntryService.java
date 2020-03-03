package com.antiy.asset.service;

import com.antiy.asset.entity.AssetEntryRecord;
import com.antiy.asset.vo.query.AssetEntryQuery;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.response.AssetEntryRecordResponse;
import com.antiy.asset.vo.response.AssetEntryResponse;
import com.antiy.common.base.PageResult;

import java.util.List;

public interface iAssetEntryService {
    PageResult<AssetEntryResponse> queryPage(AssetEntryQuery query) throws Exception;
  String  updateEntryStatus(AssetEntryRequest request);
   List<AssetEntryRecordResponse> queryRecord(AssetEntryQuery query);
}
