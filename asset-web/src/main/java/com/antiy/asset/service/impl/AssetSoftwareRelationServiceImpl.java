package com.antiy.asset.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareInstall;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.intergration.impl.CommandClientImpl;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.*;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.*;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

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
    private AssetCategoryModelDao                                               assetCategoryModelDao;
    @Resource
    private IAssetCategoryModelService                                          iAssetCategoryModelService;

    @Resource
    private IRedisService                                                       redisService;
    @Resource
    private AssetSoftwareDao                                                    assetSoftwareDao;
    @Resource
    private AssetDao                                                            assetDao;
    @Resource
    private CommandClientImpl                                                   commandClient;

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

    @Override
    public PageResult<AssetSoftwareRelationResponse> getSimpleSoftwarePageByAssetId(AssetSoftwareRelationQuery query) throws Exception {
        int count = countByAssetId(DataTypeUtils.stringToInteger(query.getAssetId()));
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), null);
        }
        List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao
            .getSimpleSoftwareByAssetId(query);
        List<AssetSoftwareRelationResponse> assetSoftwareResponseList = responseConverter
            .convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class);
        for (AssetSoftwareRelationResponse assetSoftwareRelationResponse : assetSoftwareResponseList) {
            setOperationName(assetSoftwareRelationResponse);
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetSoftwareResponseList);
    }

    private void setOperationName(AssetSoftwareRelationResponse assetSoftwareRelationResponse) throws Exception {
        if (StringUtils.isNotEmpty(assetSoftwareRelationResponse.getOperationSystem())) {
            String[] ops = assetSoftwareRelationResponse.getOperationSystem().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            List<LinkedHashMap> categoryOsResponseList = redisService.getAllSystemOs();
            for (String os : ops) {
                for (LinkedHashMap linkedHashMap : categoryOsResponseList) {
                    if (os.equals(linkedHashMap.get("stringId"))) {
                        stringBuilder.append((String) linkedHashMap.get("name")).append(",");
                    }
                }
            }
            assetSoftwareRelationResponse
                .setOperationSystemName(stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(",")));
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Integer countAssetBySoftId(Integer id) {
        return assetSoftwareRelationDao.countAssetBySoftId(id);
    }

    @Override
    public List<SelectResponse> findOS() throws Exception {
        List<String> osList = assetSoftwareRelationDao.findOS(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        List<LinkedHashMap> linkedHashMapList = redisService.getAllSystemOs();
        List<SelectResponse> result = new ArrayList<>();
        for (LinkedHashMap linkedHashMap : linkedHashMapList) {
            if (osList.contains(linkedHashMap.get("stringId"))) {
                SelectResponse selectResponse = new SelectResponse();
                selectResponse.setId((String) linkedHashMap.get("stringId"));
                selectResponse.setValue((String) linkedHashMap.get("name"));
                result.add(selectResponse);
            }
        }
        return result;
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
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSTALL_AUTO.getName(), null, "批量安装软件",
            assetSoftwareRelation, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.INSTALLABLE));
        LogUtils.info(logger, AssetEventEnum.SOFT_INSTALL_AUTO.getName() + " {}", assetSoftwareRelation);
        return assetSoftwareRelationDao.installAauto(assetSoftwareRelation);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ActionResponse installSoftware(AssetSoftwareRelationList assetSoftwareRelationList) throws Exception {
        // 人工安装
        ParamterExceptionUtils.isEmpty(assetSoftwareRelationList.getAssetInstallRequestList(), "安装信息不能为空！");
        if (checkInstalled(assetSoftwareRelationList)) {
            BusinessExceptionUtils.isTrue(false, "所选资产已经安装成功或在安装中,不允许再次操作!");
        }
        List<AssetSoftwareRelation> relationList = Lists.newArrayList();
        // 自动安装列表，用于下发给智甲
        List<AssetSoftwareRelation> autoInstallList = Lists.newArrayList();
        assetSoftwareRelationList.getAssetInstallRequestList().stream().forEach(assetInstallRequest -> {
            AssetSoftwareRelation relation = new AssetSoftwareRelation();
            // 关系表主键
            relation.setId(assetInstallRequest.getId());
            relation.setGmtModified(System.currentTimeMillis());
            relation.setModifyUser(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : null);
            if (InstallType.MANUAL.getCode().equals(assetSoftwareRelationList.getInstallType())) {
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
                relation.setInstallStatus(SoftInstallStatus.INSTALLING.getCode());
                autoInstallList.add(relation);
            }
            relationList.add(relation);
        });

        List<String> uuidList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(autoInstallList)) {
            autoInstallList.forEach(
                assetSoftwareRelation -> uuidList.add(assetDao.getUUIDByAssetId(assetSoftwareRelation.getAssetId())));
            List<String> noList = new ArrayList<>();
            noList.add(assetSoftwareRelationList.getSoftwareId());
            CommandRequest commandRequest = new CommandRequest();
            commandRequest.setType(ApiCommandType.softwareInstall);
            commandRequest.setNoList(noList);
            // 获取软件安装路径
            commandRequest.setUuidList(uuidList);
            // 远程调用安装指令
            BaseRequestOuter<CommandRequest> baseRequestOuter = new BaseRequestOuter<>();
            baseRequestOuter.setData(commandRequest);
            ActionResponse actionResponse = commandClient.executeCommand(baseRequestOuter);
            if (null == actionResponse
                || !RespBasicCode.SUCCESS.getResultCode().equals(actionResponse.getHead().getCode())) {
                LogUtils.info(logger, "远程调用安装指令" + " {}", relationList);
                throw new BusinessException("API服务未开启，请联系管理员");
            }
            // 更新安装状态 AssetSoftwareRelation
            // AssetSoftwareRelation condition = new AssetSoftwareRelation();
            // condition.setSoftwareId(assetSoftwareRelationList.getSoftwareId());
            // for (AssetSoftwareRelation softwareRelation : autoInstallList) {
            // condition.setAssetId(softwareRelation.getAssetId());
            // condition.setInstallStatus(SoftInstallStatus.INSTALLING.getCode());
            // condition.setGmtModified(System.currentTimeMillis());
            // if (LoginUserUtil.getLoginUser() != null) {
            // condition.setModifyUser(LoginUserUtil.getLoginUser().getId());
            // } else {
            // LogUtils.info(logger, AssetEventEnum.GET_USER_INOF.getName() + " {}", "失败");
            // }
            // assetSoftwareRelationDao.updateConfigStatusByAssetId(condition);
            // // 记录操作日志
            // LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSTALL.getName(), condition.getId(), "",
            // condition, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
            // }
        }

        // 更新关系表
        Integer count = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 记录操作日志和运行日志
                    LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFT_INSTALL.getName(),
                        DataTypeUtils.stringToInteger(assetSoftwareRelationList.getSoftwareId()),
                        assetSoftwareDao.getById(assetSoftwareRelationList.getSoftwareId()).getName(), relationList,
                        BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
                    LogUtils.info(logger, AssetEventEnum.ASSET_INSERT.getName() + " {}",
                        assetSoftwareRelationList.toString());
                    return assetSoftwareRelationDao.installSoftware(relationList);
                } catch (Exception e) {
                    LogUtils.info(logger, AssetEventEnum.SOFT_INSTALL.getName() + " {}", relationList);
                    transactionStatus.setRollbackOnly();
                }
                return 0;
            }
        });


        return ActionResponse.success();
    }

    /**
     * 幂等判断软件是否已经执行过安装操作
     * @param assetSoftwareRelationList
     */
    private boolean checkInstalled(AssetSoftwareRelationList assetSoftwareRelationList) {
        Integer result = 0;
        if (CollectionUtils.isNotEmpty(assetSoftwareRelationList.getAssetInstallRequestList())) {
            result = assetSoftwareRelationDao.checkInstalled(assetSoftwareRelationList.getSoftwareId(),
                assetSoftwareRelationList.getAssetInstallRequestList().stream().map(AssetInstallRequest::getAssetId)
                    .collect(Collectors.toList()));
        }
        return result > 0;
    }

    private Integer countByAssetId(Integer assetId) {
        return assetSoftwareRelationDao.countSoftwareByAssetId(assetId);
    }

    /**
     *
     * @param query
     * @return
     */
    @Override
    public PageResult<AssetSoftwareInstallResponse> queryInstallList(InstallQuery query) throws Exception {
        logger.info(LoginUserUtil.getLoginUser().toString());
        List<Integer> areaIdsList = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        query.setAreaIds(DataTypeUtils.integerArrayToStringArray(areaIdsList));
        List<Integer> statusList = new ArrayList<>();
        // 已入网
        statusList.add(AssetStatusEnum.NET_IN.getCode());
        query.setAssetStatusList(statusList);
        AssetCategoryModel assetCategoryModel = assetCategoryModelDao
            .getByName(AssetSecondCategoryEnum.COMPUTE_DEVICE.getMsg());
        List<Integer> categoryModelIdsById = iAssetCategoryModelService
            .findAssetCategoryModelIdsById(assetCategoryModel.getId());
        query.setCategoryModels(DataTypeUtils.integerListToStringList(categoryModelIdsById));
        // 获取软件列表
        AssetSoftware assetSoftware = assetSoftwareDao.getById(query.getSoftwareId());
        String operationSystem = assetSoftware.getOperationSystem();
        List<String> operationSystemList = Arrays.asList(operationSystem.split(","));
        query.setOperationSystems(operationSystemList);
        Integer count = assetSoftwareRelationDao.queryInstallCount(query);
        if (count != 0) {
            List<AssetSoftwareInstall> queryInstallList = assetSoftwareRelationDao.queryInstallList(query);
            // 处理安装状态和配置状态
            // processStatusData(queryInstallList);
            // 处理操作系统，排除操作系统不满足的列表
            /* List<AssetSoftwareInstall> adaptationResult = processOperationAdaptation(queryInstallList,
             * query.getSoftwareId()); */
            // 进行分页
            /* List<AssetSoftwareInstall> pageResult = processPage(queryInstallList, query.getPageSize(),
             * query.getPageOffset()); */
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                responseInstallConverter.convert(queryInstallList, AssetSoftwareInstallResponse.class));
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), null);
    }

    @Override
    public Integer updateAssetReleation(AssetRelationSoftRequest assetRelationSoftRequest) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
        assetSoftwareRelation.setInstallStatus(SoftInstallStatus.UNINSTALLED.getCode());
        assetSoftwareRelation.setAssetId(assetRelationSoftRequest.getAssetId());
        assetSoftwareRelation.setSoftwareId(assetRelationSoftRequest.getSoftId());
        // 基准验证结束，更新软硬件关系表中的配置状态为已配置
        AssetSoftwareRelation softwareRelation = new AssetSoftwareRelation();
        softwareRelation.setSoftwareId(assetRelationSoftRequest.getSoftId());
        softwareRelation.setAssetId(assetRelationSoftRequest.getAssetId());
        if (!Objects.isNull(LoginUserUtil.getLoginUser())) {
            softwareRelation.setModifyUser(LoginUserUtil.getLoginUser().getId());
        }
        softwareRelation.setGmtModified(System.currentTimeMillis());
        softwareRelation.setInstallStatus(SoftInstallStatus.UNINSTALLED.getCode());
        assetSoftwareRelationDao.updateConfigStatusByAssetId(softwareRelation);

        return assetSoftwareRelationDao.updateByAssetId(assetSoftwareRelation);
    }

    // private List<AssetSoftwareInstall> processPage(List<AssetSoftwareInstall> adaptationResult, int pageSize,
    // int pageOffset) {
    // if (pageOffset >= adaptationResult.size()) {
    // return new ArrayList<>();
    // }
    // if (pageSize == -1) {
    // return adaptationResult;
    // }
    // int max = Math.min((pageOffset + pageSize), adaptationResult.size());
    // List<AssetSoftwareInstall> assetSoftwareInstallList = new ArrayList<>();
    // for (int i = pageOffset; i < max; i++) {
    // assetSoftwareInstallList.add(adaptationResult.get(i));
    // }
    // return assetSoftwareInstallList;
    // }

    // private List<AssetSoftwareInstall> processOperationAdaptation(List<AssetSoftwareInstall> queryInstallList,
    // String id) throws Exception {
    // AssetSoftware assetSoftware = assetSoftwareDao.getById(id);
    // String operationSystem = assetSoftware.getOperationSystem();
    // String[] operationSystemArray = operationSystem.split(",");
    // List<String> operationSystemList = Arrays.asList(operationSystemArray);
    // List<AssetSoftwareInstall> result = new ArrayList<>();
    // for (AssetSoftwareInstall assetSoftwareInstall : queryInstallList) {
    // if (operationSystemList.contains(assetSoftwareInstall.getOperationSystem())) {
    // result.add(assetSoftwareInstall);
    // }
    // }
    // return result;
    // }

    // /**
    // * 安装方式为null时 为未安装状态 配置方式为null时 为未配置状态 进行处理方便前端进行展示
    // * @param queryInstallList
    // */
    // private void processStatusData(List<AssetSoftwareInstall> queryInstallList) {
    // for (AssetSoftwareInstall assetSoftwareInstall : queryInstallList) {
    // if (assetSoftwareInstall.getInstallStatus() == null) {
    // assetSoftwareInstall.setInstallStatus(SoftInstallStatus.WAIT_CONFIGURE.getCode());
    // }
    // }
    // }

}
