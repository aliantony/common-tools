package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.entity.*;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.StringUtils;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.request.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysUser;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.JsonUtil;

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
            oldAssetBusinessInfo.setAreaName(oldSysArea.getFullName());
            // redis调用（通过用户ID查询姓名）
            String oldKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(newAsset.getResponsibleUserId()));
            SysUser oldSysUser = redisUtil.getObject(oldKey, SysUser.class);
            oldAssetBusinessInfo.setResponsibleUserName(oldSysUser == null ? null : oldSysUser.getName());
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
            List<AssetMemoryRequest> newMemoryList = newAssetOuterRequest.getMemory();
            AssetMemory newMemory = new AssetMemory();
            AssetMemory oldMemory = new AssetMemory();
            if (newMemoryList == null && memoryList != null) {
                for (AssetMemoryRequest memoryRequest : memoryList) {
                    oldMemory.setId(DataTypeUtils.stringToInteger(memoryRequest.getId()));
                    oldMemory.setBrand(memoryRequest.getBrand());
                    oldMemory.setTransferType(memoryRequest.getTransferType());
                    oldMemory.setSerial(memoryRequest.getSerial());
                    oldMemory.setCapacity(memoryRequest.getCapacity());
                    oldMemory.setFrequency(memoryRequest.getFrequency());
                    oldMemory.setSlotType(memoryRequest.getSlotType());
                    oldMemory.setHeatsink(memoryRequest.getHeatsink());
                    oldMemory.setStitch(memoryRequest.getStitch());
                    assetMemoryCompareResult
                        .add(CompareUtils.compareClass(oldMemory, newMemory, InfoLabelEnum.MEMORY.getMsg()));
                }
            } else if (newMemoryList != null && memoryList == null) {
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
                    assetMemoryCompareResult
                        .add(CompareUtils.compareClass(oldMemory, newMemory, InfoLabelEnum.MEMORY.getMsg()));
                }
            } else if (newMemoryList != null) {
                assetMemoryCompareResult.add(
                    CompareUtils.compareClass(memoryList.get(0), newMemoryList.get(0), InfoLabelEnum.MEMORY.getMsg()));
            }

            // 提取CPU字段变更信息
            List<List<Map<String, Object>>> assetCpuCompareResult = new ArrayList<>();
            List<AssetCpuRequest> cpuRequestList = oldAssetOuterRequest.getCpu();
            List<AssetCpuRequest> newCpuList = newAssetOuterRequest.getCpu();
            AssetCpu oldCpu = new AssetCpu();
            AssetCpu newCpu = new AssetCpu();
            if (newCpuList == null && cpuRequestList != null) {
                for (AssetCpuRequest cpuRequest : cpuRequestList) {
                    oldCpu.setId(DataTypeUtils.stringToInteger(cpuRequest.getId()));
                    oldCpu.setBrand(cpuRequest.getBrand());
                    oldCpu.setModel(cpuRequest.getModel());
                    oldCpu.setSerial(cpuRequest.getSerial());
                    oldCpu.setMainFrequency(cpuRequest.getMainFrequency());
                    oldCpu.setThreadSize(cpuRequest.getThreadSize());
                    oldCpu.setCoreSize(cpuRequest.getCoreSize());
                    assetCpuCompareResult.add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (newCpuList != null && cpuRequestList == null) {
                for (AssetCpuRequest assetCpuRequest : newCpuList) {
                    newCpu.setId(DataTypeUtils.stringToInteger(assetCpuRequest.getId()));
                    newCpu.setBrand(assetCpuRequest.getBrand());
                    newCpu.setModel(assetCpuRequest.getModel());
                    newCpu.setSerial(assetCpuRequest.getSerial());
                    newCpu.setMainFrequency(assetCpuRequest.getMainFrequency());
                    newCpu.setThreadSize(assetCpuRequest.getThreadSize());
                    newCpu.setCoreSize(assetCpuRequest.getCoreSize());
                    assetCpuCompareResult.add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (newCpuList != null) {
                assetCpuCompareResult.add(
                    CompareUtils.compareClass(memoryList.get(0), newMemoryList.get(0), InfoLabelEnum.MEMORY.getMsg()));
            }

            // 提取硬盘字段变更信息
            List<List<Map<String, Object>>> assetHardDiskCompareResult = new ArrayList<>();
            List<AssetHardDiskRequest> hardDiskRequestList = oldAssetOuterRequest.getHardDisk();
            List<AssetHardDiskRequest> newHardDiskList = oldAssetOuterRequest.getHardDisk();
            AssetHardDisk oldHardDisk = new AssetHardDisk();
            AssetHardDisk newHardDisk = new AssetHardDisk();
            if (newHardDiskList == null && hardDiskRequestList != null) {
                for (AssetHardDiskRequest hardDiskRequest : hardDiskRequestList) {
                    oldHardDisk.setId(DataTypeUtils.stringToInteger(hardDiskRequest.getId()));
                    oldHardDisk.setBrand(hardDiskRequest.getBrand());
                    oldHardDisk.setModel(hardDiskRequest.getModel());
                    oldHardDisk.setSerial(hardDiskRequest.getSerial());
                    oldHardDisk.setCapacity(hardDiskRequest.getCapacity());
                    oldHardDisk.setInterfaceType(hardDiskRequest.getInterfaceType());
                    oldHardDisk.setDiskType(hardDiskRequest.getDiskType());
                    oldHardDisk.setBuyDate(hardDiskRequest.getBuyDate());
                    assetHardDiskCompareResult
                        .add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (newHardDiskList != null && hardDiskRequestList == null) {
                for (AssetHardDiskRequest assetHardDiskRequest : newHardDiskList) {
                    newHardDisk.setId(DataTypeUtils.stringToInteger(assetHardDiskRequest.getId()));
                    newHardDisk.setBrand(assetHardDiskRequest.getBrand());
                    newHardDisk.setModel(assetHardDiskRequest.getModel());
                    newHardDisk.setSerial(assetHardDiskRequest.getSerial());
                    newHardDisk.setCapacity(assetHardDiskRequest.getCapacity());
                    newHardDisk.setInterfaceType(assetHardDiskRequest.getInterfaceType());
                    newHardDisk.setDiskType(assetHardDiskRequest.getDiskType());
                    newHardDisk.setBuyDate(assetHardDiskRequest.getBuyDate());
                    assetHardDiskCompareResult
                        .add(CompareUtils.compareClass(oldHardDisk, newHardDisk, InfoLabelEnum.HARDDISK.getMsg()));
                }
            } else if (newHardDiskList != null) {
                assetHardDiskCompareResult.add(
                    CompareUtils.compareClass(memoryList.get(0), newMemoryList.get(0), InfoLabelEnum.MEMORY.getMsg()));
            }

            // 提取主板字段变更信息
            List<List<Map<String, Object>>> assetMainboardCompareResult = new ArrayList<>();
            List<AssetMainboradRequest> mainboradRequestList = oldAssetOuterRequest.getMainboard();
            List<AssetMainboradRequest> newMainboardList = oldAssetOuterRequest.getMainboard();
            AssetMainborad oldMainborad = new AssetMainborad();
            AssetMainborad newMainboard = new AssetMainborad();
            if (newMainboardList == null && mainboradRequestList != null) {
                for (AssetMainboradRequest mainboradRequest : mainboradRequestList) {
                    oldMainborad.setId(DataTypeUtils.stringToInteger(mainboradRequest.getId()));
                    oldMainborad.setBrand(mainboradRequest.getBrand());
                    oldMainborad.setModel(mainboradRequest.getModel());
                    oldMainborad.setSerial(mainboradRequest.getSerial());
                    oldMainborad.setBiosVersion(mainboradRequest.getBiosVersion());
                    oldMainborad.setBiosDate(mainboradRequest.getBiosDate());
                    assetMainboardCompareResult
                        .add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (newMainboardList != null && mainboradRequestList == null) {
                for (AssetMainboradRequest assetMainboradRequest : newMainboardList) {
                    newMainboard.setId(DataTypeUtils.stringToInteger(assetMainboradRequest.getId()));
                    newMainboard.setBrand(assetMainboradRequest.getBrand());
                    newMainboard.setModel(assetMainboradRequest.getModel());
                    newMainboard.setSerial(assetMainboradRequest.getSerial());
                    newMainboard.setBiosVersion(assetMainboradRequest.getBiosVersion());
                    newMainboard.setBiosDate(assetMainboradRequest.getBiosDate());
                    assetMainboardCompareResult
                        .add(CompareUtils.compareClass(oldMainborad, newMainboard, InfoLabelEnum.MAINBORAD.getMsg()));
                }
            } else if (newMainboardList != null) {
                assetMainboardCompareResult.add(
                    CompareUtils.compareClass(memoryList.get(0), newMemoryList.get(0), InfoLabelEnum.MEMORY.getMsg()));
            }

            // 提取网卡字段变更信息
            List<List<Map<String, Object>>> assetNetworkCompareResult = new ArrayList<>();
            List<AssetNetworkCardRequest> networkCardList = oldAssetOuterRequest.getNetworkCard();
            List<AssetNetworkCardRequest> newNetworkCardList = oldAssetOuterRequest.getNetworkCard();
            AssetNetworkCard oldNetworkCard = new AssetNetworkCard();
            AssetNetworkCard newNetworkCard = new AssetNetworkCard();
            if (newNetworkCardList == null && networkCardList != null) {
                for (AssetNetworkCardRequest networkCardRequest : networkCardList) {
                    oldNetworkCard.setId(DataTypeUtils.stringToInteger(networkCardRequest.getId()));
                    oldNetworkCard.setBrand(networkCardRequest.getBrand());
                    oldNetworkCard.setModel(networkCardRequest.getModel());
                    oldNetworkCard.setSerial(networkCardRequest.getSerial());
                    oldNetworkCard.setIpAddress(networkCardRequest.getIpAddress());
                    oldNetworkCard.setMacAddress(networkCardRequest.getMacAddress());
                    oldNetworkCard.setSubnetMask(networkCardRequest.getSubnetMask());
                    oldNetworkCard.setDefaultGateway(networkCardRequest.getDefaultGateway());
                    assetNetworkCompareResult
                        .add(CompareUtils.compareClass(oldCpu, newCpu, InfoLabelEnum.CPU.getMsg()));
                }
            } else if (newNetworkCardList != null && networkCardList == null) {
                for (AssetNetworkCardRequest assetNetworkCardRequest : newNetworkCardList) {
                    newNetworkCard.setId(DataTypeUtils.stringToInteger(assetNetworkCardRequest.getId()));
                    newNetworkCard.setBrand(assetNetworkCardRequest.getBrand());
                    newNetworkCard.setModel(assetNetworkCardRequest.getModel());
                    newNetworkCard.setSerial(assetNetworkCardRequest.getSerial());
                    newNetworkCard.setIpAddress(assetNetworkCardRequest.getIpAddress());
                    newNetworkCard.setMacAddress(assetNetworkCardRequest.getMacAddress());
                    newNetworkCard.setSubnetMask(assetNetworkCardRequest.getSubnetMask());
                    newNetworkCard.setDefaultGateway(assetNetworkCardRequest.getDefaultGateway());
                    assetNetworkCompareResult.add(
                        CompareUtils.compareClass(oldNetworkCard, newNetworkCard, InfoLabelEnum.NETWORKCARD.getMsg()));
                }
            } else if (newNetworkCardList != null) {
                assetNetworkCompareResult.add(
                    CompareUtils.compareClass(memoryList.get(0), newMemoryList.get(0), InfoLabelEnum.MEMORY.getMsg()));
            }

            // 提取关联软件变更信息
            List<List<Map<String, Object>>> relateSoftewareCompareResult = new ArrayList<>();
            List<AssetSoftwareRelationRequest> oldSoftwareRelationList = oldAssetOuterRequest
                .getAssetSoftwareRelationList();
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
            // 内存
            if (CollectionUtils.isNotEmpty(assetMemoryCompareResult)) {
                changeValList.addAll(getMaps(assetMemoryCompareResult, InfoLabelEnum.MEMORY.getMsg()));
            }
            if (CollectionUtils.isNotEmpty(assetCpuCompareResult)) {
                changeValList.addAll(getMaps(assetMemoryCompareResult, InfoLabelEnum.CPU.getMsg()));
            }
            if (CollectionUtils.isNotEmpty(assetHardDiskCompareResult)) {
                changeValList.addAll(getMaps(assetMemoryCompareResult, InfoLabelEnum.HARDDISK.getMsg()));
            }
            if (CollectionUtils.isNotEmpty(assetMainboardCompareResult)) {
                changeValList.addAll(getMaps(assetMemoryCompareResult, InfoLabelEnum.MAINBORAD.getMsg()));
            }
            if (CollectionUtils.isNotEmpty(assetNetworkCompareResult)) {
                changeValList.addAll(getMaps(assetMemoryCompareResult, InfoLabelEnum.NETWORKCARD.getMsg()));
            }

            if (CollectionUtils.isNotEmpty(relateSoftewareCompareResult)) {
                for (List<Map<String, Object>> listMap : relateSoftewareCompareResult) {
                    changeValList.addAll(listMap);
                }
            }
        }
        return changeValList;

    }

    private List<Map<String, Object>> getMaps(List<List<Map<String, Object>>> assetMemoryCompareResult, String msg) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        List<String> tempOldList = new ArrayList<>();
        List<String> newOldList = new ArrayList<>();
        for (List<Map<String, Object>> listMap : assetMemoryCompareResult) {
            for (Map<String, Object> field : listMap) {
                tempOldList.add(field.get("name").toString() + ": " + field.get("old"));
                newOldList.add(field.get("name").toString() + ": " + field.get("new"));
            }

        }
        map.put("old", StringUtils.trim(tempOldList.toString(),"[","]"));
        map.put("new", StringUtils.trim(newOldList.toString(),"[","]"));
        map.put("label", msg);
        resultMap.add(map);
        return resultMap;
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
