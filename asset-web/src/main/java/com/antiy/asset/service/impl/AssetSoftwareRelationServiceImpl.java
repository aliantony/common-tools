package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareInstall;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.enums.ConfigureStatusEnum;
import com.antiy.asset.vo.enums.InstallStatus;
import com.antiy.asset.vo.enums.InstallType;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetInstallRequest;
import com.antiy.asset.vo.request.AssetSoftwareRelationList;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.BusinessExceptionUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
@Service
public class AssetSoftwareRelationServiceImpl extends BaseServiceImpl<AssetSoftwareRelation>
                                              implements IAssetSoftwareRelationService {

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

    @Override
    public Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
        assetSoftwareRelationDao.insert(assetSoftwareRelation);
        return assetSoftwareRelation.getId();
    }

    @Override
    public Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        // TODO 添加修改人信息
        assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
        assetSoftwareRelation.setGmtModified(System.currentTimeMillis());
        return assetSoftwareRelationDao.update(assetSoftwareRelation);
    }

    @Override
    public List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        List<AssetSoftwareRelation> assetSoftwareRelationList = assetSoftwareRelationDao.findQuery(query);
        List<AssetSoftwareRelationResponse> assetSoftwareRelationResponse = responseConverter
            .convert(assetSoftwareRelationList, AssetSoftwareRelationResponse.class);
        return assetSoftwareRelationResponse;
    }

    @Override
    public PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSoftwareRelation(query));
    }

    @Override
    public List<AssetSoftwareResponse> getSoftByAssetId(Integer assetId) {
        List<AssetSoftware> assetSoftwareRelationList = assetSoftwareRelationDao.getSoftByAssetId(assetId);
        List<AssetSoftwareResponse> assetSoftwareRelationResponse = responseSoftConverter
            .convert(assetSoftwareRelationList, AssetSoftwareResponse.class);
        return assetSoftwareRelationResponse;
    }

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

    @Override
    public Integer countAssetBySoftId(Integer id) {
        return assetSoftwareRelationDao.countAssetBySoftId(id);
    }

    @Override
    public List<String> findOS() throws Exception {
        return assetSoftwareRelationDao.findOS(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
    }

    @Override
    public Integer changeSoftwareStatus(Map<String, Object> map) throws Exception {
        return assetSoftwareRelationDao.changeSoftwareStatus(map);
    }

    @Override
    public Integer installArtificial(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> assetSoftwareRelation = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        return assetSoftwareRelationDao.installArtificial(assetSoftwareRelation);
    }

    @Override
    public Integer installAauto(List<AssetSoftwareRelationRequest> assetSoftwareRelationList) {
        List<AssetSoftwareRelation> assetSoftwareRelation = BeanConvert.convert(assetSoftwareRelationList,
            AssetSoftwareRelation.class);
        // TODO 下发智甲安装
        return assetSoftwareRelationDao.installAauto(assetSoftwareRelation);
    }

    @Override
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
                    return assetSoftwareRelationDao.installSoftware(relationList);
                } catch (Exception e) {
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
    public Integer changeSoftConfiguration(AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = BeanConvert.convertBean (assetSoftwareRelationRequest, AssetSoftwareRelation.class);
        assetSoftwareRelation.setConfigureStatus (ConfigureStatusEnum.CONFIGURED.getCode ());
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
