package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetOuterResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 资产主表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService {

    @Resource
    private AssetDao                            assetDao;
    @Resource
    private AssetMainboradDao                   assetMainboradDao;
    @Resource
    private AssetMemoryDao                      assetMemoryDao;
    @Resource
    private AssetHardDiskDao                    assetHardDiskDao;
    @Resource
    private AssetCpuDao                         assetCpuDao;
    @Resource
    private AssetNetworkCardDao                 assetNetworkCardDao;
    @Resource
    private AssetNetworkEquipmentDao            assetNetworkEquipmentDao;
    @Resource
    private AssetSafetyEquipmentDao             assetSafetyEquipmentDao;
    @Resource
    private AssetSoftwareDao                    assetSoftwareDao;
    @Resource
    private AssetCategoryModelDao               assetCategoryModelDao;

    @Resource
    private AssetSoftwareRelationDao            assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>  requestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse> responseConverter;

    @Override
    public Integer saveAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
        Integer insert = assetDao.insert(asset);
        return insert;
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
    public Integer changeStatus(Integer[] ids, Integer targetStatus) throws Exception {
        int row;
        Map<String, Integer[]> map = new HashMap<>();
        map.put("ids", ids);
        map.put("assetStatus", new Integer[] { targetStatus });
        row = assetDao.changeStatus(map);
        return row;
    }

    @Override
    public Integer saveAllAsset(HashMap<String, Object> map) throws Exception {
        return null;
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
    public Map<String, Long> countManufacturer() throws Exception {
        List<Map<String, Long>> list = assetDao.countManufacturer();
        return transferListToMap(list);
    }

    private Map<String, Long> transferListToMap(List<Map<String, Long>> list) {
        Map result = new HashMap();
        for (Map map : list) {
            result.put(map.get("key"), map.get("value"));
        }
        return result;
    }

    @Override
    public Map<String, Long> countStatus() throws Exception {
        List<Map<String, Long>> list = assetDao.countStatus();
        Map<String, Long> result = new HashMap();
        for (Map map : list) {
            result.put(AssetStatusEnum.getAssetByCode((Integer) map.get("key")) + "", (Long) map.get("value"));
        }
        return result;
    }

    @Override
    public Map<String, Long> countCategory() throws Exception {
        HashMap<String, Object> map = new HashMap();
        map.put("name", "硬件");
        map.put("parentId", 0);
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getByWhere(map);
        Integer id = categoryModelList.get(0).getId();
        map.clear();
        map.put("parentId", id);
        List<AssetCategoryModel> categoryModelList1 = assetCategoryModelDao.getByWhere(map);
        HashMap<String, Long> result = new HashMap<>();
        for (AssetCategoryModel a : categoryModelList1) {
            List<AssetCategoryModel> search = recursionSearch(a.getId());
            Long sum = 0L;
            for (AssetCategoryModel b : search) {
                HashMap param = new HashMap();
                param.put("parentId", b.getId());
                sum += assetDao.getByWhere(param).size();
            }
            result.put(a.getName(), sum);
        }
        return result;
    }

    @Override
    public Integer saveAssetPC(AssetPCRequest assetPCRequest) throws Exception {
        List<AssetCpuRequest> cpuRequestList = assetPCRequest.getCpu();
        BaseConverter<AssetCpuRequest, AssetCpu> baseConverter = new BaseConverter<>();
        List<AssetCpu> cpu = baseConverter.convert(cpuRequestList, AssetCpu.class);
        List<AssetHardDisk> hardDisk = new BaseConverter<AssetHardDiskRequest, AssetHardDisk>()
            .convert(assetPCRequest.getHardDisk(), AssetHardDisk.class);
        List<AssetMemory> memory = new BaseConverter<AssetMemoryRequest, AssetMemory>()
            .convert(assetPCRequest.getMemory(), AssetMemory.class);
        List<AssetMainborad> mainboard = new BaseConverter<AssetMainboradRequest, AssetMainborad>()
            .convert(assetPCRequest.getMainboard(), AssetMainborad.class);
        List<AssetNetworkCard> networkCard = new BaseConverter<AssetNetworkCardRequest, AssetNetworkCard>()
            .convert(assetPCRequest.getNetworkCard(), AssetNetworkCard.class);

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
                assetMainboradDao.insert(assetMainborad);
            }
        }
        if (memory != null && memory.size() > 0) {
            for (AssetMemory assetMemory : memory) {
                assetMemory.setAssetId(aid);
                assetMemory.setGmtCreate(System.currentTimeMillis());
                assetMemoryDao.insert(assetMemory);
            }
        }

        if (cpu != null && cpu.size() > 0) {
            for (AssetCpu assetCpu : cpu) {
                assetCpu.setAssetId(aid);
                assetCpu.setGmtCreate(System.currentTimeMillis());
                assetCpuDao.insert(assetCpu);
            }
        }

        if (hardDisk != null && hardDisk.size() > 0) {
            for (AssetHardDisk assetHardDisk : hardDisk) {
                assetHardDisk.setAssetId(aid);
                assetHardDisk.setGmtCreate(System.currentTimeMillis());
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
            if (AssetCategoryModel.getId() == id)
                result.add(AssetCategoryModel);
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
            if (AssetCategoryModel.getParentId() == id) {
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
    public AssetOuterResponse getByAssetId(Integer id) throws Exception {
        AssetOuterResponse assetOuterResponse = new AssetOuterResponse();
        Asset asset = assetDao.getById(id);
        return null;
    }

    @Override
    public void changeAsset(Integer id) {

    }
}
