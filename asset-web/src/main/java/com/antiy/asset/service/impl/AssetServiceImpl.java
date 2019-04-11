package com.antiy.asset.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.intergration.OperatingSystemClient;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.*;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.base.SysArea;
import com.antiy.common.config.kafka.KafkaConfig;
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

/**
 * <p> 资产主表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService {
    private Logger                                                             logger   = LogUtils.get(this.getClass());
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
    private BaseConverter<AssetSafetyEquipment, AssetSafetyEquipmentRequest>   safetyEquipmentToRequestConverter;
    @Resource
    private BaseConverter<AssetStorageMedium, AssetStorageMediumRequest>       storageMediumToRequestConverter;
    @Resource
    private BaseConverter<AssetNetworkEquipment, AssetNetworkEquipmentRequest> networkEquipmentToRequestConverter;
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
    private IAssetCategoryModelService                                         assetCategoryModelService;
    @Resource
    private ActivityClient                                                     activityClient;
    @Resource
    private AreaClient                                                         areaClient;
    @Resource
    private AesEncoder                                                         aesEncoder;
    @Resource
    private RedisUtil                                                          redisUtil;
    @Resource
    private SchemeDao                                                          schemeDao;
    @Resource
    private AssetLinkRelationDao                                               assetLinkRelationDao;
    @Resource
    private OperatingSystemClient                                              operatingSystemClient;
    @Resource
    private IAssetSoftwareService                                              softwareService;
    private static final int                                                   ALL_PAGE = -1;

    @Override
    public ActionResponse saveAsset(AssetOuterRequest request) throws Exception {

        AssetRequest requestAsset = request.getAsset();
        Integer id = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                int hardware = 1;
                try {
                    // 记录资产登记信息到变更记录表
                    AssetOuterRequest assetOuterRequestToChangeRecord = new AssetOuterRequest();
                    String aid;
                    if (requestAsset != null) {
                        if (StringUtils.isNotBlank(requestAsset.getNumber())) {

                            ParamterExceptionUtils.isTrue(!CheckRepeat(requestAsset.getNumber()), "编号重复");
                        }

                        String name = requestAsset.getName();
                        ParamterExceptionUtils.isTrue(!CheckRepeatName(name), "资产名称重复");

                        if (StringUtils.isNotBlank(requestAsset.getOperationSystem())) {

                            BusinessExceptionUtils.isTrue(!checkOperatingSystem(requestAsset.getOperationSystem()),
                                "操作系统不存在，或已经注销");
                        }

                        String areaId = requestAsset.getAreaId();

                        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                            DataTypeUtils.stringToInteger(areaId));

                        SysArea sysArea = redisUtil.getObject(key, SysArea.class);

                        BusinessExceptionUtils.isTrue(
                            !Objects.isNull(assetUserDao
                                .getById(DataTypeUtils.stringToInteger(requestAsset.getResponsibleUserId()))),
                            "使用者不存在，或已经注销");

                        BusinessExceptionUtils.isTrue(
                            !Objects.isNull(assetCategoryModelDao
                                .getById(DataTypeUtils.stringToInteger(requestAsset.getCategoryModel()))),
                            "品类型号不存在，或已经注销");

                        BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "选择区域不存在，或已经注销");
                        BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "当前区域不存在，或已经注销");

                        List<AssetGroupRequest> assetGroup = requestAsset.getAssetGroups();
                        Asset asset = requestConverter.convert(requestAsset, Asset.class);
                        if (CollectionUtils.isNotEmpty(assetGroup)) {
                            assembleAssetGroupName(assetGroup, asset);
                        }

                        asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        asset.setGmtCreate(System.currentTimeMillis());
                        asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                        assetDao.insert(asset);
                        AssetRequest assetRequest = assetToRequestConverter.convert(asset, AssetRequest.class);
                        assetRequest.setId(DataTypeUtils.integerToString(asset.getId()));
                        assetOuterRequestToChangeRecord.setAsset(assetRequest);

                        LogHandle.log(requestAsset, AssetEventEnum.ASSET_INSERT.getName(),
                            AssetEventEnum.ASSET_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        // 记录操作日志和运行日志
                        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_INSERT.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
                        LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}", requestAsset.toString());

                        insertBatchAssetGroupRelation(asset, assetGroup);

                        aid = asset.getStringId();
                        // 保存安全设备
                        AssetSafetyEquipmentRequest safetyEquipmentRequest = request.getSafetyEquipment();
                        if (safetyEquipmentRequest != null) {
                            Integer id = saveSafety(aid, safetyEquipmentRequest);
                            safetyEquipmentRequest.setId(String.valueOf(id));
                            assetOuterRequestToChangeRecord.setSafetyEquipment(safetyEquipmentRequest);
                        }
                        // 保存网络设备
                        AssetNetworkEquipmentRequest networkEquipmentRequest = request.getNetworkEquipment();
                        if (networkEquipmentRequest != null) {
                            ParamterExceptionUtils.isTrue(!CheckRepeatIp(networkEquipmentRequest.getInnerIp(), 1),
                                "内网IP不能重复！");
                            Integer id = SaveNetwork(aid, networkEquipmentRequest);
                            networkEquipmentRequest.setId(String.valueOf(id));
                            assetOuterRequestToChangeRecord.setNetworkEquipment(networkEquipmentRequest);
                        }
                        // 保存存储设备
                        AssetStorageMediumRequest assetStorageMedium = request.getAssetStorageMedium();
                        if (assetStorageMedium != null) {
                            AssetStorageMedium medium = BeanConvert.convertBean(assetStorageMedium,
                                AssetStorageMedium.class);
                            Integer id = SaveStorage(asset, assetStorageMedium, medium);
                            assetStorageMedium.setId(String.valueOf(id));
                            assetOuterRequestToChangeRecord.setAssetStorageMedium(assetStorageMedium);
                        }
                        // 软件关联表
                        // List<AssetSoftwareRelationRequest> computerReques = request.getAssetSoftwareRelationList();
                        // List<AssetSoftwareRelationRequest> softwareRelationRequestListToChangeRecord = new
                        // ArrayList<>();
                        // assetOuterRequestToChangeRecord.setAssetSoftwareRelationList(computerReques);
                        // if (CollectionUtils.isNotEmpty(computerReques)) {
                        // for (AssetSoftwareRelationRequest computerReque : computerReques) {
                        // AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                        // assetSoftwareRelation.setAssetId(aid);
                        // assetSoftwareRelation.setSoftwareId(computerReque.getSoftwareId());
                        // assetSoftwareRelation.setPort(computerReque.getPort());
                        // assetSoftwareRelation.setSoftwareStatus(SoftwareStatusEnum.WAIT_ANALYZE.getCode());
                        // assetSoftwareRelation.setLicenseSecretKey(computerReque.getLicenseSecretKey());
                        // assetSoftwareRelation.setMemo(computerReque.getMemo());
                        // assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                        // assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        // // assetSoftwareRelation.setInstallType(computerReque.getInstallType());
                        // // assetSoftwareRelation.setProtocol(computerReque.getProtocol());
                        // assetSoftwareRelationDao.insert(assetSoftwareRelation);
                        // AssetSoftwareRelationRequest assetSoftwareRelationRequest = softRelationToRequestConverter
                        // .convert(assetSoftwareRelation, AssetSoftwareRelationRequest.class);
                        // assetSoftwareRelationRequest
                        // .setId(DataTypeUtils.integerToString(assetSoftwareRelation.getId()));
                        //
                        // softwareRelationRequestListToChangeRecord.add(assetSoftwareRelationRequest);
                        // // if (StringUtils.isNotBlank(computerReque.getLicenseSecretKey())) {
                        // // AssetSoftwareLicense license = new AssetSoftwareLicense();
                        // // license.setSoftwareId(assetSoftwareRelation.getId());
                        // // license.setLicenseSecretKey(computerReque.getLicenseSecretKey());
                        // // license.setGmtCreate(System.currentTimeMillis());
                        // // license.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        // // }
                        // }
                        // assetOuterRequestToChangeRecord
                        // .setAssetSoftwareRelationList(softwareRelationRequestListToChangeRecord);
                        // }

                        List<AssetNetworkCardRequest> networkCardRequestList = request.getNetworkCard();
                        List<AssetNetworkCardRequest> networkRequestListToChangeRecord = new ArrayList<>();
                        // 网卡
                        if (CollectionUtils.isNotEmpty(networkCardRequestList)) {
                            // List<AssetNetworkCard> network = new ArrayList<>();
                            List<AssetNetworkCard> networkCardList = BeanConvert.convert(networkCardRequestList,
                                AssetNetworkCard.class);
                            for (AssetNetworkCard assetNetworkCard : networkCardList) {
                                ParamterExceptionUtils.isBlank(assetNetworkCard.getBrand(), "网卡品牌为空");
                                ParamterExceptionUtils.isTrue(!CheckRepeatIp(assetNetworkCard.getIpAddress(), null),
                                    "IP不能重复！");
                                assetNetworkCard.setAssetId(aid);
                                assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                                assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                assetNetworkCardDao.insert(assetNetworkCard);
                                LogHandle.log(assetNetworkCard, AssetEventEnum.ASSET_NETWORK_INSERT.getName(),
                                    AssetEventEnum.ASSET_NETWORK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_INSERT.getName(),
                                    assetNetworkCard.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                    BusinessPhaseEnum.WAIT_REGISTER));
                                LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_INSERT.getName() + " {}",
                                    assetNetworkCard.toString());
                                // network.add(assetNetworkCard);
                                AssetNetworkCardRequest assetNetworkCardRequest = networkCardToRequestConverter
                                    .convert(assetNetworkCard, AssetNetworkCardRequest.class);
                                assetNetworkCardRequest.setId(assetNetworkCard.getStringId());
                                networkRequestListToChangeRecord.add(assetNetworkCardRequest);
                            }
                            // assetNetworkCardDao.insertBatch(network);
                            assetOuterRequestToChangeRecord.setNetworkCard(networkRequestListToChangeRecord);
                        }
                        // 主板
                        List<AssetMainboradRequest> mainboradRequestList = request.getMainboard();
                        List<AssetMainboradRequest> mainboardRequestListToChangeRecord = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(mainboradRequestList)) {
                            // List<AssetMainborad> mainboard = new ArrayList<>();
                            List<AssetMainborad> mainboradList = BeanConvert.convert(mainboradRequestList,
                                AssetMainborad.class);
                            for (AssetMainborad assetMainborad : mainboradList) {
                                ParamterExceptionUtils.isBlank(assetMainborad.getBrand(), "主板品牌为空");
                                assetMainborad.setAssetId(aid);
                                assetMainborad.setGmtCreate(System.currentTimeMillis());
                                assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetMainborad, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
                                    AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
                                    asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                    BusinessPhaseEnum.WAIT_REGISTER));
                                LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName() + " {}",
                                    assetMainborad.toString());
                                // mainboard.add(assetMainborad);
                                assetMainboradDao.insert(assetMainborad);
                                AssetMainboradRequest assetMainboradRequest = mainboradToRequestConverter
                                    .convert(assetMainborad, AssetMainboradRequest.class);
                                assetMainboradRequest.setId(assetMainborad.getStringId());
                                mainboardRequestListToChangeRecord.add(assetMainboradRequest);
                            }
                            // assetMainboradDao.insertBatch(mainboard);
                            assetOuterRequestToChangeRecord.setMainboard(mainboardRequestListToChangeRecord);
                        }
                        // 内存
                        List<AssetMemoryRequest> memoryRequestList = request.getMemory();
                        List<AssetMemoryRequest> memoryRequestListToChangeRecord = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(memoryRequestList)) {
                            List<AssetMemory> memoryList = BeanConvert.convert(memoryRequestList, AssetMemory.class);
                            // List<AssetMemory> memory = new ArrayList<>();
                            for (AssetMemory assetMemory : memoryList) {
                                ParamterExceptionUtils.isBlank(assetMemory.getBrand(), "内存品牌为空");
                                ParamterExceptionUtils.isNull(assetMemory.getFrequency(), "内存主频为空");
                                ParamterExceptionUtils.isNull(assetMemory.getCapacity(), "内存容量为空");
                                assetMemory.setAssetId(aid);
                                assetMemory.setGmtCreate(System.currentTimeMillis());
                                assetMemory.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetMemory, AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
                                    AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
                                    asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                    BusinessPhaseEnum.WAIT_REGISTER));
                                LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_INSERT.getName() + " {}",
                                    assetMemory.toString());
                                // memory.add(assetMemory);
                                assetMemoryDao.insert(assetMemory);
                                AssetMemoryRequest assetMemoryRequest = memoryToRequestConverter.convert(assetMemory,
                                    AssetMemoryRequest.class);
                                assetMemoryRequest.setId(assetMemory.getStringId());
                                memoryRequestListToChangeRecord.add(assetMemoryRequest);
                            }
                            // assetMemoryDao.insertBatch(memory);
                            assetOuterRequestToChangeRecord.setMemory(memoryRequestListToChangeRecord);
                        }
                        // CPU
                        List<AssetCpuRequest> cpuRequestList = request.getCpu();
                        List<AssetCpuRequest> cpuRequestListToChangeRecord = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(cpuRequestList)) {
                            List<AssetCpu> assetCpuList = BeanConvert.convert(cpuRequestList, AssetCpu.class);
                            // List<AssetCpu> cpu = new ArrayList<>();
                            for (AssetCpu assetCpu : assetCpuList) {
                                ParamterExceptionUtils.isBlank(assetCpu.getBrand(), "CPU品牌为空");
                                ParamterExceptionUtils.isNull(assetCpu.getMainFrequency(), "CPU主频为空");
                                assetCpu.setAssetId(aid);
                                assetCpu.setGmtCreate(System.currentTimeMillis());
                                assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetCpu, AssetEventEnum.ASSET_CPU_INSERT.getName(),
                                    AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CPU_INSERT.getName(),
                                    asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                    BusinessPhaseEnum.WAIT_REGISTER));
                                LogUtils.info(logger, AssetEventEnum.ASSET_CPU_INSERT.getName() + " {}",
                                    assetCpu.toString());
                                assetCpuDao.insert(assetCpu);
                                // cpu.add(assetCpu);
                                AssetCpuRequest assetCpuRequest = cpuToRequestConverter.convert(assetCpu,
                                    AssetCpuRequest.class);
                                assetCpuRequest.setId(assetCpu.getStringId());
                                cpuRequestListToChangeRecord.add(assetCpuRequest);
                            }
                            // assetCpuDao.insertBatch(cpu);
                            assetOuterRequestToChangeRecord.setCpu(cpuRequestListToChangeRecord);
                        }
                        // 硬盘
                        List<AssetHardDiskRequest> hardDisk = request.getHardDisk();
                        List<AssetHardDiskRequest> hardDiskRequestListToChangeRecord = new ArrayList<>();
                        if (CollectionUtils.isNotEmpty(hardDisk)) {
                            // List<AssetHardDisk> assetHardDisks = new ArrayList<>();
                            List<AssetHardDisk> hardDisks = BeanConvert.convert(hardDisk, AssetHardDisk.class);
                            for (AssetHardDisk assetHardDisk : hardDisks) {
                                ParamterExceptionUtils.isBlank(assetHardDisk.getBrand(), "硬盘品牌为空");
                                ParamterExceptionUtils.isNull(assetHardDisk.getCapacity(), "硬盘容量空");
                                assetHardDisk.setAssetId(aid);
                                assetHardDisk.setGmtCreate(System.currentTimeMillis());
                                assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                                LogHandle.log(assetHardDisk, AssetEventEnum.ASSET_DISK_INSERT.getName(),
                                    AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DISK_INSERT.getName(),
                                    asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                    BusinessPhaseEnum.WAIT_REGISTER));
                                LogUtils.info(logger, AssetEventEnum.ASSET_DISK_INSERT.getName() + " {}",
                                    assetHardDisk.toString());
                                // assetHardDisks.add(assetHardDisk);
                                assetHardDiskDao.insert(assetHardDisk);
                                AssetHardDiskRequest assetHardDiskRequest = hardDiskToRequestConverter
                                    .convert(assetHardDisk, AssetHardDiskRequest.class);
                                assetHardDiskRequest.setId(assetHardDisk.getStringId());
                                hardDiskRequestListToChangeRecord.add(assetHardDiskRequest);
                            }
                            // assetHardDiskDao.insertBatch(assetHardDisks);
                            assetOuterRequestToChangeRecord.setHardDisk(hardDiskRequestListToChangeRecord);
                        }
                    } else {
                        // 保存其他资产
                        AssetOthersRequest assetOthersRequest = request.getAssetOthersRequest();

                        if (StringUtils.isNotBlank(assetOthersRequest.getNumber())) {

                            ParamterExceptionUtils.isTrue(!CheckRepeat(assetOthersRequest.getNumber()), "编号重复");
                        }

                        String name = assetOthersRequest.getName();

                        ParamterExceptionUtils.isTrue(!CheckRepeatName(name), "资产名称重复");

                        Asset asset1 = BeanConvert.convertBean(assetOthersRequest, Asset.class);

                        BusinessExceptionUtils.isTrue(
                            !Objects.isNull(assetUserDao
                                .getById(DataTypeUtils.stringToInteger(assetOthersRequest.getResponsibleUserId()))),
                            "使用者不存在，或已经注销");

                        BusinessExceptionUtils.isTrue(
                            !Objects.isNull(assetCategoryModelDao
                                .getById(DataTypeUtils.stringToInteger(assetOthersRequest.getCategoryModel()))),
                            "品类型号不存在，或已经注销");

                        String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                            DataTypeUtils.stringToInteger(assetOthersRequest.getAreaId()));

                        SysArea sysArea = redisUtil.getObject(key, SysArea.class);

                        BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "选择区域不存在，或已经注销");
                        BusinessExceptionUtils.isTrue(!Objects.isNull(sysArea), "当前区域不存在，或已经注销");

                        List<AssetGroupRequest> assetGroup = assetOthersRequest.getAssetGroups();

                        if (CollectionUtils.isNotEmpty(assetGroup)) {
                            assembleAssetGroupName(assetGroup, asset1);
                        }

                        asset1.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        asset1.setGmtCreate(System.currentTimeMillis());
                        asset1.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                        assetDao.insert(asset1);
                        aid = asset1.getStringId();

                        insertBatchAssetGroupRelation(asset1, assetGroup);

                        AssetRequest assetRequest = assetToRequestConverter.convert(asset1, AssetRequest.class);
                        assetRequest.setId(asset1.getStringId());
                        assetOuterRequestToChangeRecord.setAsset(assetRequest);

                        LogHandle.log(assetOthersRequest, AssetEventEnum.ASSET_OTHERS_INSERT.getName(),
                            AssetEventEnum.ASSET_OTHERS_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_OTHERS_INSERT.getName(),
                            asset1.getId(), asset1.getNumber(), asset1, BusinessModuleEnum.HARD_ASSET,
                            BusinessPhaseEnum.WAIT_REGISTER));
                        LogUtils.info(logger, AssetEventEnum.ASSET_OTHERS_INSERT.getName() + " {}",
                            assetOthersRequest.toString());
                    }
                    // 将资产副本存入变更记录表
                    AssetChangeRecord assetChangeRecord = new AssetChangeRecord();
                    assetChangeRecord.setBusinessId(
                        DataTypeUtils.stringToInteger(assetOuterRequestToChangeRecord.getAsset().getId()));
                    assetChangeRecord.setChangeVal(JsonUtil.object2Json(assetOuterRequestToChangeRecord));
                    assetChangeRecord.setGmtCreate(System.currentTimeMillis());
                    assetChangeRecord.setGmtModified(System.currentTimeMillis());
                    assetChangeRecord.setType(hardware);
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
                    assetOperationRecord.setAreaId(assetOuterRequestToChangeRecord.getAsset().getAreaId());
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return Integer.parseInt(aid);
                } catch (RequestParamValidateException e) {
                    transactionStatus.setRollbackOnly();
                    ParamterExceptionUtils.isTrue(!StringUtils.equals("编号重复", e.getMessage()), "编号重复");
                    ParamterExceptionUtils.isTrue(!StringUtils.equals("资产名称重复", e.getMessage()), "资产名称重复");
                    ParamterExceptionUtils.isTrue(!StringUtils.equals("IP不能重复！", e.getMessage()), "IP不能重复！");
                    ParamterExceptionUtils.isTrue(!StringUtils.equals("内网IP不能重复！", e.getMessage()), "内网IP不能重复！");

                    logger.error("录入失败", e);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    logger.error("录入失败", e);
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("操作系统不存在，或已经注销", e.getMessage()),
                        "操作系统不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("资产组名称获取失败", e.getMessage()), "资产组名称获取失败");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("使用者不存在，或已经注销", e.getMessage()), "使用者不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("品类型号不存在，或已经注销", e.getMessage()),
                        "品类型号不存在，或已经注销");
                    BusinessExceptionUtils.isTrue(!StringUtils.equals("当前区域不存在，或已经注销", e.getMessage()),
                        "品类型号不存在，或已经注销");
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
                return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
            }

            // 对接配置模块
            ConfigRegisterRequest configRegisterRequest = new ConfigRegisterRequest();
            configRegisterRequest.setAssetId(String.valueOf(id));
            configRegisterRequest.setSource(String.valueOf(AssetTypeEnum.HARDWARE.getCode()));
            configRegisterRequest.setSuggest(request.getAsset().getMemo());
            configRegisterRequest.setConfigUserIds(request.getManualStartActivityRequest().getConfigUserId());
            configRegisterRequest.setRelId(String.valueOf(id));
            ActionResponse actionResponseAsset = softwareService.configRegister(configRegisterRequest);
            if (null == actionResponseAsset
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponseAsset.getHead().getCode())) {
                // 调用失败，逻辑删登记的资产
                assetDao.deleteById(id);
                return actionResponseAsset == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION)
                    : actionResponse;
            }
        }

        return ActionResponse.success(id);
    }

    private Integer SaveStorage(Asset asset, AssetStorageMediumRequest assetStorageMedium,
                                AssetStorageMedium medium) throws Exception {
        medium.setAssetId(asset.getStringId());
        medium.setGmtCreate(System.currentTimeMillis());
        medium.setCreateUser(LoginUserUtil.getLoginUser().getId());
        LogHandle.log(assetStorageMedium, AssetEventEnum.ASSET_STORAGE_INSERT.getName(),
            AssetEventEnum.ASSET_STORAGE_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_STORAGE_INSERT.getName(), asset.getId(),
            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_STORAGE_INSERT.getName() + " {}", assetStorageMedium.toString());
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
        LogHandle.log(networkEquipmentRequest, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName(),
            AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(
            new BusinessData(AssetEventEnum.ASSET_STORAGE_INSERT.getName(), assetNetworkEquipment.getId(), "",
                assetNetworkEquipment, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName() + " {}",
            networkEquipmentRequest.toString());
        return assetNetworkEquipment.getId();
    }

    private Integer saveSafety(String aid, AssetSafetyEquipmentRequest safetyEquipmentRequest) throws Exception {
        AssetSafetyEquipment safetyEquipment = BeanConvert.convertBean(safetyEquipmentRequest,
            AssetSafetyEquipment.class);
        safetyEquipment.setAssetId(aid);
        safetyEquipment.setGmtCreate(System.currentTimeMillis());
        safetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSafetyEquipmentDao.insert(safetyEquipment);
        LogHandle.log(safetyEquipmentRequest, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName(),
            AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_STORAGE_INSERT.getName(), safetyEquipment.getId(),
            "", safetyEquipment, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName() + " {}",
            safetyEquipmentRequest.toString());
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
                String assetGroupName = assetGroupDao.getById(assetGroupRequest.getId()).getName();
                asset.setAssetGroup(
                    stringBuffer.append(assetGroupName).append(",").substring(0, stringBuffer.length() - 1));
            } catch (Exception e) {
                throw new BusinessException("资产组名称获取失败");
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
                LogHandle.log(assetGroupRequest, AssetEventEnum.ASSET_GROUP_INSERT.getName(),
                    AssetEventEnum.ASSET_GROUP_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_GROUP_INSERT.getName(), asset1.getId(), "",
                    asset1, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
                LogUtils.info(logger, AssetEventEnum.ASSET_GROUP_INSERT.getName() + " {}",
                    assetGroupRequest.toString());
            });
            assetGroupRelationDao.insertBatch(groupRelations);
        }
    }

    private boolean CheckRepeatIp(String innerIp, Integer isNet) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setIp(innerIp);
        assetQuery.setIsNet(isNet);
        Integer countIp = assetDao.findCountIp(assetQuery);
        return countIp >= 1;
    }

    private boolean CheckRepeat(String number) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setNumber(number);
        Integer countAsset = findCountAssetNumber(assetQuery);
        return countAsset >= 1;
    }

    private boolean CheckRepeatName(String name) throws Exception {
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setAssetName(name);
        Integer countAsset = findCountAssetNumber(assetQuery);
        return countAsset >= 1;
    }

    private String CheckUser(String user) throws Exception {
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
    public List<AssetResponse> findListAsset(AssetQuery query) throws Exception {
        if (ArrayUtils.isEmpty(query.getAreaIds())) {
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("hard");
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            query.setIds(processMap.keySet().toArray(new String[] {}));
        }

        // 如果是控制台进入，并且待办任务返回为空，则直接返回
        if (query.getEnterControl() && MapUtils.isEmpty(processMap)) {
            return null;
        }

        List<Asset> asset = assetDao.findListAsset(query);
        if (CollectionUtils.isNotEmpty(asset)) {
            asset.stream().forEach(a -> {
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
            query.setAreaIds(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        }
        Map<String, WaitingTaskReponse> processMap = this.getAllHardWaitingTask("hard");
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            query.setIds(processMap.keySet().toArray(new String[] {}));
        }

        // 如果是从工作台进入，并且没有待办任务，则直接返回空即可
        if (query.getEnterControl() && MapUtils.isEmpty(processMap)) {
            return 0;
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
        activityWaitingQuery.setUser(
            aesEncoder.encode(LoginUserUtil.getLoginUser().getStringId(), LoginUserUtil.getLoginUser().getUsername()));
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
        // 品类型号及其子品类
        if (!Objects.isNull(query.getCategoryModels()) && query.getCategoryModels().length > 0) {
            List<Integer> categoryModels = Lists.newArrayList();
            for (int i = 0; i < query.getCategoryModels().length; i++) {
                categoryModels.addAll(assetCategoryModelService
                    .findAssetCategoryModelIdsById(DataTypeUtils.stringToInteger(query.getCategoryModels()[i])));
            }
            query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryModels));
        }
        if (!Objects.isNull(processMap) && !processMap.isEmpty()) {
            query.setIds(processMap.keySet().toArray(new String[] {}));
        }

        // 如果count为0 直接返回结果即可
        int count = this.findCountAsset(query);
        if (count < 1) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), this.findListAsset(query));
    }

    /**
     * 通联设置的资产查询 与普通资产查询类似， 不同点在于品类型号显示二级品类， 只查已入网，网络设备和计算设备的资产,且会去掉通联表中已存在的资产
     */
    @Override
    public PageResult<AssetResponse> findUnconnectedAsset(AssetQuery query) throws Exception {
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        // 只查已入网资产
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        // 若品类型号查询条件为空 默认只查已入网，网络设备和计算设备的资产
        Map<String, String> categoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        if (Objects.isNull(query.getCategoryModels()) || query.getCategoryModels().length <= 0) {
            List<Integer> categoryCondition = new ArrayList<>();
            for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
                if (query.getSearchNetworkDevice() == null || !query.getSearchNetworkDevice()) {
                    if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
                        categoryCondition.addAll(assetCategoryModelService
                            .findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
                    }
                }
                if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
                    categoryCondition.addAll(
                        assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
                }
            }
            query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryCondition));
        } else {
            // 品类型号及其子品类
            List<Integer> categoryModels = Lists.newArrayList();
            for (int i = 0; i < query.getCategoryModels().length; i++) {
                categoryModels.addAll(assetCategoryModelService
                    .findAssetCategoryModelIdsById(DataTypeUtils.stringToInteger(query.getCategoryModels()[i])));
            }
            query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryModels));
        }
        // 进行查询
        Integer count = assetDao.findUnconnectedCount(query);
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
        } else {
            List<AssetResponse> assetResponseList = responseConverter.convert(assetDao.findListUnconnectedAsset(query),
                AssetResponse.class);
            // 处理品类型号使其均为二级品类型号
            processCategoryToSecondCategory(assetResponseList, categoryMap);
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetResponseList);
        }
    }

    private void processCategoryToSecondCategory(List<AssetResponse> assetResponseList,
                                                 Map<String, String> categoryMap) throws Exception {
        // 作为缓存使用，提高效率
        Map<String, String> cache = new HashMap<>();
        List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        for (AssetResponse assetResponse : assetResponseList) {
            String categoryModel = assetResponse.getCategoryModel();
            String cacheId = cache.get(categoryModel);
            if (Objects.nonNull(cacheId)) {
                assetResponse.setCategoryModel(categoryModel);
                assetResponse.setCategoryModelName(categoryMap.get(cacheId));
            } else {
                String second = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all,
                    categoryMap.keySet());
                if (Objects.nonNull(second)) {
                    assetResponse.setCategoryModelName(categoryMap.get(second));
                    cache.put(categoryModel, second);
                }
            }
        }
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
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        List<Map<String, Object>> list = assetDao.countManufacturer(areaIds, status);
        return CountTypeUtil.getEnumCountResponse(maxNum, list);
    }

    @Override
    public List<EnumCountResponse> countStatus() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> searchResult = assetDao.countStatus(ids);
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
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        // 查询第二级分类id
        List<AssetCategoryModel> secondCategoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
        List<AssetCategoryModel> categoryModelDaoAll = assetCategoryModelDao.getAll();
        if (CollectionUtils.isNotEmpty(secondCategoryModelList)) {
            List<EnumCountResponse> resultList = new ArrayList<>();
            for (AssetCategoryModel secondCategoryModel : secondCategoryModelList) {
                EnumCountResponse enumCountResponse = new EnumCountResponse();
                // 查询第二级每个分类下所有的分类id，并添加至list集合
                List<AssetCategoryModel> search = iAssetCategoryModelService.recursionSearch(categoryModelDaoAll,
                    secondCategoryModel.getId());
                List<String> categoryList = new ArrayList<>();
                categoryList.add(secondCategoryModel.getStringId());
                enumCountResponse.setCode(categoryList);
                // 设置查询资产条件参数，包括区域id，状态，资产品类型号
                AssetQuery assetQuery = setAssetQueryParam(areaIds, search);
                // 将查询结果放置结果集
                enumCountResponse.setNumber((long) assetDao.findCountByCategoryModel(assetQuery));
                enumCountResponse.setMsg(secondCategoryModel.getName());
                resultList.add(enumCountResponse);
            }
            return resultList;
        }
        return null;
    }

    private AssetQuery setAssetQueryParam(List<Integer> areaIds, List<AssetCategoryModel> search) {
        List<Integer> list = new ArrayList<>();
        search.stream().forEach(x -> list.add(x.getId()));
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setCategoryModels(ArrayTypeUtil.objectArrayToStringArray(list.toArray()));
        assetQuery.setAreaIds(ArrayTypeUtil.objectArrayToStringArray(areaIds.toArray()));
        List<Integer> status = StatusEnumUtil.getAssetNotRetireStatus();
        assetQuery.setAssetStatusList(status);
        return assetQuery;
    }

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
        assetResponse.setAssetGroups(
            BeanConvert.convert(assetGroupRelationDao.queryByAssetId(asset.getId()), AssetGroupResponse.class));
        assetOuterResponse.setAsset(assetResponse);

        // CPU
        if (condition.getIsNeedCpu()) {
            assetOuterResponse.setAssetCpu(BeanConvert.convert(assetCpuDao.getByWhere(param), AssetCpuResponse.class));
        }
        // 网卡
        if (condition.getIsNeedNetwork()) {
            assetOuterResponse.setAssetNetworkCard(
                BeanConvert.convert(assetNetworkCardDao.getByWhere(param), AssetNetworkCardResponse.class));
        }
        // 硬盘
        if (condition.getIsNeedHarddisk()) {
            assetOuterResponse
                .setAssetHardDisk(BeanConvert.convert(assetHardDiskDao.getByWhere(param), AssetHardDiskResponse.class));
        }
        // 主板
        if (condition.getIsNeedMainboard()) {
            assetOuterResponse.setAssetMainborad(
                BeanConvert.convert(assetMainboradDao.getByWhere(param), AssetMainboradResponse.class));
        }
        // 内存
        if (condition.getIsNeedMemory()) {
            assetOuterResponse
                .setAssetMemory(BeanConvert.convert(assetMemoryDao.getByWhere(param), AssetMemoryResponse.class));
        }
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
            assetOuterResponse.setAssetSoftware(BeanConvert.convert(assetSoftwareList, AssetSoftwareResponse.class));

            // 资产软件关系列表
            List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao
                .getReleationByAssetId(asset.getId());
            assetOuterResponse.setAssetSoftwareRelationList(
                BeanConvert.convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class));
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
                        assetGroup.stream().forEach(assetGroupRequest -> {
                            try {
                                String assetGroupName = assetGroupDao.getById(assetGroupRequest.getId()).getName();
                                asset.setAssetGroup(stringBuilder.append(assetGroupName).append(",").substring(0,
                                    stringBuilder.length() - 1));
                            } catch (Exception e) {
                                throw new BusinessException("资产组名称获取失败");
                            }
                        });
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
                    LogHandle.log(asset, AssetEventEnum.ASSET_MODIFY.getName(), AssetEventEnum.ASSET_MODIFY.getStatus(),
                        ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum.ASSET_MODIFY.getName() + " {}", asset.toString());
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MODIFY.getName(), asset.getId(),
                        asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    int count = assetDao.update(asset);

                    // 2. 更新cpu信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_CPU_DELETE.getName(),
                        AssetEventEnum.ASSET_CPU_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CPU_DELETE.getName(), asset.getId(),
                        asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_CPU_DELETE.getName() + " {}", asset.getStringId());
                    assetCpuDao.deleteByAssetId(asset.getId());
                    List<AssetCpuRequest> assetCpuRequestList = assetOuterRequest.getCpu();
                    if (CollectionUtils.isNotEmpty(assetCpuRequestList)) {
                        List<AssetCpu> assetCpuList = BeanConvert.convert(assetCpuRequestList, AssetCpu.class);
                        List<AssetCpu> insertCpuList = Lists.newArrayList();
                        List<AssetCpu> updateCpuList = Lists.newArrayList();
                        for (AssetCpu assetCpu : assetCpuList) {
                            assetCpu.setStatus(1);
                            assetCpu.setAssetId(asset.getStringId());
                            // 修改的
                            if (StringUtils.isNotBlank(assetCpu.getStringId())) {
                                assetCpu.setModifyUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                assetCpu.setGmtModified(System.currentTimeMillis());
                                updateCpuList.add(assetCpu);
                            } else {// 新增的
                                assetCpu.setGmtCreate(System.currentTimeMillis());
                                assetCpu.setCreateUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                insertCpuList.add(assetCpu);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(insertCpuList)) {
                            LogHandle.log(assetCpuList, AssetEventEnum.ASSET_CPU_INSERT.getName(),
                                AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum.ASSET_CPU_INSERT.getName() + " {}",
                                assetCpuList.toString());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CPU_INSERT.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            assetCpuDao.insertBatch(insertCpuList);
                        }
                        if (CollectionUtils.isNotEmpty(updateCpuList)) {
                            LogHandle.log(assetCpuList, AssetEventEnum.ASSET_CPU_UPDATE.getName(),
                                AssetEventEnum.ASSET_CPU_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_CPU_UPDATE.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_CPU_UPDATE.getName() + " {}",
                                assetCpuList.toString());
                            assetCpuDao.updateBatch(updateCpuList);
                        }
                        insertCpuList.addAll(updateCpuList);
                        assetOuterRequest.setCpu(BeanConvert.convert(insertCpuList, AssetCpuRequest.class));
                    }
                    // 3. 更新网卡信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_NETWORK_DELETE.getName(),
                        AssetEventEnum.ASSET_NETWORK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils
                        .recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_DELETE.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DELETE.getName() + " {}", asset.getStringId());
                    assetNetworkCardDao.deleteByAssetId(asset.getId());
                    List<AssetNetworkCardRequest> assetNetworkCardRequestList = assetOuterRequest.getNetworkCard();
                    if (CollectionUtils.isNotEmpty(assetNetworkCardRequestList)) {
                        List<AssetNetworkCard> assetNetworkCardList = BeanConvert.convert(assetNetworkCardRequestList,
                            AssetNetworkCard.class);
                        List<AssetNetworkCard> insertNetworkList = Lists.newArrayList();
                        List<AssetNetworkCard> updateNetworkList = Lists.newArrayList();
                        for (AssetNetworkCard assetNetworkCard : assetNetworkCardList) {
                            assetNetworkCard.setStatus(1);
                            assetNetworkCard.setAssetId(asset.getStringId());
                            // 修改的
                            if (StringUtils.isNotBlank(assetNetworkCard.getStringId())) {

                                AssetNetworkCard byId = assetNetworkCardDao.getById(assetNetworkCard.getStringId());
                                if (!byId.getIpAddress().equals(assetNetworkCard.getIpAddress())) {
                                    ParamterExceptionUtils.isTrue(!CheckRepeatIp(assetNetworkCard.getIpAddress(), null),
                                        "IP不能重复！");
                                    List<Integer> integers = new ArrayList<>();
                                    integers.add(Integer.parseInt(asset.getStringId()));
                                    assetLinkRelationDao.deleteRelationByAssetId(integers);
                                }

                                assetNetworkCard.setModifyUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                assetNetworkCard.setGmtModified(System.currentTimeMillis());
                                updateNetworkList.add(assetNetworkCard);
                            } else {// 新增的
                                ParamterExceptionUtils.isTrue(!CheckRepeatIp(assetNetworkCard.getIpAddress(), null),
                                    "IP不能重复！");
                                assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                                assetNetworkCard.setCreateUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                insertNetworkList.add(assetNetworkCard);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(insertNetworkList)) {
                            LogHandle.log(insertNetworkList, AssetEventEnum.ASSET_NETWORK_INSERT.getName(),
                                AssetEventEnum.ASSET_NETWORK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_INSERT.getName() + " {}",
                                insertNetworkList.toString());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_INSERT.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            assetNetworkCardDao.insertBatch(insertNetworkList);
                        }
                        if (CollectionUtils.isNotEmpty(updateNetworkList)) {
                            LogHandle.log(updateNetworkList, AssetEventEnum.ASSET_NETWORK_UPDATE.getName(),
                                AssetEventEnum.ASSET_NETWORK_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_UPDATE.getName() + " {}",
                                updateNetworkList.toString());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_UPDATE.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            assetNetworkCardDao.updateBatch(updateNetworkList);
                        }
                        insertNetworkList.addAll(updateNetworkList);
                        assetOuterRequest
                            .setNetworkCard(BeanConvert.convert(insertNetworkList, AssetNetworkCardRequest.class));
                    }
                    // 4. 更新主板信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_MAINBORAD_DELETE.getName(),
                        AssetEventEnum.ASSET_MAINBORAD_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils
                        .recordOperLog(new BusinessData(AssetEventEnum.ASSET_MAINBORAD_DELETE.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_DELETE.getName() + " {}", asset.getStringId());
                    assetMainboradDao.deleteByAssetId(asset.getId());
                    List<AssetMainboradRequest> assetMainboradRequest = assetOuterRequest.getMainboard();
                    if (CollectionUtils.isNotEmpty(assetMainboradRequest)) {
                        List<AssetMainborad> assetMainborad = BeanConvert.convert(assetMainboradRequest,
                            AssetMainborad.class);
                        List<AssetMainborad> insertMainboradList = Lists.newArrayList();
                        List<AssetMainborad> updateMainboradList = Lists.newArrayList();
                        for (AssetMainborad mainborad : assetMainborad) {
                            mainborad.setStatus(1);
                            mainborad.setAssetId(asset.getStringId());
                            // 修改的
                            if (StringUtils.isNotBlank(mainborad.getStringId())) {
                                mainborad.setModifyUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                mainborad.setGmtModified(System.currentTimeMillis());
                                updateMainboradList.add(mainborad);
                            } else {// 新增的
                                mainborad.setGmtCreate(System.currentTimeMillis());
                                mainborad.setCreateUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                insertMainboradList.add(mainborad);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(insertMainboradList)) {
                            LogHandle.log(insertMainboradList, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
                                AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName() + " {}",
                                insertMainboradList.toString());
                            assetMainboradDao.insertBatch(insertMainboradList);
                        }
                        if (CollectionUtils.isNotEmpty(updateMainboradList)) {
                            LogHandle.log(updateMainboradList, AssetEventEnum.ASSET_MAINBORAD_UPDATE.getName(),
                                AssetEventEnum.ASSET_MAINBORAD_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MAINBORAD_UPDATE.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_UPDATE.getName() + " {}",
                                updateMainboradList.toString());
                            assetMainboradDao.updateBatch(updateMainboradList);
                        }
                        insertMainboradList.addAll(updateMainboradList);
                        assetOuterRequest
                            .setMainboard(BeanConvert.convert(insertMainboradList, AssetMainboradRequest.class));
                    }
                    // 5. 更新内存信息
                    List<AssetMemoryRequest> assetMemoryRequestList = assetOuterRequest.getMemory();
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_MEMORY_DELETE.getName(),
                        AssetEventEnum.ASSET_MEMORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_DELETE.getName(), asset.getId(),
                        asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_DELETE.getName() + " {}", asset.getStringId());
                    assetMemoryDao.deleteByAssetId(asset.getId());
                    if (CollectionUtils.isNotEmpty(assetMemoryRequestList)) {
                        List<AssetMemory> assetMemoryList = BeanConvert.convert(assetMemoryRequestList,
                            AssetMemory.class);
                        List<AssetMemory> insertMemoryList = Lists.newArrayList();
                        List<AssetMemory> updateMemoryList = Lists.newArrayList();
                        for (AssetMemory memory : assetMemoryList) {
                            memory.setStatus(1);
                            memory.setAssetId(asset.getStringId());
                            // 修改的
                            if (StringUtils.isNotBlank(memory.getStringId())) {
                                memory.setModifyUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                memory.setGmtModified(System.currentTimeMillis());
                                updateMemoryList.add(memory);
                            } else {// 新增的
                                memory.setGmtCreate(System.currentTimeMillis());
                                memory.setCreateUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                insertMemoryList.add(memory);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(insertMemoryList)) {
                            LogHandle.log(insertMemoryList, AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
                                AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_INSERT.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_INSERT.getName() + " {}",
                                insertMemoryList.toString());
                            assetMemoryDao.insertBatch(insertMemoryList);
                        }
                        if (CollectionUtils.isNotEmpty(updateMemoryList)) {
                            LogHandle.log(updateMemoryList, AssetEventEnum.ASSET_MEMORY_UPDATE.getName(),
                                AssetEventEnum.ASSET_MEMORY_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_MEMORY_UPDATE.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_MEMORY_UPDATE.getName() + " {}",
                                updateMemoryList.toString());
                            assetMemoryDao.updateBatch(updateMemoryList);
                        }
                        insertMemoryList.addAll(updateMemoryList);
                        assetOuterRequest.setMemory(BeanConvert.convert(insertMemoryList, AssetMemoryRequest.class));
                    }
                    // 6. 更新硬盘信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_DISK_DELETE.getName(),
                        AssetEventEnum.ASSET_DISK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DISK_DELETE.getName(), asset.getId(),
                        asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_DISK_DELETE.getName() + " {}", asset.getStringId());
                    assetHardDiskDao.deleteByAssetId(asset.getId());
                    List<AssetHardDiskRequest> assetHardDiskRequestList = assetOuterRequest.getHardDisk();
                    if (CollectionUtils.isNotEmpty(assetHardDiskRequestList)) {
                        List<AssetHardDisk> assetHardDiskList = BeanConvert.convert(assetHardDiskRequestList,
                            AssetHardDisk.class);
                        List<AssetHardDisk> insertHardDiskList = Lists.newArrayList();
                        List<AssetHardDisk> updateHardDiskList = Lists.newArrayList();
                        for (AssetHardDisk assetHardDisk : assetHardDiskList) {
                            assetHardDisk.setStatus(1);
                            assetHardDisk.setAssetId(asset.getStringId());
                            // 修改的
                            if (StringUtils.isNotBlank(assetHardDisk.getStringId())) {
                                assetHardDisk.setModifyUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                assetHardDisk.setGmtModified(System.currentTimeMillis());
                                updateHardDiskList.add(assetHardDisk);
                            } else {// 新增的
                                assetHardDisk.setGmtCreate(System.currentTimeMillis());
                                assetHardDisk.setCreateUser(
                                    LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                                insertHardDiskList.add(assetHardDisk);
                            }

                        }
                        if (CollectionUtils.isNotEmpty(insertHardDiskList)) {
                            LogHandle.log(insertHardDiskList, AssetEventEnum.ASSET_DISK_INSERT.getName(),
                                AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DISK_INSERT.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_DISK_INSERT.getName() + " {}",
                                insertHardDiskList.toString());
                            assetHardDiskDao.insertBatch(insertHardDiskList);
                        }
                        if (CollectionUtils.isNotEmpty(updateHardDiskList)) {
                            LogHandle.log(updateHardDiskList, AssetEventEnum.ASSET_DISK_UPDATE.getName(),
                                AssetEventEnum.ASSET_DISK_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_DISK_UPDATE.getName(),
                                asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                                BusinessPhaseEnum.WAIT_SETTING));
                            LogUtils.info(logger, AssetEventEnum.ASSET_DISK_UPDATE.getName() + " {}",
                                updateHardDiskList.toString());
                            assetHardDiskDao.updateBatch(updateHardDiskList);
                        }
                        insertHardDiskList.addAll(updateHardDiskList);
                        assetOuterRequest
                            .setHardDisk(BeanConvert.convert(insertHardDiskList, AssetHardDiskRequest.class));
                    }
                    // 7. 更新网络设备信息
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getName(),
                        AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.recordOperLog(
                        new BusinessData(AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getName(), asset.getId(),
                            asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_SETTING));
                    LogUtils.info(logger, AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getName() + " {}",
                        asset.getStringId());
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();
                    if (networkEquipment != null && StringUtils.isNotBlank(networkEquipment.getId())) {
                        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipment,
                            AssetNetworkEquipment.class);
                        // ip 变更，不重复 ，且删除关联关系
                        AssetNetworkEquipment byId = assetNetworkEquipmentDao.getById(networkEquipment.getId());
                        if (!byId.getInnerIp().equals(networkEquipment.getInnerIp())) {
                            ParamterExceptionUtils.isTrue(!CheckRepeatIp(assetNetworkEquipment.getInnerIp(), 1),
                                "内网IP不能重复！");
                            List<Integer> integers = new ArrayList<>();
                            integers.add(Integer.parseInt(asset.getStringId()));
                            assetLinkRelationDao.deleteRelationByAssetId(integers);
                        }

                        assetNetworkEquipment.setAssetId(asset.getStringId());
                        assetNetworkEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
                        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        assetNetworkEquipment.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetNetworkEquipment, AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getName(),
                            AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_NETWORK_DETAIL_UPDATE.getName(),
                            asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                            BusinessPhaseEnum.WAIT_SETTING));
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
                        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_SAFE_DETAIL_UPDATE.getName(),
                            asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                            BusinessPhaseEnum.WAIT_SETTING));
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
                        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_STORAGE_UPDATE.getName(),
                            asset.getId(), asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET,
                            BusinessPhaseEnum.WAIT_SETTING));
                        LogUtils.info(logger, AssetEventEnum.ASSET_STORAGE_UPDATE.getName() + " {}",
                            assetStorageMedium.toString());
                        assetStorageMediumDao.update(assetStorageMedium);
                    }
                    // 10. 更新资产软件关系信息
                    // 删除已有资产软件关系
                    // assetSoftwareRelationDao.deleteByAssetId(asset.getId());
                    // // 删除软件许可
                    // assetSoftwareLicenseDao.deleteByAssetId(asset.getId());
                    // if (CollectionUtils.isNotEmpty(assetOuterRequest.getAssetSoftwareRelationList())) {
                    // List<AssetSoftwareRelation> assetSoftwareRelationList = BeanConvert
                    // .convert(assetOuterRequest.getAssetSoftwareRelationList(), AssetSoftwareRelation.class);
                    // assetSoftwareRelationList.stream().forEach(relation -> {
                    // relation.setAssetId(asset.getStringId());
                    // relation.setGmtCreate(System.currentTimeMillis());
                    // // relation.setSoftwareStatus();
                    // relation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    // try {
                    // // 插入资产软件关系
                    // assetSoftwareRelationDao.insert(relation);
                    // AssetSoftwareLicense assetSoftwareLicense = new AssetSoftwareLicense();
                    // assetSoftwareLicense.setLicenseSecretKey(relation.getLicenseSecretKey());
                    // assetSoftwareLicense.setSoftwareId(relation.getStringId());
                    // // 插入资产软件许可
                    // assetSoftwareLicenseDao.insert(assetSoftwareLicense);
                    // } catch (Exception e) {
                    // logger.error(e.getMessage());
                    // }
                    // });
                    // }
                    // 记录资产变更记录
                    AssetChangeRecord assetChangeRecord = new AssetChangeRecord();
                    assetChangeRecord.setType(1);
                    assetChangeRecord.setStatus(1);
                    assetChangeRecord.setBusinessId(asset.getId());
                    assetChangeRecord.setChangeVal(JSONObject.toJSONString(assetOuterRequest));
                    assetChangeRecord.setIsStore(1);
                    assetChangeRecord
                        .setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                    assetChangeRecord.setGmtCreate(System.currentTimeMillis());
                    assetChangeRecordDao.insert(assetChangeRecord);
                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(asset.getStringId());
                    assetOperationRecord.setOriginStatus(
                        asset.getStatus() == null ? AssetStatusEnum.WATI_REGSIST.getCode() : asset.getStatus());
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    assetOperationRecord.setContent(AssetEventEnum.ASSET_MODIFY.getName());
                    assetOperationRecord
                        .setCreateUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0);
                    assetOperationRecord.setOperateUserName(
                        LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getName() : "");
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecord.setProcessResult(1);
                    assetOperationRecord.setAreaId(asset.getAreaId());
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return count;

                } catch (Exception e) {
                    logger.info("资产变更失败:", e);
                    transactionStatus.setRollbackOnly();
                    ParamterExceptionUtils.isTrue(!StringUtils.equals("IP不能重复！", e.getMessage()), "IP不能重复！");
                    ParamterExceptionUtils.isTrue(!StringUtils.equals("内网IP不能重复！", e.getMessage()), "内网IP不能重复！");
                    throw new BusinessException("资产变更失败");
                }
            }
        });
        // 工作流
        ManualStartActivityRequest manualStartActivityRequest = assetOuterRequest.getManualStartActivityRequest();
        if (!Objects.isNull(manualStartActivityRequest)) {
            // 其他设备
            if (assetOuterRequest.getAssetOthersRequest() != null) {
                manualStartActivityRequest.setBusinessId(assetOuterRequest.getAssetOthersRequest().getId());
            } else {
                manualStartActivityRequest.setBusinessId(assetOuterRequest.getAsset().getId());
            }
            manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_CHANGE.getCode());
            // 启动流程
            activityClient.manualStartProcess(manualStartActivityRequest);
        } else {
            // 处理流程
            activityClient.completeTask(assetOuterRequest.getActivityHandleRequest());
        }
        // TODO 下发智甲
        return assetCount;
    }

    /**
     * 导出后的文件格式为.zip压缩包
     * @param types 导出模板的类型
     */
    @Override
    public void exportTemplate(Integer[] types) throws Exception {
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getNextLevelCategoryByName("硬件");
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
        Map<Integer, AssetCategoryModel> categoryModelMap = new HashMap<>();
        categoryModelList.forEach(x -> categoryModelMap.put(x.getId(), x));
        int m = 0;
        for (Integer type : types) {
            AssetCategoryModel assetCategoryModel = categoryModelMap.get(type);
            if (Objects.nonNull(assetCategoryModel)) {
                // 生成模板文件
                String categoryName = exportTemplate(dictionary + "/", assetCategoryModel);
                files[m++] = new File(dictionary + "/" + categoryName + "信息模板.xlsx");
                logger.info(files[m - 1].getName() + "文件创建成功");
            } else {
                // 输入参数有错，删除临时文件
                deleteTemp(dictionaryFile, files);
                throw new BusinessException("存在错误的品类ID");
            }
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

    private String exportTemplate(String dictionary, AssetCategoryModel assetCategoryModel) {
        String categoryName = assetCategoryModel.getName();
        // 根据不同的品类名生成不同的模板
        switch (categoryName) {
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
            case "其他设备":
                exportOtherTemplate(dictionary);
                break;
        }

        return categoryName;
    }

    private void exportOtherTemplate(String dictionary) {
        // 初始化其它设备示例数据
        List<OtherDeviceEntity> dataList = initOtherData();
        ExcelUtils.exportTemplateToFile(OtherDeviceEntity.class, "其他设备信息模板.xlsx", "其他设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败", dictionary + "/", dataList);
    }

    private List<OtherDeviceEntity> initOtherData() {
        List<OtherDeviceEntity> dataList = new ArrayList<>();
        OtherDeviceEntity otherDeviceEntity = new OtherDeviceEntity();
        otherDeviceEntity.setArea("四川省");
        otherDeviceEntity.setBuyDate(System.currentTimeMillis());
        otherDeviceEntity.setDueDate(System.currentTimeMillis());
        otherDeviceEntity.setUser("留小查");
        otherDeviceEntity.setTelephone("13541771234");
        otherDeviceEntity.setSerial("ANFRWGDFETYRYF");
        otherDeviceEntity.setEmail("32535694@163.com");
        otherDeviceEntity.setName("触摸查询一体机");
        otherDeviceEntity.setMemo("宣传展览导视查询畅销触控一体机，采用FULL HD全视角高清IPS硬屏");
        otherDeviceEntity.setManufacturer("捷显");
        otherDeviceEntity.setWarranty(System.currentTimeMillis());
        otherDeviceEntity.setNumber("000001");
        otherDeviceEntity.setImportanceDegree("1");
        dataList.add(otherDeviceEntity);
        return dataList;
    }

    private void exportSafetyTemplate(String dictionary) {
        // 初始化安全设备示例数据
        List<SafetyEquipmentEntiy> dataList = initSafetyData();
        ExcelUtils.exportTemplateToFile(SafetyEquipmentEntiy.class, "安全设备信息模板.xlsx", "安全设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败", dictionary + "/", dataList);
    }

    private List<SafetyEquipmentEntiy> initSafetyData() {
        List<SafetyEquipmentEntiy> dataList = new ArrayList<>();
        SafetyEquipmentEntiy safetyEquipmentEntiy = new SafetyEquipmentEntiy();
        safetyEquipmentEntiy.setArea("四川省");
        safetyEquipmentEntiy.setBuyDate(System.currentTimeMillis());
        safetyEquipmentEntiy.setDueDate(System.currentTimeMillis());
        safetyEquipmentEntiy.setEmail("32535694@163.com");
        safetyEquipmentEntiy.setFirmwareVersion("2.0");
        safetyEquipmentEntiy.setWarranty(System.currentTimeMillis());
        safetyEquipmentEntiy.setManufacturer("安天");
        safetyEquipmentEntiy.setName("安天镇关威胁阻断系统   ");
        safetyEquipmentEntiy
            .setMemo("镇关采用高性能软硬件架构，以智能用户识别与智能应用识别为基础，实现了完全" + "以用户和应用为中心的控制策略，先进的APT防护技术能够有效防范0day攻击和社工渗透等新型威胁。");
        safetyEquipmentEntiy.setMac("00-01-6C-06-A6-29");
        safetyEquipmentEntiy.setTelephone("13541771234");
        safetyEquipmentEntiy.setNumber("00001");
        safetyEquipmentEntiy.setIp("192.168.1.9");
        safetyEquipmentEntiy.setUser("留小查");
        safetyEquipmentEntiy.setManufacturer("安天");
        safetyEquipmentEntiy.setSerial("ANFRWGDFETYRYF");
        safetyEquipmentEntiy.setLocation("成都市青羊区");
        safetyEquipmentEntiy.setHouseLocation("501机房004号");
        safetyEquipmentEntiy.setImportanceDegree("1");
        dataList.add(safetyEquipmentEntiy);
        return dataList;
    }

    private void exportStorageTemplate(String dictionary) {
        // 初始化存储设备示例数据
        List<StorageDeviceEntity> dataList = initStorageData();
        ExcelUtils.exportTemplateToFile(StorageDeviceEntity.class, "存储设备信息模板.xlsx", "存储设备",
            "备注：时间填写规范统一为XXXX/XX/XX，必填项必须填写，否则会插入失败", dictionary + "/", dataList);
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
        storageDeviceEntity.setEmail("32535694@163.com");
        storageDeviceEntity.setHardDiskNum(1);
        storageDeviceEntity.setFirmware("spi1");
        storageDeviceEntity.setHighCache("6GB/S");
        storageDeviceEntity.setHouseLocation("501机房004号");
        storageDeviceEntity.setManufacturer("联想");
        storageDeviceEntity.setMemo("私有云网络存储器nas文件共享数字备份 AS6202T 0TB 标机");
        storageDeviceEntity.setName("华芸AS6202T");
        storageDeviceEntity.setInnerInterface("SAS ，SSD");
        storageDeviceEntity.setNumber("000001");
        storageDeviceEntity.setSlotType("1");
        storageDeviceEntity.setTelephone("13541771234");
        storageDeviceEntity.setWarranty(System.currentTimeMillis());
        storageDeviceEntity.setRaidSupport("0，1，5，6，10 ");
        storageDeviceEntity.setUser("留小查");
        storageDeviceEntity.setSerial("ANFRWGDFETYRYF");
        storageDeviceEntity.setLocation("成都市青羊区");
        storageDeviceEntity.setImportanceDegree("1");
        dataList.add(storageDeviceEntity);
        return dataList;

    }

    private void exportNetworkTemplate(String dictionary) {
        // 初始化网络设备示例数据
        List<NetworkDeviceEntity> dataList = initNetworkData();
        ExcelUtils.exportTemplateToFile(NetworkDeviceEntity.class, "网络设备信息模板.xlsx", "网络设备",
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败", dictionary + "/", dataList);
    }

    private List<NetworkDeviceEntity> initNetworkData() {
        List<NetworkDeviceEntity> dataList = new ArrayList<>();
        NetworkDeviceEntity networkDeviceEntity = new NetworkDeviceEntity();
        networkDeviceEntity.setWarranty(System.currentTimeMillis());
        networkDeviceEntity.setButDate(System.currentTimeMillis());
        networkDeviceEntity.setCpuSize(4);
        networkDeviceEntity.setArea("成都市");
        networkDeviceEntity.setMac("00-01-6C-06-A6-29");
        networkDeviceEntity.setName("YTW-600-5A五端口迷你型网络延长器");
        networkDeviceEntity.setDramSize(3.42f);
        networkDeviceEntity.setEmail("32535694@163.com");
        networkDeviceEntity.setExpectBandwidth(4);
        networkDeviceEntity.setTelephone("13541771234");
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
        networkDeviceEntity.setLocation("成都市青羊区");
        networkDeviceEntity.setNcrmSize(4.22f);
        networkDeviceEntity.setOuterIp("192.168.1.9");
        networkDeviceEntity.setInnerIp("192.168.1.1");
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
            "备注：时间填写规范统一为XXXX/XX/XX(不用补0)，必填项必须填写，否则会插入失败,部件信息选填,但若填写了某一部件，则必须填写该部件的必填项", dictionary + "/", dataList);
    }

    private List<ComputeDeviceEntity> initComputeData() {
        List<ComputeDeviceEntity> dataList = new ArrayList<>();
        ComputeDeviceEntity computeDeviceEntity = new ComputeDeviceEntity();
        computeDeviceEntity.setEmail("32535694@163.com");
        computeDeviceEntity.setArea("成都市");
        computeDeviceEntity.setName("ThinkPad X1 隐士");
        computeDeviceEntity.setLocation("成都市锦江区");
        computeDeviceEntity.setWarranty(System.currentTimeMillis());
        computeDeviceEntity.setDescription("搭载第八代英特尔®酷睿TM i7处理器，配备双内存插槽，最高支持64GB内存扩展");
        computeDeviceEntity.setDueTime(System.currentTimeMillis());
        computeDeviceEntity.setImportanceDegree("1");
        computeDeviceEntity.setSerial("ADES-WRGD-EREW-TERF");
        computeDeviceEntity.setHouseLocation("501机房004号");
        computeDeviceEntity.setWarranty(System.currentTimeMillis());
        computeDeviceEntity.setTelephone("13541771234");
        computeDeviceEntity.setNumber("000001");
        computeDeviceEntity.setUser("留小查");
        computeDeviceEntity.setBuyDate(System.currentTimeMillis());
        computeDeviceEntity.setTransferType(1);
        computeDeviceEntity.setSlotType(2);
        computeDeviceEntity.setHeatsink(2);
        computeDeviceEntity.setFirmwareVersion("1.3.2");
        computeDeviceEntity.setDueTime(System.currentTimeMillis());
        computeDeviceEntity.setStitch(1);
        computeDeviceEntity.setManufacturer("联想");
        computeDeviceEntity.setOperationSystem("Window 10");

        computeDeviceEntity.setCpuBrand("英特尔酷睿");
        computeDeviceEntity.setCpuCoreSize(4);
        computeDeviceEntity.setCpuMainFrequency(1.34f);
        computeDeviceEntity.setCpuModel("i7");
        computeDeviceEntity.setCpuSerial("WERD-fw2F-r3R2-Qee2");
        computeDeviceEntity.setCpuThreadSize(1);
        computeDeviceEntity.setCpuNum(1);

        computeDeviceEntity.setHardDiskBrand("Lenovo");
        computeDeviceEntity.setHardDisCapacityl(1);
        computeDeviceEntity.setHardDiskInterfaceType(1);
        computeDeviceEntity.setHardDiskSerial("Pd2d-d2E1-er2E-2eE2");
        computeDeviceEntity.setHardDiskBuyDate(System.currentTimeMillis());
        computeDeviceEntity.setHardDiskType(2);
        computeDeviceEntity.setHardDiskModel("数据存储硬盘");
        computeDeviceEntity.setHardDiskNum(1);

        computeDeviceEntity.setNetworkBrand("intel");
        computeDeviceEntity.setNetworkDefaultGateway("192.168.1.1");
        computeDeviceEntity.setNetworkIpAddress("192.168.1.9");
        computeDeviceEntity.setNetworkMacAddress("00-01-6C-06-A6-29");
        computeDeviceEntity.setNetworkModel("全双工/半双工自适应");
        computeDeviceEntity.setNetworkSerial("WDTEF2EER3IYVN");
        computeDeviceEntity.setNetworkSubnetMask("255.255.252.0");

        computeDeviceEntity.setMainboradBiosDate(System.currentTimeMillis());
        computeDeviceEntity.setMainboradBiosVersion("3.2.3");
        computeDeviceEntity.setMainboradBrand("微星（MSI）");
        computeDeviceEntity.setMainboradModel("X299 GAMING PRO CARBON AC");
        computeDeviceEntity.setMainboradSerial("WFETDHYTEGHD");
        computeDeviceEntity.setMainboradNum(1);

        computeDeviceEntity.setMemoryBrand("影驰GAMER");
        computeDeviceEntity.setMemoryFrequency(2.32);
        computeDeviceEntity.setMemorySerial("dpfjwmadnwge");
        computeDeviceEntity.setMemoryCapacity(5);
        computeDeviceEntity.setHeatsink(1);
        computeDeviceEntity.setMemoryNum(1);

        dataList.add(computeDeviceEntity);
        return dataList;
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

    private void loggerIsDelete(File file) {
        logger.info(file.getName() + "文件删除" + isSuccess(file.delete()));
    }

    private String isSuccess(Boolean isDelete) {
        return isDelete ? "成功" : "失败";
    }

    @Override
    public String importPc(MultipartFile file, AssetImportRequest importRequest) throws Exception {

        // String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
        // DataTypeUtils.stringToInteger(importRequest.getAreaId()));
        //
        // SysArea sysArea = redisUtil.getObject(key, SysArea.class);
        // if (Objects.isNull(sysArea)) {
        // return "上传失败，选择区域不存在，或已被注销！";
        // }
        ImportResult<ComputeDeviceEntity> result = ExcelUtils.importExcelFromClient(ComputeDeviceEntity.class, file, 5,
            0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<ComputerVo> computerVos = new ArrayList<>();
        List<ComputeDeviceEntity> dataList = result.getDataList();
        if (dataList.size() == 0) {
            return result.getMsg();
        }
        for (ComputeDeviceEntity entity : dataList) {

            if (CheckRepeatName(entity.getName())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产名称重复，");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复，");
                continue;
            }
            if (CheckRepeatIp(entity.getNetworkIpAddress(), null)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产网卡IP地址重复，");
                continue;
            }

            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("没有此使用者，");
                continue;
            }

            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
            }
            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，");
                continue;
            }

            if (repeat + error == 0) {
                ComputerVo computerVo = new ComputerVo();
                Asset asset = new Asset();
                asset.setResponsibleUserId(CheckUser(entity.getUser()));
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(importRequest.getAreaId());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setResponsibleUserId(Objects.toString(LoginUserUtil.getLoginUser().getId()));
                asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
                asset.setAssetSource(ReportType.AUTOMATIC.getCode());
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
                computerVo.setAsset(asset);

                if (StringUtils.isNotBlank(entity.getMemoryBrand()) && !Objects.isNull(entity.getMemoryCapacity())
                    && !Objects.isNull(entity.getMemoryFrequency()) && !Objects.isNull(entity.getMemoryNum())
                    && entity.getMemoryNum() > 0) {
                    AssetMemory assetMemory = new AssetMemory();
                    // assetMemory.setAssetId(id);
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
                    assetMemory.setSlotType(entity.getSlotType());
                    List<AssetMemory> assetMemoryList = new ArrayList<>();

                    for (int i = 0; i < entity.getMemoryNum(); i++) {
                        assetMemoryList.add(assetMemory);
                    }
                    computerVo.setAssetMemoryList(assetMemoryList);
                    // assetMemoryDao.insertBatch(assetMemoryList);
                }
                // else {
                // builder.append("序号").append(a).append("行").append ("没有添加内存：内存品牌，内存容量，内存主频，内存数量>0")
                // }

                if (StringUtils.isNotBlank(entity.getHardDiskBrand()) && !Objects.isNull(entity.getHardDisCapacityl())
                    && !Objects.isNull(entity.getHardDiskType()) && !Objects.isNull(entity.getHardDiskNum())
                    && entity.getHardDiskNum() > 0) {
                    AssetHardDisk assetHardDisk = new AssetHardDisk();
                    // assetHardDisk.setAssetId(id);
                    assetHardDisk.setGmtCreate(System.currentTimeMillis());
                    assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetHardDisk.setSerial(entity.getHardDiskSerial());
                    assetHardDisk.setBrand(entity.getHardDiskBrand());
                    assetHardDisk.setModel(entity.getHardDiskModel());
                    assetHardDisk.setCapacity(entity.getHardDisCapacityl());
                    assetHardDisk.setBuyDate(entity.getHardDiskBuyDate());
                    assetHardDisk.setInterfaceType(entity.getHardDiskInterfaceType());
                    assetHardDisk.setDiskType(entity.getHardDiskType());

                    List<AssetHardDisk> assetHardDisks = new ArrayList<>();
                    for (int i = 0; i < entity.getHardDiskNum(); i++) {
                        assetHardDisks.add(assetHardDisk);
                    }
                    computerVo.setAssetHardDisks(assetHardDisks);
                    // assetHardDiskDao.insertBatch(assetHardDisks);

                }

                if (StringUtils.isNotBlank(entity.getMainboradBrand()) && !Objects.isNull(entity.getMainboradNum())
                    && entity.getMainboradNum() > 0) {

                    AssetMainborad assetMainborad = new AssetMainborad();
                    // assetMainborad.setAssetId(id);
                    assetMainborad.setGmtCreate(System.currentTimeMillis());
                    assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetMainborad.setSerial(entity.getMainboradSerial());
                    assetMainborad.setBrand(entity.getMainboradBrand());
                    assetMainborad.setModel(entity.getMainboradModel());
                    assetMainborad.setBiosVersion(entity.getMainboradBiosVersion());
                    assetMainborad.setBiosDate(entity.getMainboradBiosDate());
                    List<AssetMainborad> assetMainborads = new ArrayList<>();
                    for (int i = 0; i < entity.getMainboradNum(); i++) {
                        assetMainborads.add(assetMainborad);
                    }
                    computerVo.setAssetMainborads(assetMainborads);
                    // assetMainboradDao.insertBatch(assetMainborads);
                }
                if (StringUtils.isNotBlank(entity.getCpuBrand()) && !Objects.isNull(entity.getCpuNum())
                    && entity.getCpuNum() > 0 && !Objects.isNull(entity.getCpuMainFrequency())) {
                    AssetCpu assetCpu = new AssetCpu();
                    // assetCpu.setAssetId(id);
                    assetCpu.setGmtCreate(System.currentTimeMillis());
                    assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetCpu.setSerial(entity.getCpuSerial());
                    assetCpu.setBrand(entity.getCpuBrand());
                    assetCpu.setModel(entity.getCpuModel());
                    assetCpu.setMainFrequency(entity.getCpuMainFrequency());
                    assetCpu.setThreadSize(entity.getCpuThreadSize());
                    assetCpu.setCoreSize(entity.getCpuCoreSize());
                    List<AssetCpu> assetCpus = new ArrayList<>();
                    for (int i = 0; i < entity.getCpuNum(); i++) {
                        assetCpus.add(assetCpu);
                    }
                    computerVo.setAssetCpus(assetCpus);
                    // assetCpuDao.insertBatch(assetCpus);
                }

                AssetNetworkCard assetNetworkCard = new AssetNetworkCard();
                assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                assetNetworkCard.setSerial(entity.getNetworkSerial());
                assetNetworkCard.setBrand(entity.getNetworkBrand());
                assetNetworkCard.setModel(entity.getNetworkModel());
                assetNetworkCard.setSubnetMask(entity.getNetworkSubnetMask());
                assetNetworkCard.setDefaultGateway(entity.getNetworkDefaultGateway());
                assetNetworkCard.setIpAddress(entity.getNetworkIpAddress());
                assetNetworkCard.setMacAddress(entity.getNetworkMacAddress());
                computerVo.setAssetNetworkCard(assetNetworkCard);

                computerVos.add(computerVo);
            }

            a++;
        }

        if (repeat + error == 0) {
            List<ManualStartActivityRequest> manualStartActivityRequests = new ArrayList<>();
            for (ComputerVo computerVo : computerVos) {
                Asset asset = computerVo.getAsset();
                assetDao.insert(asset);
                if (CollectionUtils.isNotEmpty(computerVo.getAssetCpus())) {

                    assetCpuDao.insertBatchWithId(computerVo.getAssetCpus(), asset.getId());
                }
                if (CollectionUtils.isNotEmpty(computerVo.getAssetHardDisks())) {

                    assetHardDiskDao.insertBatchWithId(computerVo.getAssetHardDisks(), asset.getId());
                }
                if (CollectionUtils.isNotEmpty(computerVo.getAssetMainborads())) {

                    assetMainboradDao.insertBatchWithId(computerVo.getAssetMainborads(), asset.getId());
                }
                if (CollectionUtils.isNotEmpty(computerVo.getAssetMemoryList())) {

                    assetMemoryDao.insertBatchWithId(computerVo.getAssetMemoryList(), asset.getId());
                }
                if (!Objects.isNull(computerVo.getAssetNetworkCard())) {
                    AssetNetworkCard assetNetworkCard = computerVo.getAssetNetworkCard();
                    assetNetworkCard.setAssetId(asset.getStringId());
                    assetNetworkCardDao.insert(assetNetworkCard);
                }
                // 记录资产操作流程
                assetRecord(asset.getStringId());
                // 流程
                importActivity(manualStartActivityRequests, asset.getStringId());
                success++;
            }
            activityClient.startProcessWithoutFormBatch(manualStartActivityRequests);
        }
        // 写入业务日志
        LogHandle.log(computerVos.toString(), AssetEventEnum.ASSET_EXPORT_COMPUTER.getName(),
            AssetEventEnum.ASSET_EXPORT_NET.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_COMPUTER.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_COMPUTER.getName() + " {}", computerVos.toString());

        String res = "导入成功" + success + "条。";
        // re += repeat > 0 ? ", " + repeat + "条编号重复" : ",";
        // re += error > 0 ? ", " + error + "条数据导入失败" : ",";
        StringBuilder stringBuilder = new StringBuilder(res);
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
        ImportResult<NetworkDeviceEntity> result = ExcelUtils.importExcelFromClient(NetworkDeviceEntity.class, file, 5,
            0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<Asset> assets = new ArrayList<>();
        List<AssetNetworkEquipment> networkEquipments = new ArrayList<>();
        List<NetworkDeviceEntity> entities = result.getDataList();
        if (entities.size() == 0) {
            return result.getMsg();
        }
        for (NetworkDeviceEntity networkDeviceEntity : entities) {

            if (CheckRepeatName(networkDeviceEntity.getName())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产名称重复，");
                continue;
            }

            if (CheckRepeat(networkDeviceEntity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复，");
                continue;
            }

            if (CheckRepeatIp(networkDeviceEntity.getInnerIp(), 1)) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产内网IP地址重复，");
                continue;
            }

            if ("".equals(CheckUser(networkDeviceEntity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("没有此使用者，");
                continue;
            }
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
            }
            if (!areasStrings.contains(networkDeviceEntity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，");
                continue;
            }

            if (repeat + error == 0) {

                Asset asset = new Asset();
                asset.setResponsibleUserId(CheckUser(networkDeviceEntity.getUser()));
                AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(importRequest.getAreaId());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
                asset.setAssetSource(ReportType.AUTOMATIC.getCode());
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
                assets.add(asset);
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
                networkEquipments.add(assetNetworkEquipment);
            }

            a++;

        }

        if (repeat + error == 0) {
            List<ManualStartActivityRequest> manualStartActivityRequests = new ArrayList<>();
            for (int i = 0; i < assets.size(); i++) {
                assetDao.insert(assets.get(i));
                String stringId = assets.get(i).getStringId();
                networkEquipments.get(i).setAssetId(stringId);
                assetNetworkEquipmentDao.insert(networkEquipments.get(i));
                assetRecord(stringId);
                // 流程
                importActivity(manualStartActivityRequests, stringId);

                success++;
            }
            activityClient.startProcessWithoutFormBatch(manualStartActivityRequests);
        }
        // 写入业务日志
        LogHandle.log(networkEquipments.toString(), AssetEventEnum.ASSET_EXPORT_NET.getName(),
            AssetEventEnum.ASSET_EXPORT_NET.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_NET.getName() + " {}", networkEquipments.toString());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_NET.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        String re = "导入成功" + success + "条。";
        // re += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        // re += error > 0 ? ", " + error + "条数据导入失败" : "";
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
            5, 0);

        StringBuilder builder = new StringBuilder();
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<SafetyEquipmentEntiy> resultDataList = result.getDataList();
        if (resultDataList.size() == 0) {
            return result.getMsg();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        List<Asset> assets = new ArrayList<>();
        List<AssetSafetyEquipment> assetSafetyEquipments = new ArrayList<>();
        for (SafetyEquipmentEntiy entity : resultDataList) {

            if (CheckRepeatName(entity.getName())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产名称重复，");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复，");
                continue;
            }

            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("没有此使用者，");
                continue;
            }
            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
            }
            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，");
                continue;
            }

            if (repeat + error == 0) {
                Asset asset = new Asset();
                asset.setResponsibleUserId(CheckUser(entity.getUser()));
                AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(importRequest.getAreaId());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
                asset.setAssetSource(ReportType.AUTOMATIC.getCode());
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
                assets.add(asset);
                assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
                assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetSafetyEquipment.setIp(entity.getIp());
                assetSafetyEquipment.setMemo(entity.getMemo());
                assetSafetyEquipments.add(assetSafetyEquipment);
            }
            a++;
        }

        if (repeat + error == 0) {
            List<ManualStartActivityRequest> manualStartActivityRequests = new ArrayList<>();
            for (int i = 0; i < assets.size(); i++) {
                assetDao.insert(assets.get(i));
                String stringId = assets.get(i).getStringId();
                assetSafetyEquipments.get(i).setAssetId(stringId);
                assetSafetyEquipmentDao.insert(assetSafetyEquipments.get(i));
                assetRecord(stringId);
                // 流程
                importActivity(manualStartActivityRequests, stringId);
                success++;
            }
            activityClient.startProcessWithoutFormBatch(manualStartActivityRequests);
        }
        // 写入业务日志
        LogHandle.log(assetSafetyEquipments.toString(), AssetEventEnum.ASSET_EXPORT_SAFETY.getName(),
            AssetEventEnum.ASSET_EXPORT_SAFETY.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_SAFETY.getName() + " {}", assetSafetyEquipments.toString());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_SAFETY.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        String res = "导入成功" + success + "条";
        // res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        // res += error > 0 ? ", " + error + "条数据导入失败" : "";
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

        ImportResult<StorageDeviceEntity> result = ExcelUtils.importExcelFromClient(StorageDeviceEntity.class, file, 5,
            0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<StorageDeviceEntity> resultDataList = result.getDataList();
        if (resultDataList.size() == 0) {
            // return "上传失败，模板内无数据，请填写数据后再次上传";
            return result.getMsg();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<Asset> assets = new ArrayList<>();
        List<AssetStorageMedium> assetStorageMedia = new ArrayList<>();
        for (StorageDeviceEntity entity : resultDataList) {

            if (CheckRepeatName(entity.getName())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产名称重复");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复");
                continue;
            }

            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("没有此使用者");
                continue;
            }

            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
            }
            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，");
                continue;
            }

            if (repeat + error == 0) {

                Asset asset = new Asset();
                asset.setResponsibleUserId(CheckUser(entity.getUser()));
                AssetStorageMedium assetStorageMedium = new AssetStorageMedium();
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(importRequest.getAreaId());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
                asset.setAssetSource(ReportType.AUTOMATIC.getCode());
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
                assets.add(asset);
                assetStorageMedium.setGmtCreate(System.currentTimeMillis());
                assetStorageMedium.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetStorageMedium.setFirmware(entity.getFirmware());
                assetStorageMedium.setDiskNumber(entity.getHardDiskNum());
                assetStorageMedium.setDriverNumber(entity.getDriveNum());
                assetStorageMedium.setMaximumStorage(entity.getCapacity());
                assetStorageMedium.setMemo(entity.getMemo());
                assetStorageMedium.setHighCache(entity.getHighCache());
                assetStorageMedium.setRaidSupport(entity.getRaidSupport());
                assetStorageMedium.setInnerInterface(entity.getInnerInterface());
                assetStorageMedium.setOsVersion(entity.getSlotType());
                assetStorageMedium.setAverageTransferRate(entity.getAverageTransmissionRate());
                assetStorageMedia.add(assetStorageMedium);
            }

            a++;
        }

        if (repeat + error == 0) {
            List<ManualStartActivityRequest> manualStartActivityRequests = new ArrayList<>();
            for (int i = 0; i < assets.size(); i++) {
                assetDao.insert(assets.get(i));
                String stringId = assets.get(i).getStringId();
                assetStorageMedia.get(i).setAssetId(stringId);
                assetStorageMediumDao.insert(assetStorageMedia.get(i));
                assetRecord(assets.get(i).getStringId());
                // 流程
                importActivity(manualStartActivityRequests, stringId);
                success++;
            }
            activityClient.startProcessWithoutFormBatch(manualStartActivityRequests);
        }
        // 写入业务日志
        LogHandle.log(assetStorageMedia.toString(), AssetEventEnum.ASSET_EXPORT_STORAGE.getName(),
            AssetEventEnum.ASSET_EXPORT_STORAGE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_STORAGE.getName() + " {}", assetStorageMedia.toString());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_STORAGE.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        String res = "导入成功" + success + "条";
        // res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        // res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder(res);
        // if (error + repeat > 0) {
        // stringBuilder.append("其中").append(builder);
        // }
        //
        // return stringBuilder.toString();
        StringBuilder sb = new StringBuilder(result.getMsg());
        sb.delete(sb.lastIndexOf("成"), sb.lastIndexOf("."));
        return stringBuilder.append(builder).append(sb).toString();
        // return builder.append (result.getMsg ()).toString ();
    }

    @Override
    public String importOhters(MultipartFile file, AssetImportRequest importRequest) throws Exception {

        ImportResult<OtherDeviceEntity> result = ExcelUtils.importExcelFromClient(OtherDeviceEntity.class, file, 5, 0);
        if (Objects.isNull(result.getDataList())) {
            return result.getMsg();
        }
        List<OtherDeviceEntity> resultDataList = result.getDataList();
        if (resultDataList.size() == 0) {
            return result.getMsg();
        }
        int success = 0;
        int repeat = 0;
        int error = 0;
        int a = 6;
        StringBuilder builder = new StringBuilder();
        List<Asset> assets = new ArrayList<>();
        for (OtherDeviceEntity entity : resultDataList) {

            if (CheckRepeatName(entity.getName())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产名称重复，");
                continue;
            }

            if (CheckRepeat(entity.getNumber())) {
                repeat++;
                a++;
                builder.append("第").append(a).append("行").append("资产编号重复，");
                continue;
            }
            if ("".equals(CheckUser(entity.getUser()))) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("没有此使用者，");
                continue;
            }

            List<SysArea> areas = LoginUserUtil.getLoginUser().getAreas();
            List<String> areasStrings = new ArrayList<>();
            for (SysArea area : areas) {
                areasStrings.add(area.getFullName());
            }
            if (!areasStrings.contains(entity.getArea())) {
                error++;
                a++;
                builder.append("第").append(a).append("行").append("当前用户没有此所属区域，");
                continue;
            }
            if (repeat + error == 0) {
                Asset asset = new Asset();
                asset.setResponsibleUserId(CheckUser(entity.getUser()));
                asset.setGmtCreate(System.currentTimeMillis());
                asset.setAreaId(importRequest.getAreaId());
                asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                asset.setAssetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
                asset.setAssetSource(ReportType.AUTOMATIC.getCode());
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
                assets.add(asset);
            }

            a++;
        }

        if (repeat + error == 0) {
            List<ManualStartActivityRequest> manualStartActivityRequests = new ArrayList<>();
            for (Asset asset : assets) {
                assetDao.insert(asset);
                assetRecord(asset.getStringId());
                importActivity(manualStartActivityRequests, asset.getStringId());
                success++;
            }
            activityClient.startProcessWithoutFormBatch(manualStartActivityRequests);
        }

        // 写入业务日志
        LogHandle.log(assets.toString(), AssetEventEnum.ASSET_EXPORT_OTHERS.getName(),
            AssetEventEnum.ASSET_EXPORT_OTHERS.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_EXPORT_OTHERS.getName(), 0, "", null,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.WAIT_REGISTER));
        LogUtils.info(logger, AssetEventEnum.ASSET_EXPORT_OTHERS.getName() + " {}", assets.toString());
        String res = "导入成功" + success + "条";
        // res += repeat > 0 ? ", " + repeat + "条编号重复" : "";
        // res += error > 0 ? ", " + error + "条数据导入失败" : "";
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

    private void importActivity(List<ManualStartActivityRequest> manualStartActivityRequests, String stringId) {
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
        manualStartActivityRequest.setBusinessId(stringId);
        manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
        manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getStringId());
        manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANGE_AUTO.getCode());
        manualStartActivityRequests.add(manualStartActivityRequest);
    }

    private void assetRecord(String id) throws Exception {
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(id);
        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        assetOperationRecord.setTargetStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        assetOperationRecord.setContent("导入硬件资产");
        assetOperationRecord.setProcessResult(1);
        assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        String areaId = assetDao.getById(id).getAreaId();
        assetOperationRecord.setAreaId(areaId);
        assetOperationRecordDao.insert(assetOperationRecord);
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
    public void exportData(AssetQuery assetQuery, HttpServletResponse response) throws Exception {
        assetQuery.setAreaIds(
            ArrayTypeUtil.objectArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser().toArray()));
        assetQuery.setPageSize(ALL_PAGE);
        List<AssetResponse> list = this.findPageAsset(assetQuery).getItems();
        List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setSheetName("资产信息表");
        downloadVO.setDownloadList(assetEntities);
        if (Objects.nonNull(assetEntities) && assetEntities.size() > 0) {
            excelDownloadUtil.excelDownload(response, "资产信息表", downloadVO);
        } else {
            throw new BusinessException("导出数据为空");
        }
    }

    @Override
    public List<String> pulldownUnconnectedManufacturer(Boolean searchNetworkDevice) throws Exception {
        AssetQuery query = new AssetQuery();
        Map<String, String> categoryMap = assetCategoryModelService.getSecondCategoryMap();
        List<Integer> categoryCondition = new ArrayList<>();
        List<AssetCategoryModel> all = assetCategoryModelService.getAll();
        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            if (query.getSearchNetworkDevice() == null || !query.getSearchNetworkDevice()) {
                if (entry.getValue().equals(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg())) {
                    categoryCondition.addAll(
                        assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
                }
            }
            if (entry.getValue().equals(AssetSecondCategoryEnum.NETWORK_DEVICE.getMsg())) {
                categoryCondition.addAll(
                    assetCategoryModelService.findAssetCategoryModelIdsById(Integer.parseInt(entry.getKey()), all));
            }
        }
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode());
        query.setAssetStatusList(statusList);
        query.setCategoryModels(DataTypeUtils.integerArrayToStringArray(categoryCondition));
        query.setAreaIds(
            DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()));
        return assetDao.pulldownUnconnectedManufacturer(query);
    }

    /**
     * 判断操作系统是否存在
     * @return
     */
    private Boolean checkOperatingSystem(String checkStr) {
        List<Map> operatingSystemMapList = operatingSystemClient.getInvokeOperatingSystem();
        for (Map map : operatingSystemMapList) {
            if (Objects.equals(map.get("name"), checkStr)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RespBasicCode changeToNextStatus(AssetStatusJumpRequst assetStatusJumpRequst) throws Exception {
        SchemeRequest schemeRequest = assetStatusJumpRequst.getSchemeRequest();
        String assetId = assetStatusJumpRequst.getAssetId();
        // 修改资产状态
        if (AssetStatusEnum.WAIT_SETTING.getCode().equals(assetStatusJumpRequst.getAssetStatusEnum().getCode())) {
            this.changeStatusById(assetId, AssetStatusEnum.WAIT_VALIDATE.getCode());
        } else if (AssetStatusEnum.WAIT_VALIDATE.getCode()
            .equals(assetStatusJumpRequst.getAssetStatusEnum().getCode())) {
            ParamterExceptionUtils.isNull(assetStatusJumpRequst.getAgree(), "agree不能为空");
            if (assetStatusJumpRequst.getAgree()) {
                this.changeStatusById(assetId, AssetStatusEnum.WAIT_VALIDATE.getCode());
            } else {
                this.changeStatusById(assetId, AssetStatusEnum.WAIT_SETTING.getCode());
            }
        }
        Scheme scheme = BeanConvert.convertBean(schemeRequest, Scheme.class);
        // 2.保存流程
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(assetId);

        assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
        assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_VALIDATE.getCode());
        assetOperationRecord.setContent(AssetFlowEnum.HARDWARE_CONFIG_BASELINE.getMsg());
        assetOperationRecord.setProcessResult(1);
        assetOperationRecord.setOriginStatus(AssetStatusEnum.WATI_REGSIST.getCode());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());

        // 写入方案
        if (scheme != null && scheme.getFileInfo() != null && scheme.getFileInfo().length() > 0) {
            assetOperationRecord.setSchemeId(scheme.getId());

            JSONObject.parse(HtmlUtils.htmlUnescape(scheme.getFileInfo()));
            scheme.setAssetNextStatus(AssetStatusEnum.WAIT_VALIDATE.getCode());
            scheme.setAssetId(assetId);
            schemeDao.insert(scheme);

            // 记录操作日志和运行日志
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_SCHEME_INSERT.getName(), scheme.getId(), "",
                scheme, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
            LogUtils.info(logger, AssetEventEnum.ASSET_SCHEME_INSERT.getName() + " {}", scheme);
        }

        assetOperationRecordDao.insert(assetOperationRecord);

        // 写入业务日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName(),
            assetOperationRecord.getId(), "", scheme, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName() + " {}", assetOperationRecord);
        return RespBasicCode.SUCCESS;
    }

    /**
     * 资产上报接口
     * @param assetList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActionResponse saveAssetList(List<AssetOuterRequest> assetList) {
        ParamterExceptionUtils.isEmpty(assetList, "资产上报数据为空");
        assetList.stream().forEach(assetOuterRequest -> {
            try {
                // 保存资产信息,并返回资产id
                ActionResponse<Integer> response = saveAsset(assetOuterRequest);
                Integer assetId = response.getBody();
                // 处理软件
                if (CollectionUtils.isNotEmpty(assetOuterRequest.getSoftwareReportRequestList())) {
                    softwareService.reportData(assetId, assetOuterRequest.getSoftwareReportRequestList());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return null;
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
        super.convert(asset, assetEntity);
    }

    private String longToDateString(Long datetime) {
        if (Objects.nonNull(datetime) && !Objects.equals(datetime, 0L)) {
            return DateUtils.getDataString(new Date(datetime), DateUtils.WHOLE_FORMAT);
        }
        return "";
    }

}