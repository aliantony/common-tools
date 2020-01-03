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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.intergration.SysUserClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.*;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.*;
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
import com.google.common.collect.ImmutableList;

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
    private BaseConverter<AssetLockRequest, AssetLock>                                  assetLockConverter;
    @Resource
    private AssetUserDao                                                        assetUserDao;
    @Resource
    private AssetGroupRelationDao                                               assetGroupRelationDao;
    @Resource
    private ExcelDownloadUtil                                                   excelDownloadUtil;
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

    @Override
    public ActionResponse saveAsset(AssetOuterRequest request) throws Exception {
        // 授权数量校验
        anthNumValidate();
        AssetRequest requestAsset = request.getAsset();
        AssetSafetyEquipmentRequest safetyEquipmentRequest = request.getSafetyEquipment();
        AssetNetworkEquipmentRequest networkEquipmentRequest = request.getNetworkEquipment();
        AssetStorageMediumRequest assetStorageMedium = request.getAssetStorageMedium();
        Long currentTimeMillis = System.currentTimeMillis();
        final String[] admittanceResult = new String[1];
        final String[] uuid = new String[1];
        String msg = null;
        Integer id = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    String aid;

                    String areaId = requestAsset.getAreaId();
                    String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class, areaId);
                    SysArea sysArea = redisUtil.getObject(key, SysArea.class);
                    BusinessExceptionUtils.isTrue(
                        !Objects.isNull(
                            assetUserDao.getById(DataTypeUtils.stringToInteger(requestAsset.getResponsibleUserId()))),
                        "使用者不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "当前区域不存在，或已经注销");
                    List<AssetGroupRequest> assetGroup = requestAsset.getAssetGroups();
                    Asset asset = requestConverter.convert(requestAsset, Asset.class);
                    AssetHardSoftLib byBusinessId = assetHardSoftLibDao.getByBusinessId(requestAsset.getBusinessId());
                    // BusinessExceptionUtils.isTrue(!Objects.isNull(byBusinessId), "当前厂商不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(byBusinessId.getStatus() == 1, "当前(厂商+名称+版本)不存在，或已经注销");
                    // 存入业务id,基准为空进入网,不为空 实施 ./检查
                    asset.setBusinessId(Long.valueOf(requestAsset.getBusinessId()));
                    if (StringUtils.isNotBlank(requestAsset.getBaselineTemplateId())) {
                        ActionResponse baselineTemplate = baseLineClient
                            .getBaselineTemplate(requestAsset.getBaselineTemplateId());
                        HashMap body = (HashMap) baselineTemplate.getBody();
                        Integer isEnable = (Integer) body.get("isEnable");
                        BusinessExceptionUtils.isTrue(isEnable == 1, "当前基准模板已经禁用!");
                        asset.setBaselineTemplateId(requestAsset.getBaselineTemplateId());
                        asset.setBaselineTemplateCorrelationGmt(System.currentTimeMillis());
                        admittanceResult[0] = (String) request.getManualStartActivityRequest().getFormData()
                            .get("admittanceResult");
                        asset.setAssetStatus(
                            "safetyCheck".equals(admittanceResult[0]) ? AssetStatusEnum.WAIT_CHECK.getCode()
                                : AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
                    } else {
                        asset.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
                        asset.setFirstEnterNett(currentTimeMillis);
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
                    assetDao.insert(asset);

                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetOperateLogEnum.REGISTER_ASSET.getName(), asset.getId(),
                        asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetOperateLogEnum.REGISTER_ASSET.getName() + " {}",
                        requestAsset.toString());

                    insertBatchAssetGroupRelation(asset, assetGroup);
                    // 返回的资产id
                    aid = asset.getStringId();
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
                        convert.forEach(assetAssembly -> {
                            BusinessExceptionUtils.isTrue(
                                assetAssemblyDao.findAssemblyByBusiness(assetAssembly.getBusinessId()) > 0,
                                "厂商:" + assetAssembly.getSupplier() + "  名称:" + assetAssembly
                                    .getProductName() + "的组件已经被移除!");
                            assetAssembly.setAssetId(aid);
                        });
                        assetAssemblyDao.insertBatch(convert);
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
                    assetOperationRecord.setTargetStatus(asset.getAssetStatus());
                    assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setContent(AssetFlowEnum.REGISTER.getMsg());
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
                // return 0;
            }
        });

        if (request.getManualStartActivityRequest() != null) {

            ManualStartActivityRequest activityRequest = request.getManualStartActivityRequest();
            activityRequest.setBusinessId(String.valueOf(id));
            activityRequest.setProcessDefinitionKey("assetAdmittance");
            activityRequest.setAssignee(LoginUserUtil.getLoginUser().getId() + "");
            ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                // 调用失败，逻辑删登记的资产
                assetDao.deleteById(id);
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
            // 安全检查
            BaselineAssetRegisterRequest baselineAssetRegisterRequest = new BaselineAssetRegisterRequest();
            baselineAssetRegisterRequest.setAssetId(id);
            baselineAssetRegisterRequest
                .setTemplateId(DataTypeUtils.stringToInteger(requestAsset.getBaselineTemplateId()));
            baselineAssetRegisterRequest.setCheckType("safetyCheck".equals(admittanceResult[0]) ? 2 : 1);
            baselineAssetRegisterRequest.setModifiedUser(LoginUserUtil.getLoginUser().getId());
            baselineAssetRegisterRequest.setOperator(LoginUserUtil.getLoginUser().getId());
            baselineAssetRegisterRequest.setCheckUser("safetyCheck".equals(admittanceResult[0])
                ? activityRequest.getFormData().get("safetyCheckUser").toString()
                : activityRequest.getFormData().get("templateImplementUser").toString());
            // 如果没得uuid 安全检查
            ActionResponse baselineCheck;

            if (StringUtils.isBlank(uuid[0]) && baselineAssetRegisterRequest.getCheckType() == 2) {
                baselineCheck = baseLineClient.baselineCheckNoUUID(baselineAssetRegisterRequest);
                // msg = requestAsset.getInstallType() == InstallType.AUTOMATIC.getCode() ? "无法获取到资产UUID，资产维护方式将默认:人工方式"
                // : "";
            } else {
                baselineCheck = baseLineClient.baselineCheck(baselineAssetRegisterRequest);
            }

            // 如果基准为空,直接返回错误信息
            if (null == baselineCheck
                || !RespBasicCode.SUCCESS.getResultCode().equals(baselineCheck.getHead().getCode())) {
                // 调用失败，逻辑删登记的资产
                assetDao.deleteById(id);
                return baselineCheck == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : baselineCheck;

            }

        }

        // 扫描
        ActionResponse scan = baseLineClient.scan(id.toString());
        // 如果漏洞为空,直接返回错误信息
        if (null == scan || !RespBasicCode.SUCCESS.getResultCode().equals(scan.getHead().getCode())) {
            // 调用失败，逻辑删登记的资产
            assetDao.deleteById(id);
            return scan == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : scan;
        }
        return ActionResponse.success(msg);
    }

    private void checkInstallTemplateCompliance(String templateId) throws Exception {
        if (StringUtils.isNotBlank(templateId)) {
            AssetInstallTemplate response = assetInstallTemplateDao.getById(templateId);
            //装机模板必须为启用
            if (Objects.isNull(response) || !response.getStatus().equals(1) || !response.getCurrentStatus().equals(AssetInstallTemplateStatusEnum.ENABLE.getCode())) {
                throw new RequestParamValidateException("装机模板不存在或未启用,请重新选择");
            }

        }
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
        if (CollectionUtils.isNotEmpty(assetGroup)) {
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
    public boolean CheckRepeatMAC(String mac, Integer id) throws Exception {
        Integer countIp = assetDao.findCountMac(mac, id);
        return countIp >= 1;
    }

    @Override
    public boolean CheckRepeatNumber(String number, Integer id) {
        return assetDao.findCountAssetNumber(number, id) >= 1;
    }

    @Override
    public List<AssetAssemblyResponse> getAssemblyInfo(QueryCondition condition) {
        return assemblyResponseBaseConverter.convert(assetDao.getAssemblyInfoById(condition.getPrimaryKey()),
            AssetAssemblyResponse.class);
    }

    // @Transactional(propagation = Propagation.NOT_SUPPORTED)
    // public boolean checkRepeatName(String name) throws Exception {
    // AssetQuery assetQuery = new AssetQuery();
    // assetQuery.setAssetName(name);
    // Integer countAsset = findCountAssetNumber(assetQuery);
    // return countAsset >= 1;
    // }

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

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
        // 是否未知资产列表查询
        if (query.getUnknownAssets()) {
            if (Objects.isNull(query.getAssetSource())) {
                List<Integer> sourceList = Lists.newArrayList();
                sourceList.add(AssetSourceEnum.ASSET_DETECTION.getCode());
                sourceList.add(AssetSourceEnum.AGENCY_REPORT.getCode());
                query.setAssetSourceList(sourceList);
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
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("asset");
        dealProcess(query, processMap);

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
    public ActionResponse dealAssetOperation(AssetLockRequest assetLockRequest)throws Exception {
        return jugeAssetStatus(Integer.valueOf(assetLockRequest.getAssetId()),assetLockRequest.getOperation());
        /*AssetLock assetLockConvert=new AssetLock();
        assetLockConvert.setAssetId(Integer.valueOf(assetLockRequest.getAssetId()));
        assetLockConvert.setUserId(LoginUserUtil.getLoginUser().getId());
        AssetLock assetLock=assetLockDao.getByAssetId(Integer.valueOf(assetLockRequest.getAssetId()));
        if(assetLock==null){
            assetLockDao.insert(assetLockConvert);
            return jugeAssetStatus(Integer.valueOf(assetLockRequest.getAssetId()),assetLockRequest.getOperation());
        }else if(LoginUserUtil.getLoginUser().getId().equals(assetLock.getUserId())){
            return jugeAssetStatus(Integer.valueOf(assetLockRequest.getAssetId()),assetLockRequest.getOperation());
        }else{
            return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION,"该任务已被他人认领");
        }*/
    }
    private ActionResponse jugeAssetStatus(Integer assetId,Integer operation) throws Exception {

        Asset asset = assetDao.getById(String.valueOf(assetId));
        switch (operation){
            case 1:
                if(AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())){
                    return  ActionResponse.success();
                }
                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION,getAssetStatusMsg(asset.getAssetStatus()));
            case 2:
                if(AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())
                        ||AssetStatusEnum.NOT_REGISTER.getCode().equals(asset.getAssetStatus())
                        ||AssetStatusEnum.RETIRE.getCode().equals(asset.getAssetStatus())){
                    return ActionResponse.success();
                }

                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION,getAssetStatusMsg(asset.getAssetStatus()));
            case 3:
                if(AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())){
                    return ActionResponse.success();
                }

                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION,getAssetStatusMsg(asset.getAssetStatus()));
            case 4:
                if(AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())){
                    return ActionResponse.success();
                }

                return ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION,getAssetStatusMsg(asset.getAssetStatus()));
            default:return ActionResponse.success();
        }
    }
    private String getAssetStatusMsg(Integer code){
        String message="资产已处于%s，无法重复提交！";
        AssetStatusEnum assetByCode = AssetStatusEnum.getAssetByCode(code);
        return String.format(message,assetByCode.getMsg());
    }
    private List<AssetEntity> getAssetEntities(ProcessTemplateRequest processTemplateRequest) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setTemplateList(processTemplateRequest.getIds());
        assetQuery.setPageSize(Constants.ALL_PAGE);
        assetQuery.setAreaIds(
            ArrayTypeUtil.objectArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().toArray()));
        List<AssetResponse> list = this.findPageAsset(assetQuery).getItems();
        return assetEntityConvert.convert(list, AssetEntity.class);
    }

    /**
     * 处理待办任务
     * ,
     */
    public void dealProcess(AssetQuery query, Map<String, WaitingTaskReponse> processMap) {
        // 只要是工作台进来的才去查询他的待办事项
        if (MapUtils.isNotEmpty(processMap)) {
            // 待办资产id
            List<String> waitRegistIds = new ArrayList<>();
            Set<String> activitiIds = null;
            // 如果是待登记,设置查询的ids-in为过滤后的信息
            if (CollectionUtils.isNotEmpty(query.getAssetStatusList()) && AssetStatusEnum.WAIT_REGISTER.getCode().equals(query.getAssetStatusList().get(0))) {
                // 查询所有待登记资产
                List<Integer> currentStatusAssetIds = assetDao.queryIdsByAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode(),
                        LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
                if (CollectionUtils.isNotEmpty(currentStatusAssetIds)) {
                    List<AssetOperationRecord> operationRecordList = operationRecordDao.listByAssetIds(currentStatusAssetIds);
                    // 实施到待登记
                    // 当前为资产登记的 有待办任务的资产Id
                    List<Integer> waitTaskIds = processMap.entrySet().stream()
                            .filter(e -> "资产登记".equals(e.getValue().getName())).map(e -> Integer.valueOf(e.getKey()))
                            .collect(Collectors.toList());
                    // 根据操作记录表.如果资产上一步是[实施拒绝或整改退回到待登记]&&该条资产不在[资产登记]assetRegister待办,就把这条资产id排除掉
                    operationRecordList.stream().forEach(operationRecord -> {
                        currentStatusAssetIds.removeIf(
                                e -> !waitTaskIds.contains(e)
                                        && (operationRecord.getOriginStatus().equals(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode())
                                        || operationRecord.getOriginStatus().equals(AssetStatusEnum.WAIT_CORRECT.getCode()))
                                        && e.equals(Integer.valueOf(operationRecord.getTargetObjectId())));
                    });
                    waitRegistIds = currentStatusAssetIds.stream().map(DataTypeUtils::integerToString)
                            .collect(Collectors.toList());
                }
                activitiIds = new HashSet<>(waitRegistIds);
            } else {
                activitiIds = processMap.keySet();
            }
            if (CollectionUtils.isNotEmpty(query.getAssetStatusList()) && CollectionUtils.isNotEmpty(activitiIds) && query.getEnterControl()) {
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
    public PageResult<AssetResponse> findUnconnectedAsset(AssetQuery query) {
        if (query.getAreaIds() == null || query.getAreaIds().length == 0) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
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
        int maxNum = 4;
        List<String> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 不统计已退役资产
        List<Integer> status = Arrays.asList(6, 10);
        // update by zhangbing 对于空的厂商和产品确认需要统计，统计的到其他
        List<Map<String, Object>> list = assetDao.countManufacturer(areaIds, status);
        return CountTypeUtil.getEnumCountResponse(maxNum, list);
    }

    @Override
    public List<EnumCountResponse> countStatus() {
        List<String> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
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
        List<String> areaIdsOfCurrentUser = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();

        // 不统计已退役资产
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
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

        if (Objects.equals(asset.getCategoryModel(), AssetCategoryEnum.NETWORK.getCode())) {
            List<AssetNetworkEquipment> assetNetworkEquipments = assetNetworkEquipmentDao.getByWhere(param);
            if (CollectionUtils.isNotEmpty(assetNetworkEquipments)) {
                AssetNetworkEquipmentResponse assetNetworkEquipmentResponse = networkResponseConverter
                    .convert(assetNetworkEquipments.get(0), AssetNetworkEquipmentResponse.class);
                assetOuterResponse.setAssetNetworkEquipment(assetNetworkEquipmentResponse);
            }
        }
        if (Objects.equals(asset.getCategoryModel(), AssetCategoryEnum.SAFETY.getCode())) {
            List<AssetSafetyEquipment> assetSafetyEquipments = assetSafetyEquipmentDao.getByWhere(param);
            if (CollectionUtils.isNotEmpty(assetSafetyEquipments)) {
                AssetSafetyEquipmentResponse assetSafetyEquipmentResponse = safetyResponseConverter
                    .convert(assetSafetyEquipments.get(0), AssetSafetyEquipmentResponse.class);
                assetOuterResponse.setAssetSafetyEquipment(assetSafetyEquipmentResponse);
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
        // 查询代办
        ActivityWaitingQuery activityWaitingQuery = new ActivityWaitingQuery();
        activityWaitingQuery.setUser(LoginUserUtil.getLoginUser().getStringId());
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
    public  ActionResponse changeAsset(AssetOuterRequest assetOuterRequest) throws Exception {
        if (LoginUserUtil.getLoginUser() == null) {
            throw new BusinessException("获取用户失败");
        }

        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset(), "资产信息不能为空");
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");
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
            HashSet macset = new HashSet(assetOuterRequest.getMacRelationRequests().stream()
                .map(AssetMacRelationRequest::getMac).collect(Collectors.toList()));
            ParamterExceptionUtils.isTrue(assetOuterRequest.getMacRelationRequests().size() == macset.size(),
                "mac不能重复");
            Integer mcount = assetMacRelationDao.checkRepeat(assetOuterRequest.getMacRelationRequests().stream()
                .map(AssetMacRelationRequest::getMac).collect(Collectors.toList()),
                assetOuterRequest.getAsset().getId());
            ParamterExceptionUtils.isTrue(mcount <= 0, "mac不能重复");
        }
        // 计算设备变成其他设备了, 删除代办
        // if (assetOuterRequest.getCancelWaitingTask() != null) {
        // List<String> processInstanceIds = new ArrayList<>(1);
        // processInstanceIds.add(assetOuterRequest.getCancelWaitingTask().getProcessInstanceId());
        // ActionResponse response = activityClient.deleteProcessInstance(processInstanceIds);
        // if (null == response || !response.getHead().getCode().equals(RespBasicCode.SUCCESS.getResultCode())) {
        // LogUtils.info(logger, "根据流程实例id集结束工作流出错: {}", processInstanceIds);
        // throw new BusinessException("调用流程引擎出错");
        // }
        // }

        final String[] admittanceResult = new String[1];
        String msg = "";
        final String[] reason = new String[1];
        final String[] uuid = new String[1];
        Asset asset = requestConverter.convert(assetOuterRequest.getAsset(), Asset.class);
        final boolean[] isNewAddAssembly = { false };

        Integer assetCount = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    if (!Objects.isNull(assetOuterRequest.getManualStartActivityRequest())
                        && AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                        reason[0] = getChangeContent(assetOuterRequest);
                    }
                    List<AssetGroupRequest> assetGroup = assetOuterRequest.getAsset().getAssetGroups();
                    // 处理资产组关系
                    asset.setAssetGroup(dealAssetGroup(assetOuterRequest.getAsset().getId(), assetGroup));
                    asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
                    asset.setGmtModified(System.currentTimeMillis());
                    if (asset.getOperationSystem() != null) {
                        HashMap<String, Object> param = new HashMap<>();
                        param.put("status", "1");
                        param.put("businessId", asset.getOperationSystem());
                        List<AssetCpeFilter> cpeFilters = assetCpeFilterDao.getByWhere(param);
                        asset.setOperationSystemName(cpeFilters.size() > 0 ? cpeFilters.get(0).getProductName() : null);
                    }
                    if (AssetStatusEnum.RETIRE.getCode().equals(asset.getAssetStatus())
                        || AssetStatusEnum.NOT_REGISTER.getCode().equals(asset.getAssetStatus())
                        || AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())) {
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

                    int count = assetDao.changeAsset(asset);
                    // 处理ip
                    dealIp(assetOuterRequest.getAsset().getId(), assetOuterRequest.getIpRelationRequests(),
                        asset.getCategoryModel());
                    // 处理mac
                    dealMac(assetOuterRequest.getAsset().getId(), assetOuterRequest.getMacRelationRequests());
                    // 如果是再登记不会传软件信息,这里不做处理
                    if (!Objects.isNull(assetOuterRequest.getSoftwareReportRequest())) {
                        // 处理软件
                        dealSoft(assetOuterRequest.getAsset().getId(), assetOuterRequest.getSoftwareReportRequest());
                    }
                    // 处理组件
                    isNewAddAssembly[0] = dealAssembly(assetOuterRequest.getAsset().getId(),
                        assetOuterRequest.getAssemblyRequestList());

                    // 2. 更新网络设备信息
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();
                    if (!Objects.isNull(networkEquipment)) {
                        AssetNetworkEquipment anp = BeanConvert.convertBean(networkEquipment,
                            AssetNetworkEquipment.class);
                        anp.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        anp.setGmtModified(System.currentTimeMillis());
                        anp.setAssetId(asset.getStringId());
                        assetNetworkEquipmentDao.update(anp);
                    }
                    // 3.更新安全设备信息
                    AssetSafetyEquipmentRequest safetyEquipmentRequest = assetOuterRequest.getSafetyEquipment();
                    if (!Objects.isNull(safetyEquipmentRequest)) {
                        AssetSafetyEquipment asp = BeanConvert.convertBean(safetyEquipmentRequest,
                            AssetSafetyEquipment.class);
                        asp.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        asp.setAssetId(asset.getStringId());
                        asp.setGmtModified(System.currentTimeMillis());
                        assetSafetyEquipmentDao.update(asp);
                        if (assetOuterRequest.getNeedScan()) {
                            // 漏洞扫描
                            ActionResponse scan = baseLineClient.scan(assetOuterRequest.getAsset().getId());
                            if (null == scan
                                || !RespBasicCode.SUCCESS.getResultCode().equals(scan.getHead().getCode())) {
                                BusinessExceptionUtils.isTrue(false, "调用漏洞模块出错");
                            }
                        }
                    }

                    // 4. 更新存储介质信息
                    AssetStorageMediumRequest storageMedium = assetOuterRequest.getAssetStorageMedium();
                    if (!Objects.isNull(storageMedium)) {
                        AssetStorageMedium assetStorageMedium = BeanConvert.convertBean(storageMedium,
                            AssetStorageMedium.class);
                        assetStorageMedium.setAssetId(assetOuterRequest.getAsset().getId());
                        assetStorageMedium.setGmtCreate(System.currentTimeMillis());
                        assetStorageMedium.setModifyUser(LoginUserUtil.getLoginUser().getId());
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

        // 记录资产操作流程
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(assetOuterRequest.getAsset().getId());
        assetOperationRecord.setOriginStatus(0);
        assetOperationRecord.setTargetStatus(asset.getAssetStatus());
        assetOperationRecord.setOperateUserId(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecord.setStatus(1);
        if (assetCount != null && assetCount > 0) {
            // 流程数据不为空,需启动流程
            String assetId = assetOuterRequest.getAsset().getId();
            Asset assetObj = assetDao.getById(assetId);
            uuid[0] = assetObj.getUuid();
            // 已入网后变更//未知资产/退役资产重新登记-f
            if (!Objects.isNull(assetOuterRequest.getManualStartActivityRequest())) {
                //计算机设备资产变更
                    if (AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())) {
                        String[] bids = assetOuterRequest.getManualStartActivityRequest().getFormData()
                            .get("baselineConfigUserId").toString().split(",");
                        StringBuilder sb = new StringBuilder();
                        Arrays.stream(bids).forEach(bid -> {
                            sb.append(aesEncoder.decode(bid, LoginUserUtil.getLoginUser().getUsername())).append(",");
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
                        baselineWaitingConfigRequest.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        baselineWaitingConfigRequest.setReason(reason[0]);
                        baselineWaitingConfigRequest.setSource(2);
                        baselineWaitingConfigRequest.setFormData(formData);
                        baselineWaitingConfigRequest.setBusinessId(assetId + "&1&" + assetId);
                        baselineWaitingConfigRequest.setAdvice(
                            (String) assetOuterRequest.getManualStartActivityRequest().getFormData().get("memo"));
                        baselineWaitingConfigRequestList.add(baselineWaitingConfigRequest);
                        ActionResponse actionResponse = baseLineClient.baselineConfig(baselineWaitingConfigRequestList);
                        if (null == actionResponse
                            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                            BusinessExceptionUtils.isTrue(false, "调用配置模块出错");
                        }

                        // ------------------对接配置模块------------------end
                        // 记录资产操作流程
                        assetOperationRecord.setTargetStatus(AssetStatusEnum.IN_CHANGE.getCode());
                        assetOperationRecord.setContent(AssetFlowEnum.CHANGE.getMsg());
                        assetOperationRecord.setProcessResult(null);
                        assetOperationRecord.setNeedVulScan(assetOuterRequest.getNeedScan() ? 1 : 0);
                        // 更新资产状态为变更中
                        updateAssetStatus(AssetStatusEnum.IN_CHANGE.getCode(), System.currentTimeMillis(), assetId);
                        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MODIFY.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_CHECK));
                        LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
                    } else {
                    //计算机设备再登记
                    if (StringUtils.isNotBlank(asset.getBaselineTemplateId())) {
                        asset.setBaselineTemplateId(asset.getBaselineTemplateId());
                        admittanceResult[0] = (String) assetOuterRequest.getManualStartActivityRequest().getFormData()
                            .get("admittanceResult");
                        // 记录操作流程的状态,更新资产状态
                        if ("safetyCheck".equals(admittanceResult[0])) {
                            updateAssetStatus(AssetStatusEnum.WAIT_CHECK.getCode(), System.currentTimeMillis(),
                                assetId);
                            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_CHECK.getCode());
                            assetOperationRecord.setContent(AssetFlowEnum.REGISTER.getMsg());
                        } else {
                            updateAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode(), System.currentTimeMillis(),
                                assetId);
                            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
                            assetOperationRecord.setContent(AssetFlowEnum.REGISTER.getMsg());
                        }
                    } else {
                        updateAssetStatus(AssetStatusEnum.NET_IN.getCode(), System.currentTimeMillis(), assetId);
                        assetOperationRecord.setTargetStatus(AssetStatusEnum.NET_IN.getCode());
                    }

                    ManualStartActivityRequest activityRequest = assetOuterRequest.getManualStartActivityRequest();
                    activityRequest.setBusinessId(assetId);
                    activityRequest.setProcessDefinitionKey("assetAdmittance");
                    activityRequest.setAssignee(LoginUserUtil.getLoginUser().getId() + "");

                    ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
                    String assetActivityInstanceId = (String) actionResponse.getBody();
                    // 如果流程引擎为空,直接返回错误信息
                    if (null == actionResponse
                        || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                        String message="已有正在运行中的流程，请勿重复启动";
                        if(message.equals(actionResponse.getBody())){
                            // 查询数据库
                            Asset daoById = assetDao.getById(asset.getStringId());
                            BusinessExceptionUtils.isTrue(false,
                                "资产已处于" + AssetStatusEnum.getAssetByCode(daoById.getAssetStatus()).getMsg()
                                                                 + "无法重复提交！");
                        }
                        LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + "流程引擎返回结果：{}",
                            JSON.toJSONString(actionResponse));
                        BusinessExceptionUtils.isTrue(false, "调用流程引擎出错");

                    }

                    // 安全检查 2 模板1

                    BaselineAssetRegisterRequest baselineAssetRegisterRequest = new BaselineAssetRegisterRequest();
                    baselineAssetRegisterRequest.setAssetId(DataTypeUtils.stringToInteger(assetId));
                    baselineAssetRegisterRequest
                        .setTemplateId(DataTypeUtils.stringToInteger(asset.getBaselineTemplateId()));
                    baselineAssetRegisterRequest.setCheckType("safetyCheck".equals(admittanceResult[0]) ? 2 : 1);
                    baselineAssetRegisterRequest.setModifiedUser(LoginUserUtil.getLoginUser().getId());
                    baselineAssetRegisterRequest.setOperator(LoginUserUtil.getLoginUser().getId());
                    baselineAssetRegisterRequest.setCheckUser("safetyCheck".equals(admittanceResult[0])
                        ? activityRequest.getFormData().get("safetyCheckUser").toString()
                        : activityRequest.getFormData().get("templateImplementUser").toString());
                    // 如果没得uuid 安全检查
                    ActionResponse baselineCheck;
                    if (StringUtils.isBlank(uuid[0]) && baselineAssetRegisterRequest.getCheckType() == 2) {
                        baselineCheck = baseLineClient.baselineCheckNoUUID(baselineAssetRegisterRequest);
                    } else {
                        baselineCheck = baseLineClient.baselineCheck(baselineAssetRegisterRequest);
                    }

                    // 如果基准为空,直接返回错误信息
                    if (null == baselineCheck
                        || !RespBasicCode.SUCCESS.getResultCode().equals(baselineCheck.getHead().getCode())) {
                        // 基准调用失败，删除启动的资产主流程
                        if (!Objects.isNull(assetActivityInstanceId)) {
                            activityClient.deleteProcessInstance(ImmutableList.of(assetActivityInstanceId));
                        }
                        // 调用失败，直接删登记的资
                        return baselineCheck == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION)
                            : baselineCheck;
                    }
                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetOperateLogEnum.REGISTER_ASSET.getName(),
                        Integer.valueOf(assetId), assetObj.getNumber(), assetOuterRequest,
                        BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}",
                        JSON.toJSONString(assetOuterRequest));
                    // 扫描
                    ActionResponse scan = baseLineClient
                        .scan(assetId);
                    // 如果漏洞为空,直接返回错误信息
                    if (null == scan || !RespBasicCode.SUCCESS.getResultCode().equals(scan.getHead().getCode())) {
                        // 调用失败，直接删登记的资产
                        return scan == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : scan;
                    }
                }
            }
            // 其他步骤打回的待登记-推动流程
            else if (!Objects.isNull(assetOuterRequest.getActivityHandleRequest())) {
                ActivityHandleRequest activityHandleRequest = assetOuterRequest.getActivityHandleRequest();
                String ar = (String) activityHandleRequest.getFormData().get("admittanceResult");
                // 下一步到安全检查
                if ("safetyCheck".equals(ar)) {
                    updateAssetStatus(AssetStatusEnum.WAIT_CHECK.getCode(), System.currentTimeMillis(), assetId);
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_CHECK.getCode());
                } else {
                    updateAssetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode(), System.currentTimeMillis(),
                        assetId);
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode());
                }
                //记录资产动态:“登记资产”操作文案
                assetOperationRecord.setContent(AssetFlowEnum.REGISTER.getMsg());
                ActionResponse actionResponse = activityClient.completeTask(activityHandleRequest);
                if (actionResponse == null
                    || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {

                    LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + "流程引擎返回结果：{}",
                        JSON.toJSONString(actionResponse));
                    BusinessExceptionUtils.isTrue(false, "调用流程引擎出错");
                }
                // 如果没得uuid 安全检查
                BaselineAssetRegisterRequest baselineAssetRegisterRequest = new BaselineAssetRegisterRequest();
                baselineAssetRegisterRequest.setAssetId(DataTypeUtils.stringToInteger(assetId));
                baselineAssetRegisterRequest
                    .setTemplateId(DataTypeUtils.stringToInteger(asset.getBaselineTemplateId()));
                baselineAssetRegisterRequest.setCheckType("safetyCheck".equals(ar) ? 2 : 1);
                baselineAssetRegisterRequest.setModifiedUser(LoginUserUtil.getLoginUser().getId());
                baselineAssetRegisterRequest.setOperator(LoginUserUtil.getLoginUser().getId());
                baselineAssetRegisterRequest.setCheckUser(
                    "safetyCheck".equals(ar) ? activityHandleRequest.getFormData().get("safetyCheckUser").toString()
                        : activityHandleRequest.getFormData().get("templateImplementUser").toString());
                ActionResponse baselineCheck;
                if (StringUtils.isBlank(uuid[0]) && baselineAssetRegisterRequest.getCheckType() == 2) {
                    baselineCheck = baseLineClient.baselineCheckNoUUID(baselineAssetRegisterRequest);
                } else {
                    baselineCheck = baseLineClient.baselineCheck(baselineAssetRegisterRequest);
                }
                // 如果基准为空,直接返回错误信息
                if (null == baselineCheck
                    || !RespBasicCode.SUCCESS.getResultCode().equals(baselineCheck.getHead().getCode())) {
                    // 调用失败，直接删登记的资产
                    return baselineCheck == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION)
                        : baselineCheck;
                }

                // 记录操作日志和运行日志
                LogUtils.recordOperLog(new BusinessData(AssetOperateLogEnum.REGISTER_ASSET.getName(),
                        Integer.valueOf(assetId), assetObj.getNumber(), assetOuterRequest,
                        BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}",
                        JSON.toJSONString(assetOuterRequest));

            } else {
                // 待登记多人 操作
                if (AssetStatusEnum.WAIT_REGISTER.getCode().equals(asset.getAssetStatus())) {
                    // 查询数据库
                    Asset daoById = assetDao.getById(asset.getStringId());
                    if (AssetStatusEnum.NET_IN.getCode().equals(daoById.getAssetStatus())) {
                        throw new BusinessException("资产已处于入网状态，无法重复提交！");
                    }
                }
                // 直接更改状态
                updateAssetStatus(AssetStatusEnum.NET_IN.getCode(), System.currentTimeMillis(), assetId);
                assetOperationRecord.setTargetStatus(AssetStatusEnum.NET_IN.getCode());


                if(AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())){
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MODIFY.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NET_IN));
                    LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
                    assetOperationRecord.setContent(AssetFlowEnum.CHANGE.getMsg());
                }else{
                    LogUtils.recordOperLog(new BusinessData(AssetOperateLogEnum.REGISTER_ASSET.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NET_IN));
                    LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
                    assetOperationRecord.setContent(AssetOperateLogEnum.REGISTER_ASSET.getName());
                }

                // 如果组件新增则启动漏扫
