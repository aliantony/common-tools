package com.antiy.asset.service.impl;

import javax.annotation.Resource;

import com.antiy.asset.convert.SchemeRequestToSchemeConverter;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.service.IAssetStatusChangeProcessService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetFlowEnum;
import com.antiy.asset.vo.request.AssetStatusReqeust;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.utils.LoginUserUtil;

/**
 * @auther: zhangbing
 * @date: 2019/1/22 15:06
 * @description:
 */
public abstract class AbstractAssetStatusChangeProcessImpl implements IAssetStatusChangeProcessService {

    @Resource
    private AssetOperationRecordDao      assetOperationRecordDao;

    @Resource
    private SchemeDao                    schemeDao;

    @Resource
    private SchemeRequestToSchemeConverter schemeRequestToSchemeConverter;

    @Resource
    private ActivityClient               activityClient;

    @Resource
    private WorkOrderClient              workOrderClient;

    @Override
    public ActionResponse changeStatus(AssetStatusReqeust assetStatusReqeust) throws Exception {

        // 1.保存方案信息
        Scheme scheme = convertScheme(assetStatusReqeust);
        schemeDao.insert(scheme);

        // 2.保存流程
        AssetOperationRecord assetOperationRecord = convertAssetOperationRecord(assetStatusReqeust);
        assetOperationRecord.setSchemeId(scheme.getId());
        assetOperationRecordDao.insert(assetOperationRecord);

        // 3.调用流程引擎
        if (null != assetStatusReqeust.getActivityHandleRequest()) {
            ActionResponse actionResponse = activityClient.completeTask(assetStatusReqeust.getActivityHandleRequest());
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse || !actionResponse.isSuccess()) {
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
        }

        // 4.调用工单系统
        if (null != assetStatusReqeust.getWorkOrderVO()) {
            ActionResponse actionResponse = workOrderClient.createWorkOrder(assetStatusReqeust.getWorkOrderVO());
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse || !actionResponse.isSuccess()) {
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
        }
        return ActionResponse.success();
    }

    /**
     * 转换流程
     * @param assetStatusReqeust
     * @return
     */
    private AssetOperationRecord convertAssetOperationRecord(AssetStatusReqeust assetStatusReqeust) {
        AssetFlowEnum assetFlowEnum = EnumUtil.getByCode(AssetFlowEnum.class,
            assetStatusReqeust.getAssetStatus().getCode());
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        assetOperationRecord.setContent(assetFlowEnum != null ? assetFlowEnum.getMsg() : "参数异常");
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getUsername());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        return assetOperationRecord;
    }

    /**
     * 转换方案
     * @param assetStatusReqeust
     * @return
     */
    private Scheme convertScheme(AssetStatusReqeust assetStatusReqeust) {
        Scheme scheme = schemeRequestToSchemeConverter.convert(assetStatusReqeust.getSchemeRequest(), Scheme.class);
        scheme.setAssetId(DataTypeUtils.stringToInteger(assetStatusReqeust.getAssetId()));
        return scheme;
    }
}