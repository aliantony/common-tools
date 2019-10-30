package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareInstall;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.intergration.BaseLineClient;
import com.antiy.asset.intergration.impl.CommandClientImpl;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.enums.NameListTypeEnum;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.request.BaselineWaitingConfigRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.*;
import com.antiy.common.encoder.AesEncoder;
import com.antiy.common.utils.*;
import com.google.common.collect.Lists;

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
        if (CollectionUtils.isNotEmpty(softIds)) {
            List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
            assetIds.stream().forEach(assetId -> {
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

        String nextStepUserId = aesEncoder.decode(
            (String) softwareReportRequest.getManualStartActivityRequest().getFormData().get("baselineConfigUserId"),
            LoginUserUtil.getLoginUser().getUsername());
        Map formData = softwareReportRequest.getManualStartActivityRequest().getFormData();
        formData.put("baselineConfigUserId", nextStepUserId);
        // ------------------对接配置模块------------------start
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
            baselineWaitingConfigRequest.setOperator(DataTypeUtils.stringToInteger(nextStepUserId));
            baselineWaitingConfigRequest.setReason("资产变更");
            baselineWaitingConfigRequest.setSource(2);
            baselineWaitingConfigRequest.setFormData(formData);
            baselineWaitingConfigRequest.setBusinessId(assetId + "&1&" + assetId);
            baselineWaitingConfigRequestList.add(baselineWaitingConfigRequest);
        });
        ActionResponse actionResponse = baseLineClient.baselineConfig(baselineWaitingConfigRequestList);
        if (null == actionResponse
            || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
            BusinessExceptionUtils.isTrue(false, "调用配置模块出错");
        }
        // ------------------对接配置模块------------------end

        return result;
    }
}