//                if (isNewAddAssembly[0]) {
//                    ActionResponse scan = baseLineClient
//                        .scan(assetId);
//                    // 如果漏洞为空,直接返回错误信息
//                    if (null == scan || !RespBasicCode.SUCCESS.getResultCode().equals(scan.getHead().getCode())) {
//                        throw new BusinessException("调用漏洞扫描出错");
//                    }
//                }

            }
        }
        assetOperationRecordDao.insert(assetOperationRecord);


        return ActionResponse.success(msg);
    }

    /**
     * 获取该资产变更的信息
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
        // 计算设备才有操作系统
        if (AssetCategoryEnum.COMPUTER.getCode().equals(assetRequest.getCategoryModel())) {
            String oldOs = assetDao.getByAssetId(assetId).getOperationSystemName();
            String newOs = assetOuterRequest.getAsset().getOperationSystemName();
            if (!StringUtils.equals(oldOs, newOs)) {
                update.append("$更改基础信息:").append("操作系统").append(newOs);
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
                });
            } else {
                List<String> oldDiskBusinessIds = oldDisks.stream().map(AssetAssemblyRequest::getBusinessId)
                    .collect(Collectors.toList());
                Map<String, Integer> map = oldDisks.stream()
                    .collect(Collectors.toMap(AssetAssemblyRequest::getBusinessId, AssetAssemblyRequest::getAmount));
                newDisks.stream().forEach(disk -> {
                    if (oldDiskBusinessIds.contains(disk.getBusinessId())) {
                        if (!disk.getAmount().equals(map.get(disk.getBusinessId()))) {
                            update.append("$更改组件:").append("硬盘").append(disk.getProductName()).append("数量")
                                .append(disk.getAmount());
                        }
                        oldDiskBusinessIds.removeIf(a -> a.contains(disk.getBusinessId()));
                    } else {
                        add.append("$新增组件:").append("硬盘").append(disk.getProductName());
                    }
                });
                if (CollectionUtils.isNotEmpty(oldDiskBusinessIds)) {
                    oldDiskBusinessIds.stream().forEach(os -> {
                        delete.append("$删除组件:").append("硬盘")
                            .append(oldDisks.stream().filter(v -> os.equals(v.getBusinessId()))
                                .map(AssetAssemblyRequest::getProductName).findFirst().get());
                    });
                }
            }
        } else {
            if (CollectionUtils.isNotEmpty(oldDisks)) {
                oldDisks.stream().forEach(disk -> {
                    sb.append("$删除组件:").append("硬盘").append(disk.getProductName());
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
                        add.append("$新增软件:")
                            .append(assetHardSoftLibDao.getByBusinessId(ns.toString()).getProductName());
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
                            add.append("$新增软件:")
                                .append(assetHardSoftLibDao.getByBusinessId(ns.toString()).getProductName());
                        } else {
                            oldSoftIds.removeIf(a -> a.contains(String.valueOf(ns)));
                        }
                    });
                    if (CollectionUtils.isNotEmpty(oldSoftIds)) {
                        oldSoftIds.stream().forEach(os -> {
                            delete.append("$删除软件:").append(oldMap.get(os));
                        });
                    }
                }
            } else {
                // 如果变更前存在软件，则软件全部被删除了
                if (CollectionUtils.isNotEmpty(assetSoftwareInstallResponseList)) {
                    assetSoftwareInstallResponseList.stream().forEach(asr -> {
                        delete.append("$删除软件:").append(asr.getProductName());
                    });
                }
            }
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
        if (categoryModel == 1 || categoryModel == 2) {
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

    public void dealSoft(String id, AssetSoftwareReportRequest softwareReportRequest) {
        // 1.先删除旧的关系表
        assetSoftwareRelationDao.deleteSoftRealtion(id, Lists.newArrayList());
        // 2.插入新的关系
        List<Long> softIds = softwareReportRequest.getSoftId();
        if (CollectionUtils.isNotEmpty(softIds)) {
            List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
            softIds.stream().forEach(softId -> {
                AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                assetSoftwareRelation.setAssetId(id);
                assetSoftwareRelation.setSoftwareId(softId);
                assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                assetSoftwareRelationList.add(assetSoftwareRelation);
            });
            assetSoftwareRelationDao.insertBatch(assetSoftwareRelationList);
        }
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
                AssetAssembly assetAssembly = new AssetAssembly();
                assetAssembly.setAssetId(id);
                assetAssembly.setBusinessId(assemblyRequest.getBusinessId());
                assetAssembly.setAmount(assemblyRequest.getAmount());
                assetAssemblyList.add(assetAssembly);
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
        otherDeviceEntity.setArea("四川省");
        otherDeviceEntity.setBuyDate(System.currentTimeMillis());
        otherDeviceEntity.setDueDate(System.currentTimeMillis());
        otherDeviceEntity.setUser("留小查");
        otherDeviceEntity.setSerial("ANFRWGDFETYRYF");
        otherDeviceEntity.setName("ar500");
        otherDeviceEntity.setMemo("宣传展览导视查询畅销触控一体机，采用FULL HD全视角高清IPS硬屏");
        otherDeviceEntity.setManufacturer("huawei");
        otherDeviceEntity.setWarranty("2年");
        otherDeviceEntity.setNumber("000001");
        otherDeviceEntity.setImportanceDegree("1");
        otherDeviceEntity.setIp("192.158.58.58");
        otherDeviceEntity.setMac("00-01-6C-06-A6-29");
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
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败，请不要删除示例，保持模版原样从第七行开始填写。",
            dictionary + "/", dataList);
    }

    private List<ComputeDeviceEntity> initComputeData() {
        List<ComputeDeviceEntity> dataList = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = new ComputeDeviceEntity();
        computeDeviceEntity.setArea("成都市");
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

            if (CheckRepeatNumber(entity.getNumber(), null)) {
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

            if (CheckRepeatMAC(entity.getMac(), null)) {
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

            // if (!checkOperatingSystem(entity.getOperationSystem())) {
            // error++;
            // a++;
            // builder.append("第").append(a).append("行").append("操作系统不存在，或已被注销！");
            // continue;
            // }

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
            ComputerVo computerVo = new ComputerVo();
            Asset asset = new Asset();
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("productName", entity.getOperationSystem());
            AssetCpeFilter assetCpeFilter = assetCpeFilterDao.getByWhere(stringObjectHashMap).get(0);
            asset.setOperationSystem(assetCpeFilter.getBusinessId());
            asset.setOperationSystemName(entity.getOperationSystem());
            asset.setResponsibleUserId(checkUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(areaId);
            asset.setIsInnet(0);
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
            if (CheckRepeatNumber(entity.getNumber(), null)) {
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

            if (CheckRepeatMAC(entity.getMac(), null)) {
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

            assetNumbers.add(entity.getNumber());
            assetMac.add(entity.getMac());
            Asset asset = new Asset();
            AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
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

            if (CheckRepeatNumber(entity.getNumber(), null)) {
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
            if (CheckRepeatMAC(entity.getMac(), null)) {
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
            // if (!checkOperatingSystem(entity.getOperationSystem())) {
            // error++;
            // a++;
            // builder.append("第").append(a).append("行").append("操作系统不存在，或已被注销！");
            // continue;
            // }

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
            if (StringUtils.isNotBlank(entity.getOperationSystem())) {
                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("productName", entity.getOperationSystem());
                AssetCpeFilter assetCpeFilter = assetCpeFilterDao.getByWhere(stringObjectHashMap).get(0);
                asset.setOperationSystem(assetCpeFilter.getBusinessId());
                asset.setOperationSystemName(entity.getOperationSystem());
            }
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

            if (CheckRepeatNumber(entity.getNumber(), null)) {
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
            asset.setResponsibleUserId(checkUser(entity.getUser()));
            AssetStorageMedium assetStorageMedium = new AssetStorageMedium();
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

            if (CheckRepeatNumber(entity.getNumber(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复！");
                continue;
            }
            if (CheckRepeatMAC(entity.getMac(), null)) {
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

    @Override
    @Transactional
    public Integer assetNoRegister(AssetStatusChangeRequest assetStatusChangeRequest) throws Exception {
        String[] assetId = assetStatusChangeRequest.getAssetId();
        if (assetId == null || assetId.length == 0) {
            return 0;
        }
        // 查询资产当前状态
        List<Asset> currentAssetList = assetDao.getAssetStatusListByIds(assetId);
        if (CollectionUtils.isEmpty(currentAssetList)) {
            throw new BusinessException("资产不存在");
        }
        for (Asset currentAsset : currentAssetList) {
            if (!(AssetStatusEnum.WAIT_REGISTER.getCode().equals(currentAsset.getAssetStatus()))) {
                AssetStatusEnum assetByCode = AssetStatusEnum.getAssetByCode(currentAsset.getAssetStatus());
                String assetStatus=assetByCode.getMsg();
                throw new BusinessException(String.format("资产已处于%s，无法重复提交！",assetStatus));
            }
        }
        List<Asset> assetList = new ArrayList<>(currentAssetList.size());
        for (Asset currentAsset : currentAssetList) {
            // 记录资产状态变更信息到操作记录表
            operationRecord(currentAsset.getId().toString());
            // 记录日志
            if (assetDao.getNumberById(currentAsset.getId().toString()) == null) {
                LogUtils.recordOperLog(
                    new BusinessData(AssetEventEnum.NO_REGISTER.getName(), currentAsset.getId(), currentAsset.getName(),
                        assetStatusChangeRequest, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NOT_REGISTER));
            } else {
                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.NO_REGISTER.getName(), currentAsset.getId(),
                    assetDao.getNumberById(currentAsset.getId().toString()), assetStatusChangeRequest,
                    BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NOT_REGISTER));
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

        LogUtils.info(logger, AssetEventEnum.NO_REGISTER.getName() + " {}", assetStatusChangeRequest);
        return assetId.length;
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
        // 如果会查询漏洞数量
        if (query.getQueryVulCount() != null && query.getQueryVulCount()) {
            count = assetDao.queryAllAssetVulCount(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());

        }

        // 如果会查询补丁数据
        if (query.getQueryPatchCount() != null && query.getQueryPatchCount()) {
            count = assetDao.queryAllAssetPatchCount(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());

        }
        // 如果会查询告警数量
        if (query.getQueryAlarmCount() != null && query.getQueryAlarmCount()) {
            if (ArrayUtils.isEmpty(query.getAreaIds())) {
                query.setAreaIds(ArrayTypeUtil
                    .objectArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().toArray()));
            }
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
        LoginUser loginUser=LoginUserUtil.getLoginUser();
        query.setModifyUser(loginUser==null?"0":loginUser.getStringId());
        return assetDao.updateAssetBaselineTemplate(query);
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
        operationRecord.setContent(AssetFlowEnum.NO_REGISTER.getMsg());
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

        downloadVO.setSheetName("资产信息表");

        if (CollectionUtils.isNotEmpty(downloadVO.getDownloadList())) {
            excelDownloadUtil.excelDownload(request, response,
                "资产" + DateUtils.getDataString(new Date(), DateUtils.NO_TIME_FORMAT), downloadVO);
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
        // 1.排除上一步实施拒绝退回到待登记的资产数量,
        // A登记 【资产1 】 B实施 【不通过】退到待登记。
        // A工作台【资产登记】待办+1
        // 其他用户工作台数量不变。
        // 2.排除整改退回到待登记的
        // 查询所有待登记资产
        List<Integer> waitRegisterIds = assetDao.queryIdsByAssetStatus(AssetStatusEnum.WAIT_REGISTER.getCode(),
            loginUser.getAreaIdsOfCurrentUser());
        if (CollectionUtils.isNotEmpty(waitRegisterIds)) {
            List<AssetOperationRecord> operationRecordList = operationRecordDao.listByAssetIds(waitRegisterIds);
            // 实施到待登记
            Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("asset");
            // 当前为资产登记的 有待办任务的资产Id
            List<Integer> waitTaskIds = processMap.entrySet().stream()
                .filter(e -> "资产登记".equals(e.getValue().getName())).map(e -> Integer.valueOf(e.getKey()))
                .collect(Collectors.toList());
            // 根据操作记录表.如果资产上一步是[实施拒绝或待整改]&&该条资产不在[资产登记]assetRegister待办,就把这条资产id排除掉
            operationRecordList.stream().forEach(operationRecord -> {
                waitRegisterIds.removeIf(
                    e -> !waitTaskIds.contains(e)
                         && (operationRecord.getOriginStatus().equals(AssetStatusEnum.WAIT_TEMPLATE_IMPL.getCode())
                            || operationRecord.getOriginStatus().equals(AssetStatusEnum.WAIT_CORRECT.getCode()))
                             && e.equals(Integer.valueOf(operationRecord.getTargetObjectId())));
            });
        }
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
