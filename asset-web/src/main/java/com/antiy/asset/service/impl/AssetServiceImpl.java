package com.antiy.asset.service.impl;

import java.util.*;
import javax.annotation.Resource;
import com.antiy.asset.templet.*;
import com.antiy.asset.util.ExcelUtils;
import com.antiy.biz.util.LoginUserUtil;
import com.antiy.common.utils.LogUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.*;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
    private AssetCategoryModelDao                     assetCategoryModelDao;
    @Resource
    private TransactionTemplate                       transactionTemplate;
    @Resource
    private AssetSoftwareRelationDao                  assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>        requestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse>       responseConverter;
    @Resource
    private BaseConverter<Asset, ComputeDeviceEntity> entityConverter;

    private static final Logger                       LOGGER = LogUtils.get(AssetServiceImpl.class);

    @Override
    public Integer saveAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
        asset.setCreateUser(LoginUserUtil.getLoginUser().getId());
        asset.setGmtCreate(System.currentTimeMillis());
        Integer insert = assetDao.insert(asset);
        return insert;
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

    @Override
    public Integer saveAllAsset(HashMap<String, Object> map) throws Exception {
        return null;
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
    public Map<String, Long> countManufacturer() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetDao.countManufacturer(areaIds);
        if (CollectionUtils.isNotEmpty(list)) {
            Map result = new HashMap();
            for (Map map : list) {
                result.put(map.get("key"), map.get("value"));
            }
            return result;
        }
        return null;
    }

    @Override
    public Map<String, Long> countStatus() throws Exception {
        List<Integer> areaIds = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetDao.countStatus(areaIds);
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, Long> result = new HashMap();
            for (Map map : list) {
                result.put(AssetStatusEnum.getAssetByCode((Integer) map.get("key")) + "", (Long) map.get("value"));
            }
            return result;
        }
        return null;
    }

    @Override
    public Map<String, Long> countCategory() throws Exception {
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
                result.put(a.getName(), (long) assetDao.findCountByCategoryModel(assetQuery));
            }
            return result;
        }
        return null;
    }

    @Override
    @Transactional
    public Integer saveAssetPC(AssetPCRequest assetPCRequest) throws Exception {
        List<AssetCpuRequest> cpuRequestList = assetPCRequest.getCpu();
        BaseConverter<AssetCpuRequest, AssetCpu> baseConverter = new BaseConverter<>();
        List<AssetCpu> cpu = baseConverter.convert(cpuRequestList, AssetCpu.class);
        List<AssetHardDisk> hardDisk = new BaseConverter<AssetHardDiskRequest, AssetHardDisk>().convert(
            assetPCRequest.getHardDisk(), AssetHardDisk.class);
        List<AssetMemory> memory = new BaseConverter<AssetMemoryRequest, AssetMemory>().convert(
            assetPCRequest.getMemory(), AssetMemory.class);
        List<AssetMainborad> mainboard = new BaseConverter<AssetMainboradRequest, AssetMainborad>().convert(
            assetPCRequest.getMainboard(), AssetMainborad.class);
        List<AssetNetworkCard> networkCard = new BaseConverter<AssetNetworkCardRequest, AssetNetworkCard>().convert(
            assetPCRequest.getNetworkCard(), AssetNetworkCard.class);

        Asset asset = requestConverter.convert(assetPCRequest.getAsset(), Asset.class);
        assetDao.insert(asset);
        Integer aid = asset.getId();
        String softwareids = assetPCRequest.getSoftwareids();
        if (StringUtils.isNotBlank(softwareids)) {
            String[] split = softwareids.split(",");
            for (String s : split) {
                AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                assetSoftwareRelation.setAssetId(aid);
                assetSoftwareRelation.setSoftwareId(Integer.parseInt(s));
                assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetSoftwareRelationDao.insert(assetSoftwareRelation);
            }
        }

        if (networkCard != null && networkCard.size() > 0) {
            for (AssetNetworkCard assetNetworkCard : networkCard) {
                assetNetworkCard.setAssetId(aid);
                assetNetworkCard.setGmtCreate(System.currentTimeMillis());
                assetNetworkCardDao.insert(assetNetworkCard);

            }
        }
        if (mainboard != null && mainboard.size() > 0) {
            for (AssetMainborad assetMainborad : mainboard) {
                assetMainborad.setAssetId(aid);
                assetMainborad.setGmtCreate(System.currentTimeMillis());
                assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetMainboradDao.insert(assetMainborad);
            }
        }
        if (memory != null && memory.size() > 0) {
            for (AssetMemory assetMemory : memory) {
                assetMemory.setAssetId(aid);
                assetMemory.setGmtCreate(System.currentTimeMillis());
                assetMemory.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetMemoryDao.insert(assetMemory);
            }
        }
        if (cpu != null && cpu.size() > 0) {
            for (AssetCpu assetCpu : cpu) {
                assetCpu.setAssetId(aid);
                assetCpu.setGmtCreate(System.currentTimeMillis());
                assetCpu.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetCpuDao.insert(assetCpu);

            }
        }

        if (hardDisk != null && hardDisk.size() > 0) {
            for (AssetHardDisk assetHardDisk : hardDisk) {
                assetHardDisk.setAssetId(aid);
                assetHardDisk.setGmtCreate(System.currentTimeMillis());
                assetHardDisk.setCreateUser(LoginUserUtil.getLoginUser().getId());
                assetHardDiskDao.insert(assetHardDisk);
            }
        }
        return aid;
    }

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
        param.put("assetId", asset.getId());
        assetOuterResponse.setAsset(BeanConvert.convertBean(asset, AssetResponse.class));
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
        // 软件
        List<AssetSoftware> assetSoftwareList = assetSoftwareRelationDao.getSoftByAssetId(DataTypeUtils
            .stringToInteger(id));
        assetOuterResponse.setAssetSoftware(BeanConvert.convert(assetSoftwareList, AssetSoftwareResponse.class));
        return assetOuterResponse;
    }

    @Override
    @Transactional
    public Integer changeAsset(AssetOuterRequest assetOuterRequest) throws Exception {
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset(), "资产信息不能为空");
        ParamterExceptionUtils.isNull(assetOuterRequest.getAsset().getId(), "资产ID不能为空");
        Integer assetCount = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    Asset asset = BeanConvert.convertBean(assetOuterRequest.getAsset(), Asset.class);
                    // 更改资产状态为待配置
                    asset.setStatus(AssetStatusEnum.WAIT_SETTING.getCode());
                    // 1. 更新资产主表
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
                        assetCpuDao.deleteByAssetId(asset.getId());
                        assetCpuDao.insertBatch(assetCpuList);
                    }
                    // 3. 更新网卡信息
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
                        // 先删除再新增
                        assetNetworkCardDao.deleteByAssetId(asset.getId());
                        assetNetworkCardDao.insertBatch(assetNetworkCardList);
                    }
                    // 4. 更新主板信息
                    AssetMainboradRequest assetMainboradRequest = assetOuterRequest.getMainboard();
                    if (assetNetworkCardRequestList != null) {
                        AssetMainborad assetMainborad = BeanConvert.convertBean(assetMainboradRequest,
                            AssetMainborad.class);
                        assetMainborad.setAssetId(asset.getId());
                        // assetMainborad.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetMainborad.setGmtModified(System.currentTimeMillis());
                        assetMainboradDao.update(assetMainborad);
                    }
                    // 5. 更新内存信息
                    List<AssetMemoryRequest> assetMemoryRequestList = assetOuterRequest.getMemory();
                    if (assetMemoryRequestList != null && !assetMemoryRequestList.isEmpty()) {
                        List<AssetMemory> assetMemoryList = BeanConvert.convert(assetMemoryRequestList,
                            AssetMemory.class);
                        for (AssetMemory assetMemory : assetMemoryList) {
                            // 设置资产id，可能是新增的
                            assetMemory.setAssetId(asset.getId());
                            // asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
                            assetMemory.setGmtModified(System.currentTimeMillis());
                        }
                        // 先删除再新增
                        assetMemoryDao.deleteByAssetId(asset.getId());
                        assetMemoryDao.insertBatch(assetMemoryList);
                    }
                    // 6. 更新硬盘信息
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
                        // 先删除再新增
                        assetHardDiskDao.deleteByAssetId(asset.getId());
                        assetHardDiskDao.insertBacth(assetHardDiskList);
                    }
                    // 7. 更新网络设备信息
                    AssetNetworkEquipmentRequest networkEquipment = assetOuterRequest.getNetworkEquipment();
                    if (networkEquipment != null && StringUtils.isNotBlank(networkEquipment.getId())) {
                        AssetNetworkEquipment assetNetworkEquipment = BeanConvert.convertBean(networkEquipment,
                            AssetNetworkEquipment.class);
                        assetNetworkEquipment.setAssetId(asset.getId());
                        // assetNetworkEquipment.setModifyUser(LoginUserUtil.getLoginUser().getId());
                        assetNetworkEquipment.setGmtModified(System.currentTimeMillis());
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
                        assetSafetyEquipmentDao.update(assetSafetyEquipment);
                    }
                    // 9. 更新资产软件关系信息
                    assetSoftwareRelationDao.deleteByAssetId(asset.getId());
                    List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
                    Integer[]  assetSoftwareIds = assetOuterRequest.getAssetSoftwareIds();
                    for (int i = 0; i < assetSoftwareIds.length; i++) {
                        AssetSoftwareRelation relation = new AssetSoftwareRelation();
                        relation.setAssetId(asset.getId());
                        relation.setSoftwareId(assetSoftwareIds[i]);
                        relation.setSoftwareStatus(AssetStatusEnum.NET_IN.getCode());
                        relation.setGmtCreate(System.currentTimeMillis());
                        relation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                        relation.setMemo("");
                        relation.setStatus(1);
                        assetSoftwareRelationList.add(relation);
                    }
                    assetSoftwareRelationDao.insertBatch(assetSoftwareRelationList);
                    return count;
                } catch (Exception e) {
                    LOGGER.error("修改资产失败", e);
                }
                return 0;
            }
        });
        return assetCount;
        // TODO 下发智甲

        // TODO 通知工作流
    }

    /**
     * 1-计算设备 2-网络设备 3-安全设备 4-存储介质 5-服务器 6-外设
     * @param type 导出模板的类型
     */
    public void exportTemplate(int type) throws Exception {
        switch (type) {
            case 1:
                exportToClient(ComputeDeviceEntity.class, "计算设备信息模板.xlsx", "计算设备");
                break;
            case 2:
                exportToClient(ComputeDeviceEntity.class, "网络设备信息模板.xlsx", "网络设备");
                break;
            case 3:
                exportToClient(SafetyEquipment.class, "安全设备信息模板.xlsx", "安全设备");
                break;
            case 4:
                exportToClient(HardDiskEntity.class, "存储介质信息模板.xlsx", "存储介质");
                break;
            case 5:
                exportToClient(ServerEntity.class, "服务器信息模板.xlsx", "服务器");
                break;
            case 6:
                exportToClient(AssetPeripheralEquipmentEntity.class, "外设信息模板.xlsx", "外设");
                break;
        }
    }

    @Override
    public void exportData(int type, AssetQuery assetQuery) throws Exception {
        switch (type) {
            case 1:
                exportData(ComputeDeviceEntity.class, "计算设备信息模板.xlsx", "计算设备", assetQuery);
                break;
        // 之后会重写
        // case 2:
        // exportData(ComputeDeviceEntity.class, "网络设备信息模板.xlsx", "网络设备", assetQuery);
        // break;
        // case 3:
        // exportData(SafetyEquipment.class, "安全设备信息模板.xlsx", "安全设备", assetQuery);
        // break;
        // case 4:
        // exportData(HardDiskEntity.class, "存储介质信息模板.xlsx", "存储介质", assetQuery);
        // break;
        // case 5:
        // exportData(ServerEntity.class, "服务器信息模板.xlsx", "服务器", assetQuery);
        // break;
        // case 6:
        // exportData(AssetPeripheralEquipmentEntity.class, "外设信息模板.xlsx", "外设", assetQuery);
        // break;
        }
    }

    private void exportToClient(Class clazz, String fileName, String title) {
        ExcelUtils.exportTemplet(clazz, fileName, title);
    }

    private void exportData(Class clazz, String fileName, String title, AssetQuery assetQuery) throws Exception {
        List<Asset> list = assetDao.findListAsset(assetQuery);
        List<ComputeDeviceEntity> computeDeviceEntities = entityConverter.convert(assetDao.findListAsset(assetQuery),
            ComputeDeviceEntity.class);
        ExcelUtils.exportToClient(clazz, fileName, title, findListAsset(assetQuery));
    }

    /**
     * 将ComputeDeviceEntity中的其他数据装填进去
     * @param list 处理的数据
     * @return 处理完的数据
     */
    private List<ComputeDeviceEntity> handleListComputeData(List<Asset> list) {
        List<ComputeDeviceEntity> computeDeviceEntities = new ArrayList<>();
        for (Asset asset : list) {
            handleComputeData(asset);
        }
        return computeDeviceEntities;
    }

    private List<ComputeDeviceEntity> handleComputeData(Asset asset) {
        ComputeDeviceEntity computeDeviceEntity=new ComputeDeviceEntity();
        handleComputeUserData(asset,computeDeviceEntity);
        return null;
    }

    private void handleComputeUserData(Asset asset,ComputeDeviceEntity computeDeviceEntity) {


    }
}
