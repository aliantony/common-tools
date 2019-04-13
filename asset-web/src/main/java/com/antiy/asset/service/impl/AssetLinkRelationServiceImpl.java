package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.AssetLinkedCountResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@Service
public class AssetLinkRelationServiceImpl extends BaseServiceImpl<AssetLinkRelation>
                                          implements IAssetLinkRelationService {

    private static final Logger                                         logger = LogUtils.get();

    @Resource
    private AssetLinkRelationDao                                        assetLinkRelationDao;
    @Resource
    AssetNetworkEquipmentDao                                            assetNetworkEquipmentDao;
    @Resource
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;
    @Resource
    private IAssetCategoryModelService                                  assetCategoryModelService;

    @Override
    public String saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        checkAssetIp(request, assetLinkRelation);
        assetLinkRelationDao.insert(assetLinkRelation);
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LABEL_INSERT.getName(), assetLinkRelation.getId(),
            null, assetLinkRelation, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_LINK_RELATION_INSERT.getName() + "{}", assetLinkRelation);
        return assetLinkRelation.getStringId();
    }

    /**
     * 检查IP信息
     * @param request
     * @param assetLinkRelation
     */
    private void checkAssetIp(AssetLinkRelationRequest request, AssetLinkRelation assetLinkRelation) {
        assetLinkRelation.setAssetId(DataTypeUtils.stringToInteger(request.getAssetId()));
        assetLinkRelation.setParentAssetId(DataTypeUtils.stringToInteger(request.getParentAssetId()));
        // 1.校验子资产IP是否可用
        List<String> assetAddress = assetLinkRelationDao.queryIpAddressByAssetId(request.getAssetId(), true,
            request.getAssetPort());
        ParamterExceptionUtils.isTrue(assetAddress.contains(request.getAssetIp()), "子资产IP已经存在绑定关系,无法再次绑定");

        // 2.校验父资产IP是否可用
        List<String> parentAssetAddress = assetLinkRelationDao.queryIpAddressByAssetId(request.getParentAssetId(), true,
            request.getParentAssetPort());
        ParamterExceptionUtils.isTrue(parentAssetAddress.contains(request.getParentAssetIp()), "父资产IP已经存在绑定关系,无法再次绑定");

        // 3.组装通联关系
        assetLinkRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLinkRelation.setGmtCreate(System.currentTimeMillis());
    }

    @Override
    public String updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        checkAssetIp(request, assetLinkRelation);
        assetLinkRelation.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetLinkRelation.setGmtModified(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LINK_RELATION_UPDATE.getName(),
            assetLinkRelation.getId(), null, assetLinkRelation, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_LINK_RELATION_UPDATE.getName() + "{}", assetLinkRelation);
        return assetLinkRelationDao.update(assetLinkRelation).toString();
    }

    @Override
    public List<AssetLinkRelationResponse> queryListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        List<AssetLinkRelation> assetLinkRelationList = assetLinkRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetLinkRelationList, AssetLinkRelationResponse.class);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        return new PageResult<AssetLinkRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetLinkRelation(query));
    }

    @Override
    public AssetLinkRelationResponse queryAssetLinkRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetLinkRelationResponse assetLinkRelationResponse = responseConverter.convert(
            assetLinkRelationDao.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())),
            AssetLinkRelationResponse.class);
        return assetLinkRelationResponse;
    }

    @Override
    public String deleteAssetLinkRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LINK_RELATION_DELETE.getName(),
            baseRequest.getId(), null, baseRequest, BusinessModuleEnum.SOFTWARE_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_LINK_RELATION_DELETE.getName() + "{}", baseRequest.getStringId());

        return assetLinkRelationDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public PageResult<AssetResponse> queryAssetPage(AssetQuery assetQuery) {
        // 已入网的资产
        assetQuery.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        if (StringUtils.isNotBlank(assetQuery.getPrimaryKey())) {
            // 查询已关联（并绑定了端口）的资产id及其自身,查询时需要排除
            List<String> assetIds = assetLinkRelationDao.queryLinkedAssetById(assetQuery.getPrimaryKey());
            if (CollectionUtils.isNotEmpty(assetIds)) {
                assetIds.add(assetQuery.getPrimaryKey());
                String[] ids = assetIds.toArray(new String[assetIds.size()]);
                if (ids.length > 0) {
                    assetQuery.setIds(ids);
                }
            } else {
                assetQuery.setIds(new String[] { assetQuery.getPrimaryKey() });
            }
        } else {
            // 查询已经在关系表中存在的资产,将其排除
            List<String> assetIds = assetLinkRelationDao.queryLinkedAssetWithoutId();
            if (CollectionUtils.isNotEmpty(assetIds)) {
                assetQuery.setIds(assetIds.toArray(new String[assetIds.size()]));
            }
        }
        List<AssetResponse> assetList = this.queryAssetList(assetQuery);
        if (assetList.size() <= 0) {
            return new PageResult<AssetResponse>(assetQuery.getPageSize(), 0, assetQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<AssetResponse>(assetQuery.getPageSize(), assetList.size(), assetQuery.getCurrentPage(),
            this.queryAssetList(assetQuery));
    }

    @Override
    public List<AssetResponse> queryAssetList(AssetQuery assetQuery) {
        List<Asset> assetResponseList = assetLinkRelationDao.queryAssetList(assetQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetResponse.class);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryLinekedRelationPage(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelationResponse> assetLinkRelationResponseList = this
            .queryLinekedRelationList(assetLinkRelationQuery);
        if (assetLinkRelationResponseList.size() <= 0) {
            return new PageResult<AssetLinkRelationResponse>(assetLinkRelationQuery.getPageSize(), 0,
                assetLinkRelationQuery.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<AssetLinkRelationResponse>(assetLinkRelationQuery.getPageSize(),
            assetLinkRelationResponseList.size(), assetLinkRelationQuery.getCurrentPage(),
            this.queryLinekedRelationList(assetLinkRelationQuery));
    }

    @Override
    public List<AssetLinkRelationResponse> queryLinekedRelationList(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelation> assetResponseList = assetLinkRelationDao
            .queryLinekedRelationList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkRelationResponse.class);
    }

    @Override
    public List<String> queryIpAddressByAssetId(String assetId, Boolean enable) throws Exception {
        return assetLinkRelationDao.queryIpAddressByAssetId(assetId, enable, null);
    }

    @Override
    public List<SelectResponse> queryPortById(QueryCondition queryCondition) {
        return getSelectResponses(assetNetworkEquipmentDao.findPortAmount(queryCondition.getPrimaryKey()),
            assetLinkRelationDao.findUsePort(queryCondition.getPrimaryKey()));
    }

    @Override
    public PageResult<AssetLinkedCountResponse> queryAssetLinkedCountPage(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        Map<String, String> category = assetCategoryModelService.getSecondCategoryMap();
        category.forEach((k, v) -> {
            try {
                if (v.equals("计算设备")) {
                    // 计算设备下所有品类型号
                    assetLinkRelationQuery.setPcCategoryModels(
                        assetCategoryModelService.findAssetCategoryModelIdsById(DataTypeUtils.stringToInteger(k)));
                } else if (v.equals("网络设备")) {
                    // 网络设备下所有品类型号
                    assetLinkRelationQuery.setNetCategoryModels(
                        assetCategoryModelService.findAssetCategoryModelIdsById(DataTypeUtils.stringToInteger(k)));
                }
            } catch (Exception e) {
                logger.error("获取{}子品类型号出错", v, e.getStackTrace());
            }

        });
        List<String> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode().toString());
        statusList.add(AssetStatusEnum.NET_IN.getCode().toString());
        assetLinkRelationQuery.setStatusList(statusList);
        List<AssetLinkedCountResponse> assetLinkedCountResponseList = this
            .queryAssetLinkedCountList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetLinkedCountResponseList)) {
            return new PageResult<AssetLinkedCountResponse>(assetLinkRelationQuery.getPageSize(), 0,
                assetLinkRelationQuery.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<AssetLinkedCountResponse>(assetLinkRelationQuery.getPageSize(),
            this.queryAssetLinkedCount(assetLinkRelationQuery), assetLinkRelationQuery.getCurrentPage(),
            assetLinkedCountResponseList);
    }

    private Integer queryAssetLinkedCount(AssetLinkRelationQuery assetLinkRelationQuery) {
        return assetLinkRelationDao.queryAssetLinkedCount(assetLinkRelationQuery);
    }

    @Override
    public List<AssetLinkedCountResponse> queryAssetLinkedCountList(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        assetLinkRelationQuery
            .setAreaIds(LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser()
                : Lists.newArrayList());
        List<AssetLinkedCount> assetResponseList = assetLinkRelationDao
            .queryAssetLinkedCountList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkedCountResponse.class);
    }

    @Override
    public List<AssetLinkRelationResponse> queryLinkedAssetListByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) {
        ParamterExceptionUtils.isBlank(assetLinkRelationQuery.getPrimaryKey(), "请选择资产");
        Integer portSize = assetLinkRelationDao
            .queryPortSize(DataTypeUtils.stringToInteger(assetLinkRelationQuery.getPrimaryKey()));
        List<Integer> portCount = Lists.newArrayList();
        for (int i = 1; i <= portSize; i++) {
            portCount.add(i);
        }
        List<AssetLinkRelation> assetResponseList = assetLinkRelationDao.queryLinkedAssetListByAssetId(
            DataTypeUtils.stringToInteger(assetLinkRelationQuery.getPrimaryKey()), portCount);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkRelationResponse.class);
    }

    public Integer queryLinkedCountAssetByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) {
        ParamterExceptionUtils.isBlank(assetLinkRelationQuery.getPrimaryKey(), "请选择资产");
        Integer portSize = assetLinkRelationDao
            .queryPortSize(DataTypeUtils.stringToInteger(assetLinkRelationQuery.getPrimaryKey()));
        List<Integer> portCount = Lists.newArrayList();
        for (int i = 1; i <= portSize; i++) {
            portCount.add(i);
        }
        return assetLinkRelationDao.queryLinkedCountAssetByAssetId(
            DataTypeUtils.stringToInteger(assetLinkRelationQuery.getPrimaryKey()), portCount);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryLinkedAssetPageByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelationResponse> assetLinkRelationResponseList = this
            .queryLinkedAssetListByAssetId(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetLinkRelationResponseList)) {
            return new PageResult<AssetLinkRelationResponse>(assetLinkRelationQuery.getPageSize(), 0,
                assetLinkRelationQuery.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<AssetLinkRelationResponse>(assetLinkRelationQuery.getPageSize(),
            this.queryLinkedCountAssetByAssetId(assetLinkRelationQuery), assetLinkRelationQuery.getCurrentPage(),
            assetLinkRelationResponseList);
    }

    /**
     * 获取未占用的端口
     * @param amount
     * @return
     */
    private List<SelectResponse> getSelectResponses(Integer amount, List<Integer> usePortList) {
        List<Integer> portList = new ArrayList<>();
        List<SelectResponse> selectResponseList;// 还原网络设备端口
        if (amount != null && amount > 1) {
            for (int i = 1; i <= amount; i++) {
                portList.add(i);
            }

            // 排除已占用的端口
            for (Integer usePort : usePortList) {
                portList.remove(usePort);
            }
        }

        selectResponseList = new ArrayList<>();
        for (Integer unUsePort : portList) {
            SelectResponse selectResponse = new SelectResponse();
            selectResponse.setValue(DataTypeUtils.integerToString(unUsePort));
            selectResponseList.add(selectResponse);
        }
        return selectResponseList;
    }

}
