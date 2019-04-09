package com.antiy.asset.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.antiy.asset.dao.AssetOperationRecordDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.dao.SchemeDao;
import com.antiy.asset.entity.*;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.PageResult;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 资产软件关系信息 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Transactional(rollbackFor = RuntimeException.class)
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
    private SchemeDao schemeDao;
    @Resource
    private AssetOperationRecordDao assetOperationRecordDao;

    @Override
    public Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_ASSET_RELATION_INSERT.getName(), null, null,
            assetSoftwareRelation, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.SOFT_ASSET_RELATION_INSERT.getName() + " {}", assetSoftwareRelation);
        assetSoftwareRelationDao.insert(assetSoftwareRelation);
        return assetSoftwareRelation.getId();
    }

    @Override
    public Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        // TODO 添加修改人信息
        assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
        assetSoftwareRelation.setGmtModified(System.currentTimeMillis());
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_ASSET_RELATION_UPDATE.getName(), null, null,
            assetSoftwareRelation, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.SOFT_ASSET_RELATION_UPDATE.getName() + " {}", assetSoftwareRelation);
        return assetSoftwareRelationDao.update(assetSoftwareRelation);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao.findQuery(query);
        List<AssetSoftwareRelationResponse> assetSoftwareRelationResponse = responseConverter
            .convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class);
        return assetSoftwareRelationResponse;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSoftwareRelation(query));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<AssetSoftwareResponse> getSoftByAssetId(Integer assetId) {
        List<AssetSoftware> assetSoftwareRelationList = assetSoftwareRelationDao.getSoftByAssetId(assetId);
        List<AssetSoftwareResponse> assetSoftwareRelationResponse = responseSoftConverter
            .convert(assetSoftwareRelationList, AssetSoftwareResponse.class);
        return assetSoftwareRelationResponse;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public PageResult<AssetSoftwareRelationResponse> getSimpleSoftwarePageByAssetId(AssetSoftwareRelationQuery query) {
        int count = countByAssetId(DataTypeUtils.stringToInteger(query.getAssetId()));
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), null);
        }
        List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao
            .getSimpleSoftwareByAssetId(query);
        List<AssetSoftwareRelationResponse> assetSoftwareResponseList = responseConverter
            .convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetSoftwareResponseList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Integer countAssetBySoftId(Integer id) {
        return assetSoftwareRelationDao.countAssetBySoftId(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<String> findOS() throws Exception {
        return assetSoftwareRelationDao.findOS(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
    }

    @Override
    public Integer changeSoftwareStatus(Map<String, Object> map) throws Exception {
        // 记录操作日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_ASSET_STATUS_CHANGE.getName(), null, null, map,
            BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
        return assetSoftwareRelationDao.changeSoftwareStatus(map);
    }

    @Override
    public Integer installArtificial(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> assetSoftwareRelation = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSTALL_MANUAL.getName(), null, null,
            assetSoftwareRelation, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.INSTALLABLE));
        LogUtils.info(logger, AssetEventEnum.SOFT_INSTALL_MANUAL.getName() + " {}", assetSoftwareRelation);
        return assetSoftwareRelationDao.installArtificial(assetSoftwareRelation);
    }

    @Override
    public Integer installAauto(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> assetSoftwareRelation = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        // TODO 下发智甲安装
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSTALL_AUTO.getName(), null, null,
            assetSoftwareRelation, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.INSTALLABLE));
        LogUtils.info(logger, AssetEventEnum.SOFT_INSTALL_AUTO.getName() + " {}", assetSoftwareRelation);
        return assetSoftwareRelationDao.installAauto(assetSoftwareRelation);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void installSoftware(AssetSoftwareRelationList assetSoftwareRelationList) {
        // 未配置、配置中资产id列表
        List<String> assetIds = assetSoftwareRelationList.getAssetInstallRequestList().stream()
            .filter(a -> (a.getConfigureStatus().equals(ConfigureStatusEnum.NOCONFIGURE.getCode())
                          || a.getConfigureStatus().equals(ConfigureStatusEnum.CONFIGURING.getCode())))
            .map(AssetInstallRequest::getAssetId).collect(Collectors.toList());
        BusinessExceptionUtils.isTrue(assetIds.size() <= 0, "存在未配置或正在配置中的资产，安装失败，请检查！");
        List<AssetSoftwareRelation> relationList = Lists.newArrayList();
        // 自动安装列表，用于下发给智甲
        List<AssetSoftwareRelation> autoInstallList = Lists.newArrayList();
        // 人工安装
        ParamterExceptionUtils.isEmpty(assetSoftwareRelationList.getAssetInstallRequestList(), "安装信息不能为空！");
        assetSoftwareRelationList.getAssetInstallRequestList().stream().forEach(assetInstallRequest -> {
            AssetSoftwareRelation relation = new AssetSoftwareRelation();
            // 关系表主键
            relation.setId(assetInstallRequest.getId());
            relation.setGmtModified(System.currentTimeMillis());
            relation.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
            if (assetSoftwareRelationList.getInstallType().equals(InstallType.MANUAL.getCode())) {
                // 人工安装
                relation.setAssetId(assetInstallRequest.getAssetId());
                relation.setInstallType(InstallType.MANUAL.getCode());
                relation.setInstallStatus(assetSoftwareRelationList.getInstallStatus());
                relation.setInstallTime(assetSoftwareRelationList.getInstallTime());
            } else {
                // 自动安装
                relation.setAssetId(assetInstallRequest.getAssetId());
                relation.setSoftwareId(assetSoftwareRelationList.getSoftwareId());
                relation.setInstallType(InstallType.AUTOMATIC.getCode());
                relation.setInstallStatus(InstallStatus.INSTALLING.getCode());
                autoInstallList.add(relation);
            }
            relationList.add(relation);
        });
        // 更新关系表
        Integer count = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSTALL.getName(), null, null,
                        relationList, BusinessModuleEnum.SOFTWARE_ASSET, null));
                    return assetSoftwareRelationDao.installSoftware(relationList);
                } catch (Exception e) {
                    LogUtils.info(logger, AssetEventEnum.SOFT_INSTALL.getName() + " {}", relationList);
                    transactionStatus.setRollbackOnly();
                }
                return 0;
            }
        });
        if (CollectionUtils.isNotEmpty(autoInstallList)) {
            // TODO 下发智甲安装
        }
    }

    private Integer countByAssetId(Integer assetId) {
        return assetSoftwareRelationDao.countSoftwareByAssetId(assetId);
    }

    /**
     *
     * @param query
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public PageResult<AssetSoftwareInstallResponse> queryInstallList(InstallQuery query) throws Exception {
        List<Integer> areaIdsList = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        query.setAreaIds(DataTypeUtils.integerArrayToStringArray(areaIdsList));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        query.setAssetStatusList(statusList);
        Integer count = assetSoftwareRelationDao.queryInstallCount(query);
        if (count != 0) {
            List<AssetSoftwareInstall> queryInstallList = assetSoftwareRelationDao.queryInstallList(query);
            processStatusData(queryInstallList);
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                responseInstallConverter.convert(queryInstallList, AssetSoftwareInstallResponse.class));
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
    }

    @Override
    public Integer changeSoftConfiguration(AssetStatusJumpRequst assetSoftwareRelationRequest) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation ();
        assetSoftwareRelation.setAssetId ( assetSoftwareRelationRequest.getAssetId ());
        String softwareId = assetSoftwareRelationRequest.getSoftId ();
        assetSoftwareRelation.setSoftwareId (softwareId);
        assetSoftwareRelation.setConfigureStatus (ConfigureStatusEnum.CONFIGURED.getCode ());
        SchemeRequest schemeRequest = assetSoftwareRelationRequest.getSchemeRequest ();

        Scheme scheme = BeanConvert.convertBean(schemeRequest, Scheme.class);
        // 写入方案
        if (scheme.getFileInfo() != null && scheme.getFileInfo().length() > 0) {
            JSONObject.parse(HtmlUtils.htmlUnescape(scheme.getFileInfo()));
        }
        scheme.setAssetNextStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
        scheme.setAssetId(softwareId);
        schemeDao.insert(scheme);

        // 2.保存流程
        AssetOperationRecord assetOperationRecord = new AssetOperationRecord();
        assetOperationRecord.setTargetObjectId(softwareId);
        assetOperationRecord.setSchemeId(scheme.getId());
        assetOperationRecord.setTargetType(AssetOperationTableEnum.SOFTWARE.getCode());
        assetOperationRecord.setTargetStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
        assetOperationRecord.setContent(AssetEventEnum.SOFT_CONFIG.getName());
        assetOperationRecord.setProcessResult(1);
        assetOperationRecord.setOriginStatus(SoftwareStatusEnum.ALLOW_INSTALL.getCode());
        assetOperationRecord.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetOperationRecord.setOperateUserName(LoginUserUtil.getLoginUser().getName());
        assetOperationRecord.setGmtCreate(System.currentTimeMillis());
        assetOperationRecordDao.insert(assetOperationRecord);

        // 写入业务日志
        LogHandle.log(assetOperationRecord.toString(), AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName(),
                AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(LogUtils.get (this.getClass ()), AssetEventEnum.ASSET_OPERATION_RECORD_INSERT.getName() + " {}",
                assetOperationRecord.toString());

        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_ASSET_RELATION_UPDATE.getName(), null, null,
            assetSoftwareRelation, BusinessModuleEnum.SOFTWARE_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.SOFT_CONFIG.getName() + " {}", assetSoftwareRelation);
        return assetSoftwareRelationDao.updateByAssetId(assetSoftwareRelation);
    }

    /**
     * 安装方式为null时 为未安装状态 配置方式为null时 为未配置状态 进行处理方便前端进行展示
     * @param queryInstallList
     */
    private void processStatusData(List<AssetSoftwareInstall> queryInstallList) {
        for (AssetSoftwareInstall assetSoftwareInstall : queryInstallList) {
            if (assetSoftwareInstall.getInstallStatus() == null) {
                assetSoftwareInstall.setInstallStatus(4);
            }
            if (assetSoftwareInstall.getConfigureStatus() == null) {
                assetSoftwareInstall.setConfigureStatus("1");
            }
        }
    }
}
