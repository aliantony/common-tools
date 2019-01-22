package com.antiy.asset.service.impl;

import com.antiy.asset.vo.enums.SoftwareStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:48
 * @description:
 */
public class SoftWareStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {
    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        super.changeStatus(assetStatusReqeust);
        SoftwareStatusJumpEnum.getNextStatus(assetStatusReqeust.getSoftwareStatusEnum(), assetStatusReqeust.getAgree());
        // 软件表详情操作
        return null;
    }
}
