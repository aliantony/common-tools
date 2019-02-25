package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.*;
import com.antiy.asset.util.Constants;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.ActivityWaitingQuery;
import com.antiy.asset.vo.query.AssetDetialCondition;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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

    @Resource
    private AssetDao                                                           assetDao;
    @Resource
    private AssetMainboradDao                                                  assetMainboradDao;
    @Resource
    private AssetMemoryDao                                                     assetMemoryDao;
    @Resource
    private AssetHardDiskDao                                                   assetHardDiskDao;
    @Resource
    private AssetCpuDao                                                        assetCpuDao;
    @Resource
    private AssetNetworkCardDao                                                assetNetworkCardDao;
    @Resource
    private AssetNetworkEquipmentDao                                           assetNetworkEquipmentDao;
    @Resource
    private AssetSafetyEquipmentDao                                            assetSafetyEquipmentDao;
    @Resource
    private AssetSoftwareDao                                                   assetSoftwareDao;
    @Resource
    private AssetSoftwareLicenseDao                                            assetSoftwareLicenseDao;
    @Resource
    private AssetCategoryModelDao                                              assetCategoryModelDao;
    @Resource
    private TransactionTemplate                                                transactionTemplate;
    @Resource
    private AssetSoftwareRelationDao                                           assetSoftwareRelationDao;
    @Resource
    private AssetStorageMediumDao                                              assetStorageMediumDao;
    @Resource
    private AssetOperationRecordDao                                            assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>                                 requestConverter;
    @Resource
    private BaseConverter<Asset, AssetRequest>                                 assetToRequestConverter;
    @Resource
    private BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationRequest> softRelationToRequestConverter;
    @Resource
    private BaseConverter<AssetMainborad, AssetMainboradRequest>               mainboradToRequestConverter;
    @Resource
    private BaseConverter<AssetCpu, AssetCpuRequest>                           cpuToRequestConverter;
    @Resource
    private BaseConverter<AssetNetworkCard, AssetNetworkCardRequest>           networkCardToRequestConverter;
    @Resource
    private BaseConverter<AssetHardDisk, AssetHardDiskRequest>                 hardDiskToRequestConverter;
    @Resource
    private BaseConverter<AssetMemory, AssetMemoryRequest>                     memoryToRequestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse>                                responseConverter;
    @Resource
    private BaseConverter<Asset, ComputeDeviceEntity>                          entityConverter;
    @Resource
    private AssetUserDao                                                       assetUserDao;
    @Resource
    private AssetGroupRelationDao                                              assetGroupRelationDao;
    @Resource
    private AssetChangeRecordDao                                               assetChangeRecordDao;
    @Resource
    private ExcelDownloadUtil                                                  excelDownloadUtil;
    @Resource
    private AssetEntityConvert                                                 assetEntityConvert;
    @Resource
    private IAssetCategoryModelService                                         iAssetCategoryModelService;
    @Resource
    private AssetGroupDao                                                      assetGroupDao;
    @Resource
    private ActivityClient                                                     activityClient;
    private static final Logger                                                logger   = LogUtils
                                                                                            .get(AssetServiceImpl.class);
    @Resource
    private AesEncoder                                                         aesEncoder;

    @Override
    public ActionResponse saveAsset(AssetOuterRequest request) throws Exception {

        AssetRequest requestAsset = request.getAsset();
        Integer aid = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 记录资产登记信息到变更记录表
                    AssetOuterRequest assetOuterRequestToChangeRecord = new AssetOuterRequest();
                    String aid = "";
                    if (requestAsset != null) {
                        String number = requestAsset.getNumber();
                        if (CheckRepeat(number)) {
                            ParamterExceptionUtils.isTrue(false, "编号重复");
                        }
                        String name = requestAsset.getName();
                        if (CheckRepeatName(name)) {
                            ParamterExceptionUtils.isTrue(false, "资产名称重复");
                        }
                        List<AssetGroupRequest> assetGroup = requestAsset.getAssetGroups();
                        Asset asset = requestConverter.convert(requestAsset, Asset.class);
                        if (assetGroup != null && !assetGroup.isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            assetGroup.forEach(assetGroupRequest -> {
                                try {
                                    String assetGroupName = assetGroupDao.getById(
                                        DataTypeUtils.stringToInteger(assetGroupRequest.getId())).getName();
                                    asset.setAssetGroup(stringBuilder.append(assetGroupName).append(",")
                                        .substring(0, stringBuilder.length() - 1));
                                } catch (Exception e) {
                                    throw new BusinessException("资产组名称获取失败");
                                }
                            });
                        }

                        // asset.setAssetSource(2);
                        asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        asset.setGmtCreate(System.currentTimeMillis());
                        asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                        assetDao.insert(asset);
                        AssetRequest assetRequest = assetToRequestConverter.convert(asset, AssetRequest.class);
                        assetRequest.setId(DataTypeUtils.integerToString(asset.getId()));
                        assetOuterRequestToChangeRecord.setAsset(assetRequest);

                        LogHandle.log(requestAsset, AssetEventEnum.ASSET_INSERT.getName(),
                            AssetEventEnum.ASSET_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}", requestAsset.toString());

                        insertBatchAssetGroupRelation(asset, assetGroup);

                        aid = asset.getStringId();

                        AssetSafetyEquipmentRequest safetyEquipmentRequest = request.getSafetyEquipment();
                        if (safetyEquipmentRequest != null) {
                            AssetSafetyEquipment safetyEquipment = BeanConvert.convertBean(safetyEquipmentRequest,
                                AssetSafetyEquipment.class);
                            safetyEquipment.setAssetId(aid);
                            safetyEquipment.setGmtCreate(System.currentTimeMillis());
                            safetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(safetyEquipmentRequest, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName(),
                                AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName() + " {}",
                                safetyEquipmentRequest.toString());
                            assetSafetyEquipmentDao.insert(safetyEquipment);
                        }

                        AssetNetworkEquipmentRequest networkEquipmentRequest = request.getNetworkEquipment();
                        if (networkEquipmentRequest != null) {
                            AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(
                                networkEquipmentRequest, AssetNetworkEquipment.class);
                            assetNetworkEquipment.setAssetId(aid);
                            assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
                            assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(networkEquipmentRequest,
                                AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName(),
                                AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName() + " {}",
                                networkEquipmentRequest.toString());
                            assetNetworkEquipmentDao.insert(assetNetworkEquipment);
                        }
                        AssetStorageMediumRequest assetStorageMedium = request.getAssetStorageMedium();
                        if (assetStorageMedium != null) {
                            AssetStorageMedium medium = BeanConvert.convertBean(assetStorageMedium,
                                AssetStorageMedium.class);
                            medium.setAssetId(asset.getStringId());
                            medium.setGmtCreate(System.currentTimeMillis());
                            medium.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetStorageMedium, AssetEventEnum.ASSET_STORAGE_INSERT.getName(),
                                AssetEventEnum.ASSET_STORAGE_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum.ASSET_STORAGE_INSERT.getName() + " {}",
                                assetStorageMedium.toString());
                            assetStorageMediumDao.insert(medium);
                        }
                        // 软件关联表
                        List<AssetSoftwareRelationRequest> computerReques = request.getAssetSoftwareRelationList();
                        List<AssetSoftwareRelationRequest> softwareRelationRequestListToChangeRecord = new ArrayList<>();
                        assetOuterRequestToChangeRecord.setAssetSoftwareRelationList(computerReques);
                        if (computerReques != null && computerReques.size() > 0) {
                            for (AssetSoftwareRelationRequest computerReque : computerReques) {
                                AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                                assetSoftwareRelation.setAssetId(aid);
                                assetSoftwareRelation.setSoftwareId(computerReque.getSoftwareId());
                                assetSoftwareRelation.setPort(computerReque.getPort());
                                // assetSoftwareRelation.setProtocol(computerReque.getProtocol());
                                assetSoftwareRelation.setSoftwareStatus(3);
                                assetSoftwareRelation.setLicenseSecretKey(computerReque.getLicenseSecretKey());
                                assetSoftwareRelation.setMemo(computerReque.getMemo());
                                assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                                assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                // assetSoftwareRelation.setInstallType(computerReque.getInstallType());
                                assetSoftwareRelationDao.insert(assetSoftwareRelation);
                                AssetSoftwareRelationRequest assetSoftwareRelationRequest = softRelationToRequestConverter
                                    .convert(assetSoftwareRelation, AssetSoftwareRelationRequest.class);
                                assetSoftwareRelationRequest.setId(DataTypeUtils.integerToString(assetSoftwareRelation
                                    .getId()));
                                softwareRelationRequestListToChangeRecord.add(assetSoftwareRelationRequest);
                                // if (StringUtils.isNotBlank(computerReque.getLicenseSecretKey())) {
                                // AssetSoftwareLicense license = new AssetSoftwareLicense();
                                // license.setSoftwareId(assetSoftwareRelation.getId());
                                // license.setLicenseSecretKey(computerReque.getLicenseSecretKey());
                                // license.setGmtCreate(System.currentTimeMillis());
                                // license.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                // }
                            }
                            assetOuterRequestToChangeRecord
                                .setAssetSoftwareRelationList(softwareRelationRequestListToChangeRecord);
                        }

                        List<AssetNetworkCardRequest> networkCardRequestList = request.getNetworkCard();
                        List<AssetNetworkCardRequest> networkRequestListToChangeRecord = new ArrayList<>();
                        if (networkCardRequestList != null && networkCardRequestList.size() > 0) {
                            List<AssetNetworkCard> networkCardList = BeanConvert.convert(networkCardRequestList,
                                AssetNetworkCard.class);
                            for (AssetNetworkCard assetNetworkCard : networkCardList) {
                                ParamterExceptionUtils.isBlank(assetNetworkCard.getBrand(), "网卡品牌为空");
                                assetNetworkCard.setAssetId(aid);
                                assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                                assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetNetworkCard, AssetEventEnum.ASSET_NETWORK_INSERT.getName(),
                                    AssetEventEnum.ASSET_NETWORK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_INSERT.getName() + " {}",
                                    assetNetworkCard.toString());
                                assetNetworkCardDao.insert(assetNetworkCard);
                                AssetNetworkCardRequest assetNetworkCardRequest = networkCardToRequestConverter
                                    .convert(assetNetworkCard, AssetNetworkCardRequest.class);
                                assetNetworkCardRequest.setId(DataTypeUtils.integerToString(assetNetworkCard.getId()));
                                networkRequestListToChangeRecord.add(assetNetworkCardRequest);
                            }
                            assetOuterRequestToChangeRecord.setNetworkCard(networkRequestListToChangeRecord);
                        }
                        List<AssetMainboradRequest> mainboradRequestList = request.getMainboard();
                        List<AssetMainboradRequest> mainboardRequestListToChangeRecord = new ArrayList<>();
                        if (mainboradRequestList != null && mainboradRequestList.size() > 0) {
                            List<AssetMainborad> mainboradList = BeanConvert.convert(mainboradRequestList,
                                AssetMainborad.class);
                            for (AssetMainborad assetMainborad : mainboradList) {
                                ParamterExceptionUtils.isBlank(assetMainborad.getBrand(), "主板品牌为空");
                                assetMainborad.setAssetId(aid);
                                assetMainborad.setGmtCreate(System.currentTimeMillis());
                                assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetMainborad, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
                                    AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName() + " {}",
                                    assetMainborad.toString());
                                assetMainboradDao.insert(assetMainborad);
                                AssetMainboradRequest assetMainboradRequest = mainboradToRequestConverter.convert(
                                    assetMainborad, AssetMainboradRequest.class);
                                assetMainboradRequest.setId(DataTypeUtils.integerToString(assetMainborad.getId()));
                                mainboardRequestListToChangeRecord.add(assetMainboradRequest);
                            }
                            assetOuterRequestToChangeRecord.setMainboard(mainboardRequestListToChangeRecord);
                        }
                        List<AssetMemoryRequest> memoryRequestList = request.getMemory();
                        List<AssetMemoryRequest> memoryRequestListToChangeRecord = new ArrayList<>();
                        if (memoryRequestList != null && memoryRequestList.size() > 0) {
                            List<AssetMemory> memoryList = BeanConvert.convert(memoryRequestList, AssetMemory.class);
                            for (AssetMemory assetMemory : memoryList) {
                                ParamterExceptionUtils.isBlank(assetMemory.getBrand(), "内存品牌为空");
                                ParamterExceptionUtils.isNull(assetMemory.getFrequency(), "内存主频为空");
                                ParamterExceptionUtils.isNull(assetMemory.getCapacity(), "内存容量为空");
                                assetMemory.setAssetId(aid);
                                assetMemory.setGmtCreate(System.currentTimeMillis());
                                assetMemory.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetMemory, AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
                                    AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_INSERT.getName() + " {}",
                                    assetMemory.toString());
                                assetMemoryDao.insert(assetMemory);
                                AssetMemoryRequest assetMemoryRequest = memoryToRequestConverter.convert(assetMemory,
                                    AssetMemoryRequest.class);
                                assetMemoryRequest.setId(DataTypeUtils.integerToString(assetMemory.getId()));
                                memoryRequestListToChangeRecord.add(assetMemoryRequest);
                            }
                            assetOuterRequestToChangeRecord.setMemory(memoryRequestListToChangeRecord);
                        }
                        List<AssetCpuRequest> cpuRequestList = request.getCpu();
                        List<AssetCpuRequest> cpuRequestListToChangeRecord = new ArrayList<>();
                        if (cpuRequestList != null && cpuRequestList.size() > 0) {
                            List<AssetCpu> assetCpuList = BeanConvert.convert(cpuRequestList, AssetCpu.class);
                            for (AssetCpu assetCpu : assetCpuList) {
                                ParamterExceptionUtils.isBlank(assetCpu.getBrand(), "CPU品牌为空");
                                ParamterExceptionUtils.isNull(assetCpu.getMainFrequency(), "CPU主频为空");
                                assetCpu.setAssetId(aid);
                                assetCpu.setGmtCreate(System.currentTimeMillis());
                                assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetCpu, AssetEventEnum.ASSET_CPU_INSERT.getName(),
                                    AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.info(logger, AssetEventEnum.ASSET_CPU_INSERT.getName() + " {}",
                                    assetCpu.toString());
                                assetCpuDao.insert(assetCpu);
                                AssetCpuRequest assetCpuRequest = cpuToRequestConverter.convert(assetCpu,
                                    AssetCpuRequest.class);
                                assetCpuRequest.setId(DataTypeUtils.integerToString(assetCpu.getId()));
                                cpuRequestListToChangeRecord.add(assetCpuRequest);
                            }
                            assetOuterRequestToChangeRecord.setCpu(cpuRequestListToChangeRecord);
                        }
                        List<AssetHardDiskRequest> hardDisk = request.getHardDisk();
                        List<AssetHardDiskRequest> hardDiskRequestListToChangeRecord = new ArrayList<>();
                        if (hardDisk != null && hardDisk.size() > 0) {
                            List<AssetHardDisk> hardDisks = BeanConvert.convert(hardDisk, AssetHardDisk.class);
                            for (AssetHardDisk assetHardDisk : hardDisks) {
                                ParamterExceptionUtils.isBlank(assetHardDisk.getBrand(), "硬盘品牌为空");
                                ParamterExceptionUtils.isNull(assetHardDisk.getCapacity(), "硬盘容量空");
                                assetHardDisk.setAssetId(aid);
                                assetHardDisk.setGmtCreate(System.currentTimeMillis());
                                assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetHardDisk, AssetEventEnum.ASSET_DISK_INSERT.getName(),
                                    AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.info(logger, AssetEventEnum.ASSET_DISK_INSERT.getName() + " {}",
                                    assetHardDisk.toString());
                                assetHardDiskDao.insert(assetHardDisk);
                                AssetHardDiskRequest assetHardDiskRequest = hardDiskToRequestConverter.convert(
                                    assetHardDisk, AssetHardDiskRequest.class);
                                assetHardDiskRequest.setId(DataTypeUtils.integerToString(assetHardDisk.getId()));
                                hardDiskRequestListToChangeRecord.add(assetHardDiskRequest);
                            }
                            assetOuterRequestToChangeRecord.setHardDisk(hardDiskRequestListToChangeRecord);
                        }
                    } else {

                        AssetOthersRequest assetOthersRequest = request.getAssetOthersRequest();
                        String number = assetOthersRequest.getNumber();
                        if (CheckRepeat(number)) {
                            ParamterExceptionUtils.isTrue(false, "编号重复");
                        }
                        String name = assetOthersRequest.getName();
                        if (CheckRepeatName(name)) {
                            ParamterExceptionUtils.isTrue(false, "资产名称重复");
                        }
                        Asset asset1 = BeanConvert.convertBean(assetOthersRequest, Asset.class);

                        List<AssetGroupRequest> assetGroup = assetOthersRequest.getAssetGroups();

                        if (assetGroup != null && !assetGroup.isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            assetGroup.forEach(assetGroupRequest -> {
                                try {
                                    String assetGroupName = assetGroupDao.getById(
                                        DataTypeUtils.stringToInteger(assetGroupRequest.getId())).getName();
                                    asset1.setAssetGroup(stringBuilder.append(assetGroupName).append(",")
                                        .substring(0, stringBuilder.length() - 1));
                                } catch (Exception e) {
                                    throw new BusinessException("资产组名称获取失败");
                                }
                            });

                        }

                        asset1.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        asset1.setGmtCreate(System.currentTimeMillis());
                        asset1.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                        assetDao.insert(asset1);
                        aid = asset1.getStringId();

                        insertBatchAssetGroupRelation(asset1, assetGroup);

                        LogHandle.log(assetOthersRequest, AssetEventEnum.ASSET_OTHERS_INSERT.getName(),
                            AssetEventEnum.ASSET_OTHERS_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_OTHERS_INSERT.getName() + " {}",
                            assetOthersRequest.toString());
                    }
                    // 将资产副本存入变更记录表
                    AssetChangeRecord assetChangeRecord = new AssetChangeRecord();
                    assetChangeRecord.setBusinessId(DataTypeUtils.stringToInteger(assetOuterRequestToChangeRecord
                        .getAsset().getId()));
                    assetChangeRecord.setChangeVal(JsonUtil.object2Json(assetOuterRequestToChangeRecord));
                    assetChangeRecord.setGmtCreate(System.currentTimeMillis());
                    assetChangeRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetChangeRecordDao.insert(assetChangeRecord);

                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(aid);
                    assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    assetOperationRecord.setProcessResult(1);
                    assetOperationRecord.setContent("登记硬件资产");
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return Integer.parseInt(aid);
                } catch (RequestParamValidateException e) {
                    transactionStatus.setRollbackOnly();
                    if (e.getMessage().equals("编号重复")) {
                        ParamterExceptionUtils.isTrue(false, "编号重复");
                    } else {
                        ParamterExceptionUtils.isTrue(false, "资产名称重复");
                    }
                    logger.error("录入失败", e);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    e.printStackTrace();
                    logger.error("录入失败", e);
                }
                return 0;
            }
        });

        int i = aid;

        if (i > 0) {
            // 启动流程
            ManualStartActivityRequest activityRequest = request.getManualStartActivityRequest();
            activityRequest.setBusinessId(aid.toString());
            activityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
            // 如果流程引擎为空,直接返回错误信息
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }
        }

        return ActionResponse.success(aid);
    }

    private void insertBatchAssetGroupRelation(Asset asset1, List<AssetGroupRequest> assetGroup) {
        if (assetGroup != null && !assetGroup.isEmpty()) {
            List<AssetGroupRelation> groupRelations = new ArrayList<>();
            assetGroup
                .forEach(assetGroupRequest -> {
                    AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                    assetGroupRelation.setAssetGroupId(assetGroupRequest.getId());
                    assetGroupRelation.setAssetId(asset1.getStringId());
                    assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                    assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    groupRelations.add(assetGroupRelation);
                    LogHandle.log(assetGroupRequest, AssetEventEnum.ASSET_GROUP_INSERT.getName(),
                        AssetEventEnum.ASSET_GROUP_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_INSERT.getName() + " {}",
                        assetGroupRequest.toString());
                });
            assetGroupRelationDao.insertBatch(groupRelations);
        }
    }

    private boolean CheckRepeat(String number) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setNumber(number);
        Integer countAsset = findCountAssetNumber(assetQuery);
        if (countAsset >= 1) {
            return true;
        }
        return false;
    }

    private boolean CheckRepeatName(String name) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAssetName(name);
        Integer countAsset = findCountAssetNumber(assetQuery);
        if (countAsset >= 1) {
            return true;
        }
        return false;
    }

    String uid = null;

    private String CheckUser(String user) throws Exception {
        AssetUserQuery assetUserQuery = new AssetUserQuery();
        assetUserQuery.setExportName(user);
        List<AssetUser> assetUsers = assetUserDao.queryUserList(assetUserQuery);
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
    public List<AssetResponse> findListAsset(AssetQuery query) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser()
                .getAreaIdsOfCurrentUser()));
        }
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("hard");
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            query.setIds(processMap.keySet().toArray(new String[] {}));
        }
        List<Asset> asset = assetDao.findListAsset(query);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            objects.forEach(object -> {
                object.setWaitingTaskReponse(processMap.get(object.getStringId()));
            });
        }
        return objects;
    }

    public Integer findCountAsset(AssetQuery query) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser()
                .getAreaIdsOfCurrentUser()));
        }
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("hard");
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            query.setIds(processMap.keySet().toArray(new String[] {}));
        }
        return assetDao.findCount(query);
    }

    /**
     * 获取流程引擎数据，并且返回map对象
     * @return
     */
    public Map<String, WaitingTaskReponse> getAllHardWaitingTask(String definitionKeyType) {
        // 1.获取当前用户的所有代办任务
        ActivityWaitingQuery activityWaitingQuery = new ActivityWaitingQuery();
        activityWaitingQuery.setUser(aesEncoder.encode(LoginUserUtil.getLoginUser().getStringId(), LoginUserUtil
            .getLoginUser().getUsername()));
        activityWaitingQuery.setProcessDefinitionKey(definitionKeyType);
        ActionResponse<List<WaitingTaskReponse>> actionResponse = activityClient
            .queryAllWaitingTask(activityWaitingQuery);
        ParamterExceptionUtils.isTrue(
            actionResponse != null && RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode()),
            "获取工作流异常");
        List<WaitingTaskReponse> waitingTaskReponses = actionResponse.getBody();
        return waitingTaskReponses.stream()
            .filter(waitingTaskReponse -> StringUtils.isNotBlank(waitingTaskReponse.getBusinessId()))
            .collect(Collectors.toMap(WaitingTaskReponse::getBusinessId, Function.identity(), (key1, key2) -> key2));
    }

    public Integer findCountAssetNumber(AssetQuery query) throws Exception {
        return assetDao.findCount(query);
    }

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("hard");
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            query.setIds(processMap.keySet().toArray(new String[] {}));
        }

        return new PageResult<>(query.getPageSize(), this.findCountAsset(query), query.getCurrentPage(),
            this.findListAsset(query));
    }

    @Override
    public void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception {

    }

    // /*@Override
    // public void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception {
    // ParamterExceptionUtils.isEmpty(assetOuterRequestList, "上报数据为空");
    // Asset nasset = new Asset();
    // assetOuterRequestList.stream().forEach(assetOuterRequest -> {
    // try {
    // Asset asset = assetOuterRequest.getAsset();
    // if (asset == null) {
    // return;
    // }
    // List<AssetNetworkCard> networkCardList = assetOuterRequest.getNetworkCard();
    // if (networkCardList != null && !networkCardList.isEmpty()) {
    // List<String[]> ipMac = Lists.newArrayList();
    // for (AssetNetworkCard networkCard : networkCardList) {
    // ipMac.add(new String[]{networkCard.getIpAddress(), networkCard.getMacAddress()});
    // }
    // //资产判重，通过网卡IP地址和MAC地址判断
    // if (checkRepeatAsset(asset.getUuid(), ipMac)) {
    // return;
    // }
    // }
    // //资产主表信息
    // asset.setGmtCreate(System.currentTimeMillis());
    // //asset.setCreateUser();
    // assetDao.insert(asset);
    // nasset.setId(asset.getId());
    // //保存主板信息
    // AssetMainborad mainborad = assetOuterRequest.getMainboard();
    // if (mainborad != null) {
    // mainborad.setAssetId(asset.getId());
    // mainborad.setGmtCreate(System.currentTimeMillis());
    // assetMainboradDao.insert(mainborad);
    // }
    // //保存内存信息
    // List<AssetMemory> memoryList = assetOuterRequest.getMemory();
    // if (memoryList != null && !memoryList.isEmpty()) {
    // JSONArray jsonArray = new JSONArray();
    // for (AssetMemory memory : memoryList) {
    // memory.setAssetId(asset.getId());
    // memory.setGmtCreate(System.currentTimeMillis());
    // assetMemoryDao.insert(memory);
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("id", memory.getId());
    // jsonObject.put("capacity", memory.getCapacity());
    // jsonObject.put("frequency", memory.getFrequency());
    // jsonArray.add(jsonObject);
    // }
    // nasset.setMemory(jsonArray.toJSONString());
    // }
    // //保存硬盘信息
    // List<AssetHardDisk> hardDiskList = assetOuterRequest.getHardDisk();
    // if (hardDiskList != null && !hardDiskList.isEmpty()) {
    // JSONArray jsonArray = new JSONArray();
    // for (AssetHardDisk hardDisk : hardDiskList) {
    // hardDisk.setAssetId(asset.getId());
    // hardDisk.setGmtCreate(System.currentTimeMillis());
    // assetHardDiskDao.insert(hardDisk);
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("id", hardDisk.getId());
    // jsonObject.put("capacity", hardDisk.getCapacity());
    // jsonObject.put("brand", hardDisk.getBrand());
    // jsonObject.put("model", hardDisk.getModel());
    // jsonArray.add(jsonObject);
    // }
    // nasset.setHardDisk(jsonArray.toJSONString());
    // }
    // //保存CPU信息
    // List<AssetCpu> cpuList = assetOuterRequest.getCpu();
    // if (cpuList != null && cpuList.size() > 0) {
    // JSONArray jsonArray = new JSONArray();
    // for (AssetCpu cpu : cpuList) {
    // cpu.setAssetId(asset.getId());
    // cpu.setGmtCreate(System.currentTimeMillis());
    // assetCpuDao.insert(cpu);
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("id", cpu.getId());
    // jsonObject.put("brand", cpu.getBrand());
    // jsonObject.put("model", cpu.getModel());
    // jsonObject.put("core_size", cpu.getCoreSize());
    // jsonObject.put("thread_size", cpu.getThreadSize());
    // jsonArray.add(jsonObject);
    // }
    // nasset.setCpu(jsonArray.toJSONString());
    // }
    // //保存网卡信息
    // if (networkCardList != null && !networkCardList.isEmpty()) {
    // JSONArray jsonArray = new JSONArray();
    // for (AssetNetworkCard networkCard : networkCardList) {
    // networkCard.setAssetId(asset.getId());
    // networkCard.setGmtCreate(System.currentTimeMillis());
    // assetNetworkCardDao.insert(networkCard);
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("id", networkCard.getId());
    // jsonObject.put("brand", networkCard.getBrand());
    // jsonObject.put("model", networkCard.getModel());
    // jsonObject.put("ip_address", networkCard.getIpAddress());
    // jsonObject.put("mac_address", networkCard.getMacAddress());
    // jsonArray.add(jsonObject);
    // }
    // nasset.setNetworkCard(jsonArray.toJSONString());
    // }
    // //网络设备
    // AssetNetworkEquipment networkEquipment = assetOuterRequest.getNetworkEquipment();
    // if (networkEquipment != null) {
    // networkEquipment.setAssetId(asset.getId());
    // networkEquipment.setGmtCreate(System.currentTimeMillis());
    // assetNetworkEquipmentDao.insert(networkEquipment);
    // }
    // //安全设备
    // AssetSafetyEquipment safetyEquipment = assetOuterRequest.getSafetyEquipment();
    // if (safetyEquipment != null) {
    // safetyEquipment.setAssetId(asset.getId());
    // safetyEquipment.setGmtCreate(System.currentTimeMillis());
    // assetSafetyEquipmentDao.insert(safetyEquipment);
    // }
    // //软件
    // List<AssetSoftware> softwareList = assetOuterRequest.getSoftware();
    // if (softwareList != null && !softwareList.isEmpty()) {
    // for (AssetSoftware software : softwareList) {
    // //判断软件是否已经存在
    // if(assetSoftwareDao.checkRepeatSoftware(software) <= 0){
    // software.setGmtCreate(System.currentTimeMillis());
    // assetSoftwareDao.insert(software);
    // //资产软件关系表
    // AssetSoftwareRelation softwareRelation = new AssetSoftwareRelation();
    // softwareRelation.setAssetId(asset.getId());
    // softwareRelation.setSoftwareId(software.getId());
    // softwareRelation.setGmtCreate(System.currentTimeMillis());
    // assetSoftwareRelationDao.insert(softwareRelation);
    // }
    // }
    // }
    // //保存资产硬件的JSON数据
    // assetDao.update(nasset);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // });
    // }*/

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
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Integer changeStatus(String[] ids, Integer targetStatus) throws Exception {
        int row;
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("assetStatus", new String[] { targetStatus.toString() });
        map.put("gmtModified", LoginUserUtil.getLoginUser().getId());
        map.put("modifyUser", System.currentTimeMillis());
        row = assetDao.changeStatus(map);
        return row;
    }

    @Override
    public Integer changeStatusById(String id, Integer targetStatus) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", new String[] { id });
        map.put("assetStatus", new String[] { targetStatus.toString() });
        map.put("gmtModified", LoginUserUtil.getLoginUser().getId());
        map.put("modifyUser", System.currentTimeMillis());
        return assetDao.changeStatus(map);
    }

    // public Integer saveAllAsset(HashMap<String, Object> map) throws Exception {
    // Asset asset = new Asset ();
    // AssetCpu assetCpu = new AssetCpu();
    // AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
    // AssetHardDisk assetHardDisk = new AssetHardDisk();
    // AssetMainborad assetMainborad = new AssetMainborad();
    // AssetMemory assetMemory = new AssetMemory();
    // asset.setNumber((String) map.get("number"));
    // asset.setType((Integer) map.get("type"));
    // asset.setCategory((Integer) map.get("category"));
    // asset.setModel((Integer) map.get("model"));
    // asset.setResponsibleUserId((Integer) map.get("responsibleUserId"));
    // asset.setSystemBit((Integer) map.get("systemBit"));
    // asset.setImportanceDegree((Integer) map.get("importanceDegree"));
    // asset.setParentId((Integer) map.get("parentId"));
    // asset.setCreateUser((Integer) map.get("createUser"));
    // //3-待配置
    // asset.setAssetStatus(3);
    // // 2-人工上报
    // asset.setAssetSource(2);
    // asset.setName((String) map.get("name"));
    // asset.setSerial((String) map.get("serial"));
    // asset.setManufacturer((String) map.get("manufacturer"));
    // asset.setOperationSystem((String) map.get("operationSystem"));
    // asset.setLocation((String) map.get("location"));
    // asset.setLatitude((String) map.get("latitude"));
    // asset.setLongitude((String) map.get("longitude"));
    // asset.setFirmwareVersion((String) map.get("firmwareVersion"));
    // asset.setUuid((String) map.get("uuid"));
    // asset.setContactTel((String) map.get("contactTel"));
    // asset.setEmail((String) map.get("email"));
    // asset.setHardDisk((String) map.get("hardDisk"));
    // asset.setMemory((String) map.get("memory"));
    // asset.setDescrible((String) map.get("describle"));
    // asset.setCpu((String) map.get("cpu"));
    // asset.setNetworkCard((String) map.get("networkCard"));
    // asset.setTags((String) map.get("tags"));
    // asset.setMemo((String) map.get("memo"));
    // asset.setInnet(true);
    // asset.setFirstEnterNett((Long) map.get("firstEnterNett"));
    // asset.setServiceLife((Long) map.get("serviceLife"));
    // asset.setBuyDate((Long) map.get("buyDate"));
    // asset.setWarranty((Long) map.get("warranty"));
    // asset.setGmtCreate(System.currentTimeMillis());
    //
    // Integer aid = assetDao.insert (asset);
    //
    // assetCpu.setAssetId(aid);
    // assetCpu.setSerial((String) map.get("cserial"));
    // assetCpu.setBrand((String) map.get("cbrand"));
    // assetCpu.setModel((String) map.get("cmodel"));
    // assetCpu.setMainFrequency((Float) map.get("mainFrequency"));
    // assetCpu.setThreadSize((Integer) map.get("threadSize"));
    // assetCpu.setCoreSize((Integer) map.get("coreSize"));
    // assetCpu.setGmtCreate(System.currentTimeMillis());
    // assetCpu.setMemo((String) map.get("memo"));
    // assetCpu.setCreateUser((Integer) map.get("createUser"));
    // Integer cid = assetCpuDao.insert(assetCpu);
    //
    // assetNetworkCard.setAssetId(aid);
    // assetNetworkCard.setBrand((String) map.get("nbrand"));
    // assetNetworkCard.setSerial((String) map.get("nserial"));
    // assetNetworkCard.setModel((String) map.get("nmodel"));
    // assetNetworkCard.setIpAddress((String) map.get("ipAddress"));
    // assetNetworkCard.setMacAddress((String) map.get("macAddress"));
    // assetNetworkCard.setDefaultGateway((String) map.get("defaultGateway"));
    // assetNetworkCard.setNetwordAddress((String) map.get("networdAddress"));
    // assetNetworkCard.setSubnetMask((String) map.get("subnetMask"));
    // assetNetworkCard.setGmtCreate(System.currentTimeMillis());
    // assetNetworkCard.setMemo((String) map.get("memo"));
    // assetNetworkCard.setCreateUser((Integer) map.get("createUser"));
    // Integer nid = assetNetworkCardDao.insert(assetNetworkCard);
    //
    // assetHardDisk.setAssetId(aid);
    // assetHardDisk.setSerial((String) map.get("hserial"));
    // assetHardDisk.setBrand((String) map.get("hbrand"));
    // assetHardDisk.setModel((String) map.get("hmodel"));
    // assetHardDisk.setInterfaceType((Integer) map.get("interfaceType"));
    // assetHardDisk.setCapacity((Integer) map.get("hcapacity"));
    // assetHardDisk.setDiskType((Integer) map.get("diskType"));
    // assetHardDisk.setBuyDate((Long) map.get("hbuyDate"));
    // assetHardDisk.setGmtCreate(System.currentTimeMillis());
    // assetHardDisk.setMemo((String) map.get("memo"));
    // assetHardDisk.setCreateUser((Integer) map.get("createUser"));
    // Integer hid = assetHardDiskDao.insert(assetHardDisk);
    //
    // assetMainborad.setAssetId(aid);
    // assetMainborad.setSerial((String) map.get("mserial"));
    // assetMainborad.setBrand((String) map.get("mbrand"));
    // assetMainborad.setModel((String) map.get("mmodel"));
    // assetMainborad.setBiosVersion((String) map.get("biosVersion"));
    // assetMainborad.setBiosDate((Long) map.get("biosDate"));
    // assetMainborad.setGmtCreate(System.currentTimeMillis());
    // assetMainborad.setMemo((String) map.get("memo"));
    // assetMainborad.setCreateUser((Integer) map.get("createUser"));
    // Integer mid = assetMainboradDao.insert(assetMainborad);
    //
    // assetMemory.setAssetId(aid);
    // assetMemory.setCapacity((Integer) map.get("mecapacity"));
    // assetMemory.setFrequency((Integer) map.get("mefrequency"));
    // assetMemory.setSlotType((Integer) map.get("meslotType"));
    // assetMemory.setStitch((Integer) map.get("mestitch"));
    // assetMemory.setHeatsink(true);
    // assetMemory.setBuyDate ((Long) map.get("hbuyDate"));
    //
    // return null;
    // }

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
    public AssetCountResponse countManufacturer() throws Exception {
        int maxNum = 5;
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        List<Map<String, Long>> list = assetDao.countManufacturer(areaIds, status);
        return CountTypeUtil.getAssetCountResponse(maxNum, list);
    }

    @Override
    public AssetCountColumnarResponse countStatus() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> searchResult = assetDao.countStatus(ids);
        Map<String, Long> result = new HashMap();
        // 初始化result
        for (AssetStatusEnum assetStatusEnum : AssetStatusEnum.values()) {
            result.put(assetStatusEnum.getMsg(), 0L);
        }
        // 将查询结果的值放入结果集
        for (Map map : searchResult) {
            AssetStatusEnum assetStatusEnum = AssetStatusEnum.getAssetByCode((Integer) map.get("key"));
            if (assetStatusEnum != null) {
                result.put(assetStatusEnum.getMsg(), (Long) map.get("value"));
            }
        }
        return CountTypeUtil.getAssetCountColumnarResponse(result);
    }

    @Override
    public AssetCountResponse countCategory() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 查询第二级分类id
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        List<AssetCategoryModel> categoryModelDaoAll = assetCategoryModelDao.getAll();
        if (CollectionUtils.isNotEmpty(secondCategoryModelList)) {
            HashMap<String, Long> result = new HashMap<>();
            for (AssetCategoryModel secondCategoryModel : secondCategoryModelList) {
                // 查询第二级每个分类下所有的分类id，并添加至list集合
                List<AssetCategoryModel> search = iAssetCategoryModelService.recursionSearch(categoryModelDaoAll,
                    secondCategoryModel.getId());
                // 设置查询资产条件参数，包括区域id，状态，资产品类型号
                AssetQuery assetQuery = setAssetQueryParam(areaIds, search);
                // 将查询结果放置结果集
                result.put(secondCategoryModel.getName(), (long) assetDao.findCountByCategoryModel(assetQuery));
            }
            return CountTypeUtil.getAssetCountResponse(result);
        }
        return null;
    }

    private AssetQuery setAssetQueryParam(List<Integer> areaIds, List<AssetCategoryModel> search) {
        List<Integer> list = new ArrayList<>();
        search.stream().forEach(x -> list.add(x.getId()));
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setCategoryModels(ArrayTypeUtil.ObjectArrayToStringArray(list.toArray()));
        assetQuery.setAreaIds(ArrayTypeUtil.ObjectArrayToStringArray(areaIds.toArray()));
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        assetQuery.setAssetStatusList(status);
        return assetQuery;
    }

    // @Override
    // public String importPc(MultipartFile file) throws Exception {
    // String filePath = System.getProperty("user.dir") + File.separator + "antiy" + File.separator + "ExcelFiles";
    // InputStream in = null;
    // OutputStream out = null;
    // File dir = new File(filePath);
    // if (!dir.exists())
    // {
    // dir.mkdirs();
    // }
    // File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
    // in = file.getInputStream();
    // out = new FileOutputStream (serverFile);
    // byte[] b = new byte[1024];
    // int len = 0;
    // while ((len = in.read(b)) > 0)
    // {
    // out.write(b, 0, len);
    // }
    // out.close();
    // in.close();
    // List<Map<String, Object>> maps = null;
    // for (Map<String, Object> map : maps) {
    // Asset asset = new Asset ();
    // if (StringUtils.isBlank (map.get ("名称").toString ())||StringUtils.isBlank (map.get ("使用者工号").toString ())){
    // continue;
    // }
    //
    //
    // //TODO 查询人员ID
    // asset.setResponsibleUserId (1);
    // asset.setManufacturer (map.get("厂商") == null?null:map.get("厂商").toString());
    // asset.setName (map.get("名称") == null?null:map.get("名称").toString());
    // asset.setSerial (map.get("序列号") == null?null:map.get("序列号").toString());
    // asset.setLocation (map.get("物理位置") == null?null:map.get("物理位置").toString());
    // asset.setHouseLocation (map.get("机房位置") == null?null:map.get("机房位置").toString());
    // asset.setFirmwareVersion (map.get("固件版本") == null?null:map.get("固件版本").toString());
    // asset.setOperationSystem (map.get("操作系统") == null?null:map.get("操作系统").toString());
    // asset.setGmtCreate(System.currentTimeMillis());
    //
    // if (map.get("重要程度") != null)
    // {
    // switch (map.get("重要程度").toString())
    // {
    // case "不重要":
    // asset.setImportanceDegree (1);
    // break;
    // case "一般":
    // asset.setImportanceDegree (2);
    // break;
    // case "重要":
    // asset.setImportanceDegree (3);
    // break;
    // }
    // }
    // //TODO 确认品类
    // asset.setCategoryModel(1);
    // asset.setAreaId ("1");
    // // TODO: 2019/1/15 插入调用流程
    // asset.setAssetStatus (3);
    // //人工
    // asset.setAssetSource (2);
    // assetDao.insert (asset);
    // Integer id = asset.getId ();
    //
    // if (StringUtils.isNotBlank (map.get("内存品牌").toString ())){
    // AssetMemory assetMemory = new AssetMemory ();
    // assetMemory.setAssetId (id);
    // assetMemory.setGmtCreate (System.currentTimeMillis ());
    // assetMemory.setSerial (map.get("内存序列号") == null?null:map.get("内存序列号").toString());
    // assetMemory.setBrand (map.get("内存品牌") == null?null:map.get("内存品牌").toString());
    // assetMemory.setCapacity (map.get("内存容量") == null?null:Integer.parseInt (map.get("内存容量").toString()));
    // assetMemory.setFrequency (map.get("内存频率") == null?null:Double.parseDouble (map.get("内存频率").toString()));
    // assetMemory.setCapacity (map.get("内存容量") == null?null:Integer.parseInt (map.get("内存容量").toString()));
    // assetMemory.setStitch (map.get("内存针脚数") == null?null:Integer.parseInt (map.get("内存针脚数").toString()));
    // if (map.get("内存是否带散热") != null){
    // assetMemory.setHeatsink (map.get("内存是否带散热") .equals ("是")?true:false);
    // }
    //
    // if (map.get("内存插槽类型") != null)
    // {
    // switch (map.get("内存插槽类型").toString())
    // {
    // case "SDRAM":
    // assetMemory.setSlotType (0);
    // break;
    // case "SIMM":
    // assetMemory.setSlotType (1);
    // break;
    // case "DIMM":
    // assetMemory.setSlotType (2);
    // break;
    // case "RIMM":
    // assetMemory.setSlotType (3);
    // break;
    // }
    // }
    // if (map.get("内存数量") != null) {
    // int num = Integer.parseInt (map.get ("内存数量").toString ());
    // for (int i = 0; i <num; i++) {
    // assetMemoryDao.insert (assetMemory);
    //
    // }
    // }
    //
    // }
    //
    //
    //
    // if (StringUtils.isNotBlank (map.get("硬盘品牌").toString ())){
    // AssetHardDisk assetHardDisk = new AssetHardDisk ();
    // assetHardDisk.setAssetId (id);
    // assetHardDisk.setGmtCreate (System.currentTimeMillis ());
    // assetHardDisk.setSerial (map.get("硬盘序列号") == null?null:map.get("硬盘序列号").toString());
    // assetHardDisk.setBrand (map.get("硬盘品牌") == null?null:map.get("硬盘品牌").toString());
    // assetHardDisk.setModel (map.get("硬盘型号") == null?null:map.get("硬盘型号").toString());
    // assetHardDisk.setCapacity (map.get ("硬盘容量") == null ? null : Integer.parseInt (map.get ("硬盘容量").toString ()));
    //
    // assetHardDisk.setBuyDate (map.get ("硬盘购买日期") == null ? null : DateUtils.getDateFormat (map.get
    // ("硬盘购买日期").toString (),"yyyy-MM-dd HH:mm:ss").getTime ());
    //
    // if (map.get("硬盘接口类型") != null)
    // {
    // switch (map.get("硬盘接口类型").toString())
    // {
    // case "SATA":
    // assetHardDisk.setInterfaceType (1);
    // break;
    // case "IDE":
    // assetHardDisk.setInterfaceType (2);
    // break;
    // case "ATA":
    // assetHardDisk.setInterfaceType (3);
    // break;
    // case "SCSI":
    // assetHardDisk.setInterfaceType (4);
    // break;
    // case "光纤":
    // assetHardDisk.setInterfaceType (5);
    // break;
    // }
    // }
    // if (map.get("硬盘磁盘类型") != null)
    // {
    // switch (map.get("硬盘磁盘类型").toString())
    // {
    // case "HDD":
    // assetHardDisk.setInterfaceType (1);
    // break;
    // case "SSD":
    // assetHardDisk.setInterfaceType (2);
    // break;
    // }
    // }
    //
    // if (map.get("硬盘数量") != null) {
    // int num = Integer.parseInt (map.get ("硬盘数量").toString ());
    // for (int i = 0; i <num; i++) {
    // assetHardDiskDao.insert (assetHardDisk);
    //
    // }
    // }
    //
    // }
    //
    //
    // if (StringUtils.isNotBlank (map.get("主板品牌").toString ())){
    // AssetMainborad assetMainborad = new AssetMainborad ();
    // assetMainborad.setAssetId (id);
    // assetMainborad.setGmtCreate (System.currentTimeMillis ());
    // assetMainborad.setSerial (map.get("主板序列号") == null?null:map.get("主板序列号").toString());
    // assetMainborad.setBrand (map.get("主板品牌") == null?null:map.get("主板品牌").toString());
    // assetMainborad.setModel (map.get("主板型号") == null?null:map.get("主板型号").toString());
    // assetMainborad.setBiosVersion (map.get("主板BIOS版本") == null?null:map.get("主板BIOS版本").toString());
    // assetMainborad.setBiosDate (map.get ("主板日期") == null ? null : DateUtils.getDateFormat (map.get ("主板日期").toString
    // (),"yyyy-MM-dd HH:mm:ss").getTime ());
    // if (map.get("主板数量") != null) {
    // int num = Integer.parseInt (map.get ("硬盘数量").toString ());
    // for (int i = 0; i <num; i++) {
    // assetMainboradDao.insert (assetMainborad);
    //
    // }
    // }
    //
    // }
    // if (StringUtils.isNotBlank (map.get("CPU品牌").toString ())){
    // AssetCpu assetCpu = new AssetCpu ();
    // assetCpu.setAssetId (id);
    // assetCpu.setGmtCreate (System.currentTimeMillis ());
    // assetCpu.setSerial (map.get("CPU序列号") == null?null:map.get("CPU序列号").toString());
    // assetCpu.setBrand (map.get("CPU品牌") == null?null:map.get("CPU品牌").toString());
    // assetCpu.setModel (map.get("CPU型号") == null?null:map.get("CPU型号").toString());
    // assetCpu.setMainFrequency (map.get("CPU主频") == null?null:Float.parseFloat (map.get("CPU主频").toString()));
    // assetCpu.setThreadSize (map.get("CPU线程数") == null?null:Integer.parseInt (map.get("CPU线程数").toString()));
    // assetCpu.setCoreSize (map.get("CPU核心数") == null?null:Integer.parseInt (map.get("CPU核心数").toString()));
    // if (map.get("CPU数量") != null) {
    // int num = Integer.parseInt (map.get ("硬盘数量").toString ());
    // for (int i = 0; i <num; i++) {
    // assetCpuDao.insert (assetCpu);
    //
    // }
    // }
    // }
    // if (StringUtils.isNotBlank (map.get("网卡品牌").toString ())){
    // AssetNetworkCard assetNetworkCard = new AssetNetworkCard ();
    // assetNetworkCard.setAssetId (id);
    // assetNetworkCard.setGmtCreate (System.currentTimeMillis ());
    // assetNetworkCard.setSerial (map.get("网卡序列号") == null?null:map.get("网卡序列号").toString());
    // assetNetworkCard.setBrand (map.get("网卡品牌") == null?null:map.get("网卡品牌").toString());
    // assetNetworkCard.setModel (map.get("网卡型号") == null?null:map.get("网卡型号").toString());
    // assetNetworkCard.setSubnetMask (map.get("网卡子网掩码") == null?null:map.get("网卡子网掩码").toString());
    // assetNetworkCard.setDefaultGateway (map.get("网卡默认网关") == null?null:map.get("网卡默认网关").toString());
    //
    // String ip = map.get ("网卡ip地址") == null ? null : map.get ("网卡ip地址").toString ();
    // String mac = map.get ("网卡MAC地址") == null ? null : map.get ("网卡MAC地址").toString ();
    // String[] ips = ip.split (",");
    // String[] macs = mac.split (",");
    // if (map.get("网卡数量") != null) {
    // if (ips.length==macs.length&&macs.length==Integer.parseInt (map.get("网卡数量").toString () )){
    // int num = Integer.parseInt (map.get ("网卡数量").toString ());
    // for (int i = 0; i <num; i++) {
    // assetNetworkCard.setMacAddress (macs[i]);
    // assetNetworkCard.setIpAddress (ips[i]);
    // assetNetworkCardDao.insert (assetNetworkCard);
    //
    // }
    // }
    //
    // }
    //
    // }
    //
    // }
    // return "导入完成";
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
        // 查询资产组
        param.put("assetId", asset.getId());
        AssetResponse assetResponse = BeanConvert.convertBean(asset, AssetResponse.class);
        assetResponse.setAssetGroups(BeanConvert.convert(assetGroupRelationDao.queryByAssetId(asset.getId()),
            AssetGroupResponse.class));
        assetOuterResponse.setAsset(assetResponse);

        // CPU
        if (condition.getIsNeedCpu()) {
            assetOuterResponse.setAssetCpu(BeanConvert.convert(assetCpuDao.getByWhere(param), AssetCpuResponse.class));
        }
        // 网卡
        if (condition.getIsNeedNetwork()) {
            assetOuterResponse.setAssetNetworkCard(BeanConvert.convert(assetNetworkCardDao.getByWhere(param),
                AssetNetworkCardResponse.class));
        }
        // 硬盘
        if (condition.getIsNeedHarddisk()) {
            assetOuterResponse.setAssetHardDisk(BeanConvert.convert(assetHardDiskDao.getByWhere(param),
                AssetHardDiskResponse.class));
        }
        // 主板
        if (condition.getIsNeedMainboard()) {
            assetOuterResponse.setAssetMainborad(BeanConvert.convert(assetMainboradDao.getByWhere(param),
                AssetMainboradResponse.class));
        }
        // 内存
        if (condition.getIsNeedMemory()) {
            assetOuterResponse.setAssetMemory(BeanConvert.convert(assetMemoryDao.getByWhere(param),
                AssetMemoryResponse.class));
        }
        // 网络设备
        List<AssetNetworkEquipment> assetNetworkEquipmentList = assetNetworkEquipmentDao.getByWhere(param);
        if (CollectionUtils.isNotEmpty(assetNetworkEquipmentList)) {
            assetOuterResponse.setAssetNetworkEquipment(BeanConvert.convertBean(assetNetworkEquipmentList.get(0),
                AssetNetworkEquipmentResponse.class));
        }
        // 安全设备
        List<AssetSafetyEquipment> assetSafetyEquipmentList = assetSafetyEquipmentDao.getByWhere(param);
        if (CollectionUtils.isNotEmpty(assetSafetyEquipmentList)) {
            assetOuterResponse.setAssetSafetyEquipment(BeanConvert.convertBean(assetSafetyEquipmentList.get(0),
                AssetSafetyEquipmentResponse.class));
        }
        // 存储介质
        List<AssetStorageMedium> assetStorageMediumList = assetStorageMediumDao.getByWhere(param);
        if (CollectionUtils.isNotEmpty(assetStorageMediumList)) {
            assetOuterResponse.setAssetStorageMedium(BeanConvert.convertBean(assetStorageMediumList.get(0),
                AssetStorageMediumResponse.class));
        }
        // 软件列表
        if (condition.getIsNeedSoftware()) {
            List<AssetSoftware> assetSoftwareList = assetSoftwareRelationDao.getSoftByAssetId(DataTypeUtils
                .stringToInteger(condition.getPrimaryKey()));
            assetOuterResponse.setAssetSoftware(BeanConvert.convert(assetSoftwareList, AssetSoftwareResponse.class));

            // 资产软件关系列表
            List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao
                .getReleationByAssetId(asset.getId());
            assetOuterResponse.setAssetSoftwareRelationList(BeanConvert.convert(assetSoftwareRelationList,
                AssetSoftwareRelationResponse.class));
        }
        return assetOuterResponse;
    }

    @Override
    public Integer changeAsset(AssetOuterRequest assetOuterRequest) throws Exception {
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset(), "资产信息不能为空");
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");
        Asset asset = BeanConvert.convertBean(assetOuterRequest.getAsset(), Asset.class);
        Integer assetCount = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    List<AssetGroupRequest> assetGroup = assetOuterRequest.getAsset().getAssetGroups();
                    if (assetGroup != null && !assetGroup.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        assetGroup.forEach(assetGroupRequest -> {
                            try {
                                String assetGroupName = assetGroupDao.getById(
                                    DataTypeUtils.stringToInteger(assetGroupRequest.getId())).getName();
                                // asset.setAssetGroup(stringBuilder.append(assetGroupName).append(",").substring(0,
                            asset.setAssetGroup(stringBuilder.append(assetGroupName).substring(0,
                                stringBuilder.length() - 1));
                        } catch (Exception e) {
                            throw new BusinessException("资产组名称获取失败");
                        }
                    })  ;
                        asset.setAssetGroup(stringBuilder.toString());
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    List<AssetGroupRequest> assetGroups = assetOuterRequest.getAsset().getAssetGroups();
                    List<AssetGroupRelation> assetGroupRelations = Lists.newArrayList();
                    for (AssetGroupRequest assetGroupRequest : assetGroups) {
                        stringBuffer.append(assetGroupRequest.getName()).append(",");
                        AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                        assetGroupRelation.setAssetGroupId(assetGroupRequest.getId());
                        assetGroupRelation.setAssetId(asset.getStringId());
                        assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                        assetGroupRelations.add(assetGroupRelation);
                    }
                    assetGroupRelationDao.deleteByAssetId(asset.getId());
                    if (!assetGroupRelations.isEmpty()) {
                        assetGroupRelationDao.insertBatch(assetGroupRelations);
                    }
                    // asset.setAssetGroup(stringBuffer.toString());
                    asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
                    asset.setGmtModified(System.currentTimeMillis());
                    // 1. 更新资产主表
                    LogHandle.log(asset, AssetEventEnum.ASSET_MODIFY.getName(),
                        AssetEventEnum.ASSET_MODIFY.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
                    int count = assetDao.update(asset);

                    // 2. 更新cpu信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_CPU_DELETE.getName(),
                        AssetEventEnum.ASSET_CPU_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_CPU_DELETE.getName() + " {}", asset.getStringId());
                    assetCpuDao.deleteByAssetId(asset.getId());
                    List<AssetCpuRequest> assetCpuRequestList = assetOuterRequest.getCpu();
                    if (CollectionUtils.isNotEmpty(assetCpuRequestList)) {
                        List<AssetCpu> assetCpuList = BeanConvert.convert(assetCpuRequestList, AssetCpu.class);
                        for (AssetCpu assetCpu : assetCpuList) {
                            // 设置资产id，可能是新增的
                            assetCpu.setAssetId(asset.getStringId());
                            assetCpu.setGmtCreate(System.currentTimeMillis());
                            assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            assetCpu.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetCpu.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetCpuList, AssetEventEnum.ASSET_CPU_INSERT.getName(),
                            AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_CPU_INSERT.getName() + " {}",
                            assetCpuList.toString());
                        assetCpuDao.insertBatch(assetCpuList);
                    }
                    // 3. 更新网卡信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_NETWORK_DELETE.getName(),
                        AssetEventEnum.ASSET_NETWORK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DELETE.getName() + " {}", asset.getStringId());
                    assetNetworkCardDao.deleteByAssetId(asset.getId());
                    List<AssetNetworkCardRequest> assetNetworkCardRequestList = assetOuterRequest.getNetworkCard();
                    if (CollectionUtils.isNotEmpty(assetNetworkCardRequestList)) {
                        List<AssetNetworkCard> assetNetworkCardList = BeanConvert.convert(assetNetworkCardRequestList,
                            AssetNetworkCard.class);
                        for (AssetNetworkCard assetNetworkCard : assetNetworkCardList) {
                            // 设置资产id，可能是新增的
                            assetNetworkCard.setAssetId(asset.getStringId());
                            assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                            assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            assetNetworkCard.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetNetworkCard.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetNetworkCardList, AssetEventEnum.ASSET_NETWORK_INSERT.getName(),
                            AssetEventEnum.ASSET_NETWORK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_INSERT.getName() + " {}",
                            assetNetworkCardList.toString());
                        assetNetworkCardDao.insertBatch(assetNetworkCardList);
                    }
                    // 4. 更新主板信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_MAINBORAD_DELETE.getName(),
                        AssetEventEnum.ASSET_MAINBORAD_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_DELETE.getName() + " {}", asset.getStringId());
                    assetMainboradDao.deleteByAssetId(asset.getId());
                    List<AssetMainboradRequest> assetMainboradRequest = assetOuterRequest.getMainboard();
                    if (CollectionUtils.isNotEmpty(assetMainboradRequest)) {
                        List<AssetMainborad> assetMainborad = BeanConvert.convert(assetMainboradRequest,
                            AssetMainborad.class);
                        for (AssetMainborad mainborad : assetMainborad) {
                            mainborad.setAssetId(asset.getStringId());
                            mainborad.setGmtCreate(System.currentTimeMillis());
                            mainborad.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            mainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            mainborad.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetMainborad, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
                            AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName() + " {}",
                            assetMainborad.toString());
                        assetMainboradDao.insertBatch(assetMainborad);
                    }
                    // 5. 更新内存信息
                    List<AssetMemoryRequest> assetMemoryRequestList = assetOuterRequest.getMemory();
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_MEMORY_DELETE.getName(),
                        AssetEventEnum.ASSET_MEMORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_DELETE.getName() + " {}", asset.getStringId());
                    assetMemoryDao.deleteByAssetId(asset.getId());
                    if (CollectionUtils.isNotEmpty(assetMemoryRequestList)) {
                        List<AssetMemory> assetMemoryList = BeanConvert.convert(assetMemoryRequestList,
                            AssetMemory.class);
                        for (AssetMemory assetMemory : assetMemoryList) {
                            // 设置资产id，可能是新增的
                            assetMemory.setAssetId(asset.getStringId());
                            // asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetMemory.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetMemoryList, AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
                            AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_INSERT.getName() + " {}",
                            assetMemoryList.toString());
                        assetMemoryDao.insertBatch(assetMemoryList);
                    }
                    // 6. 更新硬盘信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_DISK_DELETE.getName(),
                        AssetEventEnum.ASSET_DISK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_DISK_DELETE.getName() + " {}", asset.getStringId());
                    assetHardDiskDao.deleteByAssetId(asset.getId());
                    List<AssetHardDiskRequest> assetHardDiskRequestList = assetOuterRequest.getHardDisk();
                    if (CollectionUtils.isNotEmpty(assetHardDiskRequestList)) {
                        List<AssetHardDisk> assetHardDiskList = BeanConvert.convert(assetHardDiskRequestList,
                            AssetHardDisk.class);
                        for (AssetHardDisk assetHardDisk : assetHardDiskList) {
                            // 设置资产id，可能是新增的
                            assetHardDisk.setAssetId(asset.getStringId());
                            assetHardDisk.setGmtCreate(System.currentTimeMillis());
                            assetHardDisk.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            assetHardDisk.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetHardDiskList, AssetEventEnum.ASSET_DISK_INSERT.getName(),
                            AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_DISK_INSERT.getName() + " {}",
                            assetHardDiskList.toString());
                        assetHardDiskDao.insertBatch(assetHardDiskList);
                    }
                    // 7. 更新网络设备信息
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getName(),
                        AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getName() + " {}",
                        asset.getStringId());
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();
                    if (networkEquipment != null && StringUtils.isNotBlank(networkEquipment.getId())) {
                        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipment,
                            AssetNetworkEquipment.class);
                        assetNetworkEquipment.setAssetId(asset.getStringId());
                        assetNetworkEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
                        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        assetNetworkEquipment.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetNetworkEquipment, AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getName(),
                            AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getName() + " {}",
                            assetNetworkEquipment.toString());
                        assetNetworkEquipmentDao.update(assetNetworkEquipment);
                    }
                    // 8. 更新安全设备信息
                    AssetSafetyEquipmentRequest safetyEquipment = assetOuterRequest.getSafetyEquipment();
                    if (safetyEquipment != null && StringUtils.isNotBlank(safetyEquipment.getId())) {
                        AssetSafetyEquipment assetSafetyEquipment = BeanConvert.convertBean(safetyEquipment,
                            AssetSafetyEquipment.class);
                        assetSafetyEquipment.setAssetId(asset.getStringId());
                        assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
                        assetSafetyEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetSafetyEquipment.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetSafetyEquipment, AssetEventEnum.ASSET_SAFE_DETAIL_UPDATE.getName(),
                            AssetEventEnum.ASSET_SAFE_DETAIL_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_SAFE_DETAIL_UPDATE.getName() + " {}",
                            assetSafetyEquipment.toString());
                        assetSafetyEquipmentDao.update(assetSafetyEquipment);
                    }
                    // 9. 更新存储介质信息
                    AssetStorageMediumRequest storageMedium = assetOuterRequest.getAssetStorageMedium();
                    if (storageMedium != null && StringUtils.isNotBlank(storageMedium.getId())) {
                        AssetStorageMedium assetStorageMedium = BeanConvert.convertBean(storageMedium,
                            AssetStorageMedium.class);
                        assetStorageMedium.setAssetId(asset.getStringId());
                        assetStorageMedium.setGmtCreate(System.currentTimeMillis());
                        assetStorageMedium.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetStorageMedium.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetStorageMedium, AssetEventEnum.ASSET_STORAGE_UPDATE.getName(),
                            AssetEventEnum.ASSET_STORAGE_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum.ASSET_STORAGE_UPDATE.getName() + " {}",
                            assetStorageMedium.toString());
                        assetStorageMediumDao.update(assetStorageMedium);
                    }
                    // 10. 更新资产软件关系信息
                    // 删除已有资产软件关系
                    assetSoftwareRelationDao.deleteByAssetId(asset.getId());
                    // 删除软件许可
                    assetSoftwareLicenseDao.deleteByAssetId(asset.getId());
                    if (CollectionUtils.isNotEmpty(assetOuterRequest.getAssetSoftwareRelationList())) {
                        List<AssetSoftwareRelation> assetSoftwareRelationList = BeanConvert.convert(
                            assetOuterRequest.getAssetSoftwareRelationList(), AssetSoftwareRelation.class);
                        assetSoftwareRelationList.stream().forEach(relation -> {
                            relation.setAssetId(asset.getStringId());
                            relation.setGmtCreate(System.currentTimeMillis());
                            // relation.setSoftwareStatus();
                            relation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            try {
                                // 插入资产软件关系
                                assetSoftwareRelationDao.insert(relation);
                                AssetSoftwareLicense assetSoftwareLicense = new AssetSoftwareLicense();
                                assetSoftwareLicense.setLicenseSecretKey(relation.getLicenseSecretKey());
                                assetSoftwareLicense.setSoftwareId(relation.getStringId());
                                // 插入资产软件许可
                                assetSoftwareLicenseDao.insert(assetSoftwareLicense);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(asset.getStringId());
                    assetOperationRecord.setOriginStatus(asset.getStatus() == null ? AssetStatusEnum.WATI_REGSIST
                        .getCode() : asset.getStatus());
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    assetOperationRecord.setContent(AssetEventEnum.ASSET_MODIFY.getName());
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecord.setProcessResult(1);
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return count;

                } catch (Exception e) {
                    logger.info("资产变更失败:", e);
                    transactionStatus.setRollbackOnly();
                    throw new BusinessException("资产变更失败");
                }
            }
        });
        /* // 状态变更 AssetStatusReqeust assetStatusReqeust = new AssetStatusReqeust();
         * assetStatusReqeust.setAssetStatus(AssetStatusEnum.NET_IN);
         * assetStatusReqeust.setAssetId(asset.getStringId()); assetStatusReqeust.setAgree(true);
         * assetStatusReqeust.setSoftware(false);
         * assetStatusReqeust.setAssetFlowCategoryEnum(AssetFlowCategoryEnum.HARDWARE_CHANGE);
         * assetStatusReqeust.setManualStartActivityRequest(assetOuterRequest.getActivityRequest());
         * AssetStatusChangeFactory.getStatusChangeProcess(AssetStatusChangeProcessImpl.class)
         * .changeStatus(assetStatusReqeust); */
        // TODO 下发智甲

        // 通知工作流
        // ManualStartActivityRequest manualStartActivityRequest = assetOuterRequest.getActivityRequest();
        // if (Objects.isNull(manualStartActivityRequest)) {
        // manualStartActivityRequest = new ManualStartActivityRequest();
        // }
        // manualStartActivityRequest.setBusinessId(asset.getStringId());
        // manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
        // manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_CHANGE.getCode());
        // activityClient.manualStartProcess(manualStartActivityRequest);
        return assetCount;
    }

    /**
     * 导出后的文件格式为.zip压缩包
     * @param types 导出模板的类型
     */
    @Override
    public void exportTemplate(Integer[] types) throws Exception {
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        Map<String, Class> map = initMap();
        // 根据时间戳创建文件夹，防止产生冲突
        Long currentTime = System.currentTimeMillis();
        // 创建临时文件夹
        String dictionary = "/temp" + currentTime + "/模板" + currentTime;
        File dictionaryFile = new File(dictionary);
        if (!dictionaryFile.exists()) {
            dictionaryFile.mkdirs();
        }
        // 创造模板文件
        File[] files = new File[types.length];
        // 创造压缩文件
        File zip = new File("/temp" + currentTime + "/模板.zip");
        Map<Integer, AssetCategoryModel> categoryModelMap = new HashMap<>();
        categoryModelList.forEach(x -> categoryModelMap.put(x.getId(), x));
        int m = 0;
        for (Integer type : types) {
            AssetCategoryModel assetCategoryModel = categoryModelMap.get(type);
            if (Objects.nonNull(assetCategoryModel)) {
                // 生成模板文件
                String categoryName = assetCategoryModel.getName();
                ExcelUtils.exportTemplet(map.get(assetCategoryModel.getName()), categoryName + "信息模板.xlsx",
                    categoryName, dictionary + "/");
                files[m++] = new File(dictionary + "/" + categoryName + "信息模板.xlsx");
            } else {
                // 输入参数有错，删除临时文件
                for (File fil : files) {
                    if (Objects.nonNull(fil)) {
                        loggerIsDelete(fil);
                    }
                }
                loggerIsDelete(dictionaryFile);
                loggerIsDelete(dictionaryFile.getParentFile());
                throw new BusinessException("存在错误的品类ID");
            }
        }
        zip.createNewFile();
        // 压缩文件为zip压缩包
        ZipUtil.compress(zip, files);
        // 将文件流发送到客户端
        sendStreamToClient(zip);
        // 记录临时文件删除是否成功
        loggerIsDelete(zip);
        for (File fil : files) {
            loggerIsDelete(fil);
        }
        loggerIsDelete(dictionaryFile);
        loggerIsDelete(dictionaryFile.getParentFile());

    }

    private void sendStreamToClient(File file) {
        FileInputStream fileInputStream = null;
        OutputStream ous = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
            response.reset();
            response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
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

    private Map<String, Class> initMap() {
        Map<String, Class> map = new HashMap();
        map.put("计算设备", ComputeDeviceEntity.class);
        map.put("网络设备", NetworkDeviceEntity.class);
        map.put("安全设备", SafetyEquipmentEntiy.class);
        map.put("存储设备", StorageDeviceEntity.class);
        map.put("其他设备", OtherDeviceEntity.class);
        return map;
    }

    void loggerIsDelete(File file) {
        logger.info(file.getName() + "文件删除" + isDeleteSuccess(file.delete()));
    }

    String isDeleteSuccess(Boolean isDelete) {
        return isDelete ? "成功" : "失败";
    }

    @Override
    public void exportData(AssetQuery assetQuery, HttpServletResponse response) throws Exception {
        exportData(ComputeDeviceEntity.class, "资产信息表.xlsx", assetQuery, response);
    }

    @Override
    public String importPc(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        ImportResult<ComputeDeviceEntity> result = ExcelUtils.importExcelFromClient(ComputeDeviceEntity.class, file, 0,
            0);
        int success = 0;
        int repeat = 0;
        int error = 0;
        String user = null;
        StringBuilder builder = new StringBuilder();
        List<ComputeDeviceEntity> dataList = result.getDataList();
        for (ComputeDeviceEntity entity : dataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产名称为空");
                continue;
            }
            if (StringUtils.isBlank(entity.getUser())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("使用者为空");
                continue;
            }

            if (StringUtils.isBlank(entity.getNumber())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号为空");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号重复");
                continue;
            }
            if (StringUtils.isBlank(entity.getUser())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("使用者为空");
                continue;
            }
            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("没有此使用者");
                continue;
            }

            Asset asset = new Asset();
            asset.setResponsibleUserId(CheckUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(importRequest.getAreaId());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setResponsibleUserId(Objects.toString(LoginUserUtil.getLoginUser().getId()));
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setManufacturer(entity.getManufacturer());
            asset.setFirmwareVersion(entity.getFirmwareVersion());
            asset.setSerial(entity.getSerial());
            asset.setContactTel(entity.getTelephone());
            asset.setLocation(entity.getLocation());
            asset.setHouseLocation(entity.getHouseLocation());
            asset.setEmail(entity.getEmail());
            asset.setBuyDate(entity.getBuyDate());
            asset.setServiceLife(entity.getDueTime());
            asset.setWarranty(entity.getWarranty());
            asset.setDescrible(entity.getDescription());
            asset.setMemo(importRequest.getMemo());
            asset.setOperationSystem(entity.getOperationSystem());
            asset.setContactTel(entity.getTelephone());
            asset.setEmail(entity.getEmail());
            asset.setCategoryModel("4");

            assetDao.insert(asset);
            String id = asset.getStringId();

            if (StringUtils.isNotBlank(entity.getMemoryBrand()) && !Objects.isNull(entity.getMemoryCapacity())
                && !Objects.isNull(entity.getMemoryFrequency()) && entity.getMemoryNum() > 0) {
                AssetMemory assetMemory = new AssetMemory();
                assetMemory.setAssetId(id);
                assetMemory.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetMemory.setGmtCreate(System.currentTimeMillis());
                assetMemory.setSerial(entity.getMemorySerial());
                assetMemory.setBrand(entity.getMemoryBrand());
                assetMemory.setCapacity(entity.getMemoryCapacity());
                assetMemory.setFrequency(entity.getMemoryFrequency());
                assetMemory.setCapacity(entity.getMemoryCapacity());
                assetMemory.setStitch(entity.getStitch());
                assetMemory.setHeatsink(entity.getHeatsink());
                assetMemory.setTransferType(entity.getTransferType());
                for (int i = 0; i < entity.getMemoryNum(); i++) {
                    assetMemoryDao.insert(assetMemory);
                }
            }
            // else {
            // builder.append("序号").append(entity.getOrderNumber()).append ("没有添加内存：内存品牌，内存容量，内存主频，内存数量>0")
            // }

            if (StringUtils.isNotBlank(entity.getHardDiskBrand()) && !Objects.isNull(entity.getHardDisCapacityl())
                && !Objects.isNull(entity.getHardDiskType()) && !Objects.isNull(entity.getHardDiskNum())
                && entity.getHardDiskNum() > 0) {
                AssetHardDisk assetHardDisk = new AssetHardDisk();
                assetHardDisk.setAssetId(id);
                assetHardDisk.setGmtCreate(System.currentTimeMillis());
                assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetHardDisk.setSerial(entity.getHardDiskSerial());
                assetHardDisk.setBrand(entity.getHardDiskBrand());
                assetHardDisk.setModel(entity.getHardDiskModel());
                assetHardDisk.setCapacity(entity.getHardDisCapacityl());
                assetHardDisk.setBuyDate(entity.getHardDiskBuyDate());
                assetHardDisk.setInterfaceType(entity.getHardDiskInterfaceType());
                assetHardDisk.setDiskType(entity.getHardDiskType());

                for (int i = 0; i < entity.getHardDiskNum(); i++) {
                    assetHardDiskDao.insert(assetHardDisk);

                }

            }

            if (StringUtils.isNotBlank(entity.getMainboradBrand()) && !Objects.isNull(entity.getMainboradNum())
                && entity.getMainboradNum() > 0) {

                AssetMainborad assetMainborad = new AssetMainborad();
                assetMainborad.setAssetId(id);
                assetMainborad.setGmtCreate(System.currentTimeMillis());
                assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetMainborad.setSerial(entity.getMainboradSerial());
                assetMainborad.setBrand(entity.getMainboradBrand());
                assetMainborad.setModel(entity.getMainboradModel());
                assetMainborad.setBiosVersion(entity.getMainboradBiosVersion());
                assetMainborad.setBiosDate(entity.getMainboradBiosDate());

                for (int i = 0; i < entity.getMainboradNum(); i++) {
                    assetMainboradDao.insert(assetMainborad);

                }

            }
            if (StringUtils.isNotBlank(entity.getCpuBrand()) && !Objects.isNull(entity.getCpuNum())
                && entity.getCpuNum() > 0 && !Objects.isNull(entity.getCpuMainFrequency())) {
                AssetCpu assetCpu = new AssetCpu();
                assetCpu.setAssetId(id);
                assetCpu.setGmtCreate(System.currentTimeMillis());
                assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetCpu.setSerial(entity.getCpuSerial());
                assetCpu.setBrand(entity.getCpuBrand());
                assetCpu.setModel(entity.getCpuModel());
                assetCpu.setMainFrequency(entity.getCpuMainFrequency());
                assetCpu.setThreadSize(entity.getCpuThreadSize());
                assetCpu.setCoreSize(entity.getCpuCoreSize());

                for (int i = 0; i < entity.getCpuNum(); i++) {
                    assetCpuDao.insert(assetCpu);

                }
            }
            if (StringUtils.isNotBlank(entity.getNetworkBrand()) && !Objects.isNull(entity.getNetworkNum())
                && entity.getNetworkNum() > 0) {
                AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
                assetNetworkCard.setAssetId(id);
                assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                assetNetworkCard.setSerial(entity.getNetworkSerial());
                assetNetworkCard.setBrand(entity.getNetworkBrand());
                assetNetworkCard.setModel(entity.getNetworkModel());
                assetNetworkCard.setSubnetMask(entity.getNetworkSubnetMask());
                assetNetworkCard.setDefaultGateway(entity.getNetworkDefaultGateway());

                String ip = entity.getNetworkIpAddress();
                String mac = entity.getNetworkMacAddress();
                if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(mac)) {

                    String[] ips = ip.split(",");
                    String[] macs = mac.split(",");
                    for (int i = 0; i < entity.getNetworkNum(); i++) {

                        assetNetworkCard.setMacAddress(macs[i]);
                        assetNetworkCard.setIpAddress(ips[i]);
                        assetNetworkCardDao.insert(assetNetworkCard);
                    }
                } else {
                    for (int i = 0; i < entity.getNetworkNum(); i++) {

                        assetNetworkCard.setMacAddress(ip);
                        assetNetworkCard.setIpAddress(mac);
                        assetNetworkCardDao.insert(assetNetworkCard);
                    }
                }

            }

            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getStringId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecord.setProcessResult(1);
            assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
            assetOperationRecordDao.insert(assetOperationRecord);

            // 流程

            Map<String, Object> formData = new HashMap();
            String[] userIds = importRequest.getUserId();
            for (String configBaselineUserId : userIds) {
                formData.put("configBaselineUserId", configBaselineUserId);
                formData.put("discard", 0);
            }
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getName());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);
            success++;
        }

        String re = "导入成功" + success + "条";
        re += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        re += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder(re);
        // if (error + repeat > 0) {
        // stringBuilder.append(re).append("其中").append(builder);
        // return stringBuilder.toString();
        // }
        // return stringBuilder.toString();

        StringBuilder sb = new StringBuilder(result.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();

    }

    @Override
    public String importNet(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        ImportResult<NetworkDeviceEntity> result = ExcelUtils.importExcelFromClient(NetworkDeviceEntity.class, file, 0,
            0);
        int success = 0;
        int repeat = 0;
        int error = 0;
        StringBuilder builder = new StringBuilder();
        List<NetworkDeviceEntity> entities = result.getDataList();
        for (NetworkDeviceEntity networkDeviceEntity : entities) {
            if (StringUtils.isBlank(networkDeviceEntity.getName())) {
                error++;
                builder.append("序号").append(networkDeviceEntity.getOrderNumber()).append("资产名称为空");
                continue;
            }
            if (StringUtils.isBlank(networkDeviceEntity.getNumber())) {
                error++;
                builder.append("序号").append(networkDeviceEntity.getOrderNumber()).append("资产编号为空");
                continue;
            }

            if (CheckRepeat(networkDeviceEntity.getNumber())) {
                repeat++;
                builder.append("序号").append(networkDeviceEntity.getOrderNumber()).append("资产编号重复");
                continue;
            }

            if (StringUtils.isBlank(networkDeviceEntity.getUser())) {
                error++;
                builder.append("序号").append(networkDeviceEntity.getOrderNumber()).append("使用者为空");
                continue;
            }

            if ("".equals(CheckUser(networkDeviceEntity.getUser()))) {
                error++;
                builder.append("序号").append(networkDeviceEntity.getOrderNumber()).append("没有此使用者");
                continue;
            }

            Asset asset = new Asset();
            asset.setResponsibleUserId(CheckUser(networkDeviceEntity.getUser()));
            AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(importRequest.getAreaId());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
            asset.setNumber(networkDeviceEntity.getNumber());
            asset.setName(networkDeviceEntity.getName());
            asset.setManufacturer(networkDeviceEntity.getManufacturer());
            asset.setFirmwareVersion(networkDeviceEntity.getFirmwareVersion());
            asset.setSerial(networkDeviceEntity.getSerial());
            asset.setContactTel(networkDeviceEntity.getTelephone());
            asset.setLocation(networkDeviceEntity.getLocation());
            asset.setHouseLocation(networkDeviceEntity.getHouseLocation());
            asset.setEmail(networkDeviceEntity.getEmail());
            asset.setBuyDate(networkDeviceEntity.getButDate());
            asset.setServiceLife(networkDeviceEntity.getDueDate());
            asset.setWarranty(networkDeviceEntity.getWarranty());
            asset.setMemo(networkDeviceEntity.getMemo());
            asset.setContactTel(networkDeviceEntity.getTelephone());
            asset.setEmail(networkDeviceEntity.getEmail());
            asset.setCategoryModel("5");
            assetDao.insert(asset);
            assetNetworkEquipment.setAssetId(asset.getStringId());
            assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
            assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetNetworkEquipment.setInterfaceSize(networkDeviceEntity.getInterfaceSize());
            assetNetworkEquipment.setPortSize(networkDeviceEntity.getPortSize());
            assetNetworkEquipment.setIos(networkDeviceEntity.getIos());
            assetNetworkEquipment.setInnerIp(networkDeviceEntity.getInnerIp());
            assetNetworkEquipment.setOuterIp(networkDeviceEntity.getOuterIp());
            assetNetworkEquipment.setMacAddress(networkDeviceEntity.getMac());
            assetNetworkEquipment.setCpuVersion(networkDeviceEntity.getCpuVersion());
            assetNetworkEquipment.setSubnetMask(networkDeviceEntity.getSubnetMask());
            assetNetworkEquipment.setExpectBandwidth(networkDeviceEntity.getExpectBandwidth());
            assetNetworkEquipment.setMemo(networkDeviceEntity.getMemo());
            assetNetworkEquipment.setNcrmSize(networkDeviceEntity.getNcrmSize());
            assetNetworkEquipment.setCpuSize(networkDeviceEntity.getCpuSize());
            assetNetworkEquipment.setDramSize(networkDeviceEntity.getDramSize());
            assetNetworkEquipment.setFlashSize(networkDeviceEntity.getFlashSize());
            assetNetworkEquipment.setRegister(networkDeviceEntity.getRegister());
            assetNetworkEquipment.setIsWireless(networkDeviceEntity.getIsWireless());
            assetNetworkEquipmentDao.insert(assetNetworkEquipment);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getStringId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setProcessResult(1);
            assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecordDao.insert(assetOperationRecord);
            // 流程

            Map<String, Object> formData = new HashMap();
            String[] userIds = importRequest.getUserId();
            for (String configBaselineUserId : userIds) {
                formData.put("configBaselineUserId", configBaselineUserId);
                formData.put("discard", 0);
            }
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getName());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);

            success++;
        }

        String re = "导入成功" + success + "条";
        re += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        re += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder(re);
        // if (error + repeat > 0) {
        // stringBuilder.append(re).append("其中").append(builder);
        // }
        //
        // return stringBuilder.toString();

        StringBuilder sb = new StringBuilder(result.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();
    }

    @Override
    public String importSecurity(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        ImportResult<SafetyEquipmentEntiy> result = ExcelUtils.importExcelFromClient(SafetyEquipmentEntiy.class, file,
            0, 0);
        int success = 0;
        int repeat = 0;
        int error = 0;
        StringBuilder builder = new StringBuilder();
        List<SafetyEquipmentEntiy> resultDataList = result.getDataList();
        for (SafetyEquipmentEntiy entity : resultDataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产名称为空");
                continue;
            }
            if (StringUtils.isBlank(entity.getNumber())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号为空");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号重复");
                continue;
            }

            if (StringUtils.isBlank(entity.getUser())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("使用者为空");
                continue;
            }
            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("没有此使用者");
                continue;
            }

            Asset asset = new Asset();
            asset.setResponsibleUserId(CheckUser(entity.getUser()));
            AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(importRequest.getAreaId());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setManufacturer(entity.getManufacturer());
            asset.setFirmwareVersion(entity.getFirmwareVersion());
            asset.setSerial(entity.getSerial());
            asset.setContactTel(entity.getTelephone());
            asset.setLocation(entity.getLocation());
            asset.setHouseLocation(entity.getHouseLocation());
            asset.setEmail(entity.getEmail());
            asset.setBuyDate(entity.getBuyDate());
            asset.setServiceLife(entity.getDueDate());
            asset.setWarranty(entity.getWarranty());
            asset.setMemo(entity.getMemo());
            asset.setContactTel(entity.getTelephone());
            asset.setEmail(entity.getEmail());
            asset.setCategoryModel("7");
            assetDao.insert(asset);
            assetSafetyEquipment.setAssetId(asset.getStringId());
            assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
            assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetSafetyEquipment.setSoftwareVersion(entity.getSoftwareVersion());
            assetSafetyEquipment.setIp(entity.getIp());
            assetSafetyEquipment.setMemo(entity.getMemo());
            assetSafetyEquipmentDao.insert(assetSafetyEquipment);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getStringId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecord.setProcessResult(1);
            assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
            assetOperationRecordDao.insert(assetOperationRecord);
            // 流程

            Map<String, Object> formData = new HashMap();
            String[] userIds = importRequest.getUserId();
            for (String configBaselineUserId : userIds) {
                formData.put("configBaselineUserId", configBaselineUserId);
                formData.put("discard", 0);
            }
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getName());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);
            success++;
        }

        String res = "导入成功" + success + "条";
        res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder(res);
        // if (error > 0) {
        // stringBuilder.append(res).append("其中").append(builder);
        // }
        //
        // return stringBuilder.toString();

        StringBuilder sb = new StringBuilder(result.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();

    }

    @Override
    public String importStory(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        ImportResult<StorageDeviceEntity> result = ExcelUtils.importExcelFromClient(StorageDeviceEntity.class, file, 0,
            0);
        List<StorageDeviceEntity> resultDataList = result.getDataList();
        int success = 0;
        int repeat = 0;
        int error = 0;
        StringBuilder builder = new StringBuilder();
        for (StorageDeviceEntity entity : resultDataList) {

            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产名称为空");
                continue;
            }
            if (StringUtils.isBlank(entity.getNumber())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号为空");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号重复");
                continue;
            }
            if (StringUtils.isBlank(entity.getUser())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("使用者为空");
                continue;
            }
            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("没有此使用者");
                continue;
            }

            Asset asset = new Asset();
            asset.setResponsibleUserId(CheckUser(entity.getUser()));
            AssetStorageMedium assetSafetyEquipment = new AssetStorageMedium();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(importRequest.getAreaId());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setManufacturer(entity.getManufacturer());
            asset.setFirmwareVersion(entity.getFirmware());
            asset.setSerial(entity.getSerial());
            asset.setContactTel(entity.getTelephone());
            asset.setLocation(entity.getLocation());
            asset.setHouseLocation(entity.getHouseLocation());
            asset.setEmail(entity.getEmail());
            asset.setBuyDate(entity.getBuyDate());
            asset.setServiceLife(entity.getDueDate());
            asset.setWarranty(entity.getWarranty());
            asset.setMemo(entity.getMemo());
            asset.setContactTel(entity.getTelephone());
            asset.setEmail(entity.getEmail());
            asset.setCategoryModel("6");
            assetDao.insert(asset);
            assetSafetyEquipment.setAssetId(asset.getStringId());
            assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
            assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetSafetyEquipment.setFirmware(entity.getFirmware());
            assetSafetyEquipment.setDiskNumber(entity.getHardDiskNum());
            assetSafetyEquipment.setDriverNumber(entity.getDriveNum());
            assetSafetyEquipment.setMaximumStorage(entity.getCapacity());
            assetSafetyEquipment.setMemo(entity.getMemo());
            assetSafetyEquipment.setHighCache(entity.getHighCache());
            assetSafetyEquipment.setRaidSupport(entity.getRaidSupport());
            assetSafetyEquipment.setInnerInterface(entity.getInnerInterface());
            assetSafetyEquipment.setOsVersion(entity.getSlotType());
            assetSafetyEquipment.setAverageTransferRate(entity.getAverageTransmissionRate());
            assetStorageMediumDao.insert(assetSafetyEquipment);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getStringId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            // 流程
            Map<String, Object> formData = new HashMap();
            String[] userIds = importRequest.getUserId();
            for (String configBaselineUserId : userIds) {
                formData.put("configBaselineUserId", configBaselineUserId);
                formData.put("discard", 0);
            }
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getName());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);
            success++;
        }

        String res = "导入成功" + success + "条";
        res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder(res);
        // if (error + repeat > 0) {
        // stringBuilder.append("其中").append(builder);
        // }
        //
        // return stringBuilder.toString();
        StringBuilder sb = new StringBuilder(result.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();
    }

    @Override
    public String importOhters(MultipartFile file, AssetImportRequest importRequest) throws Exception {
        ImportResult<OtherDeviceEntity> result = ExcelUtils.importExcelFromClient(OtherDeviceEntity.class, file, 0, 0);
        List<OtherDeviceEntity> resultDataList = result.getDataList();
        int success = 0;
        int repeat = 0;
        int error = 0;
        StringBuilder builder = new StringBuilder();
        for (OtherDeviceEntity entity : resultDataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产名称为空");
                continue;
            }
            if (StringUtils.isBlank(entity.getNumber())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号为空");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                builder.append("序号").append(entity.getOrderNumber()).append("资产编号重复");
                continue;
            }
            if (StringUtils.isBlank(entity.getUser())) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("使用者为空");
                continue;
            }
            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                builder.append("序号").append(entity.getOrderNumber()).append("没有此使用者");
                continue;
            }

            Asset asset = new Asset();
            asset.setResponsibleUserId(CheckUser(entity.getUser()));
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId(importRequest.getAreaId());
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
            asset.setNumber(entity.getNumber());
            asset.setName(entity.getName());
            asset.setManufacturer(entity.getManufacturer());
            asset.setSerial(entity.getSerial());
            asset.setContactTel(entity.getTelephone());
            asset.setEmail(entity.getEmail());
            asset.setBuyDate(entity.getBuyDate());
            asset.setServiceLife(entity.getDueDate());
            asset.setWarranty(entity.getWarranty());
            asset.setMemo(entity.getMemo());
            asset.setContactTel(entity.getTelephone());
            asset.setEmail(entity.getEmail());
            asset.setCategoryModel("8");
            assetDao.insert(asset);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getStringId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecord.setProcessResult(1);
            assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
            assetOperationRecordDao.insert(assetOperationRecord);
            // 流程
            Map<String, Object> formData = new HashMap();
            String[] userIds = importRequest.getUserId();
            for (String configBaselineUserId : userIds) {
                formData.put("configBaselineUserId", configBaselineUserId);
                formData.put("discard", 0);
            }
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getName());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess(manualStartActivityRequest);
            success++;
        }

        String res = "导入成功" + success + "条";
        res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder(res);
        // if (error + repeat > 0) {
        // stringBuilder.append("其中").append(builder);
        // }
        //
        // return stringBuilder.toString();
        StringBuilder sb = new StringBuilder(result.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();
    }

    private void exportToClient(Class clazz, String fileName, String title) {
        ExcelUtils.exportTemplet(clazz, fileName, title);
    }

    private void exportData(Class clazz, String fileName, AssetQuery assetQuery, HttpServletResponse response)
                                                                                                              throws Exception {
        assetQuery.setAreaIds(ArrayTypeUtil.ObjectArrayToStringArray(LoginUserUtil.getLoginUser()
            .getAreaIdsOfCurrentUser().toArray()));
        assetQuery.setPageSize(Constants.ALL_PAGE);
        List<AssetResponse> list = this.findListAsset(assetQuery);
        List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setSheetName("资产信息表");
        downloadVO.setDownloadList(assetEntities);
        if (downloadVO.getDownloadList() != null) {
            excelDownloadUtil.excelDownload(response, "资产信息表", downloadVO);
        } else {
            throw new BusinessException("导出数据为空");
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
        assetEntity.setGmtCreate(LongToDateString(asset.getGmtCreate()));
        super.convert(asset, assetEntity);
    }

    private String LongToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }

}