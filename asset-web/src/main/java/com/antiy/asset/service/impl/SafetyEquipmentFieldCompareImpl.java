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
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.request.AssetSafetyEquipmentRequest;
import com.antiy.asset.vo.request.SysArea;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.SysUser;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.JsonUtil;

/**
 * 安全设备字段对比
 * @author zhangyajun
 * @create 2019-02-25 9:47
 **/
@Service
public class SafetyEquipmentFieldCompareImpl extends AbstractChangeRecordCompareImpl {
    @Resource
    private AreaClient                         areaClient;
    @Resource
    private RedisUtil                          redisUtil;
    @Resource
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;

    @Override
    List<Map<String, Object>> compareCommonBusinessInfo(Integer businessId) throws Exception {
        Integer hardware = 1;
        Integer oldInfo = 1;
        Integer newInfo = 0;
        SafetyEquipmentFieldCompareImpl safetyEquipmentFieldCompare = new SafetyEquipmentFieldCompareImpl();
        List<String> changeValStrList = safetyEquipmentFieldCompare.getTwoRecentChangeVal(businessId, hardware);
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
            String key = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(newAsset.getResponsibleUserId()));
            SysUser sysUser = redisUtil.getObject(key, SysUser.class);
            oldAssetBusinessInfo.setResponsibleUserName(sysUser == null ? null : sysUser.getName());
            oldAssetBusinessInfo.setContactTel(oldAsset.getContactTel());
            oldAssetBusinessInfo.setEmail(oldAsset.getEmail());
            oldAssetBusinessInfo.setAssetGroup(oldAsset.getAssetGroup());
            oldAssetBusinessInfo.setNumber(oldAsset.getNumber());
            oldAssetBusinessInfo.setLocation(oldAsset.getLocation());
            oldAssetBusinessInfo.setHouseLocation(oldAsset.getHouseLocation());
            oldAssetBusinessInfo.setFirmwareVersion(oldAsset.getFirmwareVersion());
            oldAssetBusinessInfo.setSoftwareVersion(oldAsset.getSoftwareVersion());
            oldAssetBusinessInfo.setIp(oldAsset.getIp());
            oldAssetBusinessInfo.setBuyDate(oldAsset.getBuyDate());
            oldAssetBusinessInfo.setServiceLife(oldAsset.getServiceLife());
            oldAssetBusinessInfo.setWarranty(oldAsset.getWarranty());
            oldAssetBusinessInfo.setDescrible(oldAsset.getDescrible());

            Asset newAssetBusinessInfo = new Asset();
            SysArea newSysArea = JsonUtil
                .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(newAsset.getAreaId())), SysArea.class);
            newAssetBusinessInfo.setAreaName(newSysArea.getFullName());
            String newKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysUser.class,
                DataTypeUtils.stringToInteger(newAsset.getResponsibleUserId()));
            SysUser newSysUser = redisUtil.getObject(newKey, SysUser.class);
            newAssetBusinessInfo.setResponsibleUserName(newSysUser == null ? null : newSysUser.getName());
            newAssetBusinessInfo.setContactTel(newAsset.getContactTel());
            newAssetBusinessInfo.setEmail(newAsset.getEmail());
            newAssetBusinessInfo.setAssetGroup(newAsset.getAssetGroup());
            newAssetBusinessInfo.setNumber(newAsset.getNumber());
            newAssetBusinessInfo.setLocation(newAsset.getLocation());
            newAssetBusinessInfo.setHouseLocation(newAsset.getHouseLocation());
            newAssetBusinessInfo.setFirmwareVersion(newAsset.getFirmwareVersion());
            newAssetBusinessInfo.setSoftwareVersion(newAsset.getSoftwareVersion());
            newAssetBusinessInfo.setIp(newAsset.getIp());
            newAssetBusinessInfo.setBuyDate(newAsset.getBuyDate());
            newAssetBusinessInfo.setServiceLife(newAsset.getServiceLife());
            newAssetBusinessInfo.setWarranty(newAsset.getWarranty());
            newAssetBusinessInfo.setDescrible(newAsset.getDescrible());

            List<Map<String, Object>> assetBusinessInfoCompareResult = CompareUtils.compareClass(oldAssetBusinessInfo,
                newAssetBusinessInfo, InfoLabelEnum.BUSINESSINFO.getMsg());

            AssetSafetyEquipmentRequest oldSafetyEquipmentRequest = oldAssetOuterRequest.getSafetyEquipment();
            AssetSafetyEquipmentRequest newSafetyEquipmentRequest = newAssetOuterRequest.getSafetyEquipment();
            List<Map<String, Object>> safetyEquipmentCompareResult = null;
            if (newSafetyEquipmentRequest != null && oldSafetyEquipmentRequest != null) {
                safetyEquipmentCompareResult = CompareUtils.compareClass(oldSafetyEquipmentRequest,
                    newSafetyEquipmentRequest, InfoLabelEnum.BUSINESSINFO.getMsg());
                changeValList.addAll(safetyEquipmentCompareResult);
            }
            changeValList.addAll(assetCommonInoCompareResult);
            changeValList.addAll(assetBusinessInfoCompareResult);
        }
        return changeValList;
    }

    @Override
    protected List<? extends BaseRequest> compareComponentInfo() {
        return null;
    }
}
