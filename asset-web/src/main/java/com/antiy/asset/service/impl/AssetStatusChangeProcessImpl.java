package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:38
 * @description:
 */
@Service
public class AssetStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {

    @Resource
    AssetDao assetDao;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        super.changeStatus(assetStatusReqeust);
        AssetStatusEnum assetStatusEnum = AssetStatusJumpEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());
        Asset asset = new Asset();
        asset.setAssetStatus(assetStatusEnum.getCode());
        asset.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        asset.setGmtModified(System.currentTimeMillis());
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
        // 更新软件资产状态
        assetDao.update(asset);
        return ActionResponse.success();
    }
}
