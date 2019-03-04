package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetFlowCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.AssetStatusJumpEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @auther: zhangyajun
 * @date: 2019/1/23 15:38
 * @description: 硬件资产状态跃迁
 */
@Service
public class AssetStatusChangeFlowProcessImpl extends AbstractAssetStatusChangeProcessImpl {

    @Resource
    AssetDao assetDao;
    @Resource
    AssetChangeRecordDao assetChangeRecordDao;
    @Resource
    IAssetService assetService;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {
        ActionResponse actionResponse = super.changeStatus(assetStatusReqeust);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse;
        }

        AssetStatusEnum assetStatusEnum = AssetStatusJumpEnum.getNextStatus(assetStatusReqeust.getAssetStatus(),
            assetStatusReqeust.getAgree());
        Asset asset = new Asset();
        asset.setAssetStatus(assetStatusEnum.getCode());
        asset.setId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        asset.setGmtModified(System.currentTimeMillis());
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());

        if(assetStatusReqeust.getSchemeRequest() != null && StringUtils.isNotBlank(assetStatusReqeust.getSchemeRequest().getExtension())) {
            // 判断资产变更流程中的的分析信息中是否影响基准
            Map<String, Boolean> analyzeInfo = (Map<String, Boolean>) JSONArray
                    .parse(assetStatusReqeust.getSchemeRequest().getExtension());

            // 如果是硬件资产变更,并且不会影响基准，则会直接到已入网状态
            if (AssetFlowCategoryEnum.HARDWARE_CHANGE.equals(assetStatusReqeust.getAssetFlowCategoryEnum())
                    && analyzeInfo != null && !analyzeInfo.get("baseline")) {
                asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
            }
        }
        //更新资产状态
        assetDao.update(asset);
//
//        //更新硬件
//
//        if (AssetFlowCategoryEnum.HARDWARE_CHANGE.equals(assetStatusReqeust.getAssetFlowCategoryEnum())){
//            AssetChangeRecord changeRecord = assetChangeRecordDao.getByDescTime (DataTypeUtils.stringToInteger (assetStatusReqeust.getAssetId ()));
//            if (changeRecord != null){
//                String changeVal = changeRecord.getChangeVal ();
//                AssetOuterRequest outerRequest = JsonUtil.json2Object (changeVal, AssetOuterRequest.class);
//                assetService.changeAsset (outerRequest);
//            }
//        }

        return ActionResponse.success();
    }
}
