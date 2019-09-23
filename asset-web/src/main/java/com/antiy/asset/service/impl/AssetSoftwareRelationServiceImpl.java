package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.antiy.asset.intergration.impl.CommandClientImpl;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.service.IRedisService;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.request.AssetSoftwareReportRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
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

    private void setOperationName(AssetSoftwareRelationResponse assetSoftwareRelationResponse) throws Exception {
        if (StringUtils.isNotEmpty(assetSoftwareRelationResponse.getOperationSystem())) {
            String[] ops = assetSoftwareRelationResponse.getOperationSystem().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            List<BaselineCategoryModelResponse> categoryOsResponseList = redisService.getAllSystemOs();
            for (String os : ops) {
                for (BaselineCategoryModelResponse categoryModelResponse : categoryOsResponseList) {
                    if (os.equals(categoryModelResponse.getStringId())) {
                        stringBuilder.append(categoryModelResponse.getName()).append(",");
                    }
                }
            }
            assetSoftwareRelationResponse.setOperationSystemName(
                stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(",")));
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
    public List<AssetSoftwareInstallResponse> queryInstalledList(InstallQuery query) throws Exception {
        // 查询资产已关联的软件列表
        return assetSoftwareRelationDao.queryInstalledList(query);
    }

    @Override
    public PageResult<AssetSoftwareInstallResponse> queryInstallableList(InstallQuery query) {
        // 模板黑白名单类型
        Integer nameListType = assetSoftwareRelationDao.queryNameListType(query);
        // 模板的软件
        List<Long> softwareIds = assetSoftwareRelationDao.querySoftwareIds(query);
        // 已安装的软件
        List<String> installedSoftIds = assetSoftwareRelationDao.queryInstalledList(query).stream()
            .map(AssetSoftwareInstallResponse::getSoftwareId).collect(Collectors.toList());
        // 模板是黑名单需排除黑名单中的软件,以及已经安装过的软件
        if (nameListType == 1 && !query.getIsBatch()) {
            installedSoftIds.stream().forEach(a -> {
                softwareIds.add(Long.parseLong(a));
            });
        }
        // 模板是白名单需排除白名单中已安装过的软件
        else if (nameListType == 2 && !query.getIsBatch()) {
            installedSoftIds.stream().forEach(a -> {
                softwareIds.remove(DataTypeUtils.stringToInteger(a));
            });
        }
        Integer count = assetSoftwareRelationDao.queryInstallableCount(query, nameListType, softwareIds,
            installedSoftIds);
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
            assetSoftwareRelationDao.queryInstallableList(query, nameListType, softwareIds, installedSoftIds));
    }

    @Override
    public Integer batchRelation(AssetSoftwareReportRequest softwareReportRequest) {
        List<String> assetIds = softwareReportRequest.getAssetId();
        List<Integer> softIds = softwareReportRequest.getSoftId();
        ParamterExceptionUtils.isEmpty(assetIds, "请选择资产");
        if (CollectionUtils.isNotEmpty(softIds)) {
            List<AssetSoftwareRelation> assetSoftwareRelationList = Lists.newArrayList();
            assetIds.stream().forEach(assetId -> {
                // 1.先删除旧的关系表
                assetSoftwareRelationDao.deleteSoftRealtion(assetId);
                // 2.插入新的关系
                softIds.stream().forEach(softId -> {
                    AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                    assetSoftwareRelation.setAssetId(assetId);
                    assetSoftwareRelation.setSoftwareId(DataTypeUtils.integerToString(softId));
                    assetSoftwareRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
                    assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                    assetSoftwareRelationList.add(assetSoftwareRelation);
                });
            });
            return assetSoftwareRelationDao.insertBatch(assetSoftwareRelationList);
        }
        return 0;
    }

    private Integer countByAssetId(Integer assetId) {
        return assetSoftwareRelationDao.countSoftwareByAssetId(assetId);
    }

}
