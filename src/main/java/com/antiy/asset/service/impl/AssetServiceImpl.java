package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.asset.vo.response.ManufacturerResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.ParamterExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资产主表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Slf4j
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService {


    @Resource
    private AssetDao assetDao;
    @Resource
    private AssetMainboradDao assetMainboradDao;
    @Resource
    private AssetMemoryDao assetMemoryDao;
    @Resource
    private AssetHardDiskDao assetHardDiskDao;
    @Resource
    private AssetCpuDao assetCpuDao;
    @Resource
    private AssetNetworkCardDao assetNetworkCardDao;
    @Resource
    private AssetNetworkEquipmentDao assetNetworkEquipmentDao;
    @Resource
    private AssetSafetyEquipmentDao assetSafetyEquipmentDao;
    @Resource
    private AssetSoftwareDao assetSoftwareDao;
    @Resource
    private AssetCategoryModelDao assetCategoryModelDao;

    @Resource
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetRequest, Asset> requestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse> responseConverter;
    @Resource
    private BaseConverter<Asset, ManufacturerResponse> manufacturerResponseConverter;

    @Override
    public Integer saveAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
        return assetDao.insert(asset);
    }



    @Override
    public Integer updateAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
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
        return new PageResult<>(query.getPageSize(), this.findCountAsset(query), query.getCurrentPage(), this.findListAsset(query));
    }

