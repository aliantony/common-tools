package com.antiy.asset.service;

import com.antiy.asset.vo.request.AssetCorrectRequest;
import com.antiy.asset.vo.request.AssetEntryRequest;
import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.asset.vo.response.AssetCorrectIInfoResponse;
import com.antiy.common.base.ActionResponse;

import java.util.List;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:05
 * @description:
 */
public interface IAssetStatusJumpService {

    /**
     * 资产状态跃迁
     * @param statusJumpRequest
     * @return
     * @throws Exception
     */
    ActionResponse changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception;
    ActionResponse statusJump(AssetStatusJumpRequest statusJumpRequest) throws Exception;

    String entryExecution(AssetEntryRequest request);


    AssetCorrectIInfoResponse assetCorrectingInfo(AssetCorrectRequest activityHandleRequest) throws Exception;
    AssetCorrectIInfoResponse assetCorrectingOfbaseLine(AssetCorrectRequest assetCorrectRequest) throws Exception;
    Integer netToCorrect(List<String> assetIds);

    /**
     * 继续入网
     */
    Integer continueNetIn(String assetId) throws Exception;
}
