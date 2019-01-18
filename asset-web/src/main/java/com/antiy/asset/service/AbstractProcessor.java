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

    private AssetOperationRecordDao assetOperationRecordDao = (AssetOperationRecordDao) SpringUtil
        .getBean("assetOperationRecordDao");

    @Override
    public void process(String assetOperationEnum, Scheme scheme) throws Exception {
        if (assetOperationEnum.equals(AssetOperationTableEnum.ASSET.getCode())) {
            // --------------------------------操作记录start------------------------------
            SchemeTypeEnum codeEnum = EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType());
            saveOperationRecord(codeEnum, scheme);
            // --------------------------------操作记录end--------------------------------

            // --------------------------------工单start------------------------------
            if (scheme.getPutintoUserId() != null) {
                WorkOrderClientImpl workOrderClient = new WorkOrderClientImpl();
                WorkOrderVO workOrderVO = new WorkOrderVO();
                workOrderVO.setName("准入实施工单");
                workOrderVO.setWorkLevel(scheme.getOrderLevel());
                //准入实施
                workOrderVO.setOrderType(8);
                //资产管理
                workOrderVO.setOrderSource(1);
                workOrderVO.setContent("入网申请");
                workOrderVO.setExecuteUserId(scheme.getPutintoUserId());
                workOrderVO.setExecuteUserName(scheme.getPutintoUser());
                workOrderVO.setCreateUser(1);
                workOrderVO.setStartTime(scheme.getExpecteStartTime());
                workOrderVO.setEndTime(scheme.getExpecteEndTime());
                // TODO 附件
                workOrderClient.createWorkOrder(workOrderVO);
            }
            // --------------------------------工单end--------------------------------

        } else if (assetOperationEnum.equals(AssetOperationTableEnum.SOFTWARE.getCode())) {

        }
    }

    private void saveOperationRecord(CodeEnum codeEnum, Scheme scheme) throws Exception {
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

    // public AssetOperationRecord saveOperationRecord(Scheme scheme) throws Exception {
    // AssetOperationRecord record = new AssetOperationRecord();
    // // 判断请求的方案类型
    // SchemeTypeEnum codeEnum = EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType());
    // record.setTargetObjectId(scheme.getAssetId());
    // record.setTargetType(AssetOperationTableEnum.ASSET.getCode());
    // record.setSchemaId(scheme.getId());
    // record.setGmtCreate(System.currentTimeMillis());
    // record.setOperateUserId(scheme.getPutintoUserId());
    // record.setOperateUserName(scheme.getPutintoUser());
    // // TODO
    // // record.setCreateUser(LoginUserUtil.getLoginUser().getId());
    // if (codeEnum == null) {
    // record.setContent(RespBasicCode.PARAMETER_ERROR.getResultDes());
    // throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
    // } else {
    // record.setContent(codeEnum.getMsg());
    // }
    // return record;
    // }

    public abstract Integer changeStatus(SchemeRequest schemeRequest) throws Exception;
}
