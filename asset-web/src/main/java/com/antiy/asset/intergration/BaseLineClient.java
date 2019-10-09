package com.antiy.asset.intergration;

import com.antiy.asset.vo.query.ConfigRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.common.base.ActionResponse;

import java.io.File;
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
     * 模板下载
     * @param assetIdList
     * @return
     */
    File getTemplate(List<String> assetIdList);

    /**
     * 配置启动流程
     * @param baselineWaitingConfigRequest
     * @return
     */
    ActionResponse baselineConfig(BaselineWaitingConfigRequest baselineWaitingConfigRequest);

}
