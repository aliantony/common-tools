package com.antiy.asset.intergration;

import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.request.BaselineAssetRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.common.base.ActionResponse;

import java.util.List;

/**
 * @author: zhangbing
 * @date: 2019/4/4 16:22
 * @description:
 */
public interface BaseLineClient {

    /**
     * 硬件登记调用配置注册接口
     * @param request
     * @return
     */
    ActionResponse configRegister(List<ConfigRegisterRequest> request);

    /**
     * 硬件登记调用配置安全检查接口
     * @param request
     * @return
     */
    ActionResponse baselineCheck(BaselineAssetRegisterRequest request);

    /**
     * 硬件带入网到待验证，调用验证接口
     * @param assetId
     * @return
     */
    ActionResponse updateAssetVerify(String assetId);

    /**
     * 入网下发基准
     * @param assetId
     * @return
     */
    ActionResponse distributeBaseline(String assetId);



    /**
     * 配置启动流程
     * @param baselineWaitingConfigRequestList
     * @return
     */
    ActionResponse baselineConfig(List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList);

}
