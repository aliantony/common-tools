package com.antiy.asset.service.impl;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.impl.CommandClientImpl;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.RequestParamValidateException;
import com.antiy.common.utils.*;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> 资产软件关系信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AssetSoftwareRelationServiceImpl extends BaseServiceImpl<AssetSoftwareRelation>
                                              implements IAssetSoftwareRelationService {
    private Logger                                                              logger = LogUtils.get(this.getClass());

    @Resource
    private AssetSoftwareRelationDao                                            assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetSoftwareRelationRequest, AssetSoftwareRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationResponse> responseConverter;
    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareResponse>                 responseSoftConverter;
    @Resource
    private BaseConverter<AssetSoftwareInstall, AssetSoftwareInstallResponse>   responseInstallConverter;
    @Resource
    private TransactionTemplate                                                 transactionTemplate;
    @Resource
    private IRedisService                                                       redisService;
    @Resource
    private AssetSoftwareDao                                                    assetSoftwareDao;
    @Resource
    private AssetDao                                                            assetDao;
    @Resource
    private CommandClientImpl                                                   commandClient;
    @Resource
    private BaseLineClient                                                      baseLineClient;
    @Resource
    private AesEncoder                                                          aesEncoder;
    @Resource
    private AssetHardSoftLibDao                                                 assetHardSoftLibDao;
    @Resource
    private AssetOperationRecordDao assetOperationRecordDao;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Integer countAssetBySoftId(Integer id) {
        return assetSoftwareRelationDao.countAssetBySoftId(id);
    }

    @Override
    public List<SelectResponse> findOS() throws Exception {
        List<String> osList = assetSoftwareRelationDao.findOS(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        List<BaselineCategoryModelResponse> categoryModelResponseList = redisService.getAllSystemOs();
        List<SelectResponse> result = new ArrayList<>();
        for (BaselineCategoryModelResponse categoryModelResponse : categoryModelResponseList) {
            if (osList.contains(categoryModelResponse.getStringId())) {
                SelectResponse selectResponse = new SelectResponse();
                selectResponse.setId(categoryModelResponse.getStringId());
                selectResponse.setValue(categoryModelResponse.getName());
                result.add(selectResponse);
            }
        }
        return result;
    }

    @Override
    public List<AssetSoftwareInstallResponse> queryInstalledList(QueryCondition query) {
        // 查询资产已关联的软件列表
        return assetSoftwareRelationDao.queryInstalledList(query);
    }
    @Override
    public PageResult<AssetSoftwareInstallResponse> queryInstalledPageList(ObjectQuery query) {
        // 查询资产已关联的软件列表
        Integer count = assetSoftwareRelationDao.countSoftwareByAssetId(Integer.valueOf(query.getPrimaryKey()));
        List<AssetSoftwareInstallResponse> assetSoftwareInstallResponses = assetSoftwareRelationDao.queryInstalledPageList(query);
        if(count==0){
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetSoftwareInstallResponses);
    }
    @Override
    public PageResult<AssetSoftwareInstallResponse> queryInstallableList(InstallQuery query) {
        // 模板黑白名单类型
        Integer nameListType = assetSoftwareRelationDao.queryNameListType(query);
        // 模板黑白名单类型
        String os = assetSoftwareRelationDao.queryOs(query);
        // 模板的软件
        List<Long> softwareIds = assetSoftwareRelationDao.querySoftwareIds(query);
        // 已安装的软件
        List<String> installedSoftIds = assetSoftwareRelationDao.queryInstalledList(query).stream()
            .map(AssetSoftwareInstallResponse::getSoftwareId).collect(Collectors.toList());
        // 模板是黑名单需排除黑名单中的软件,以及已经安装过的软件
        if (Objects.equals(nameListType, NameListTypeEnum.BLACK.getCode())) {
            installedSoftIds.stream().forEach(a -> {
                softwareIds.add(Long.parseLong(a));
            });
        }
        // 模板是白名单需排除白名单中已安装过的软件
        else if (Objects.equals(nameListType, NameListTypeEnum.WHITE.getCode()) && !query.getIsBatch()) {
            installedSoftIds.stream().forEach(a -> {
                softwareIds.remove(Long.parseLong(a));
            });
        }
        if (Objects.equals(nameListType, NameListTypeEnum.WHITE.getCode()) && CollectionUtils.isEmpty(softwareIds)) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        Integer count = assetSoftwareRelationDao.queryInstallableCount(query, nameListType, softwareIds,
            installedSoftIds, os);
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
            assetSoftwareRelationDao.queryInstallableList(query, nameListType, softwareIds, installedSoftIds, os));
    }

    @Override
    public Integer batchRelation(AssetSoftwareReportRequest softwareReportRequest) {
        int result = 0;
        List<String> assetIds = softwareReportRequest.getAssetId();
        List<Long> softIds = softwareReportRequest.getSoftId();
        ParamterExceptionUtils.isEmpty(assetIds, "请选择资产");
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(softIds)) {
            checkSoftwareCompliance(softIds.toArray(new Long[softIds.size()]));
            List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
            assetIds.stream().forEach(assetId -> {
                map.put(assetId, assetSoftwareRelationDao.getContent(assetId, softIds));
                // 1.先删除旧的关系表
                assetSoftwareRelationDao.deleteSoftRealtion(assetId, softIds);
                // 2.插入新的关系
                softIds.stream().forEach(softId -> {
                    AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                    assetSoftwareRelation.setAssetId(assetId);
                    assetSoftwareRelation.setSoftwareId(softId);
                    assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                    assetSoftwareRelationList.add(assetSoftwareRelation);
                });
            });
            result = assetSoftwareRelationDao.insertBatch(assetSoftwareRelationList);
        }

        // ------------------对接配置模块------------------start
        String[] bids = softwareReportRequest.getManualStartActivityRequest().getFormData().get("baselineConfigUserId")
            .toString().split(",");
        StringBuilder sb = new StringBuilder();
        Arrays.stream(bids).forEach(bid -> {
            sb.append(aesEncoder.decode(bid, LoginUserUtil.getLoginUser().getUsername())).append(",");
        });
        Map formData = softwareReportRequest.getManualStartActivityRequest().getFormData();
        formData.put("baselineConfigUserId", sb.toString());
        List<BaselineWaitingConfigRequest> baselineWaitingConfigRequestList = Lists.newArrayList();
        assetIds.stream().forEach(assetId -> {
            BaselineWaitingConfigRequest baselineWaitingConfigRequest = new BaselineWaitingConfigRequest();
            baselineWaitingConfigRequest.setAssetId(DataTypeUtils.stringToInteger(assetId));
            Integer installType = assetDao.queryInstallType(assetId);
            if (Objects.isNull(installType)) {
                baselineWaitingConfigRequest.setCheckType(InstallType.AUTOMATIC.getCode());
            } else {
                baselineWaitingConfigRequest.setCheckType(installType);
            }
            baselineWaitingConfigRequest.setConfigStatus(1);
            baselineWaitingConfigRequest.setCreateUser(LoginUserUtil.getLoginUser().getId());
            baselineWaitingConfigRequest.setReason(map.get(assetId));
            baselineWaitingConfigRequest.setSource(2);
            baselineWaitingConfigRequest.setFormData(new HashMap(formData));
            baselineWaitingConfigRequest.setBusinessId(assetId + "&1&" + assetId);
            baselineWaitingConfigRequest
                .setAdvice((String) softwareReportRequest.getManualStartActivityRequest().getFormData().get("memo"));
            baselineWaitingConfigRequestList.add(baselineWaitingConfigRequest);
        });
        ActionResponse actionResponse = baseLineClient.baselineConfig(baselineWaitingConfigRequestList);



        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {

            BusinessExceptionUtils.isTrue(false, "调用配置模块出错");
        }
        // ------------------对接配置模块------------------end
        //记录操作日志
        List<String> assetIdList = softwareReportRequest.getAssetId();
        List<Asset> assetList=assetDao.getByAssetIds(assetIdList);
        for(Asset asset:assetList){
            LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_SOFTWARE_RELATION.getName(),asset.getId(),
                    asset.getNumber(), asset, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NET_IN));
        }
        //操作记录
        List<AssetOperationRecord> recordList=new ArrayList<>();
        assetList.forEach(asset -> {
            AssetOperationRecord record=new AssetOperationRecord();
            record.setTargetObjectId(asset.getId().toString());
            record.setOriginStatus(asset.getAssetStatus());
            record.setTargetStatus(AssetStatusEnum.IN_CHANGE.getCode());
            record.setNeedVulScan(0);
            record.setContent(AssetFlowEnum.CHANGE.getMsg());
            record.setOperateUserId(LoginUserUtil.getLoginUser().getId());
            record.setOperateUserName(LoginUserUtil.getLoginUser().getName());
            record.setGmtCreate(System.currentTimeMillis());
            record.setCreateUser(LoginUserUtil.getLoginUser().getId());
            record.setStatus(1);
            recordList.add(record);
        });
        assetOperationRecordDao.insertBatch(recordList);

        //更改资产状态
        assetDao.updateAssetBatch(assetIds.stream().map(v->{
            Asset asset=new Asset();
            asset.setAssetStatus(AssetStatusEnum.IN_CHANGE.getCode());
            asset.setId(Integer.valueOf(v));
            asset.setModifyUser(LoginUserUtil.getLoginUser().getId());
            asset.setGmtModified(System.currentTimeMillis());
            return asset;
        }).collect(Collectors.toList()));

        return result;
    }

    private void checkSoftwareCompliance(Long... softBusinessId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("isStorage", "1");
        for (long id : softBusinessId) {
            map.put("businessId", String.valueOf(id));
            int count = assetHardSoftLibDao.countByWhere(map);
            if (count == 0) {
                throw new RequestParamValidateException("关联的软件部分不存在或未入库,请刷新并重新选择");
            }
        }
    }

}
