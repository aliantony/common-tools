package com.antiy.asset.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.antiy.asset.vo.query.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.cache.AssetBaseDataCache;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.*;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.*;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.asset.vo.user.OauthMenuResponse;
import com.antiy.asset.vo.user.UserStatus;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.base.SysArea;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.*;
import com.antiy.common.utils.DataTypeUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p> 资产主表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService {
    private static final int                                                    ALL_PAGE = -1;
    private Logger                                                              logger   = LogUtils
        .get(this.getClass());
    @Resource
    private AssetDao                                                            assetDao;
    @Resource
    private AssetCpeFilterDao                                                   assetCpeFilterDao;
    @Resource
    private AssetInstallTemplateDao                                             assetInstallTemplateDao;
    @Resource
    private AssetNetworkEquipmentDao                                            assetNetworkEquipmentDao;
    @Resource
    private AssetSafetyEquipmentDao                                             assetSafetyEquipmentDao;
    @Resource
    private TransactionTemplate                                                 transactionTemplate;
    @Resource
    private AssetSoftwareRelationDao                                            assetSoftwareRelationDao;
    @Resource
    private AssetStorageMediumDao                                               assetStorageMediumDao;
    @Resource
    private AssetOperationRecordDao                                             assetOperationRecordDao;
    @Resource
    private AssetLockDao                                                        assetLockDao;
    @Resource
    private AssetNettypeManageDao                                               assetNettypeManageDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>                                  requestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse>                                 responseConverter;
    @Resource
    private BaseConverter<AssetIpRelation, AssetIpRelationResponse>             ipResponseConverter;
    @Resource
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentResponse> networkResponseConverter;
    @Resource
    private BaseConverter<AssetMacRelation, AssetMacRelationResponse>           macResponseConverter;
    @Resource
    private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentResponse>   safetyResponseConverter;
    @Resource
    private BaseConverter<AssetStorageMedium, AssetStorageMediumResponse>       storageResponseConverter;
    @Resource
    private BaseConverter<AssetAssembly, AssetAssemblyResponse>                 assemblyResponseBaseConverter;
    @Resource
    private BaseConverter<AssetGroup, AssetGroupResponse>                       assetGroupResponseBaseConverter;
    @Resource
    private BaseConverter<AssetLockRequest, AssetLock>                          assetLockConverter;
    @Resource
    private BaseConverter<AssetBusinessRelation, AssetBusinessRelationResponse> businessRelationResponseConverter;
    @Resource
    private AssetUserDao                                                        assetUserDao;
    @Resource
    private AssetGroupRelationDao                                               assetGroupRelationDao;
    @Resource
    private ExcelDownloadUtil                                                   excelDownloadUtil;
    @Resource
    private CSVUtils                                                            csvUtils;
    @Resource
    private Dom4jUtils                                                          dom4jUtils;
    @Resource
    private AssetEntityConvert                                                  assetEntityConvert;
    @Resource
    private AssetGroupDao                                                       assetGroupDao;
    @Resource
    private ActivityClient                                                      activityClient;
    @Resource
    private SysUserClient                                                       sysUserClient;
    @Resource
    private AesEncoder                                                          aesEncoder;
    @Resource
    private RedisUtil                                                           redisUtil;
    @Resource
    private AssetLinkRelationDao                                                assetLinkRelationDao;
    @Resource
    private OperatingSystemClient                                               operatingSystemClient;
    @Resource
    private IRedisService                                                       redisService;
    @Resource
    private AssetIpRelationDao                                                  assetIpRelationDao;
    @Resource
    private AssetMacRelationDao                                                 assetMacRelationDao;
    @Resource
    private AssetHardSoftLibDao                                                 assetHardSoftLibDao;
    @Resource
    private AssetAssemblyDao                                                    assetAssemblyDao;
    @Resource
    private AssetOperationRecordDao                                             operationRecordDao;
    @Resource
    private BaseLineClient                                                      baseLineClient;
    @Resource
    private AreaClient                                                          areaClient;
    @Resource
    private AssetBusinessRelationDao                                            assetBusinessRelationDao;
    @Resource
    private AssetEntryServiceImpl                                               entryService;
    @Resource
    private AssetBusinessServiceImpl                                            businessService;
    @Resource
    private AssetBaseDataCache                                                  assetBaseDataCache;
    @Resource
    private AssetCpeTreeDao                                                     treeDao;
    private Object                                                              lock     = new Object();

    @Override
    public ActionResponse saveAsset(AssetOuterRequest request) throws Exception {
        // 授权数量校验
        anthNumValidate();
        AssetRequest requestAsset = request.getAsset();
        AssetNetworkEquipmentRequest networkEquipmentRequest = request.getNetworkEquipment();
        AssetStorageMediumRequest assetStorageMedium = request.getAssetStorageMedium();
        Long currentTimeMillis = System.currentTimeMillis();
        final String[] uuid = new String[1];
        String msg = null;
        Integer curentUser = LoginUserUtil.getLoginUser().getId();
        Integer id = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    String aid;

                    String areaId = requestAsset.getAreaId();
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class, areaId);
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    BusinessExceptionUtils.isTrue(!Objects.isNull(assetBaseDataCache.get(AssetBaseDataCache.ASSET_USER,
                        DataTypeUtils.stringToInteger(requestAsset.getResponsibleUserId()))), "使用者不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "当前区域不存在，或已经注销");
                    List<AssetGroupRequest> assetGroup = requestAsset.getAssetGroups();
                    Asset asset = requestConverter.convert(requestAsset, Asset.class);
                    // 设置顶级品类型号
                    setCategroy(asset);
                    AssetHardSoftLib byBusinessId = assetHardSoftLibDao.getByBusinessId(requestAsset.getBusinessId());
                    BusinessExceptionUtils.isTrue(byBusinessId.getStatus() == 1, "当前(厂商+名称+版本)不存在，或已经注销");
                    // 不跳过整改=>整改中(计算设备，安全设备)
                    if (!Objects.isNull(request.getManualStartActivityRequest())) {
                        asset.setAssetStatus(AssetStatusEnum.CORRECTING.getCode());
                    } else {
                        // 计算设备、网络设备=>待准入
                        if (AssetCategoryEnum.COMPUTER.getCode().equals(asset.getCategoryModelType())
                            || AssetCategoryEnum.NETWORK.getCode().equals(asset.getCategoryModelType())) {
                            asset.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
                        } else {
                            // 安全设备、存储设备、其他设备 =>已入网
                            asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
                            asset.setFirstEnterNett(currentTimeMillis);
                        }
                    }
                    asset.setBusinessId(Long.valueOf(requestAsset.getBusinessId()));
                    if (StringUtils.isNotBlank(requestAsset.getBaselineTemplateId())) {
                        ActionResponse baselineTemplate = baseLineClient
                            .getBaselineTemplate(requestAsset.getBaselineTemplateId());
                        HashMap body = (HashMap) baselineTemplate.getBody();
                        Integer isEnable = (Integer) body.get("isEnable");
                        BusinessExceptionUtils.isTrue(isEnable == 1, "当前基准模板已经禁用!");
                        asset.setBaselineTemplateId(requestAsset.getBaselineTemplateId());
                        asset.setBaselineTemplateCorrelationGmt(System.currentTimeMillis());
                    }
                    // 装机模板
                    if (StringUtils.isNotBlank(requestAsset.getInstallTemplateId())) {
                        checkInstallTemplateCompliance(requestAsset.getInstallTemplateId());
                        asset.setInstallTemplateId(requestAsset.getInstallTemplateId());
                        asset.setInstallTemplateCorrelationGmt(System.currentTimeMillis());
                    }

                    if (CollectionUtils.isNotEmpty(assetGroup)) {
                        assembleAssetGroupName(assetGroup, asset);
                    }
                    asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    asset.setGmtCreate(System.currentTimeMillis());
                    asset.setAssetSource(AssetSourceEnum.MANUAL_REGISTRATION.getCode());
                    // 处理自定义字段
                    List<AssetCustomizeRequest> assetCustomizeRequests = request.getAssetCustomizeRequests();
                    if (CollectionUtils.isNotEmpty(assetCustomizeRequests)) {
                        asset.setCustomField(JsonUtil.ListToJson(assetCustomizeRequests));
                    }

                    // 是否孤岛设备：1、是 2、否
                    asset.setIsOrphan(requestAsset.getIsOrphan());

                    // 返回的资产id
                    assetDao.insert(asset);
                    aid = asset.getStringId();
                    // 添加业务 关联
                    List<AssetBusinessRelationRequest> asetBusinessRelationRequests = request
                        .getAsetBusinessRelationRequests();
                    if (CollectionUtils.isNotEmpty(asetBusinessRelationRequests)) {
                        List<AssetBusinessRelation> assetBusinessRelations = BeanConvert
                            .convert(asetBusinessRelationRequests, AssetBusinessRelation.class);
                        assetBusinessRelations.forEach(assetBusinessRelation -> {
                            assetBusinessRelation.setAssetId(aid);
                            assetBusinessRelation.setGmtCreate(currentTimeMillis);
                            assetBusinessRelation.setCreateUser(curentUser);
                        });
                        assetBusinessRelationDao.insertBatch(assetBusinessRelations);
                    }
                    // 保存资产与资产组关系
                    insertBatchAssetGroupRelation(aid, assetGroup);
                    uuid[0] = asset.getUuid();
                    // //装机模板 .存入软件
                    if (StringUtils.isNotBlank(asset.getInstallTemplateId())) {
                        List<AssetHardSoftLib> assetHardSoftLibs = assetHardSoftLibDao
                            .querySoftsRelations(asset.getInstallTemplateId());
                        if (CollectionUtils.isNotEmpty(assetHardSoftLibs)) {
                            List<AssetSoftwareRelation> assetSoftwareRelations = Lists.newArrayList();
                            assetHardSoftLibs.forEach(assetHardSoftLib -> {
                                AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                                assetSoftwareRelation.setAssetId(asset.getStringId());
                                assetSoftwareRelation.setGmtCreate(currentTimeMillis);
                                assetSoftwareRelation.setSoftwareId(Long.parseLong(assetHardSoftLib.getBusinessId()));
                                assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                assetSoftwareRelation.setModifyUser(LoginUserUtil.getLoginUser().getId());
                                assetSoftwareRelations.add(assetSoftwareRelation);
                            });
                            assetSoftwareRelationDao.insertBatch(assetSoftwareRelations);
                        }
                    }

                    // 组件
                    if (CollectionUtils.isNotEmpty(request.getAssemblyRequestList())) {
                        List<AssetAssemblyRequest> assemblyRequestList = request.getAssemblyRequestList();
                        List<AssetAssembly> convert = BeanConvert.convert(assemblyRequestList, AssetAssembly.class);
                        List<AssetAssembly> insert = Lists.newArrayList();
                        StringBuilder builder = new StringBuilder();
                        convert.forEach(assetAssembly -> {
                            if (assetAssemblyDao.findAssemblyByBusiness(assetAssembly.getBusinessId()) <= 0) {
                                builder.append("厂商:" + assetAssembly.getSupplier() + " 名称:"
                                               + assetAssembly.getProductName() + " ");
                            }
                            assetAssembly.setAssetId(aid);
                        });
                        if (builder.length() > 0) {
                            BusinessExceptionUtils.isTrue(false, builder.append(",所选组件已更新，请刷新页面后重新添加!").toString());
                        } else {
                            convert.forEach(assetAssembly -> {
                                for (int i = 0; i < assetAssembly.getAmount(); i++) {
                                    AssetAssembly assembly = new AssetAssembly();
                                    assembly.setAssetId(assetAssembly.getAssetId());
                                    assembly.setBusinessId(assetAssembly.getBusinessId());
                                    assembly.setUniqueId(Long.parseLong(SnowFlakeUtil.getSnowId()));
                                    insert.add(assembly);
                                }
                            });
                        }
                        assetAssemblyDao.insertBatch(insert);
                    }
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

                    // 保存网络设备
                    if (networkEquipmentRequest != null) {
                        saveNetwork(aid, networkEquipmentRequest);
                    }
                    // 保存存储设备
                    if (assetStorageMedium != null) {
                        AssetStorageMedium medium = BeanConvert.convertBean(assetStorageMedium,
                            AssetStorageMedium.class);
                        saveStorage(asset, assetStorageMedium, medium);
                    }

                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(aid);
                    assetOperationRecord.setOriginStatus(0);
                    assetOperationRecord.setTargetStatus(asset.getAssetStatus());
                    assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setContent(AssetFlowEnum.REGISTER.getNextMsg());
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(currentTimeMillis);
                    assetOperationRecord.setStatus(1);
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return Integer.parseInt(aid);
                } catch (DuplicateKeyException exception) {
                    transactionStatus.setRollbackOnly();
                    throw new BusinessException("重复提交！");
                } catch (BusinessException e) {
                    transactionStatus.setRollbackOnly();
                    throw new BusinessException(e.getMessage());
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    logger.error("录入失败", e);
                    throw new BusinessException("操作失败");
                }
            }

        });
        if (request.getManualStartActivityRequest() != null) {

            ManualStartActivityRequest activityRequest = request.getManualStartActivityRequest();
            activityRequest.setBusinessId(String.valueOf(id));
            activityRequest.setProcessDefinitionKey("assetAdmittance");
            activityRequest.setAssignee(LoginUserUtil.getLoginUser().getId() + "");
            ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
            // 如果流程引擎为空,直接返回错误信息 启动流程后，activiti会返回procInstId 发给漏洞
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                // 调用失败，逻辑删登记的资产
                assetDao.deleteById(id);
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
            String procInstId = actionResponse.getBody().toString();
            assetOperationRecordDao.writeProcInstId(id, Integer.valueOf(procInstId));
        }

        // 是否需要进行漏扫
        if (request.getNeedScan()) {
            ActionResponse scan = baseLineClient.scan(id.toString());
            // 如果漏洞为空,直接返回错误信息
            if (null == scan || !RespBasicCode.SUCCESS.getResultCode().equals(scan.getHead().getCode())) {
                // 调用失败，逻辑删登记的资产
                assetDao.deleteById(id);
                return scan == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : scan;
            }
        }
        return ActionResponse.success(msg);
    }

    private void setCategroy(Asset asset) {
        List<AssetCategoryModel> assetCategoryModels = assetBaseDataCache
            .getAll(AssetBaseDataCache.ASSET_CATEGORY_MODEL);
        Integer flag = asset.getCategoryModel();
        for (int i = assetCategoryModels.size() - 1; i > 0; i++) {
            AssetCategoryModel categoryModel = assetCategoryModels.get(i);
            if (categoryModel.getId().equals(flag)) {
                if ("1".equals(categoryModel.getParentId())) {
                    asset.setCategoryModelType(categoryModel.getId() - 1);
                    break;
                } else {
                    flag = categoryModel.getId();
                }
            }
        }
    }

    private void checkInstallTemplateCompliance(String templateId) throws Exception {
        if (StringUtils.isNotBlank(templateId)) {
            AssetInstallTemplate response = assetInstallTemplateDao.getById(templateId);
            // 装机模板必须为启用
            if (Objects.isNull(response) || !response.getStatus().equals(1)
                || !response.getCurrentStatus().equals(AssetInstallTemplateStatusEnum.ENABLE.getCode())) {
                throw new RequestParamValidateException("装机模板不存在或未启用,请重新选择");
            }
        }
    }

    private Integer saveStorage(Asset asset, AssetStorageMediumRequest assetStorageMedium,
                                AssetStorageMedium medium) throws Exception {
        medium.setAssetId(asset.getStringId());
        medium.setGmtCreate(System.currentTimeMillis());
        medium.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetStorageMediumDao.insert(medium);
        return medium.getId();
    }

    private Integer saveNetwork(String aid, AssetNetworkEquipmentRequest networkEquipmentRequest) throws Exception {
        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipmentRequest,
            AssetNetworkEquipment.class);
        assetNetworkEquipment.setAssetId(aid);
        assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkEquipmentDao.insert(assetNetworkEquipment);
        return assetNetworkEquipment.getId();
    }

    private Integer saveSafety(String aid, AssetSafetyEquipmentRequest safetyEquipmentRequest) throws Exception {
        AssetSafetyEquipment safetyEquipment = BeanConvert.convertBean(safetyEquipmentRequest,
            AssetSafetyEquipment.class);
        safetyEquipment.setAssetId(aid);
        safetyEquipment.setGmtCreate(System.currentTimeMillis());
        safetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSafetyEquipmentDao.insert(safetyEquipment);
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

    private void insertBatchAssetGroupRelation(String assetId, List<AssetGroupRequest> assetGroup) {
        if (CollectionUtils.isNotEmpty(assetGroup)) {
            List<AssetGroupRelation> groupRelations = new ArrayList<>();
            assetGroup.forEach(assetGroupRequest -> {
                AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                assetGroupRelation.setAssetGroupId(assetGroupRequest.getId());
                assetGroupRelation.setAssetId(assetId);
                assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                groupRelations.add(assetGroupRelation);
            });
            assetGroupRelationDao.insertBatch(groupRelations);
        }
    }

    @Override
    public boolean checkRepeatMAC(String mac, Integer id) throws Exception {
        Integer countIp = assetDao.findCountMac(mac, id);
        return countIp >= 1;
    }

    @Override
    public boolean checkRepeatNumber(String number, Integer id) {
        return assetDao.findCountAssetNumber(number, id) >= 1;
    }

    @Override
    public List<AssetAssemblyDetailResponse> getAssemblyInfo(QueryCondition condition) {
        List<AssetAssemblyDetailResponse> assemblyDetailResponseList = Lists.newArrayList();
        List<AssetAssemblyResponse> assemblyResponseList = assemblyResponseBaseConverter
            .convert(assetDao.getAssemblyInfoById(condition.getPrimaryKey()), AssetAssemblyResponse.class);
        if (CollectionUtils.isNotEmpty(assemblyResponseList)) {
            Map<String, List<AssetAssemblyResponse>> map = assemblyResponseList.stream()
                .collect(Collectors.groupingBy(AssetAssemblyResponse::getType));
            for (Map.Entry<String, List<AssetAssemblyResponse>> entryAssembly : map.entrySet()) {
                AssetAssemblyDetailResponse detailResponse = new AssetAssemblyDetailResponse();
                detailResponse.setAssemblyResponseList(entryAssembly.getValue());
                detailResponse.setCount(entryAssembly.getValue().size());
                detailResponse.setType(entryAssembly.getKey());
                detailResponse
                    .setTypeName(AssemblyTypeEnum.getNameByCode(entryAssembly.getKey()));
                assemblyDetailResponseList.add(detailResponse);
            }
        }
        return assemblyDetailResponseList;
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
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(loginUser.getAreaIdsOfCurrentUser()));
        }
        Map<String, String> vulCountMaps = new HashMap<>();
        if (query.getQueryVulCount() != null && query.getQueryVulCount()) {
            List<IdCount> vulCountList = assetDao.queryAssetVulCount(query);
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

        Map<String, String> patchCountMaps = null;
        if (query.getQueryPatchCount() != null && query.getQueryPatchCount()) {
            List<IdCount> patchCountList = assetDao.queryAssetPatchCount(query);
            if (CollectionUtils.isEmpty(patchCountList)) {
                return new ArrayList<>();
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
            alarmCountMaps = alarmCountList.stream().collect(Collectors.toMap(IdCount::getId, IdCount::getCount));
            String[] ids = new String[alarmCountMaps.size()];
            query.setIds(alarmCountMaps.keySet().toArray(ids));
        }
        // 查询资产信息
        List<Asset> assetList = assetDao.findListAsset(query);

        if (CollectionUtils.isNotEmpty(assetList)) {
            assetList.stream().forEach(a -> {
                try {
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                        a.getAreaId());
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    a.setAreaName(sysArea.getFullName());
                } catch (Exception e) {
                    logger.warn("获取资产区域名称失败", e);
                }
            });
        }
        List<AssetResponse> objects = responseConverter.convert(assetList, AssetResponse.class);

        for (AssetResponse object : objects) {
            object.setDecryptId(object.getStringId());
            object.setOriginStatus(assetDao.queryOriginStatus(object.getStringId()));
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
        }
        return objects;

    }

    public Integer findCountAsset(AssetQuery query) throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return 0;
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(loginUser.getAreaIdsOfCurrentUser()));
        }
        return assetDao.findCount(query);
    }

    /**
     * 获取流程引擎数据，并且返回map对象
     *
     * @return
     */
    public Map<String, WaitingTaskReponse> getAllHardWaitingTask(String definitionKeyType) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return new HashMap<>();
        }
        // 1.获取当前用户的所有代办任务
        ActivityWaitingQuery activityWaitingQuery = new ActivityWaitingQuery();
        activityWaitingQuery.setUser(loginUser.getStringId());
        activityWaitingQuery.setProcessDefinitionKey(definitionKeyType);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = null;
        try {
            actionResponse = activityClient.queryAllWaitingTask(activityWaitingQuery);
            if (actionResponse != null
                && RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                List<WaitingTaskReponse> waitingTaskReponses = actionResponse.getBody();
                return waitingTaskReponses.stream()
                    .filter(waitingTaskReponse -> StringUtils.isNotBlank(waitingTaskReponse.getBusinessId())).collect(
                        Collectors.toMap(WaitingTaskReponse::getBusinessId, Function.identity(), (key1, key2) -> key2));
            }
            throw new BusinessException("获取工作流异常");
        } catch (Exception e) {
            LogUtils.info(logger, "获取当前用户的所有代办任务失败" + " {}", definitionKeyType);
            throw new BusinessException("获取工作流异常");
        }
    }

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        // 是否未知资产列表查询
        if (query.getUnknownAssets()) {
            if (Objects.isNull(query.getAssetSource())) {
                query.setAssetSource(AssetSourceEnum.AGENCY_REPORT.getCode());
            }
            query.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        }
        // 是否资产组关联资产查询
        if (null != query.getAssociateGroup()) {
            ParamterExceptionUtils.isBlank(query.getGroupId(), "资产组ID不能为空");
            List<String> associateAssetIdList = assetGroupRelationDao.findAssetIdByAssetGroupId(query.getGroupId());
            if (CollectionUtils.isNotEmpty(associateAssetIdList)) {
                query.setExistAssociateIds(associateAssetIdList);
            }
        }

        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(loginUser.getAreaIdsOfCurrentUser()));
        }

        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("asset");
        dealProcess(query, processMap);

        int count = 0;

        // 来源为资产概览-异常资产统计
        // count1 异常资产数量
        Integer count1 = setCountForStatisticsOfAbnormalAsset(query);
        if (Objects.isNull(count1)) {
            count = this.findCountAsset(query);
        } else {
            count = count1;
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

    // 统计异常资产数量
    private Integer setCountForStatisticsOfAbnormalAsset(AssetQuery query) {
        Integer count = null;
        // 查询漏洞资产数量
        if (query.getQueryVulCount() != null && query.getQueryVulCount()) {
            count = assetDao.queryAllAssetVulCount(query);
        }

        // 查询补丁资产数量
        if (query.getQueryPatchCount() != null && query.getQueryPatchCount()) {
            count = assetDao.queryAllAssetPatchCount(query);
        }
        // 查询告警资产数量
        if (query.getQueryAlarmCount() != null && query.getQueryAlarmCount()) {
            count = assetDao.findAlarmAssetCount(query);
        }
        return count;
    }

    @Override
    public ActionResponse dealAssetOperation(AssetLockRequest assetLockRequest) throws Exception {
        return jugeAssetStatus(Integer.valueOf(assetLockRequest.getAssetId()), assetLockRequest.getOperation());
        /* AssetLock assetLockConvert=new AssetLock();
         * assetLockConvert.setAssetId(Integer.valueOf(assetLockRequest.getAssetId()));
         * assetLockConvert.setUserId(LoginUserUtil.getLoginUser().getId()); AssetLock
         * assetLock=assetLockDao.getByAssetId(Integer.valueOf(assetLockRequest.getAssetId())); if(assetLock==null){
         * assetLockDao.insert(assetLockConvert); return
         * jugeAssetStatus(Integer.valueOf(assetLockRequest.getAssetId()),assetLockRequest.getOperation()); }else
         * if(LoginUserUtil.getLoginUser().getId().equals(assetLock.getUserId())){ return
         * jugeAssetStatus(Integer.valueOf(assetLockRequest.getAssetId()),assetLockRequest.getOperation()); }else{
         * return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION,"该任务已被他人认领"); } */
    }

    private ActionResponse jugeAssetStatus(Integer assetId, Integer operation) throws Exception {

        Asset asset = assetDao.getById(String.valueOf(assetId));
        switch (operation) {
            case 1:
                if (AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                    return ActionResponse.success();
                }
                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, getAssetStatusMsg(asset.getAssetStatus()));
            case 2:
                if (AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())
                    || AssetStatusEnum.NOT_REGISTER.getCode().equals(asset.getAssetStatus())
                    || AssetStatusEnum.RETIRE.getCode().equals(asset.getAssetStatus())) {
                    return ActionResponse.success();
                }

                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, getAssetStatusMsg(asset.getAssetStatus()));
            case 3:
                if (AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())) {
                    return ActionResponse.success();
                }

                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, getAssetStatusMsg(asset.getAssetStatus()));
            case 4:
                if (AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                    return ActionResponse.success();
                }

                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION, getAssetStatusMsg(asset.getAssetStatus()));
            default:
                return ActionResponse.success();
        }
    }

    private String getAssetStatusMsg(Integer code) {
        String message = "资产已处于%s，无法重复提交！";
        AssetStatusEnum assetByCode = AssetStatusEnum.getAssetByCode(code);
        return String.format(message, assetByCode.getMsg());
    }

    private List<AssetEntity> getAssetEntities(ProcessTemplateRequest processTemplateRequest) throws Exception {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setTemplateList(processTemplateRequest.getIds());
        assetQuery.setPageSize(Constants.ALL_PAGE);
        assetQuery.setAreaIds(ArrayTypeUtil.objectArrayToStringArray(loginUser.getAreaIdsOfCurrentUser().toArray()));
        List<AssetResponse> list = this.findPageAsset(assetQuery).getItems();
        return assetEntityConvert.convert(list, AssetEntity.class);
    }

    /**
     * 处理待办任务 ,
     */
    public void dealProcess(AssetQuery query, Map<String, WaitingTaskReponse> processMap) {
        // 只要是工作台进来的才去查询他的待办事项
        if (MapUtils.isNotEmpty(processMap)) {
            // 待办资产id
            List<String> waitRegistIds = new ArrayList<>();
            Set<String> activitiIds = null;
            // 如果是待登记,设置查询的ids-in为过滤后的信息
            if (CollectionUtils.isNotEmpty(query.getAssetStatusList())
                && AssetStatusEnum.WAIT_REGISTER.getCode().equals(query.getAssetStatusList().get(0))) {
                // 查询所有待登记资产
                List<Integer> currentStatusAssetIds = assetDao.queryIdsByAssetStatus(
                    AssetStatusEnum.WAIT_REGISTER.getCode(), LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
                if (CollectionUtils.isNotEmpty(currentStatusAssetIds)) {
                    waitRegistIds = currentStatusAssetIds.stream().map(DataTypeUtils::integerToString)
                        .collect(Collectors.toList());
                }
                activitiIds = new HashSet<>(waitRegistIds);
            } else {
                activitiIds = processMap.keySet();
            }
            if (CollectionUtils.isNotEmpty(query.getAssetStatusList()) && CollectionUtils.isNotEmpty(activitiIds)
                && query.getEnterControl()) {
                query.setIds(activitiIds.toArray(new String[activitiIds.size()]));
            }
        }
    }

    /**
     * 通联设置的资产查询 与普通资产查询类似， 不同点在于品类型号显示二级品类， 只查已入网，网络设备和计算设备的资产,且会去掉通联表中已存在的资产
     */
    @Override
    public PageResult<AssetResponse> findUnconnectedAsset(AssetQuery query) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return new PageResult<>();
        }
        if (query.getAreaIds() == null || query.getAreaIds().length == 0) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(loginUser.getAreaIdsOfCurrentUser()));
        }
        // 只查已入网资产
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);

        if (query.getCategoryModels() == null || query.getCategoryModels().length == 0) {
            // 查询资产的类型
            Asset asset = assetDao.getByAssetId(query.getPrimaryKey());
            if (AssetCategoryEnum.COMPUTER.getCode().equals(asset.getCategoryModel())) {
                query.setCategoryModels(new Integer[] { AssetCategoryEnum.NETWORK.getCode() });
            } else if (AssetCategoryEnum.NETWORK.getCode().equals(asset.getCategoryModel())) {
                query.setCategoryModels(
                    new Integer[] { AssetCategoryEnum.COMPUTER.getCode(), AssetCategoryEnum.NETWORK.getCode() });
            }
        }

        // 进行查询
        Integer count = assetLinkRelationDao.findUnconnectedCount(query);
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
        } else {
            List<AssetResponse> assetResponseList = responseConverter
                .convert(assetLinkRelationDao.findListUnconnectedAsset(query), AssetResponse.class);
            assetResponseList.stream().forEach(assetLinkedCount -> {
                String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                    assetLinkedCount.getAreaId());
                try {
                    SysArea sysArea = redisUtil.getObject(newAreaKey, SysArea.class);
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

    @Override
    @Transactional
    public Integer batchSave(List<Asset> assetList) throws Exception {
        int i = 0;
        for (; i < assetList.size(); i++) {
            assetDao.insert(assetList.get(i));
        }
        return i + 1;
    }

    private boolean checkSupplier(String supplier) {
        HashMap<String, String> map = new HashMap<>();
        map.put("supplier", supplier);
        map.put("type", "h");
        map.put("isStorage", "1");
        map.put("status", "1");
        return assetHardSoftLibDao.countByWhere(map) > 0;
    }

    private boolean checkName(String supplier, String productName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("supplier", supplier);
        map.put("type", "h");
        map.put("status", "1");
        map.put("isStorage", "1");
        map.put("productName", productName);
        return assetHardSoftLibDao.countByWhere1(map) > 0;
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
    public List<EnumCountResponse> countManufacturer() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        int maxNum = 4;
        List<String> areaIds = loginUser.getAreaIdsOfCurrentUser();
        // 不统计已退役资产
        List<Integer> status = Arrays.asList(6, 10);
        // update by zhangbing 对于空的厂商和产品确认需要统计，统计的到其他
        List<Map<String, Object>> list = assetDao.countManufacturer(areaIds, status);
        return CountTypeUtil.getEnumCountResponse(maxNum, list);
    }

    @Override
    public List<EnumCountResponse> countStatus() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        List<String> ids = loginUser.getAreaIdsOfCurrentUser();
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
    public List<EnumCountResponse> countCategory() {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return Lists.newArrayList();
        }
        List<String> areaIdsOfCurrentUser = loginUser.getAreaIdsOfCurrentUser();

        // 已入网+变更中+退役待审批+退役审批未通过的状态资产类型分
        List<Integer> status = StatusEnumUtil.getAssetTypeStatus();
        /**
         *
         * 统计品类型号 Map中的数据 key--品类型号 value--总数
         */
        List<Map<String, Object>> categoryModelCount = assetDao.countCategoryModel(areaIdsOfCurrentUser, status);

        List<EnumCountResponse> listResponse = new LinkedList<>();
        for (AssetCategoryEnum categoryEnum : AssetCategoryEnum.values()) {
            EnumCountResponse enumCountResponse = new EnumCountResponse(categoryEnum.getName(),
                categoryEnum.getCode() + "", 0);
            // 查找品类型号对应的总数并设置到EnumCountResponse对象中
            for (Map<String, Object> map : categoryModelCount) {
                Integer code = (Integer) map.get("key");
                if (categoryEnum.getCode().equals(code)) {
                    Long n = (Long) map.get("value");
                    enumCountResponse.setNumber(n);
                    break;
                }
            }

            listResponse.add(enumCountResponse);
        }
        return listResponse;

    }

    @Override
    public List<AssetResponse> queryAssetByIds(Integer[] ids) {
        List<Asset> asset = assetDao.queryAssetByIds(ids);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        return objects;
    }

    @Override
    public AssetOuterResponse getByAssetId(QueryCondition condition) throws Exception {
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        Asset asset = assetDao.getByAssetId(condition.getPrimaryKey());
        // 获取主表信息
        AssetResponse assetResponse = responseConverter.convert(asset, AssetResponse.class);
        assetResponse.setDecryptId(Objects.toString(asset.getId()));
        assetResponse.setDecryptInstallTemplateId(asset.getInstallTemplateId());
        assetOuterResponse.setAsset(assetResponse);
        // 获取区域
        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class, asset.getAreaId());
        SysArea sysArea = redisUtil.getObject(key, SysArea.class);
        assetResponse.setAreaName(Optional.ofNullable(sysArea).map(SysArea::getFullName).orElse(null));
        // 设置品类型号名
        assetResponse.setCategoryModelName(AssetCategoryEnum.getNameByCode(assetResponse.getCategoryModel()));
        // 设置操作系统
        if (asset.getOperationSystem() != null) {
            assetResponse.setOperationSystem(asset.getOperationSystem().toString());
        }
        // 获取资产组
        List<AssetGroupResponse> assetGroupResponses = assetGroupResponseBaseConverter
            .convert(assetGroupRelationDao.queryByAssetId(asset.getId()), AssetGroupResponse.class);
        if (CollectionUtils.isNotEmpty(assetGroupResponses)) {
            assetResponse.setAssetGroups(assetGroupResponses);
            StringBuilder assetGroup = new StringBuilder();
            assetGroupResponses.forEach(x -> assetGroup.append(x.getName() + ","));
            assetResponse.setAssetGroup(assetGroup.substring(0, assetGroup.length() - 1));
        }
        // 查询ip
        HashMap<String, Object> param = new HashMap<>();
        param.put("status", "1");
        param.put("assetId", condition.getPrimaryKey());
        List<AssetIpRelation> assetIpRelations = assetIpRelationDao.getByWhere(param);
        assetResponse.setIp(ipResponseConverter.convert(assetIpRelations, AssetIpRelationResponse.class));
        // 查询mac
        List<AssetMacRelation> assetMacRelations = assetMacRelationDao.getByWhere(param);
        assetResponse.setMac(macResponseConverter.convert(assetMacRelations, AssetMacRelationResponse.class));
        // 查询从属业务
        List<AssetBusinessResponse> dependentBusiness = assetBusinessRelationDao
            .getBusinessInfoByAssetId(condition.getPrimaryKey());
        assetResponse.setDependentBusiness(dependentBusiness);

        if (Objects.equals(asset.getCategoryModel(), AssetCategoryEnum.NETWORK.getCode())) {
            List<AssetNetworkEquipment> assetNetworkEquipments = assetNetworkEquipmentDao.getByWhere(param);
            if (CollectionUtils.isNotEmpty(assetNetworkEquipments)) {
                AssetNetworkEquipmentResponse assetNetworkEquipmentResponse = networkResponseConverter
                    .convert(assetNetworkEquipments.get(0), AssetNetworkEquipmentResponse.class);
                assetOuterResponse.setAssetNetworkEquipment(assetNetworkEquipmentResponse);
            }
        }
        if (Objects.equals(asset.getCategoryModel(), AssetCategoryEnum.STORAGE.getCode())) {
            List<AssetStorageMedium> assetStorageMedias = assetStorageMediumDao.getByWhere(param);
            if (CollectionUtils.isNotEmpty(assetStorageMedias)) {
                AssetStorageMediumResponse assetStorageMediumResponse = storageResponseConverter
                    .convert(assetStorageMedias.get(0), AssetStorageMediumResponse.class);
                assetOuterResponse.setAssetStorageMedium(assetStorageMediumResponse);
            }

        }
        /* // 查询组件 List<AssetAssemblyRequest> assetAssemblys =
         * assetAssemblyDao.findAssemblyByAssetId(condition.getPrimaryKey(), ""); List<AssetAssemblyResponse>
         * assemblyResponseList = BeanConvert.convert(assetAssemblys, AssetAssemblyResponse.class);
         * assetOuterResponse.setAssemblyResponseList(assemblyResponseList); */
        // 查询代办
        ActivityWaitingQuery activityWaitingQuery = new ActivityWaitingQuery();
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        if (loginUser == null) {
            return assetOuterResponse;
        }
        activityWaitingQuery.setUser(loginUser.getStringId());
        activityWaitingQuery.setProcessDefinitionKey("asset");

        ActionResponse<List<WaitingTaskReponse>> actionResponse = activityClient
            .queryAllWaitingTask(activityWaitingQuery);
        if (actionResponse != null
            && RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            List<WaitingTaskReponse> waitingTaskReponses = actionResponse.getBody();
            for (WaitingTaskReponse waitingTaskReponse : waitingTaskReponses) {
                if (Objects.equals(assetResponse.getStringId(), waitingTaskReponse.getBusinessId())) {
                    assetResponse.setWaitingTaskReponse(waitingTaskReponse);
                    break;
                }
            }
        }
        return assetOuterResponse;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResponse changeAsset(AssetOuterRequest assetOuterRequest) throws Exception {
        // 校验资产合规性，如ip、mac不能重复等
        checkAssetCompliance(assetOuterRequest);
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        synchronized (lock) {
            // 校验资产状态
            if (!assetDao.getByAssetId(assetOuterRequest.getAsset().getId()).getAssetStatus()
                .equals(assetOuterRequest.getAsset().getAssetStatus())) {
                throw new RequestParamValidateException("当前资产状态已被修改，请勿重复提交");
            }
            if (AssetStatusEnum.NET_IN.getCode().equals(assetOuterRequest.getAsset().getAssetStatus())) {
                // 处理资产变更流程
                dealChangeProcess(assetOuterRequest);
            } else {
                // 处理资产再次登记流程
                dealRegisterProcess(assetOuterRequest);
            }
        }
        // 更新资产基础信息及各种关联信息
        Integer assetCount = updateAssetInfo(assetOuterRequest);
        ParamterExceptionUtils.isTrue(assetCount != null && assetCount > 0, "信息入库失败");

        // 根据前端判断启动漏扫,排除走基准配置的已入网资产
        if (!(EnumUtil.equals(assetOuterRequest.getAsset().getAssetStatus(), AssetStatusEnum.NET_IN)
              && EnumUtil.equals(assetOuterRequest.getAsset().getCategoryModelType(), AssetCategoryEnum.COMPUTER))
            && assetOuterRequest.getNeedScan()) {
            logger.info("启动漏扫");
            // 漏洞扫描
            ActionResponse scan = baseLineClient.scan(assetOuterRequest.getAsset().getId());
            if (null == scan || !RespBasicCode.SUCCESS.getResultCode().equals(scan.getHead().getCode())) {
                BusinessExceptionUtils.isTrue(false, "调用漏洞模块出错");
            }
        }

        // 记录资产操作流程
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(assetOuterRequest.getAsset().getId());
        assetOperationRecord.setOriginStatus(assetOuterRequest.getAsset().getAssetStatus());
        if (EnumUtil.equals(assetOuterRequest.getAsset().getAssetStatus(), AssetStatusEnum.NET_IN)
            && EnumUtil.equals(assetOuterRequest.getAsset().getCategoryModelType(), AssetCategoryEnum.COMPUTER)) {
            assetOperationRecord.setTargetStatus(AssetStatusEnum.IN_CHANGE.getCode());
        } else if (!EnumUtil.equals(assetOuterRequest.getAsset().getCategoryModelType(), AssetCategoryEnum.COMPUTER)) {
            assetOperationRecord.setTargetStatus(AssetStatusEnum.NET_IN.getCode());
        } else {
            assetOperationRecord.setTargetStatus(AssetStatusEnum.CORRECTING.getCode());
        }
        // 1需要漏扫，0否
        assetOperationRecord.setNeedVulScan(assetOuterRequest.getNeedScan() ? 1 : 0);
        assetOperationRecord.setContent(AssetFlowEnum.CHANGE.getNextMsg());
        assetOperationRecord.setOperateUserId(loginUser.getId());
        assetOperationRecord.setCreateUser(loginUser.getId());
        assetOperationRecord.setOperateUserName(loginUser.getName());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setStatus(1);
        assetOperationRecordDao.insert(assetOperationRecord);

        return ActionResponse.success();
    }

    private void dealRegisterProcess(AssetOuterRequest assetOuterRequest) throws Exception {
        Asset asset = requestConverter.convert(assetOuterRequest.getAsset(), Asset.class);
        ActionResponse response;
        Integer status;
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        ManualStartActivityRequest activityRequest = assetOuterRequest.getManualStartActivityRequest();
        if (!Objects.isNull(activityRequest)) {
            asset.setAssetStatus(AssetStatusEnum.CORRECTING.getCode());
            status = AssetStatusEnum.CORRECTING.getCode();
            // 待登记、不予登记、已退回->登记
            activityRequest.setBusinessId(asset.getStringId());
            activityRequest.setAssignee(loginUser.getStringId());
            response = activityClient.manualStartProcess(activityRequest);
            // 如果流程引擎为空,直接返回错误信息
            if (null == response || !RespBasicCode.SUCCESS.getResultCode().equals(response.getHead().getCode())) {
                LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + "流程引擎返回结果：{}",
                    JSON.toJSONString(response));
                BusinessExceptionUtils.isTrue(false, "调用工作流程引擎出错");
            }
        } else {
            // 计算设备、网络设备=>待准入
            if (AssetCategoryEnum.COMPUTER.getCode().equals(asset.getCategoryModelType())
                || AssetCategoryEnum.NETWORK.getCode().equals(asset.getCategoryModelType())) {
                asset.setAssetStatus(AssetStatusEnum.NET_IN_CHECK.getCode());
                status = AssetStatusEnum.NET_IN_CHECK.getCode();
            } else {
                // 安全设备、存储设备、其他设备 =>已入网
                asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
                asset.setFirstEnterNett(System.currentTimeMillis());
                status = AssetStatusEnum.NET_IN.getCode();
            }
        }
        // 更新资产状态
        updateAssetStatus(status, System.currentTimeMillis(), asset.getStringId());
        // 记录操作日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_INSERT.getName(), asset.getId(), asset.getNumber(),
            asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_CHECK));
        LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}", asset.toString());
    }

    private void dealChangeProcess(AssetOuterRequest assetOuterRequest) throws Exception {
        Asset asset = BeanConvert.convertBean(assetOuterRequest.getAsset(), Asset.class);
        String assetId = asset.getStringId();
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        Integer changeStatus = null;
        BusinessPhaseEnum businessPhaseEnum = null;
        // 计算设备变更走基准配置
        if (!Objects.isNull(assetOuterRequest.getManualStartActivityRequest())) {
            changeStatus = AssetStatusEnum.IN_CHANGE.getCode();
            businessPhaseEnum = BusinessPhaseEnum.WAIT_SETTING;
            String reason = getChangeContent(assetOuterRequest);
            String[] bids = assetOuterRequest.getManualStartActivityRequest().getFormData().get("baselineConfigUserId")
                .toString().split(",");
            StringBuilder sb = new StringBuilder();
            Arrays.stream(bids).forEach(bid -> {
                sb.append(aesEncoder.decode(bid, loginUser.getUsername())).append(",");
            });
            Map formData = assetOuterRequest.getManualStartActivityRequest().getFormData();
            formData.put("baselineConfigUserId", sb.toString());
            List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList = Lists.newArrayList();
            // ------------------对接配置模块------------------start
            BaselineWaitingConfigRequest baselineWaitingConfigRequest = new BaselineWaitingConfigRequest();
            baselineWaitingConfigRequest.setAssetId(DataTypeUtils.stringToInteger(assetId));
            if (Objects.isNull(asset.getInstallType())) {
                baselineWaitingConfigRequest.setCheckType(InstallType.AUTOMATIC.getCode());
            } else {
                baselineWaitingConfigRequest.setCheckType(asset.getInstallType());
            }
            baselineWaitingConfigRequest.setConfigStatus(1);
            baselineWaitingConfigRequest.setCreateUser(loginUser.getId());
            baselineWaitingConfigRequest.setReason(reason);
            baselineWaitingConfigRequest.setSource(2);
            baselineWaitingConfigRequest.setFormData(formData);
            baselineWaitingConfigRequest.setBusinessId(assetId + "&1&" + assetId);
            baselineWaitingConfigRequest
                .setAdvice((String) assetOuterRequest.getManualStartActivityRequest().getFormData().get("memo"));
            baselineWaitingConfigRequestList.add(baselineWaitingConfigRequest);
            ActionResponse actionResponse = baseLineClient.baselineConfig(baselineWaitingConfigRequestList);
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                BusinessExceptionUtils.isTrue(false, "调用配置模块出错");
            }
            // ------------------对接配置模块------------------end
        }
        // 非计算设备变更直接入网
        else {
            businessPhaseEnum = BusinessPhaseEnum.NET_IN;
            changeStatus = AssetStatusEnum.NET_IN.getCode();
        }

        // 阻断入网判断
        if (assetOuterRequest.isNeedEntryForbidden()) {
            new Thread(() -> {
                AssetEntryRequest request = new AssetEntryRequest();
                ActivityHandleRequest handleRequest = new ActivityHandleRequest();
                handleRequest.setId(assetId);
                request.setAssetActivityRequests(Arrays.asList(handleRequest));
                request.setUpdateStatus(String.valueOf(AssetEnterStatusEnum.NO_ENTER.getCode()));
                request.setEntrySource(AssetEntrySourceEnum.ASSET_CHANGE);
                entryService.updateEntryStatus(request);
            }).start();
        }
        // 更新资产状态
        updateAssetStatus(changeStatus, System.currentTimeMillis(), assetId);
        // 记录操作日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MODIFY.getName(), asset.getId(), asset.getNumber(),
            asset, BusinessModuleEnum.HARD_ASSET, businessPhaseEnum));
        LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
    }

    private void checkAssetCompliance(AssetOuterRequest assetOuterRequest) {

        // 资产编号不能重复
        ParamterExceptionUtils.isTrue(
            !checkNumber(assetOuterRequest.getAsset().getId(), assetOuterRequest.getAsset().getNumber()), "资产编号重复");
        // 同一资产的IP不能重复
        if (CollectionUtils.isNotEmpty(assetOuterRequest.getIpRelationRequests())
            && assetOuterRequest.getIpRelationRequests().size() > 1) {
            HashSet ipset = new HashSet(assetOuterRequest.getIpRelationRequests().stream()
                .map(AssetIpRelationRequest::getIp).collect(Collectors.toList()));
            ParamterExceptionUtils.isTrue(assetOuterRequest.getIpRelationRequests().size() == ipset.size(),
                "同一资产ip不能重复");
        }
        // mac不能重复
        if (CollectionUtils.isNotEmpty(assetOuterRequest.getMacRelationRequests())) {
            List<String> macs = assetOuterRequest.getMacRelationRequests().stream().map(AssetMacRelationRequest::getMac)
                .collect(Collectors.toList());
            ParamterExceptionUtils.isTrue(assetOuterRequest.getMacRelationRequests().size() == new HashSet(macs).size(),
                "mac不能重复");
            Integer mcount = assetMacRelationDao.checkRepeat(macs, assetOuterRequest.getAsset().getId());
            ParamterExceptionUtils.isTrue(mcount <= 0, "mac不能重复");
        }
    }

    private Integer updateAssetInfo(AssetOuterRequest assetOuterRequest) {
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        Asset asset = requestConverter.convert(assetOuterRequest.getAsset(), Asset.class);
        Integer assetCount = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {

                    List<AssetGroupRequest> assetGroup = assetOuterRequest.getAsset().getAssetGroups();
                    // 处理资产组关系
                    asset.setAssetGroup(dealAssetGroup(assetOuterRequest.getAsset().getId(), assetGroup));
                    asset.setModifyUser(loginUser.getId());
                    asset.setGmtModified(System.currentTimeMillis());
                    if (asset.getOperationSystem() != null && StringUtils.isBlank(asset.getOperationSystemName())) {
                        // 让前端传操作系统和操作系统名称
                        // HashMap<String, Object> param = new HashMap<>();
                        // param.put("status", "1");
                        // param.put("businessId", asset.getOperationSystem());
                        // List<AssetCpeFilter> cpeFilters = assetCpeFilterDao.getByWhere(param);
                        // asset.setOperationSystemName(cpeFilters.size() > 0 ? cpeFilters.get(0).getProductName() :
                        // null);
                    }
                    // 入网审批未通过、不予登记、代理上报、已退役->登记
                    if (!EnumUtil.equals(asset.getAssetStatus(), AssetStatusEnum.NET_IN)) {
                        if (StringUtils.isNotBlank(asset.getInstallTemplateId())) {
                            dealInstallTemplete(asset.getInstallTemplateId(), assetOuterRequest.getAsset().getId());
                        }
                    }
                    // 1. 更新资产主表
                    asset.setId(DataTypeUtils.stringToInteger(assetOuterRequest.getAsset().getId()));
                    if (StringUtils.isNotBlank(assetOuterRequest.getAsset().getBusinessId())) {
                        asset.setBusinessId(Long.parseLong(assetOuterRequest.getAsset().getBusinessId()));
                    }
                    // 更新 装机 ,基准 关联 时间
                    Asset byId = assetDao.getById(asset.getStringId());
                    if (StringUtils.isNotBlank(asset.getInstallTemplateId())
                        && !asset.getInstallTemplateId().equals(byId.getInstallTemplateId())) {
                        asset.setInstallTemplateCorrelationGmt(System.currentTimeMillis());
                    }
                    if (StringUtils.isNotBlank(asset.getBaselineTemplateId())
                        && !asset.getBaselineTemplateId().equals(byId.getBaselineTemplateId())) {
                        asset.setBaselineTemplateCorrelationGmt(System.currentTimeMillis());
                    }
                    // 处理自定义字段
                    List<AssetCustomizeRequest> assetCustomizeRequests = assetOuterRequest.getAssetCustomizeRequests();
                    if (CollectionUtils.isNotEmpty(assetCustomizeRequests)) {
                        asset.setCustomField(JsonUtil.ListToJson(assetCustomizeRequests));
                    }
                    // 处理业务
                    if (CollectionUtils.isNotEmpty(assetOuterRequest.getAsetBusinessRelationRequests())) {
                        dealBusiness(asset.getStringId(), assetOuterRequest.getAsetBusinessRelationRequests());
                    }
                    int count = assetDao.changeAsset(asset);
                    // 处理ip
                    dealIp(assetOuterRequest.getAsset().getId(), assetOuterRequest.getIpRelationRequests(),
                        asset.getCategoryModelType());
                    // 处理mac
                    dealMac(assetOuterRequest.getAsset().getId(), assetOuterRequest.getMacRelationRequests());
                    // 资产变更-软件处理
                    if (!Objects.isNull(assetOuterRequest.getSoftwareReportRequest())) {
                        // 处理软件
                        dealSoft(assetOuterRequest.getAsset().getId(), assetOuterRequest.getSoftwareReportRequest());
                    }
                    // 处理组件
                    dealAssembly(assetOuterRequest.getAsset().getId(), assetOuterRequest.getAssemblyRequestList());

                    // 2. 更新网络设备信息
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();
                    if (!Objects.isNull(networkEquipment)) {
                        AssetNetworkEquipment anp = BeanConvert.convertBean(networkEquipment,
                            AssetNetworkEquipment.class);
                        anp.setModifyUser(loginUser.getId());
                        anp.setGmtModified(System.currentTimeMillis());
                        anp.setAssetId(asset.getStringId());
                        assetNetworkEquipmentDao.update(anp);
                    }

                    // 4. 更新存储介质信息
                    AssetStorageMediumRequest storageMedium = assetOuterRequest.getAssetStorageMedium();
                    if (!Objects.isNull(storageMedium)) {
                        AssetStorageMedium assetStorageMedium = BeanConvert.convertBean(storageMedium,
                            AssetStorageMedium.class);
                        assetStorageMedium.setAssetId(assetOuterRequest.getAsset().getId());
                        assetStorageMedium.setGmtCreate(System.currentTimeMillis());
                        assetStorageMedium.setModifyUser(loginUser.getId());
                        assetStorageMedium.setGmtModified(System.currentTimeMillis());
                        assetStorageMediumDao.update(assetStorageMedium);
                    }
                    return 1;
                } catch (Exception e) {
                    logger.info("资产变更失败:", e);
                    transactionStatus.setRollbackOnly();
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("网口数只能增加不能减少", e.getMessage()), "网口数只能增加不能减少");
                    throw new BusinessException("资产变更失败");
                }
            }
        });
        return assetCount;
    }

    void dealBusiness(String assetId, List<AssetBusinessRelationRequest> relationRequests) {
        List<String> preRelation = assetBusinessRelationDao.getBusinessInfoByAssetId(assetId).stream()
            .map(AssetBusinessResponse::getUniqueId).collect(Collectors.toList());
        List<String> filterRelaton = new ArrayList<>();
        List<AssetBusinessRelationRequest> updateRelation = new ArrayList<>();
        List<AssetBusinessRelation> insertRelation = relationRequests.stream().filter(v -> {
            AssetBusinessRelation businessRelation = assetBusinessRelationDao.getByUniqueIdAndAssetId(v.getUniqueId(),
                Integer.valueOf(assetId));
            if (Objects.isNull(businessRelation)) {
                v.setGmtCreate(System.currentTimeMillis());
                v.setCreateUser(LoginUserUtil.getLoginUser().getId());
                return true;
            } else if (!v.getBusinessInfluence().equals(businessRelation.getBusinessInfluence())) {
                v.setGmtModified(System.currentTimeMillis());
                v.setModifyUser(LoginUserUtil.getLoginUser().getId());
                updateRelation.add(v);
            }
            filterRelaton.add(businessRelation.getUniqueId());
            return false;
        }).map(t -> BeanConvert.convertBean(t, AssetBusinessRelation.class)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(filterRelaton)) {
            preRelation.removeAll(filterRelaton);
        }
        // 删除以前关联的业务
        if (CollectionUtils.isNotEmpty(preRelation)) {
            assetBusinessRelationDao.deleteByAssetIdAndUniqueId(preRelation, assetId);
        }
        // 更新关联过的业务
        if (CollectionUtils.isNotEmpty(updateRelation)) {
            assetBusinessRelationDao.updateBatchInfluenceByAssetId(updateRelation, assetId);
        }
        // 插入新业务
        if (CollectionUtils.isNotEmpty(insertRelation)) {
            assetBusinessRelationDao.insertBatchRelation(insertRelation);
        }
    }

    /**
     * 获取该资产变更的信息
     *
     * @param assetOuterRequest
     * @return
     */
    public String getChangeContent(AssetOuterRequest assetOuterRequest) {
        StringBuilder sb = new StringBuilder();
        StringBuilder add = new StringBuilder();
        StringBuilder update = new StringBuilder();
        StringBuilder delete = new StringBuilder();

        AssetRequest assetRequest = assetOuterRequest.getAsset();
        String assetId = assetOuterRequest.getAsset().getId();
        List<RollBack> rollbackInfo = new ArrayList<>();
        // 计算设备才有操作系统
        if (AssetCategoryEnum.COMPUTER.getCode().equals(assetRequest.getCategoryModel())) {
            Asset asset = assetDao.getByAssetId(assetId);
            String oldOs = asset.getOperationSystemName();
            String newOs = assetOuterRequest.getAsset().getOperationSystemName();
            if (!StringUtils.equals(oldOs, newOs)) {
                update.append("$更改基础信息:").append("操作系统").append(newOs);
                RollBack operationSystem = new RollBack("operation_system", String.valueOf(asset.getOperationSystem()),
                    "asset", OperationTypeEnum.UPDATE.getCode());
                RollBack operationSystemName = new RollBack("operation_system_name", oldOs, "asset",
                    OperationTypeEnum.UPDATE.getCode());
                rollbackInfo.add(operationSystem);
                rollbackInfo.add(operationSystemName);
            }
        }

        // 变更之前的硬盘
        List<AssetAssemblyRequest> oldDisks = assetAssemblyDao.findAssemblyByAssetId(assetId, "DISK");

        if (CollectionUtils.isNotEmpty(assetOuterRequest.getAssemblyRequestList())) {
            // 变更后的硬盘
            List<AssetAssemblyRequest> newDisks = assetOuterRequest.getAssemblyRequestList().stream()
                .filter(a -> "DISK".equals(a.getType())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(oldDisks) && CollectionUtils.isNotEmpty(newDisks)) {
                newDisks.stream().forEach(disk -> {
                    add.append("$新增组件:").append("硬盘").append(disk.getProductName());
                    RollBack rollBack = new RollBack("business_id", disk.getBusinessId(), "asset_assembly",
                        OperationTypeEnum.ADD.getCode(), disk.getProductName());
                    rollbackInfo.add(rollBack);
                });
            } else {
                List<String> oldDiskBusinessIds = oldDisks.stream().map(AssetAssemblyRequest::getBusinessId)
                    .collect(Collectors.toList());
                // Map<String, Integer> map = oldDisks.stream()
                // .collect(Collectors.toMap(AssetAssemblyRequest::getBusinessId, AssetAssemblyRequest::getAmount));
                newDisks.stream().forEach(disk -> {
                    if (oldDiskBusinessIds.contains(disk.getBusinessId())) {
                        // if (!disk.getAmount().equals(map.get(disk.getBusinessId()))) {
                        // update.append("$更改组件:").append("硬盘").append(disk.getProductName()).append("数量")
                        // .append(disk.getAmount());
                        // }
                        oldDiskBusinessIds.removeIf(a -> a.contains(disk.getBusinessId()));
                    } else {
                        add.append("$新增组件:").append("硬盘").append(disk.getProductName());
                        RollBack rollBack = new RollBack("business_id", disk.getBusinessId(), "asset_assembly",
                            OperationTypeEnum.ADD.getCode(), disk.getProductName());
                        rollbackInfo.add(rollBack);
                    }
                });
                if (CollectionUtils.isNotEmpty(oldDiskBusinessIds)) {
                    oldDiskBusinessIds.stream().forEach(os -> {
                        delete.append("$删除组件:").append("硬盘")
                            .append(oldDisks.stream().filter(v -> os.equals(v.getBusinessId()))
                                .map(AssetAssemblyRequest::getProductName).findFirst().get());
                        AssetAssemblyRequest assemblyRequest = oldDisks.stream()
                            .filter(v -> v.getBusinessId().equals(os)).toArray(AssetAssemblyRequest[]::new)[0];
                        RollBack rollBack = new RollBack("business_id", assemblyRequest.getBusinessId(),
                            "asset_assembly", OperationTypeEnum.DELETE.getCode(), assemblyRequest.getProductName());
                        rollbackInfo.add(rollBack);
                    });
                }
            }
        } else {
            if (CollectionUtils.isNotEmpty(oldDisks)) {
                oldDisks.stream().forEach(disk -> {
                    sb.append("$删除组件:").append("硬盘").append(disk.getProductName());
                    RollBack rollBack = new RollBack("business_id", disk.getBusinessId(), "asset_assembly",
                        OperationTypeEnum.DELETE.getCode(), disk.getProductName());
                    rollbackInfo.add(rollBack);
                });

            }
        }
        if (AssetCategoryEnum.COMPUTER.getCode().equals(assetOuterRequest.getAsset().getCategoryModel())) {
            QueryCondition queryCondition = new QueryCondition();
            queryCondition.setPrimaryKey(assetId);
            // 变更前的软件
            List<AssetSoftwareInstallResponse> assetSoftwareInstallResponseList = assetSoftwareRelationDao
                .queryInstalledList(queryCondition);

            // 变更后的软件
            List<Long> newSoftIds = assetOuterRequest.getSoftwareReportRequest().getSoftId();
            // 判断变更后软件存不存在
            if (CollectionUtils.isNotEmpty(newSoftIds)) {
                // 变更前没关联过软件
                if (CollectionUtils.isEmpty(assetSoftwareInstallResponseList)) {
                    newSoftIds.stream().forEach(ns -> {
                        AssetHardSoftLib hardSoftLib = assetHardSoftLibDao.getByBusinessId(ns.toString());
                        add.append("$新增软件:").append(hardSoftLib.getProductName());
                        RollBack rollBack = new RollBack("software_id", ns.toString(), "asset_software_relation",
                            OperationTypeEnum.ADD.getCode(), hardSoftLib.getProductName());
                        rollbackInfo.add(rollBack);
                    });
                } else {
                    // 变更前的软件id
                    List<String> oldSoftIds = assetSoftwareInstallResponseList.stream()
                        .map(AssetSoftwareInstallResponse::getSoftwareId).collect(Collectors.toList());
                    // 变更前的软件id，软件名称
                    Map<String, String> oldMap = assetSoftwareInstallResponseList.stream().collect(HashMap::new,
                        (m, v) -> m.put(v.getSoftwareId(), v.getProductName()), HashMap::putAll);
                    newSoftIds.stream().forEach(ns -> {
                        if (!oldSoftIds.contains(String.valueOf(ns))) {
                            AssetHardSoftLib hardSoftLib = assetHardSoftLibDao.getByBusinessId(ns.toString());
                            add.append("$新增软件:").append(hardSoftLib.getProductName());
                            RollBack rollBack = new RollBack("software_id", ns.toString(), "asset_software_relation",
                                OperationTypeEnum.ADD.getCode(), hardSoftLib.getProductName());
                            rollbackInfo.add(rollBack);
                        } else {
                            oldSoftIds.removeIf(a -> a.contains(String.valueOf(ns)));
                        }
                    });
                    if (CollectionUtils.isNotEmpty(oldSoftIds)) {
                        oldSoftIds.stream().forEach(os -> {
                            delete.append("$删除软件:").append(oldMap.get(os));
                            RollBack rollBack = new RollBack("software_id", os, "asset_software_relation",
                                OperationTypeEnum.DELETE.getCode(), oldMap.get(os));
                            rollbackInfo.add(rollBack);
                        });
                    }
                }
            } else {
                // 如果变更前存在软件，则软件全部被删除了
                if (CollectionUtils.isNotEmpty(assetSoftwareInstallResponseList)) {
                    assetSoftwareInstallResponseList.stream().forEach(asr -> {
                        delete.append("$删除软件:").append(asr.getProductName());
                        RollBack rollBack = new RollBack("software_id", asr.getSoftwareId(), "asset_software_relation",
                            OperationTypeEnum.DELETE.getCode(), asr.getProductName());
                        rollbackInfo.add(rollBack);
                    });
                }
            }
        }
        // 回滚信息入库
        if (CollectionUtils.isNotEmpty(rollbackInfo)) {
            AssetRollbackRequest rollbackRequest = new AssetRollbackRequest();
            rollbackRequest.setAssetId(assetId);
            rollbackRequest.setCreateUser(LoginUserUtil.getLoginUser().getStringId());
            rollbackRequest.setGmtCreate(System.currentTimeMillis());
            rollbackRequest.setRollBackInfo(rollbackInfo);
            assetDao.insertRollbackInfo(rollbackRequest);
        }
        return add.toString() + "###" + update.toString() + "###" + delete.toString() + sb.toString();
    }

    private void dealInstallTemplete(String newInstallId, String assetId) {
        // 校验装机模板是否被修改
        // 旧的装机模板id
        String installId = assetDao.getInstallTemplateId(assetId);
        if (!StringUtils.equals(newInstallId, installId)) {
            // 删除旧的与资产关联的装机模板中的软件
            assetSoftwareRelationDao.deleteByInstallTemplateId(assetId, installId);
            // 保存新的装机模板中的id
            List<Long> sids = assetInstallTemplateDao.querySoftIds(newInstallId);
            if (CollectionUtils.isNotEmpty(sids)) {
                List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
                sids.stream().forEach(softId -> {
                    AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                    assetSoftwareRelation.setAssetId(assetId);
                    assetSoftwareRelation.setSoftwareId(softId);
                    assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                    assetSoftwareRelationList.add(assetSoftwareRelation);
                });
                assetSoftwareRelationDao.insertBatch(assetSoftwareRelationList);
            }
        }
    }

    private String dealAssetGroup(String id, List<AssetGroupRequest> assetGroup) {
        StringBuilder groupName = new StringBuilder();
        // 1.删除旧的资产组关系
        assetGroupRelationDao.deleteByAssetId(DataTypeUtils.stringToInteger(id));
        // 2.保存现有资产组关系
        if (CollectionUtils.isNotEmpty(assetGroup)) {
            List<AssetGroupRelation> assetGroupRelationList = Lists.newArrayList();
            assetGroup.stream().forEach(ag -> {
                AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                assetGroupRelation.setAssetId(id);
                assetGroupRelation.setAssetGroupId(ag.getId());
                assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                assetGroupRelationList.add(assetGroupRelation);
                groupName.append(ag.getName()).append(",");
            });
            assetGroupRelationDao.insertBatch(assetGroupRelationList);
        }
        return StringUtils.isBlank(groupName.toString()) ? ""
            : groupName.toString().substring(0, groupName.toString().length() - 1);
    }

    public void dealIp(String id, List<AssetIpRelationRequest> ipRelationRequests,
                       Integer categoryModel) throws Exception {
        // 1. 删除旧的资产ip关系
        AssetIpRelationQuery query = new AssetIpRelationQuery();
        query.setAssetId(id);
        query.setStatus("1");
        List<AssetIpRelation> list = assetIpRelationDao.findQuery(query);
        assetIpRelationDao.deleteByAssetId(id);
        // 2. 批量保存资产ip关系
        if (CollectionUtils.isNotEmpty(ipRelationRequests)) {
            List<AssetIpRelation> assetIpRelationList = Lists.newArrayList();
            Integer assetId = DataTypeUtils.stringToInteger(id);
            ipRelationRequests.stream().forEach(ip -> {
                AssetIpRelation assetIpRelation = new AssetIpRelation();
                assetIpRelation.setAssetId(assetId);
                assetIpRelation.setIp(ip.getIp());
                assetIpRelation.setNet(ip.getNet());
                assetIpRelationList.add(assetIpRelation);
            });
            assetIpRelationDao.insertBatch(assetIpRelationList);
        }
        // 3. 处理通联
        // 删除被删/改的IP端口通联关系
        if (EnumUtil.equals(categoryModel, AssetCategoryEnum.COMPUTER)
            || EnumUtil.equals(categoryModel, AssetCategoryEnum.NETWORK)) {
            List<String> ips = ipRelationRequests.stream().map(AssetIpRelationRequest::getIp)
                .collect(Collectors.toList());
            List<AssetIpRelation> ipRelations = list.stream().filter(x -> !ips.contains(x.getIp()))
                .collect(Collectors.toList());
            if (ipRelations.size() > 0) {
                assetLinkRelationDao.deleteRelationByIp(id, ipRelations, categoryModel);
            }
        }
    }

    public void dealMac(String id, List<AssetMacRelationRequest> macRelationRequests) {
        // 1. 删除旧的资产mac关系
        assetMacRelationDao.deleteByAssetId(id);
        // 2.插入新的资产mac关系
        if (CollectionUtils.isNotEmpty(macRelationRequests)) {
            List<AssetMacRelation> assetMacRelationList = Lists.newArrayList();
            Integer assetId = DataTypeUtils.stringToInteger(id);
            macRelationRequests.stream().forEach(mac -> {
                AssetMacRelation assetMacRelation = new AssetMacRelation();
                assetMacRelation.setAssetId(assetId);
                assetMacRelation.setMac(mac.getMac());
                assetMacRelationList.add(assetMacRelation);
            });
            assetMacRelationDao.insertBatch(assetMacRelationList);
        }
    }

    public void dealSoft(String id, AssetSoftwareReportRequest softwareReportRequest) throws Exception {
        HashMap<String, Object> query = new HashMap<String, Object>();
        query.put("assetId", id);
        List<AssetSoftwareRelation> preSoft = assetSoftwareRelationDao.getByWhere(query);
        List<Long> preSoftIds = new ArrayList<>();
        if (!preSoft.isEmpty()) {
            preSoftIds = preSoft.stream().map(AssetSoftwareRelation::getSoftwareId).collect(Collectors.toList());
        }
        List<Long> softIds = softwareReportRequest.getSoftId() == null ? new ArrayList<>()
            : softwareReportRequest.getSoftId();
        if (CollectionUtils.isNotEmpty(softIds)) {
            List<Long> deletedSoftIds = new ArrayList<>(softIds);
            List<AssetSoftwareRelation> assetNewSoftware = Lists.newArrayList();
            softIds.removeAll(preSoftIds);
            if (!softIds.isEmpty()) {
                assetNewSoftware = getSoftRelation(id, softIds);
            }
            preSoftIds.removeAll(deletedSoftIds);
            deletedSoftIds = preSoftIds;
            if (!assetNewSoftware.isEmpty()) {
                StringBuilder newSoftIds = new StringBuilder();
                assetNewSoftware.forEach(v -> {
                    newSoftIds.append(v.getSoftwareId());
                    newSoftIds.append(",");
                });
                logger.info("新增软件：{}", newSoftIds.deleteCharAt(newSoftIds.length() - 1).toString());
                assetSoftwareRelationDao.insertBatch(assetNewSoftware);
            }
            if (!deletedSoftIds.isEmpty()) {
                StringBuilder delSoftIds = new StringBuilder();
                deletedSoftIds.forEach(v -> {
                    delSoftIds.append(v);
                    delSoftIds.append(",");
                });
                logger.info("删除软件：{}", delSoftIds.deleteCharAt(delSoftIds.length() - 1).toString());
                assetSoftwareRelationDao.deleteSoftRealtion(id, deletedSoftIds);
            }
        }
    }

    private List<AssetSoftwareRelation> getSoftRelation(String assetId, List<Long> softIds) {
        List<AssetSoftwareRelation> assetSoftwareRelationList = new ArrayList<>();
        Collections.reverse(softIds);
        softIds.stream().forEach(softId -> {
            AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
            assetSoftwareRelation.setAssetId(assetId);
            assetSoftwareRelation.setSoftwareId(softId);
            assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
            assetSoftwareRelationList.add(assetSoftwareRelation);
        });
        return assetSoftwareRelationList;
    }

    public boolean dealAssembly(String id, List<AssetAssemblyRequest> assemblyRequestList) {
        // 1.先删除旧的关系表
        List<String> preAssemblyIds = assetAssemblyDao.findAssemblyIds(id);
        assetAssemblyDao.deleteAssemblyRelation(id);
        boolean isNewAdd = false;
        // 2.插入新的关系
        if (CollectionUtils.isNotEmpty(assemblyRequestList)) {
            List<AssetAssembly> assetAssemblyList = Lists.newArrayList();
            assemblyRequestList.stream().forEach(assemblyRequest -> {
                for (int i = 0; i < assemblyRequest.getAmount(); i++) {
                    AssetAssembly assetAssembly = new AssetAssembly();
                    assetAssembly.setAssetId(id);
                    assetAssembly.setUniqueId(Long.parseLong(SnowFlakeUtil.getSnowId()));
                    assetAssembly.setBusinessId(assemblyRequest.getBusinessId());
                    assetAssemblyList.add(assetAssembly);
                }
            });
            assetAssemblyDao.insertBatch(assetAssemblyList);
            for (AssetAssembly assetAssembly : assetAssemblyList) {
                if (!preAssemblyIds.contains(assetAssembly.getBusinessId())) {
                    isNewAdd = true;
                    break;
                }
            }
        }
        return isNewAdd;
    }

    private boolean checkNumber(String id, String number) {
        return assetDao.selectRepeatNumber(number, id) > 0;
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
     *
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
        otherDeviceEntity.setManufacturer("huawei");
        otherDeviceEntity.setName("ar500");
        otherDeviceEntity.setVersion("1.0.2::~~~wordpress~~");
        otherDeviceEntity.setArea("四川省");
        otherDeviceEntity.setNumber("000001");
        otherDeviceEntity.setCode("3773287221233");
        otherDeviceEntity.setUser("留小查");
        otherDeviceEntity.setImportanceDegree("1");
        otherDeviceEntity.setMachineName("dsa02321");
        otherDeviceEntity.setIsSecrecy("是");
        otherDeviceEntity.setNetType("红网");
        otherDeviceEntity.setIp("192.158.58.58");
        otherDeviceEntity.setMac("00-01-6C-06-A6-29");
        otherDeviceEntity.setSerial("ANFRWGDFETYRYF");
        otherDeviceEntity.setHouseLocation("07-08-1");
        otherDeviceEntity.setLocation("武侯大道108号");
        otherDeviceEntity.setBuyDate(System.currentTimeMillis());
        otherDeviceEntity.setDueDate(System.currentTimeMillis());
        otherDeviceEntity.setExpirationReminder(System.currentTimeMillis());
        otherDeviceEntity.setInstallDate(System.currentTimeMillis());
        otherDeviceEntity.setActiviateDate(System.currentTimeMillis());
        otherDeviceEntity.setWarranty("2年");
        otherDeviceEntity.setMemo("宣传展览导视查询畅销触控一体机，采用FULL HD全视角高清IPS硬屏");
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
        safetyEquipmentEntiy.setName("ar500");
        safetyEquipmentEntiy.setNewVersion("1.1.1");
        safetyEquipmentEntiy
            .setMemo("镇关采用高性能软硬件架构，以智能用户识别与智能应用识别为基础，实现了完全" + "以用户和应用为中心的控制策略，先进的APT防护技术能够有效防范0day攻击和社工渗透等新型威胁。");
        safetyEquipmentEntiy.setMac("00-01-6C-06-A6-29");
        safetyEquipmentEntiy.setNumber("00001");
        safetyEquipmentEntiy.setIp("192.168.1.9");
        safetyEquipmentEntiy.setUser("留小查");
        safetyEquipmentEntiy.setManufacturer("huawei");
        safetyEquipmentEntiy.setSerial("ANFRWGDFETYRYF");
        safetyEquipmentEntiy.setHouseLocation("501机房004号");
        safetyEquipmentEntiy.setImportanceDegree("1");
        safetyEquipmentEntiy.setOperationSystem("Windows");
        safetyEquipmentEntiy.setCode("cd002");
        safetyEquipmentEntiy.setIsSecrecy("是");
        safetyEquipmentEntiy.setNetType("红网");
        safetyEquipmentEntiy.setActiviateDate(System.currentTimeMillis());
        safetyEquipmentEntiy.setInstallDate(System.currentTimeMillis());
        safetyEquipmentEntiy.setExpirationReminder(getCalendar(System.currentTimeMillis()));
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
        storageDeviceEntity.setDueDate(System.currentTimeMillis());
        storageDeviceEntity.setHardDiskNum(1);
        storageDeviceEntity.setFirmwareVersion("spi1");
        storageDeviceEntity.setHighCache("6GB/S");
        storageDeviceEntity.setHouseLocation("501机房004号");
        storageDeviceEntity.setManufacturer("huawei");
        storageDeviceEntity.setMemo("私有云网络存储器nas文件共享数字备份 AS6202T 0TB 标机");
        storageDeviceEntity.setName("ar500");
        storageDeviceEntity.setInnerInterface("SSD");
        storageDeviceEntity.setNumber("000001");
        storageDeviceEntity.setSlotType("1");
        storageDeviceEntity.setWarranty("2年");
        storageDeviceEntity.setRaidSupport(1);
        storageDeviceEntity.setUser("留小查");
        storageDeviceEntity.setSerial("ANFRWGDFETYRYF");
        storageDeviceEntity.setImportanceDegree("1");
        storageDeviceEntity.setCode("cd002");
        storageDeviceEntity.setIsSecrecy("是");
        storageDeviceEntity.setActiviateDate(System.currentTimeMillis());
        storageDeviceEntity.setInstallDate(System.currentTimeMillis());
        storageDeviceEntity.setExpirationReminder(getCalendar(System.currentTimeMillis()));
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
        networkDeviceEntity.setCode("cd002");
        networkDeviceEntity.setIsSecrecy("是");
        networkDeviceEntity.setNetType("红网");
        networkDeviceEntity.setActiviateDate(System.currentTimeMillis());
        networkDeviceEntity.setInstallDate(System.currentTimeMillis());
        networkDeviceEntity.setExpirationReminder(getCalendar(System.currentTimeMillis()));
        networkDeviceEntity.setCpuSize(2.3f);
        networkDeviceEntity.setArea("成都市");
        networkDeviceEntity.setMac("00-01-6C-06-A6-29");
        networkDeviceEntity.setName("ar500");
        networkDeviceEntity.setDramSize(3.42f);
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
        networkDeviceEntity.setManufacturer("huawei");
        networkDeviceEntity.setIsWireless(1);
        networkDeviceEntity.setSerial("ANFRWGDFETYRYF");
        networkDeviceEntity.setHouseLocation("501机房004号");
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
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。", dictionary + "/", dataList);
    }

    private List<ComputeDeviceEntity> initComputeData() {
        List<ComputeDeviceEntity> dataList = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = new ComputeDeviceEntity();
        computeDeviceEntity.setArea("成都市");
        computeDeviceEntity.setCode("cd002");
        computeDeviceEntity.setIsOrphan("是");
        computeDeviceEntity.setIsSecrecy("是");
        computeDeviceEntity.setNetType("红网");
        computeDeviceEntity.setActiviateDate(System.currentTimeMillis());
        computeDeviceEntity.setInstallDate(System.currentTimeMillis());
        computeDeviceEntity.setExpirationReminder(getCalendar(System.currentTimeMillis()));
        computeDeviceEntity.setName("ar500");
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
        computeDeviceEntity.setDueTime(System.currentTimeMillis());
        computeDeviceEntity.setManufacturer("huawei");
        computeDeviceEntity.setOperationSystem("Window 10");
        dataList.add(computeDeviceEntity);
        return dataList;
    }

    private void loggerIsDelete(File file) {
        logger.info(file.getName() + "文件删除" + isSuccess(file.delete()));
    }

    public void sendStreamToClient(File file) {
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
                fileName = new String(fileName.replaceAll(" ", "").getBytes(StandardCharsets.UTF_8), "ISO8859-1");
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

            if (checkRepeatNumber(entity.getNumber(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (!checkSupplier(entity.getManufacturer())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商不存在！");
                continue;
            }
            if (!checkName(entity.getManufacturer(), entity.getName())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商下,不存在当前名称！");
                continue;
            }

            if (checkRepeatMAC(entity.getMac(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("MAC地址重复！");
                continue;
            }

            if (entity.getExpirationReminder() >= entity.getDueTime()) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期提醒时间需小于到期时间！");
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

            String typeId = getNetTypeByName(entity.getNetType());
            if (checkIP(entity.getIp(), aesEncoder.encode(areaId, LoginUserUtil.getLoginUser().getUsername()),
                aesEncoder.encode(typeId, LoginUserUtil.getLoginUser().getUsername()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该IP已被使用或与归属区域、网络类型冲突！");
                continue;
            }

            assetNumbers.add(entity.getNumber());
            assetMac.add(entity.getMac());
            ComputerVo computerVo = new ComputerVo();
            Asset asset = new Asset();
            asset.setOperationSystem(Long.parseLong(treeDao.queryUniqueIdByNodeName(entity.getOperationSystem())));
            asset.setOperationSystemName(entity.getOperationSystem());
            asset.setResponsibleUserId(checkUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(areaId);
            asset.setIsInnet(0);
            asset.setActiviateDate(entity.getActiviateDate());
            asset.setInstallDate(entity.getInstallDate());
            if (null == entity.getExpirationReminder()) {
                asset.setExpirationReminder(getCalendar(entity.getDueTime()));
            } else {
                asset.setExpirationReminder(entity.getExpirationReminder());
            }
            asset.setCode(entity.getCode());
            asset.setNetType(typeId);
            asset.setIsOrphan("是".equals(entity.getIsOrphan()) ? 1 : 2);
            asset.setIsSecrecy("是".equals(entity.getIsSecrecy()) ? 1 : 2);
            asset.setAdmittanceStatus(1);
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
            asset.setInstallType(InstallType.AUTOMATIC.getCode());
            asset.setCategoryModel(AssetCategoryEnum.COMPUTER.getCode());
            asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
            AssetIpRelation assetIpRelation = new AssetIpRelation();
            assetIpRelation.setIp(entity.getIp());
            AssetMacRelation assetMacRelation = new AssetMacRelation();
            assetMacRelation.setMac(entity.getMac());
            computerVo.setAsset(asset);
            computerVo.setAssetIpRelation(assetIpRelation);
            computerVo.setAssetMacRelation(assetMacRelation);
            computerVos.add(computerVo);
            a++;
        }

        if (repeat + error == 0) {
            for (ComputerVo computerVo : computerVos) {
                Asset asset = computerVo.getAsset();
                AssetIpRelation assetIpRelation = computerVo.getAssetIpRelation();
                AssetMacRelation assetMacRelation = computerVo.getAssetMacRelation();

                try {
                    assetDao.insert(asset);
                } catch (DuplicateKeyException exception) {
                    throw new BusinessException("请勿重复提交！");
                }
                // ip/mac
                assetIpRelation.setAssetId(asset.getId());
                assetMacRelation.setAssetId(asset.getId());
                assetIpRelationDao.insert(assetIpRelation);
                assetMacRelationDao.insert(assetMacRelation);
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
        List<String> assetNumbers = new ArrayList<>();
        List<String> assetMac = new ArrayList<>();
        List<AssetNetworkEquipment> networkEquipments = new ArrayList<>();
        List<AssetMacRelation> assetMacRelations = new ArrayList<>();

        for (NetworkDeviceEntity entity : entities) {

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
            if (checkRepeatNumber(entity.getNumber(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (!checkSupplier(entity.getManufacturer())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商不存在！");
                continue;
            }
            if (!checkName(entity.getManufacturer(), entity.getName())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商下,不存在当前名称！");
                continue;
            }

            if (checkRepeatMAC(entity.getMac(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("MAC地址重复！");
                continue;
            }

            if (entity.getPortSize() <= 0 || entity.getPortSize() > 100) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("网口数目范围为1-100！");
                continue;
            }
            if (entity.getInterfaceSize() != null) {
                if (entity.getInterfaceSize() > 127) {
                    error++;
                    a++;
                    builder.append("第").append(a).append("行").append("接口数目不大于127！");
                    continue;
                }
            }

            if (entity.getButDate() != null) {

                if (isBuyDataBig(entity.getButDate())) {
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

            if (entity.getExpirationReminder() >= entity.getDueDate()) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期提醒时间需小于到期时间！");
                continue;
            }

            if ("".equals(checkUser(entity.getUser()))) {
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
            String typeId = getNetTypeByName(entity.getNetType());
            assetNumbers.add(entity.getNumber());
            assetMac.add(entity.getMac());
            Asset asset = new Asset();
            AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
            asset.setActiviateDate(entity.getActiviateDate());
            asset.setInstallDate(entity.getInstallDate());
            if (null == entity.getExpirationReminder()) {
                asset.setExpirationReminder(getCalendar(entity.getDueDate()));
            } else {
                asset.setExpirationReminder(entity.getExpirationReminder());
            }
            asset.setCode(entity.getCode());
            asset.setNetType(typeId);
            asset.setIsSecrecy("是".equals(entity.getIsSecrecy()) ? 1 : 2);
            asset.setResponsibleUserId(checkUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(areaId);
            asset.setInstallType(InstallType.AUTOMATIC.getCode());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
            asset.setAssetSource(ReportType.MANUAL.getCode());
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setManufacturer(entity.getManufacturer());
            asset.setSerial(entity.getSerial());
            asset.setHouseLocation(entity.getHouseLocation());
            asset.setBuyDate(entity.getButDate());
            asset.setServiceLife(entity.getDueDate());
            asset.setWarranty(entity.getWarranty());
            asset.setCategoryModel(AssetCategoryEnum.NETWORK.getCode());
            asset.setDescrible(entity.getMemo());
            asset.setIsInnet(0);
            asset.setAdmittanceStatus(1);
            asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
            assets.add(asset);
            AssetMacRelation assetMacRelation = new AssetMacRelation();
            assetMacRelation.setMac(entity.getMac());
            assetMacRelations.add(assetMacRelation);
            assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
            assetNetworkEquipment.setFirmwareVersion(entity.getFirmwareVersion());
            assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetNetworkEquipment.setInterfaceSize(entity.getInterfaceSize());
            assetNetworkEquipment.setPortSize(entity.getPortSize());
            assetNetworkEquipment.setIos(entity.getIos());
            assetNetworkEquipment.setOuterIp(entity.getOuterIp());
            assetNetworkEquipment.setCpuVersion(entity.getCpuVersion());
            assetNetworkEquipment.setSubnetMask(entity.getSubnetMask());
            assetNetworkEquipment.setExpectBandwidth(entity.getExpectBandwidth());
            assetNetworkEquipment.setNcrmSize(entity.getNcrmSize());
            assetNetworkEquipment.setCpuSize(entity.getCpuSize());
            assetNetworkEquipment.setDramSize(entity.getDramSize());
            assetNetworkEquipment.setFlashSize(entity.getFlashSize());
            assetNetworkEquipment.setRegister(entity.getRegister());
            assetNetworkEquipment.setIsWireless(entity.getIsWireless());
            assetNetworkEquipment.setStatus(1);
            networkEquipments.add(assetNetworkEquipment);

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
                assetMacRelations.get(i).setAssetId(assets.get(i).getId());
                recordList.add(assetRecord(stringId, assets.get(i).getAreaId()));
                success++;
            }
            assetNetworkEquipmentDao.insertBatch(networkEquipments);
            assetMacRelationDao.insertBatch(assetMacRelations);
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
        List<String> assetNumbers = new ArrayList<>();
        List<String> assetMac = new ArrayList<>();
        List<Asset> assets = new ArrayList<>();
        List<AssetMacRelation> assetMacRelations = new ArrayList<>();
        List<AssetIpRelation> assetIpRelations = new ArrayList<>();
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

            if (checkRepeatNumber(entity.getNumber(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (!checkSupplier(entity.getManufacturer())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商不存在！");
                continue;
            }
            if (!checkName(entity.getManufacturer(), entity.getName())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商下,不存在当前名称！");
                continue;
            }
            if (checkRepeatMAC(entity.getMac(), null)) {
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
            if (entity.getExpirationReminder() >= entity.getDueDate()) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期提醒时间需小于到期时间！");
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
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域！");
                continue;
            }
            String typeId = getNetTypeByName(entity.getNetType());
            if (checkIP(entity.getIp(), aesEncoder.encode(areaId, LoginUserUtil.getLoginUser().getUsername()),
                aesEncoder.encode(typeId, LoginUserUtil.getLoginUser().getUsername()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该IP已被使用或与归属区域、网络类型冲突！");
                continue;
            }
            assetNumbers.add(entity.getNumber());
            assetMac.add(entity.getMac());
            AssetMacRelation assetMacRelation = new AssetMacRelation();
            assetMacRelation.setMac(entity.getMac());
            assetMacRelations.add(assetMacRelation);
            AssetIpRelation assetIpRelation = new AssetIpRelation();
            assetIpRelation.setIp(entity.getIp());
            assetIpRelations.add(assetIpRelation);
            AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
            Asset asset = new Asset();
            asset.setActiviateDate(entity.getActiviateDate());
            asset.setInstallDate(entity.getInstallDate());
            if (null == entity.getExpirationReminder()) {
                asset.setExpirationReminder(getCalendar(entity.getDueDate()));
            } else {
                asset.setExpirationReminder(entity.getExpirationReminder());
            }
            asset.setCode(entity.getCode());
            asset.setNetType(typeId);
            asset.setIsSecrecy("是".equals(entity.getIsSecrecy()) ? 1 : 2);
            asset.setOperationSystem(Long.parseLong(treeDao.queryUniqueIdByNodeName(entity.getOperationSystem())));
            asset.setOperationSystemName(entity.getOperationSystem());
            asset.setInstallType(InstallType.AUTOMATIC.getCode());
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
            asset.setIsInnet(0);
            asset.setAdmittanceStatus(1);
            asset.setCategoryModel(AssetCategoryEnum.SAFETY.getCode());
            assets.add(asset);
            assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
            assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetSafetyEquipment.setIp(entity.getIp());
            assetSafetyEquipment.setMac(entity.getMac());
            assetSafetyEquipment.setStatus(1);
            assetSafetyEquipment.setNewVersion(entity.getNewVersion());
            assetSafetyEquipments.add(assetSafetyEquipment);

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
                assetIpRelations.get(i).setAssetId(assets.get(i).getId());
                assetMacRelations.get(i).setAssetId(assets.get(i).getId());
                recordList.add(assetRecord(stringId, assets.get(i).getAreaId()));
                success++;
            }
            assetSafetyEquipmentDao.insertBatch(assetSafetyEquipments);
            assetIpRelationDao.insertBatch(assetIpRelations);
            assetMacRelationDao.insertBatch(assetMacRelations);
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
        List<String> assetNumbers = new ArrayList<>();
        List<AssetStorageMedium> assetStorageMedia = new ArrayList<>();
        for (StorageDeviceEntity entity : resultDataList) {

            if (assetNumbers.contains(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (checkRepeatNumber(entity.getNumber(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }

            if (!checkSupplier(entity.getManufacturer())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商不存在！");
                continue;
            }
            if (!checkName(entity.getManufacturer(), entity.getName())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商下,不存在当前名称！");
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
            if (entity.getExpirationReminder() >= entity.getDueDate()) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("到期提醒时间需小于到期时间！");
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

            assetNumbers.add(entity.getNumber());
            Asset asset = new Asset();
            AssetStorageMedium assetStorageMedium = new AssetStorageMedium();
            asset.setActiviateDate(entity.getActiviateDate());
            asset.setInstallDate(entity.getInstallDate());
            if (null == entity.getExpirationReminder()) {
                asset.setExpirationReminder(getCalendar(entity.getDueDate()));
            } else {
                asset.setExpirationReminder(entity.getExpirationReminder());
            }
            asset.setCode(entity.getCode());
            asset.setIsSecrecy("是".equals(entity.getIsSecrecy()) ? 1 : 2);
            asset.setResponsibleUserId(checkUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(areaId);
            asset.setCategoryModel(AssetCategoryEnum.STORAGE.getCode());
            asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
            asset.setAssetSource(ReportType.MANUAL.getCode());
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setInstallType(InstallType.AUTOMATIC.getCode());
            asset.setIsInnet(0);
            asset.setAdmittanceStatus(1);
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
        List<String> assetNumbers = new ArrayList<>();
        List<String> assetMac = new ArrayList<>();
        List<AssetMacRelation> assetMacRelations = new ArrayList<>();
        List<AssetIpRelation> assetIpRelations = new ArrayList<>();
        for (OtherDeviceEntity entity : resultDataList) {

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

            if (checkRepeatNumber(entity.getNumber(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (checkRepeatMAC(entity.getMac(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("MAC地址重复！");
                continue;
            }

            if (!checkSupplier(entity.getManufacturer())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商不存在！");
                continue;
            }
            if (!checkName(entity.getManufacturer(), entity.getName())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("该厂商下,不存在当前名称！");
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

            assetNumbers.add(entity.getNumber());
            assetMac.add(entity.getMac());
            Asset asset = new Asset();
            asset.setResponsibleUserId(checkUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(areaId);
            asset.setInstallType(InstallType.AUTOMATIC.getCode());
            asset.setImportanceDegree(DataTypeUtils.stringToInteger(entity.getImportanceDegree()));
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
            asset.setAssetSource(ReportType.MANUAL.getCode());
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setIsInnet(0);
            asset.setAdmittanceStatus(1);
            asset.setManufacturer(entity.getManufacturer());
            asset.setSerial(entity.getSerial());
            asset.setBuyDate(entity.getBuyDate());
            asset.setServiceLife(entity.getDueDate());
            asset.setWarranty(entity.getWarranty());
            asset.setDescrible(entity.getMemo());
            asset.setCategoryModel(AssetCategoryEnum.OTHER.getCode());
            assets.add(asset);
            AssetIpRelation assetIpRelation = new AssetIpRelation();
            assetIpRelation.setIp(entity.getIp());
            AssetMacRelation assetMacRelation = new AssetMacRelation();
            assetMacRelation.setMac(entity.getMac());
            assetMacRelations.add(assetMacRelation);
            assetIpRelations.add(assetIpRelation);

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
                assetIpRelations.get(i).setAssetId(assets.get(i).getId());
                assetMacRelations.get(i).setAssetId(assets.get(i).getId());
                recordList.add(assetRecord(stringId, assets.get(i).getAreaId()));
                success++;
            }
            assetIpRelationDao.insertBatch(assetIpRelations);
            assetMacRelationDao.insertBatch(assetMacRelations);
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

    private AssetOperationRecord assetRecord(String id, String areaId) {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(id);
        assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetOperationRecord.setContent("导入硬件资产");
        assetOperationRecord.setOriginStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        return assetOperationRecord;
        // assetOperationRecordDao.insert(assetOperationRecord);
    }

    private boolean checkIP(String ip, String areaId, String typeId) {
        ActionResponse areaClientIP = areaClient.getIP(areaId, ip, typeId);
        if (null == areaClientIP || !RespBasicCode.SUCCESS.getResultCode().equals(areaClientIP.getHead().getCode())) {
            return true;
        }
        return false;
    }

    // 前一周 时间
    private Long getCalendar(Long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.add(Calendar.DATE, -7);
        return c.getTimeInMillis();
    }

    private String getNetTypeByName(String netType) {
        return assetNettypeManageDao.findIdsByName(netType).toString();
    }

    @Override
    public Integer assetNoRegister(List<NoRegisterRequest> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        List<String> collect = list.stream().map(t -> t.getAssetId()).collect(Collectors.toList());
        String[] assetIds = new String[collect.size()];
        collect.toArray(assetIds);
        // 查询资产当前状态
        List<Asset> currentAssetList = assetDao.getAssetStatusListByIds(assetIds);
        if (CollectionUtils.isEmpty(currentAssetList)) {
            throw new BusinessException("资产不存在");
        }
        for (Asset currentAsset : currentAssetList) {
            if (!(AssetStatusEnum.WAIT_REGISTER.getCode().equals(currentAsset.getAssetStatus()))) {
                AssetStatusEnum assetByCode = AssetStatusEnum.getAssetByCode(currentAsset.getAssetStatus());
                String assetStatus = assetByCode.getMsg();
                throw new BusinessException(String.format("资产已处于%s，无法重复提交！", assetStatus));
            }
        }
        List<Asset> assetList = new ArrayList<>(currentAssetList.size());
        for (Asset currentAsset : currentAssetList) {
            // 记录资产状态变更信息到操作记录表
            operationRecord(currentAsset.getId().toString());
            // 记录日志
            if (assetDao.getNumberById(currentAsset.getId().toString()) == null) {
                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.NO_REGISTER.getName(), currentAsset.getId(),
                    currentAsset.getName(), list, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NOT_REGISTER));
            } else {
                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.NO_REGISTER.getName(), currentAsset.getId(),
                    assetDao.getNumberById(currentAsset.getId().toString()), list, BusinessModuleEnum.HARD_ASSET,
                    BusinessPhaseEnum.NOT_REGISTER));
            }

            // 更新状态
            Asset asset = new Asset();
            asset.setId(currentAsset.getId());
            asset.setAssetStatus(AssetStatusEnum.NOT_REGISTER.getCode());
            asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
            assetList.add(asset);
        }
        // 更新状态
        assetDao.updateAssetBatch(assetList);

        List<ActivityHandleRequest> activityHandleRequests = new ArrayList<>();
        List<String> taskIds = list.stream().filter(t -> t.getTaskId() != null).map(t -> t.getTaskId())
            .collect(Collectors.toList());
        for (String taskId : taskIds) {
            ActivityHandleRequest activityHandleRequest = new ActivityHandleRequest();
            activityHandleRequest.setTaskId(taskId);
            Map map = new HashMap();
            Map map2 = new HashMap();
            map2.put("admittanceResult", "noAdmittance");
            map.put("formData", map2);
            activityHandleRequest.setFormData(map);
            activityHandleRequests.add(activityHandleRequest);
        }
        if (activityHandleRequests.size() > 0)
            activityClient.completeTaskBatch(activityHandleRequests);
        LogUtils.info(logger, AssetEventEnum.NO_REGISTER.getName() + " {}", list);
        return currentAssetList.size();
    }

    @Override
    @Transactional
    public Integer assetNoRegister(AssetStatusChangeRequest assetStatusChangeRequest) throws Exception {
        return 0;
    }

    @Override
    public List<SelectResponse> queryBaselineTemplate() {
        return assetDao.queryBaselineTemplate();
    }

    @Override
    public List<AssetEntity> assetsTemplate(ProcessTemplateRequest request) throws Exception {
        return getAssetEntities(request);

    }

    @Override
    public List<IpMacPort> matchAssetByIpMac(AssetMatchRequest request) throws Exception {
        List<IpMacPort> ipMacList = new ArrayList<>();
        // 获取用户所在的区域
        // List<Integer> areaIdList =
        // LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().map(DataTypeUtils::integerToString).collect(Collectors.toList());
        // request.setAreaIds(areaIdList);
        for (IpMacPort ipMacPort : request.getIpMacPorts()) {
            request.setIpMacPort(ipMacPort);
            // 筛选异常资产
            if (!assetDao.matchAssetByIpMac(request)) {
                ipMacList.add(ipMacPort);
            }

        }
        return ipMacList;
    }

    @Override
    public List<String> queryUuidByAssetId(AssetIdRequest request) throws Exception {
        if (CollectionUtils.isNotEmpty(request.getAssetIds())) {
            return assetDao.findUuidByAssetId(request.getAssetIds());
        } else {
            throw new BusinessException("资产ID不能为空");
        }
    }

    @Override
    public Integer countUnusual(AssetQuery query) {
        Integer count = 0;
        if (CollectionUtils.isEmpty(query.getAssetStatusList())) {
            List<Integer> status = Arrays.asList(AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.IN_CHANGE.getCode(),
                AssetStatusEnum.WAIT_RETIRE.getCode(), AssetStatusEnum.WAIT_SCRAP.getCode());
            query.setAssetStatusList(status);
        }
        if (ArrayUtils.isEmpty(query.getAreaIds())) {

            query.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().stream().toArray(String[]::new));
        }
        // 如果会查询漏洞数量
        if (query.getQueryVulCount() != null && query.getQueryVulCount()) {
            count = assetDao.queryAllAssetVulCount(query);

        }

        // 如果会查询补丁数据
        if (query.getQueryPatchCount() != null && query.getQueryPatchCount()) {
            count = assetDao.queryAllAssetPatchCount(query);

        }
        // 如果会查询告警数量
        if (query.getQueryAlarmCount() != null && query.getQueryAlarmCount()) {
            count = assetDao.findAlarmAssetCount(query);

        }
        return count;
    }

    @Override
    public Integer queryUnknownAssetCount(AssetUnknownRequest request) throws Exception {
        request.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        return assetDao.findUnknownAssetCount(request);
    }

    @Override
    public Integer updateAssetBaselineTemplate(AssetBaselinTemplateQuery query) {
        query.setGmtModify(System.currentTimeMillis());
        LoginUser loginUser = LoginUserUtil.getLoginUser();
        query.setModifyUser(loginUser == null ? "0" : loginUser.getStringId());
        return assetDao.updateAssetBaselineTemplate(query);
    }

    @Override
    public List<String> findAvailableAssetStatus(Integer assetStatus) {
        return Arrays.stream(AvailableStatusEnum.values()).filter(v -> v.getCode().equals(assetStatus))
            .map(AvailableStatusEnum::getMsg).collect(Collectors.toList());
    }

    private void operationRecord(String id) throws Exception {
        // 记录操作历史到数据库
        AssetOperationRecord operationRecord = new AssetOperationRecord();
        operationRecord.setTargetObjectId(id);
        operationRecord.setOriginStatus(AssetStatusEnum.WAIT_REGISTER.getCode());
        operationRecord.setTargetStatus(AssetStatusEnum.NOT_REGISTER.getCode());
        operationRecord.setGmtCreate(System.currentTimeMillis());
        operationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        operationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        operationRecord.setContent(AssetFlowEnum.NO_REGISTER.getNextMsg());
        operationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        operationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        operationRecordDao.insert(operationRecord);
    }

    @Override
    public Integer queryAssetCountByAreaIds(List<String> areaIds) {

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
        DownloadVO downloadVO = new DownloadVO();
        // 未知资产
        if (assetQuery.getUnknownAssets()) {
            List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
            List<AssetUnkonwEntity> assetEntities1 = BeanConvert.convert(assetEntities, AssetUnkonwEntity.class);
            downloadVO.setDownloadList(assetEntities1);
        } else {
            List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
            downloadVO.setDownloadList(assetEntities);
        }

        // 3种导方式 1 excel 2 cvs 3 xml
        if (CollectionUtils.isNotEmpty(downloadVO.getDownloadList())) {

            if (assetQuery.getExportType() == 1) {
                excelDownloadUtil.excelDownload(request, response,
                    "资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);
            } else if (assetQuery.getExportType() == 2) {
                List<?> downloadList = downloadVO.getDownloadList();
                String[] files;
                if (assetQuery.getUnknownAssets()) {
                    files = new String[] { "名称", "编号", "资产类型", "厂商", "ip", "mac", "资产组", "重要程度", "使用者", "上报方式", "操作系统名",
                                           "状态", "首次发现时间", "首次入网时间", "到期时间" };
                } else {
                    files = new String[] { "ip", "mac", "上报方式", "首次发现时间" };
                }

                csvUtils.writeCSV(downloadList, "资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT),
                    files, request, response);
            } else if (assetQuery.getExportType() == 3) {
                String fileName = "资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT);
                if (assetQuery.getUnknownAssets()) {
                    List<AssetUnkonwEntity> downloadList = (List<AssetUnkonwEntity>) downloadVO.getDownloadList();
                    Dom4jUtils.createXml(downloadList, AssetUnkonwEntity.class, request, response, fileName);
                } else {

                    List<AssetEntity> downloadList = (List<AssetEntity>) downloadVO.getDownloadList();
                    Dom4jUtils.createXml(downloadList, AssetEntity.class, request, response, fileName);
                }
            }

            LogUtils.recordOperLog(
                new BusinessData("导出《资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT) + "》", 0, "",
                    assetQuery, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        } else {
            throw new BusinessException("导出数据为空");
        }
    }

    @Override
    public Set<String> pulldownUnconnectedManufacturer(Integer isNet, String primaryKey) {
        AssetQuery query = new AssetQuery();
        if ((isNet == null) || isNet == 1) {
            query.setCategoryModels(
                new Integer[] { AssetCategoryEnum.COMPUTER.getCode(), AssetCategoryEnum.NETWORK.getCode() });
        } else {
            query.setCategoryModels(new Integer[] { AssetCategoryEnum.NETWORK.getCode() });
        }
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        query.setPrimaryKey(primaryKey);
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        return assetLinkRelationDao.pulldownUnconnectedManufacturer(query);
    }

    @Override
    public AlarmAssetDataResponse queryAlarmAssetList(AlarmAssetRequest alarmAssetRequest) {
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
        List<String> list = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        if (CollectionUtils.isEmpty(list)) {
            return idResponse;
        }
        idResponse.setResult(StringUtils.join(assetDao.findAssetIds(list).toArray(), ","));
        return idResponse;
    }

    @Override
    public Integer queryWaitRegistCount() {
        LoginUser loginUser;
        if (Objects.isNull(loginUser = LoginUserUtil.getLoginUser())) {
            return 0;
        }

        // 如果用户不具备登记权限，返回0个待登记资产
        UserStatus userStatus = sysUserClient.getLoginUserInfo(loginUser.getUsername());
        if (CollectionUtils.isNotEmpty(userStatus.getMenus())) {
            List<OauthMenuResponse> menuResponseList = userStatus.getMenus();
            boolean isTag = false;
            for (OauthMenuResponse oauthMenuResponse : menuResponseList) {
                String tag = oauthMenuResponse.getTag();
                if ("asset:info:list:checkin".equals(tag)) {
                    isTag = true;
                }
            }

            if (!isTag) {
                return 0;
            }

        }
        // 查询待登记的资产数量
        List<Integer> waitRegisterIds = assetDao.queryIdsByAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode(),
            loginUser.getAreaIdsOfCurrentUser());
        return waitRegisterIds.size();
    }

    @Override
    public Integer queryNormalCount() {
        AssetQuery query = new AssetQuery();
        // 已入网、待退役资产
        query
            .setAssetStatusList(Arrays.asList(AssetStatusEnum.NET_IN.getCode(), AssetStatusEnum.WAIT_RETIRE.getCode()));

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

    @Override
    public List<String> queryIdByAreaIds(AreaIdRequest request) throws Exception {
        if (CollectionUtils.isNotEmpty(request.getAreaIds())) {
            return assetDao.findIdByAreaIds(request.getAreaIds());
        } else {
            throw new BusinessException("区域ID不能为空");
        }

    }

    @Override
    public List<AssetAreaAndIpResponse> queryIpByAreaId(AssetIpRequest request) {
        List<AssetAreaAndIpResponse> assetAreaAndIpResponses = assetDao.queryIpByAreaId(request);
        LogUtils.info(logger, "查询区域ip:{}", assetAreaAndIpResponses);
        return assetAreaAndIpResponses.stream().filter(ipResponse -> StringUtils.isNotBlank(ipResponse.getIp()))
            .collect(Collectors.toList());
    }

    @Override
    public String recommendName() {

        return UUID.randomUUID().toString();
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

    @Override
    public List<AssetMatchResponse> queryAssetInfo(AssetMatchRequest request) {
        return assetDao.queryAssetInfo(request);
    }

    @Override
    public PageResult<AssetResponse> queryAssetPage(AssetMultipleQuery assetMultipleQuery) {
        if (CollectionUtils.isEmpty(assetMultipleQuery.getAreaIds())) {
            assetMultipleQuery.setAreaIds(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        }
        // 查询待办
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("asset");
        // 工作台进入,过滤当前用户待办的资产id
        if (assetMultipleQuery.getEnterControl()) {
            if (MapUtils.isNotEmpty(processMap)) {
                // 待办资产id
                Set<String> ids = processMap.keySet();
                assetMultipleQuery.setIds(ids.toArray(new String[ids.size()]));
            }
        }

        Integer count = assetDao.queryAssetCount(assetMultipleQuery);
        if (count <= 0) {
            return new PageResult<>(assetMultipleQuery.getPageSize(), 0, assetMultipleQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        List<Asset> assetList = assetDao.queryAssetList(assetMultipleQuery);
        assetList.stream().forEach(asset -> {
            // 责任人名称
            if (StringUtils.isNotBlank(asset.getResponsibleUserId())) {
                AssetUser assetUser = (AssetUser) assetBaseDataCache.get(AssetBaseDataCache.ASSET_USER,
                    DataTypeUtils.stringToInteger(asset.getResponsibleUserId()));
                if (assetUser != null) {
                    if (StringUtils.isNotBlank(assetUser.getDepartmentId())) {
                        AssetDepartment department = (AssetDepartment) assetBaseDataCache.get(
                            AssetBaseDataCache.ASSET_DEPARTMENT,
                            DataTypeUtils.stringToInteger(assetUser.getDepartmentId()));
                        asset.setResponsibleUserName(department.getName() + "/" + assetUser.getName());
                        asset.setDepartmentName(department.getName());
                    }
                }
            }
            // 所属业务
            if (StringUtils.isNotBlank(asset.getAssetBusiness())) {
                List<AssetBusiness> assetBusinessList = assetBaseDataCache.getAll(AssetBaseDataCache.ASSET_BUSINESS,
                    DataTypeUtils.stringArrayToIntegerArray(asset.getAssetBusiness().split(",")));
                asset.setAssetBusiness(StringUtils
                    .join(assetBusinessList.stream().map(AssetBusiness::getName).collect(Collectors.toList()), ","));
            }
            // 所属区域
            if (StringUtils.isNotBlank(asset.getAreaId())) {
                String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                    asset.getAreaId());
                SysArea sysArea = null;
                try {
                    sysArea = redisUtil.getObject(key, SysArea.class);
                } catch (Exception e) {
                    logger.error("获取区域名称出错");
                }
                asset.setAreaName(Optional.ofNullable(sysArea).map(SysArea::getFullName).orElse(null));
            }
            // 行颜色
            if (asset.getServiceLife() != null && asset.getServiceLife() > 0) {
                // 已过到期时间,显示黄色
                if (System.currentTimeMillis() > asset.getServiceLife()) {
                    asset.setRowColor("yellow");
                } else {
                    if (asset.getExpirationReminder() != null && asset.getExpirationReminder() > 0) {
                        // 未到到期时间，但是以到到期提醒时间，显示绿色
                        if (System.currentTimeMillis() > asset.getServiceLife()) {
                            asset.setRowColor("green");
                        }
                    }
                }
            }
        });
        List<AssetResponse> assetResponseList = responseConverter.convert(assetList, AssetResponse.class);
        for (AssetResponse object : assetResponseList) {
            if (MapUtils.isNotEmpty(processMap)) {
                object.setWaitingTaskReponse(processMap.get(object.getStringId()));
            }
        }
        return new PageResult<>(assetMultipleQuery.getPageSize(), count, assetMultipleQuery.getCurrentPage(),
            assetResponseList);
    }

    @Override
    public List<String> queryManufacturer() {
        return assetDao.queryManufacturer();
    }

    @Override
    public List<String> queryName() {
        return assetDao.queryName();
    }

    @Override
    public List<String> queryVersion() {
        return assetDao.queryVersion();
    }

    @Override
    public List<SelectResponse> queryAssetGroup() {
        return assetDao.queryAssetGroup();
    }

    @Override
    public List<SelectResponse> queryUser() {
        return assetDao.queryUser();
    }

    @Override
    public List<SelectResponse> queryDepartment() {
        return assetDao.queryDepartment();
    }

    @Override
    public List<SelectResponse> queryTemplate() {
        return assetDao.queryTemplate();
    }

    @Override
    public List<SelectResponse> queryBusiness() {
        return assetDao.queryBusiness();
    }

    @Override
    public List<SelectResponse> queryNetType() {
        return assetDao.queryNetType();
    }

    @Override
    public PageResult<AssetResponse> queryOrderAssetPage(AssetOaOrderQuery assetOaOrderQuery) {
        List<Asset> assets = assetDao.queryOrderAssetList(assetOaOrderQuery);
        List<AssetResponse> assetResponses = responseConverter.convert(assets, AssetResponse.class);
        return new PageResult<>(assetOaOrderQuery.getPageSize(), assetDao.queryOrderAssetCount(assetOaOrderQuery),
            assetOaOrderQuery.getCurrentPage(), assetResponses);
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

            assetEntity.setAssetSource(AssetSourceEnum.getNameByCode(asset.getAssetSource()));
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

enum AvailableStatusEnum implements CodeEnum {
                                              // WAIT_REGISTER(AssetStatusEnum.WAIT_REGISTER.getCode(),
                                              // "不予登记"), NET_IN_LEADER_DISAGREE(AssetStatusEnum.NET_IN_LEADER_DISAGREE
                                              // .getCode(), "入网未通过处理"),
                                              // NET_IN_CHECK(AssetStatusEnum.NET_IN_CHECK.getCode(), "准入实施"),
                                              // NET_IN(AssetStatusEnum.NET_IN.getCode(), "退役申请"),
                                              // RETIRE_DISAGREE(AssetStatusEnum.RETIRE_DISAGREE.getCode(), "退役未通过处理"),
                                              // WAIT_RETIRE(AssetStatusEnum.WAIT_RETIRE.getCode(), "退役执行"),
                                              // RETIRE(AssetStatusEnum.RETIRE.getCode(), "报废申请"),
                                              // SCRAP_DISAGREE(AssetStatusEnum.SCRAP_DISAGREE.getCode(), "报废未通过处理"),
                                              // WAIT_SCRAP(AssetStatusEnum.WAIT_SCRAP.getCode(), "报废执行"),
                                              // // 已入网的计算设备才有关联软件
                                              // COMPUTER(AssetStatusEnum.NET_IN.getCode(), "关联软件");
                                              WAIT_REGISTER(AssetStatusEnum.WAIT_REGISTER.getCode(),
                                                            "不予登记"), NET_IN_LEADER_DISAGREE(0,
                                                                                            "入网未通过处理"), NET_IN_CHECK(AssetStatusEnum.NET_IN_CHECK
                                                                                                .getCode(), "准入实施"), NET_IN(AssetStatusEnum.NET_IN.getCode(), "退役申请"), RETIRE_DISAGREE(0, "退役未通过处理"), WAIT_RETIRE(AssetStatusEnum.WAIT_RETIRE.getCode(), "退役执行"), RETIRE(AssetStatusEnum.RETIRE.getCode(), "报废申请"), SCRAP_DISAGREE(0, "报废未通过处理"), WAIT_SCRAP(AssetStatusEnum.WAIT_SCRAP.getCode(), "报废执行"),
                                              // 已入网的计算设备才有关联软件
                                              COMPUTER(AssetStatusEnum.NET_IN.getCode(), "关联软件");

    int    code;
    String msg;

    AvailableStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
