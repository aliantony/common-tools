package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.intergration.AreaClient;
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
    private AreaClient                         areaClient;
    @Resource
    RedisUtil                                  redisUtil;
    @Resource
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;

    @Override
    List<Map<String, Object>> compareCommonBusinessInfo(Integer businessId) throws Exception {
        Integer hardware = 1;
        List<String> changeValStrList = super.getTwoRecentChangeVal(businessId, hardware);
        List<Map<String, Object>> changeValList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(changeValStrList) && changeValStrList.size() > 1) {
            // 变更前的信息
            AssetOuterRequest oldAssetOuterRequest = JsonUtil.json2Object(changeValStrList.get(1),
                AssetOuterRequest.class);
            // 变更后的信息
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
            SysArea oldSysAreaVO = JsonUtil
                .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(oldAsset.getAreaId())), SysArea.class);
            oldAssetBusinessInfo.setAreaName(oldSysAreaVO.getFullName());
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

            Asset newAssetBusinessInfo = new Asset();
            SysArea newSysAreaVO = JsonUtil
                .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(newAsset.getAreaId())), SysArea.class);
            oldAssetBusinessInfo.setAreaName(newSysAreaVO.getFullName());

            newAssetBusinessInfo.setContactTel(newAsset.getContactTel());
            newAssetBusinessInfo.setEmail(newAsset.getEmail());
            newAssetBusinessInfo.setAssetGroup(newAsset.getAssetGroup());
            newAssetBusinessInfo.setNumber(newAsset.getNumber());
            newAssetBusinessInfo.setLocation(newAsset.getLocation());
            newAssetBusinessInfo.setHouseLocation(newAsset.getHouseLocation());
            newAssetBusinessInfo.setDescrible(newAsset.getDescrible());

            List<Map<String, Object>> assetBusinessInfoCompareResult = CompareUtils.compareClass(oldAssetBusinessInfo,
                newAssetBusinessInfo, InfoLabelEnum.BUSINESSINFO.getMsg());

            AssetNetworkEquipmentRequest oldNetworkEquipment = oldAssetOuterRequest.getNetworkEquipment();
            // AssetNetworkEquipmentRequest networkEquipmentRequest = oldAssetOuterRequest.getNetworkEquipment();
            // AssetNetworkEquipment oldNetworkEquipment = new AssetNetworkEquipment();
            // oldNetworkEquipment.setPortSize(networkEquipmentRequest.getPortSize());
            // oldNetworkEquipment.setInterfaceSize(networkEquipmentRequest.getInterfaceSize());
            // oldNetworkEquipment.setIos(networkEquipmentRequest.getIos());
            // oldNetworkEquipment.setFirmwareVersion(networkEquipmentRequest.getFirmwareVersion());
            // oldNetworkEquipment.setIsWireless(networkEquipmentRequest.getIsWireless());
            // oldNetworkEquipment.setInnerIp(networkEquipmentRequest.getInnerIp());
            // oldNetworkEquipment.setOuterIp(networkEquipmentRequest.getOuterIp());
            // oldNetworkEquipment.setMacAddress(networkEquipmentRequest.getMacAddress());
            // oldNetworkEquipment.setSubnetMask(networkEquipmentRequest.getSubnetMask());
            // oldNetworkEquipment.setExpectBandwidth(networkEquipmentRequest.getExpectBandwidth());
            // oldNetworkEquipment.setRegister(networkEquipmentRequest.getRegister());
            // oldNetworkEquipment.setSubnetMask(networkEquipmentRequest.getSubnetMask());
            AssetNetworkEquipmentRequest newNetworkEquipment = newAssetOuterRequest.getNetworkEquipment();
            List<Map<String, Object>> networkEquipmentCompareResult = CompareUtils.compareClass(oldNetworkEquipment,
                newNetworkEquipment, InfoLabelEnum.BUSINESSINFO.getMsg());

            changeValList.addAll(assetCommonInoCompareResult);
            changeValList.addAll(assetBusinessInfoCompareResult);
            changeValList.addAll(networkEquipmentCompareResult);
        }
        return changeValList;
    }

    @Override
    protected List<? extends BaseRequest> compareComponentInfo() {
        return null;
    }
}