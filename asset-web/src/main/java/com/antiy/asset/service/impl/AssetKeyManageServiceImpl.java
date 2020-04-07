package com.antiy.asset.service.impl;

import com.antiy.asset.service.IAssetKeyManageService;
import com.antiy.asset.vo.query.AssetKeyManageQuery;
import com.antiy.asset.vo.request.AssetKeyManageRequest;
import com.antiy.asset.vo.response.AsssetKeyManageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chenchaowu
 * @Package com.antiy.asset.service.impl
 * @date 2020/4/7 15:23
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class AssetKeyManageServiceImpl implements IAssetKeyManageService {
    /**
     * key模糊查询
     *
     * @param keyManageQuery
     * @return
     */
    @Override
    public List<AsssetKeyManageResponse> queryList(AssetKeyManageQuery keyManageQuery) {
        return null;
    }

    /**
     * key登记
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyRegister(AssetKeyManageRequest request) {
        return null;
    }

    /**
     * key领用
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Integer keyRecipients(AssetKeyManageRequest request) throws Exception {
        return null;
    }

    /**
     * key归还
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyReturn(AssetKeyManageRequest request) {
        return null;
    }

    /**
     * key冻结 or 解冻
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyFreeze(AssetKeyManageRequest request) {
        return null;
    }

    /**
     * key删除
     *
     * @param request
     * @return
     */
    @Override
    public Integer keyRemove(AssetKeyManageRequest request) {
        return null;
    }
}
