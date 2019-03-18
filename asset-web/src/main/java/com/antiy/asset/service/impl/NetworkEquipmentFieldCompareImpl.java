package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetNetworkEquipment;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.request.AssetNetworkEquipmentRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysArea;
import com.antiy.common.base.SysUser;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.JsonUtil;

/**
 * 网络设备字段对比
 * @author zhangyajun
 * @create 2019-02-25 9:47
 **/
@Service
public class NetworkEquipmentFieldCompareImpl extends AbstractChangeRecordCompareImpl {
    @Resource
    RedisUtil                                  redisUtil;
    @Resource
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;

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
            String oldAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(newAsset.getAreaId()));
            SysArea oldSysArea = redisUtil.getObject(oldAreaKey, SysArea.class);
            oldAssetBusinessInfo.setAreaName(oldSysArea.getFullName());
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(newAsset.getResponsibleUserId()));
            SysUser sysUser = redisUtil.getObject(key, SysUser.class);
            oldAssetBusinessInfo.setResponsibleUserName(sysUser == null ? "" : sysUser.getName());
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
            String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(newAsset.getAreaId()));
            SysArea newSysArea = redisUtil.getObject(newAreaKey, SysArea.class);
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
        AssetNetworkEquipmentRequest oldNetworkEquipmentRequest = oldAssetOuterRequest.getNetworkEquipment();
        AssetNetworkEquipmentRequest newWorkEquipmentRequest = newAssetOuterRequest.getNetworkEquipment();
        AssetNetworkEquipment oldNetworkEquipment = new AssetNetworkEquipment();

        oldNetworkEquipment.setPortSize(oldNetworkEquipmentRequest.getPortSize());
        oldNetworkEquipment.setInterfaceSize(oldNetworkEquipmentRequest.getInterfaceSize());
        oldNetworkEquipment.setIos(oldNetworkEquipmentRequest.getIos());
        oldNetworkEquipment.setFirmwareVersion(oldNetworkEquipmentRequest.getFirmwareVersion());
        oldNetworkEquipment.setIsWireless(oldNetworkEquipmentRequest.getIsWireless());
        oldNetworkEquipment.setInnerIp(oldNetworkEquipmentRequest.getInnerIp());
        oldNetworkEquipment.setOuterIp(oldNetworkEquipmentRequest.getOuterIp());
        oldNetworkEquipment.setMacAddress(oldNetworkEquipmentRequest.getMacAddress());
        oldNetworkEquipment.setSubnetMask(oldNetworkEquipmentRequest.getSubnetMask());
        oldNetworkEquipment.setRegister(oldNetworkEquipmentRequest.getRegister());
        oldNetworkEquipment.setSubnetMask(oldNetworkEquipmentRequest.getSubnetMask());
        oldNetworkEquipment.setExpectBandwidth(oldNetworkEquipmentRequest.getExpectBandwidth());
        oldNetworkEquipment.setCpuSize(oldNetworkEquipmentRequest.getCpuSize());
        oldNetworkEquipment.setCpuVersion(oldNetworkEquipmentRequest.getCpuVersion());
        oldNetworkEquipment.setDramSize(oldNetworkEquipmentRequest.getDramSize());
        oldNetworkEquipment.setFlashSize(oldNetworkEquipmentRequest.getFlashSize());
        oldNetworkEquipment.setNcrmSize(oldNetworkEquipmentRequest.getNcrmSize());

        AssetNetworkEquipment newNetworkEquipment = new AssetNetworkEquipment();
        newNetworkEquipment.setPortSize(newWorkEquipmentRequest.getPortSize());
        newNetworkEquipment.setInterfaceSize(newWorkEquipmentRequest.getInterfaceSize());
        newNetworkEquipment.setIos(newWorkEquipmentRequest.getIos());
        newNetworkEquipment.setFirmwareVersion(newWorkEquipmentRequest.getFirmwareVersion());
        newNetworkEquipment.setIsWireless(newWorkEquipmentRequest.getIsWireless());
        newNetworkEquipment.setInnerIp(newWorkEquipmentRequest.getInnerIp());
        newNetworkEquipment.setOuterIp(newWorkEquipmentRequest.getOuterIp());
        newNetworkEquipment.setMacAddress(newWorkEquipmentRequest.getMacAddress());
        newNetworkEquipment.setSubnetMask(newWorkEquipmentRequest.getSubnetMask());
        newNetworkEquipment.setRegister(newWorkEquipmentRequest.getRegister());
        newNetworkEquipment.setSubnetMask(newWorkEquipmentRequest.getSubnetMask());
        newNetworkEquipment.setExpectBandwidth(newWorkEquipmentRequest.getExpectBandwidth());
        newNetworkEquipment.setCpuSize(newWorkEquipmentRequest.getCpuSize());
        newNetworkEquipment.setCpuVersion(newWorkEquipmentRequest.getCpuVersion());
        newNetworkEquipment.setDramSize(newWorkEquipmentRequest.getDramSize());
        newNetworkEquipment.setFlashSize(newWorkEquipmentRequest.getFlashSize());
        newNetworkEquipment.setNcrmSize(newWorkEquipmentRequest.getNcrmSize());

        return CompareUtils.compareClass(oldNetworkEquipment, newNetworkEquipment, InfoLabelEnum.BUSINESSINFO.getMsg());
    }

    @Override
    protected List<? extends BaseRequest> compareComponentInfo() {
        return null;
    }
}
