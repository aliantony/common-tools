package com.antiy.asset.service;

import javax.annotation.Resource;

import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.SchemeTypeEnum;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;

/**
 * @author zhangyajun
 * @create 2019-01-16 17:43
 **/
public abstract class AbstractProcessor implements Processor {
    @Resource
    private BaseConverter<SchemeRequest, Scheme> requestConverter;
    // @Resource
    // private BaseConverter<AssetRequest, Asset> requestConverter;
    @Resource
    private AssetOperationRecordDao              operationRecordDao;

    @Override
    public void saveOperationHistory(SchemeRequest schemeRequest, AssetOperationRecord record) throws Exception {

        // if (basicRequest instanceof SchemeRequest) {
        // SchemeRequest schemeRequest = (SchemeRequest) basicRequest;
        // if (schemeRequest.getTopCategory().equals(AssetOperationTableEnum.ASSET.getCode())){
        Scheme scheme = requestConverter.convert(schemeRequest, Scheme.class);
        // 判断请求的方案类型
        SchemeTypeEnum codeEnum = EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType());
        record.setTargetObjectId(scheme.getAssetId());
        record.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        record.setSchemaId(scheme.getId());
        record.setGmtCreate(System.currentTimeMillis());
        record.setOperateUserId(scheme.getPutintoUserId());
        record.setOperateUserName(scheme.getPutintoUser());
        //TODO
        // record.setCreateUser(LoginUserUtil.getLoginUser().getId());
        if (codeEnum == null){
            record.setContent(RespBasicCode.PARAMETER_ERROR.getResultDes());
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        }else {
            record.setContent(codeEnum.getMsg());
        }
        operationRecordDao.insert(record);
    }

    public abstract Integer changeStatus(SchemeRequest schemeRequest);
}
