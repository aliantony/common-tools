package com.antiy.asset.service;

import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.query.KeyPullQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AssetKeyManageResponse;
import com.antiy.asset.vo.response.KeyPullDownResponse;
import com.antiy.common.base.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * key--资产ID、编号列表获取
     * @param query
     * @return
     * @throws Exception
     */
    List<KeyPullDownResponse> assetMapList(KeyPullQuery query) throws Exception;

    /**
     * key--用户ID、名列表获取
     * @param query
     * @return
     * @throws Exception
     */
    List<KeyPullDownResponse> userMapList(KeyPullQuery query) throws Exception;
}
