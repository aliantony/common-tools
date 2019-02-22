package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

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
    @Resource
    private AreaClient                                                  areaClient;

    @Override
    public ActionResponse saveAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
        AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
        AssetOuterRequest assetOuterRequest = request.getAssetOuterRequest();

        assetChangeRecord.setChangeVal(JsonUtil.object2Json(assetOuterRequest));
        assetChangeRecord.setGmtCreate(System.currentTimeMillis());
        assetChangeRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetChangeRecordDao.insert(assetChangeRecord);
        ManualStartActivityRequest manualStartActivityRequest = assetOuterRequest.getManualStartActivityRequest();
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
        ActionResponse actionResponse = activityClient.manualStartProcess(manualStartActivityRequest);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        }
        return ActionResponse.success(assetChangeRecord.getId());
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
        List<String> changeValStrList = assetChangeRecordDao.findChangeValByBusinessId(assetChangeRecordQuery);
        List<Map<String, Object>> changeValList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(changeValStrList) && changeValStrList.size() > 1){
            // 提取值已变更的属性
            // 硬件
            Integer asset = 1;
            // 软件
            Integer software = 2;
            if (asset.equals(type)) {
                //变更前的信息
                AssetOuterRequest oldAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(1),
                        AssetOuterRequest.class);
                //变更后的信息
                Asset oldAsset = assetRequestToAssetConverter.convert(oldAssetOuterRequest.getAsset(), Asset.class);
                AssetOuterRequest newAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(0),
                        AssetOuterRequest.class);
                Asset newAsset = assetRequestToAssetConverter.convert(newAssetOuterRequest.getAsset(), Asset.class);
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
                // 远程调用（通过区域ID查询名称）
                SysAreaVO oldSysAreaVO = JsonUtil
                        .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(oldAsset.getAreaId())), SysAreaVO.class);
                oldAssetBusinessInfo.setAreaName(oldSysAreaVO.getFullName());
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
                SysAreaVO newSysAreaVO = JsonUtil
                        .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(newAsset.getAreaId())), SysAreaVO.class);
                newAssetBusinessInfo.setAreaName(newSysAreaVO.getFullName());
                newAssetBusinessInfo.setResponsibleUserId(newAsset.getResponsibleUserId());
                newAssetBusinessInfo.setContactTel(newAsset.getContactTel());
                newAssetBusinessInfo.setEmail(newAsset.getEmail());
                newAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
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
                List<AssetMemoryRequest> memoryList = oldAssetOuterRequest.getMemory();
                if (CollectionUtils.isNotEmpty(memoryList)) {
                    for (AssetMemoryRequest memoryRequest : memoryList) {
                        AssetMemory oldMemory = new AssetMemory();
                        oldMemory.setId(DataTypeUtils.stringToInteger(memoryRequest.getId()));
                        oldMemory.setBrand(memoryRequest.getBrand());
                        oldMemory.setTransferType(memoryRequest.getTransferType());
                        oldMemory.setSerial(memoryRequest.getSerial());
                        oldMemory.setCapacity(memoryRequest.getCapacity());
                        oldMemory.setFrequency(memoryRequest.getFrequency());
                        oldMemory.setSlotType(memoryRequest.getSlotType());
                        oldMemory.setHeatsink(memoryRequest.getHeatsink());
                        oldMemory.setStitch(memoryRequest.getStitch());
                        List<AssetMemoryRequest> newMemoryList = newAssetOuterRequest.getMemory();
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
                        } else {
                            break;
                        }
                    }
                }

                // 提取CPU字段变更信息
                List<List<Map<String, Object>>> assetCpuCompareResult = new ArrayList<>();
                List<AssetCpuRequest> cpuRequestList = oldAssetOuterRequest.getCpu();
                if (CollectionUtils.isNotEmpty(cpuRequestList)) {
                    for (AssetCpuRequest cpuRequest : cpuRequestList) {
                        AssetCpu oldCpu = new AssetCpu();
                        oldCpu.setId(DataTypeUtils.stringToInteger(cpuRequest.getId()));
                        oldCpu.setBrand(cpuRequest.getBrand());
                        oldCpu.setModel(cpuRequest.getModel());
                        oldCpu.setSerial(cpuRequest.getSerial());
                        oldCpu.setMainFrequency(cpuRequest.getMainFrequency());
                        oldCpu.setThreadSize(cpuRequest.getThreadSize());
                        oldCpu.setCoreSize(cpuRequest.getCoreSize());
                        List<AssetCpuRequest> newCpuList = newAssetOuterRequest.getCpu();
                        if (CollectionUtils.isNotEmpty(newCpuList)) {
                            AssetCpu newCpu = new AssetCpu();
                            for (AssetCpuRequest assetCpuRequest : newCpuList) {
                                newCpu.setId(DataTypeUtils.stringToInteger(assetCpuRequest.getId()));
                                newCpu.setBrand(assetCpuRequest.getBrand());
                                newCpu.setModel(assetCpuRequest.getModel());
                                newCpu.setSerial(assetCpuRequest.getSerial());
                                newCpu.setMainFrequency(assetCpuRequest.getMainFrequency());
                                newCpu.setThreadSize(assetCpuRequest.getThreadSize());
                                newCpu.setCoreSize(assetCpuRequest.getCoreSize());
                                if (oldCpu.getId().equals(newCpu.getId())) {
                                    assetCpuCompareResult
                                            .add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }

                // 提取硬盘字段变更信息
                List<List<Map<String, Object>>> assetHardDiskCompareResult = new ArrayList<>();
                List<AssetHardDiskRequest> hardDiskRequestList = oldAssetOuterRequest.getHardDisk();
                if (CollectionUtils.isNotEmpty(hardDiskRequestList)) {
                    for (AssetHardDiskRequest hardDiskRequest : hardDiskRequestList) {
                        AssetHardDisk oldHardDisk = new AssetHardDisk();
                        oldHardDisk.setId(DataTypeUtils.stringToInteger(hardDiskRequest.getId()));
                        oldHardDisk.setBrand(hardDiskRequest.getBrand());
                        oldHardDisk.setModel(hardDiskRequest.getModel());
                        oldHardDisk.setSerial(hardDiskRequest.getSerial());
                        oldHardDisk.setCapacity(hardDiskRequest.getCapacity());
                        oldHardDisk.setInterfaceType(hardDiskRequest.getInterfaceType());
                        oldHardDisk.setDiskType(hardDiskRequest.getDiskType());
                        oldHardDisk.setBuyDate(hardDiskRequest.getBuyDate());
                        List<AssetHardDiskRequest> newHardDiskList = oldAssetOuterRequest.getHardDisk();
                        if (CollectionUtils.isNotEmpty(newHardDiskList)) {
                            AssetHardDisk newHardDisk = new AssetHardDisk();
                            for (AssetHardDiskRequest assetHardDiskRequest : newHardDiskList) {
                                newHardDisk.setId(DataTypeUtils.stringToInteger(assetHardDiskRequest.getId()));
                                newHardDisk.setBrand(assetHardDiskRequest.getBrand());
                                newHardDisk.setModel(assetHardDiskRequest.getModel());
                                newHardDisk.setSerial(assetHardDiskRequest.getSerial());
                                newHardDisk.setCapacity(assetHardDiskRequest.getCapacity());
                                newHardDisk.setInterfaceType(assetHardDiskRequest.getInterfaceType());
                                newHardDisk.setDiskType(assetHardDiskRequest.getDiskType());
                                newHardDisk.setBuyDate(assetHardDiskRequest.getBuyDate());
                                if (oldHardDisk.getId().equals(newHardDisk.getId())) {
                                    assetHardDiskCompareResult.add(CompareUtils.compareClass(oldHardDisk, newHardDisk,
                                            InfoLabelEnum.HARDDISK.getMsg()));
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }

                // 提取主板字段变更信息
                List<List<Map<String, Object>>> assetMainboardCompareResult = new ArrayList<>();
                List<AssetMainboradRequest> mainboradRequestList = oldAssetOuterRequest.getMainboard();
                if (CollectionUtils.isNotEmpty(mainboradRequestList)) {
                    AssetMainborad oldMainborad = new AssetMainborad();
                    for (AssetMainboradRequest mainboradRequest : mainboradRequestList) {
                        oldMainborad.setId(DataTypeUtils.stringToInteger(mainboradRequest.getId()));
                        oldMainborad.setBrand(mainboradRequest.getBrand());
                        oldMainborad.setModel(mainboradRequest.getModel());
                        oldMainborad.setSerial(mainboradRequest.getSerial());
                        oldMainborad.setBiosVersion(mainboradRequest.getBiosVersion());
                        oldMainborad.setBiosDate(mainboradRequest.getBiosDate());
                        List<AssetMainboradRequest> newMainboardList = oldAssetOuterRequest.getMainboard();
                        if (CollectionUtils.isNotEmpty(newMainboardList)) {
                            AssetMainborad newMainboard = new AssetMainborad();
                            for (AssetMainboradRequest assetMainboradRequest : newMainboardList) {
                                newMainboard.setId(DataTypeUtils.stringToInteger(assetMainboradRequest.getId()));
                                newMainboard.setBrand(assetMainboradRequest.getBrand());
                                newMainboard.setModel(assetMainboradRequest.getModel());
                                newMainboard.setSerial(assetMainboradRequest.getSerial());
                                newMainboard.setBiosVersion(assetMainboradRequest.getBiosVersion());
                                newMainboard.setBiosDate(assetMainboradRequest.getBiosDate());
                                if (oldMainborad.getId().equals(newMainboard.getId())) {
                                    assetHardDiskCompareResult.add(CompareUtils.compareClass(oldMainborad, newMainboard,
                                            InfoLabelEnum.MAINBORAD.getMsg()));
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }

                // 提取网卡字段变更信息
                List<List<Map<String, Object>>> assetNetworkCompareResult = new ArrayList<>();
                List<AssetNetworkCardRequest> networkCardList = oldAssetOuterRequest.getNetworkCard();
                if (CollectionUtils.isNotEmpty(networkCardList)) {
                    AssetNetworkCard oldNetworkCard = new AssetNetworkCard();
                    for (AssetNetworkCardRequest networkCardRequest : networkCardList) {
                        oldNetworkCard.setId(DataTypeUtils.stringToInteger(networkCardRequest.getId()));
                        oldNetworkCard.setBrand(networkCardRequest.getBrand());
                        oldNetworkCard.setModel(networkCardRequest.getModel());
                        oldNetworkCard.setSerial(networkCardRequest.getSerial());
                        oldNetworkCard.setIpAddress(networkCardRequest.getIpAddress());
                        oldNetworkCard.setMacAddress(networkCardRequest.getMacAddress());
                        oldNetworkCard.setSubnetMask(networkCardRequest.getSubnetMask());
                        oldNetworkCard.setDefaultGateway(networkCardRequest.getDefaultGateway());
                        List<AssetNetworkCardRequest> newNetworkCardList = oldAssetOuterRequest.getNetworkCard();
                        if (CollectionUtils.isNotEmpty(newNetworkCardList)) {
                            AssetNetworkCard newNetworkCard = new AssetNetworkCard();
                            for (AssetNetworkCardRequest assetNetworkCardRequest : newNetworkCardList) {
                                newNetworkCard.setId(DataTypeUtils.stringToInteger(assetNetworkCardRequest.getId()));
                                newNetworkCard.setBrand(assetNetworkCardRequest.getBrand());
                                newNetworkCard.setModel(assetNetworkCardRequest.getModel());
                                newNetworkCard.setSerial(assetNetworkCardRequest.getSerial());
                                newNetworkCard.setIpAddress(assetNetworkCardRequest.getIpAddress());
                                newNetworkCard.setMacAddress(assetNetworkCardRequest.getMacAddress());
                                newNetworkCard.setSubnetMask(assetNetworkCardRequest.getSubnetMask());
                                newNetworkCard.setDefaultGateway(assetNetworkCardRequest.getDefaultGateway());
                                if (oldNetworkCard.getId().equals(newNetworkCard.getId())) {
                                    assetNetworkCompareResult.add(CompareUtils.compareClass(oldNetworkCard, newNetworkCard,
                                            InfoLabelEnum.NETWORKCARD.getMsg()));
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }

                // 提取关联软件变更信息
                List<List<Map<String, Object>>> relateSoftewareCompareResult = new ArrayList<>();
                List<AssetSoftwareRelationRequest> oldSoftwareRelationList = oldAssetOuterRequest.getAssetSoftwareRelationList();
                List<AssetSoftwareRelationRequest> newAssetOuterRequestList = newAssetOuterRequest
                        .getAssetSoftwareRelationList();
                if (CollectionUtils.isNotEmpty(oldSoftwareRelationList)) {
                    for (AssetSoftwareRelationRequest assetSoftwareRelationRequest : oldSoftwareRelationList) {
                        RelateSoftware oldRelateSoftware = new RelateSoftware();
                        oldRelateSoftware.setSoftwareId(assetSoftwareRelationRequest.getSoftwareId());
                        AssetSoftwareLicense assetSoftwareLicense = softwareLicenseDao
                                .getById(DataTypeUtils.stringToInteger(assetSoftwareRelationRequest.getSoftwareId()));
                        oldRelateSoftware.setLicenseSecretKey(
                                assetSoftwareLicense == null ? null : assetSoftwareLicense.getLicenseSecretKey());
                        oldRelateSoftware.setMulPort(assetSoftwareRelationRequest.getPort());
                        oldRelateSoftware.setDescription(assetSoftwareRelationRequest.getMemo());
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
                        } else {
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
                //变更前的信息
                AssetSoftwareRequest oldSoftware = JsonUtil.json2Object(changeValStrList.get(1),
                        AssetSoftwareRequest.class);
                //变更后的信息
                AssetSoftwareRequest newSoftware = JsonUtil.json2Object(changeValStrList.get(0),
                        AssetSoftwareRequest.class);
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
        }else {
            return null;
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
