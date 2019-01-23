package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.*;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetOperationTableEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.ActionResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.download.DownloadVO;
import com.antiy.common.download.ExcelDownloadUtil;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p> 资产主表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService {

    @Resource
    private AssetDao                                  assetDao;
    @Resource
    private AssetMainboradDao                         assetMainboradDao;
    @Resource
    private AssetMemoryDao                            assetMemoryDao;
    @Resource
    private AssetHardDiskDao                          assetHardDiskDao;
    @Resource
    private AssetCpuDao                               assetCpuDao;
    @Resource
    private AssetNetworkCardDao                       assetNetworkCardDao;
    @Resource
    private AssetNetworkEquipmentDao                  assetNetworkEquipmentDao;
    @Resource
    private AssetSafetyEquipmentDao                   assetSafetyEquipmentDao;
    @Resource
    private AssetSoftwareDao                          assetSoftwareDao;
    @Resource
    private AssetSoftwareLicenseDao                   assetSoftwareLicenseDao;
    @Resource
    private AssetCategoryModelDao                     assetCategoryModelDao;
    @Resource
    private TransactionTemplate                       transactionTemplate;
    @Resource
    private AssetSoftwareRelationDao                  assetSoftwareRelationDao;
    @Resource
    private AssetStorageMediumDao                     assetStorageMediumDao;
    @Resource
    private AssetOperationRecordDao                   assetOperationRecordDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>        requestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse>       responseConverter;
    @Resource
    private BaseConverter<Asset, ComputeDeviceEntity> entityConverter;
    @Resource
    private AssetUserDao                              assetUserDao;
    @Resource
    private AssetGroupRelationDao                     assetGroupRelationDao;
    @Resource
    private ExcelDownloadUtil                         excelDownloadUtil;
    @Resource
    private AssetEntityConvert                        assetEntityConvert;
    @Resource
    private ActivityClient                            activityClient;
    private static final Logger                       logger = LogUtils.get(AssetServiceImpl.class);

    @Override
    public Integer saveAsset(AssetOuterRequest request) throws Exception {

        Integer num = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {

                    AssetRequest requestAsset = request.getAsset();
                    List<AssetGroupRequest> assetGroup = requestAsset.getAssetGroup();
                    Asset asset = requestConverter.convert(requestAsset, Asset.class);
                    if (assetGroup != null && !assetGroup.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        assetGroup.forEach(assetGroupRequest -> {
                            asset.setAssetGroup(stringBuilder.append(assetGroupRequest.getName()).append(",")
                                .substring(0, stringBuilder.length() - 1));
                        });

                    }

                    asset.setResponsibleUserId(LoginUserUtil.getLoginUser().getId());
                    asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    asset.setAssetSource(2);
                    asset.setGmtCreate(System.currentTimeMillis());
                    asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());

                    assetDao.insert(asset);
                    if (assetGroup != null && !assetGroup.isEmpty()) {

                        for (AssetGroupRequest assetGroupRequest : assetGroup) {
                            AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                            assetGroupRelation.setAssetGroupId(Integer.parseInt(assetGroupRequest.getId()));
                            assetGroupRelation.setAssetId(asset.getId());
                            assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                            assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetGroupRequest, AssetEventEnum.ASSET_GROUP_INSERT.getName(), AssetEventEnum.ASSET_GROUP_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum .ASSET_GROUP_INSERT.getName() + " {}", assetGroupRequest.toString());
                            assetGroupRelationDao.insert(assetGroupRelation);
                        }
                    }

                    Integer aid = asset.getId();

                    AssetSafetyEquipmentRequest safetyEquipmentRequest = request.getSafetyEquipment();
                    if (safetyEquipmentRequest != null) {
                        AssetSafetyEquipment safetyEquipment = BeanConvert.convertBean(safetyEquipmentRequest,
                            AssetSafetyEquipment.class);
                        safetyEquipment.setAssetId(aid);
                        safetyEquipment.setGmtCreate(System.currentTimeMillis());
                        safetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        LogHandle.log(safetyEquipmentRequest, AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getName(), AssetEventEnum.ASSET_SAFE_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_SAFE_DETAIL_INSERT.getName() + " {}", safetyEquipmentRequest.toString());
                        assetSafetyEquipmentDao.insert(safetyEquipment);
                    }
                    AssetNetworkEquipmentRequest networkEquipmentRequest = request.getNetworkEquipment();
                    if (networkEquipmentRequest != null) {
                        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipmentRequest,
                            AssetNetworkEquipment.class);
                        assetNetworkEquipment.setAssetId(aid);
                        assetNetworkEquipment.setGmtCreate(System.currentTimeMillis());
                        assetNetworkEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        LogHandle.log(networkEquipmentRequest, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName(), AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_NETWORK_DETAIL_INSERT.getName() + " {}", networkEquipmentRequest.toString());
                        assetNetworkEquipmentDao.insert(assetNetworkEquipment);
                    }
                    AssetStorageMediumRequest assetStorageMedium = request.getAssetStorageMedium();
                    if (assetStorageMedium != null) {
                        AssetStorageMedium medium = BeanConvert.convertBean(assetStorageMedium,
                            AssetStorageMedium.class);
                        medium.setAssetId(aid);
                        medium.setGmtCreate(System.currentTimeMillis());
                        medium.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        LogHandle.log(assetStorageMedium, AssetEventEnum.ASSET_STORAGE_INSERT.getName(), AssetEventEnum.ASSET_STORAGE_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_STORAGE_INSERT.getName() + " {}", assetStorageMedium.toString());
                        assetStorageMediumDao.insert(medium);
                    }
                    // 软件关联表
                    List<AssetSoftwareRelationRequest> computerReques = request.getAssetSoftwareRelationList();
                    if (computerReques != null && computerReques.size() > 0) {
                        for (AssetSoftwareRelationRequest computerReque : computerReques) {
                            AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                            assetSoftwareRelation.setAssetId(aid);
                            assetSoftwareRelation.setSoftwareId(computerReque.getSoftwareId());
                            assetSoftwareRelation.setPort(computerReque.getPort());
                            assetSoftwareRelation.setProtocol(computerReque.getProtocol());
                            assetSoftwareRelation.setSoftwareStatus(3);
                            assetSoftwareRelation.setMemo(computerReque.getMemo());
                            assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            assetSoftwareRelation.setInstallType(computerReque.getInstallType());
                            assetSoftwareRelationDao.insert(assetSoftwareRelation);
                            if (StringUtils.isNotBlank(computerReque.getLicenseSecretKey())) {
                                AssetSoftwareLicense license = new AssetSoftwareLicense();
                                license.setSoftwareId(assetSoftwareRelation.getId());
                                license.setLicenseSecretKey(computerReque.getLicenseSecretKey());
                                license.setGmtCreate(System.currentTimeMillis());
                                license.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            }
                        }
                    }

                    List<AssetNetworkCardRequest> networkCardRequestList = request.getNetworkCard();
                    if (networkCardRequestList != null && networkCardRequestList.size() > 0) {
                        List<AssetNetworkCard> networkCardList = BeanConvert.convert(networkCardRequestList,
                            AssetNetworkCard.class);
                        for (AssetNetworkCard assetNetworkCard : networkCardList) {
                            assetNetworkCard.setAssetId(aid);
                            assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                            assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetNetworkCard, AssetEventEnum.ASSET_NETWORK_INSERT.getName(), AssetEventEnum.ASSET_NETWORK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum .ASSET_NETWORK_INSERT.getName() + " {}", assetNetworkCard.toString());
                            assetNetworkCardDao.insert(assetNetworkCard);

                        }
                    }
                    List<AssetMainboradRequest> mainboradRequestList = request.getMainboard();
                    if (mainboradRequestList != null && mainboradRequestList.size() > 0) {
                        List<AssetMainborad> mainboradList = BeanConvert.convert(mainboradRequestList,
                            AssetMainborad.class);
                        for (AssetMainborad assetMainborad : mainboradList) {
                            assetMainborad.setAssetId(aid);
                            assetMainborad.setGmtCreate(System.currentTimeMillis());
                            assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetMainborad, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(), AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum .ASSET_MAINBORAD_INSERT.getName() + " {}", assetMainborad.toString());
                            assetMainboradDao.insert(assetMainborad);
                        }
                    }
                    List<AssetMemoryRequest> memoryRequestList = request.getMemory();
                    if (memoryRequestList != null && memoryRequestList.size() > 0) {
                        List<AssetMemory> memoryList = BeanConvert.convert(memoryRequestList, AssetMemory.class);
                        for (AssetMemory assetMemory : memoryList) {
                            assetMemory.setAssetId(aid);
                            assetMemory.setGmtCreate(System.currentTimeMillis());
                            assetMemory.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetMemory, AssetEventEnum.ASSET_MEMORY_INSERT.getName(), AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum .ASSET_MEMORY_INSERT.getName() + " {}", assetMemory.toString());
                            assetMemoryDao.insert(assetMemory);
                        }
                    }
                    List<AssetCpuRequest> cpuRequestList = request.getCpu();
                    if (cpuRequestList != null && cpuRequestList.size() > 0) {
                        List<AssetCpu> assetCpuList = BeanConvert.convert(cpuRequestList, AssetCpu.class);
                        for (AssetCpu assetCpu : assetCpuList) {
                            assetCpu.setAssetId(aid);
                            assetCpu.setGmtCreate(System.currentTimeMillis());
                            assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetCpu, AssetEventEnum.ASSET_CPU_INSERT.getName(), AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum .ASSET_CPU_INSERT.getName() + " {}", assetCpu.toString());
                            assetCpuDao.insert(assetCpu);

                        }
                    }
                    List<AssetHardDiskRequest> hardDisk = request.getHardDisk();
                    if (hardDisk != null && hardDisk.size() > 0) {
                        List<AssetHardDisk> hardDisks = BeanConvert.convert(hardDisk, AssetHardDisk.class);
                        for (AssetHardDisk assetHardDisk : hardDisks) {
                            assetHardDisk.setAssetId(aid);
                            assetHardDisk.setGmtCreate(System.currentTimeMillis());
                            assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                            LogHandle.log(assetHardDisk, AssetEventEnum.ASSET_DISK_INSERT.getName(), AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                            LogUtils.info(logger, AssetEventEnum .ASSET_DISK_INSERT.getName() + " {}", assetHardDisk.toString());
                            assetHardDiskDao.insert(assetHardDisk);
                        }
                    }
                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(asset.getId());
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    assetOperationRecord.setContent("登记硬件资产");
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return aid;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        //启动流程
        //ActionResponse actionResponse = activityClient.manualStartProcess (activityRequest);

        if (num != null && num > 0) {
//         启动流程
        ManualStartActivityRequest activityRequest = request.getActivityRequest ();
        activityRequest.setBusinessId (String.valueOf (num));
        activityRequest.setProcessDefinitionKey (AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode ());
            ActionResponse actionResponse = activityClient.manualStartProcess(activityRequest);
        }


        return num;
    }

    @Override
    public Integer updateAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
        asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
        asset.setGmtCreate(System.currentTimeMillis());
        return assetDao.update(asset);
    }

    @Override
    public List<AssetResponse> findListAsset(AssetQuery query) throws Exception {
        List<Asset> asset = assetDao.findListAsset(query);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        return objects;
    }

    public Integer findCountAsset(AssetQuery query) throws Exception {
        return assetDao.findCount(query);
    }

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
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
        List<Map<String, Long>> list = assetDao.countManufacturer(null);
        if (CollectionUtils.isNotEmpty(list)) {
            Map result = new HashMap();
            if (list.size() > maxNum) {
                list.sort(new Comparator<Map<String, Long>>() {
                    @Override
                    public int compare(Map<String, Long> o1, Map<String, Long> o2) {
                        return (int) (o2.get("value") - o1.get("value"));
                    }
                });
            }
            int i = 0;
            long sum = 0;
            for (Map map : list) {
                if (i < maxNum) {
                    result.put(map.get("key"), map.get("value"));
                    i++;
                } else {
                    sum = sum + (Long) map.get("value");
                }
            }
            result.put("其他", sum);
            AssetCountResponse assetCountResponse = new AssetCountResponse();
            assetCountResponse.setMap(result);
            return assetCountResponse;
        }
        return null;
    }

    @Override
    public AssetCountResponse countStatus() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetDao.countStatus(areaIds);
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, Long> result = new HashMap();
            for (Map map : list) {
                AssetStatusEnum assetStatusEnum = AssetStatusEnum.getAssetByCode((Integer) map.get("key"));
                result.put(assetStatusEnum == null ? "" : assetStatusEnum.getMsg(), (Long) map.get("value"));
            }
            AssetCountResponse assetCountResponse = new AssetCountResponse();
            assetCountResponse.setMap(result);
            return assetCountResponse;
        }
        return null;
    }

    @Override
    public AssetCountResponse countCategory() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        HashMap<String, Object> map = new HashMap();
        map.put("name", "硬件");
        map.put("parentId", 0);
        // 查询第一级分类id
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getByWhere(map);
        if (CollectionUtils.isNotEmpty(categoryModelList)) {
            Integer id = categoryModelList.get(0).getId();
            map.clear();
            map.put("parentId", id);
            // 查询第二级分类id
            List<AssetCategoryModel> categoryModelList1 = assetCategoryModelDao.getByWhere(map);
            HashMap<String, Long> result = new HashMap<>();
            for (AssetCategoryModel a : categoryModelList1) {
                // 查询第二级每个分类下所有的分类id，并添加至list集合
                List<AssetCategoryModel> search = recursionSearch(a.getId());
                Long sum = 0L;
                List<Integer> list = new ArrayList<>();
                for (AssetCategoryModel b : search) {
                    list.add(b.getId());
                }
                AssetQuery assetQuery = new AssetQuery();
                assetQuery.setCategoryModels(ArrayTypeUtil.ObjectArrayToIntegerArray(list.toArray()));
                assetQuery.setAreaIds(ArrayTypeUtil.ObjectArrayToIntegerArray(areaIds.toArray()));
                List<Integer> status = new ArrayList<>();
                for (int i = 1; i < 8; i++) {
                    status.add(i);
                }
                assetQuery.setAssetStatusList(status);
                result.put(a.getName(), (long) assetDao.findCountByCategoryModel(assetQuery));
            }
            AssetCountResponse assetCountResponse = new AssetCountResponse();
            assetCountResponse.setMap(result);
            return assetCountResponse;
        }
        return null;
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

    /**
     * 递归查询出品类和其子品类
     *
     * @param id 查询的品类id
     */
    private List<AssetCategoryModel> recursionSearch(Integer id) throws Exception {
        List<AssetCategoryModel> list = assetCategoryModelDao.getAll();
        List<AssetCategoryModel> result = new ArrayList();
        for (AssetCategoryModel AssetCategoryModel : list) {
            if (Objects.equals(AssetCategoryModel.getId(), id)) {
                result.add(AssetCategoryModel);
            }
        }
        recursion(result, list, id);
        return result;
    }

    /**
     * 递归查询出所有的品类和其子品类
     *
     * @param result 查询的结果集
     * @param list 查询的数据集
     * @param id 递归的参数
     */
    private void recursion(List<AssetCategoryModel> result, List<AssetCategoryModel> list, Integer id) {
        for (AssetCategoryModel AssetCategoryModel : list) {
            if (Objects.equals(AssetCategoryModel.getParentId(), id)) {
                result.add(AssetCategoryModel);
                recursion(result, list, AssetCategoryModel.getId());
            }
        }
    }

    @Override
    public List<AssetResponse> queryAssetByIds(Integer[] ids) {
        List<Asset> asset = assetDao.queryAssetByIds(ids);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        return objects;
    }

    @Override
    public AssetOuterResponse getByAssetId(String id) throws Exception {
        BusinessExceptionUtils.isNull(id, "资产ID不能为空");
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        HashMap<String, Object> param = new HashMap();
        // 资产信息
        AssetQuery assetQuery = new AssetQuery();
        assetQuery.setIds(new String[] { id });
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
        assetOuterResponse.setAssetCpu(BeanConvert.convert(assetCpuDao.getByWhere(param), AssetCpuResponse.class));
        // 网卡
        assetOuterResponse.setAssetNetworkCard(BeanConvert.convert(assetNetworkCardDao.getByWhere(param),
            AssetNetworkCardResponse.class));
        // 硬盘
        assetOuterResponse.setAssetHardDisk(BeanConvert.convert(assetHardDiskDao.getByWhere(param),
            AssetHardDiskResponse.class));
        // 主板
        assetOuterResponse.setAssetMainborad(BeanConvert.convert(assetMainboradDao.getByWhere(param),
            AssetMainboradResponse.class));
        // 内存
        assetOuterResponse.setAssetMemory(BeanConvert.convert(assetMemoryDao.getByWhere(param),
            AssetMemoryResponse.class));
        // 网络设备
        List<AssetNetworkEquipment> assetNetworkEquipmentList = assetNetworkEquipmentDao.getByWhere(param);
        if (assetNetworkEquipmentList != null && !assetNetworkEquipmentList.isEmpty()) {
            assetOuterResponse.setAssetNetworkEquipment(BeanConvert.convertBean(assetNetworkEquipmentList.get(0),
                AssetNetworkEquipmentResponse.class));
        }
        // 安全设备
        List<AssetSafetyEquipment> assetSafetyEquipmentList = assetSafetyEquipmentDao.getByWhere(param);
        if (assetSafetyEquipmentList != null && !assetSafetyEquipmentList.isEmpty()) {
            assetOuterResponse.setAssetSafetyEquipment(BeanConvert.convertBean(assetSafetyEquipmentList.get(0),
                AssetSafetyEquipmentResponse.class));
        }
        // 存储介质
        List<AssetStorageMedium> assetStorageMediumList = assetStorageMediumDao.getByWhere(param);
        if (assetStorageMediumList != null && !assetStorageMediumList.isEmpty()) {
            assetOuterResponse.setAssetStorageMedium(BeanConvert.convertBean(assetStorageMediumList.get(0),
                AssetStorageMediumResponse.class));
        }
        // 软件列表
        List<AssetSoftware> assetSoftwareList = assetSoftwareRelationDao.getSoftByAssetId(DataTypeUtils
            .stringToInteger(id));
        assetOuterResponse.setAssetSoftware(BeanConvert.convert(assetSoftwareList, AssetSoftwareResponse.class));

        // 资产软件关系列表
        List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao.getReleationByAssetId(asset
            .getId());
        assetOuterResponse.setAssetSoftwareRelationList(BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelationResponse.class));
        return assetOuterResponse;
    }

    @Override
    public Integer changeAsset(AssetOuterRequest assetOuterRequest, Integer configBaselineUserId) throws Exception {
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset(), "资产信息不能为空");
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");
        Asset asset = BeanConvert.convertBean(assetOuterRequest.getAsset(), Asset.class);
        Integer assetCount = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 更改资产状态为待配置
                    asset.setStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    StringBuffer stringBuffer = new StringBuffer();
                    List<AssetGroupRequest> assetGroups = assetOuterRequest.getAsset().getAssetGroup();
                    List<AssetGroupRelation> assetGroupRelations = Lists.newArrayList();
                    for (AssetGroupRequest assetGroupRequest : assetGroups) {
                        stringBuffer.append(assetGroupRequest.getName()).append(",");
                        AssetGroupRelation assetGroupRelation = new AssetGroupRelation();
                        assetGroupRelation.setAssetGroupId(DataTypeUtils.stringToInteger(assetGroupRequest.getId()));
                        assetGroupRelation.setAssetId(asset.getId());
                        // assetGroupRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        assetGroupRelation.setGmtCreate(System.currentTimeMillis());
                    }
                    assetGroupRelationDao.deleteByAssetId(asset.getId());
                    assetGroupRelationDao.insertBatch(assetGroupRelations);
                    asset.setAssetGroup(stringBuffer.toString());

                    // 1. 更新资产主表
                    LogHandle.log(asset, AssetEventEnum.ASSET_MODIFY.getName(), AssetEventEnum.ASSET_MODIFY.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum .ASSET_MODIFY.getName() + " {}", asset.toString());
                    int count = assetDao.update(asset);

                    // 2. 更新cpu信息
                    List<AssetCpuRequest> assetCpuRequestList = assetOuterRequest.getCpu();
                    if (assetCpuRequestList != null && !assetCpuRequestList.isEmpty()) {
                        List<AssetCpu> assetCpuList = BeanConvert.convert(assetCpuRequestList, AssetCpu.class);
                        for (AssetCpu assetCpu : assetCpuList) {
                            // 设置资产id，可能是新增的
                            assetCpu.setAssetId(asset.getId());
                            // assetCpu.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetCpu.setGmtModified(System.currentTimeMillis());
                        }
                        // 先删除再新增
                        LogHandle.log(asset.getId(), AssetEventEnum.ASSET_CPU_DELETE.getName(), AssetEventEnum.ASSET_CPU_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_CPU_DELETE.getName() + " {}", asset.getId().toString());
                        assetCpuDao.deleteByAssetId(asset.getId());

                        LogHandle.log(assetCpuList, AssetEventEnum.ASSET_CPU_INSERT.getName(), AssetEventEnum.ASSET_CPU_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_CPU_INSERT.getName() + " {}", assetCpuList.toString());
                        assetCpuDao.insertBatch(assetCpuList);
                    }
                    // 3. 更新网卡信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_NETWORK_DELETE.getName(), AssetEventEnum.ASSET_NETWORK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum .ASSET_NETWORK_DELETE.getName() + " {}", asset.getId().toString());
                    assetNetworkCardDao.deleteByAssetId(asset.getId());
                    List<AssetNetworkCardRequest> assetNetworkCardRequestList = assetOuterRequest.getNetworkCard();
                    if (assetNetworkCardRequestList != null && !assetNetworkCardRequestList.isEmpty()) {
                        List<AssetNetworkCard> assetNetworkCardList = BeanConvert.convert(assetNetworkCardRequestList,
                            AssetNetworkCard.class);
                        for (AssetNetworkCard assetNetworkCard : assetNetworkCardList) {
                            // 设置资产id，可能是新增的
                            assetNetworkCard.setAssetId(asset.getId());
                            // assetNetworkCard.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetNetworkCard.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetNetworkCardList, AssetEventEnum.ASSET_NETWORK_INSERT.getName(), AssetEventEnum.ASSET_NETWORK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_NETWORK_INSERT.getName() + " {}", assetNetworkCardList.toString());
                        assetNetworkCardDao.insertBatch(assetNetworkCardList);
                    }
                    // 4. 更新主板信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_MAINBORAD_DELETE.getName(), AssetEventEnum.ASSET_MAINBORAD_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum .ASSET_MAINBORAD_DELETE.getName() + " {}", asset.getId().toString());
                    assetMainboradDao.deleteByAssetId(asset.getId());
                    List<AssetMainboradRequest> assetMainboradRequest = assetOuterRequest.getMainboard();
                    if (assetNetworkCardRequestList != null && !assetMainboradRequest.isEmpty()) {
                        List<AssetMainborad> assetMainborad = BeanConvert.convert(assetMainboradRequest,
                            AssetMainborad.class);
                        for (AssetMainborad mainborad : assetMainborad) {
                            mainborad.setAssetId(asset.getId());
                            // mainborad.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            mainborad.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetMainborad, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(), AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_MAINBORAD_INSERT.getName() + " {}", assetMainborad.toString());
                        assetMainboradDao.insertBatch(assetMainborad);
                    }
                    // 5. 更新内存信息
                    List<AssetMemoryRequest> assetMemoryRequestList = assetOuterRequest.getMemory();
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_MEMORY_DELETE.getName(), AssetEventEnum.ASSET_MEMORY_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum .ASSET_MEMORY_DELETE.getName() + " {}", asset.getId().toString());
                    assetMemoryDao.deleteByAssetId(asset.getId());
                    if (assetMemoryRequestList != null && !assetMemoryRequestList.isEmpty()) {
                        List<AssetMemory> assetMemoryList = BeanConvert.convert(assetMemoryRequestList,
                            AssetMemory.class);
                        for (AssetMemory assetMemory : assetMemoryList) {
                            // 设置资产id，可能是新增的
                            assetMemory.setAssetId(asset.getId());
                            // asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetMemory.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetMemoryList, AssetEventEnum.ASSET_MEMORY_INSERT.getName(), AssetEventEnum.ASSET_MEMORY_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_MEMORY_INSERT.getName() + " {}", assetMemoryList.toString());
                        assetMemoryDao.insertBatch(assetMemoryList);
                    }
                    // 6. 更新硬盘信息
                    // 先删除再新增
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_DISK_DELETE.getName(), AssetEventEnum.ASSET_DISK_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum .ASSET_DISK_DELETE.getName() + " {}", asset.getId().toString());
                    assetHardDiskDao.deleteByAssetId(asset.getId());
                    List<AssetHardDiskRequest> assetHardDiskRequestList = assetOuterRequest.getHardDisk();
                    if (assetHardDiskRequestList != null && !assetHardDiskRequestList.isEmpty()) {
                        List<AssetHardDisk> assetHardDiskList = BeanConvert.convert(assetHardDiskRequestList,
                            AssetHardDisk.class);
                        for (AssetHardDisk assetHardDisk : assetHardDiskList) {
                            // 设置资产id，可能是新增的
                            assetHardDisk.setAssetId(asset.getId());
                            assetHardDisk.setGmtCreate(System.currentTimeMillis());
                            // assetHardDisk.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetHardDisk.setGmtModified(System.currentTimeMillis());
                        }
                        LogHandle.log(assetHardDiskList, AssetEventEnum.ASSET_DISK_INSERT.getName(), AssetEventEnum.ASSET_DISK_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_DISK_INSERT.getName() + " {}", assetHardDiskList.toString());
                        assetHardDiskDao.insertBacth(assetHardDiskList);
                    }
                    // 7. 更新网络设备信息
                    LogHandle.log(asset.getId(), AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getName(), AssetEventEnum.ASSET_NETWORK_DETAIL_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
                    LogUtils.info(logger, AssetEventEnum .ASSET_NETWORK_DETAIL_DELETE.getName() + " {}", asset.getId().toString());
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();
                    if (networkEquipment != null && StringUtils.isNotBlank(networkEquipment.getId())) {
                        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipment,
                            AssetNetworkEquipment.class);
                        assetNetworkEquipment.setAssetId(asset.getId());
                        // assetNetworkEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetNetworkEquipment.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetNetworkEquipment, AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getName(), AssetEventEnum.ASSET_NETWORK_DETAIL_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_NETWORK_DETAIL_INSERT.getName() + " {}", assetNetworkEquipment.toString());
                        assetNetworkEquipmentDao.update(assetNetworkEquipment);
                    }
                    // 8. 更新安全设备信息
                    AssetSafetyEquipmentRequest safetyEquipment = assetOuterRequest.getSafetyEquipment();
                    if (safetyEquipment != null && StringUtils.isNotBlank(safetyEquipment.getId())) {
                        AssetSafetyEquipment assetSafetyEquipment = BeanConvert.convertBean(safetyEquipment,
                            AssetSafetyEquipment.class);
                        assetSafetyEquipment.setAssetId(asset.getId());
                        // assetSafetyEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetSafetyEquipment.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetSafetyEquipment, AssetEventEnum.ASSET_SAFE_DETAIL_UPDATE.getName(), AssetEventEnum.ASSET_SAFE_DETAIL_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_SAFE_DETAIL_UPDATE.getName() + " {}", assetSafetyEquipment.toString());
                        assetSafetyEquipmentDao.update(assetSafetyEquipment);
                    }
                    // 9. 更新存储介质信息
                    AssetStorageMediumRequest storageMedium = assetOuterRequest.getAssetStorageMedium();
                    if (storageMedium != null && StringUtils.isNotBlank(storageMedium.getId())) {
                        AssetStorageMedium assetStorageMedium = BeanConvert.convertBean(storageMedium,
                            AssetStorageMedium.class);
                        assetStorageMedium.setAssetId(asset.getId());
                        // assetStorageMedium.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetStorageMedium.setGmtModified(System.currentTimeMillis());
                        LogHandle.log(assetStorageMedium, AssetEventEnum.ASSET_STORAGE_UPDATE.getName(), AssetEventEnum.ASSET_STORAGE_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
                        LogUtils.info(logger, AssetEventEnum .ASSET_STORAGE_UPDATE.getName() + " {}", assetStorageMedium.toString());
                        assetStorageMediumDao.update(assetStorageMedium);
                    }
                    // 10. 更新资产软件关系信息
                    // 删除已有资产软件关系
                    assetSoftwareRelationDao.deleteByAssetId(asset.getId());
                    // 删除软件许可
                    assetSoftwareLicenseDao.deleteByAssetId(asset.getId());
                    List<AssetSoftwareRelation> assetSoftwareRelationList = BeanConvert.convert(
                        assetOuterRequest.getAssetSoftwareRelationList(), AssetSoftwareRelation.class);
                    assetSoftwareRelationList.stream().forEach(relation -> {
                        relation.setAssetId(asset.getId());
                        relation.setGmtCreate(System.currentTimeMillis());
                        // relation.setSoftwareStatus();
                        // relation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        try {
                            // 插入资产软件关系
                            assetSoftwareRelationDao.insert(relation);
                            AssetSoftwareLicense assetSoftwareLicense = new AssetSoftwareLicense();
                            assetSoftwareLicense.setLicenseSecretKey(relation.getLicenseSecretKey());
                            assetSoftwareLicense.setSoftwareId(relation.getId());
                            // 插入资产软件许可
                            assetSoftwareLicenseDao.insert(assetSoftwareLicense);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    // 记录资产操作流程
                    AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
                    assetOperationRecord.setTargetObjectId(asset.getId());
                    assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
                    assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    assetOperationRecord.setContent("资产变更");
                    assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
                    assetOperationRecord.setGmtCreate(System.currentTimeMillis());
                    assetOperationRecordDao.insert(assetOperationRecord);
                    return count;
                } catch (Exception e) {
                    logger.error("修改资产失败", e);
                }
                return 0;
            }
        });
        // TODO 下发智甲

        // TODO 通知工作流
        Map<String, Object> formData = new HashMap();
        formData.put("configBaselineUserId", configBaselineUserId);
        formData.put("discard", 0);
        ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
        manualStartActivityRequest.setBusinessId(asset.getId().toString());
        manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
        // manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId());
        manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_CHANGE.getCode());
        activityClient.manualStartProcess(manualStartActivityRequest);
        return assetCount;
    }

    /**
     * 1-计算设备 2-网络设备 3-安全设备 4-存储设备 5-其他设备
     * @param type 导出模板的类型
     */
    @Override
    public void exportTemplate(int type) throws Exception {
        switch (type) {
            case 1:
                exportToClient(ComputeDeviceEntity.class, "计算设备信息模板.xlsx", "计算设备");
                break;
            case 2:
                exportToClient(NetworkDeviceEntity.class, "网络设备信息模板.xlsx", "网络设备");
                break;
            case 3:
                exportToClient(SafetyEquipmentEntiy.class, "安全设备信息模板.xlsx", "安全设备");
                break;
            case 4:
                exportToClient(StorageDeviceEntity.class, "存储设备信息模板.xlsx", "存储设备");
                break;
            case 5:
                exportToClient(OtherDeviceEntity.class, "其他设备信息模板.xlsx", "其他设备");
                break;
        }
    }

    @Override
    public void exportData(AssetQuery assetQuery, HttpServletResponse response) throws Exception {
        exportData(ComputeDeviceEntity.class, "资产信息表.xlsx", assetQuery, response);
    }

    @Override
    public String importPc(MultipartFile file, String areaId) throws Exception {
        ImportResult<ComputeDeviceEntity> result = ExcelUtils.importExcelFromClient(ComputeDeviceEntity.class, file, 0,
            0);
        int success=0;
//        int repeat=0;
        int error=0;
        StringBuilder builder = new StringBuilder ();
        List<ComputeDeviceEntity> dataList = result.getDataList();
        for (ComputeDeviceEntity entity : dataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append ("序号").append (entity.getOrderNumber ()).append ("资产名称为空");
                continue;
            }
            Asset asset = new Asset();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId (areaId);
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setResponsibleUserId (LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
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
            asset.setMemo(entity.getDescription());
            asset.setOperationSystem(entity.getOperationSystem());
            asset.setContactTel(entity.getTelephone());
            asset.setEmail(entity.getEmail());
            assetDao.insert(asset);
            Integer id = asset.getId();
            if (StringUtils.isNotBlank(entity.getMemoryBrand())) {
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
                assetMemory.setIsHeatsink(entity.getHeatsink());
                if (entity.getMemoryNum() != null) {
                    for (int i = 0; i < entity.getMemoryNum(); i++) {
                        assetMemoryDao.insert(assetMemory);

                    }
                }

            }

            if (StringUtils.isNotBlank(entity.getHardDiskBrand())) {
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

                if (entity.getHardDiskNum() != null) {

                    for (int i = 0; i < entity.getHardDiskNum(); i++) {
                        assetHardDiskDao.insert(assetHardDisk);

                    }
                }

            }

            if (StringUtils.isNotBlank(entity.getMainboradBrand())) {
                AssetMainborad assetMainborad = new AssetMainborad();
                assetMainborad.setAssetId(id);
                assetMainborad.setGmtCreate(System.currentTimeMillis());
                assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetMainborad.setSerial(entity.getMainboradSerial());
                assetMainborad.setBrand(entity.getMainboradBrand());
                assetMainborad.setModel(entity.getMainboradModel());
                assetMainborad.setBiosVersion(entity.getMainboradBiosVersion());
                assetMainborad.setBiosDate(entity.getMainboradBiosDate());
                if (entity.getMainboradNum() != null) {
                    for (int i = 0; i < entity.getMainboradNum(); i++) {
                        assetMainboradDao.insert(assetMainborad);

                    }
                }

            }
            if (StringUtils.isNotBlank(entity.getCpuBrand())) {
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
                if (entity.getCpuNum() != null) {

                    for (int i = 0; i < entity.getCpuNum(); i++) {
                        assetCpuDao.insert(assetCpu);

                    }
                }
            }
            if (StringUtils.isNotBlank(entity.getNetworkBrand())) {
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
                if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(mac) && entity.getNetworkNum() > 0) {

                    String[] ips = ip.split(",");
                    String[] macs = mac.split(",");
                    for (int i = 0; i < entity.getNetworkNum(); i++) {

                        assetNetworkCard.setMacAddress(macs[i]);
                        assetNetworkCard.setIpAddress(ips[i]);
                        assetNetworkCardDao.insert(assetNetworkCard);
                    }
                }

            }

            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecordDao.insert(assetOperationRecord);

            //  流程
            // TODO: 2019/1/22 根据区域ID 查询全部的配置人员

            Map<String, Object> formData = new HashMap();
//            formData.put("configBaselineUserId", configBaselineUserId);
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId ().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee (LoginUserUtil.getLoginUser ().getName ());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess (manualStartActivityRequest);
            success++;
        }

        String re = "导入成功" + success + "条";
