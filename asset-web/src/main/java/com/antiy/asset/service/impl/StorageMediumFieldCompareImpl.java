package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetUserDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetStorageMedium;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysArea;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.JsonUtil;

/**
 * 存储设备字段对比
 * @author zhangyajun
 * @create 2019-02-25 9:47
 **/
@Service
public class StorageMediumFieldCompareImpl extends AbstractChangeRecordCompareImpl {
    @Resource
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;
    @Resource
    AssetUserDao                               userDao;
    @Resource
    private RedisUtil                          redisUtil;

    @Override
    List<Map<String, Object>> compareCommonBusinessInfo(Integer businessId) throws Exception {
        Integer hardware = 1;
        int old = 0;
        int fresh = 1;
        List<String> changeValStrList = super.getTwoRecentChangeVal(businessId, hardware);
        List<Map<String, Object>> changeValList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(changeValStrList) && changeValStrList.size() > 1) {
            // 变更前的信息
            AssetOuterRequest oldAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(old),
                AssetOuterRequest.class);
            // 变更后的信息
            Asset oldAsset = assetRequestToAssetConverter.convert(oldAssetOuterRequest.getAsset(), Asset.class);
            AssetOuterRequest newAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(fresh),
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
            String oldAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(),
                com.antiy.common.base.SysArea.class, DataTypeUtils.stringToInteger(newAsset.getAreaId()));
            com.antiy.common.base.SysArea oldSysArea = redisUtil.getObject(oldAreaKey,
                com.antiy.common.base.SysArea.class);
            oldAssetBusinessInfo.setAreaName(oldSysArea.getFullName());
            oldAssetBusinessInfo
                .setResponsibleUserName(userDao.findUserName(userDao.findUserName(newAsset.getResponsibleUserId())));
            oldAssetBusinessInfo.setContactTel(oldAsset.getContactTel());
            oldAssetBusinessInfo.setEmail(oldAsset.getEmail());
            oldAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
            oldAssetBusinessInfo.setNumber(oldAsset.getNumber());
            oldAssetBusinessInfo.setLocation(oldAsset.getLocation());
            oldAssetBusinessInfo.setHouseLocation(oldAsset.getHouseLocation());
            oldAssetBusinessInfo.setDescrible(oldAsset.getDescrible());
            oldAssetBusinessInfo.setBuyDate(oldAsset.getBuyDate());
            oldAssetBusinessInfo.setWarranty(oldAsset.getWarranty());

            Asset newAssetBusinessInfo = new Asset();
            // redis调用（通过区域ID查询名称）
            String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(),
                com.antiy.common.base.SysArea.class, DataTypeUtils.stringToInteger(newAsset.getAreaId()));
            com.antiy.common.base.SysArea newSysArea = redisUtil.getObject(newAreaKey, SysArea.class);
            oldAssetBusinessInfo.setAreaName(newSysArea.getFullName());

            newAssetBusinessInfo.setContactTel(newAsset.getContactTel());
            newAssetBusinessInfo.setEmail(newAsset.getEmail());
            newAssetBusinessInfo.setAssetGroup(newAsset.getAssetGroup());
            newAssetBusinessInfo.setNumber(newAsset.getNumber());
            newAssetBusinessInfo.setLocation(newAsset.getLocation());
            newAssetBusinessInfo.setHouseLocation(newAsset.getHouseLocation());
            newAssetBusinessInfo.setDescrible(newAsset.getDescrible());
            newAssetBusinessInfo.setBuyDate(newAsset.getBuyDate());
            newAssetBusinessInfo.setServiceLife(newAsset.getServiceLife());
            newAssetBusinessInfo.setWarranty(newAsset.getWarranty());

            List<Map<String, Object>> assetBusinessInfoCompareResult = CompareUtils.compareClass(oldAssetBusinessInfo,
                newAssetBusinessInfo, InfoLabelEnum.BUSINESSINFO.getMsg());

            // 组合数据
            changeValList.addAll(assetCommonInoCompareResult);
            changeValList.addAll(assetBusinessInfoCompareResult);
            changeValList.addAll(getDetailCompareResult(oldAssetOuterRequest, newAssetOuterRequest));
        }
        return changeValList;
    }

    /**
     * 获取详细信息字段的对比结果
     * @param oldAssetOuterRequest
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    private List<Map<String, Object>> getDetailCompareResult(AssetOuterRequest oldAssetOuterRequest,
                                                             AssetOuterRequest newAssetOuterRequest) throws IllegalAccessException {
        AssetStorageMediumRequest oldStorageMediumRequest = oldAssetOuterRequest.getAssetStorageMedium();
        AssetStorageMediumRequest newStorageMediumRequest = newAssetOuterRequest.getAssetStorageMedium();
        AssetStorageMedium oldStorageMedium = new AssetStorageMedium();

        oldStorageMedium.setMaximumStorage(oldStorageMediumRequest.getMaximumStorage());
        oldStorageMedium.setDiskNumber(oldStorageMediumRequest.getDiskNumber());
        oldStorageMedium.setHighCache(oldStorageMediumRequest.getHighCache());
        oldStorageMedium.setInnerInterface(oldStorageMediumRequest.getInnerInterface());
        oldStorageMedium.setRaidSupport(oldStorageMediumRequest.getRaidSupport());
        oldStorageMedium.setAverageTransferRate(oldStorageMediumRequest.getAverageTransferRate());
        oldStorageMedium.setFirmwareVersion(oldStorageMediumRequest.getFirmwareVersion());
        oldStorageMedium.setOsVersion(oldStorageMediumRequest.getOsVersion());
        oldStorageMedium.setDriverNumber(oldStorageMediumRequest.getDriverNumber());

        AssetStorageMedium newStorageMedium = new AssetStorageMedium();
        newStorageMedium.setMaximumStorage(newStorageMediumRequest.getMaximumStorage());
        newStorageMedium.setDiskNumber(newStorageMediumRequest.getDiskNumber());
        newStorageMedium.setHighCache(newStorageMediumRequest.getHighCache());
        newStorageMedium.setInnerInterface(newStorageMediumRequest.getInnerInterface());
        newStorageMedium.setRaidSupport(newStorageMediumRequest.getRaidSupport());
        newStorageMedium.setAverageTransferRate(newStorageMediumRequest.getAverageTransferRate());
        newStorageMedium.setFirmwareVersion(newStorageMediumRequest.getFirmwareVersion());
        newStorageMedium.setOsVersion(newStorageMediumRequest.getOsVersion());
        newStorageMedium.setDriverNumber(newStorageMediumRequest.getDriverNumber());

        return CompareUtils.compareClass(oldStorageMedium, newStorageMedium, InfoLabelEnum.BUSINESSINFO.getMsg());
    }

    @Override
    protected List<? extends BaseRequest> compareComponentInfo() {
        return null;
    }
}
