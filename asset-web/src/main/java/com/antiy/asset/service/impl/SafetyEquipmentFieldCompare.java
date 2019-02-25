package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.antiy.asset.dao.AssetChangeRecordDao;
import com.antiy.asset.dao.AssetSafetyEquipmentDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.util.CompareUtils;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.InfoLabelEnum;
import com.antiy.asset.vo.request.AssetOthersRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.request.SysAreaVO;
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
public class SafetyEquipmentFieldCompare extends AbstractChangeRecordCompareImpl {
    @Resource
    AssetSafetyEquipmentDao                    safetyEquipmentDao;
    @Resource
    private AreaClient                         areaClient;
    @Resource
    private RedisUtil                                  redisUtil;
    @Resource
    private BaseConverter<AssetRequest, Asset> assetRequestToAssetConverter;
    @Resource
    private AssetChangeRecordDao               assetChangeRecordDao;

    @Override
    List<Map<String, Object>> compareCommonBusinessInfo(Integer businessId) throws Exception {
        Integer hardware = 1;
        SafetyEquipmentFieldCompare safetyEquipmentFieldCompare = new SafetyEquipmentFieldCompare();
        List<String> changeValStrList = safetyEquipmentFieldCompare.getTwoRecentChangeVal(businessId, hardware);
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
            // redis调用（通过区域ID查询名称）
            SysAreaVO oldSysAreaVO = JsonUtil
                .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(oldAsset.getAreaId())), SysAreaVO.class);
            oldAssetBusinessInfo.setAreaName(oldSysAreaVO.getFullName());
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
            SysAreaVO newSysAreaVO = JsonUtil
                .json2Object(JSONUtils.toJSONString(areaClient.getInvokeResult(newAsset.getAreaId())), SysAreaVO.class);
            newAssetBusinessInfo.setAreaName(newSysAreaVO.getFullName());
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

            AssetOthersRequest oldNetworkEquipment = oldAssetOuterRequest.getAssetOthersRequest();
            AssetOthersRequest newNetworkEquipment = newAssetOuterRequest.getAssetOthersRequest();
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
