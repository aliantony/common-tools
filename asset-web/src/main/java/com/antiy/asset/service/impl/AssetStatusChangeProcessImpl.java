package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusChangeEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;


/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:38
 * @description:
 */
@Service
public class AssetStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {
    private Logger   logger = LogUtils.get(this.getClass());
    @Resource
    private AssetDao assetDao;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        ActionResponse actionResponse = super.changeStatus(assetStatusReqeust);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse;
        }

        AssetStatusEnum assetStatusEnum = AssetStatusChangeEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());
        Asset asset = new Asset();
        asset.setAssetStatus(assetStatusEnum.getCode());
        asset.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        asset.setGmtModified(System.currentTimeMillis());
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
        // 更新资产状态
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName(), asset.getId(), null,
            asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName() + " {}", asset);
        assetDao.update(asset);
        return ActionResponse.success();
    }
}
