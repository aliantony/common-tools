package com.antiy.asset.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description:
 */
@Service
public class AssetStatusChangeFlowProcessImpl extends AbstractAssetStatusChangeProcessImpl {

    @Resource
    AssetDao   assetDao;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        super.changeStatus(assetStatusReqeust);
        AssetStatusEnum assetStatusEnum = AssetStatusJumpEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());
        Asset asset = new Asset();
        asset.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        asset.setGmtModified(System.currentTimeMillis());
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());

        // 判断资产变更流程中的的分析信息中是否影响基准
        Map<String, Boolean> analyzeInfo = (Map<String, Boolean>) JSONArray
            .parse(assetStatusReqeust.getSchemeRequest().getExtension());
        // 如果影响，直接修改资产状态为待配置
        if (analyzeInfo.get("baseline")) {
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetDao.update(asset);
        } else {
            asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
            assetDao.update(asset);
        }

        // 更新软件资产状态
        return ActionResponse.success();
    }
}
