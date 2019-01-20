package com.antiy.asset.service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.impl.WorkOrderClientImpl;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.request.WorkOrderVO;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.SpringUtil;

/**
 * @author zhangyajun
 * @create 2019-01-16 17:43
 **/
public abstract class AbstractProcessor implements Processor {

    @Override
    public void process(String assetOperationEnum, SchemeRequest schemeRequest,Scheme scheme) throws Exception {
        AssetOperationRecordDao assetOperationRecordDao = (AssetOperationRecordDao) SpringUtil
            .getBean("assetOperationRecordDao");
        if (assetOperationEnum.equals(AssetOperationTableEnum.ASSET.getCode())) {
            // --------------------------------操作记录start------------------------------
            AssetSchemeTypeEnum codeEnum = EnumUtil.getByCode(AssetSchemeTypeEnum.class, scheme.getType());
            saveOperationRecord(codeEnum, scheme, assetOperationRecordDao,AssetOperationTableEnum.ASSET.getCode());
            // --------------------------------操作记录end--------------------------------

            // --------------------------------工单start------------------------------
            if (schemeRequest.getAssetStatus().equals(AssetStatusEnum.WAIT_NET.getCode())) {
                createWordOrder(scheme, codeEnum);
            }
            // --------------------------------工单end--------------------------------

        } else if (assetOperationEnum.equals(AssetOperationTableEnum.SOFTWARE.getCode())) {
            // --------------------------------操作记录start------------------------------
            SoftwareSchemeTypeEnum codeEnum = EnumUtil.getByCode(SoftwareSchemeTypeEnum.class, scheme.getType());
            saveOperationRecord(codeEnum, scheme, assetOperationRecordDao,AssetOperationTableEnum.ASSET.getCode());
            // --------------------------------操作记录end--------------------------------

            // --------------------------------工单start------------------------------
            // --------------------------------工单end--------------------------------
        }
    }

    private void createWordOrder(Scheme scheme, CodeEnum codeEnum) {
        WorkOrderClientImpl workOrderClient = new WorkOrderClientImpl();
        WorkOrderVO workOrderVO = new WorkOrderVO();
        workOrderVO.setName(codeEnum != null ? codeEnum.getMsg() + "工单" : RespBasicCode.PARAMETER_ERROR.getResultDes());
        workOrderVO.setWorkLevel(scheme.getOrderLevel());
        // 准入实施
        workOrderVO.setOrderType(8);
        // 资产管理
        workOrderVO.setOrderSource(1);
        workOrderVO
            .setContent(codeEnum != null ? codeEnum.getMsg() + "申请" : RespBasicCode.PARAMETER_ERROR.getResultDes());
        workOrderVO.setExecuteUserId(scheme.getPutintoUserId());
        workOrderVO.setExecuteUserName(scheme.getPutintoUser());
        workOrderVO.setCreateUser(1);
        workOrderVO.setStartTime(scheme.getExpecteStartTime());
        workOrderVO.setEndTime(scheme.getExpecteEndTime());
        workOrderClient.createWorkOrder(workOrderVO);
    }

    private void saveOperationRecord(CodeEnum codeEnum, Scheme scheme,
                                     AssetOperationRecordDao assetOperationRecordDao, String topType) throws Exception {
        AssetOperationRecord record = new AssetOperationRecord();
        record.setTargetObjectId(scheme.getAssetId());
        record.setTargetType(topType);
        record.setSchemaId(scheme.getId());
        record.setGmtCreate(System.currentTimeMillis());
        record.setOperateUserId(scheme.getPutintoUserId());
        record.setOperateUserName(scheme.getPutintoUser());
        record.setSchemaId(scheme.getId());
        // TODO
        // record.setCreateUser(LoginUserUtil.getLoginUser().getId());
        if (codeEnum == null) {
            record.setContent(RespBasicCode.PARAMETER_ERROR.getResultDes());
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        } else {
            record.setContent(codeEnum.getMsg());
        }
        assetOperationRecordDao.insert(record);

        if (scheme.getPutintoUserId() != null) {
            // TODO 调用工单接口
        }
    }

    public abstract Integer changeStatus(SchemeRequest schemeRequest, Scheme scheme) throws Exception;
}
