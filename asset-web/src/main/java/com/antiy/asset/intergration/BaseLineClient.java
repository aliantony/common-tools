package com.antiy.asset.intergration;

import java.util.List;

import com.antiy.asset.vo.query.WorkFlowQuery;
import com.antiy.asset.vo.request.BaselineAssetRegisterRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.asset.vo.response.AssetCorrectIInfoResponse;
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
     * getBaselineTemplate
     * @param id
     * @return
     */
    ActionResponse getBaselineTemplate(String id);

    /**
     * 配置启动流程
     * @param baselineWaitingConfigRequestList
     * @return
     */
    ActionResponse baselineConfig(List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList);

    /**
     * 资产退役
     * @param assetIdList
     * @return
     */
    ActionResponse removeAsset(List<Integer> assetIdList);

    ActionResponse situationOfVul(String primaryKey);

    ActionResponse<AssetCorrectIInfoResponse> rectification(String assetId);

    ActionResponse deleteProcessInstance(List<String> procInstIds);

    ActionResponse listRy(WorkFlowQuery workFlowQuery);
}
