package com.antiy.asset.intergration;

import java.util.List;

import com.antiy.asset.vo.request.BaselineAssetRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.common.base.ActionResponse;

/**
 * @author: zhangbing
 * @date: 2019/4/4 16:22
 * @description:
 */
public interface BaseLineClient {

    /**
     * 硬件登记调用配置安全检查接口
     * @param request
     * @return
     */
    ActionResponse baselineCheck(BaselineAssetRegisterRequest request);

    /**
     * 硬件登记调用配置安全检查接口
     * @param request
     * @return
     */
    ActionResponse baselineCheckNoUUID(BaselineAssetRegisterRequest request);

    /**
     * 入网下发基准
     * @param assetId
     * @return
     */
    ActionResponse scan(String assetId);

    /**
     * 配置启动流程
     * @param baselineWaitingConfigRequestList
     * @return
     */
    ActionResponse baselineConfig(List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList);

}
