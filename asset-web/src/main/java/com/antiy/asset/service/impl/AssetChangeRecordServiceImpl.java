package com.antiy.asset.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetChangeRecord;
import com.antiy.asset.intergration.ActivityClient;
import com.antiy.asset.intergration.AreaClient;
import com.antiy.asset.service.IAssetChangeRecordService;
import com.antiy.asset.vo.enums.AssetActivityTypeEnum;
import com.antiy.asset.vo.query.AssetChangeRecordQuery;
import com.antiy.asset.vo.request.AssetChangeRecordRequest;
import com.antiy.asset.vo.request.AssetOuterRequest;
import com.antiy.asset.vo.request.AssetRequest;
import com.antiy.asset.vo.request.ManualStartActivityRequest;
import com.antiy.asset.vo.response.AssetChangeRecordResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.JsonUtil;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 变更记录表 服务实现类 </p>
 *
 * @author why
 * @since 2019-02-19
 */
@Service
public class AssetChangeRecordServiceImpl extends BaseServiceImpl<AssetChangeRecord>
                                          implements IAssetChangeRecordService {

    private static final Logger                                         logger = LogUtils.get();

    @Resource
    private AssetDao                                                    assetDao;
    @Resource
    private AssetMemoryDao                                              memoryDao;
    @Resource
    private AssetMainboradDao                                           mainboradDao;
    @Resource
    private AssetNetworkCardDao                                         networkCardDao;
    @Resource
    private AssetHardDiskDao                                            hardDiskDao;
    @Resource
    private AssetSoftwareDao                                            softwareDao;
    @Resource
    private AssetCpuDao                                                 cpuDao;
    @Resource
    private AssetSoftwareRelationDao                                    softwareRelationDao;
    @Resource
    private AssetSoftwareLicenseDao                                     softwareLicenseDao;
    @Resource
    private ActivityClient                                              activityClient;
    @Resource
    private AssetChangeRecordDao                                        assetChangeRecordDao;
    @Resource
    private BaseConverter<AssetRequest, Asset>                          assetRequestToAssetConverter;
    @Resource
    private BaseConverter<AssetChangeRecordRequest, AssetChangeRecord>  requestConverter;
    @Resource
    private BaseConverter<AssetChangeRecord, AssetChangeRecordResponse> responseConverter;
    @Resource
    private AreaClient                                                  areaClient;

    @Override
    public ActionResponse saveAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
        AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
        AssetOuterRequest assetOuterRequest = request.getAssetOuterRequest();

        assetChangeRecord.setChangeVal(JsonUtil.object2Json(assetOuterRequest));
        assetChangeRecord.setGmtCreate(System.currentTimeMillis());
        assetChangeRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetChangeRecordDao.insert(assetChangeRecord);
        ManualStartActivityRequest manualStartActivityRequest = assetOuterRequest.getManualStartActivityRequest();
        if (Objects.isNull(manualStartActivityRequest)) {
            manualStartActivityRequest = new ManualStartActivityRequest();
        }
        // 其他设备
        if (assetOuterRequest.getAssetOthersRequest() != null) {

            manualStartActivityRequest.setBusinessId(assetOuterRequest.getAssetOthersRequest().getId());
        } else {
            manualStartActivityRequest.setBusinessId(assetOuterRequest.getAsset().getId());
        }
        manualStartActivityRequest.setAssignee(LoginUserUtil.getLoginUser().getId().toString());
        manualStartActivityRequest.setProcessDefinitionKey(AssetActivityTypeEnum.HARDWARE_CHANGE.getCode());
        ActionResponse actionResponse = activityClient.manualStartProcess(manualStartActivityRequest);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            return actionResponse == null ? ActionResponse.fail(RespBasicCode.BUSSINESS_EXCETION) : actionResponse;
        }
        return ActionResponse.success(assetChangeRecord.getId());
    }

    @Override
    public Integer updateAssetChangeRecord(AssetChangeRecordRequest request) throws Exception {
        AssetChangeRecord assetChangeRecord = requestConverter.convert(request, AssetChangeRecord.class);
        return assetChangeRecordDao.update(assetChangeRecord);
    }

    @Override
    public List<AssetChangeRecordResponse> findListAssetChangeRecord(AssetChangeRecordQuery query) throws Exception {
        List<AssetChangeRecord> assetChangeRecordList = assetChangeRecordDao.findQuery(query);
        // TODO
        List<AssetChangeRecordResponse> assetChangeRecordResponse = responseConverter.convert(assetChangeRecordList,
            AssetChangeRecordResponse.class);
        return assetChangeRecordResponse;
    }

    @Override
    public PageResult<AssetChangeRecordResponse> findPageAssetChangeRecord(AssetChangeRecordQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetChangeRecord(query));
    }

    @Override
    public List<Map<String, Object>> queryNetworkEquipmentById(Integer businessId) throws Exception {
        NetworkEquipmentFieldCompare networkEquipmentFieldCompare = AssetChangeRecordFactory.getAssetChangeRecordProcess(NetworkEquipmentFieldCompare.class);
        return networkEquipmentFieldCompare.compareCommonBusinessInfo(businessId);
    }

    @Override
    public List<Map<String, Object>> queryStorageEquipmentById(Integer businessId) throws Exception {
        StorageMediumFieldCompare storageMediumFieldCompare = AssetChangeRecordFactory.getAssetChangeRecordProcess(StorageMediumFieldCompare.class);
        return storageMediumFieldCompare.compareCommonBusinessInfo(businessId);
    }

    @Override
    public List<Map<String, Object>> querySafetyEquipmentById(Integer businessId) throws Exception {
        SafetyEquipmentFieldCompare safetyEquipmentFieldCompare = AssetChangeRecordFactory.getAssetChangeRecordProcess(SafetyEquipmentFieldCompare.class);
        return safetyEquipmentFieldCompare.compareCommonBusinessInfo(businessId);
    }

    @Override
    public List<Map<String, Object>> queryOtherEquipmentById(Integer businessId) throws Exception {
        OtherEquipmentFieldCompare otherEquipmentFieldCompare = AssetChangeRecordFactory.getAssetChangeRecordProcess(OtherEquipmentFieldCompare.class);
        return otherEquipmentFieldCompare.compareCommonBusinessInfo(businessId);
    }

    @Override
    public List<Map<String, Object>> queryComputerEquipmentById(Integer businessId) throws Exception {
        ComputerEquipmentFieldCompareImpl computerEquipmentFieldCompare = AssetChangeRecordFactory.getAssetChangeRecordProcess(ComputerEquipmentFieldCompareImpl.class);
        return computerEquipmentFieldCompare.compareCommonBusinessInfo(businessId);
    }
}
