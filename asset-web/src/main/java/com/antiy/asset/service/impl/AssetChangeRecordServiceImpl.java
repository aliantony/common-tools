package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p> 变更记录表 服务实现类 </p>
 *
 * @author why
 * @since 2019-02-19
 */
@Service
public class AssetChangeRecordServiceImpl extends BaseServiceImpl<AssetChangeRecord>
                                          implements IAssetChangeRecordService {

    private static final Logger                                         logger = LogUtils.get();

    @Resource
    private AssetDao                                                    assetDao;
    @Resource
    private AssetMemoryDao                                              memoryDao;
    @Resource
    private AssetMainboradDao                                           mainboradDao;
    @Resource
    private AssetNetworkCardDao                                         networkCardDao;
    @Resource
    private AssetHardDiskDao                                            hardDiskDao;
    @Resource
    private AssetSoftwareDao                                            softwareDao;
    @Resource
    private AssetCpuDao                                                 cpuDao;
    @Resource
    AssetSoftwareRelationDao                                            softwareRelationDao;
    @Resource
    AssetSoftwareLicenseDao                                             softwareLicenseDao;
    @Resource
    private ActivityClient                                              activityClient;
    @Resource
    private AssetChangeRecordDao                                        assetChangeRecordDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>                          assetRequestToAssetConverter;
    @Resource
    private BaseConverter<AssetChangeRecordRequest, AssetChangeRecord>  requestConverter;
    @Resource
    private BaseConverter<AssetChangeRecord, AssetChangeRecordResponse> responseConverter;

    @Override
    public Integer saveAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
        AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
        AssetOuterRequest assetOuterRequest = request.getAssetOuterRequest();

        assetChangeRecord.setChangeVal(JsonUtil.object2Json(assetOuterRequest));
        assetChangeRecord.setGmtCreate (System.currentTimeMillis ());
        assetChangeRecord.setCreateUser (LoginUserUtil.getLoginUser ().getId ());
        assetChangeRecordDao.insert(assetChangeRecord);
        ManualStartActivityRequest manualStartActivityRequest = assetOuterRequest.getActivityRequest();
        if (Objects.isNull(manualStartActivityRequest)) {
            manualStartActivityRequest = new ManualStartActivityRequest();
        }
        // 其他设备
        if (assetOuterRequest.getAssetOthersRequest() != null) {

            manualStartActivityRequest.setBusinessId(assetOuterRequest.getAssetOthersRequest().getId());
        } else {
            manualStartActivityRequest.setBusinessId(assetOuterRequest.getAsset().getId());
        }
        manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
        manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_CHANGE.getCode());
        activityClient.manualStartProcess(manualStartActivityRequest);
        return assetChangeRecord.getId();
    }

    @Override
    public Integer updateAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
        AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
        return assetChangeRecordDao.update(assetChangeRecord);
    }

    @Override
    public List<AssetChangeRecordResponse> findListAssetChangeRecord(AssetChangeRecordQuery query) throws Exception {
        List<AssetChangeRecord> assetChangeRecordList = assetChangeRecordDao.findQuery(query);
        // TODO
        List<AssetChangeRecordResponse> assetChangeRecordResponse = responseConverter.convert(assetChangeRecordList,
            AssetChangeRecordResponse.class);
        return assetChangeRecordResponse;
    }

    @Override
    public PageResult<AssetChangeRecordResponse> findPageAssetChangeRecord(AssetChangeRecordQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetChangeRecord(query));
    }

    @Override
    public List<Map<String, Object>> queryChangeFieldInfoById(Integer businessId, Integer type) throws Exception {
        AssetChangeRecordQuery assetChangeRecordQuery = new AssetChangeRecordQuery();
        assetChangeRecordQuery.setBusinessId(businessId);
        assetChangeRecordQuery.setType(type);
        String changeVal = assetChangeRecordDao.findChangeValByBusinessId(assetChangeRecordQuery);
        List<Map<String, Object>> changeValList = new ArrayList<>();
        // 提取值已变更的属性
        // 硬件
        Integer asset = 1;
        // 软件
        Integer software = 2;
        if (asset.equals(type)) {
            AssetOuterRequest assetOuterRequest = JsonUtil.json2Object(changeVal, AssetOuterRequest.class);
            Asset oldAsset = assetDao.getById(businessId);
            AssetRequest newAssetRequest = assetOuterRequest.getAsset();
            Asset newAsset = assetRequestToAssetConverter.convert(newAssetRequest, Asset.class);
            // 拆分资产信息为通用信息和业务信息，便于前端显示
            // 通用信息
            AssetRequest oldAssetCommonInfo = new AssetRequest();
            oldAssetCommonInfo.setName(oldAsset.getName());
            oldAssetCommonInfo.setManufacturer(oldAsset.getManufacturer());
            oldAssetCommonInfo.setSerial(oldAsset.getSerial());

            AssetRequest newAssetCommonInfo = new AssetRequest();
            newAssetCommonInfo.setName(newAsset.getName());
            newAssetCommonInfo.setManufacturer(newAsset.getManufacturer());
            newAssetCommonInfo.setSerial(newAsset.getSerial());

            List<Map<String, Object>> assetCommonInoCompareResult = CompareUtils.compareClass(oldAssetCommonInfo,
                newAssetCommonInfo, InfoLabelEnum.COMMONINFO.getMsg());

            // 业务信息
            Asset oldAssetBusinessInfo = new Asset();
            oldAssetBusinessInfo.setAreaId(oldAsset.getAreaId());
            oldAssetBusinessInfo.setResponsibleUserId(oldAsset.getResponsibleUserId());
            oldAssetBusinessInfo.setContactTel(oldAsset.getContactTel());
            oldAssetBusinessInfo.setEmail(oldAsset.getEmail());
            oldAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
            oldAssetBusinessInfo.setNumber(oldAsset.getNumber());
            oldAssetBusinessInfo.setInstallType(oldAsset.getInstallType());
            oldAssetBusinessInfo.setFirmwareVersion(oldAsset.getFirmwareVersion());
            oldAssetBusinessInfo.setOperationSystem(oldAsset.getOperationSystem());
            oldAssetBusinessInfo.setImportanceDegree(oldAsset.getImportanceDegree());
            oldAssetBusinessInfo.setBuyDate(oldAsset.getBuyDate());
            oldAssetBusinessInfo.setServiceLife(oldAsset.getServiceLife());
            oldAssetBusinessInfo.setWarranty(oldAsset.getWarranty());
            oldAssetBusinessInfo.setDescrible(oldAsset.getDescrible());

            Asset newAssetBusinessInfo = new Asset();
            newAssetBusinessInfo.setAreaId(newAsset.getAreaId());
            newAssetBusinessInfo.setResponsibleUserId(newAsset.getResponsibleUserId());
            newAssetBusinessInfo.setContactTel(newAsset.getContactTel());
            newAssetBusinessInfo.setEmail(newAsset.getEmail());
            oldAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
            newAssetBusinessInfo.setNumber(newAsset.getNumber());
            newAssetBusinessInfo.setInstallType(newAsset.getInstallType());
            newAssetBusinessInfo.setFirmwareVersion(newAsset.getFirmwareVersion());
            newAssetBusinessInfo.setOperationSystem(newAsset.getOperationSystem());
            newAssetBusinessInfo.setImportanceDegree(newAsset.getImportanceDegree());
            newAssetBusinessInfo.setBuyDate(newAsset.getBuyDate());
            newAssetBusinessInfo.setServiceLife(newAsset.getServiceLife());
            newAssetBusinessInfo.setWarranty(newAsset.getWarranty());
            newAssetBusinessInfo.setDescrible(newAsset.getDescrible());

            List<Map<String, Object>> assetBusinessInfoCompareResult = CompareUtils.compareClass(oldAssetBusinessInfo,
                newAssetBusinessInfo, InfoLabelEnum.BUSINESSINFO.getMsg());

            // 提取内存字段变更信息
            List<List<Map<String, Object>>> assetMemoryCompareResult = new ArrayList<>();
            List<AssetMemory> memoryList = memoryDao.findMemoryByAssetId(businessId);
            if (CollectionUtils.isNotEmpty(memoryList)) {
                for (AssetMemory assetMemory : memoryList) {
                    AssetMemory oldMemory = new AssetMemory();
                    oldMemory.setId(assetMemory.getId());
                    oldMemory.setBrand(assetMemory.getBrand());
                    oldMemory.setTransferType(assetMemory.getTransferType());
                    oldMemory.setSerial(assetMemory.getSerial());
                    oldMemory.setCapacity(assetMemory.getCapacity());
                    oldMemory.setFrequency(assetMemory.getFrequency());
                    oldMemory.setSlotType(assetMemory.getSlotType());
                    oldMemory.setHeatsink(assetMemory.getHeatsink());
                    oldMemory.setStitch(assetMemory.getStitch());
                    List<AssetMemoryRequest> newMemoryList = assetOuterRequest.getMemory();
                    if (CollectionUtils.isNotEmpty(newMemoryList)) {
                        AssetMemory newMemory = new AssetMemory();
                        for (AssetMemoryRequest assetMemoryRequest : newMemoryList) {
                            newMemory.setId(DataTypeUtils.stringToInteger(assetMemoryRequest.getId()));
                            newMemory.setBrand(assetMemoryRequest.getBrand());
                            newMemory.setTransferType(assetMemoryRequest.getTransferType());
                            newMemory.setSerial(assetMemoryRequest.getSerial());
                            newMemory.setCapacity(assetMemoryRequest.getCapacity());
                            newMemory.setFrequency(assetMemoryRequest.getFrequency());
                            newMemory.setSlotType(assetMemoryRequest.getSlotType());
                            newMemory.setHeatsink(assetMemoryRequest.getHeatsink());
                            newMemory.setStitch(assetMemoryRequest.getStitch());
                            if (oldMemory.getId().equals(newMemory.getId())) {
                                assetMemoryCompareResult.add(
                                    CompareUtils.compareClass(oldMemory, newMemory, InfoLabelEnum.MEMORY.getMsg()));
                            }
                        }
                    }else {
                        break;
                    }
                }
            }

            // 提取CPU字段变更信息
            List<List<Map<String, Object>>> assetCpuCompareResult = new ArrayList<>();
            List<AssetCpu> cpuList = cpuDao.findCpuByAssetId(businessId);
            if (CollectionUtils.isNotEmpty(cpuList)) {
                for (AssetCpu cpu : cpuList) {
                    AssetCpu oldCpu = new AssetCpu();
                    oldCpu.setId(cpu.getId());
                    oldCpu.setBrand(cpu.getBrand());
                    oldCpu.setModel(cpu.getModel());
                    oldCpu.setSerial(cpu.getSerial());
                    oldCpu.setMainFrequency(cpu.getMainFrequency());
                    oldCpu.setThreadSize(cpu.getThreadSize());
                    oldCpu.setCoreSize(cpu.getCoreSize());
                    List<AssetCpuRequest> newCpuList = assetOuterRequest.getCpu();
                    if (CollectionUtils.isNotEmpty(newCpuList)) {
                        AssetCpu newCpu = new AssetCpu();
                        for (AssetCpuRequest cpuRequest : newCpuList) {
                            newCpu.setId(DataTypeUtils.stringToInteger(cpuRequest.getId()));
                            newCpu.setBrand(cpuRequest.getBrand());
                            newCpu.setModel(cpuRequest.getModel());
                            newCpu.setSerial(cpuRequest.getSerial());
                            newCpu.setMainFrequency(cpuRequest.getMainFrequency());
                            newCpu.setThreadSize(cpuRequest.getThreadSize());
                            newCpu.setCoreSize(cpuRequest.getCoreSize());
                            if (oldCpu.getId().equals(newCpu.getId())) {
                                assetCpuCompareResult
                                    .add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                            }
                        }
                    }else {
                        break;
                    }
                }
            }

            // 提取硬盘字段变更信息
            List<List<Map<String, Object>>> assetHardDiskCompareResult = new ArrayList<>();
            List<AssetHardDisk> hardDiskList = hardDiskDao.findHardDiskByAssetId(businessId);
            if (CollectionUtils.isNotEmpty(hardDiskList)) {
                for (AssetHardDisk hardDisk : hardDiskList) {
                    AssetHardDisk oldHardDisk = new AssetHardDisk();
                    oldHardDisk.setId(hardDisk.getId());
                    oldHardDisk.setBrand(hardDisk.getBrand());
                    oldHardDisk.setModel(hardDisk.getModel());
                    oldHardDisk.setSerial(hardDisk.getSerial());
                    oldHardDisk.setCapacity(hardDisk.getCapacity());
                    oldHardDisk.setInterfaceType(hardDisk.getInterfaceType());
                    oldHardDisk.setDiskType(hardDisk.getDiskType());
                    oldHardDisk.setBuyDate(hardDisk.getBuyDate());
                    List<AssetHardDiskRequest> newHardDiskList = assetOuterRequest.getHardDisk();
                    if (CollectionUtils.isNotEmpty(newHardDiskList)) {
                        AssetHardDisk newHardDisk = new AssetHardDisk();
                        for (AssetHardDiskRequest hardDiskRequest : newHardDiskList) {
                            newHardDisk.setId(DataTypeUtils.stringToInteger(newAssetRequest.getId()));
                            newHardDisk.setBrand(hardDiskRequest.getBrand());
                            newHardDisk.setModel(hardDiskRequest.getModel());
                            newHardDisk.setSerial(hardDiskRequest.getSerial());
                            newHardDisk.setCapacity(hardDiskRequest.getCapacity());
                            newHardDisk.setInterfaceType(hardDiskRequest.getInterfaceType());
                            newHardDisk.setDiskType(hardDiskRequest.getDiskType());
                            newHardDisk.setBuyDate(hardDiskRequest.getBuyDate());
                            if (oldHardDisk.getId().equals(newHardDisk.getId())) {
                                assetHardDiskCompareResult.add(CompareUtils.compareClass(oldHardDisk, newHardDisk,
                                    InfoLabelEnum.HARDDISK.getMsg()));
                            }
                        }
                    }else {
                        break;
                    }
                }
            }

            // 提取主板字段变更信息
            List<List<Map<String, Object>>> assetMainboardCompareResult = new ArrayList<>();
            List<AssetMainborad> mainboardList = mainboradDao.findMainboardByAssetId(businessId);
            if (CollectionUtils.isNotEmpty(mainboardList)) {
                AssetMainborad oldMainborad = new AssetMainborad();
                for (AssetMainborad mainborad : mainboardList) {
                    oldMainborad.setId(mainborad.getId());
                    oldMainborad.setBrand(mainborad.getBrand());
                    oldMainborad.setModel(mainborad.getModel());
                    oldMainborad.setSerial(mainborad.getSerial());
                    oldMainborad.setBiosVersion(mainborad.getBiosVersion());
                    oldMainborad.setBiosDate(mainborad.getBiosDate());
                    List<AssetMainboradRequest> newMainboardList = assetOuterRequest.getMainboard();
                    if (CollectionUtils.isNotEmpty(newMainboardList)) {
                        AssetMainborad newMainboard = new AssetMainborad();
                        for (AssetMainboradRequest mainboradRequest : newMainboardList) {
                            newMainboard.setId(DataTypeUtils.stringToInteger(mainboradRequest.getId()));
                            newMainboard.setBrand(mainboradRequest.getBrand());
                            newMainboard.setModel(mainboradRequest.getModel());
                            newMainboard.setSerial(mainboradRequest.getSerial());
                            newMainboard.setBiosVersion(mainboradRequest.getBiosVersion());
                            newMainboard.setBiosDate(mainboradRequest.getBiosDate());
                            if (oldMainborad.getId().equals(newMainboard.getId())) {
                                assetHardDiskCompareResult.add(CompareUtils.compareClass(oldMainborad, newMainboard,
                                    InfoLabelEnum.MAINBORAD.getMsg()));
                            }
                        }
                    }else {
                        break;
                    }
                }
            }

            // 提取网卡字段变更信息
            List<List<Map<String, Object>>> assetNetworkCompareResult = new ArrayList<>();
            List<AssetNetworkCard> networkCardList = networkCardDao.findNetworkCardByAssetId(businessId);
            if (CollectionUtils.isNotEmpty(networkCardList)) {
                AssetNetworkCard oldNetworkCard = new AssetNetworkCard();
                for (AssetNetworkCard networkCard : networkCardList) {
                    oldNetworkCard.setId(networkCard.getId());
                    oldNetworkCard.setBrand(networkCard.getBrand());
                    oldNetworkCard.setModel(networkCard.getModel());
                    oldNetworkCard.setSerial(networkCard.getSerial());
                    oldNetworkCard.setIpAddress(networkCard.getIpAddress());
                    oldNetworkCard.setMacAddress(networkCard.getMacAddress());
                    oldNetworkCard.setSubnetMask(networkCard.getSubnetMask());
                    oldNetworkCard.setDefaultGateway(networkCard.getDefaultGateway());
                    List<AssetNetworkCardRequest> newNetworkCardList = assetOuterRequest.getNetworkCard();
                    if (CollectionUtils.isNotEmpty(newNetworkCardList)) {
                        AssetNetworkCard newNetworkCard = new AssetNetworkCard();
                        for (AssetNetworkCardRequest networkCardRequest : newNetworkCardList) {
                            newNetworkCard.setId(DataTypeUtils.stringToInteger(networkCardRequest.getId()));
                            newNetworkCard.setBrand(networkCardRequest.getBrand());
                            newNetworkCard.setModel(networkCardRequest.getModel());
                            newNetworkCard.setSerial(networkCardRequest.getSerial());
                            newNetworkCard.setIpAddress(networkCardRequest.getIpAddress());
                            newNetworkCard.setMacAddress(networkCardRequest.getMacAddress());
                            newNetworkCard.setSubnetMask(networkCardRequest.getSubnetMask());
                            newNetworkCard.setDefaultGateway(networkCardRequest.getDefaultGateway());
                            if (oldNetworkCard.getId().equals(newNetworkCard.getId())) {
                                assetNetworkCompareResult.add(CompareUtils.compareClass(oldNetworkCard, newNetworkCard,
                                    InfoLabelEnum.NETWORKCARD.getMsg()));
                            }
                        }
                    }else {
                        break;
                    }
                }
            }

            // 提取关联软件变更信息
            List<List<Map<String, Object>>> relateSoftewareCompareResult = new ArrayList<>();
            List<AssetSoftwareRelation> oldSoftwareRelationList = softwareRelationDao.getReleationByAssetId(businessId);
            List<AssetSoftwareRelationRequest> newAssetOuterRequestList = assetOuterRequest
                .getAssetSoftwareRelationList();
            if (CollectionUtils.isNotEmpty(oldSoftwareRelationList)) {
                for (AssetSoftwareRelation softwareRelation : oldSoftwareRelationList) {
                    RelateSoftware oldRelateSoftware = new RelateSoftware();
                    oldRelateSoftware.setSoftwareId(softwareRelation.getSoftwareId());
                    AssetSoftwareLicense assetSoftwareLicense = softwareLicenseDao.getById(DataTypeUtils.stringToInteger(softwareRelation.getSoftwareId()));
                    oldRelateSoftware.setLicenseSecretKey(
                            assetSoftwareLicense == null ? null : assetSoftwareLicense.getLicenseSecretKey());
                    oldRelateSoftware.setMulPort(softwareRelation.getPort());
                    oldRelateSoftware.setDescription(softwareRelation.getMemo());
                    if (CollectionUtils.isNotEmpty(newAssetOuterRequestList)) {
                        RelateSoftware newRelateSoftware = new RelateSoftware();
                        for (AssetSoftwareRelationRequest newAssetSoftwareRelationRequest : newAssetOuterRequestList) {
                            if (oldRelateSoftware.getSoftwareId()
                                .equals(newAssetSoftwareRelationRequest.getSoftwareId())) {
                                newRelateSoftware
                                    .setLicenseSecretKey(newAssetSoftwareRelationRequest.getLicenseSecretKey());
                                newRelateSoftware.setMulPort(newAssetSoftwareRelationRequest.getPort());
                                newRelateSoftware.setDescription(newAssetSoftwareRelationRequest.getMemo());
                                relateSoftewareCompareResult.add(CompareUtils.compareClass(oldRelateSoftware,
                                    newAssetSoftwareRelationRequest, InfoLabelEnum.RELATESOFTWARE.getMsg()));
                            }
                        }
                    }else {
                        break;
                    }
                }
            }

            // 合并集合
            changeValList.addAll(assetCommonInoCompareResult);
            changeValList.addAll(assetBusinessInfoCompareResult);
            if (CollectionUtils.isNotEmpty(assetMemoryCompareResult)) {
                for (List<Map<String, Object>> listMap : assetMemoryCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
            if (CollectionUtils.isNotEmpty(assetCpuCompareResult)) {
                for (List<Map<String, Object>> listMap : assetCpuCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
            if (CollectionUtils.isNotEmpty(assetHardDiskCompareResult)) {
                for (List<Map<String, Object>> listMap : assetHardDiskCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
            if (CollectionUtils.isNotEmpty(assetMainboardCompareResult)) {
                for (List<Map<String, Object>> listMap : assetMainboardCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
            if (CollectionUtils.isNotEmpty(assetNetworkCompareResult)) {
                for (List<Map<String, Object>> listMap : assetNetworkCompareResult) {
                    changeValList.addAll(listMap);
                }
            }

            if (CollectionUtils.isNotEmpty(relateSoftewareCompareResult)) {
                for (List<Map<String, Object>> listMap : relateSoftewareCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
        } else if (software.equals(type)) {
            AssetSoftwareRequest newSoftware = JsonUtil.json2Object(changeVal, AssetSoftwareRequest.class);
            AssetSoftware oldSoftware = softwareDao.getById(businessId);
            // 拆分资产信息为通用信息和业务信息，便于前端显示
            // 通用信息
            AssetSoftwareRequest oldSoftwareCommonInfo = new AssetSoftwareRequest();
            oldSoftwareCommonInfo.setName(oldSoftware.getName());
            oldSoftwareCommonInfo.setManufacturer(oldSoftware.getManufacturer());
            oldSoftwareCommonInfo.setSerial(oldSoftware.getSerial());

            AssetSoftwareRequest newSoftwareCommonInfo = new AssetSoftwareRequest();
            oldSoftwareCommonInfo.setName(newSoftware.getName());
            oldSoftwareCommonInfo.setManufacturer(newSoftware.getManufacturer());
            oldSoftwareCommonInfo.setSerial(newSoftware.getSerial());

            List<Map<String, Object>> softwareCommonInoCompareResult = CompareUtils.compareClass(oldSoftwareCommonInfo,
                newSoftwareCommonInfo, InfoLabelEnum.COMMONINFO.getMsg());

            // 业务信息
            AssetSoftwareRequest oldSoftwareBusinessInfo = new AssetSoftwareRequest();
            oldSoftwareBusinessInfo.setVersion(oldSoftware.getVersion());
            oldSoftwareBusinessInfo.setOperationSystem(oldSoftware.getOperationSystem());
            oldSoftwareBusinessInfo.setProtocol(oldSoftware.getProtocol());
            oldSoftwareBusinessInfo.setMd5Code(oldSoftware.getMd5Code());
            oldSoftwareBusinessInfo.setBuyDate(oldSoftware.getBuyDate());
            oldSoftwareBusinessInfo.setServiceLife(oldSoftware.getServiceLife());
            oldSoftwareBusinessInfo.setReleaseTime(oldSoftware.getReleaseTime());
            oldSoftwareBusinessInfo.setPath(oldSoftware.getPath());
            oldSoftwareBusinessInfo.setDescription(oldSoftware.getDescription());

            AssetSoftwareRequest newSoftwareBusinessInfo = new AssetSoftwareRequest();
            newSoftwareBusinessInfo.setVersion(oldSoftware.getVersion());
            newSoftwareBusinessInfo.setOperationSystem(oldSoftware.getOperationSystem());
            newSoftwareBusinessInfo.setProtocol(oldSoftware.getProtocol());
            newSoftwareBusinessInfo.setMd5Code(oldSoftware.getMd5Code());
            newSoftwareBusinessInfo.setBuyDate(oldSoftware.getBuyDate());
            newSoftwareBusinessInfo.setServiceLife(oldSoftware.getServiceLife());
            newSoftwareBusinessInfo.setReleaseTime(oldSoftware.getReleaseTime());
            newSoftwareBusinessInfo.setPath(oldSoftware.getPath());
            newSoftwareBusinessInfo.setDescription(oldSoftware.getDescription());

            List<Map<String, Object>> softwareBusinessInfoCompareResult = CompareUtils
                .compareClass(oldSoftwareBusinessInfo, newSoftwareBusinessInfo, InfoLabelEnum.BUSINESSINFO.getMsg());

            // 合并集合
            changeValList.addAll(softwareCommonInoCompareResult);
            changeValList.addAll(softwareBusinessInfoCompareResult);
        }
        return changeValList;
    }

    /**
     * 关联软件信息
     */
    class RelateSoftware {
        private String softwareId;
        private String softName;
        private String licenseSecretKey;
        private String mulPort;
        private String description;

        public String getSoftwareId() {
            return softwareId;
        }

        public void setSoftwareId(String softwareId) {
            this.softwareId = softwareId;
        }

        public String getSoftName() {
            return softName;
        }

        public void setSoftName(String softName) {
            this.softName = softName;
        }

        public String getLicenseSecretKey() {
            return licenseSecretKey;
        }

        public void setLicenseSecretKey(String licenseSecretKey) {
            this.licenseSecretKey = licenseSecretKey;
        }

        public String getMulPort() {
            return mulPort;
        }

        public void setMulPort(String mulPort) {
            this.mulPort = mulPort;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
