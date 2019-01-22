package com.antiy.asset.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.vo.request.ActivityHandleRequest;
import com.antiy.common.base.BusinessData;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.AssetOperationRecord;
import com.antiy.asset.entity.Scheme;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.impl.WorkOrderClientImpl;
import com.antiy.asset.service.ISchemeService;
import com.antiy.asset.service.OperationWOProcessor;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.request.SchemeRequest;
import com.antiy.asset.vo.response.SchemeResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 方案表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements ISchemeService {

    private static final Logger                  LOGGER = LogUtils.get(SchemeServiceImpl.class);

    @Resource
    private SchemeDao                            schemeDao;
    @Resource
    private AssetOperationRecordDao              assetOperationRecordDao;
    @Resource
    private AssetDao                             assetDao;
    @Resource
    private WorkOrderClientImpl                  workOrderClient;
    @Resource
    private AesEncoder                           aesEncoder;
    @Resource
    private BaseConverter<SchemeRequest, Scheme> requestConverter;
    @Resource
    private SchemeResponseConverter              responseConverter;
    @Resource
    TransactionTemplate                          transactionTemplate;
    @Resource
    private ActivityClient                       activityClient;

    @Override
    public String saveScheme(SchemeRequest schemeRequest) throws Exception {

        Integer schemeId = null;
        if (schemeRequest.getTopCategory().equals(AssetOperationTableEnum.ASSET.getCode())) {
            // schemeId = assetStatusProcessor.process(request);
            // Scheme scheme = (Scheme) BeanConvert.convert(schemeRequest, Scheme.class);
            Scheme scheme = new Scheme();
            scheme.setAssetd(1);
            scheme.setPutintoUserId(1);
            scheme.setPutintoUser("张三");

            Integer assetStatus = schemeRequest.getAssetStatus();
            Integer targetStatus = AssetStatusJumpEnum.getNextStatus(schemeRequest.getAssetStatus(),
                schemeRequest.getIsAgree());
            if (targetStatus != -1) {
                // 修改状态
                Map<String, Object> map = new HashMap<>();
                map.put("ids", new Object[] { DataTypeUtils.stringToInteger(schemeRequest.getAssetId()) });
                map.put("targetStatus", targetStatus);
                map.put("modifyUser", LoginUserUtil.getLoginUser().getId());
                map.put("gmtModified", System.currentTimeMillis());
                return transactionTemplate.execute(transactionStatus -> {
                    try {

                        schemeDao.insert(scheme);
                        assetDao.changeStatus(map);
                        if (assetStatus.equals(AssetStatusEnum.NET_IN.getCode()) && targetStatus.equals(AssetStatusEnum.WAIT_RETIRE.getCode())){
                            //启动待退役流程
                            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
                            manualStartActivityRequest.setBusinessId(scheme.getAssetId().toString());
                            manualStartActivityRequest.setFormData(JSONObject.toJSONString(schemeRequest));
                            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
                            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_RETIRE.getCode());
                            activityClient.manualStartProcess(manualStartActivityRequest);
                        }else {
                            // --------------------------------调用工作流start------------------------------
                            ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
                            activityHandleRequest.setTaskId(schemeRequest.getTaskId());
                            activityHandleRequest.setFormData(JSONObject.toJSONString(schemeRequest));
                            activityClient.completeTask(activityHandleRequest);
                            // --------------------------------调用工作流end------------------------------
                        }

                        // --------------------------------操作记录start------------------------------
                        AssetFlowEnum assetFlowEnum = EnumUtil.getByCode(AssetFlowEnum.class,
                            schemeRequest.getAssetStatus());
                        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                        assetOperationRecord.setTargetType(schemeRequest.getTopCategory());
                        assetOperationRecord.setTargetStatus(targetStatus);
                        assetOperationRecord.setTargetObjectId(scheme.getAssetId());
                        assetOperationRecord.setSchemeId(scheme.getId());
                        OperationWOProcessor.saveOperationRecord(assetFlowEnum, assetOperationRecord,
                            assetOperationRecordDao, schemeRequest.getTopCategory());
                        // --------------------------------操作记录end--------------------------------

                        BusinessData businessData = new BusinessData();
                        // 事件名 在这可以写登记资产
                        businessData.setIncident(assetFlowEnum.getMsg());
                        // 操作的业务阶段
                        businessData.setBusinessPhase(assetFlowEnum.getCode());
                        // 操作的资产id
                        businessData.setManageObjId(scheme.getAssetId());
                        // 模块id ModuleEnum
                        businessData.setModuleId(1);
                        // 附加信息
                        businessData.setInformation(schemeRequest.getMemo());
                        LogUtils.recordOperLog(businessData);


                        // --------------------------------工单start------------------------------
                        // 目前只有待入网操作时，才有工单
                        if (assetStatus.equals(AssetStatusEnum.WAIT_NET.getCode())) {
                            OperationWOProcessor.createWordOrder(workOrderClient, schemeRequest,
                                EnumUtil.getByCode(AssetFlowEnum.class, schemeRequest.getAssetStatus()));
                        }
                        // --------------------------------工单end--------------------------------
                    } catch (Exception e) {
                        LOGGER.error("保存信息失败", e);
                    }
                    return scheme.getId().toString();
                });
            } else {
                throw new BusinessException("修改状态失败：无法获取下一个状态");
            }

        } else if (schemeRequest.getTopCategory().equals(AssetOperationTableEnum.SOFTWARE.getCode())) {

        }
        return aesEncoder.decode(schemeId.toString(), LoginUserUtil.getLoginUser().getPassword());
    }

    @Override
    public Integer updateScheme(SchemeRequest request) throws Exception {
        Scheme scheme = requestConverter.convert(request, Scheme.class);
        scheme.setModifyUser(LoginUserUtil.getLoginUser().getId());
        scheme.setGmtModified(System.currentTimeMillis());
        return schemeDao.update(scheme);
    }

    @Override
    public List<SchemeResponse> findListScheme(SchemeQuery query) throws Exception {
        List<Scheme> schemeList = schemeDao.findQuery(query);
        return responseConverter.convert(schemeList, SchemeResponse.class);
    }

    @Override
    public SchemeResponse findSchemeById(String id) throws Exception {
        return responseConverter.convert(super.getById(id), SchemeResponse.class);
    }

    @Override
    public PageResult<SchemeResponse> findPageScheme(SchemeQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListScheme(query));
    }
}

@Component
class SchemeResponseConverter extends BaseConverter<Scheme, SchemeResponse> {
    Logger logger = LogUtils.get(SchemeResponseConverter.class);

    @Override
    protected void convert(Scheme scheme, SchemeResponse schemeResponse) {
        try {
            schemeResponse.setId(Objects.toString(scheme.getId()));
            schemeResponse.setAssetId(Objects.toString(scheme.getAssetId()));
        } catch (Exception e) {
            logger.error("String转Integer出错");
        }
        super.convert(scheme, schemeResponse);
    }
}
