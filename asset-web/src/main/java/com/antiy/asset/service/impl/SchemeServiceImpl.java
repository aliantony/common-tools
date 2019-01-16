package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.ProcessTypeEnum;
import com.antiy.asset.vo.enums.SchemeTypeEnum;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.RespBasicCode;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;

/**
 * <p> 方案表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService {

    @Resource
    private SchemeDao                             schemeDao;
    @Resource
    private AssetDao                              assetDao;
    @Resource
    private AssetOperationRecordDao               operationRecordDao;
    @Resource
    private BaseConverter<SchemeRequest, Scheme>  requestConverter;
    @Resource
    private BaseConverter<Scheme, SchemeResponse> responseConverter;
    @Resource
    private TransactionTemplate                   transactionTemplate;
    private static final Logger                   LOGGER = LogUtils.get(AssetSoftwareServiceImpl.class);

    @Override
    public Integer saveScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        scheme.setGmtCreate(System.currentTimeMillis());
        // scheme.setCreateUser(LoginUserUtil.getLoginUser().getId());

        Integer targetStatus = request.getTargetStatus();
        Integer isAgree = request.getIsAgree();

        // 修改资产状态
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", new Object[] { request.getAssetId() });
        map.put("targetStatus", request.getTargetStatus());

        Integer flowType = scheme.getType();
        if (EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType()) != null) {
            switch (flowType) {
                case 1:
                    if (targetStatus.equals(AssetStatusEnum.WAIT_CHECK.getCode())) {
                        map.put("targetStatus", AssetStatusEnum.WAIT_CHECK.getCode());
                        map.put("limitStatus", AssetStatusEnum.WAIT_NET.getCode());
                    }
                    break;
                case 2:
                    if (targetStatus.equals(AssetStatusEnum.NET_IN.getCode())) {
                        map.put("targetStatus", AssetStatusEnum.NET_IN.getCode());
                        map.put("limitStatus", AssetStatusEnum.WAIT_CHECK.getCode());
                    }
                    break;
                // 制定待退役方案-由入网变为“待退役”
                case 3:
                    if (targetStatus.equals(AssetStatusEnum.WAIT_RETIRE.getCode())) {
                        map.put("targetStatus", AssetStatusEnum.WAIT_RETIRE.getCode());
                        map.put("limitStatus", AssetStatusEnum.NET_IN.getCode());
                    }
                    break;
                // 实施退役流程
                case 4:
                    if (!isAgree.equals(ProcessTypeEnum.YES.getCode())) {
                        map.put("targetStatus", AssetStatusEnum.NET_IN.getCode());
                        map.put("limitStatus", AssetStatusEnum.WAIT_RETIRE.getCode());
                    }
                case 5:
                    if (isAgree.equals(ProcessTypeEnum.YES.getCode())) {
                        map.put("targetStatus", AssetStatusEnum.RETIRE.getCode());
                        map.put("limitStatus", AssetStatusEnum.WAIT_RETIRE.getCode());
                    }
                    break;
                default:
                    break;
            }
        } else {
            throw new BusinessException(RespBasicCode.PARAMETER_ERROR.getResultDes());
        }
        // transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        Integer count = transactionTemplate.execute(transactionStatus -> {
            int row = 0;
            try {
                // TODO 调用工作流,推动流程start
                // 开始调用工作流接口,推动流程
                // TODO 调用工作流end
                schemeDao.insert(scheme);
                row = assetDao.changeStatus(map);
            } catch (Exception e) {
                LOGGER.error("保存信息失败", e);
            }

            return row;
        });

        // 记录操作历史
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        // 判断请求的方案类型
        SchemeTypeEnum codeEnum = EnumUtil.getByCode(SchemeTypeEnum.class, scheme.getType());
        assetOperationRecord.setTargetObjectId(DataTypeUtils.stringToInteger(request.getAssetId()));
        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());

        assetOperationRecord.setSchemaId(scheme.getId());
        if (count != null && 0 < count) {
            assetOperationRecord.setContent(codeEnum != null ? codeEnum.getMsg() : "类型参数异常");
        } else {
            assetOperationRecord.setContent(codeEnum != null ? codeEnum.getMsg() + "：保存信息失败" : "类型参数异常");
        }
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setOperateUserId(request.getPutintoUserId());
        assetOperationRecord.setOperateUserName(request.getPutintoUser());
        // assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());

        operationRecordDao.insert(assetOperationRecord);
        return count;
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        // scheme.setModifyUser(LoginUserUtil.getLoginUser().getId());
        scheme.setGmtModified(System.currentTimeMillis());
        return schemeDao.update(scheme);
    }

    @Override
    public List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception {
        List<Scheme> schemeList = schemeDao.findQuery(query);
        // TODO
        List<SchemeResponse> schemeResponse = responseConverter.convert(schemeList, SchemeResponse.class);
        return schemeResponse;
    }

    @Override
    public SchemeResponse findSchemeById(Integer id) throws Exception {
        return responseConverter.convert(super.getById(id), SchemeResponse.class);
    }

    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListScheme(query));
    }
}
