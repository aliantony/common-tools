package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetRequest, Asset> requestConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse> responseConverter;

    @Override
    public Integer saveAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
        return assetDao.insert(asset);
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
    public Integer updateAsset(AssetRequest request) throws Exception {
        Asset asset = requestConverter.convert(request, Asset.class);
        return assetDao.update(asset);
    }

    @Override
    public List<AssetResponse> findListAsset(AssetQuery query) throws Exception {
        List<Asset> asset = assetDao.findListAsset(query);
        List<Object> objects = BeanConvert.convert(asset, AssetResponse.class);
        List<AssetResponse> assetResponses = new ArrayList<>();
        objects.forEach(x -> assetResponses.add((AssetResponse) x));
        return assetResponses;
    }

    public Integer findCountAsset(AssetQuery query) throws Exception {
        return assetDao.findCount(query);
    }

    @Override
    public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAsset(query), query.getCurrentPage(), this.findListAsset(query));
    }

    @Override
    public void saveReportAsset(List<AssetOuterRequest> assetOuterRequestList) throws Exception {
        ParamterExceptionUtils.isEmpty(assetOuterRequestList, "上报数据为空");
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
                //保存主板信息
                AssetMainborad mainborad = assetOuterRequest.getMainboard();
                if (mainborad != null) {
                    mainborad.setAssetId(asset.getId());
                    mainborad.setGmtCreate(System.currentTimeMillis());
                    assetMainboradDao.insert(mainborad);
                }
                //保存内存信息
                List<AssetMemory> memoryList = assetOuterRequest.getMemory();
                if (memoryList != null && !memoryList.isEmpty()) {
                    for (AssetMemory memory : memoryList) {
                        memory.setAssetId(asset.getId());
                        memory.setGmtCreate(System.currentTimeMillis());
                        assetMemoryDao.insert(memory);
                    }
                }
                //保存硬盘信息
                List<AssetHardDisk> hardDiskList = assetOuterRequest.getHardDisk();
                if (hardDiskList != null && !hardDiskList.isEmpty()) {
                    for (AssetHardDisk hardDisk : hardDiskList) {
                        hardDisk.setAssetId(asset.getId());
                        hardDisk.setGmtCreate(System.currentTimeMillis());
                        assetHardDiskDao.insert(hardDisk);
                    }
                }
                //保存CPU信息
                List<AssetCpu> cpuList = assetOuterRequest.getCpu();
                if (cpuList != null && cpuList.size() > 0) {
                    for (AssetCpu cpu : cpuList) {
                        cpu.setAssetId(asset.getId());
                        cpu.setGmtCreate(System.currentTimeMillis());
                        assetCpuDao.insert(cpu);
                    }
                }
                //保存网卡信息
                if (networkCardList != null && !networkCardList.isEmpty()) {
                    for (AssetNetworkCard networkCard : networkCardList) {
                        networkCard.setAssetId(asset.getId());
                        networkCard.setGmtCreate(System.currentTimeMillis());
                        assetNetworkCardDao.insert(networkCard);
                    }
                }
                //网络设备
                AssetNetworkEquipment networkEquipment = assetOuterRequest.getNetworkEquipment();
                if (networkEquipment != null) {
                    networkEquipment.setAssetId(asset.getId());
                    networkEquipment.setGmtCreate(System.currentTimeMillis());
                    assetNetworkEquipmentDao.insert(networkEquipment);
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
                        if(assetSoftwareDao.checkRepeatSoftware(software) <= 0){
                            software.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareDao.insert(software);
                            //资产软件关系表
                            AssetSoftwareRelation softwareRelation = new AssetSoftwareRelation();
                            softwareRelation.setAssetId(asset.getId());
                            softwareRelation.setSoftwareId(software.getId());
                            softwareRelation.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareRelationDao.insert(softwareRelation);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
}
