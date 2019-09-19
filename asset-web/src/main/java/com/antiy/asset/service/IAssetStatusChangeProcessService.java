package com.antiy.asset.service;

import com.antiy.asset.vo.request.AssetStatusJumpRequest;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:05
 * @description:
 */
public interface IAssetStatusChangeProcessService {

    /**
     * 资产状态跃迁
     * @param statusJumpRequest
     * @return
     * @throws Exception
     */
    ActionResponse  changeStatus(AssetStatusJumpRequest statusJumpRequest) throws Exception;
}