//    /*@Override
//    public void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception {
//        ParamterExceptionUtils.isEmpty(assetOuterRequestList, "上报数据为空");
//        Asset nasset = new Asset();
//        assetOuterRequestList.stream().forEach(assetOuterRequest -> {
//            try {
//                Asset asset = assetOuterRequest.getAsset();
//                if (asset == null) {
//                    return;
//                }
//                List<AssetNetworkCard> networkCardList = assetOuterRequest.getNetworkCard();
//                if (networkCardList != null && !networkCardList.isEmpty()) {
//                    List<String[]> ipMac = Lists.newArrayList();
//                    for (AssetNetworkCard networkCard : networkCardList) {
//                        ipMac.add(new String[]{networkCard.getIpAddress(), networkCard.getMacAddress()});
//                    }
//                    //资产判重，通过网卡IP地址和MAC地址判断
//                    if (checkRepeatAsset(asset.getUuid(), ipMac)) {
//                        return;
//                    }
//                }
//                //资产主表信息
//                asset.setGmtCreate(System.currentTimeMillis());
//                //asset.setCreateUser();
//                assetDao.insert(asset);
//                nasset.setId(asset.getId());
//                //保存主板信息
//                AssetMainborad mainborad = assetOuterRequest.getMainboard();
//                if (mainborad != null) {
//                    mainborad.setAssetId(asset.getId());
//                    mainborad.setGmtCreate(System.currentTimeMillis());
//                    assetMainboradDao.insert(mainborad);
//                }
//                //保存内存信息
//                List<AssetMemory> memoryList = assetOuterRequest.getMemory();
//                if (memoryList != null && !memoryList.isEmpty()) {
//                    JSONArray jsonArray = new JSONArray();
//                    for (AssetMemory memory : memoryList) {
//                        memory.setAssetId(asset.getId());
//                        memory.setGmtCreate(System.currentTimeMillis());
//                        assetMemoryDao.insert(memory);
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("id", memory.getId());
//                        jsonObject.put("capacity", memory.getCapacity());
//                        jsonObject.put("frequency", memory.getFrequency());
//                        jsonArray.add(jsonObject);
//                    }
//                    nasset.setMemory(jsonArray.toJSONString());
//                }
//                //保存硬盘信息
//                List<AssetHardDisk> hardDiskList = assetOuterRequest.getHardDisk();
//                if (hardDiskList != null && !hardDiskList.isEmpty()) {
//                    JSONArray jsonArray = new JSONArray();
//                    for (AssetHardDisk hardDisk : hardDiskList) {
//                        hardDisk.setAssetId(asset.getId());
//                        hardDisk.setGmtCreate(System.currentTimeMillis());
//                        assetHardDiskDao.insert(hardDisk);
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("id", hardDisk.getId());
//                        jsonObject.put("capacity", hardDisk.getCapacity());
//                        jsonObject.put("brand", hardDisk.getBrand());
//                        jsonObject.put("model", hardDisk.getModel());
//                        jsonArray.add(jsonObject);
//                    }
//                    nasset.setHardDisk(jsonArray.toJSONString());
//                }
//                //保存CPU信息
//                List<AssetCpu> cpuList = assetOuterRequest.getCpu();
//                if (cpuList != null && cpuList.size() > 0) {
//                    JSONArray jsonArray = new JSONArray();
//                    for (AssetCpu cpu : cpuList) {
//                        cpu.setAssetId(asset.getId());
//                        cpu.setGmtCreate(System.currentTimeMillis());
//                        assetCpuDao.insert(cpu);
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("id", cpu.getId());
//                        jsonObject.put("brand", cpu.getBrand());
//                        jsonObject.put("model", cpu.getModel());
//                        jsonObject.put("core_size", cpu.getCoreSize());
//                        jsonObject.put("thread_size", cpu.getThreadSize());
//                        jsonArray.add(jsonObject);
//                    }
//                    nasset.setCpu(jsonArray.toJSONString());
//                }
//                //保存网卡信息
//                if (networkCardList != null && !networkCardList.isEmpty()) {
//                    JSONArray jsonArray = new JSONArray();
//                    for (AssetNetworkCard networkCard : networkCardList) {
//                        networkCard.setAssetId(asset.getId());
//                        networkCard.setGmtCreate(System.currentTimeMillis());
//                        assetNetworkCardDao.insert(networkCard);
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("id", networkCard.getId());
//                        jsonObject.put("brand", networkCard.getBrand());
//                        jsonObject.put("model", networkCard.getModel());
//                        jsonObject.put("ip_address", networkCard.getIpAddress());
//                        jsonObject.put("mac_address", networkCard.getMacAddress());
//                        jsonArray.add(jsonObject);
//                    }
//                    nasset.setNetworkCard(jsonArray.toJSONString());
//                }
//                //网络设备
//                AssetNetworkEquipment networkEquipment = assetOuterRequest.getNetworkEquipment();
//                if (networkEquipment != null) {
//                    networkEquipment.setAssetId(asset.getId());
//                    networkEquipment.setGmtCreate(System.currentTimeMillis());
//                    assetNetworkEquipmentDao.insert(networkEquipment);
//                }
//                //安全设备
//                AssetSafetyEquipment safetyEquipment = assetOuterRequest.getSafetyEquipment();
//                if (safetyEquipment != null) {
//                    safetyEquipment.setAssetId(asset.getId());
//                    safetyEquipment.setGmtCreate(System.currentTimeMillis());
//                    assetSafetyEquipmentDao.insert(safetyEquipment);
//                }
//                //软件
//                List<AssetSoftware> softwareList = assetOuterRequest.getSoftware();
//                if (softwareList != null && !softwareList.isEmpty()) {
//                    for (AssetSoftware software : softwareList) {
//                        //判断软件是否已经存在
//                        if(assetSoftwareDao.checkRepeatSoftware(software) <= 0){
//                            software.setGmtCreate(System.currentTimeMillis());
//                            assetSoftwareDao.insert(software);
//                            //资产软件关系表
//                            AssetSoftwareRelation softwareRelation = new AssetSoftwareRelation();
//                            softwareRelation.setAssetId(asset.getId());
//                            softwareRelation.setSoftwareId(software.getId());
//                            softwareRelation.setGmtCreate(System.currentTimeMillis());
//                            assetSoftwareRelationDao.insert(softwareRelation);
//                        }
//                    }
//                }
//                //保存资产硬件的JSON数据
//                assetDao.update(nasset);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }*/

    @Override
    public void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception {
        ParamterExceptionUtils.isEmpty(assetOuterRequestList, "上报数据为空");
        List<Asset> assetList = Lists.newArrayList();
        List<AssetMainborad> assetMainboradList = Lists.newArrayList();
        List<AssetMemory> assetMemoryList = Lists.newArrayList();
        List<AssetHardDisk> assetHardDiskList = Lists.newArrayList();
        List<AssetCpu> assetCpuList = Lists.newArrayList();
        List<AssetNetworkCard> assetNetworkCardList = Lists.newArrayList();
        List<AssetSafetyEquipment> assetSafetyEquipmentList = Lists.newArrayList();
        List<AssetNetworkEquipment> assetNetworkEquipmentList = Lists.newArrayList();
        List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();

        assetOuterRequestList.stream().forEach(assetOuterRequest -> {
            try {
                Asset asset = assetOuterRequest.getAsset();
                if (asset == null) {
                    return;
                }
                List<AssetNetworkCard> networkCardList = assetOuterRequest.getNetworkCard();
                if (networkCardList != null && !networkCardList.isEmpty()) {
                    List<String[]> ipMac = Lists.newArrayList();
                    for (AssetNetworkCard networkCard : networkCardList) {
                        ipMac.add(new String[]{networkCard.getIpAddress(), networkCard.getMacAddress()});
                    }
                    //资产判重，通过网卡IP地址和MAC地址判断
                    if (checkRepeatAsset(asset.getUuid(), ipMac)) {
                        return;
                    }
                }
                //资产主表信息
                asset.setGmtCreate(System.currentTimeMillis());
                //asset.setCreateUser();
                assetDao.insert(asset);
                Asset nasset = new Asset();
                nasset.setId(asset.getId());
                //保存主板信息
                AssetMainborad mainborad = assetOuterRequest.getMainboard();
                if (mainborad != null) {
                    mainborad.setAssetId(asset.getId());
                    mainborad.setGmtCreate(System.currentTimeMillis());
                    assetMainboradList.add(mainborad);
                }
                //保存内存信息
                List<AssetMemory> memoryList = assetOuterRequest.getMemory();
                if (memoryList != null && !memoryList.isEmpty()) {
                    for (AssetMemory memory : memoryList) {
                        memory.setAssetId(asset.getId());
                        memory.setGmtCreate(System.currentTimeMillis());
                        assetMemoryList.add(memory);
                    }
                }
                //保存硬盘信息
                List<AssetHardDisk> hardDiskList = assetOuterRequest.getHardDisk();
                if (hardDiskList != null && !hardDiskList.isEmpty()) {
                    for (AssetHardDisk hardDisk : hardDiskList) {
                        hardDisk.setAssetId(asset.getId());
                        hardDisk.setGmtCreate(System.currentTimeMillis());
                        assetHardDiskList.add(hardDisk);
                    }
                }
                //保存CPU信息
                List<AssetCpu> cpuList = assetOuterRequest.getCpu();
                if (cpuList != null && cpuList.size() > 0) {
                    for (AssetCpu cpu : cpuList) {
                        cpu.setAssetId(asset.getId());
                        cpu.setGmtCreate(System.currentTimeMillis());
                        assetCpuList.add(cpu);

                    }
                }
                //保存网卡信息
                if (networkCardList != null && !networkCardList.isEmpty()) {
                    for (AssetNetworkCard networkCard : networkCardList) {
                        networkCard.setAssetId(asset.getId());
                        networkCard.setGmtCreate(System.currentTimeMillis());
                        assetNetworkCardList.add(networkCard);
                    }
                }
                //网络设备
                AssetNetworkEquipment networkEquipment = assetOuterRequest.getNetworkEquipment();
                if (networkEquipment != null) {
                    networkEquipment.setAssetId(asset.getId());
                    networkEquipment.setGmtCreate(System.currentTimeMillis());
                    assetNetworkEquipmentList.add(networkEquipment);
                }
                //安全设备
                AssetSafetyEquipment safetyEquipment = assetOuterRequest.getSafetyEquipment();
                if (safetyEquipment != null) {
                    safetyEquipment.setAssetId(asset.getId());
                    safetyEquipment.setGmtCreate(System.currentTimeMillis());
                    assetSafetyEquipmentDao.insert(safetyEquipment);
                }
                //软件
                List<AssetSoftware> softwareList = assetOuterRequest.getSoftware();
                if (softwareList != null && !softwareList.isEmpty()) {
                    for (AssetSoftware software : softwareList) {
                        //判断软件是否已经存在
                        if (assetSoftwareDao.checkRepeatSoftware(software) <= 0) {
                            software.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareDao.insert(software);
                        }
                        AssetSoftwareRelation softwareRelation = new AssetSoftwareRelation();
                        softwareRelation.setAssetId(asset.getId());
                        softwareRelation.setSoftwareId(software.getId());
                        assetSoftwareRelationList.add(softwareRelation);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

    @Override
    public List<ManufacturerResponse> findManufacturer() throws Exception {
        List<ManufacturerResponse> manufacturerResponseList = manufacturerResponseConverter.convert(assetDao.findManufacturer(),ManufacturerResponse.class);
        return manufacturerResponseList;
    }

    @Override
    public boolean checkRepeatAsset(String uuid, List<String[]> ipMac) {
        ParamterExceptionUtils.isBlank(uuid, "上报设备资产UUID不能为空");
        try {
            List<Asset> list = assetDao.checkRepeatAsset(ipMac);
            //资产已存在
            if (list != null && !list.isEmpty()) {
                if (list.size() > 1) {
                    //资产数量大于1，异常情况，产生告警
                    //TODO

                } else {
                    Asset asset = list.get(0);
                    //原记录UUID为空，将新的UUID更新进去
                    if (StringUtils.isBlank(asset.getUuid())) {
                        asset.setUuid(uuid);
                        assetDao.update(asset);
                    } else {
                        if (uuid.equals(asset.getUuid())) {
                            //新旧UUID不一致
                            //TODO
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
    public Integer changeStatus(Integer[] ids, Integer targetStatus) throws Exception {
        int row;
        Map<String, Integer[]> map = new HashMap<>();
        map.put("ids", ids);
        map.put("assetStatus", new Integer[]{targetStatus});
        row = assetDao.changeStatus(map);
        return row;
    }

    @Override
    public Integer saveAllAsset(HashMap<String, Object> map) throws Exception {
        Asset asset = new Asset ();
        Integer aid = assetDao.insert (asset);
        Object id = map.get ("id");
        return  null;
    }

    @Override
    public List<AssetResponse> findListAssetByCategoryModel(AssetCategoryModelQuery query) throws Exception {
        assetCategoryModelD
        List<Asset> asset = assetDao.findListAssetByCategoryModel(query);
        List<AssetResponse> objects = responseConverter.convert(asset, AssetResponse.class);
        return objects;
    }

    @Override
    public Integer findCountByCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return assetDao.findCountByCategoryModel(query);
    }

    @Override
    public PageResult<AssetResponse> findPageAssetByCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountByCategoryModel(query), query.getCurrentPage(), this.findListAssetByCategoryModel(query));
    }
}
