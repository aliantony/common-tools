package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import com.antiy.common.utils.LoginUserUtil;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.enums.SoftwareStatusEnum;
import com.antiy.asset.vo.enums.SoftwareStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;

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
        super.changeStatus(assetStatusReqeust);
        SoftwareStatusEnum softwareStatusEnum = SoftwareStatusJumpEnum
            .getNextStatus(assetStatusReqeust.getSoftwareStatusEnum(), assetStatusReqeust.getAgree());
        // 软件表详情操作
        AssetSoftware assetSoftware = new AssetSoftware();
        //TODO 获取用户密码失败，待与用户小组调试
//        assetSoftware.setId(DataTypeUtils.stringToInteger(aesEncoder.decode(assetStatusReqeust.getAssetId(),LoginUserUtil.getLoginUser().getUsername())));
        assetSoftware.setId(3);
        assetSoftware.setGmtModified(System.currentTimeMillis());
        assetSoftware.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetSoftware.setSoftwareStatus(softwareStatusEnum.getCode());
        return ActionResponse.success(assetSoftwareDao.update(assetSoftware));
    }
}
