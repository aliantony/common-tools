package com.antiy.asset.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:38
 * @description:
 */
@Service
public class AssetStatusChangeProcessImpl extends AbstractAssetStatusChangeProcessImpl {

    @Resource
    AssetDao   assetDao;
    @Resource
    AesEncoder aesEncoder;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        super.changeStatus(assetStatusReqeust);
        AssetStatusEnum assetStatusEnum = AssetStatusJumpEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());
        Asset asset = new Asset();
        // TODO 获取用户密码失败，待与用户小组调试
        // asset.setId(DataTypeUtils.stringToInteger(aesEncoder.decode(assetStatusReqeust.getAssetId(),LoginUserUtil.getLoginUser().getUsername())));
        asset.setId(1);
        asset.setGmtModified(System.currentTimeMillis());
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
        // 更新软件资产状态
        return ActionResponse.success();
    }
}
