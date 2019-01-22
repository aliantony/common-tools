package com.antiy.asset.service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.intergration.WorkOrderClient;
import com.antiy.asset.vo.enums.CodeEnum;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.request.WorkOrderVO;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LoginUserUtil;

/**
 * 操作流程和创建工单通用对象
 * 
 * @author zhangyajun
 * @create 2019-01-16 17:43
 **/
public class OperationWOProcessor {

    public static void createWordOrder(WorkOrderClient workOrderClient, SchemeRequest schemeRequest,
                                       CodeEnum codeEnum) {
//        Integer appointUser = LoginUserUtil.getLoginUser().getId();
//        // 指定操作员不是自己时，创建工单
//        if (appointUser != null && !schemeRequest.getPutintoUserId().equals(appointUser)) {
//            WorkOrderVO workOrderVO = new WorkOrderVO();
//            workOrderVO
//                .setName(codeEnum != null ? codeEnum.getMsg() + "工单" : RespBasicCode.PARAMETER_ERROR.getResultDes());
//            workOrderVO.setWorkLevel(schemeRequest.getOrderLevel());
//            // 准入实施
//            workOrderVO.setOrderType("8");
//            // 资产管理
//            workOrderVO.setOrderSource("1");
//            workOrderVO
//                .setContent(codeEnum != null ? codeEnum.getMsg() + "申请" : RespBasicCode.PARAMETER_ERROR.getResultDes());
//            workOrderVO.setExecuteUserId(schemeRequest.getPutintoUserId());
//            workOrderVO.setExecuteUserName(schemeRequest.getPutintoUser());
//            workOrderVO.setStartTime(schemeRequest.getExpecteStartTime().toString());
//            workOrderVO.setEndTime(schemeRequest.getExpecteEndTime().toString());
//            workOrderClient.createWorkOrder(workOrderVO);
//        }
//    }
//
//    public static void saveOperationRecord(CodeEnum codeEnum, AssetOperationRecord assetOperationRecord,
//                                           AssetOperationRecordDao assetOperationRecordDao,
//                                           String topType) throws Exception {
//        assetOperationRecord.setTargetObjectId(assetOperationRecord.getTargetObjectId());
//        assetOperationRecord.setTargetType(topType);
//        assetOperationRecord.setTargetStatus(assetOperationRecord.getTargetStatus());
//        assetOperationRecord.setSchemeId(assetOperationRecord.getSchemeId());
//
//        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
//        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
//        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getUsername());
//        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
//        if (codeEnum == null) {
//            assetOperationRecord.setContent(RespBasicCode.PARAMETER_ERROR.getResultDes());
//            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
//        } else {
//            assetOperationRecord.setContent(codeEnum.getMsg());
//        }
//        assetOperationRecordDao.insert(assetOperationRecord);
    }
}
