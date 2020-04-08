package com.antiy.asset.service;

import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AssetKeyManageResponse;
import com.antiy.common.base.PageResult;
import org.springframework.web.multipart.MultipartFile;

public interface IAssetKeyManageService {

    /**
     * key模糊查询
     * @param keyManageQuery
     * @return
     */
    PageResult<AssetKeyManageResponse> findPageAssetKeys(AssetKeyManageQuery keyManageQuery);

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
    Integer keyFreeze(AssetKeyManageRequest request, Integer keyStatus);

    /**
     * key删除
     * @param request
     * @return
     */
    Integer keyRemove(AssetKeyManageRequest request);

    String importKey(MultipartFile file) throws Exception;

    void exportTemplate();
}
