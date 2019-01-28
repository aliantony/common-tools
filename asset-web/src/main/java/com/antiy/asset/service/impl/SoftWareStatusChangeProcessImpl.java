package com.antiy.asset.service.impl;

import static com.antiy.biz.file.FileHelper.logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:48
 * @description:
 */
@Service
public class SoftWareStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {
    @Resource
    AssetSoftwareDao assetSoftwareDao;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        ActionResponse actionResponse = super.changeStatus(assetStatusReqeust);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse;
        }

        SoftwareStatusEnum softwareStatusEnum = SoftwareStatusJumpEnum
            .getNextStatus(assetStatusReqeust.getSoftwareStatusEnum(), assetStatusReqeust.getAgree());
        // 软件表详情操作
        AssetSoftware assetSoftware = new AssetSoftware();
        assetSoftware.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        assetSoftware.setGmtModified(System.currentTimeMillis());
        assetSoftware.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetSoftware.setSoftwareStatus(softwareStatusEnum.getCode());

        LogHandle.log(assetSoftware.toString(), AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName(),
            AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName() + " {}", assetSoftware.toString());
        return ActionResponse.success(assetSoftwareDao.update(assetSoftware));
    }
}