//        re += repeat > 0 ? ", " + repeat + "条编号重复"  : "";
            re += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder ();
        if (error>0){
            stringBuilder.append (re).append ("其中").append (builder);
        }

        return stringBuilder.toString ();
    }

    @Override
    public String importNet(MultipartFile file, String areaId) throws Exception {
        ImportResult<NetworkDeviceEntity> importResult = ExcelUtils.importExcelFromClient(NetworkDeviceEntity.class,
            file, 0, 0);
        int success=0;
//        int repeat=0;
        int error=0;
        StringBuilder builder = new StringBuilder ();
        List<NetworkDeviceEntity> entities = importResult.getDataList();
        for (NetworkDeviceEntity networkDeviceEntity : entities) {
            if (StringUtils.isBlank(networkDeviceEntity.getName())) {
                error++;
                builder.append ("序号").append (networkDeviceEntity.getOrderNumber ()).append ("资产名称为空");
                continue;
            }
            Asset asset = new Asset();
            AssetNetworkEquipment assetNetworkEquipment = new AssetNetworkEquipment();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId (areaId);
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setResponsibleUserId (LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
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
            assetDao.insert(asset);
            assetNetworkEquipment.setAssetId(asset.getId());
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
            assetNetworkEquipment.setCpu(networkDeviceEntity.getCpuSize());
            assetNetworkEquipment.setDramSize(networkDeviceEntity.getDramSize());
            assetNetworkEquipment.setFlashSize(networkDeviceEntity.getFlashSize());
            assetNetworkEquipment.setRegister(networkDeviceEntity.getRegister());
            assetNetworkEquipment.setIsWireless(networkDeviceEntity.getIsWireless());
            assetNetworkEquipmentDao.insert(assetNetworkEquipment);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecordDao.insert(assetOperationRecord);
            //  流程
            // TODO: 2019/1/22 根据区域ID 查询全部的配置人员

            Map<String, Object> formData = new HashMap();
//            formData.put("configBaselineUserId", configBaselineUserId);
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId ().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee (LoginUserUtil.getLoginUser ().getName ());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess (manualStartActivityRequest);

            success++;
        }

        String re = "导入成功" + success + "条";
//        re += repeat > 0 ? ", " + repeat + "条编号重复"  : "";
        re += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder ();
        if (error>0){
            stringBuilder.append (re).append ("其中").append (builder);
        }

        return stringBuilder.toString ();
    }

    @Override
    public String importSecurity(MultipartFile file, String areaId) throws Exception {
        ImportResult<SafetyEquipmentEntiy> result = ExcelUtils
            .importExcelFromClient(SafetyEquipmentEntiy.class, file, 0, 0);
        int success=0;
//        int repeat=0;
        int error=0;
        StringBuilder builder = new StringBuilder ();
        List<SafetyEquipmentEntiy> resultDataList = result.getDataList();
        for (SafetyEquipmentEntiy entity : resultDataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append ("序号").append (entity.getOrderNumber ()).append ("资产名称为空");
                continue;
            }
            Asset asset = new Asset();
            AssetSafetyEquipment assetSafetyEquipment = new AssetSafetyEquipment();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId (areaId);
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setResponsibleUserId (LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
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
            assetDao.insert(asset);
            assetSafetyEquipment.setAssetId(asset.getId());
            assetSafetyEquipment.setGmtCreate(System.currentTimeMillis());
            assetSafetyEquipment.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetSafetyEquipment.setSoftwareVersion(entity.getSoftwareVersion());
            assetSafetyEquipment.setIp(entity.getIp());
            assetSafetyEquipment.setMemo(entity.getMemo());
            assetSafetyEquipmentDao.insert(assetSafetyEquipment);
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecordDao.insert(assetOperationRecord);
            //  流程
            // TODO: 2019/1/22 根据区域ID 查询全部的配置人员

            Map<String, Object> formData = new HashMap();
//            formData.put("configBaselineUserId", configBaselineUserId);
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId ().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee (LoginUserUtil.getLoginUser ().getName ());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess (manualStartActivityRequest);
            success++;
        }

        String re = "导入成功" + success + "条";
//        re += repeat > 0 ? ", " + repeat + "条编号重复"  : "";
        re += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder ();
        if (error>0){
            stringBuilder.append (re).append ("其中").append (builder);
        }

        return stringBuilder.toString ();

    }

    @Override
    public String importStory(MultipartFile file, String areaId) throws Exception {
        ImportResult<StorageDeviceEntity> re = ExcelUtils.importExcelFromClient(StorageDeviceEntity.class, file, 0, 0);
        List<StorageDeviceEntity> resultDataList = re.getDataList();
        int success=0;
//        int repeat=0;
        int error=0;
        StringBuilder builder = new StringBuilder ();
        for (StorageDeviceEntity entity : resultDataList) {
            Asset asset = new Asset();
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append ("序号").append (entity.getOrderNumber ()).append ("资产名称为空");
                continue;
            }
            AssetStorageMedium assetSafetyEquipment = new AssetStorageMedium();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId (areaId);
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setResponsibleUserId (LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
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
            assetDao.insert(asset);
            assetSafetyEquipment.setAssetId(asset.getId());
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
            assetOperationRecord.setTargetObjectId(asset.getId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            //  流程
            // TODO: 2019/1/22 根据区域ID 查询全部的配置人员

            Map<String, Object> formData = new HashMap();
//            formData.put("configBaselineUserId", configBaselineUserId);
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId ().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee (LoginUserUtil.getLoginUser ().getName ());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess (manualStartActivityRequest);
            success++;
        }

        String res = "导入成功" + success + "条";
//        res += repeat > 0 ? ", " + repeat + "条编号重复"  : "";
        res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder ();
        if (error>0){
            stringBuilder.append (re).append ("其中").append (builder);
        }

        return stringBuilder.toString ();
    }

    @Override
    public String importOhters(MultipartFile file, String areaId) throws Exception {
        ImportResult<OtherDeviceEntity> re = ExcelUtils.importExcelFromClient(OtherDeviceEntity.class, file, 0, 0);
        List<OtherDeviceEntity> resultDataList = re.getDataList();
        int success=0;
//        int repeat=0;
        int error=0;
        StringBuilder builder = new StringBuilder ();
        for (OtherDeviceEntity entity : resultDataList) {
            if (StringUtils.isBlank(entity.getName())) {
                error++;
                builder.append ("序号").append (entity.getOrderNumber ()).append ("资产名称为空");
                continue;
            }
            Asset asset = new Asset();
            asset.setGmtCreate(System.currentTimeMillis());
            asset.setAreaId (areaId);
            asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
            asset.setResponsibleUserId (LoginUserUtil.getLoginUser().getId());
            asset.setAssetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            asset.setAssetSource(2);
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
            assetDao.insert(asset);
            // // TODO: 2019/1/17 流程
            // 记录资产操作流程
            AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
            assetOperationRecord.setTargetObjectId(asset.getId());
            assetOperationRecord.setTargetType(AssetOperationTableEnum.ASSET.getCode());
            assetOperationRecord.setTargetStatus(AssetStatusEnum.WAIT_SETTING.getCode());
            assetOperationRecord.setContent("登记硬件资产");
            assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
            assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            assetOperationRecord.setGmtCreate(System.currentTimeMillis());
            assetOperationRecordDao.insert(assetOperationRecord);
            //  流程
            // TODO: 2019/1/22 根据区域ID 查询全部的配置人员

            Map<String, Object> formData = new HashMap();
//            formData.put("configBaselineUserId", configBaselineUserId);
            formData.put("discard", 0);
            ManualStartActivityRequest manualStartActivityRequest = new ManualStartActivityRequest();
            manualStartActivityRequest.setBusinessId(asset.getId ().toString());
            manualStartActivityRequest.setFormData(JSONObject.toJSONString(formData));
            manualStartActivityRequest.setAssignee (LoginUserUtil.getLoginUser ().getName ());
            manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_ADMITTANCE.getCode());
            activityClient.manualStartProcess (manualStartActivityRequest);
            success++;
        }

        String res = "导入成功" + success + "条";
//        res += repeat > 0 ? ", " + repeat + "条编号重复"  : "";
        res += error > 0 ? ", " + error + "条数据导入失败" : "";
        StringBuilder stringBuilder = new StringBuilder ();
        if (error>0){
            stringBuilder.append (re).append ("其中").append (builder);
        }

        return stringBuilder.toString ();
    }

    private void exportToClient(Class clazz, String fileName, String title) {
        ExcelUtils.exportTemplet(clazz, fileName, title);
    }

    private void exportData(Class clazz, String fileName, AssetQuery assetQuery, HttpServletResponse response)
                                                                                                              throws Exception {
        assetQuery.setAreaIds(ArrayTypeUtil.ObjectArrayToIntegerArray(LoginUserUtil.getLoginUser()
            .getAreaIdsOfCurrentUser().toArray()));
        List<AssetResponse> list = this.findListAsset(assetQuery);
        List<AssetEntity> assetEntities = assetEntityConvert.convert(list, AssetEntity.class);
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setSheetName("资产信息表");
        downloadVO.setDownloadList(assetEntities);
        excelDownloadUtil.excelDownload(response, "资产信息表", downloadVO);
    }

}

@Component
class AssetEntityConvert extends BaseConverter<AssetResponse, AssetEntity> {
    private final Logger logger = LogUtils.get();

    @Override
    protected void convert(AssetResponse asset, AssetEntity assetEntity) {
        if (Objects.nonNull(asset.getInnet())) {
            assetEntity.setIsInnet(asset.getInnet() ? "已入网" : "未入网");
        }

        if (Objects.nonNull(asset.getAssetStatus())) {
            AssetStatusEnum assetStatusEnum = AssetStatusEnum.getAssetByCode(asset.getAssetStatus());
            assetEntity.setAssetStatus(assetStatusEnum == null ? "" : assetStatusEnum.getMsg());
        }
        assetEntity.setCategoryModel(asset.getCategoryModelName());
        if (Objects.nonNull(asset.getAssetSource())) {
            if (asset.getAssetSource().equals(1)) {
                assetEntity.setAssetSource("人工上报");
            }
            if (asset.getAssetSource().equals(2)) {
                assetEntity.setAssetSource("自动上报");
            }
        }
        super.convert(asset, assetEntity);
    }
}