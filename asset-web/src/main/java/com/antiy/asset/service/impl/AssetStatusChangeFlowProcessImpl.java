package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import com.antiy.common.encoder.AesEncoder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 *
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description: 硬件资产状态跃迁
 */
@Service
public class AssetStatusChangeFlowProcessImpl extends AbstractAssetStatusChangeProcessImpl {
    private Logger         logger = LogUtils.get(this.getClass());
    @Resource
    private AssetDao       assetDao;
    @Resource
    private AesEncoder     aesEncoder;
    @Resource
    private BaseLineClient baseLineClient;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        AssetStatusEnum assetStatusEnum = super.getNextAssetStatus(assetStatusReqeust);
        Asset asset = new Asset();
        ActionResponse actionResponse = super.changeStatus(assetStatusReqeust);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse;
        }
        // 检查资产主表的首次入网时间，为空时写入入网时间

        Asset currentAsset = assetDao.getById(assetStatusReqeust.getAssetId());
        if (currentAsset != null && currentAsset.getFirstEnterNett() == null
            && assetStatusEnum.getCode().equals(AssetStatusEnum.NET_IN.getCode())) {
            asset.setFirstEnterNett(System.currentTimeMillis());
        }
        asset.setAssetStatus(assetStatusEnum.getCode());
        asset.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        asset.setGmtModified(System.currentTimeMillis());
        asset.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);

        // 如果是带入网并且选择拒绝，则会调用流程引擎的待验证接口
        ParamterExceptionUtils.isNull(assetStatusReqeust.getAssetStatus(), "硬件当前状态不能为空");
        if (!assetStatusReqeust.getAgree()
            && AssetStatusEnum.WAIT_NET.getCode().equals(assetStatusReqeust.getAssetStatus().getCode())) {

            ActionResponse actionResponseVerify = baseLineClient
                .updateAssetVerify(asset.getStringId());
            if (null == actionResponseVerify
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponseVerify.getHead().getCode())) {
                logger.warn("调用基准待验证接口失败,回滚资产状态,{}", assetStatusReqeust);
                asset.setStatus(assetStatusReqeust.getAssetStatus().getCode());
                assetDao.update(asset);
                return actionResponseVerify;
            }
        }

        // 更新资产状态
        assetDao.update(asset);
        return ActionResponse.success();
    }
}
