package com.antiy.asset.service;

import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AsssetKeyManageResponse;

import java.util.List;

public interface IAssetKeyManageService {

    /**
     * key模糊查询
     * @param keyManageQuery
     * @return
     */
    List<AsssetKeyManageResponse> queryList(AssetKeyManageQuery keyManageQuery);

    /**
     * key登记
     * @param request
     * @return
     */
    Integer keyRegister(AssetKeyManageRequest request);

    /**
     * key领用
     * @param request
     * @return
     * @throws Exception
     */
    Integer keyRecipients(AssetKeyManageRequest request) throws Exception;

    /**
     * key归还
     * @param request
     * @return
     */
    Integer keyReturn(AssetKeyManageRequest request);

    /**
     * key冻结 or 解冻
     * @param request
     * @return
     */
    Integer keyFreeze(AssetKeyManageRequest request);

    /**
     * key删除
     * @param request
     * @return
     */
    Integer keyRemove(AssetKeyManageRequest request);
}
