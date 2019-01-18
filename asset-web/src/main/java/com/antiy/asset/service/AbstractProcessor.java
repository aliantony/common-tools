package com.antiy.asset.service;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.impl.WorkOrderClientImpl;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.CodeEnum;
import com.antiy.asset.vo.enums.SchemeTypeEnum;
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
    public void process(String assetOperationEnum, Scheme scheme) throws Exception {
        AssetOperationRecordDao assetOperationRecordDao = (AssetOperationRecordDao) SpringUtil
            .getBean("assetOperationRecordDao");
        if (assetOperationEnum.equals(AssetOperationTableEnum.ASSET.getCode())) {
            // --------------------------------操作记录start------------------------------
            SchemeTypeEnum codeEnum = EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType());
            saveOperationRecord(codeEnum, scheme, assetOperationRecordDao);
            // --------------------------------操作记录end--------------------------------

            // --------------------------------工单start------------------------------
            if (scheme.getPutintoUserId() != null) {
                WorkOrderClientImpl workOrderClient = new WorkOrderClientImpl();
                WorkOrderVO workOrderVO = new WorkOrderVO();
                workOrderVO.setName(
                    codeEnum != null ? codeEnum.getMsg() + "工单" : RespBasicCode.PARAMETER_ERROR.getResultDes());
                workOrderVO.setWorkLevel(scheme.getOrderLevel());
                // 准入实施
                workOrderVO.setOrderType(8);
                // 资产管理
                workOrderVO.setOrderSource(1);
                workOrderVO.setContent(
                    codeEnum != null ? codeEnum.getMsg() + "申请" : RespBasicCode.PARAMETER_ERROR.getResultDes());
                workOrderVO.setExecuteUserId(scheme.getPutintoUserId());
                workOrderVO.setExecuteUserName(scheme.getPutintoUser());
                workOrderVO.setCreateUser(1);
                workOrderVO.setStartTime(scheme.getExpecteStartTime());
                workOrderVO.setEndTime(scheme.getExpecteEndTime());
                workOrderClient.createWorkOrder(workOrderVO);
            }
            // --------------------------------工单end--------------------------------

        } else if (assetOperationEnum.equals(AssetOperationTableEnum.SOFTWARE.getCode())) {

        }
    }

    private void saveOperationRecord(CodeEnum codeEnum, Scheme scheme,
                                     AssetOperationRecordDao assetOperationRecordDao) throws Exception {
        AssetOperationRecord record = new AssetOperationRecord();
        record.setTargetObjectId(scheme.getAssetId());
        record.setTargetType(AssetOperationTableEnum.ASSET.getCode());
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

    public abstract void changeStatus(SchemeRequest schemeRequest) throws Exception;
}
