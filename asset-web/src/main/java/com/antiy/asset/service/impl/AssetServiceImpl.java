package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AssetClient;
import com.antiy.asset.intergration.EmergencyClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.Constants;
import com.antiy.asset.util.*;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.SysArea;
import com.antiy.common.base.*;
import com.antiy.common.config.kafka.KafkaConfig;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p> 资产主表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService {
    private Logger                              logger   = LogUtils.get(this.getClass());
    @Resource
    private AssetDao                            assetDao;
    @Resource
    private AssetInstallTemplateDao             assetInstallTemplateDao;
    @Resource
    private AssetNetworkEquipmentDao            assetNetworkEquipmentDao;
    @Resource
    private AssetSafetyEquipmentDao             assetSafetyEquipmentDao;
    @Resource
    private TransactionTemplate                 transactionTemplate;
    @Resource
    private AssetSoftwareRelationDao            assetSoftwareRelationDao;
    @Resource
    private AssetStorageMediumDao               assetStorageMediumDao;
    @Resource
    private AssetOperationRecordDao             assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>  requestConverter;
    private BaseConverter<Asset, AssetResponse> responseConverter;
    private AssetUserDao                        assetUserDao;
    @Resource
    private AssetGroupRelationDao               assetGroupRelationDao;
    @Resource
    private ExcelDownloadUtil                   excelDownloadUtil;
    @Resource
    private AssetEntityConvert                  assetEntityConvert;
    @Resource
    private AssetGroupDao                       assetGroupDao;
    @Resource
    private ActivityClient                      activityClient;
    @Resource
    private AesEncoder                          aesEncoder;
    @Resource
    private RedisUtil                           redisUtil;
    @Resource
    private AssetLinkRelationDao                assetLinkRelationDao;
    @Resource
    private OperatingSystemClient               operatingSystemClient;
    @Resource
    private IRedisService                       redisService;
    @Resource
    private AssetClient                         assetClient;
    @Resource
    private AssetIpRelationDao                  assetIpRelationDao;
    @Resource
    private AssetMacRelationDao                 assetMacRelationDao;
    private static final int                    ALL_PAGE = -1;

    @Resource
    private EmergencyClient                     emergencyClient;

    @Override
    public ActionResponse saveAsset(AssetOuterRequest request) throws Exception {
        // 授权数量校验
        anthNumValidate();
        AssetRequest requestAsset = request.getAsset();
        AssetSafetyEquipmentRequest safetyEquipmentRequest = request.getSafetyEquipment();
        AssetNetworkEquipmentRequest networkEquipmentRequest = request.getNetworkEquipment();
        AssetStorageMediumRequest assetStorageMedium = request.getAssetStorageMedium();
        Long currentTimeMillis = System.currentTimeMillis();
        Integer id = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    String aid;
                    if (StringUtils.isNotBlank(requestAsset.getOperationSystem())) {
                        BusinessExceptionUtils.isTrue(checkOperatingSystemById(requestAsset.getOperationSystem()),
                            "操作系统不存在，或已经注销");
                    }
                    String areaId = requestAsset.getAreaId();
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                        DataTypeUtils.stringToInteger(areaId));
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    BusinessExceptionUtils.isTrue(
                        !Objects.isNull(
                            assetUserDao.getById(DataTypeUtils.stringToInteger(requestAsset.getResponsibleUserId()))),
                        "使用者不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "当前区域不存在，或已经注销");
                    List<AssetGroupRequest> assetGroup = requestAsset.getAssetGroups();
                    Asset asset = requestConverter.convert(requestAsset, Asset.class);
                    // 存入业务id,基准为空,进入实施,不为空进入网
                    asset.setBusinessId(Long.valueOf(requestAsset.getBusinessId()));

                    if (StringUtils.isNotBlank(requestAsset.getBaselineTemplateId())) {
                        asset.setBaselineTemplateId(Integer.valueOf(requestAsset.getBaselineTemplateId()));
                        asset.setAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
                    } else {
                        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
                    }
                    if (StringUtils.isNotBlank(requestAsset.getInstallTemplateId())) {
                        asset.setInstallTemplateId(Integer.valueOf(requestAsset.getInstallTemplateId()));
                    }

                    if (CollectionUtils.isNotEmpty(assetGroup)) {
                        assembleAssetGroupName(assetGroup, asset);
                    }
                    asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    asset.setGmtCreate(System.currentTimeMillis());
                    assetDao.insert(asset);

                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetOperateLogEnum.REGISTER_ASSET.getName(), asset.getId(),
                        asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetOperateLogEnum.REGISTER_ASSET.getName() + " {}",
                        requestAsset.toString());

                    insertBatchAssetGroupRelation(asset, assetGroup);
                    // 返回的资产id
                    aid = asset.getStringId();
                    // 插入ip/net mac
                    if (CollectionUtils.isNotEmpty(request.getIpRelationRequests())) {
                        List<AssetIpRelationRequest> ipRequestList = request.getIpRelationRequests();
                        List<AssetIpRelation> ipRelationList = BeanConvert.convert(ipRequestList,
                            AssetIpRelation.class);
                        for (AssetIpRelation assetIpRelation : ipRelationList) {
                            assetIpRelation.setAssetId(DataTypeUtils.stringToInteger(aid));
                            assetIpRelationDao.insert(assetIpRelation);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(request.getMacRelationRequests())) {
                        List<AssetMacRelationRequest> ipRequestList = request.getMacRelationRequests();
                        List<AssetMacRelation> assetMacRelations = BeanConvert.convert(ipRequestList,
                            AssetMacRelation.class);
                        for (AssetMacRelation assetIpRelation : assetMacRelations) {
                            assetIpRelation.setAssetId(DataTypeUtils.stringToInteger(aid));
                            assetMacRelationDao.insert(assetIpRelation);
                        }
                    }

                    // 保存安全设备
                    if (safetyEquipmentRequest != null) {
                        Integer id = saveSafety(aid, safetyEquipmentRequest);
                    }
                    // 保存网络设备
                    if (networkEquipmentRequest != null) {
                        Integer id = SaveNetwork(aid, networkEquipmentRequest);
                    }
                    // 保存存储设备
                    if (assetStorageMedium != null) {
                        AssetStorageMedium medium = BeanConvert.convertBean(assetStorageMedium,
                            AssetStorageMedium.class);
                        Integer id = SaveStorage(asset, assetStorageMedium, medium);
                    }

                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(aid);
                    assetOperationRecord.setOriginStatus(0);
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
                    assetOperationRecord.setTargetStatus(
                        asset.getAssetStatus() == AssetStatusEnum.NET_IN.getCode() ? AssetStatusEnum.NET_IN.getCode()
                            : AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
                    assetOperationRecord.setProcessResult(1);
                    assetOperationRecord.setContent(AssetFlowEnum.REGISTER.getMsg());
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(currentTimeMillis);
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return Integer.parseInt(aid);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    logger.error("录入失败", e);
                    if (e.getMessage().contains("失效")) {
                        throw new BusinessException(e.getMessage());
                    }
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("操作系统不存在，或已经注销", e.getMessage()),
                        "操作系统不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("使用者不存在，或已经注销", e.getMessage()), "使用者不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("当前区域不存在，或已经注销", e.getMessage()),
                        "当前区域不存在，或已经注销");
                }
                return 0;
            }
        });

        if (id != null && id > 0) {
            // 启动流程
            ManualStartActivityRequest activityRequest = request.getManualStartActivityRequest();
            activityRequest.setBusinessId(String.valueOf(id));
            activityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityRequest.setAssignee(LoginUserUtil.getLoginUser().getId() + "");
            ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                // 调用失败，逻辑删登记的资产
                assetDao.deleteById(id);
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }

        } else {
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION);
        }
        return ActionResponse.success();
    }

    private void checkLocationNotBlank(AssetRequest request) throws Exception {
        // Map<String, String> map = iAssetCategoryModelService.getSecondCategoryMap();
        // String secondCategory = iAssetCategoryModelService.recursionSearchParentCategory(request.getCategoryModel(),
        // assetCategoryModelDao.getAll(), map.keySet());
        // AssetCategoryModel assetCategoryModel = assetCategoryModelDao
        // .getByName(AssetSecondCategoryEnum.OTHER_DEVICE.getMsg());
        // if (!Objects.equals(assetCategoryModel.getStringId(), secondCategory)) {
        // ParamterExceptionUtils.isBlank(request.getLocation().trim(), "物理位置不能为空");
        // }
    }

    private Integer SaveStorage(Asset asset, AssetStorageMediumRequest assetStorageMedium,
                                AssetStorageMedium medium) throws Exception {
        medium.setAssetId(asset.getStringId());
        medium.setGmtCreate(System.currentTimeMillis());
        medium.setCreateUser(LoginUserUtil.getLoginUser().getId());
        // LogHandle.log(assetStorageMedium, AssetEventEnum.ASSET_STORAGE_INSERT.getName(),
        // AssetEventEnum.ASSET_STORAGE_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_STORAGE_INSERT.getName(), asset.getId(),
        // asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        // LogUtils.info(logger, AssetEventEnum.ASSET_STORAGE_INSERT.getName() + " {}", assetStorageMedium.toString());
        assetStorageMediumDao.insert(medium);
        return medium.getId();
    }

    private Integer SaveNetwork(String aid, AssetNetworkEquipmentRequest networkEquipmentRequest) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipmentRequest,
            AssetNetworkEquipment.class);
        assetNetworkEquipment.setAssetId(aid);
        assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkEquipmentDao.insert(assetNetworkEquipment);
        // LogHandle.log(networkEquipmentRequest, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName(),
        // AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        // LogUtils.recordOperLog(
        // new BusinessData(AssetEventEnum.ASSET_STORAGE_INSERT.getName(), assetNetworkEquipment.getId(), "",
        // assetNetworkEquipment, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        // LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName() + " {}",
        // networkEquipmentRequest.toString());
        return assetNetworkEquipment.getId();
    }

    private Integer saveSafety(String aid, AssetSafetyEquipmentRequest safetyEquipmentRequest) throws Exception {
        AssetSafetyEquipment safetyEquipment = BeanConvert.convertBean(safetyEquipmentRequest,
            AssetSafetyEquipment.class);
        safetyEquipment.setAssetId(aid);
        safetyEquipment.setGmtCreate(System.currentTimeMillis());
        safetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSafetyEquipmentDao.insert(safetyEquipment);
        // LogHandle.log(safetyEquipmentRequest, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName(),
        // AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_STORAGE_INSERT.getName(),
        // safetyEquipment.getId(),
        // "", safetyEquipment, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        // LogUtils.info(logger, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName() + " {}",
        // safetyEquipmentRequest.toString());
        return safetyEquipment.getId();
    }

    /**
     * 资产组名称拼装
     *
     * @param assetGroup
     * @param asset
     */
    private void assembleAssetGroupName(List<AssetGroupRequest> assetGroup, Asset asset) {
        StringBuffer stringBuffer = new StringBuffer();
        assetGroup.forEach(assetGroupRequest -> {
            try {
                AssetGroup tempGroup = assetGroupDao.getById(assetGroupRequest.getId());
                String assetGroupName = tempGroup.getName();
                if (tempGroup.getStatus() == 0) {
                    throw new BusinessException(tempGroup.getName() + "已失效，请核对后提交");
                }
                asset.setAssetGroup(
                    stringBuffer.append(assetGroupName).append(",").substring(0, stringBuffer.length() - 1));
            } catch (Exception e) {

                throw new BusinessException(e.getMessage());
            }
        });
    }

    private void insertBatchAssetGroupRelation(Asset asset1, List<AssetGroupRequest> assetGroup) {
        if (assetGroup != null && !assetGroup.isEmpty()) {
            List<AssetGroupRelation> groupRelations = new ArrayList<>();
            assetGroup.forEach(assetGroupRequest -> {
                AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                assetGroupRelation.setAssetGroupId(assetGroupRequest.getId());
                assetGroupRelation.setAssetId(asset1.getStringId());
                assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                groupRelations.add(assetGroupRelation);
                // LogHandle.log(assetGroupRequest, AssetEventEnum.ASSET_GROUP_INSERT.getName(),
                // AssetEventEnum.ASSET_GROUP_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_GROUP_INSERT.getName(), asset1.getId(),
                // "",
                // asset1, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
                // LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_INSERT.getName() + " {}",
                // assetGroupRequest.toString());
            });
            assetGroupRelationDao.insertBatch(groupRelations);
        }
    }

    // private boolean CheckRepeatIp(String innerIp, Integer isNet, Integer isSafety) throws Exception {
    // AssetQuery assetQuery = new AssetQuery();
    // assetQuery.setIp(innerIp);
    // assetQuery.setIsNet(isNet);
    // assetQuery.setIsSafety(isSafety);
    // Integer countIp = assetDao.findCountIp(assetQuery);
    // return countIp >= 1;
    // }
    @Override
    public boolean CheckRepeatMAC(String mac) throws Exception {
        Integer countIp = assetDao.findCountMac(mac);
        return countIp >= 1;
    }

    @Override
    public boolean CheckRepeatNumber(String number) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setNumber(number);
        Integer countAsset = findCountAssetNumber(assetQuery);
        return countAsset >= 1;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean checkRepeatName(String name) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAssetName(name);
        Integer countAsset = findCountAssetNumber(assetQuery);
        return countAsset >= 1;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String checkUser(String user) throws Exception {
        AssetUserQuery assetUserQuery = new AssetUserQuery();
        assetUserQuery.setExportName(user);
        List<AssetUser> assetUsers = assetUserDao.findListAssetUser(assetUserQuery);
        if (CollectionUtils.isNotEmpty(assetUsers)) {
            return assetUsers.get(0).getStringId();
        }
        return "";
    }

    @Override
    public Integer updateAsset(AssetRequest request) throws Exception {
        Asset asset = BeanConvert.convertBean(request, Asset.class);
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
        asset.setGmtCreate(System.currentTimeMillis());
        return assetDao.update(asset);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetResponse> findListAsset(AssetQuery query,
                                             Map<String, WaitingTaskReponse> processMap) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        // 1.查询漏洞个数
        Map<String, String> vulCountMaps = new HashMap<>();
        if (query.getQueryVulCount() != null && query.getQueryVulCount()) {
            List<IdCount> vulCountList = assetDao.queryAssetVulCount(
                LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser(), query.getPageSize(), query.getPageOffset());
            if (CollectionUtils.isEmpty(vulCountList)) {
                return new ArrayList<AssetResponse>();
            }
            vulCountMaps = vulCountList.stream().collect(Collectors.toMap(IdCount::getId, IdCount::getCount));
            String[] ids = new String[vulCountMaps.size()];
            query.setIds(vulCountMaps.keySet().toArray(ids));

            // 由于计算Id列表添加了区域，此处不用添加
            query.setAreaIds(null);
            query.setCurrentPage(1);
        }

        // 2.查询补丁个数
        Map<String, String> patchCountMaps = null;
        if (query.getQueryPatchCount() != null && query.getQueryPatchCount()) {
            List<IdCount> patchCountList = assetDao.queryAssetPatchCount(
                LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser(), query.getPageSize(), query.getPageOffset());
            if (CollectionUtils.isEmpty(patchCountList)) {
                return new ArrayList<AssetResponse>();
            }
            patchCountMaps = patchCountList.stream().collect(Collectors.toMap(IdCount::getId, IdCount::getCount));
            String[] ids = new String[patchCountMaps.size()];
            query.setIds(patchCountMaps.keySet().toArray(ids));
            // 由于计算Id列表添加了区域，此处不用添加
            query.setAreaIds(null);
            query.setCurrentPage(1);
        }

        // 3.查询告警个数

        Map<String, String> alarmCountMaps = null;
        if (query.getQueryAlarmCount() != null && query.getQueryAlarmCount()) {
            List<Integer> alarmAssetId = assetDao.findAlarmAssetId(query);
            query.setIds(DataTypeUtils.integerArrayToStringArray(alarmAssetId));
            // 由于计算Id列表添加了区域，此处不用添加
            query.setAreaIds(null);
            List<IdCount> alarmCountList = assetDao.queryAlarmCountByAssetIds(query);
            if (CollectionUtils.isEmpty(alarmCountList)) {
                return new ArrayList<AssetResponse>();
            }
            alarmCountMaps = alarmCountList.stream()
                .collect(Collectors.toMap(idcount -> idcount.getId(), IdCount::getCount));
            String[] ids = new String[alarmCountMaps.size()];
            query.setIds(alarmCountMaps.keySet().toArray(ids));
        }
        // 查询资产信息
        List<Asset> assetList = assetDao.findListAsset(query);

        if (CollectionUtils.isNotEmpty(assetList)) {
            assetList.stream().forEach(a -> {
                try {
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                        DataTypeUtils.stringToInteger(a.getAreaId()));
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    a.setAreaName(sysArea.getFullName());
                } catch (Exception e) {
                    logger.warn("获取资产区域名称失败", e);
                }
            });
        }
        List<AssetResponse> objects = responseConverter.convert(assetList, AssetResponse.class);

        List<BaselineCategoryModelResponse> categoryModelResponseList = redisService.getAllSystemOs();
        for (AssetResponse object : objects) {
            if (MapUtils.isNotEmpty(processMap)) {
                object.setWaitingTaskReponse(processMap.get(object.getStringId()));
            }

            if (MapUtils.isNotEmpty(vulCountMaps)) {
                object.setVulCount(vulCountMaps.getOrDefault(object.getStringId(), "0"));
            }

            if (MapUtils.isNotEmpty(patchCountMaps)) {
                object.setPatchCount(
                    patchCountMaps.containsKey(object.getStringId()) ? patchCountMaps.get(object.getStringId()) : "0");
            }

            if (MapUtils.isNotEmpty(alarmCountMaps)) {
                object.setAlarmCount(
                    alarmCountMaps.containsKey(object.getStringId()) ? alarmCountMaps.get(object.getStringId()) : "0");
            }

            // 设置操作系统名
            if (StringUtils.isNotEmpty(object.getOperationSystem())) {
                for (BaselineCategoryModelResponse categoryModelResponse : categoryModelResponseList) {
                    if (object.getOperationSystem().equals(categoryModelResponse.getStringId())) {
                        object.setOperationSystemName((String) categoryModelResponse.getName());
                    }
                }
            }
        }
        return objects;

    }

    public Integer findCountAsset(AssetQuery query) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        return assetDao.findCount(query);
    }

    /**
     * 获取流程引擎数据，并且返回map对象
     *
     * @return
     */
    public Map<String, WaitingTaskReponse> getAllHardWaitingTask(String definitionKeyType) {
        // 1.获取当前用户的所有代办任务
        ActivityWaitingQuery activityWaitingQuery = new ActivityWaitingQuery();
        activityWaitingQuery.setUser(LoginUserUtil.getLoginUser().getStringId());
        activityWaitingQuery.setProcessDefinitionKey(definitionKeyType);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = activityClient
            .queryAllWaitingTask(activityWaitingQuery);
        if (actionResponse != null
            && RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            List<WaitingTaskReponse> waitingTaskReponses = actionResponse.getBody();
            return waitingTaskReponses.stream()
                .filter(waitingTaskReponse -> StringUtils.isNotBlank(waitingTaskReponse.getBusinessId())).collect(
                    Collectors.toMap(WaitingTaskReponse::getBusinessId, Function.identity(), (key1, key2) -> key2));
        } else {
            LogUtils.info(logger, "获取当前用户的所有代办任务失败" + " {}", definitionKeyType);
            throw new BusinessException("获取工作流异常");
        }
    }

    public Integer findCountAssetNumber(AssetQuery query) throws Exception {
        return assetDao.findCount(query);
    }

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
        // 是否资产组关联资产查询
        if (null != query.getAssociateGroup()) {
            ParamterExceptionUtils.isBlank(query.getGroupId(), "资产组ID不能为空");
            List<String> associateAssetIdList = assetGroupRelationDao.findAssetIdByAssetGroupId(query.getGroupId());
            if (CollectionUtils.isNotEmpty(associateAssetIdList)) {
                query.setExistAssociateIds(associateAssetIdList);
            }
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("hard");
        dealProcess(query, processMap);
        // 品类型号及其子品类
        if (ArrayUtils.isNotEmpty(query.getCategoryModels())) {
            List<Integer> categoryModels = Lists.newArrayList();
            // for (int i = 0; i < query.getCategoryModels().length; i++) {
            // categoryModels.addAll(assetCategoryModelService
            // .findAssetCategoryModelIdsById(DataTypeUtils.stringToInteger(query.getCategoryModels()[i])));
            // }
            query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryModels));
        }

        int count = 0;
        // 如果会查询漏洞数量
        if (query.getQueryVulCount() != null && query.getQueryVulCount()) {
            count = assetDao.queryAllAssetVulCount(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
            if (count <= 0) {
                return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
            }
        }

        // 如果会查询补丁数据
        if (query.getQueryPatchCount() != null && query.getQueryPatchCount()) {
            count = assetDao.queryAllAssetPatchCount(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
            if (count <= 0) {
                return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
            }
        }
        // 如果会查询告警数量
        if (query.getQueryAlarmCount() != null && query.getQueryAlarmCount()) {
            count = assetDao.findAlarmAssetCount(query);
            if (count <= 0) {
                return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
            }
        }

        // 如果count为0 直接返回结果即可
        if (count <= 0) {
            if (query.getAreaIds() != null && query.getAreaIds().length <= 0) {
                query.setAreaIds(
                    DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
            }
            count = this.findCountAsset(query);
        }

        if (count <= 0) {
            if (query.getEnterControl()) {
                // 如果是工作台进来的但是有没有存在当前状态的待办任务，则把当前状态的资产全部查询出来
                query.setEnterControl(false);
                return new PageResult<>(query.getPageSize(), this.findCountAsset(query), query.getCurrentPage(),
                    this.findListAsset(query, processMap));
            }
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
        }

        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
            this.findListAsset(query, processMap));
    }

    @Override
    public Map findAlarmAssetCount() {
        AssetQuery assetQuery = new AssetQuery();
        if (ArrayUtils.isEmpty(assetQuery.getAreaIds())) {
            assetQuery.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        assetQuery.setAssetStatusList(StatusEnumUtil.getAssetNotRetireStatus());
        Map map = new HashMap();
        map.put("currentAlarmAssetIdNum", assetDao.findAlarmAssetCount(assetQuery));
        return map;
    }

    @Override
    public void implementationFile(ProcessTemplateRequest baseRequest) throws InvocationTargetException,
                                                                       IllegalAccessException, IOException {

        // 根据时间戳创建文件夹，防止产生冲突
        Long currentTime = System.currentTimeMillis();
        // 创建临时文件夹
        String dictionary = "/temp" + currentTime + "/模板" + currentTime;
        File dictionaryFile = new File(dictionary);
        if (!dictionaryFile.exists()) {
            logger.info(dictionaryFile.getName() + "目录创建" + isSuccess(dictionaryFile.mkdirs()));
        }
        File comFile = null;

        // 需要下载装机模板
        if (CollectionUtils.isNotEmpty(baseRequest.getComIds())) {
            comFile = new File(dictionaryFile, "装机模板列表.xls");
            List<AssetInstallTemplate> byAssetIds = assetInstallTemplateDao.findByAssetIds(baseRequest.getComIds());
            // 下载装机模板列表
            HSSFWorkbook hssfWorkbook = excelDownloadUtil.getHSSFWorkbook("装机模板列表", byAssetIds);
            FileOutputStream fileOutputStream = new FileOutputStream(comFile);
            hssfWorkbook.write(fileOutputStream);
            CloseUtils.close(fileOutputStream);
        }

        List<AssetResponse> list = this
            .queryAssetByIds(DataTypeUtils.stringArrayToIntegerArray(baseRequest.getIds().toArray(new String[] {})));

        List<AssetEntity> assetEntities1 = assetEntityConvert.convert(list, AssetEntity.class);

        File assetFile = new File(dictionaryFile, "资产列表.xls");
        // 下载资产列表
        HSSFWorkbook hssfWorkbook1 = excelDownloadUtil.getHSSFWorkbook("资产列表", assetEntities1);

        FileOutputStream fileOutputStream1 = new FileOutputStream(assetFile);
        hssfWorkbook1.write(fileOutputStream1);
        fileOutputStream1.close();
        CloseUtils.close(fileOutputStream1);
        // 入网流程不需要基准模板
        if (!baseRequest.getType()) {
            System.out.println("-----------why--------值=" + "dfd" + "," + "当前类=.()");
        }

        // 创造模板文件

        List<File> fileList = new ArrayList<>();
        fileList.add(assetFile);
        if (!Objects.isNull(comFile)) {
            fileList.add(comFile);
        }

        // 创造压缩文件
        File zip = new File("/temp" + currentTime + "/模板.zip");

        logger.info(zip.getName() + "文件创建" + isSuccess(zip.createNewFile()));
        // 压缩文件为zip压缩包
        File[] files = fileList.toArray(new File[] {});
        ZipUtil.compress(zip, files);
        // 将文件流发送到客户端
        sendStreamToClient(zip);
        // 记录临时文件删除是否成功
        loggerIsDelete(zip);
        deleteTemp(dictionaryFile, files);
    }

    /**
     * 处理待办任务
     */
    public void dealProcess(AssetQuery query, Map<String, WaitingTaskReponse> processMap) {
        // 只要是工作台进来的才去查询他的待办事项
        if (MapUtils.isNotEmpty(processMap)) {
            // 待办资产id
            Set<String> activitiIds = processMap.keySet();
            if (CollectionUtils.isNotEmpty(query.getAssetStatusList()) && query.getEnterControl()) {
                List<String> sortedIds = assetDao.sortAssetIds(activitiIds, query.getAssetStatus());
                if (CollectionUtils.isNotEmpty(sortedIds)) {
                    query.setIds(DataTypeUtils.integerArrayToStringArray(sortedIds));
                }
            }
        }
    }

    /**
     * 通联设置的资产查询 与普通资产查询类似， 不同点在于品类型号显示二级品类， 只查已入网，网络设备和计算设备的资产,且会去掉通联表中已存在的资产
     */
    @Override
    public PageResult<AssetResponse> findUnconnectedAsset(AssetQuery query) throws Exception {
        if (query.getAreaIds() == null || query.getAreaIds().length == 0) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        // 只查已入网资产
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        // 若品类型号查询条件为空 默认只查已入网，网络设备和计算设备的资产
        // Map<String, String> categoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        if (Objects.isNull(query.getCategoryModels()) || query.getCategoryModels().length <= 0) {
            List<Integer> categoryCondition = new ArrayList<>();
            // for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            // Integer isNet = query.getIsNet();
            // if ((isNet == null) || isNet == 1) {
            // if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
            // categoryCondition.addAll(assetCategoryModelService
            // .findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
            // }
            // }
            // if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
            // categoryCondition.addAll(
            // assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
            // }
            // }
            query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryCondition));
        } else {
            // 品类型号及其子品类
            // List<Integer> categoryModels = Lists.newArrayList();
            // for (int i = 0; i < query.getCategoryModels().length; i++) {
            // categoryModels.addAll(assetCategoryModelService
            // .findAssetCategoryModelIdsById(DataTypeUtils.stringToInteger(query.getCategoryModels()[i])));
            // }
            // query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryModels));
        }
        // 进行查询
        Integer count = assetLinkRelationDao.findUnconnectedCount(query);
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
        } else {
            List<AssetResponse> assetResponseList = responseConverter
                .convert(assetLinkRelationDao.findListUnconnectedAsset(query), AssetResponse.class);
            assetResponseList.stream().forEach(assetLinkedCount -> {
                String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(),
                    com.antiy.asset.vo.request.SysArea.class,
                    DataTypeUtils.stringToInteger(assetLinkedCount.getAreaId()));
                try {
                    com.antiy.asset.vo.request.SysArea sysArea = redisUtil.getObject(newAreaKey,
                        com.antiy.asset.vo.request.SysArea.class);
                    if (!Objects.isNull(sysArea)) {
                        assetLinkedCount.setAreaName(sysArea.getFullName());
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            });
            // processCategoryToSecondCategory(assetResponseList, categoryMap);
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetResponseList);
        }
    }

    // 处理品类型号使其均为二级品类型号
    private void processCategoryToSecondCategory(List<AssetResponse> assetResponseList,
                                                 Map<String, String> categoryMap) throws Exception {
        // 作为缓存使用，提高效率
        Map<String, String> cache = new HashMap<>();
        // List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        // Map<String, String> secondCategoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        // for (AssetResponse assetResponse : assetResponseList) {
        // String categoryModel = assetResponse.getCategoryModel();
        // String cacheId = cache.get(categoryModel);
        // if (Objects.nonNull(cacheId)) {
        // assetResponse.setCategoryType(new CategoryType(secondCategoryMap.get(cacheId)));
        // } else {
        // String second = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all,
        // categoryMap.keySet());
        // if (Objects.nonNull(second)) {
        // assetResponse.setCategoryType(new CategoryType(secondCategoryMap.get(second)));
        // cache.put(categoryModel, second);
        // }
        // }
        // }
    }

    @Override
    @Transactional
    public Integer batchSave(List<Asset> assetList) throws Exception {
        int i = 0;
        for (; i < assetList.size(); i++) {
            assetDao.insert(assetList.get(i));
        }
        return i + 1;
    }

    @Override
    public List<String> pulldownManufacturer() throws Exception {
        return assetDao.pulldownManufacturer();
    }

    @Override
    public boolean checkRepeatAsset(String uuid, List<String[]> ipMac) {
        ParamterExceptionUtils.isBlank(uuid, "上报设备资产UUID不能为空");
        try {
            List<Asset> list = assetDao.checkRepeatAsset(ipMac);
            // 资产已存在
            if (list != null && !list.isEmpty()) {
                if (list.size() > 1) {
                    // 资产数量大于1，异常情况，产生告警
                    // TODO

                } else {
                    Asset asset = list.get(0);
                    // 原记录UUID为空，将新的UUID更新进去
                    if (StringUtils.isBlank(asset.getUuid())) {
                        asset.setUuid(uuid);
                        assetDao.update(asset);
                    } else {
                        if (uuid.equals(asset.getUuid())) {
                            // 新旧UUID不一致
                            // TODO
                        }
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    @Override
    @Transactional
    public Integer changeStatus(String[] ids, Integer targetStatus) throws Exception {
        int row;
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("targetStatus", new String[] { targetStatus.toString() });
        map.put("gmtModified", LoginUserUtil.getLoginUser().getId());
        map.put("modifyUser", System.currentTimeMillis());
        row = assetDao.changeStatus(map);
        return row;
    }

    @Override
    public Integer changeStatusById(String id, Integer targetStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("targetStatus", targetStatus);
        map.put("gmtModified", System.currentTimeMillis());
        if (LoginUserUtil.getLoginUser() != null) {
            map.put("modifyUser", LoginUserUtil.getLoginUser().getId());
            return assetDao.changeStatus(map);
        } else {
            LogUtils.info(logger, AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName() + "{}", "用户获取失败");
            throw new BusinessException("用户获取失败");
        }
    }

    @Override
    public List<AssetResponse> findListAssetByCategoryModel(AssetQuery query) throws Exception {
        List<Asset> asset = assetDao.findListAssetByCategoryModel(query);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        return objects;
    }

    @Override
    public Integer findCountByCategoryModel(AssetQuery query) throws Exception {
        return assetDao.findCountByCategoryModel(query);
    }

    @Override
    public PageResult<AssetResponse> findPageAssetByCategoryModel(AssetQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountByCategoryModel(query), query.getCurrentPage(),
            this.findListAssetByCategoryModel(query));
    }

    @Override
    public List<EnumCountResponse> countManufacturer() throws Exception {
        int maxNum = 5;
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 不统计已退役资产
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        // update by zhangbing 对于空的厂商和产品确认需要统计，统计的到其他
        List<Map<String, Object>> list = assetDao.countManufacturer(areaIds, status);
        return CountTypeUtil.getEnumCountResponse(maxNum, list);
    }

    @Override
    public List<EnumCountResponse> countStatus() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Object>> searchResult = assetDao.countStatus(ids);
        Map<AssetStatusEnum, EnumCountResponse> resultMap = new HashMap<>();
        List<EnumCountResponse> resultList = new ArrayList<>();
        // 初始化result
        for (AssetStatusEnum assetStatusEnum : AssetStatusEnum.values()) {
            EnumCountResponse enumCountResponse = new EnumCountResponse(assetStatusEnum.getMsg(),
                assetStatusEnum.getCode() + "", 0);
            resultMap.put(assetStatusEnum, enumCountResponse);
        }
        // 将查询结果的值放入结果集
        for (Map map : searchResult) {
            AssetStatusEnum assetStatusEnum = AssetStatusEnum.getAssetByCode((Integer) map.get("key"));
            if (assetStatusEnum != null) {
                EnumCountResponse enumCountResponse = resultMap.get(assetStatusEnum);
                enumCountResponse.setNumber((long) map.get("value"));
            }
        }
        for (AssetStatusEnum assetStatusEnum : AssetStatusEnum.values()) {
            resultList.add(resultMap.get(assetStatusEnum));
        }
        return resultList;

    }

    @Override
    public List<EnumCountResponse> countCategory() throws Exception {
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        return countCategoryByStatus(status);
    }

    @Override
    public List<EnumCountResponse> countCategoryByStatus(List<Integer> assetStatusList) throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 查询第二级分类id
        // List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        // List<AssetCategoryModel> categoryModelDaoAll = assetCategoryModelDao.getAll();
        // if (CollectionUtils.isNotEmpty(secondCategoryModelList)) {
        // List<EnumCountResponse> resultList = new ArrayList<>();
        //// for (AssetCategoryModel secondCategoryModel : secondCategoryModelList) {
        //// EnumCountResponse enumCountResponse = new EnumCountResponse();
        //// // 查询第二级每个分类下所有的分类id，并添加至list集合
        ////// List<AssetCategoryModel> search = iAssetCategoryModelService.recursionSearch(categoryModelDaoAll,
        ////// secondCategoryModel.getId());
        //// List<String> categoryList = new ArrayList<>();
        //// categoryList.add(
        //// aesEncoder.encode(secondCategoryModel.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
        //// enumCountResponse.setCode(categoryList);
        //// // 设置查询资产条件参数，包括区域id，状态，资产品类型号
        ////// AssetQuery assetQuery = setAssetQueryParam(enumCountResponse, areaIds, search, assetStatusList);
        //// // 将查询结果放置结果集
        ////// enumCountResponse.setNumber((long) assetDao.findCountByCategoryModel(assetQuery));
        ////// enumCountResponse.setMsg(secondCategoryModel.getName());
        //// resultList.add(enumCountResponse);
        //// }
        // return resultList;
        // }
        return null;
    }

    // private AssetQuery setAssetQueryParam(EnumCountResponse enumCountResponse, List<Integer> areaIds,
    // List<AssetCategoryModel> search, List<Integer> assetStatusList) {
    // List<Integer> list = new ArrayList<>();
    // search.stream().forEach(x -> list.add(x.getId()));
    // AssetQuery assetQuery = new AssetQuery();
    // assetQuery.setCategoryModels(ArrayTypeUtil.objectArrayToStringArray(list.toArray()));
    // assetQuery.setAreaIds(ArrayTypeUtil.objectArrayToStringArray(areaIds.toArray()));
    // // 需要排除已退役资产
    // enumCountResponse.setStatus(assetStatusList);
    // assetQuery.setAssetStatusList(assetStatusList);
    // return assetQuery;
    // }

    @Override
    public List<AssetResponse> queryAssetByIds(Integer[] ids) {
        List<Asset> asset = assetDao.queryAssetByIds(ids);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        return objects;
    }

    @Override
    public AssetOuterResponse getByAssetId(AssetDetialCondition condition) throws Exception {
        BusinessExceptionUtils.isNull(condition, "资产不能为空");
        BusinessExceptionUtils.isNull(condition.getPrimaryKey(), "资产ID不能为空");
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        HashMap<String, Object> param = new HashMap();
        // 资产信息
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setIds(new String[] { condition.getPrimaryKey() });
        List<Asset> assetList = assetDao.findListAsset(assetQuery);
        BusinessExceptionUtils.isEmpty(assetList, "资产不存在");
        Asset asset = assetList.get(0);

        // 查询资产组AbstractOperations
        param.put("assetId", asset.getId());
        AssetResponse assetResponse = BeanConvert.convertBean(asset, AssetResponse.class);

        // 设置区域
        if (StringUtils.isNotEmpty(asset.getAreaId())) {
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(asset.getAreaId()));
            SysArea sysArea = redisUtil.getObject(key, SysArea.class);
            assetResponse.setAreaName(sysArea != null ? sysArea.getFullName() : null);
        }

        // 设置操作系统名
        if (StringUtils.isNotEmpty(asset.getOperationSystem())) {
            List<BaselineCategoryModelResponse> categoryModelResponseList = redisService.getAllSystemOs();
            for (BaselineCategoryModelResponse categoryModelResponse : categoryModelResponseList) {
                if (asset.getOperationSystem().equals(categoryModelResponse.getStringId())) {
                    assetResponse.setOperationSystemName(categoryModelResponse.getName());
                }
            }
        } else {
            Object osName = redisUtil.get("asset:unknown:os:" + asset.getStringId());
            assetResponse.setOperationSystemNotice(osName != null ? osName.toString() : null);
        }
        assetResponse.setAssetGroups(
            BeanConvert.convert(assetGroupRelationDao.queryByAssetId(asset.getId()), AssetGroupResponse.class));
        assetResponse.setAssetGroups(
            BeanConvert.convert(assetGroupRelationDao.queryByAssetId(asset.getId()), AssetGroupResponse.class));
        assetOuterResponse.setAsset(assetResponse);

        // 网络设备
        List<AssetNetworkEquipment> assetNetworkEquipmentList = assetNetworkEquipmentDao.getByWhere(param);
        if (CollectionUtils.isNotEmpty(assetNetworkEquipmentList)) {
            assetOuterResponse.setAssetNetworkEquipment(
                BeanConvert.convertBean(assetNetworkEquipmentList.get(0), AssetNetworkEquipmentResponse.class));
        }
        // 安全设备
        List<AssetSafetyEquipment> assetSafetyEquipmentList = assetSafetyEquipmentDao.getByWhere(param);
        if (CollectionUtils.isNotEmpty(assetSafetyEquipmentList)) {
            assetOuterResponse.setAssetSafetyEquipment(
                BeanConvert.convertBean(assetSafetyEquipmentList.get(0), AssetSafetyEquipmentResponse.class));
        }
        // 存储介质
        List<AssetStorageMedium> assetStorageMediumList = assetStorageMediumDao.getByWhere(param);
        if (CollectionUtils.isNotEmpty(assetStorageMediumList)) {
            assetOuterResponse.setAssetStorageMedium(
                BeanConvert.convertBean(assetStorageMediumList.get(0), AssetStorageMediumResponse.class));
        }
        // 软件列表
        if (condition.getIsNeedSoftware()) {
            List<AssetSoftware> assetSoftwareList = assetSoftwareRelationDao
                .getSoftByAssetId(DataTypeUtils.stringToInteger(condition.getPrimaryKey()));
            if (CollectionUtils.isNotEmpty(assetSoftwareList)) {
                List<AssetSoftwareResponse> assetSoftware = BeanConvert.convert(assetSoftwareList,
                    AssetSoftwareResponse.class);
                assetSoftware.stream().forEach(as -> {
                    if (StringUtils.isNotBlank(as.getOperationSystem())) {
                        as.setOperationSystems(Arrays.asList(as.getOperationSystem().split(",")));
                    }
                });
                assetOuterResponse.setAssetSoftware(assetSoftware);
            }
        }
        return assetOuterResponse;
    }

    @Override
    public Integer changeAsset(AssetOuterRequest assetOuterRequest) throws Exception {
        // 校验品类型号是否是叶子节点
        // ParamterExceptionUtils.isTrue(
        // assetCategoryModelDao.hasChild(assetOuterRequest.getAsset().getCategoryModel()) <= 0,
        // "品类型号只能选择末级节点数据，请重新选择");
        // Asset currentAsset = assetDao.getById(assetOuterRequest.getAsset().getId());
        // // 幂等校验
        // if (!Objects.isNull(assetOuterRequest.getManualStartActivityRequest())) {
        // if (!(AssetStatusEnum.WAIT_REGISTER.getCode().equals(currentAsset.getAssetStatus())
        // || AssetStatusEnum.RETIRE.getCode().equals(currentAsset.getAssetStatus())
        // || AssetStatusEnum.NOT_REGISTER.getCode().equals(currentAsset.getAssetStatus()))) {
        // throw new BusinessException(
        // "请勿重复提交，当前资产状态是：" + AssetStatusEnum.getAssetByCode(currentAsset.getAssetStatus()).getMsg());
        // }
        // }
        // 判断物理位置是否为空，因为其它设备没有物理位置所以需要单独校验
        checkLocationNotBlank(assetOuterRequest.getAsset());

        if (LoginUserUtil.getLoginUser() == null) {
            throw new BusinessException("获取用户失败");
        }
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset(), "资产信息不能为空");
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");

        ParamterExceptionUtils.isTrue(
            !checkNumber(assetOuterRequest.getAsset().getId(), assetOuterRequest.getAsset().getNumber()), "资产编号重复");
        ParamterExceptionUtils
            .isTrue(!checkName(assetOuterRequest.getAsset().getId(), assetOuterRequest.getAsset().getName()), "资产名称重复");
        Asset asset = BeanConvert.convertBean(assetOuterRequest.getAsset(), Asset.class);
        AssetQuery assetQuery = new AssetQuery();
        Long currentTimeMillis = System.currentTimeMillis();
        Integer assetCount = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    List<AssetGroupRequest> assetGroup = assetOuterRequest.getAsset().getAssetGroups();
                    if (assetGroup != null && !assetGroup.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        assetGroup.stream().forEach(assetGroupRequest -> {
                            try {
                                AssetGroup tempGroup = assetGroupDao.getById(assetGroupRequest.getId());
                                String assetGroupName = tempGroup.getName();
                                if (tempGroup.getStatus() == 0) {
                                    throw new BusinessException(assetGroupName + "已失效，请核对后提交");
                                } else {
                                    asset.setAssetGroup(stringBuilder.append(assetGroupName).append(",").substring(0,
                                        stringBuilder.length() - 1));
                                }
                            } catch (Exception e) {

                                LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}", e.getMessage());
                                throw new BusinessException(e.getMessage());
                            }
                        });
                    }
                    // 得到已存在的资产组关系,对新增的插入,移除的删除
                    List<AssetGroupRelation> existedRelationList = assetGroupRelationDao
                        .listRelationByAssetId(DataTypeUtils.stringToInteger(assetOuterRequest.getAsset().getId()));
                    List<AssetGroupRequest> assetGroups = assetOuterRequest.getAsset().getAssetGroups();
                    List<AssetGroupRelation> addAssetGroupRelations = Lists.newArrayList();
                    // 参数中资产组id与已存在的的资产组id相等,不操作;不等就添加插入
                    for (AssetGroupRequest assetGroupRequest : assetGroups) {
                        boolean addRelation = true;
                        for (AssetGroupRelation existedRelation : existedRelationList) {
                            if (existedRelation.getAssetGroupId().equals(assetGroupRequest.getId())) {
                                addRelation = false;
                                break;
                            }
                        }
                        if (addRelation) {
                            AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                            assetGroupRelation.setAssetGroupId(assetGroupRequest.getId());
                            assetGroupRelation.setAssetId(asset.getStringId());
                            assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                            addAssetGroupRelations.add(assetGroupRelation);
                        }
                    }

                    // 移除库中existedRelationList已经有的与本次请求相等的,剩下的existedRelationList是本次操作删除的
                    assetOuterRequest.getAsset().getAssetGroups().forEach(assetGroupRequest -> {
                        existedRelationList
                            .removeIf(relation -> assetGroupRequest.getId().equals(relation.getAssetGroupId()));
                    });
                    List<Integer> deleteRelationIdList = existedRelationList.stream()
                        .map(deleteRelation -> deleteRelation.getId()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(deleteRelationIdList)) {
                        assetGroupRelationDao.deleteBatch(deleteRelationIdList);
                    }
                    if (CollectionUtils.isNotEmpty(addAssetGroupRelations)) {
                        assetGroupRelationDao.insertBatch(addAssetGroupRelations);
                    }
                    asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
                    asset.setGmtModified(System.currentTimeMillis());
                    // 1. 更新资产主表
                    int count = assetDao.changeAsset(asset);

                    List<AssetNetworkCardRequest> assetNetworkCardRequestList = assetOuterRequest.getNetworkCard();
                    // 如果网卡被删除，则同时删除通联关系表
                    AssetNetworkCardQuery assetNetworkCardQuery = new AssetNetworkCardQuery();
                    assetNetworkCardQuery.setAssetId(asset.getStringId());
                    // 当前数据库存在的网卡

                    // 7. 更新网络设备信息
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();

                    // 9. 更新存储介质信息
                    AssetStorageMediumRequest storageMedium = assetOuterRequest.getAssetStorageMedium();
                    if (storageMedium != null && StringUtils.isNotBlank(storageMedium.getId())) {
                        AssetStorageMedium assetStorageMedium = BeanConvert.convertBean(storageMedium,
                            AssetStorageMedium.class);
                        assetStorageMedium.setAssetId(asset.getStringId());
                        assetStorageMedium.setGmtCreate(System.currentTimeMillis());
                        assetStorageMedium.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetStorageMedium.setGmtModified(System.currentTimeMillis());
                        assetStorageMediumDao.update(assetStorageMedium);
                    }
                    // 10. 更新资产软件关系信息
                    return 0;

                } catch (Exception e) {
                    logger.info("资产变更失败:", e);
                    transactionStatus.setRollbackOnly();

                    if (e.getMessage().contains("失效")) {
                        throw new BusinessException(e.getMessage());
                    }
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("IP不能重复", e.getMessage()), "IP不能重复");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("网络设备IP不能重复", e.getMessage()), "网络设备IP不能重复");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("安全设备IP不能重复", e.getMessage()), "安全设备IP不能重复");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("网口数只能增加不能减少", e.getMessage()), "网口数只能增加不能减少");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("MAC不能重复", e.getMessage()), "MAC不能重复");
                    throw new BusinessException("资产变更失败");
                }
            }
        });
        if (assetCount != null && assetCount > 0) {
            // 已退役、待登记，不予登记再登记，需启动新流程
            String assetId = assetOuterRequest.getAsset().getId();
            Asset assetObj = assetDao.getById(assetId);
            // if (AssetStatusEnum.RETIRE.getCode().equals(currentAsset.getAssetStatus())) {
            // // 授权数量校验
            // anthNumValidate();
            // // 记录操作日志和运行日志
            // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_INSERT.getName(), Integer.valueOf(assetId),
            // assetObj.getNumber(), assetOuterRequest, BusinessModuleEnum.HARD_ASSET,
            // BusinessPhaseEnum.WAIT_SETTING));
            // LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}",
            // JSON.toJSONString(assetOuterRequest));
            // } else if (AssetStatusEnum.NOT_REGISTER.getCode().equals(currentAsset.getAssetStatus())) {
            // // 授权数量校验
            // anthNumValidate();
            // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_INSERT.getName(), Integer.valueOf(assetId),
            // assetDao.getById(assetId).getNumber(), assetOuterRequest, BusinessModuleEnum.HARD_ASSET,
            // BusinessPhaseEnum.WAIT_SETTING));
            // LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}",
            // JSON.toJSONString(assetOuterRequest));
            // } else if (AssetStatusEnum.WAIT_REGISTER.getCode().equals(currentAsset.getAssetStatus())) {
            // // 授权数量校验
            // anthNumValidate();
            // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_INSERT.getName(), Integer.valueOf(assetId),
            // assetDao.getById(assetId).getNumber(), assetOuterRequest, BusinessModuleEnum.HARD_ASSET,
            // BusinessPhaseEnum.WAIT_SETTING));
            // LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}",
            // JSON.toJSONString(assetOuterRequest));
            // } else if (AssetStatusEnum.NET_IN.getCode().equals(currentAsset.getAssetStatus())) {
            // LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
            // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MODIFY.getName(), asset.getId(),
            // asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NET_IN));
            // }
            //
            // if (AssetStatusEnum.RETIRE.getCode().equals(currentAsset.getAssetStatus())
            // || AssetStatusEnum.NOT_REGISTER.getCode().equals(currentAsset.getAssetStatus())
            // || AssetStatusEnum.WAIT_REGISTER.getCode().equals(currentAsset.getAssetStatus())) {
            // ManualStartActivityRequest manualStartActivityRequest = assetOuterRequest
            // .getManualStartActivityRequest();
            // ParamterExceptionUtils.isNull(manualStartActivityRequest, "配置信息不能为空");
            // // ------------------启动工作流------------------start
            // manualStartActivityRequest.setBusinessId(assetId);
            // manualStartActivityRequest.setAssignee(String.valueOf(LoginUserUtil.getLoginUser().getId()));
            // manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            // ActionResponse actionResponse = activityClient.manualStartProcess(manualStartActivityRequest);
            // if (null != actionResponse
            // && RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            //
            // // ------------------对接配置模块------------------start
            // ConfigRegisterRequest configRegisterRequest = new ConfigRegisterRequest();
            // configRegisterRequest.setHard(true);
            // if (LoginUserUtil.getLoginUser() != null) {
            // configRegisterRequest
            // .setAssetId(aesEncoder.encode(assetId, LoginUserUtil.getLoginUser().getUsername()));
            // } else {
            // LogUtils.warn(logger, AssetEventEnum.GET_USER_INOF.getName() + " {}",
            // AssetEventEnum.GET_USER_INOF.getName());
            // throw new BusinessException(AssetEventEnum.GET_USER_INOF.getName() + "： 用户服务异常");
            // }
            //
            // configRegisterRequest.setSource(String.valueOf(AssetTypeEnum.HARDWARE.getCode()));
            // configRegisterRequest.setSuggest(assetOuterRequest.getManualStartActivityRequest().getSuggest());
            // List<String> configUserIdList = assetOuterRequest.getManualStartActivityRequest()
            // .getConfigUserIds();
            // List<String> aesConfigUserIdList = new ArrayList<>();
            // configUserIdList.forEach(
            // e -> aesConfigUserIdList.add(aesEncoder.encode(e, LoginUserUtil.getLoginUser().getUsername())));
            // configRegisterRequest.setConfigUserIds(aesConfigUserIdList);
            // configRegisterRequest
            // .setRelId(aesEncoder.encode(assetId, LoginUserUtil.getLoginUser().getUsername()));
            // // 避免重复记录操作记录
            // configRegisterRequest.setHard(true);
            // ActionResponse actionResponseAsset = softwareService.configRegister(configRegisterRequest,
            // currentTimeMillis);
            // if (null == actionResponseAsset
            // || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponseAsset.getHead().getCode())) {
            // throw new BusinessException("配置服务异常，登记失败");
            // }
            // // 更新资产状态为待配置
            // assetOuterRequest.getAsset().setAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
            // updateAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode(), currentTimeMillis, assetId);
            // // ------------------对接配置模块------------------end
            // } else if (null != actionResponse && RespBasicCode.BUSSINESS_EXCETION.getResultCode()
            // .equals(actionResponse.getHead().getCode())) {
            // LogUtils.info(logger, AssetEventEnum.ASSET_START_ACTIVITY.getName() + " {}",
            // JSON.toJSONString(manualStartActivityRequest));
            // throw new BusinessException(actionResponse.getBody().toString());
            // } else if (actionResponse == null) {
            // throw new BusinessException("流程服务异常");
            // }
            //
            // // ------------------启动工作流------------------end
            // }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 下发智甲
                    AssetExternalRequest assetExternalRequest = BeanConvert.convertBean(assetOuterRequest,
                        AssetExternalRequest.class);
                    try {
                        assetExternalRequest.setAsset(BeanConvert
                            .convertBean(assetDao.getById(assetOuterRequest.getAsset().getId()), AssetRequest.class));
                    } catch (Exception e) {
                        LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}", e);
                    }
                    // 获取资产上安装的软件信息
                    List<AssetSoftware> assetSoftwareRelationList = assetSoftwareRelationDao
                        .findInstalledSoft(assetOuterRequest.getAsset().getId());
                    assetExternalRequest
                        .setSoftware(BeanConvert.convert(assetSoftwareRelationList, AssetSoftwareRequest.class));
                    List<AssetExternalRequest> assetExternalRequests = new ArrayList<>();
                    assetExternalRequests.add(assetExternalRequest);
                    assetClient.issueAssetData(assetExternalRequests);
                }
            }).start();
        }
        return assetCount;
    }

    private boolean checkNumber(String id, String number) {
        return assetDao.selectRepeatNumber(number, id) > 0;
    }

    private boolean checkName(String id, String name) {
        return assetDao.selectRepeatName(name, id) > 0;
    }

    private void updateAssetStatus(Integer assetStatus, Long currentTimeMillis, String assetId) throws Exception {
        Asset asset = new Asset();
        asset.setId(DataTypeUtils.stringToInteger(assetId));
        asset.setGmtModified(currentTimeMillis);
        asset.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
        asset.setAssetStatus(assetStatus);
        assetDao.updateStatus(asset);
    }

    /**
     * 导出后的文件格式为.zip压缩包
     * @param types 导出模板的类型
     */
    @Override
    public void exportTemplate(String[] types) throws Exception {
        // 根据时间戳创建文件夹，防止产生冲突
        Long currentTime = System.currentTimeMillis();
        // 创建临时文件夹
        String dictionary = "/temp" + currentTime + "/模板" + currentTime;
        File dictionaryFile = new File(dictionary);
        if (!dictionaryFile.exists()) {
            logger.info(dictionaryFile.getName() + "目录创建" + isSuccess(dictionaryFile.mkdirs()));
        }
        // 创造模板文件
        File[] files = new File[types.length];
        // 创造压缩文件
        File zip = new File("/temp" + currentTime + "/模板.zip");
        int m = 0;
        for (String type : types) {
            // 生成模板文件
            exportTemplate(dictionary + "/", type);
            files[m++] = new File(dictionary + "/" + type + "信息模板.xlsx");
            logger.info(files[m - 1].getName() + "文件创建成功");

        }
        logger.info(zip.getName() + "文件创建" + isSuccess(zip.createNewFile()));
        // 压缩文件为zip压缩包
        ZipUtil.compress(zip, files);
        // 将文件流发送到客户端
        sendStreamToClient(zip);
        // 记录临时文件删除是否成功
        loggerIsDelete(zip);
        deleteTemp(dictionaryFile, files);

    }

    private void deleteTemp(File dictionaryFile, File[] files) {
        for (File fil : files) {
            if (Objects.nonNull(fil)) {
                loggerIsDelete(fil);
            }
        }
        loggerIsDelete(dictionaryFile);
        loggerIsDelete(dictionaryFile.getParentFile());
    }

    private void exportTemplate(String dictionary, String type) {

        // 根据不同的品类名生成不同的模板
        switch (type) {
            case "计算设备":
                exportComputeTemplate(dictionary);
                break;
            case "网络设备":
                exportNetworkTemplate(dictionary);
                break;
            case "存储设备":
                exportStorageTemplate(dictionary);
                break;
            case "安全设备":
                exportSafetyTemplate(dictionary);
                break;
            case "其它设备":
                exportOtherTemplate(dictionary);
                break;
        }

    }

    private void exportOtherTemplate(String dictionary) {
        // 初始化其它设备示例数据
        List<OtherDeviceEntity> dataList = initOtherData();
        ExcelUtils.exportTemplateToFile(OtherDeviceEntity.class, "其它设备信息模板.xlsx", "其它设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dictionary + "/", dataList);
    }

    private List<OtherDeviceEntity> initOtherData() {
        List<OtherDeviceEntity> dataList = new ArrayList<>();
        OtherDeviceEntity otherDeviceEntity = new OtherDeviceEntity();
        otherDeviceEntity.setArea("四川省");
        otherDeviceEntity.setBuyDate(System.currentTimeMillis());
        otherDeviceEntity.setDueDate(System.currentTimeMillis());
        otherDeviceEntity.setUser("留小查");
        otherDeviceEntity.setVersion("1.1.2");
        otherDeviceEntity.setSerial("ANFRWGDFETYRYF");
        otherDeviceEntity.setName("触摸查询一体机");
        otherDeviceEntity.setMemo("宣传展览导视查询畅销触控一体机，采用FULL HD全视角高清IPS硬屏");
        otherDeviceEntity.setManufacturer("捷显");
        otherDeviceEntity.setWarranty("2年");
        otherDeviceEntity.setNumber("000001");
        otherDeviceEntity.setImportanceDegree("1");
        dataList.add(otherDeviceEntity);
        return dataList;
    }

    private void exportSafetyTemplate(String dictionary) {
        // 初始化安全设备示例数据
        List<SafetyEquipmentEntiy> dataList = initSafetyData();
        ExcelUtils.exportTemplateToFile(SafetyEquipmentEntiy.class, "安全设备信息模板.xlsx", "安全设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dictionary + "/", dataList);
    }

    private List<SafetyEquipmentEntiy> initSafetyData() {
        List<SafetyEquipmentEntiy> dataList = new ArrayList<>();
        SafetyEquipmentEntiy safetyEquipmentEntiy = new SafetyEquipmentEntiy();
        safetyEquipmentEntiy.setArea("四川省");
        safetyEquipmentEntiy.setBuyDate(System.currentTimeMillis());
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis());
        safetyEquipmentEntiy.setMac("00-01-6C-06-A6-29");
        safetyEquipmentEntiy.setIp("192.168.1.120");
        safetyEquipmentEntiy.setWarranty("2年");
        safetyEquipmentEntiy.setManufacturer("安天");
        safetyEquipmentEntiy.setName("安天镇关威胁阻断系统   ");
        safetyEquipmentEntiy
            .setMemo("镇关采用高性能软硬件架构，以智能用户识别与智能应用识别为基础，实现了完全" + "以用户和应用为中心的控制策略，先进的APT防护技术能够有效防范0day攻击和社工渗透等新型威胁。");
        safetyEquipmentEntiy.setMac("00-01-6C-06-A6-29");
        safetyEquipmentEntiy.setNumber("00001");
        safetyEquipmentEntiy.setIp("192.168.1.9");
        safetyEquipmentEntiy.setUser("留小查");
        safetyEquipmentEntiy.setManufacturer("安天");
        safetyEquipmentEntiy.setSerial("ANFRWGDFETYRYF");
        safetyEquipmentEntiy.setVersion("1.1.2");
        safetyEquipmentEntiy.setHouseLocation("501机房004号");
        safetyEquipmentEntiy.setImportanceDegree("1");
        safetyEquipmentEntiy.setOperationSystem("Windows");
        dataList.add(safetyEquipmentEntiy);
        return dataList;
    }

    private void exportStorageTemplate(String dictionary) {
        // 初始化存储设备示例数据
        List<StorageDeviceEntity> dataList = initStorageData();
        ExcelUtils.exportTemplateToFile(StorageDeviceEntity.class, "存储设备信息模板.xlsx", "存储设备",
            "备注：时间填写规范统一为XXXX/XX/XX，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dictionary + "/", dataList);
    }

    private List<StorageDeviceEntity> initStorageData() {
        List<StorageDeviceEntity> dataList = new ArrayList<>();
        StorageDeviceEntity storageDeviceEntity = new StorageDeviceEntity();
        storageDeviceEntity.setArea("四川省");
        storageDeviceEntity.setAverageTransmissionRate("4GB");
        storageDeviceEntity.setBuyDate(System.currentTimeMillis());
        storageDeviceEntity.setCapacity("256GB");
        storageDeviceEntity.setDriveNum(3);
        storageDeviceEntity.setVersion("1.1.2");
        storageDeviceEntity.setDueDate(System.currentTimeMillis());
        storageDeviceEntity.setHardDiskNum(1);
        storageDeviceEntity.setFirmwareVersion("spi1");
        storageDeviceEntity.setHighCache("6GB/S");
        storageDeviceEntity.setHouseLocation("501机房004号");
        storageDeviceEntity.setManufacturer("联想");
        storageDeviceEntity.setMemo("私有云网络存储器nas文件共享数字备份 AS6202T 0TB 标机");
        storageDeviceEntity.setName("华芸AS6202T");
        storageDeviceEntity.setInnerInterface("SSD");
        storageDeviceEntity.setNumber("000001");
        storageDeviceEntity.setSlotType("1");
        storageDeviceEntity.setWarranty("2年");
        storageDeviceEntity.setRaidSupport(1);
        storageDeviceEntity.setUser("留小查");
        storageDeviceEntity.setSerial("ANFRWGDFETYRYF");
        storageDeviceEntity.setImportanceDegree("1");
        dataList.add(storageDeviceEntity);
        return dataList;

    }

    private void exportNetworkTemplate(String dictionary) {
        // 初始化网络设备示例数据
        List<NetworkDeviceEntity> dataList = initNetworkData();
        ExcelUtils.exportTemplateToFile(NetworkDeviceEntity.class, "网络设备信息模板.xlsx", "网络设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dictionary + "/", dataList);
    }

    private List<NetworkDeviceEntity> initNetworkData() {
        List<NetworkDeviceEntity> dataList = new ArrayList<>();
        NetworkDeviceEntity networkDeviceEntity = new NetworkDeviceEntity();
        networkDeviceEntity.setWarranty("2年");
        networkDeviceEntity.setButDate(System.currentTimeMillis());
        networkDeviceEntity.setCpuSize(4);
        networkDeviceEntity.setArea("成都市");
        networkDeviceEntity.setMac("00-01-6C-06-A6-29");
        networkDeviceEntity.setName("YTW-600-5A五端口迷你型网络延长器");
        networkDeviceEntity.setDramSize(3.42f);
        networkDeviceEntity.setVersion("1.1.2");
        networkDeviceEntity.setExpectBandwidth(4);
        networkDeviceEntity.setFirmwareVersion("1.1.1");
        networkDeviceEntity.setUser("留小查");
        networkDeviceEntity.setRegister(1);
        networkDeviceEntity.setNumber("000001");
        networkDeviceEntity.setIsWireless(1);
        networkDeviceEntity.setCpuVersion("i7");
        networkDeviceEntity.setIos("WDWFER");
        networkDeviceEntity.setDueDate(System.currentTimeMillis());
        networkDeviceEntity.setFlashSize(2.32f);
        networkDeviceEntity.setPortSize(4);
        networkDeviceEntity.setNcrmSize(4.22f);
        networkDeviceEntity.setOuterIp("192.168.1.9");
        networkDeviceEntity.setManufacturer("邮通");
        networkDeviceEntity.setIsWireless(1);
        networkDeviceEntity.setSerial("ANFRWGDFETYRYF");
        networkDeviceEntity.setHouseLocation("501机房004号");
        networkDeviceEntity.setCpuSize(3);
        networkDeviceEntity.setInterfaceSize(4);
        networkDeviceEntity.setSubnetMask("255.255.252.0");
        networkDeviceEntity.setImportanceDegree("1");
        networkDeviceEntity.setMemo("该产品的基本原理是通过信号整形，增加敏感度来实现距离延长的，其电压、波形完全符合以太网国际标准，不会对网络带来危害，请放心在五类或超五类非屏蔽网线上使用。");
        dataList.add(networkDeviceEntity);
        return dataList;
    }

    private void exportComputeTemplate(String dictionary) {
        // 初始化计算设备示例数据
        List<ComputeDeviceEntity> dataList = initComputeData();
        ExcelUtils.exportTemplateToFile(ComputeDeviceEntity.class, "计算设备信息模板.xlsx", "计算设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败,部件信息选填,但若填写了某一部件，则必须填写该部件的必填项，请不要删除示例，保持模版原样从第七行开始填写。",
            dictionary + "/", dataList);
    }

    private List<ComputeDeviceEntity> initComputeData() {
        List<ComputeDeviceEntity> dataList = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = new ComputeDeviceEntity();
        computeDeviceEntity.setArea("成都市");
        computeDeviceEntity.setName("ThinkPad X1 隐士");
        computeDeviceEntity.setIp("192.168.1.1");
        computeDeviceEntity.setMac("00-01-6C-06-A6-29");
        computeDeviceEntity.setWarranty("2年");
        computeDeviceEntity.setDescription("搭载第八代英特尔®酷睿TM i7处理器，配备双内存插槽，最高支持64GB内存扩展");
        computeDeviceEntity.setDueTime(System.currentTimeMillis());
        computeDeviceEntity.setImportanceDegree("1");
        computeDeviceEntity.setSerial("ADES-WRGD-EREW-TERF");
        computeDeviceEntity.setHouseLocation("501机房004号");
        computeDeviceEntity.setNumber("123");
        computeDeviceEntity.setUser("留小查");
        computeDeviceEntity.setBuyDate(System.currentTimeMillis());
        computeDeviceEntity.setVersion("1.3.2");
        computeDeviceEntity.setDueTime(System.currentTimeMillis());
        computeDeviceEntity.setManufacturer("联想");
        computeDeviceEntity.setOperationSystem("Window 10");
        dataList.add(computeDeviceEntity);
        return dataList;
    }

    private void loggerIsDelete(File file) {
        logger.info(file.getName() + "文件删除" + isSuccess(file.delete()));
    }

    private void sendStreamToClient(File file) {
        FileInputStream fileInputStream = null;
        OutputStream ous = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
            response.reset();
            String fileName = file.getName();
            Boolean flag = request.getHeader("User-Agent").indexOf("like Gecko") > 0;
            if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0 || flag) {
                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
            } else {
                // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,
                // 这个文件名称用于浏览器的下载框中自动显示的文件名
                fileName = new String(fileName.replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
                // firefox浏览器
                // firefox浏览器User-Agent字符串:
                // Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");
            ous = new BufferedOutputStream(response.getOutputStream());
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                ous.write(buffer, 0, length);
            }
        } catch (Exception e) {

            logger.error(e.getMessage());
            throw new BusinessException("发送客户端失败");
        } finally {
            CloseUtils.close(ous);
            CloseUtils.close(fileInputStream);
        }

    }

    private String isSuccess(Boolean isDelete) {
        return isDelete ? "成功" : "失败";
    }

    private boolean isDataBig(Long date) {
        String Due = DateUtils.convertDateString(date, "yyyy-MM-dd");
        Date format = DateUtils.getDateFormat(Due, DateUtils.NO_TIME_FORMAT);
        String dateString = DateUtils.convertDateString(System.currentTimeMillis(), DateUtils.NO_TIME_FORMAT);
        Date dateFormat = DateUtils.getDateFormat(dateString, DateUtils.NO_TIME_FORMAT);

        return format.getTime() < dateFormat.getTime();
    }

    private boolean isBuyDataBig(Long date) {
        String Due = DateUtils.convertDateString(date, "yyyy-MM-dd");
        Date format = DateUtils.getDateFormat(Due, DateUtils.NO_TIME_FORMAT);
        String dateString = DateUtils.convertDateString(System.currentTimeMillis(), DateUtils.NO_TIME_FORMAT);
        Date dateFormat = DateUtils.getDateFormat(dateString, DateUtils.NO_TIME_FORMAT);

        return format.getTime() > dateFormat.getTime();
    }

    @Override
    @Transactional
    public String importPc(MultipartFile file, AssetImportRequest importRequest) throws Exception {

        // 授权数量限制校验
        anthNumValidate();

        ImportResult<ComputeDeviceEntity> result = ExcelUtils.importExcelFromClient(ComputeDeviceEntity.class, file, 5,
            0);

        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<ComputeDeviceEntity> dataList = result.getDataList();
        if (dataList.size() == 0 && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }

        StringBuilder sb = new StringBuilder(result.getMsg());

        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<ComputerVo> computerVos = new ArrayList<>();

        List<String> assetNames = new ArrayList<>();
        List<String> assetNumbers = new ArrayList<>();
        List<String> assetMac = new ArrayList<>();
        for (ComputeDeviceEntity entity : dataList) {

            if (assetNumbers.contains(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (assetMac.contains(entity.getMac())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("MAC地址重复！");
                continue;
            }

            if (CheckRepeatNumber(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (CheckRepeatMAC(entity.getMac())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("MAC地址重复！");
                continue;
            }

            if (entity.getBuyDate() != null) {

                if (isBuyDataBig(entity.getBuyDate())) {
                    error++;
                    a++;
                    builder.append("第").append(a).append("行").append("购买时间需小于等于今天！");
                    continue;
                }
            }

            if (isDataBig(entity.getDueTime())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期时间需大于等于今天！");
                continue;
            }

            if ("".equals(checkUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("系统中没有此使用者，或已被注销！");
                continue;
            }

            if (!checkOperatingSystem(entity.getOperationSystem())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("操作系统不存在，或已被注销！");
                continue;
            }

            String areaId = null;
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
                if (area.getFullName().equals(entity.getArea())) {
                    areaId = area.getStringId();
                }
            }

            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，或已被注销！");
                continue;
            }

            if (repeat + error == 0) {
                assetNames.add(entity.getName());
                assetNumbers.add(entity.getNumber());
                assetMac.add(entity.getMac());
                ComputerVo computerVo = new ComputerVo();
                Asset asset = new Asset();
                List<BaselineCategoryModelResponse> categoryModelResponseList = redisService.getAllSystemOs();
                for (BaselineCategoryModelResponse categoryModelResponse : categoryModelResponseList) {
                    if (categoryModelResponse.getName().equals(entity.getOperationSystem())) {
                        asset.setOperationSystem((String) categoryModelResponse.getStringId());
                    }
                }
                asset.setResponsibleUserId(checkUser(entity.getUser()));
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(areaId);
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
                asset.setAssetSource(ReportType.MANUAL.getCode());
                asset.setNumber(entity.getNumber());
                asset.setName(entity.getName());
                asset.setManufacturer(entity.getManufacturer());
                asset.setSerial(entity.getSerial());
                asset.setHouseLocation(entity.getHouseLocation());
                asset.setBuyDate(entity.getBuyDate());
                asset.setServiceLife(entity.getDueTime());
                asset.setWarranty(entity.getWarranty());
                asset.setDescrible(entity.getDescription());
                asset.setCategoryModel("计算设备");
                asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
                computerVo.setAsset(asset);
                computerVos.add(computerVo);
            }

            a++;
        }

        if (repeat + error == 0) {
            // assetDao.insertBatch (computerVos);
            for (ComputerVo computerVo : computerVos) {
                Asset asset = computerVo.getAsset();

                try {
                    assetDao.insert(asset);
                } catch (DuplicateKeyException exception) {
                    throw new BusinessException("请勿重复提交！");
                }
                // 记录资产操作流程
                AssetOperationRecord assetOperationRecord = assetRecord(asset.getStringId(), asset.getAreaId());
                assetOperationRecordDao.insert(assetOperationRecord);
                success++;
            }

        }

        String res = "导入成功" + success + "条";
        if (repeat + error > 0) {
            res = "导入失败，";
        }

        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志
        LogHandle.log(computerVos.toString(), AssetEventEnum.ASSET_EXPORT_COMPUTER.getName(),
            AssetEventEnum.ASSET_EXPORT_NET.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_COMPUTER.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_COMPUTER.getName() + " {}", computerVos.toString());

        return stringBuilder.append(builder).append(sb).toString();

    }

    @Override
    @Transactional
    public String importNet(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        // 授权数量限制校验
        anthNumValidate();

        ImportResult<NetworkDeviceEntity> result = ExcelUtils.importExcelFromClient(NetworkDeviceEntity.class, file, 5,
            0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<NetworkDeviceEntity> entities = result.getDataList();
        if (entities.size() == 0 && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }
        StringBuilder sb = new StringBuilder(result.getMsg());
        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<Asset> assets = new ArrayList<>();
        List<String> assetNames = new ArrayList<>();
        List<String> assetNumbers = new ArrayList<>();
        List<String> assetMac = new ArrayList<>();
        List<AssetNetworkEquipment> networkEquipments = new ArrayList<>();

        for (NetworkDeviceEntity networkDeviceEntity : entities) {

            if (assetNumbers.contains(networkDeviceEntity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (assetMac.contains(networkDeviceEntity.getMac())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产MAC地址重复！");
                continue;
            }

            if (networkDeviceEntity.getPortSize() <= 0 || networkDeviceEntity.getPortSize() > 99) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("网口数目范围为1-99！");
                continue;
            }

            if (networkDeviceEntity.getButDate() != null) {

                if (isBuyDataBig(networkDeviceEntity.getButDate())) {
                    error++;
                    a++;
                    builder.append("第").append(a).append("行").append("购买时间需小于等于今天！");
                    continue;
                }
            }

            if (isDataBig(networkDeviceEntity.getDueDate())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期时间需大于等于今天！");
                continue;
            }

            if (CheckRepeatNumber(networkDeviceEntity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (CheckRepeatMAC(networkDeviceEntity.getMac())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产MAC地址重复！");
                continue;
            }

            if ("".equals(checkUser(networkDeviceEntity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("系统没有此使用者，或已被注销！");
                continue;
            }

            String areaId = null;
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
                if (area.getFullName().equals(networkDeviceEntity.getArea())) {
                    areaId = area.getStringId();
                }
            }

            if (!areasStrings.contains(networkDeviceEntity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，或已被注销！");
                continue;
            }

            if (repeat + error == 0) {
                assetNames.add(networkDeviceEntity.getName());
                assetNumbers.add(networkDeviceEntity.getNumber());
                assetMac.add(networkDeviceEntity.getMac());
                Asset asset = new Asset();
                AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
                asset.setResponsibleUserId(checkUser(networkDeviceEntity.getUser()));
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(areaId);
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
                asset.setAssetSource(ReportType.MANUAL.getCode());
                asset.setNumber(networkDeviceEntity.getNumber());
                asset.setName(networkDeviceEntity.getName());
                asset.setManufacturer(networkDeviceEntity.getManufacturer());
                asset.setSerial(networkDeviceEntity.getSerial());
                asset.setHouseLocation(networkDeviceEntity.getHouseLocation());
                asset.setBuyDate(networkDeviceEntity.getButDate());
                asset.setServiceLife(networkDeviceEntity.getDueDate());
                asset.setWarranty(networkDeviceEntity.getWarranty());
                asset.setCategoryModel(importRequest.getCategory());
                asset.setDescrible(networkDeviceEntity.getMemo());
                asset.setImportanceDegree(DataTypeUtils.stringToInteger(networkDeviceEntity.getImportanceDegree()));
                assets.add(asset);
                assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
                assetNetworkEquipment.setFirmwareVersion(networkDeviceEntity.getFirmwareVersion());
                assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetNetworkEquipment.setInterfaceSize(networkDeviceEntity.getInterfaceSize());
                assetNetworkEquipment.setPortSize(networkDeviceEntity.getPortSize());
                assetNetworkEquipment.setIos(networkDeviceEntity.getIos());
                assetNetworkEquipment.setOuterIp(networkDeviceEntity.getOuterIp());
                assetNetworkEquipment.setMacAddress(networkDeviceEntity.getMac());
                assetNetworkEquipment.setCpuVersion(networkDeviceEntity.getCpuVersion());
                assetNetworkEquipment.setSubnetMask(networkDeviceEntity.getSubnetMask());
                assetNetworkEquipment.setExpectBandwidth(networkDeviceEntity.getExpectBandwidth());
                assetNetworkEquipment.setNcrmSize(networkDeviceEntity.getNcrmSize());
                assetNetworkEquipment.setCpuSize(networkDeviceEntity.getCpuSize());
                assetNetworkEquipment.setDramSize(networkDeviceEntity.getDramSize());
                assetNetworkEquipment.setFlashSize(networkDeviceEntity.getFlashSize());
                assetNetworkEquipment.setRegister(networkDeviceEntity.getRegister());
                assetNetworkEquipment.setIsWireless(networkDeviceEntity.getIsWireless());
                assetNetworkEquipment.setStatus(1);
                networkEquipments.add(assetNetworkEquipment);
            }

            a++;

        }

        if (repeat + error == 0) {
            try {
                assetDao.insertBatch(assets);
            } catch (DuplicateKeyException exception) {
                throw new BusinessException("请勿重复提交！");
            }

            List<AssetOperationRecord> recordList = new ArrayList<>();
            for (int i = 0; i < assets.size(); i++) {
                String stringId = assets.get(i).getStringId();
                networkEquipments.get(i).setAssetId(stringId);
                recordList.add(assetRecord(stringId, assets.get(i).getAreaId()));
                success++;
            }
            assetNetworkEquipmentDao.insertBatch(networkEquipments);
            assetOperationRecordDao.insertBatch(recordList);
        }

        String res = "导入成功" + success + "条";
        if (repeat + error > 0) {
            res = "导入失败，";
        }

        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志
        LogHandle.log(networkEquipments.toString(), AssetEventEnum.ASSET_EXPORT_NET.getName(),
            AssetEventEnum.ASSET_EXPORT_NET.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_NET.getName() + " {}", networkEquipments.toString());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_NET.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        return stringBuilder.append(builder).append(sb).toString();
    }

    @Override
    @Transactional
    public String importSecurity(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        // 授权数量限制校验
        anthNumValidate();

        ImportResult<SafetyEquipmentEntiy> result = ExcelUtils.importExcelFromClient(SafetyEquipmentEntiy.class, file,
            5, 0);

        StringBuilder builder = new StringBuilder();
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<SafetyEquipmentEntiy> resultDataList = result.getDataList();
        if (resultDataList.size() == 0 && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }
        StringBuilder sb = new StringBuilder(result.getMsg());

        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        List<String> assetNames = new ArrayList<>();
        List<String> assetNumbers = new ArrayList<>();
        List<String> assetMac = new ArrayList<>();
        List<Asset> assets = new ArrayList<>();
        List<AssetSafetyEquipment> assetSafetyEquipments = new ArrayList<>();
        for (SafetyEquipmentEntiy entity : resultDataList) {

            if (assetNumbers.contains(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (assetMac.contains(entity.getMac())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产MAC地址重复！");
                continue;
            }

            if (CheckRepeatNumber(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (CheckRepeatMAC(entity.getMac())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产MAC地址重复！");
                continue;
            }

            if (entity.getBuyDate() != null) {

                if (isBuyDataBig(entity.getBuyDate())) {
                    error++;
                    a++;
                    builder.append("第").append(a).append("行").append("购买时间需小于等于今天！");
                    continue;
                }
            }

            if (isDataBig(entity.getDueDate())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期时间需大于等于今天！");
                continue;
            }

            if ("".equals(checkUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("系统中没有此使用者，或已被注销！");
                continue;
            }
            if (!checkOperatingSystem(entity.getOperationSystem())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("操作系统不存在，或已被注销！");
                continue;
            }

            String areaId = null;
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
                if (area.getFullName().equals(entity.getArea())) {
                    areaId = area.getStringId();
                }
            }

            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域！");
                continue;
            }

            if (repeat + error == 0) {
                assetNames.add(entity.getName());
                assetNumbers.add(entity.getNumber());
                assetMac.add(entity.getMac());
                Asset asset = new Asset();
                AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
                List<BaselineCategoryModelResponse> categoryModelResponseList = redisService.getAllSystemOs();
                for (BaselineCategoryModelResponse categoryModelResponse : categoryModelResponseList) {
                    if (categoryModelResponse.getName().equals(entity.getOperationSystem())) {
                        asset.setOperationSystem((String) categoryModelResponse.getStringId());
                    }
                }
                asset.setResponsibleUserId(checkUser(entity.getUser()));
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(areaId);
                asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
                asset.setAssetSource(ReportType.MANUAL.getCode());
                asset.setNumber(entity.getNumber());
                asset.setName(entity.getName());
                asset.setManufacturer(entity.getManufacturer());
                asset.setSerial(entity.getSerial());

                asset.setHouseLocation(entity.getHouseLocation());

                asset.setBuyDate(entity.getBuyDate());
                asset.setServiceLife(entity.getDueDate());
                asset.setWarranty(entity.getWarranty());
                asset.setDescrible(entity.getMemo());

                asset.setCategoryModel(importRequest.getCategory());
                assets.add(asset);
                assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
                assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetSafetyEquipment.setIp(entity.getIp());
                assetSafetyEquipment.setMac(entity.getMac());
                assetSafetyEquipment.setStatus(1);
                assetSafetyEquipments.add(assetSafetyEquipment);
            }
            a++;
        }

        if (repeat + error == 0) {
            try {
                assetDao.insertBatch(assets);
            } catch (DuplicateKeyException exception) {
                throw new BusinessException("请勿重复提交！");
            }

            List<AssetOperationRecord> recordList = new ArrayList<>();
            for (int i = 0; i < assets.size(); i++) {
                String stringId = assets.get(i).getStringId();
                assetSafetyEquipments.get(i).setAssetId(stringId);
                recordList.add(assetRecord(stringId, assets.get(i).getAreaId()));
                success++;
            }
            assetSafetyEquipmentDao.insertBatch(assetSafetyEquipments);
            assetOperationRecordDao.insertBatch(recordList);
        }

        String res = "导入成功" + success + "条";
        if (repeat + error > 0) {
            res = "导入失败，";
        }

        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志
        LogHandle.log(assetSafetyEquipments.toString(), AssetEventEnum.ASSET_EXPORT_SAFETY.getName(),
            AssetEventEnum.ASSET_EXPORT_SAFETY.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_SAFETY.getName() + " {}", assetSafetyEquipments.toString());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_SAFETY.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        return stringBuilder.append(builder).append(sb).toString();

    }

    @Override
    @Transactional
    public String importStory(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        // 授权数量限制校验
        anthNumValidate();

        ImportResult<StorageDeviceEntity> result = ExcelUtils.importExcelFromClient(StorageDeviceEntity.class, file, 5,
            0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }

        List<StorageDeviceEntity> resultDataList = result.getDataList();
        if (resultDataList.size() == 0 && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }
        StringBuilder sb = new StringBuilder(result.getMsg());

        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<Asset> assets = new ArrayList<>();
        List<String> assetNames = new ArrayList<>();
        List<String> assetNumbers = new ArrayList<>();
        List<AssetStorageMedium> assetStorageMedia = new ArrayList<>();
        for (StorageDeviceEntity entity : resultDataList) {

            if (assetNumbers.contains(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (CheckRepeatNumber(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (entity.getBuyDate() != null) {

                if (isBuyDataBig(entity.getBuyDate())) {
                    error++;
                    a++;
                    builder.append("第").append(a).append("行").append("购买时间需小于等于今天！");
                    continue;
                }
            }

            if (isDataBig(entity.getDueDate())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期时间需大于等于今天！");
                continue;
            }

            if ("".equals(checkUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("系统中没有此使用者，或已被注销！");
                continue;
            }

            String areaId = null;
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
                if (area.getFullName().equals(entity.getArea())) {
                    areaId = area.getStringId();
                }
            }

            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，或已被注销！");
                continue;
            }

            if (repeat + error == 0) {
                assetNames.add(entity.getName());
                assetNumbers.add(entity.getNumber());
                Asset asset = new Asset();
                asset.setResponsibleUserId(checkUser(entity.getUser()));
                AssetStorageMedium assetStorageMedium = new AssetStorageMedium();
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(areaId);
                asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
                asset.setAssetSource(ReportType.MANUAL.getCode());
                asset.setNumber(entity.getNumber());
                asset.setName(entity.getName());
                asset.setManufacturer(entity.getManufacturer());
                asset.setFirmwareVersion(entity.getFirmwareVersion());
                asset.setSerial(entity.getSerial());
                asset.setHouseLocation(entity.getHouseLocation());
                asset.setBuyDate(entity.getBuyDate());
                asset.setServiceLife(entity.getDueDate());
                asset.setWarranty(entity.getWarranty());
                asset.setDescrible(entity.getMemo());
                assets.add(asset);
                assetStorageMedium.setGmtCreate(System.currentTimeMillis());
                assetStorageMedium.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetStorageMedium.setFirmwareVersion(entity.getFirmwareVersion());
                assetStorageMedium.setDiskNumber(entity.getHardDiskNum());
                assetStorageMedium.setDriverNumber(entity.getDriveNum());
                assetStorageMedium.setMaximumStorage(entity.getCapacity());
                assetStorageMedium.setHighCache(entity.getHighCache());
                assetStorageMedium.setStatus(1);

                if (entity.getRaidSupport() != null) {

                    assetStorageMedium.setRaidSupport(entity.getRaidSupport() == 1 ? "是" : "否");
                }
                assetStorageMedium.setInnerInterface(entity.getInnerInterface());
                assetStorageMedium.setOsVersion(entity.getSlotType());
                assetStorageMedium.setAverageTransferRate(entity.getAverageTransmissionRate());
                assetStorageMedia.add(assetStorageMedium);
            }

            a++;
        }

        if (repeat + error == 0) {
            try {
                assetDao.insertBatch(assets);
            } catch (DuplicateKeyException exception) {
                throw new BusinessException("请勿重复提交！");
            }

            List<AssetOperationRecord> recordList = new ArrayList<>();
            for (int i = 0; i < assets.size(); i++) {
                String stringId = assets.get(i).getStringId();
                assetStorageMedia.get(i).setAssetId(stringId);
                recordList.add(assetRecord(stringId, assets.get(i).getAreaId()));
                success++;
            }
            assetStorageMediumDao.insertBatch(assetStorageMedia);
            assetOperationRecordDao.insertBatch(recordList);
        }

        String res = "导入成功" + success + "条";
        if (repeat + error > 0) {
            res = "导入失败，";
        }

        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志
        LogHandle.log(assetStorageMedia.toString(), AssetEventEnum.ASSET_EXPORT_STORAGE.getName(),
            AssetEventEnum.ASSET_EXPORT_STORAGE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_STORAGE.getName() + " {}", assetStorageMedia.toString());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_STORAGE.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));

        return stringBuilder.append(builder).append(sb).toString();

    }

    @Override
    @Transactional
    public String importOhters(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        // 授权数量限制校验
        anthNumValidate();

        ImportResult<OtherDeviceEntity> result = ExcelUtils.importExcelFromClient(OtherDeviceEntity.class, file, 5, 0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<OtherDeviceEntity> resultDataList = result.getDataList();
        if (resultDataList.size() == 0 && StringUtils.isBlank(result.getMsg())) {
            return "导入失败，模板中无数据！" + result.getMsg();
        }

        StringBuilder sb = new StringBuilder(result.getMsg());

        if (result.getMsg().contains("导入失败")) {
            return sb.toString();
        }

        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<Asset> assets = new ArrayList<>();
        List<String> assetNames = new ArrayList<>();
        List<String> assetNumbers = new ArrayList<>();
        for (OtherDeviceEntity entity : resultDataList) {

            if (assetNumbers.contains(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (CheckRepeatNumber(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (entity.getBuyDate() != null) {

                if (isBuyDataBig(entity.getBuyDate())) {
                    error++;
                    a++;
                    builder.append("第").append(a).append("行").append("购买时间需小于等于今天！");
                    continue;
                }
            }

            if (isDataBig(entity.getDueDate())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期时间需大于等于今天！");
                continue;
            }

            if ("".equals(checkUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("系统中没有此使用者，或已被注销！");
                continue;
            }

            String areaId = null;
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
                if (area.getFullName().equals(entity.getArea())) {
                    areaId = area.getStringId();
                }
            }

            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，或已被注销！");
                continue;
            }

            if (repeat + error == 0) {
                assetNames.add(entity.getName());
                assetNumbers.add(entity.getNumber());
                Asset asset = new Asset();
                asset.setResponsibleUserId(checkUser(entity.getUser()));
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(areaId);
                asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
                asset.setAssetSource(ReportType.MANUAL.getCode());
                asset.setNumber(entity.getNumber());
                asset.setName(entity.getName());
                asset.setManufacturer(entity.getManufacturer());
                asset.setSerial(entity.getSerial());
                asset.setBuyDate(entity.getBuyDate());
                asset.setServiceLife(entity.getDueDate());
                asset.setWarranty(entity.getWarranty());
                asset.setDescrible(entity.getMemo());
                asset.setCategoryModel(importRequest.getCategory());
                assets.add(asset);
            }

            a++;
        }

        if (repeat + error == 0) {
            try {
                assetDao.insertBatch(assets);
            } catch (DuplicateKeyException exception) {
                throw new BusinessException("请勿重复提交！");
            }

            List<AssetOperationRecord> recordList = new ArrayList<>();
            for (Asset asset : assets) {
                recordList.add(assetRecord(asset.getStringId(), asset.getAreaId()));
                success++;
            }
            assetOperationRecordDao.insertBatch(recordList);
        }

        String res = "导入成功" + success + "条";
        if (repeat + error > 0) {
            res = "导入失败，";
        }

        StringBuilder stringBuilder = new StringBuilder(res);

        // 写入业务日志
        LogHandle.log(assets.toString(), AssetEventEnum.ASSET_EXPORT_OTHERS.getName(),
            AssetEventEnum.ASSET_EXPORT_OTHERS.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_OTHERS.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_OTHERS.getName() + " {}", assets.toString());
        return stringBuilder.append(builder).append(sb).toString();
    }

    private AssetOperationRecord assetRecord(String id, String areaId) throws Exception {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(id);
        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetOperationRecord.setContent("导入硬件资产");
        assetOperationRecord.setProcessResult(1);
        assetOperationRecord.setOriginStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setAreaId(areaId);
        return assetOperationRecord;
        // assetOperationRecordDao.insert(assetOperationRecord);
    }

    @Override
    public Integer queryAssetCountByAreaIds(List<Integer> areaIds) {

        // 如果移除以后全部为空，则直接返回0
        if (CollectionUtils.isEmpty(areaIds)) {
            return 0;
        }

        return assetDao.queryAssetCountByAreaIds(areaIds);
    }

    @Override
    public void exportData(AssetQuery assetQuery, HttpServletResponse response,
                           HttpServletRequest request) throws Exception {
        if ((assetQuery.getStart() != null && assetQuery.getEnd() != null)) {
            assetQuery.setStart(assetQuery.getStart() - 1);
            assetQuery.setEnd(assetQuery.getEnd() - assetQuery.getStart());
        }
        assetQuery.setPageSize(Constants.ALL_PAGE);
        assetQuery.setAreaIds(
            ArrayTypeUtil.objectArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().toArray()));
        List<AssetResponse> list = this.findPageAsset(assetQuery).getItems();
        List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setSheetName("资产信息表");
        downloadVO.setDownloadList(assetEntities);
        if (Objects.nonNull(assetEntities) && assetEntities.size() > 0) {
            excelDownloadUtil.excelDownload(request, response,
                "硬件资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);
            LogUtils.recordOperLog(
                new BusinessData("导出《硬件资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT) + "》", 0, "",
                    assetQuery, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        } else {
            throw new BusinessException("导出数据为空");
        }
    }

    @Override
    public List<String> pulldownUnconnectedManufacturer(Integer isNet, String primaryKey) throws Exception {
        AssetQuery query = new AssetQuery();
        // Map<String, String> categoryMap = assetCategoryModelService.getSecondCategoryMap();
        List<Integer> categoryCondition = new ArrayList<>();
        // List<AssetCategoryModel> all = assetCategoryModelService.getAll();
        // for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
        if ((isNet == null) || isNet == 1) {
            // if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
            // categoryCondition.addAll(
            // assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
            // }
            // }
            // if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
            // categoryCondition.addAll(
            // assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
            // }
        }
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryCondition));
        query.setPrimaryKey(primaryKey);
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        return assetLinkRelationDao.pulldownUnconnectedManufacturer(query);
    }

    /**
     * 判断操作系统是否存在
     *
     * @return
     */
    private Boolean checkOperatingSystem(String checkStr) {
        List<BaselineCategoryModelResponse> linkedHashMapList = operatingSystemClient.getInvokeOperatingSystem();
        for (BaselineCategoryModelResponse baselineCategoryModelResponse : linkedHashMapList) {
            if (Objects.equals(baselineCategoryModelResponse.getName(), checkStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过ID判断操作系统是否存在且是叶子节点
     *
     * @return
     */
    private boolean checkOperatingSystemById(String id) {
        List<BaselineCategoryModelNodeResponse> baselineCategoryModelNodeResponse = operatingSystemClient
            .getInvokeOperatingSystemTree();
        Set<String> result = new HashSet<>();
        if (CollectionUtils.isNotEmpty(baselineCategoryModelNodeResponse)) {
            for (BaselineCategoryModelNodeResponse baselineCategoryModelNodeResponse1 : baselineCategoryModelNodeResponse) {
                operatingSystemRecursion(result, baselineCategoryModelNodeResponse1);
            }
        }
        return result.contains(id);
    }

    private void operatingSystemRecursion(Set<String> result, BaselineCategoryModelNodeResponse response) {
        if (response != null) {
            if (CollectionUtils.isEmpty(response.getChildrenNode())) {
                result.add(aesEncoder.decode(response.getStringId(), LoginUserUtil.getLoginUser().getUsername()));
            } else {
                for (BaselineCategoryModelNodeResponse baselineCategoryModelNodeResponse : response.getChildrenNode()) {
                    operatingSystemRecursion(result, baselineCategoryModelNodeResponse);
                }
            }
        }
    }

    @Override
    public AlarmAssetDataResponse queryAlarmAssetList(AlarmAssetRequest alarmAssetRequest) throws Exception {
        List<Asset> assetList = assetDao.queryAlarmAssetList(alarmAssetRequest);
        BaseConverter<Asset, AlarmAssetResponse> converter = new BaseConverter<Asset, AlarmAssetResponse>() {
            @Override
            protected void convert(Asset asset, AlarmAssetResponse alarmAssetResponse) {
                super.convert(asset, alarmAssetResponse);
                alarmAssetResponse.setAssetNumber(asset.getNumber());
                alarmAssetResponse.setAssetId(asset.getStringId());
            }
        };
        return new AlarmAssetDataResponse(converter.convert(assetList, AlarmAssetResponse.class));
    }

    @Override
    public IDResponse findAssetIds() {
        IDResponse idResponse = new IDResponse();
        List<Integer> list = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        if (CollectionUtils.isEmpty(list)) {
            return idResponse;
        }
        idResponse.setResult(StringUtils.join(assetDao.findAssetIds(list).toArray(), ","));
        return idResponse;
    }

    @Override
    public Integer queryWaitRegistCount() {
        if (Objects.isNull(LoginUserUtil.getLoginUser())) {
            return 0;
        }
        return assetDao.queryWaitRegistCount(AssetStatusEnum.WAIT_REGISTER.getCode(),
            LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
    }

    @Override
    public Integer queryNormalCount() {
        AssetQuery query = new AssetQuery();
        // 已入网、待退役资产
        query.setAssetStatusList(Arrays.asList(7, 8));

        // 当前用户所在区域
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        return assetDao.queryNormalCount(query);
    }

    @Override
    public List<String> queryUuidByAreaIds(AreaIdRequest request) throws Exception {
        if (CollectionUtils.isNotEmpty(request.getAreaIds())) {
            return assetDao.findUuidByAreaIds(request.getAreaIds());
        } else {
            throw new BusinessException("区域ID不能为空");
        }

    }

    @KafkaListener(topics = KafkaConfig.USER_AREA_TOPIC, containerFactory = "sampleListenerContainerFactory")
    public void listen(String data, Acknowledgment ack) {
        AreaOperationRequest areaOperationRequest = JsonUtil.json2Object(data, AreaOperationRequest.class);
        if (areaOperationRequest != null) {
            try {
                LogUtils.info(logger, "消息消费成功 " + data);
                assetDao.updateAssetAreaId(areaOperationRequest.getTargetAreaId(),
                    areaOperationRequest.getSourceAreaIds());
                ack.acknowledge();
            } catch (Exception e) {
                LogUtils.error(logger, e, "消息消费失败");
            }
        }
    }

    /**
     * 授权数量校验
     */
    private void anthNumValidate() {
        Integer authNum = LicenseUtil.getLicense().getAssetNum();
        if (!Objects.isNull(authNum)) {
            Integer num = assetDao.countAsset();
            if (authNum <= num) {
                throw new BusinessException("资产数量已超过授权数量，请联系客服人员！");
            }
        } else {
            throw new BusinessException("license异常，请联系客服人员！");
        }
    }
}

@Component
class AssetEntityConvert extends BaseConverter<AssetResponse, AssetEntity> {
    private final Logger logger = LogUtils.get();

    @Override
    protected void convert(AssetResponse asset, AssetEntity assetEntity) {
        if (Objects.nonNull(asset.getAssetStatus())) {
            AssetStatusEnum assetStatusEnum = AssetStatusEnum.getAssetByCode(asset.getAssetStatus());
            assetEntity.setAssetStatus(assetStatusEnum == null ? "" : assetStatusEnum.getMsg());
        }
        assetEntity.setCategoryModelName(asset.getCategoryModelName());
        assetEntity.setGmtCreate(longToDateString(asset.getGmtCreate()));
        assetEntity.setServiceLife(longToDateString(asset.getServiceLife()));
        AssetImportanceDegreeEnum degreeEnum = AssetImportanceDegreeEnum.getByCode(asset.getImportanceDegree());
        if (degreeEnum != null) {
            assetEntity.setImportanceDegree(degreeEnum.getMsg());
        } else {
            assetEntity.setImportanceDegree(null);
        }
        assetEntity.setResponsibleUserName(asset.getResponsibleUserName());
        if (null != asset.getAssetSource()) {
            assetEntity.setAssetSource(asset.getAssetSource().compareTo(1) == 0 ? "自动上报" : "人工登记");
        }
        assetEntity.setFirstEnterNett(longToDateString(asset.getFirstEnterNett()));
        super.convert(asset, assetEntity);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }

}
