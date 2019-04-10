package com.antiy.asset.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.entity.*;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.EnumUtil;
import com.antiy.asset.util.StringUtils;
import com.antiy.asset.vo.enums.AssetImportanceDegreeEnum;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.enums.TransferTypeMemoryEnum;
import com.antiy.asset.vo.request.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysUser;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.JsonUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * 计算设备字段对比
 * @author zhangyajun
 * @create 2019-02-25 9:47
 **/
@Service
public class ComputerEquipmentFieldCompareImpl extends AbstractChangeRecordCompareImpl {
    @Resource
    private AssetSoftwareLicenseDao            softwareLicenseDao;
    @Resource
    private AssetSoftwareDao                   softwareDao;
    @Resource
    private RedisUtil                          redisUtil;
    @Resource
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;

    @Override
    List<Map<String, Object>> compareCommonBusinessInfo(Integer businessId) throws Exception {
        Integer hardware = 1;
        Integer oldInfo = 1;
        Integer newInfo = 0;
        List<String> changeValStrList = super.getTwoRecentChangeVal(businessId, hardware);
        List<Map<String, Object>> changeValList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(changeValStrList) && changeValStrList.size() > 1) {
            // 变更前的信息
            AssetOuterRequest oldAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(oldInfo),
                AssetOuterRequest.class);
            // 变更后的信息
            Asset oldAsset = assetRequestToAssetConverter.convert(oldAssetOuterRequest.getAsset(), Asset.class);
            AssetOuterRequest newAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(newInfo),
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
            // redis调用（通过区域ID查询名称）
            String oldAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(newAsset.getAreaId()));
            SysArea oldSysArea = redisUtil.getObject(oldAreaKey, SysArea.class);
            oldAssetBusinessInfo.setAreaName(oldSysArea != null ? oldSysArea.getFullName() : null);
            // redis调用（通过用户ID查询姓名）
            String oldKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(newAsset.getResponsibleUserId()));
            SysUser oldSysUser = redisUtil.getObject(oldKey, SysUser.class);
            oldAssetBusinessInfo.setResponsibleUserName(oldSysUser == null ? null : oldSysUser.getName());
            oldAssetBusinessInfo.setContactTel(oldAsset.getContactTel());
            oldAssetBusinessInfo.setEmail(oldAsset.getEmail());
            oldAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
            oldAssetBusinessInfo.setNumber(oldAsset.getNumber());
            oldAssetBusinessInfo.setInstallTypeName(oldAsset.getInstallType() != null
                ? oldAsset.getInstallType().equals(InstallType.MANUAL.getCode()) ? InstallType.MANUAL.getStatus()
                    : InstallType.AUTOMATIC.getStatus()
                : null);
            oldAssetBusinessInfo.setFirmwareVersion(oldAsset.getFirmwareVersion());
            oldAssetBusinessInfo.setOperationSystem(oldAsset.getOperationSystem());
            oldAssetBusinessInfo.setImportanceDegreeName(
                EnumUtil.getByCode(AssetImportanceDegreeEnum.class, oldAsset.getImportanceDegree()).getMsg());
            oldAssetBusinessInfo.setBuyDate(oldAsset.getBuyDate());
            oldAssetBusinessInfo.setServiceLife(oldAsset.getServiceLife());
            oldAssetBusinessInfo.setWarranty(oldAsset.getWarranty());
            oldAssetBusinessInfo.setDescrible(oldAsset.getDescrible());

            Asset newAssetBusinessInfo = new Asset();
            String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(newAsset.getAreaId()));
            SysArea newSysArea = redisUtil.getObject(newAreaKey, SysArea.class);
            newAssetBusinessInfo.setAreaName(newSysArea.getFullName());
            // redis调用（通过用户ID查询姓名）
            String newKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(newAsset.getResponsibleUserId()));
            SysUser newSysUser = redisUtil.getObject(newKey, SysUser.class);
            newAssetBusinessInfo.setResponsibleUserName(newSysUser == null ? null : newSysUser.getName());
            newAssetBusinessInfo.setContactTel(newAsset.getContactTel());
            newAssetBusinessInfo.setEmail(newAsset.getEmail());
            newAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
            newAssetBusinessInfo.setNumber(newAsset.getNumber());
            oldAssetBusinessInfo.setInstallTypeName(oldAsset.getInstallType() != null
                ? oldAsset.getInstallType().equals(InstallType.MANUAL.getCode()) ? InstallType.MANUAL.getStatus()
                    : InstallType.AUTOMATIC.getStatus()
                : null);
            newAssetBusinessInfo.setFirmwareVersion(newAsset.getFirmwareVersion());
            newAssetBusinessInfo.setOperationSystem(newAsset.getOperationSystem());
            newAssetBusinessInfo.setImportanceDegreeName(
                EnumUtil.getByCode(AssetImportanceDegreeEnum.class, newAsset.getImportanceDegree()).getMsg());
            newAssetBusinessInfo.setBuyDate(newAsset.getBuyDate());
            newAssetBusinessInfo.setServiceLife(newAsset.getServiceLife());
            newAssetBusinessInfo.setWarranty(newAsset.getWarranty());
            newAssetBusinessInfo.setDescrible(newAsset.getDescrible());

            List<Map<String, Object>> assetBusinessInfoCompareResult = CompareUtils.compareClass(oldAssetBusinessInfo,
                newAssetBusinessInfo, InfoLabelEnum.BUSINESSINFO.getMsg());

            // 提取内存字段变更信息
            List<List<Map<String, Object>>> assetMemoryCompareResult = new ArrayList<>();
            List<AssetMemoryRequest> memoryList = oldAssetOuterRequest.getMemory();
            List<AssetMemoryRequest> newMemoryList = newAssetOuterRequest.getMemory();
            AssetMemory newMemory = new AssetMemory();
            AssetMemory oldMemory = new AssetMemory();
            if (newMemoryList == null && memoryList != null) {
                for (AssetMemoryRequest memoryRequest : memoryList) {
                    buildMemoryCompareData(oldMemory, memoryRequest);
                    assetMemoryCompareResult
                        .add(CompareUtils.compareClass(oldMemory, newMemory, InfoLabelEnum.MEMORY.getMsg()));
                }
            } else if (newMemoryList != null && memoryList == null) {
                for (AssetMemoryRequest assetMemoryRequest : newMemoryList) {
                    buildMemoryCompareData(newMemory, assetMemoryRequest);
                    assetMemoryCompareResult
                        .add(CompareUtils.compareClass(oldMemory, newMemory, InfoLabelEnum.MEMORY.getMsg()));
                }
            } else if (CollectionUtils.isNotEmpty(memoryList)) {
                Map<String, AssetMemoryRequest> newMemoryMap = null;
                for (AssetMemoryRequest memoryRequest : memoryList) {
                    buildMemoryCompareData(oldMemory, memoryRequest);
                    newMemoryMap = this.getMemoryByIdMap(newMemoryList);
                    if (newMemoryMap.containsKey(memoryRequest.getId())) {
                        AssetMemoryRequest comparedMemoryRequest = newMemoryMap.get(memoryRequest.getId());
                        buildMemoryCompareData(newMemory, comparedMemoryRequest);
                        List<Map<String, Object>> mapList = CompareUtils.compareClass(oldMemory, newMemory,
                            InfoLabelEnum.MEMORY.getMsg());
                        if (mapList != null) {
                            assetMemoryCompareResult.add(mapList);
                        }
                        newMemoryMap.remove(memoryRequest.getId());
                    }
                }
                // 处理新增的部件
                processNewMemoryComponent(assetMemoryCompareResult, newMemory, oldMemory, newMemoryMap);
            }

            // 提取CPU字段变更信息
            List<List<Map<String, Object>>> assetCpuCompareResult = new ArrayList<>();
            List<AssetCpuRequest> oldCpuList = oldAssetOuterRequest.getCpu();
            List<AssetCpuRequest> newCpuList = newAssetOuterRequest.getCpu();
            AssetCpu oldCpu = new AssetCpu();
            AssetCpu newCpu = new AssetCpu();
            if (newCpuList == null && oldCpuList != null) {
                for (AssetCpuRequest cpuRequest : oldCpuList) {
                    buildCpuCompareData(oldCpu, cpuRequest);
                    assetCpuCompareResult.add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (newCpuList != null && oldCpuList == null) {
                for (AssetCpuRequest assetCpuRequest : newCpuList) {
                    buildCpuCompareData(newCpu, assetCpuRequest);
                    assetCpuCompareResult.add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (CollectionUtils.isNotEmpty(oldCpuList)) {
                Map<String, AssetCpuRequest> newCpuMap = null;
                for (AssetCpuRequest assetCpuRequest : oldCpuList) {
                    buildCpuCompareData(oldCpu, assetCpuRequest);
                    newCpuMap = this.getCpuByIdMap(newCpuList);
                    if (newCpuMap.containsKey(assetCpuRequest.getId())) {
                        AssetCpuRequest newCpuRequest = newCpuMap.get(assetCpuRequest.getId());
                        buildCpuCompareData(newCpu, newCpuRequest);
                        List<Map<String, Object>> mapList = CompareUtils.compareClass(oldCpu, newCpu,
                            InfoLabelEnum.CPU.getMsg());
                        if (mapList != null) {
                            assetCpuCompareResult.add(mapList);
                        }
                        newCpuMap.remove(assetCpuRequest.getId());
                    }
                }
                // 处理新增的部件
                processNewCpuComponent(assetCpuCompareResult, newCpu, oldCpu, newCpuMap);

            }

            // 提取硬盘字段变更信息
            List<List<Map<String, Object>>> assetHardDiskCompareResult = new ArrayList<>();
            List<AssetHardDiskRequest> oldHardDiskRequestList = oldAssetOuterRequest.getHardDisk();
            List<AssetHardDiskRequest> newHardDiskList = newAssetOuterRequest.getHardDisk();
            AssetHardDisk oldHardDisk = new AssetHardDisk();
            AssetHardDisk newHardDisk = new AssetHardDisk();
            if (newHardDiskList == null && oldHardDiskRequestList != null) {
                for (AssetHardDiskRequest hardDiskRequest : oldHardDiskRequestList) {
                    buildHarddiskCompareData(oldHardDisk, hardDiskRequest);
                    assetHardDiskCompareResult
                        .add(CompareUtils.compareClass(oldHardDisk, newHardDisk, InfoLabelEnum.HARDDISK.getMsg()));
                }
            } else if (newHardDiskList != null && oldHardDiskRequestList == null) {
                for (AssetHardDiskRequest assetHardDiskRequest : newHardDiskList) {
                    buildHarddiskCompareData(newHardDisk, assetHardDiskRequest);
                    assetHardDiskCompareResult
                        .add(CompareUtils.compareClass(oldHardDisk, newHardDisk, InfoLabelEnum.HARDDISK.getMsg()));
                }
            } else if (CollectionUtils.isNotEmpty(newHardDiskList)) {
                Map<String, AssetHardDiskRequest> newHardDiskMap = null;
                for (AssetHardDiskRequest assetHardDiskRequest : oldHardDiskRequestList) {
                    buildHarddiskCompareData(oldHardDisk, assetHardDiskRequest);
                    newHardDiskMap = this.getHarddiskByIdMap(newHardDiskList);
                    if (newHardDiskMap.containsKey(assetHardDiskRequest.getId())) {
                        AssetHardDiskRequest newHarddiskRequest = newHardDiskMap.get(assetHardDiskRequest.getId());
                        buildHarddiskCompareData(newHardDisk, newHarddiskRequest);
                        List<Map<String, Object>> mapList = CompareUtils.compareClass(oldHardDisk, newHardDisk,
                            InfoLabelEnum.HARDDISK.getMsg());
                        if (mapList != null) {
                            assetHardDiskCompareResult.add(mapList);
                        }
                        newHardDiskMap.remove(assetHardDiskRequest.getId());
                    }
                }
                // 处理新增的部件
                processNewHarddiskComponent(assetHardDiskCompareResult, newHardDisk, oldHardDisk, newHardDiskMap);
            }

            // 提取主板字段变更信息
            List<List<Map<String, Object>>> assetMainboardCompareResult = new ArrayList<>();
            List<AssetMainboradRequest> oldMainboardList = oldAssetOuterRequest.getMainboard();
            List<AssetMainboradRequest> newMainboardList = newAssetOuterRequest.getMainboard();
            AssetMainborad oldMainboard = new AssetMainborad();
            AssetMainborad newMainboard = new AssetMainborad();
            if (newMainboardList == null && oldMainboardList != null) {
                for (AssetMainboradRequest mainboradRequest : oldMainboardList) {
                    buildMainboardCompareData(oldMainboard, mainboradRequest);
                    assetMainboardCompareResult
                        .add(CompareUtils.compareClass(oldMainboard, newMainboard, InfoLabelEnum.MAINBORAD.getMsg()));
                }
            } else if (newMainboardList != null && oldMainboardList == null) {
                for (AssetMainboradRequest assetMainboradRequest : newMainboardList) {
                    buildMainboardCompareData(newMainboard, assetMainboradRequest);
                    assetMainboardCompareResult
                        .add(CompareUtils.compareClass(oldMainboard, newMainboard, InfoLabelEnum.MAINBORAD.getMsg()));
                }
            } else if (CollectionUtils.isNotEmpty(newMainboardList)) {
                Map<String, AssetMainboradRequest> newMainboardMap = null;
                for (AssetMainboradRequest assetMainboardRequest : oldMainboardList) {
                    buildMainboardCompareData(oldMainboard, assetMainboardRequest);
                    newMainboardMap = this.getMainboardByIdMap(newMainboardList);
                    if (newMainboardMap.containsKey(assetMainboardRequest.getId())) {
                        AssetMainboradRequest newMainboardRequest = newMainboardMap.get(assetMainboardRequest.getId());
                        buildMainboardCompareData(newMainboard, newMainboardRequest);
                        List<Map<String, Object>> mapList = CompareUtils.compareClass(oldMainboard, newMainboard,
                            InfoLabelEnum.MAINBORAD.getMsg());
                        if (mapList != null) {
                            assetMainboardCompareResult.add(mapList);
                        }
                        newMainboardMap.remove(assetMainboardRequest.getId());
                    }
                }
                // 处理新增的部件
                processMainboardComponent(assetMainboardCompareResult, newMainboard, oldMainboard, newMainboardMap);
            }

            // 提取网卡字段变更信息
            List<List<Map<String, Object>>> assetNetworkCompareResult = new ArrayList<>();
            List<AssetNetworkCardRequest> oldNetworkCardList = oldAssetOuterRequest.getNetworkCard();
            List<AssetNetworkCardRequest> newNetworkCardList = newAssetOuterRequest.getNetworkCard();
            AssetNetworkCard oldNetworkCard = new AssetNetworkCard();
            AssetNetworkCard newNetworkCard = new AssetNetworkCard();
            if (newNetworkCardList == null && oldNetworkCardList != null) {
                for (AssetNetworkCardRequest networkCardRequest : oldNetworkCardList) {
                    buildNetworkCardCompareData(oldNetworkCard, networkCardRequest);
                    assetNetworkCompareResult
                        .add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.NETWORKCARD.getMsg()));
                }
            } else if (newNetworkCardList != null && oldNetworkCardList == null) {
                for (AssetNetworkCardRequest assetNetworkCardRequest : newNetworkCardList) {
                    buildNetworkCardCompareData(newNetworkCard, assetNetworkCardRequest);
                    assetNetworkCompareResult.add(
                        CompareUtils.compareClass(oldNetworkCard, newNetworkCard, InfoLabelEnum.NETWORKCARD.getMsg()));
                }
            } else if (CollectionUtils.isNotEmpty(oldNetworkCardList)) {
                Map<String, AssetNetworkCardRequest> newNetworkCardMap = null;
                for (AssetNetworkCardRequest assetNetworkCardRequest : oldNetworkCardList) {
                    buildNetworkCardCompareData(oldNetworkCard, assetNetworkCardRequest);
                    newNetworkCardMap = this.getNetworkCardByIdMap(newNetworkCardList);
                    if (newNetworkCardMap.containsKey(assetNetworkCardRequest.getId())) {
                        AssetNetworkCardRequest newNetworkCardRequest = newNetworkCardMap
                            .get(assetNetworkCardRequest.getId());
                        buildNetworkCardCompareData(newNetworkCard, newNetworkCardRequest);
                        List<Map<String, Object>> mapList = CompareUtils.compareClass(oldNetworkCard, newNetworkCard,
                            InfoLabelEnum.NETWORKCARD.getMsg());
                        if (mapList != null) {
                            assetNetworkCompareResult.add(mapList);
                        }
                        newNetworkCardMap.remove(assetNetworkCardRequest.getId());
                    }
                }
                // 处理新增的部件
                processNewNetworkCardComponent(assetNetworkCompareResult, newNetworkCard, oldNetworkCard,
                    newNetworkCardMap);
            }

            // 提取关联软件变更信息
            List<List<Map<String, Object>>> relateSoftewareCompareResult = new ArrayList<>();
            /*List<AssetSoftwareRelationRequest> oldSoftwareRelationList = oldAssetOuterRequest
                .getAssetSoftwareRelationList();
            List<AssetSoftwareRelationRequest> newSoftwareRelationList = newAssetOuterRequest
                .getAssetSoftwareRelationList();*/
            RelateSoftware oldRelateSoftware = new RelateSoftware();
            RelateSoftware newRelateSoftware = new RelateSoftware();
            /*if (newSoftwareRelationList == null && oldSoftwareRelationList != null) {
                for (AssetSoftwareRelationRequest assetSoftwareRelationRequest : oldSoftwareRelationList) {
                    buildSoftwareRelationCompareData(oldRelateSoftware, assetSoftwareRelationRequest);
                    relateSoftewareCompareResult.add(CompareUtils.compareClass(oldRelateSoftware, newRelateSoftware,
                        InfoLabelEnum.RELATESOFTWARE.getMsg() + "-" + oldRelateSoftware.getSoftName()));
                }
            } else if (newSoftwareRelationList != null && oldSoftwareRelationList == null) {
                for (AssetSoftwareRelationRequest softwareRelationRequest : newSoftwareRelationList) {
                    buildSoftwareRelationCompareData(newRelateSoftware, softwareRelationRequest);
                    relateSoftewareCompareResult.add(CompareUtils.compareClass(oldRelateSoftware, newRelateSoftware,
                        InfoLabelEnum.RELATESOFTWARE.getMsg() + "-" + oldRelateSoftware.getSoftName()));
                }
            } else if (CollectionUtils.isNotEmpty(newSoftwareRelationList)) {
                Map<String, AssetSoftwareRelationRequest> newSoftwareRelationMap = null;
                for (AssetSoftwareRelationRequest softwareRelationRequest : oldSoftwareRelationList) {
                    buildSoftwareRelationCompareData(oldRelateSoftware, softwareRelationRequest);
                    newSoftwareRelationMap = this.getRelationSoftwareByIdMap(newSoftwareRelationList);
                    if (newSoftwareRelationMap.containsKey(softwareRelationRequest.getId())) {
                        AssetSoftwareRelationRequest comparedSoftwareRelationRequest = newSoftwareRelationMap
                            .get(softwareRelationRequest.getId());
                        buildSoftwareRelationCompareData(newRelateSoftware, comparedSoftwareRelationRequest);
                        List<Map<String, Object>> mapList = CompareUtils.compareClass(oldRelateSoftware,
                            newRelateSoftware,
                            InfoLabelEnum.RELATESOFTWARE.getMsg() + "-" + oldRelateSoftware.getSoftName());
                        if (mapList != null) {
                            relateSoftewareCompareResult.add(mapList);
                        }
                        newSoftwareRelationMap.remove(softwareRelationRequest.getId());
                    }
                }
                // 处理新增的部件
                processRelateSofwareComponent(relateSoftewareCompareResult, newRelateSoftware, oldRelateSoftware,
                    newSoftwareRelationMap);
            }*/

            // 合并集合
            if (CollectionUtils.isNotEmpty(assetCommonInoCompareResult)) {
                changeValList.addAll(assetCommonInoCompareResult);
            }

            if (CollectionUtils.isNotEmpty(assetBusinessInfoCompareResult)) {
                changeValList.addAll(assetBusinessInfoCompareResult);
            }
            // 内存
            if (CollectionUtils.isNotEmpty(assetMemoryCompareResult)) {
                changeValList.addAll(getMaps(assetMemoryCompareResult, InfoLabelEnum.MEMORY.getMsg()));
            }
            // CPU
            if (CollectionUtils.isNotEmpty(assetCpuCompareResult)) {
                changeValList.addAll(getMaps(assetCpuCompareResult, InfoLabelEnum.CPU.getMsg()));
            }
            // 硬盘
            if (CollectionUtils.isNotEmpty(assetHardDiskCompareResult)) {
                changeValList.addAll(getMaps(assetHardDiskCompareResult, InfoLabelEnum.HARDDISK.getMsg()));
            }
            // 主板
            if (CollectionUtils.isNotEmpty(assetMainboardCompareResult)) {
                changeValList.addAll(getMaps(assetMainboardCompareResult, InfoLabelEnum.MAINBORAD.getMsg()));
            }
            // 网卡
            if (CollectionUtils.isNotEmpty(assetNetworkCompareResult)) {
                changeValList.addAll(getMaps(assetNetworkCompareResult, InfoLabelEnum.NETWORKCARD.getMsg()));
            }
            // 关联软件
            if (CollectionUtils.isNotEmpty(relateSoftewareCompareResult)) {
                for (List<Map<String, Object>> listMap : relateSoftewareCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
            return changeValList;
        }
        return null;

    }

    /**
     * 处理新增的内存部件
     * @param assetMemoryCompareResult
     * @param newMemory
     * @param oldMemory
     * @param newMemoryMap
     * @throws IllegalAccessException
     */
    private void processNewMemoryComponent(List<List<Map<String, Object>>> assetMemoryCompareResult,
                                           AssetMemory newMemory, AssetMemory oldMemory,
                                           Map<String, AssetMemoryRequest> newMemoryMap) throws IllegalAccessException {
        if (newMemoryMap.size() > 0) {
            Set<Map.Entry<String, AssetMemoryRequest>> mapEntrySet = newMemoryMap.entrySet();
            for (Map.Entry<String, AssetMemoryRequest> mapEntry : mapEntrySet) {
                buildMemoryCompareData(newMemory, mapEntry.getValue());
                assetMemoryCompareResult
                    .add(CompareUtils.compareClass(oldMemory, newMemory, InfoLabelEnum.MEMORY.getMsg()));
            }
        }
    }

    /**
     * 处理新增的CPU部件
     * @param assetCpuCompareResult
     * @param newCpu
     * @param oldCpu
     * @param newCpuMap
     * @throws IllegalAccessException
     */
    private void processNewCpuComponent(List<List<Map<String, Object>>> assetCpuCompareResult, AssetCpu newCpu,
                                        AssetCpu oldCpu,
                                        Map<String, AssetCpuRequest> newCpuMap) throws IllegalAccessException {
        if (newCpuMap.size() > 0) {
            Set<Map.Entry<String, AssetCpuRequest>> mapEntrySet = newCpuMap.entrySet();
            for (Map.Entry<String, AssetCpuRequest> mapEntry : mapEntrySet) {
                buildCpuCompareData(newCpu, mapEntry.getValue());
                assetCpuCompareResult.add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
            }
        }
    }

    /**
     * 处理新增的硬盘部件
     * @param assetHarddiskCompareResult
     * @param newHarddisk
     * @param oldHarddisk
     * @param newHarddiskMap
     * @throws IllegalAccessException
     */
    private void processNewHarddiskComponent(List<List<Map<String, Object>>> assetHarddiskCompareResult,
                                             AssetHardDisk newHarddisk, AssetHardDisk oldHarddisk,
                                             Map<String, AssetHardDiskRequest> newHarddiskMap) throws IllegalAccessException {
        Set<Map.Entry<String, AssetHardDiskRequest>> mapEntrySet = newHarddiskMap.entrySet();
        for (Map.Entry<String, AssetHardDiskRequest> mapEntry : mapEntrySet) {
            buildHarddiskCompareData(newHarddisk, mapEntry.getValue());
            assetHarddiskCompareResult
                .add(CompareUtils.compareClass(oldHarddisk, newHarddisk, InfoLabelEnum.HARDDISK.getMsg()));
        }
    }

    /**
     * 处理新增的网卡部件
     * @param assetNetworkCardCompareResult
     * @param newNetworkCard
     * @param oldNetworkCard
     * @param newNetworkCardMap
     * @throws IllegalAccessException
     */
    private void processNewNetworkCardComponent(List<List<Map<String, Object>>> assetNetworkCardCompareResult,
                                                AssetNetworkCard newNetworkCard, AssetNetworkCard oldNetworkCard,
                                                Map<String, AssetNetworkCardRequest> newNetworkCardMap) throws IllegalAccessException {
        if (newNetworkCardMap.size() > 0) {
            Set<Map.Entry<String, AssetNetworkCardRequest>> mapEntrySet = newNetworkCardMap.entrySet();
            for (Map.Entry<String, AssetNetworkCardRequest> mapEntry : mapEntrySet) {
                buildNetworkCardCompareData(newNetworkCard, mapEntry.getValue());
                assetNetworkCardCompareResult
                    .add(CompareUtils.compareClass(oldNetworkCard, newNetworkCard, InfoLabelEnum.NETWORKCARD.getMsg()));
            }
        }
    }

    /**
     * 处理新增的主板部件
     * @param assetMainboardCompareResult
     * @param newMainboard
     * @param oldMainboard
     * @param newMainboardMap
     * @throws IllegalAccessException
     */
    private void processMainboardComponent(List<List<Map<String, Object>>> assetMainboardCompareResult,
                                           AssetMainborad newMainboard, AssetMainborad oldMainboard,
                                           Map<String, AssetMainboradRequest> newMainboardMap) throws IllegalAccessException {
        if (newMainboardMap.size() > 0) {
            Set<Map.Entry<String, AssetMainboradRequest>> mapEntrySet = newMainboardMap.entrySet();
            for (Map.Entry<String, AssetMainboradRequest> mapEntry : mapEntrySet) {
                buildMainboardCompareData(newMainboard, mapEntry.getValue());
                assetMainboardCompareResult
                    .add(CompareUtils.compareClass(oldMainboard, newMainboard, InfoLabelEnum.NETWORKCARD.getMsg()));
            }
        }

    }

    /**
     * 处理新增的关联软件部件
     * @param assetRelateSoftwareCompareResult
     * @param newRelateSoftware
     * @param oldRelateSoftware
     * @param newRelateSoftwareMap
     * @throws Exception
     */
    private void processRelateSofwareComponent(List<List<Map<String, Object>>> assetRelateSoftwareCompareResult,
                                               RelateSoftware newRelateSoftware, RelateSoftware oldRelateSoftware,
                                               Map<String, AssetSoftwareRelationRequest> newRelateSoftwareMap) throws Exception {
        if (newRelateSoftwareMap.size() > 0) {
            Set<Map.Entry<String, AssetSoftwareRelationRequest>> mapEntrySet = newRelateSoftwareMap.entrySet();
            for (Map.Entry<String, AssetSoftwareRelationRequest> mapEntry : mapEntrySet) {
                buildSoftwareRelationCompareData(newRelateSoftware, mapEntry.getValue());
                assetRelateSoftwareCompareResult.add(CompareUtils.compareClass(oldRelateSoftware, newRelateSoftware,
                    InfoLabelEnum.RELATESOFTWARE.getMsg()));
            }
        }
    }

    /**
     * 构建内存比较数据
     * @param memory
     * @param request
     */
    private void buildMemoryCompareData(AssetMemory memory, AssetMemoryRequest request) {
        memory.setId(DataTypeUtils.stringToInteger(request.getId()));
        memory.setBrand(request.getBrand());
        TransferTypeMemoryEnum transferTypeMemoryEnum = EnumUtil.getByCode(TransferTypeMemoryEnum.class,
            request.getTransferType());
        memory.setTransferTypeName(transferTypeMemoryEnum != null ? transferTypeMemoryEnum.getMsg() : null);
        memory.setSerial(request.getSerial());
        memory.setCapacity(request.getCapacity());
        memory.setFrequency(request.getFrequency());
        memory.setSlotType(request.getSlotType());
        memory.setHeatsink(request.getHeatsink());
        memory.setStitch(request.getStitch());
    }

    /**
     * 构建CPU比较数据
     * @param cpu
     * @param request
     */
    private void buildCpuCompareData(AssetCpu cpu, AssetCpuRequest request) {
        cpu.setId(DataTypeUtils.stringToInteger(request.getId()));
        cpu.setBrand(request.getBrand());
        cpu.setModel(request.getModel());
        cpu.setSerial(request.getSerial());
        cpu.setMainFrequency(request.getMainFrequency());
        cpu.setThreadSize(request.getThreadSize());
        cpu.setCoreSize(request.getCoreSize());
    }

    /**
     * 构建硬盘比较数据
     * @param hardDisk
     * @param request
     */
    private void buildHarddiskCompareData(AssetHardDisk hardDisk, AssetHardDiskRequest request) {
        hardDisk.setId(DataTypeUtils.stringToInteger(request.getId()));
        hardDisk.setBrand(request.getBrand());
        hardDisk.setModel(request.getModel());
        hardDisk.setSerial(request.getSerial());
        hardDisk.setCapacity(request.getCapacity());
        hardDisk.setInterfaceType(request.getInterfaceType());
        hardDisk.setDiskType(request.getDiskType());
        hardDisk.setBuyDate(request.getBuyDate());
    }

    /**
     * 构建网卡比较数据
     * @param networkCard
     * @param request
     */
    private void buildNetworkCardCompareData(AssetNetworkCard networkCard, AssetNetworkCardRequest request) {
        networkCard.setId(DataTypeUtils.stringToInteger(request.getId()));
        networkCard.setBrand(request.getBrand());
        networkCard.setModel(request.getModel());
        networkCard.setSerial(request.getSerial());
        networkCard.setIpAddress(request.getIpAddress());
        networkCard.setMacAddress(request.getMacAddress());
        networkCard.setSubnetMask(request.getSubnetMask());
        networkCard.setDefaultGateway(request.getDefaultGateway());
    }

    /**
     * 构主板比较数据
     * @param mainboard
     * @param request
     */
    private void buildMainboardCompareData(AssetMainborad mainboard, AssetMainboradRequest request) {
        mainboard.setId(DataTypeUtils.stringToInteger(request.getId()));
        mainboard.setBrand(request.getBrand());
        mainboard.setModel(request.getModel());
        mainboard.setSerial(request.getSerial());
        mainboard.setBiosVersion(request.getBiosVersion());
        mainboard.setBiosDate(request.getBiosDate());
    }

    /**
     * 构关联软件比较数据
     * @param relateSoftware
     * @param request
     */
    private void buildSoftwareRelationCompareData(RelateSoftware relateSoftware,
                                                  AssetSoftwareRelationRequest request) throws Exception {
        relateSoftware.setSoftName(softwareDao.getById(request.getSoftwareId()).getName());
        relateSoftware.setLicenseSecretKey(request.getLicenseSecretKey());
        relateSoftware.setMulPort(request.getPort());
        relateSoftware.setDescription(
            org.apache.commons.lang.StringUtils.isNotEmpty(request.getMemo()) ? request.getMemo().trim() : null);
    }

    private List<Map<String, Object>> getMaps(List<List<Map<String, Object>>> assetMemoryCompareResult, String msg) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        List<String> tempOldList = new ArrayList<>();
        List<String> newOldList = new ArrayList<>();
        for (List<Map<String, Object>> listMap : assetMemoryCompareResult) {
            for (Map<String, Object> fieldMap : listMap) {
                tempOldList.add(fieldMap.get("name").toString() + ": " + fieldMap.get("old"));
                newOldList.add(fieldMap.get("name").toString() + ": " + fieldMap.get("new"));
            }

        }
        map.put("old", StringUtils.trim(tempOldList.toString(), "[", "]"));
        map.put("new", StringUtils.trim(newOldList.toString(), "[", "]"));
        map.put("label", msg);
        resultMap.add(map);
        return resultMap;
    }

    private Map<String, AssetMemoryRequest> getMemoryByIdMap(List<AssetMemoryRequest> list) {
        return list.stream().collect(Collectors.toMap(AssetMemoryRequest::getId, memory -> memory));
    }

    private Map<String, AssetCpuRequest> getCpuByIdMap(List<AssetCpuRequest> list) {
        return list.stream().collect(Collectors.toMap(AssetCpuRequest::getId, cpu -> cpu));
    }

    private Map<String, AssetHardDiskRequest> getHarddiskByIdMap(List<AssetHardDiskRequest> list) {
        return list.stream().collect(Collectors.toMap(AssetHardDiskRequest::getId, hardisk -> hardisk));
    }

    private Map<String, AssetNetworkCardRequest> getNetworkCardByIdMap(List<AssetNetworkCardRequest> list) {
        return list.stream().collect(Collectors.toMap(AssetNetworkCardRequest::getId, networkCard -> networkCard));
    }

    private Map<String, AssetMainboradRequest> getMainboardByIdMap(List<AssetMainboradRequest> list) {
        return list.stream().collect(Collectors.toMap(AssetMainboradRequest::getId, mainboard -> mainboard));
    }

    private Map<String, AssetSoftwareRelationRequest> getRelationSoftwareByIdMap(List<AssetSoftwareRelationRequest> list) {
        return list.stream()
            .collect(Collectors.toMap(AssetSoftwareRelationRequest::getId, softwareRelation -> softwareRelation));
    }

    @Override
    protected List<? extends BaseRequest> compareComponentInfo() {
        return null;
    }

    /**
     * 关联软件信息
     */
    class RelateSoftware {
        private String softwareId;
        @ApiModelProperty("名称")
        private String softName;
        @ApiModelProperty("许可秘钥")
        private String licenseSecretKey;
        @ApiModelProperty("端口")
        private String mulPort;
        @ApiModelProperty("描述")
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
