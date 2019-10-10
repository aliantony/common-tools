package com.antiy.asset.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.dao.AssetNetworkEquipmentDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.AssetLinkedCount;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.request.SysArea;
import com.antiy.asset.vo.request.UseableIpRequest;
import com.antiy.asset.vo.response.*;
import com.antiy.biz.util.RedisKeyUtil;
import com.antiy.biz.util.RedisUtil;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.exception.BusinessException;
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
    private AssetNetworkEquipmentDao                                    assetNetworkEquipmentDao;
    @Resource
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;
    @Resource
    private IAssetService                                               assetService;
    @Resource
    private AssetDao                                                    assetDao;
    @Resource
    private RedisUtil                                                   redisUtil;

    // private CategoryValiEntity func(CategoryValiEntity category) {
    // categoryVali = assetCategoryDao.getNameByCtegoryId(category.getParentId());
    // if (categoryVali.getParentId() != 2) {
    // categoryVali = func(categoryVali);
    // }
    // return categoryVali;
    // }

    @Override
    public Boolean saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        checkAssetIp(request, assetLinkRelation);
        assetLinkRelationDao.insert(assetLinkRelation);
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LINK_RELATION_INSERT.getName(),
            DataTypeUtils.stringToInteger(request.getAssetId()), assetDao.getById(request.getAssetId()).getNumber(),
            assetLinkRelation, BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
        LogUtils.info(logger, AssetEventEnum.ASSET_LINK_RELATION_INSERT.getName() + "{}", assetLinkRelation);
        return StringUtils.isNotBlank(assetLinkRelation.getStringId()) ? true : false;
    }

    // private void validate(AssetLinkRelationRequest request, QueryCondition queryCondition, CategoryType categoryType,
    // CategoryValiEntity category) {
    // if (category.getName().equals("计算设备")) {
    // categoryType.setPc(true);
    // UseableIpRequest useableIpRequest = new UseableIpRequest();
    // useableIpRequest.setAssetId(request.getParentAssetId());
    // useableIpRequest.setCategoryType(categoryType);
    // List<String> list = this.queryUseableIp(useableIpRequest);
    // if (!list.contains(request.getParentAssetIp())) {
    // throw new RequestParamValidateException("必须选定下拉框中的IP");
    // }
    // } else {
    // categoryType.setNet(true);
    // UseableIpRequest useableIpRequest = new UseableIpRequest();
    // useableIpRequest.setAssetId(request.getParentAssetId());
    // useableIpRequest.setCategoryType(categoryType);
    // List<String> list = this.queryUseableIp(useableIpRequest);
    // if (!list.contains(request.getParentAssetIp())) {
    // throw new RequestParamValidateException("必须选定下拉框中的IP");
    // }
    // queryCondition.setPrimaryKey(request.getParentAssetId());
    // List<SelectResponse> selectResponses = this.queryPortById(queryCondition);
    // List<String> valueList = new ArrayList<>();
    // for (SelectResponse selectResponse : selectResponses) {
    // valueList.add(selectResponse.getValue());
    // }
    // if (!valueList.contains(request.getParentAssetPort())) {
    // throw new RequestParamValidateException("必须选定下拉框中的网口");
    // }
    // }
    // }

    /**
     * 检查IP信息
     * @param request
     * @param assetLinkRelation
     */
    public void checkAssetIp(AssetLinkRelationRequest request, AssetLinkRelation assetLinkRelation) {
        assetLinkRelation.setAssetId(request.getAssetId());
        assetLinkRelation.setParentAssetId(request.getParentAssetId());
        // 1.校验子资产IP是否可用
        Integer count = assetLinkRelationDao.checkIp(request.getAssetId(), request.getAssetIp(),
            request.getAssetPort());
        if (count > 0) {
            throw new BusinessException("所选设备IP已存在绑定关系，请勿重复设置");
        }
        // 2.校验父资产IP是否可用
        count = assetLinkRelationDao.checkIp(request.getParentAssetId(), request.getAssetPort(),
            request.getParentAssetPort());
        if (count > 0) {
            throw new BusinessException("关联设备IP已存在绑定关系，请勿重复设置");
        }

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
        String assetId = assetLinkRelationDao.getById(baseRequest.getStringId()).getAssetId();
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LINK_RELATION_DELETE.getName(),
            DataTypeUtils.stringToInteger(assetId), assetDao.getById(assetId).getNumber(), baseRequest,
            BusinessModuleEnum.HARD_ASSET, BusinessPhaseEnum.NONE));
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
    public ActionResponse saveAssetLinkRelationList(List<AssetLinkRelationRequest> assetLinkRelationRequestList) {
        List<AssetLinkRelation> assetLinkRelationList = BeanConvert.convert(assetLinkRelationRequestList,
            AssetLinkRelation.class);
        if (CollectionUtils.isNotEmpty(assetLinkRelationList)) {
            Integer createUser = LoginUserUtil.getLoginUser() != null ? LoginUserUtil.getLoginUser().getId() : 0;
            assetLinkRelationList.stream().forEach(assetLinkRelation -> {
                assetLinkRelation.setCreateUser(createUser);
                assetLinkRelation.setGmtCreate(System.currentTimeMillis());
            });
            assetLinkRelationDao.insertBatch(assetLinkRelationList);
        }
        return ActionResponse.success();
    }

    @Override
    public List<IpPortResponse> queryUseableIp(UseableIpRequest useableIpRequest) {
        return assetLinkRelationDao.queryUseableIp(useableIpRequest.getAssetId());
    }

    @Override
    public PageResult<AssetLinkedCountResponse> queryAssetLinkedCountPage(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        List<String> statusList = new ArrayList<>();
        statusList.add(AssetStatusEnum.WAIT_RETIRE.getCode().toString());
        statusList.add(AssetStatusEnum.NET_IN.getCode().toString());
        assetLinkRelationQuery.setStatusList(statusList);
        // 区域条件
        if (CollectionUtils.isEmpty(assetLinkRelationQuery.getAreaIds())) {
            assetLinkRelationQuery.setAreaIds(Arrays.asList(
                DataTypeUtils.integerArrayToStringArray(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser())));
        }
        List<AssetLinkedCountResponse> assetLinkedCountResponseList = this
            .queryAssetLinkedCountList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetLinkedCountResponseList)) {
            return new PageResult<>(assetLinkRelationQuery.getPageSize(), 0, assetLinkRelationQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<>(assetLinkRelationQuery.getPageSize(),
            this.queryAssetLinkedCount(assetLinkRelationQuery), assetLinkRelationQuery.getCurrentPage(),
            assetLinkedCountResponseList);
    }

    private Integer queryAssetLinkedCount(AssetLinkRelationQuery assetLinkRelationQuery) {
        return assetLinkRelationDao.queryAssetLinkedCount(assetLinkRelationQuery);
    }

    @Override
    public List<AssetLinkedCountResponse> queryAssetLinkedCountList(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        List<AssetLinkedCount> assetResponseList = assetLinkRelationDao
            .queryAssetLinkedCountList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        assetResponseList.stream().forEach(assetLinkedCount -> {
            String newAreaKey = RedisKeyUtil.getKeyWhenGetObject(ModuleEnum.SYSTEM.getType(), SysArea.class,
                DataTypeUtils.stringToInteger(assetLinkedCount.getAreaId()));
            try {
                SysArea sysArea = redisUtil.getObject(newAreaKey, SysArea.class);
                if (!Objects.isNull(sysArea)) {
                    assetLinkedCount.setAreaName(sysArea.getFullName());
                }
            } catch (Exception e) {
                LogUtils.info(logger, "{}  获取区域失败", RespBasicCode.BUSSINESS_EXCETION);
            }
        });
        // Map<String, String> categoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        // processLinkCount(assetResponseList, categoryMap);
        return BeanConvert.convert(assetResponseList, AssetLinkedCountResponse.class);
    }

    // 处理品类型号使其均为二级品类型号
    private void processLinkCount(List<AssetLinkedCount> assetResponseList,
                                  Map<String, String> categoryMap) throws Exception {
        // 作为缓存使用，提高效率
        Map<String, String> cache = new HashMap<>();
        // List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        // Map<String, String> secondCategoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        /* for (AssetLinkedCount assetResponse : assetResponseList) { //String categoryModel =
         * assetResponse.getCategoryModel(); String cacheId = cache.get(categoryModel); if (Objects.nonNull(cacheId)) {
         * // assetResponse.setCategoryType(new CategoryType(secondCategoryMap.get(cacheId))); } else { // String second
         * = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all, // categoryMap.keySet()); //
         * if (Objects.nonNull(second)) { // assetResponse.setCategoryType(new
         * CategoryType(secondCategoryMap.get(second))); // cache.put(categoryModel, second); // } } } */
    }

    private void processLinkRelation(List<AssetLinkRelation> assetResponseList,
                                     Map<String, String> categoryMap) throws Exception {
        // 作为缓存使用，提高效率
        Map<String, String> cache = new HashMap<>();
        // List<AssetCategoryModel> all = iAssetCategoryModelService.getAll();
        // Map<String, String> secondCategoryMap = iAssetCategoryModelService.getSecondCategoryMap();
        /* for (AssetLinkRelation assetResponse : assetResponseList) { String categoryModel =
         * assetResponse.getCategoryModel(); String cacheId = cache.get(categoryModel); if (Objects.nonNull(cacheId)) {
         * // assetResponse.setCategoryType(new CategoryType(secondCategoryMap.get(cacheId))); } else { // String second
         * = iAssetCategoryModelService.recursionSearchParentCategory(categoryModel, all, // categoryMap.keySet()); //
         * if (Objects.nonNull(second)) { // assetResponse.setCategoryType(new
         * CategoryType(secondCategoryMap.get(second))); // cache.put(categoryModel, second); // } } } */
    }

    @Override
    public List<AssetLinkRelationResponse> queryLinkedAssetListByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        ParamterExceptionUtils.isBlank(assetLinkRelationQuery.getPrimaryKey(), "请选择资产");
        List<AssetLinkRelation> assetResponseList = assetLinkRelationDao
            .queryLinkedAssetListByAssetId(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkRelationResponse.class);
    }

    public Integer queryLinkedCountAssetByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) {
        ParamterExceptionUtils.isBlank(assetLinkRelationQuery.getPrimaryKey(), "请选择资产");
        return assetLinkRelationDao.queryLinkedCountAssetByAssetId(assetLinkRelationQuery);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryLinkedAssetPageByAssetId(AssetLinkRelationQuery assetLinkRelationQuery) throws Exception {
        List<AssetLinkRelationResponse> assetLinkRelationResponseList = this
            .queryLinkedAssetListByAssetId(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetLinkRelationResponseList)) {
            return new PageResult<>(assetLinkRelationQuery.getPageSize(), 0, assetLinkRelationQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<>(assetLinkRelationQuery.getPageSize(),
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
