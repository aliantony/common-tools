package com.antiy.asset.service.impl;

import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:38
 * @description:
 */
public class AssetStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {
    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        super.changeStatus(assetStatusReqeust);
        AssetStatusEnum assetNextStatus = AssetStatusJumpEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());

        // 更新软件资产状态
        return null;
    }
}
