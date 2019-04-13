package com.antiy.asset.service.impl;

import static com.antiy.asset.vo.enums.AssetFlowEnum.HARDWARE_CONFIG_BASELINE;
import static com.antiy.asset.vo.enums.SoftwareFlowEnum.SOFTWARE_INSTALL_CONFIG;
import static com.antiy.biz.file.FileHelper.logger;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.*;
import com.antiy.asset.templet.AssetSoftwareEntity;
import com.antiy.asset.templet.ExportSoftwareEntity;
import com.antiy.asset.templet.ImportResult;
import com.antiy.asset.util.*;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.base.Constants;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.*;

/**
 * <p> 软件信息表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AssetSoftwareServiceImpl extends BaseServiceImpl<AssetSoftware> implements IAssetSoftwareService {
    @Resource
    private AssetSoftwareDao                                                 assetSoftwareDao;
    @Resource
    private AesEncoder                                                       aesEncoder;
    @Resource
    private AssetSoftwareRelationDao                                         assetSoftwareRelationDao;
    @Resource
    private AssetSoftwareLicenseDao                                          assetSoftwareLicenseDao;
    @Resource
    private AssetPortProtocolDao                                             assetPortProtocolDao;
    @Resource
    private AssetCategoryModelDao                                            assetCategoryModelDao;
    @Resource
    private AssetOperationRecordDao                                          assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetSoftwareRequest, AssetSoftware>               requestConverter;

    @Resource
    private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense> assetSoftwareLicenseBaseConverter;

    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareDetailResponse>        assetSoftwareDetailConverter;

    @Resource
    private IAssetPortProtocolService                                        iAssetPortProtocolService;

    @Resource
    private IAssetSoftwareLicenseService                                     iAssetSoftwareLicenseService;

    @Resource
    private IAssetCategoryModelService                                       iAssetCategoryModelService;

    @Resource
    private TransactionTemplate                                              transactionTemplate;

    @Resource
    private ExcelDownloadUtil                                                excelDownloadUtil;

    @Resource
    private ActivityClient                                                   activityClient;
    @Resource
    private AreaClient                                                       areaClient;

    @Resource
    private SoftwareEntityConvert                                            softwareEntityConvert;

    private static final Logger                                              LOGGER = LogUtils
        .get(AssetSoftwareServiceImpl.class);
    @Resource
    private AssetChangeRecordDao                                             assetChangeRecordDao;
    @Resource
    private OperatingSystemClient                                            operatingSystemClient;
    @Resource
    private BaseLineClient                                                   baseLineClient;
    @Resource
    private SchemeDao                                                        schemeDao;
    @Resource
    private AssetDao                                                         assetDao;
    @Resource
    private IRedisService                                                    redisService;

    @Override
    public ActionResponse saveAssetSoftware(AssetSoftwareRequest request) throws Exception {
        Integer num = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);

                    // AssetSoftwareLicense license = BeanConvert.convertBean(request.getSoftwareLicenseRequest(),
                    // AssetSoftwareLicense.class);
                    // AssetPortProtocol protocol = BeanConvert.convertBean(request.getAssetPortProtocolRequest(),
                    // AssetPortProtocol.class);

                    ParamterExceptionUtils.isTrue(!CheckRepeatName(assetSoftware.getName()), "资产名称重复");
                    BusinessExceptionUtils.isTrue(checkOperatingSystemById(assetSoftware.getOperationSystem()),
                        "兼容系统不存在，或已经注销！");
                    BusinessExceptionUtils.isTrue(
                        !Objects.isNull(assetCategoryModelDao.getById(
                            com.antiy.common.utils.DataTypeUtils.stringToInteger(assetSoftware.getCategoryModel()))),
                        "品类型号不存在，或已经注销");

                    assetSoftware.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetSoftware.setGmtCreate(System.currentTimeMillis());
                    assetSoftware.setSoftwareStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
                    assetSoftwareDao.insert(assetSoftware);
                    String sid = String.valueOf(assetSoftware.getId());
                    // protocol.setAssetSoftId(sid);
                    // protocol.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    // protocol.setGmtCreate(System.currentTimeMillis());
                    // assetPortProtocolDao.insert(protocol);
                    // license.setSoftwareId(assetSoftware.getId());
                    // license.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    // license.setGmtCreate(System.currentTimeMillis());
                    // license.setExpiryDate(assetSoftware.getServiceLife());
                    // license.setBuyDate(assetSoftware.getBuyDate());
                    // assetSoftwareLicenseDao.insert(license);

                    // if (ArrayUtils.isNotEmpty(request.getAssetIds())) {
                    // String[] assetIds = request.getAssetIds();
                    // for (String s : assetIds) {
                    // AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                    // assetSoftwareRelation.setSoftwareId(sid);
                    // assetSoftwareRelation.setAssetId(s);
                    // assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                    // assetSoftwareRelation.setSoftwareStatus(AssetStatusEnum.ANALYZE.getCode());
                    // assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    // assetSoftwareRelationDao.insert(assetSoftwareRelation);
                    // }
                    // }
                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(sid);
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
                    assetOperationRecord.setOriginStatus(SoftwareStatusEnum.WATI_REGSIST.getCode());
                    assetOperationRecord.setTargetStatus(SoftwareStatusEnum.WAIT_ANALYZE.getCode());
                    assetOperationRecord.setContent("登记软件资产");
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecord.setProcessResult(1);
                    assetOperationRecordDao.insert(assetOperationRecord);
                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSERT.getName(), assetSoftware.getId(),
                        null, assetSoftware, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
                    LogUtils.info(logger, AssetEventEnum.SOFT_INSERT.getName() + " {}", assetSoftware);
                    return DataTypeUtils.stringToInteger(sid);
                } catch (RequestParamValidateException e) {
                    transactionStatus.setRollbackOnly();
                    ParamterExceptionUtils.isTrue(false, "资产名称重复");
                    logger.error("录入失败", e);

                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("品类型号不存在，或已经注销", e.getMessage()),
                        "品类型号不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("兼容系统存在，或已经注销！", e.getMessage()),
                        "兼容系统存在，或已经注销！");
                    logger.error("录入失败", e);
                }
                return 0;
            }
        });

        // if (num != null && num > 0) {
        // // 启动流程
        // ManualStartActivityRequest activityRequest = request.getActivityRequest();
        // activityRequest.setBusinessId(String.valueOf(num));
        // activityRequest.setAssignee(DataTypeUtils.integerToString(LoginUserUtil.getLoginUser().getId()));
        // activityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.SOFTWARE_ADMITTANCE.getCode());
        // activityRequest.setAssignee(LoginUserUtil.getLoginUser().getId() + "");
        // ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
        // // 如果流程引擎为空,直接返回错误信息
        // if (null == actionResponse
        // || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
        // return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        // }

        // 对接配置模块
        // ConfigRegisterRequest configRegisterRequest = new ConfigRegisterRequest();
        // configRegisterRequest.setAssetId(String.valueOf(num));
        // configRegisterRequest.setSource(String.valueOf(AssetTypeEnum.SOFTWARE.getCode()));
        // configRegisterRequest.setSuggest(request.getMemo());
        // configRegisterRequest.setConfigUserIds(request.getActivityRequest().getConfigUserIds());
        // configRegisterRequest.setRelId(String.valueOf(num));
        // ActionResponse actionResponseSoftware = this.configRegister(configRegisterRequest);
        // if (null == actionResponseSoftware
        // || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponseSoftware.getHead().getCode())) {
        // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSERT.getName(), num, null,
        // configRegisterRequest, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
        // // 记录操作日志和运行日志
        // LogUtils.info(logger, AssetEventEnum.SOFT_INSERT.getName() + " {}", configRegisterRequest);
        // // 调用失败，逻辑删登记的资产
        // assetSoftwareDao.deleteById(num);
        // return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION);
        // }
        // }

        return ActionResponse.success(num);

    }

    /**
     * 判断操作系统是否存在
     * @return
     */
    private Boolean checkOperatingSystem(String checkStr) {
        List<LinkedHashMap> categoryOsResponseList = operatingSystemClient.getInvokeOperatingSystem();
        for (LinkedHashMap linkedHashMap : categoryOsResponseList) {
            if (Objects.equals(linkedHashMap.get("name"), checkStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过ID判断操作系统是否存在且是叶子节点
     * @return
     */
    private Boolean checkOperatingSystemById(String id) {
        List<BaselineCategoryModelNodeResponse> baselineCategoryModelNodeResponse = operatingSystemClient
            .getInvokeOperatingSystemTree();
        Set<String> result = new HashSet<>();
        if (CollectionUtils.isNotEmpty(baselineCategoryModelNodeResponse)) {
            operatingSystemRecursion(result, baselineCategoryModelNodeResponse.get(0));
        }
        return result.contains(id);
    }

    private void operatingSystemRecursion(Set<String> result, BaselineCategoryModelNodeResponse response) {
        if (response != null) {
            if (CollectionUtils.isEmpty(response.getChildrenNode())) {
                result.add(response.getStringId());
            } else {
                for (BaselineCategoryModelNodeResponse baselineCategoryModelNodeResponse : response.getChildrenNode()) {
                    operatingSystemRecursion(result, baselineCategoryModelNodeResponse);
                }
            }
        }
    }

    @Override
    @Transactional
    public Integer batchSave(List<AssetSoftware> assetSoftwareList) throws Exception {
        int i = 0;
        for (; i < assetSoftwareList.size(); i++) {
            AssetSoftware t = assetSoftwareList.get(i);
            t.setGmtCreate(System.currentTimeMillis());
            t.setSoftwareStatus(3);
            assetSoftwareDao.insert(t);
        }
        return i + 1;
    }

    @Override
    public Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception {
        // 如果软件已退役，修改资产状态为待分析，并启动登记流程
        AssetSoftware software = assetSoftwareDao.getById(request.getId());
        Integer softwareStatus = software.getSoftwareStatus();
        if (request.getActivityRequest() != null && softwareStatus.equals(SoftwareStatusEnum.RETIRE.getCode())
            || softwareStatus.equals(SoftwareStatusEnum.NOT_REGSIST.getCode())) {
            ParamterExceptionUtils.isNull(request.getActivityRequest(), "activityRequest参数不能为空");
            ActionResponse actionResponse = activityClient.manualStartProcess(request.getActivityRequest());
            // 如果流程引擎为空,直接返回-1
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                return -1;
            }
            // 设置软件状态为待分析
            request.setSoftwareStatus(SoftwareStatusEnum.WAIT_ANALYZE.getCode());
        }
        Integer count = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 1.更新软件信息
                    AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
                    assetSoftware.setId(DataTypeUtils.stringToInteger(request.getId()));
                    int assetSoftwareCount = assetSoftwareDao.update(assetSoftware);

                    /**
                     * // 2.更新license表 if (null != request.getSoftwareLicenseRequest() &&
                     * StringUtils.isNotBlank(request.getSoftwareLicenseRequest().getId())) { updateLicense(request); }
                     *
                     * // 3.是否存在关联表Id和状态，如果存在，则更新关联表即可(更新某一个实例) if
                     * (StringUtils.isNotBlank(request.getAssetSoftwareRelationId()) && request.getSoftwareStatus() !=
                     * null) { AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                     * assetSoftwareRelation.setId(DataTypeUtils.stringToInteger(request.getAssetSoftwareRelationId()));
                     * assetSoftwareRelation.setSoftwareStatus(request.getSoftwareStatus());
                     * assetSoftwareRelationDao.update(assetSoftwareRelation); } else if
                     * (ArrayUtils.isNotEmpty(request.getAssetIds())) { // 更新一批实例 // 4.移除端口和关联表的关系 List<Integer>
                     * releationIds = assetSoftwareRelationDao.getAllReleationId(null,
                     * DataTypeUtils.stringToInteger(request.getId())); if (CollectionUtils.isNotEmpty(releationIds)) {
                     * assetPortProtocolDao.deletePortProtocol(releationIds); }
                     *
                     * // 5.移除关系表 assetSoftwareRelationDao.deleteSoftwareRelAsset(null,
                     * DataTypeUtils.stringToInteger(request.getId()));
                     *
                     * // 5.插入关系表，并且插入端口数据 for (String assetId : request.getAssetIds()) { AssetSoftwareRelation
                     * assetSoftwareRelation = new AssetSoftwareRelation();
                     * assetSoftwareRelation.setSoftwareId(request.getId()); assetSoftwareRelation.setAssetId(assetId);
                     * assetSoftwareRelation.setSoftwareStatus(request.getSoftwareStatus());
                     * assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                     * assetSoftwareRelationDao.insert(assetSoftwareRelation);
                     * ParamterExceptionUtils.isNull(assetSoftwareRelation.getId(), "更新软件失败");
                     *
                     * // 批量插入端口信息 if (ArrayUtils.isNotEmpty(request.getAssetPortProtocolRequest().getPort())) {
                     * AssetPortProtocol protocol = new BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>()
                     * .convert(request.getAssetPortProtocolRequest(), AssetPortProtocol.class);
                     * protocol.setAssetSoftId(assetSoftwareRelation.getStringId()); for (Integer port :
                     * request.getAssetPortProtocolRequest().getPort()) { protocol.setPort(port); // 插入端口信息
                     * assetPortProtocolDao.insert(protocol); } }
                     *
                     * } }
                     */
                    // 记录更新操作
                    assetOperationRecordDao.insert(convertAssetOperationRecord(request, softwareStatus));
                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_UPDATE.getName(), assetSoftware.getId(),
                        null, assetSoftware, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
                    LogUtils.info(logger, AssetEventEnum.SOFT_UPDATE.getName() + " {}", assetSoftware);
                    return assetSoftwareCount;
                } catch (Exception e) {
                    LOGGER.error("修改软件信息失败", e);
                }
                return 0;
            }
        });

        // TODO 调用工作流，给配置管理员
        // activityClient.completeTask(request.getRequest());
        return count;
    }

    private AssetOperationRecord convertAssetOperationRecord(AssetSoftwareRequest request, Integer softwareStatus) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        if (softwareStatus.equals(SoftwareFlowEnum.SOFTWARE_RETIRE_REGISTER.getCode())) {
            assetOperationRecord.setOriginStatus(SoftwareStatusEnum.RETIRE.getCode());
            assetOperationRecord.setContent(SoftwareFlowEnum.SOFTWARE_RETIRE_REGISTER.getMsg());
        } else if (softwareStatus.equals(SoftwareFlowEnum.SOFTWARE_NOT_REGSIST_REGISTER.getCode())) {
            assetOperationRecord.setOriginStatus(SoftwareStatusEnum.NOT_REGSIST.getCode());
            assetOperationRecord.setContent(SoftwareFlowEnum.SOFTWARE_NOT_REGSIST_REGISTER.getMsg());
        }
        assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
        assetOperationRecord.setTargetObjectId(request.getId());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setProcessResult(1);
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        return assetOperationRecord;
    }

    /**
     * 更新lincense
     * @param request
     */
    private void updateLicense(AssetSoftwareRequest request) throws Exception {
        AssetSoftwareLicense assetSoftwareLicense = assetSoftwareLicenseBaseConverter
            .convert(request.getSoftwareLicenseRequest(), AssetSoftwareLicense.class);
        assetSoftwareLicense.setSoftwareId(request.getId());
        // 写入业务日志
        LogHandle.log(assetSoftwareLicense.toString(), AssetEventEnum.SOFT_INSERT.getName(),
            AssetEventEnum.SOFT_LICENSE_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.SOFT_LICENSE_UPDATE.getName() + " {}", assetSoftwareLicense.toString());
        assetSoftwareLicenseDao.update(assetSoftwareLicense);
    }

    private Map<Integer, Long> handleSoftCount(List<Map<String, Object>> softObjectList) {
        Map<Integer, Long> map = new HashMap<>();
        for (Map<String, Object> objectMap : softObjectList) {
            Integer id = objectMap.get("id") != null ? Integer.valueOf(objectMap.get("id").toString()) : 0;
            Long count = objectMap.get("name") != null ? Long.parseLong(objectMap.get("name").toString()) : 0;
            map.put(id, count);
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception {
        List<AssetSoftware> assetSoftware = assetSoftwareDao.findListAssetSoftware(query);
        Map<Integer, Long> softAssetCount = null;
        if (query.getQueryAssetCount()) {
            List<Integer> allSoftwareIds = new ArrayList<>();
            assetSoftware.stream().forEach(assetSoftwareDO -> allSoftwareIds.add(assetSoftwareDO.getId()));
            softAssetCount = handleSoftCount(assetSoftwareRelationDao.countSoftwareRelAsset(allSoftwareIds));
        }

        Map<Integer, Long> finalSoftAssetCount = softAssetCount;
        BaseConverter baseConverter = new BaseConverter<AssetSoftware, AssetSoftwareResponse>() {

            @Override
            protected void convert(AssetSoftware assetSoftware, AssetSoftwareResponse assetSoftwareResponse) {
                super.convert(assetSoftware, assetSoftwareResponse);
                assetSoftwareResponse.setAssetCount(0);
                if (MapUtils.isNotEmpty(finalSoftAssetCount)) {
                    assetSoftwareResponse.setAssetCount(finalSoftAssetCount.get(assetSoftware.getId()) != null
                        ? finalSoftAssetCount.get(assetSoftware.getId()).intValue()
                        : 0);
                }
            }
        };
        List<AssetSoftwareResponse> objects = baseConverter.convert(assetSoftware, AssetSoftwareResponse.class);
        return objects;
    }

    public Integer findCountAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findCount(query);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception {
        if (!Objects.isNull(query.getCategoryModels()) && query.getCategoryModels().length > 0) {
            List<Integer> categoryModels = Lists.newArrayList();
            for (int i = 0; i < query.getCategoryModels().length; i++) {
                categoryModels.addAll(iAssetCategoryModelService.findAssetCategoryModelIdsById(
                    com.antiy.common.utils.DataTypeUtils.stringToInteger(query.getCategoryModels()[i])));
            }
            query.setCategoryModels(com.antiy.common.utils.DataTypeUtils.integerArrayToStringArray(categoryModels));
        }
        // 如果count 小于等于0，则表示没数据，也直接返回
        int count = this.findCountAssetSoftware(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), null);
        }

        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), this.findListAssetSoftware(query));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> getManufacturerName(String manufacturerName) throws Exception {
        List<Integer> list = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        return assetSoftwareDao.findManufacturerName(manufacturerName, list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<EnumCountResponse> countManufacturer() throws Exception {
        int maxNum = 5;
        List<Integer> status = StatusEnumUtil.getSoftwareNotRetireStatusList();
        List<Map<String, Object>> list = assetSoftwareDao.countManufacturer(status);
        return CountTypeUtil.getEnumCountResponse(maxNum, list);

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<EnumCountResponse> countStatus() throws Exception {
        List<EnumCountResponse> resultList = new ArrayList<>();
        List<Map<String, Object>> list = assetSoftwareDao.countStatus();
        Map<SoftwareStatusEnum, EnumCountResponse> resultMap = new HashMap<>();
        // 初始化result
        initResultMap(resultMap);
        // 将查询结果的值放入结果集
        searchResultToMap(resultList, list, resultMap);
        // 将结果集中的数据放至结果列表中
        resultMapToResultList(resultList, resultMap);
        return resultList;
    }

    private void resultMapToResultList(List<EnumCountResponse> resultList,
                                       Map<SoftwareStatusEnum, EnumCountResponse> resultMap) {
        for (Map.Entry<SoftwareStatusEnum, EnumCountResponse> entry : resultMap.entrySet()) {
            resultList.add(entry.getValue());
        }
    }

    private void searchResultToMap(List<EnumCountResponse> resultList, List<Map<String, Object>> list,
                                   Map<SoftwareStatusEnum, EnumCountResponse> resultMap) {
        for (Map map : list) {
            SoftwareStatusEnum softwareStatusEnum = SoftwareStatusEnum.getAssetByCode((Integer) map.get("key"));
            if (softwareStatusEnum != null) {
                // 待退役和待退役分析视为同一状态
                if (softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_RETIRE)
                    || softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_ANALYZE_RETIRE)) {
                    processList(resultList, resultMap, map, SoftwareStatusEnum.WAIT_RETIRE);
                }
                // 待分析和待卸载分析视为同一状态
                else if (softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_ANALYZE)
                         || softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_ANALYZE_UNINSTALL)) {
                    processList(resultList, resultMap, map, SoftwareStatusEnum.WAIT_ANALYZE);
                } else if (!softwareStatusEnum.equals(SoftwareStatusEnum.UNINSTALL)) {
                    EnumCountResponse e = resultMap.get(softwareStatusEnum);
                    e.setNumber((Long) map.get("value"));
                }
            }
        }
    }

    private void initResultMap(Map<SoftwareStatusEnum, EnumCountResponse> resultMap) {
        for (SoftwareStatusEnum softwareStatusEnum : SoftwareStatusEnum.values()) {
            EnumCountResponse enumCountResponse;
            if (softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_RETIRE)
                || softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_ANALYZE_RETIRE)) {
                String waitRetire = SoftwareStatusEnum.WAIT_RETIRE.getMsg();
                List<String> codeList = new ArrayList<>();
                codeList.add(SoftwareStatusEnum.WAIT_RETIRE.getCode() + "");
                codeList.add(SoftwareStatusEnum.WAIT_ANALYZE_RETIRE.getCode() + "");
                enumCountResponse = new EnumCountResponse(SoftwareStatusEnum.WAIT_RETIRE.getMsg(), codeList, 0);
                resultMap.put(SoftwareStatusEnum.WAIT_RETIRE, enumCountResponse);
            }
            // 待分析和待卸载分析视为同一状态
            else if (softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_ANALYZE)
                     || softwareStatusEnum.equals(SoftwareStatusEnum.WAIT_ANALYZE_UNINSTALL)) {
                String waitAnalyze = SoftwareStatusEnum.WAIT_ANALYZE.getMsg();
                List<String> codeList = new ArrayList<>();
                codeList.add(SoftwareStatusEnum.WAIT_ANALYZE.getCode() + "");
                codeList.add(SoftwareStatusEnum.WAIT_ANALYZE_UNINSTALL.getCode() + "");
                enumCountResponse = new EnumCountResponse(SoftwareStatusEnum.WAIT_ANALYZE.getMsg(), codeList, 0);
                resultMap.put(SoftwareStatusEnum.WAIT_ANALYZE, enumCountResponse);
            } else if (!softwareStatusEnum.equals(SoftwareStatusEnum.UNINSTALL)) {
                enumCountResponse = new EnumCountResponse(softwareStatusEnum.getMsg(),
                    softwareStatusEnum.getCode() + "", 0);
                resultMap.put(softwareStatusEnum, enumCountResponse);
            }
        }
    }

    private void processList(List<EnumCountResponse> resultList, Map<SoftwareStatusEnum, EnumCountResponse> resultMap,
                             Map map, SoftwareStatusEnum softwareStatusEnum) {
        EnumCountResponse e = resultMap.get(softwareStatusEnum);
        Long waitAnalyzeNum = e.getNumber();
        e.setNumber(waitAnalyzeNum == null ? (Long) map.get("value") : (waitAnalyzeNum + (Long) map.get("value")));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<EnumCountResponse> countCategory() throws Exception {
        // 查询第二级分类id
        List<EnumCountResponse> resultList = new ArrayList<>();
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("软件");
        HashMap<String, Long> result = new HashMap<>();
        List<AssetCategoryModel> categoryModelDaoAll = assetCategoryModelDao.getAll();
        if (CollectionUtils.isNotEmpty(categoryModelDaoAll)) {
            for (AssetCategoryModel secondCategoryModel : secondCategoryModelList) {
                EnumCountResponse enumCountResponse = new EnumCountResponse();
                // 查询第二级每个分类下所有的分类id，并添加至list集合
                List<AssetCategoryModel> search = iAssetCategoryModelService.recursionSearch(categoryModelDaoAll,
                    secondCategoryModel.getId());
                List<String> idList = iAssetCategoryModelService.getCategoryIdList(search);
                enumCountResponse.setCode(idList);
                // 设置查询资产条件参数，包括，状态，资产品类型号
                AssetSoftwareQuery assetSoftwareQuery = setAssetSoftwareQueryParam(search);
                // 将查询结果放置结果集
                enumCountResponse.setMsg(secondCategoryModel.getName());
                enumCountResponse.setNumber(assetSoftwareDao.findCountByCategoryModel(assetSoftwareQuery));
                resultList.add(enumCountResponse);
            }
            return resultList;
        }
        return null;
    }

    private AssetSoftwareQuery setAssetSoftwareQueryParam(List<AssetCategoryModel> search) {
        String[] list = new String[search.size()];
        for (int i = 0; i < search.size(); i++) {
            list[i] = search.get(i).getStringId();
        }
        List status = StatusEnumUtil.getSoftwareNotRetireStatusList();
        AssetSoftwareQuery assetSoftwareQuery = new AssetSoftwareQuery();
        assetSoftwareQuery.setCategoryModels(list);
        assetSoftwareQuery.setSoftwareStatusList(status);
        return assetSoftwareQuery;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AssetSoftwareDetailResponse querySoftWareDetail(SoftwareQuery softwareQuery) throws Exception {

        // 1 获取软件资产详情
        AssetSoftware assetSoftware = this.getById(softwareQuery.getPrimaryKey());
        AssetSoftwareDetailResponse assetSoftwareDetailResponse = assetSoftwareDetailConverter.convert(assetSoftware,
            AssetSoftwareDetailResponse.class);

        // TODO 软件资产码表信息

        // 2 是否需要查询端口信息
        if (softwareQuery.getQueryPort()) {
            querySoftwarePort(softwareQuery, assetSoftwareDetailResponse);
        }

        // 3 是否需要查询license信息
        if (softwareQuery.getQueryLicense()) {
            querySoftwareLicense(softwareQuery, assetSoftwareDetailResponse);
        }

        // 获取软件的操作系统名
        // 设置操作系统名
        setOperationName(assetSoftware, assetSoftwareDetailResponse);
        return assetSoftwareDetailResponse;
    }

    private void setOperationName(AssetSoftware assetSoftware,
                                  AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        if (StringUtils.isNotEmpty(assetSoftware.getOperationSystem())) {
            List<LinkedHashMap> categoryOsResponseList = redisService.getAllSystemOs();
            for (LinkedHashMap linkedHashMap : categoryOsResponseList) {
                if (assetSoftware.getOperationSystem().equals(linkedHashMap.get("stringId"))) {
                    assetSoftwareDetailResponse.setOperationSystemName((String) linkedHashMap.get("name"));
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void exportData(AssetSoftwareQuery assetSoftwareQuery, HttpServletResponse response) throws Exception {
        exportData("软件信息表", assetSoftwareQuery, response);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetSoftwareResponse> findInstallList(AssetSoftwareQuery softwareQuery) throws Exception {
        List<AssetSoftware> assetSoftwareList = assetSoftwareDao.findInstallList(softwareQuery);
        List<AssetSoftwareResponse> assetSoftwareResponseList = BeanConvert.convert(assetSoftwareList,
            AssetSoftwareResponse.class);
        return assetSoftwareResponseList;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSoftwareResponse> findPageInstall(AssetSoftwareQuery softwareQuery) throws Exception {
        return new PageResult<>(softwareQuery.getPageSize(), this.findCountInstall(softwareQuery),
            softwareQuery.getCurrentPage(), this.findInstallList(softwareQuery));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetSoftwareInstallResponse> findAssetInstallList(AssetSoftwareQuery softwareQuery) throws Exception {
        return BeanConvert.convert(assetSoftwareDao.findAssetInstallList(softwareQuery),
            AssetSoftwareInstallResponse.class);
    }

    public Integer findAssetInstallCount(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findAssetInstallCount(query);
    }

    private boolean CheckRepeatName(String name) throws Exception {
        AssetSoftwareQuery assetQuery = new AssetSoftwareQuery();
        assetQuery.setAssetName(name);
        Integer countAsset = assetSoftwareDao.findCountCheck(assetQuery);
        return countAsset >= 1;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSoftwareInstallResponse> findPageAssetInstall(AssetSoftwareQuery softwareQuery) throws Exception {
        // 只查询已入网、待退役的资产
        softwareQuery.setAssetStatus(new ArrayList<Integer>() {
            {
                add(AssetStatusEnum.NET_IN.getCode());
                add(AssetStatusEnum.WAIT_RETIRE.getCode());
            }
        });
        List<AssetSoftwareInstallResponse> assetSoftwareInstallResponseList = this.findAssetInstallList(softwareQuery);
        if (CollectionUtils.isEmpty(assetSoftwareInstallResponseList)) {
            return new PageResult<>(softwareQuery.getPageSize(), 0, softwareQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<>(softwareQuery.getPageSize(), this.findAssetInstallCount(softwareQuery),
            softwareQuery.getCurrentPage(), assetSoftwareInstallResponseList);
    }

    @Override
    public ActionResponse configRegister(ConfigRegisterRequest request) throws Exception {
        // 1.保存操作流程
        assetOperationRecordDao.insert(convertRecord(request));

        // 2.保存配置建议信息
        schemeDao.insert(convertScheme(request));

        // 3.调用配置接口
        List<ConfigRegisterRequest> configRegisterList = new ArrayList<>();
        configRegisterList.add(request);
        return baseLineClient.configRegister(configRegisterList);
    }

    /**
     * 处理资产上报的软件数据
     * @param assetId
     * @param softwareReportRequestList
     */
    @Override
    public void reportData(Integer assetId, List<AssetSoftwareReportRequest> softwareReportRequestList) {
        List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(softwareReportRequestList)) {
            softwareReportRequestList.stream().forEach(softwareReportRequest -> {
                // 判断软件是否存在
                Integer softwareId = isExsit(softwareReportRequest.getName(), softwareReportRequest.getVersion());
                // 不存在该软件，添加软件信息
                if (Objects.isNull(softwareId)) {
                    AssetSoftware assetSoftware = BeanConvert.convertBean(softwareReportRequest, AssetSoftware.class);
                    assetSoftware.setSoftwareStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
                    try {
                        softwareId = assetSoftwareDao.insert(assetSoftware);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 保存资产软件关系
                AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                assetSoftwareRelation.setAssetId(DataTypeUtils.integerToString(assetId));
                assetSoftwareRelation.setSoftwareId(DataTypeUtils.integerToString(softwareId));
                assetSoftwareRelation.setInstallStatus(InstallStatus.SUCCESS.getCode());
                assetSoftwareRelation.setInstallTime(softwareReportRequest.getInstallTime());
                assetSoftwareRelation.setInstallType(InstallType.MANUAL.getCode());
                assetSoftwareRelation
                    .setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                assetSoftwareRelationList.add(assetSoftwareRelation);
            });
            // 保存资产软件关系
            assetSoftwareRelationDao.insertBatch(assetSoftwareRelationList);
        }
    }

    /**
     * 判断软件是否存在
     * @return
     */
    public Integer isExsit(String name, String version) {
        return assetSoftwareDao.isExsit(name, version);
    }

    private Scheme convertScheme(ConfigRegisterRequest registerRequest) {
        Scheme scheme = new Scheme();
        scheme.setContent(registerRequest.getSuggest());
        scheme.setMemo(registerRequest.getSuggest());
        if (AssetTypeEnum.SOFTWARE.getCode().equals(DataTypeUtils.stringToInteger(registerRequest.getSource()))) {
            scheme.setAssetNextStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
            scheme.setSchemeSource(2);
        } else {
            scheme.setAssetNextStatus(AssetStatusEnum.WAIT_VALIDATE.getCode());
            scheme.setSchemeSource(1);
        }
        scheme.setAssetId(registerRequest.getAssetId());
        scheme.setCreateUser(LoginUserUtil.getLoginUser().getId());
        scheme.setGmtCreate(System.currentTimeMillis());
        scheme.setFileInfo(registerRequest.getFiles());

        return scheme;
    }

    private AssetOperationRecord convertRecord(ConfigRegisterRequest request) throws Exception {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        if (AssetTypeEnum.SOFTWARE.getCode().equals(request.getSource())) {
            assetOperationRecord.setOriginStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
            assetOperationRecord.setContent(SOFTWARE_INSTALL_CONFIG.getMsg());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
        } else {
            assetOperationRecord.setOriginStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent(HARDWARE_CONFIG_BASELINE.getMsg());
        }

        assetOperationRecord.setTargetObjectId(request.getAssetId());
        assetOperationRecord.setAreaId("");
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setProcessResult(1);
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        return assetOperationRecord;
    }

    public Integer findCountInstall(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findCount(query);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSoftwareResponse> findPageInstallList(AssetSoftwareQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountInstall(query), query.getCurrentPage(),
            this.findInstallList(query));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String importExcel(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        ImportResult<AssetSoftwareEntity> importResult = ExcelUtils.importExcelFromClient(AssetSoftwareEntity.class,
            file, 5, 0);
        if (Objects.isNull(importResult.getDataList())) {
            return importResult.getMsg();
        }
        List<AssetSoftwareEntity> resultDataList = importResult.getDataList();
        if (resultDataList.size() == 0) {
            return importResult.getMsg();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<AssetSoftware> assetList = new ArrayList<>();
        for (AssetSoftwareEntity entity : resultDataList) {

            AssetCategoryModel categoryModel = assetCategoryModelDao
                .getById(com.antiy.common.utils.DataTypeUtils.stringToInteger(entity.getCategory()));

            if (Objects.isNull(categoryModel)) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("选择的品类型号不存在，或已经注销！，");
                continue;
            }

            if (CheckRepeatName(entity.getName())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("软件名称重复，");
                continue;
            }

            if (repeat + error == 0) {

                AssetSoftware asset = new AssetSoftware();
                asset.setServiceLife(entity.getServiceLife());
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setMd5Code(entity.getMD5());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setSoftwareStatus(SoftwareStatusEnum.WATI_REGSIST.getCode());
                asset.setReleaseTime(entity.getReleaseTime());
                asset.setName(entity.getName());
                asset.setVersion(entity.getVersion());
                asset.setManufacturer(entity.getManufacturer());
                asset.setOperationSystem(entity.getOperationSystem());
                asset.setSerial(entity.getSerial());
                asset.setBuyDate(entity.getBuyDate());
                asset.setAuthorization(entity.getAuthorization());
                asset.setMemo(entity.getDescription());
                asset.setDescription(entity.getDescription());
                asset.setCategoryModel(entity.getCategory());
                assetList.add(asset);
            }
            a++;
        }

        if (repeat + error == 0) {
            List<ManualStartActivityRequest> manualStartActivityRequests = new ArrayList<>();
            for (AssetSoftware assetSoftware : assetList) {
                assetSoftwareDao.insert(assetSoftware);
                // 记录资产操作流程
                AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                assetOperationRecord.setTargetObjectId(assetSoftware.getStringId());
                assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
                assetOperationRecord.setTargetStatus(SoftwareStatusEnum.WATI_REGSIST.getCode());
                assetOperationRecord.setContent("导入软件资产");
                assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                assetOperationRecord.setOriginStatus(SoftwareStatusEnum.WATI_REGSIST.getCode());
                assetOperationRecordDao.insert(assetOperationRecord);
                ActionResponse actionResponse = areaClient.queryCdeAndAreaId("zichanguanliyuan");
                // ActionResponse actionResponse = areaClient.queryCdeAndAreaId("config_admin");
                List<LinkedHashMap> mapList = (List<LinkedHashMap>) actionResponse.getBody();
                StringBuilder stringBuilder = new StringBuilder();

                for (LinkedHashMap linkedHashMap : mapList) {
                    stringBuilder.append(linkedHashMap.get("stringId")).append(",");
                }
                String ids = stringBuilder.substring(0, stringBuilder.length() - 1);

                Map<String, Object> formData = new HashMap<>();

                formData.put("admittanceUserId", ids);

                ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
                manualStartActivityRequest.setBusinessId(assetSoftware.getStringId());
                manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
                manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getStringId());
                manualStartActivityRequest
                    .setProcessDefinitionKey(AssetActivityTypeEnum.SOFTWARE_ADMITTANCE_ATUO.getCode());
                manualStartActivityRequests.add(manualStartActivityRequest);
                success++;
            }
            activityClient.startProcessWithoutFormBatch(manualStartActivityRequests);
        }

        String res = "导入成功" + success + "条。";
        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志
        LogHandle.log(resultDataList.toString(), AssetEventEnum.SOFT_EXPORT.getName(),
            AssetEventEnum.SOFT_EXPORT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.SOFT_EXPORT.getName() + " {}", resultDataList.toString());
        StringBuilder sb = new StringBuilder(importResult.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();
    }

    private void exportData(String s, AssetSoftwareQuery assetSoftwareQuery,
                            HttpServletResponse response) throws Exception {
        assetSoftwareQuery.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        assetSoftwareQuery.setQueryAssetCount(true);
        assetSoftwareQuery.setPageSize(com.antiy.asset.util.Constants.ALL_PAGE);
        List<AssetSoftwareResponse> list = this.findPageAssetSoftware(assetSoftwareQuery).getItems();
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setSheetName("资产信息表");
        List<ExportSoftwareEntity> softwareEntities = softwareEntityConvert.convert(list, ExportSoftwareEntity.class);
        downloadVO.setDownloadList(softwareEntities);
        if (Objects.nonNull(softwareEntities) && softwareEntities.size() > 0) {
            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_EXPORT.getName(), null, null, downloadVO,
                BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.SOFT_EXPORT.getName() + " {}", downloadVO);
            excelDownloadUtil.excelDownload(response, s, downloadVO);
        } else {
            throw new BusinessException("导出数据为空");
        }

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer changeStatusById(Map<String, Object> map) throws Exception {
        return assetSoftwareDao.changeStatusById(map);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void exportTemplate() throws Exception {
        exportToClient(AssetSoftwareEntity.class, "软件信息模板.xlsx", "软件信息");
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> pulldownManufacturer() {
        return assetSoftwareDao.pulldownManufacturer();
    }

    private void exportToClient(Class clazz, String fileName, String title) {
        List<AssetSoftwareEntity> assetSoftwareEntities = new ArrayList<>();
        initExampleData(assetSoftwareEntities);
        String memo = "备注：时间填写规范统一为XXXX/XX/XX，必填项必须填写，否则会插入失败";
        ExcelUtils.exportTemplateToClient(clazz, fileName, title, memo, assetSoftwareEntities);
    }

    private void initExampleData(List<AssetSoftwareEntity> assetSoftwareEntities) {
        AssetSoftwareEntity assetSoftwareEntity = new AssetSoftwareEntity();
        assetSoftwareEntity.setName("智甲");
        assetSoftwareEntity.setOperationSystem("WINDOW 8,WINDOW 10");
        assetSoftwareEntity.setAuthorization(1);
        assetSoftwareEntity.setBuyDate(System.currentTimeMillis());
        assetSoftwareEntity.setCategory("10");
        assetSoftwareEntity.setDescription("安天智甲终端防御系统（中文简称“智甲”，英文简称“IEP”）是一套专业终端安全防护产品。");
        assetSoftwareEntity.setManufacturer("安天");
        assetSoftwareEntity.setVersion("1.1.1");
        assetSoftwareEntity.setServiceLife(System.currentTimeMillis());
        assetSoftwareEntity.setSerial("425-0022172 EWIN95");
        assetSoftwareEntity.setMD5("ASFFSDADQ2424r#@R#R");
        assetSoftwareEntity.setReleaseTime(System.currentTimeMillis());
        assetSoftwareEntities.add(assetSoftwareEntity);
    }

    private int getNextPage(PageResult pageResult) {
        int currentPage = pageResult.getCurrentPage() + 1;
        int pages = pageResult.getTotalPages();
        if (pages > 0) {
            return currentPage > pages ? pages : currentPage < 1 ? 1 : currentPage;
        }
        return 0;
    }

    /**
     * 查询软件端口信息
     * @param softwareQuery
     * @param assetSoftwareDetailResponse
     * @throws Exception
     */
    private void querySoftwarePort(SoftwareQuery softwareQuery,
                                   AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        AssetPortProtocolQuery assetPortProtocolQuery = new AssetPortProtocolQuery();
        assetPortProtocolQuery.setAssetSoftId(softwareQuery.getPrimaryKey());
        assetPortProtocolQuery.setPageSize(Constants.MAX_PAGESIZE);
        List<AssetPortProtocolResponse> assetPortProtocolResponses = iAssetPortProtocolService
            .findListAssetPortProtocol(assetPortProtocolQuery);
        assetSoftwareDetailResponse.setSoftwarePort(assetPortProtocolResponses);
    }

    /**
     * 查询软件license 信息
     * @param softwareQuery
     * @param assetSoftwareDetailResponse
     * @throws Exception
     */
    private void querySoftwareLicense(SoftwareQuery softwareQuery,
                                      AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        AssetSoftwareLicenseQuery assetSoftwareLicenseQuery = new AssetSoftwareLicenseQuery();
        assetSoftwareLicenseQuery.setSoftwareId(DataTypeUtils.stringToInteger(softwareQuery.getPrimaryKey()));
        assetSoftwareLicenseQuery.setPageSize(Constants.MAX_PAGESIZE);
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponses = iAssetSoftwareLicenseService
            .findListAssetSoftwareLicense(assetSoftwareLicenseQuery);
        assetSoftwareDetailResponse.setSoftwareLicense(assetSoftwareLicenseResponses);
    }

}

@Component
class SoftwareEntityConvert extends BaseConverter<AssetSoftwareResponse, ExportSoftwareEntity> {
    @Override
    protected void convert(AssetSoftwareResponse assetSoftware, ExportSoftwareEntity exportSoftwareEntity) {
        if (Objects.nonNull(assetSoftware.getSoftwareStatus())) {
            SoftwareStatusEnum assetStatusEnum = SoftwareStatusEnum.getAssetByCode(assetSoftware.getSoftwareStatus());
            exportSoftwareEntity.setStatus(assetStatusEnum == null ? "" : assetStatusEnum.getMsg());
        }
        exportSoftwareEntity.setGmtCreate(LongToDateString(assetSoftware.getGmtCreate()));
        super.convert(assetSoftware, exportSoftwareEntity);
    }

    private String LongToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }
}