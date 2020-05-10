package com.antiy.asset.service.impl;

import com.antiy.asset.cache.AssetBaseDataCache;
import com.antiy.asset.dao.AssetBusinessDao;
import com.antiy.asset.dao.AssetBusinessRelationDao;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetBusiness;
import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.login.LoginTool;
import com.antiy.asset.service.IAssetBusinessService;
import com.antiy.asset.util.SnowFlakeUtil;
import com.antiy.asset.vo.enums.AssetCategoryEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetAddOfBusinessQuery;
import com.antiy.asset.vo.query.AssetBusinessQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.request.AssetBusinessRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
import com.antiy.asset.vo.response.AssetBusinessResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.*;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.enums.BusinessPhaseEnum;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */
@Service
public class AssetBusinessServiceImpl extends BaseServiceImpl<AssetBusiness> implements IAssetBusinessService {

    private Logger                                                             logger = LogUtils.get(this.getClass());

    @Resource
    private AssetBusinessDao                                                   assetBusinessDao;
    @Resource
    private BaseConverter<AssetBusinessRequest, AssetBusiness>                 requestConverter;
    @Resource
    private BaseConverter<AssetBusiness, AssetBusinessResponse>                responseConverter;
    @Resource
    private BaseConverter<Asset, AssetResponse>                                assetConverter;

    @Resource
    private BaseConverter<AssetBusinessRelationRequest, AssetBusinessRelation> relationRequestConverter;
    @Resource
    private AssetBusinessRelationDao                                           assetBusinessRelationDao;
    @Resource
    private AssetDao                                                           assetDao;
    @Resource
    private AssetBaseDataCache                                                 assetBaseDataCache;

    @Resource
    private AssetLinkRelationServiceImpl assetLinkRelationService;
    @Resource
    private AssetCategoryModelDao assetCategoryModelDao;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveAssetBusiness(AssetBusinessRequest request) throws Exception {
        String name = request.getName();
        AssetBusiness business = assetBusinessDao.getByName(name);
        if (business != null) {
            throw new BusinessException("业务名不能重复！");
        }
        AssetBusiness assetBusiness = requestConverter.convert(request, AssetBusiness.class);
        assetBusiness.setUniqueId(SnowFlakeUtil.getSnowId());
        assetBusiness.setGmtCreate(System.currentTimeMillis());
        assetBusiness.setGmtModified(System.currentTimeMillis());
        assetBusinessDao.insert(assetBusiness);
        // 更新缓存
        assetBaseDataCache.put(AssetBaseDataCache.ASSET_BUSINESS, assetBusiness);
        List<AssetBusinessRelationRequest> list = request.getAssetRelaList();
        List<AssetBusinessRelation> assetRelationList = new ArrayList<>();
        for (AssetBusinessRelationRequest itme : list) {
            Asset asset = assetDao.getById(itme.getAssetId());
            if(AssetStatusEnum.NOT_REGISTER.getCode().equals(asset.getAssetStatus())
                    ||AssetStatusEnum.RETIRE.getCode().equals(asset.getAssetStatus())
                    ||AssetStatusEnum.SCRAP.getCode().equals(asset.getAssetStatus())){
                throw new BusinessException("资产状态已改变，不能添加此状态资产！");
            }
            AssetBusinessRelation assetBusinessRelation = new AssetBusinessRelation();
            assetBusinessRelation.setAssetId(itme.getAssetId());
            assetBusinessRelation.setBusinessInfluence(itme.getBusinessInfluence());
            assetBusinessRelation.setGmtCreate(System.currentTimeMillis());
            assetBusinessRelation.setAssetBusinessId(assetBusiness.getId());
            assetBusinessRelation.setUniqueId(assetBusiness.getUniqueId());
            assetBusinessRelation.setGmtCreate(System.currentTimeMillis());
            assetBusinessRelation.setGmtModified(System.currentTimeMillis());
            assetRelationList.add(assetBusinessRelation);
        }
        if(CollectionUtils.isNotEmpty(assetRelationList)){
            assetBusinessRelationDao.insertBatch(assetRelationList);
        }
        LogUtils.recordOperLog(
                new BusinessData("新增业务信息", assetBusiness.getId(), assetBusiness.getName(),
                        request, BusinessModuleEnum.BUSINESS_MANAGE, BusinessPhaseEnum.BUSINESS_ADD)
        );
        return assetBusiness.getStringId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateAssetBusiness(AssetBusinessRequest request) throws Exception {
        ParamterExceptionUtils.isNull(request.getUniqueId(), "唯一键不能为空！");
        String name = request.getName();
        AssetBusiness business = assetBusinessDao.getByName(name);
        if (business != null && !business.getUniqueId().equals(request.getUniqueId())) {
            throw new BusinessException("业务名不能重复！");
        }
        // 更新基本信息
        AssetBusiness assetBusiness = requestConverter.convert(request, AssetBusiness.class);
        String uniqueId = assetBusiness.getUniqueId();
        assetBusiness.setId(null);
        assetBusiness.setGmtModified(System.currentTimeMillis());
        Integer result = assetBusinessDao.updateByUniqueId(assetBusiness);
        // 更新缓存
        assetBaseDataCache.update(AssetBaseDataCache.ASSET_BUSINESS, assetBusiness);
        // 分离出编辑 、 删除 、 新增的 资产
        AssetAddOfBusinessQuery assetAddOfBusinessQuery = new AssetAddOfBusinessQuery();
        assetAddOfBusinessQuery.setUniqueId(uniqueId);
        List<AssetBusinessRelationResponse> assetOfDB = queryAssetByBusinessId(assetAddOfBusinessQuery);
        setAsset(request, assetOfDB);
        // 更新关联资产信息
        addAssetOfBusiness(request);
        deleteAssetOfBusiness(request);
        editAssetOfBusiness(request);
        LogUtils.recordOperLog(
                new BusinessData("编辑业务信息", request.getId(), assetBusiness.getName(),
                        request, BusinessModuleEnum.BUSINESS_MANAGE, BusinessPhaseEnum.BUSINESS_EDIT)
        );
        return result.toString();
    }

    private void setAsset(AssetBusinessRequest request, List<AssetBusinessRelationResponse> assetOfDB) {
        // 删除
        List<AssetBusinessRelationRequest> assetOfParamter = request.getAssetRelaList();
        Set<String> assetIdOfDB = assetOfDB.stream().map(t -> t.getStringId()).collect(Collectors.toSet());
        Set<String> assetIdOfParamter = assetOfParamter.stream().map(t -> t.getAssetId()).collect(Collectors.toSet());

        // 删除的资产
        Set<String> deleteAssetId = new HashSet<>(assetIdOfDB);
        deleteAssetId.removeAll(assetIdOfParamter);
        request.setDeleteAssetList(deleteAssetId);
        // 新增的资产
        Set<String> addAssetId = new HashSet<>(assetIdOfParamter);
        addAssetId.removeAll(assetIdOfDB);
        List<AssetBusinessRelationRequest> addAsset = assetOfParamter.stream()
            .filter(t -> addAssetId.contains(t.getAssetId())).collect(Collectors.toList());
        request.setAddAssetList(addAsset);
        // 编辑的资产
        List<AssetBusinessRelationRequest> editAsset = new ArrayList<>();
        for (AssetBusinessRelationResponse itemOfDb : assetOfDB) {
            for (AssetBusinessRelationRequest itemOfParam : assetOfParamter) {
                if (
                    itemOfDb.getStringId().equals(itemOfParam.getAssetId())
                    && (itemOfDb.getBusinessInfluence()==null && itemOfParam.getBusinessInfluence()!=null
                    || !itemOfDb.getBusinessInfluence().equals(itemOfParam.getBusinessInfluence()))
                ) {
                    editAsset.add(itemOfParam);
                }

            }
        }
        request.setEditAsset(editAsset);
    }

    private void editAssetOfBusiness(AssetBusinessRequest request) {
        List<AssetBusinessRelationRequest> editAsset = request.getEditAsset();
        if (CollectionUtils.isNotEmpty(editAsset)) {
            editAsset.forEach(v -> v.setGmtModified(System.currentTimeMillis()));
            assetBusinessRelationDao.updateBatch(editAsset, request.getUniqueId());
        }
    }

    private void deleteAssetOfBusiness(AssetBusinessRequest request) {
        Set<String> deleteAssetList = request.getDeleteAssetList();
        if (CollectionUtils.isNotEmpty(deleteAssetList)) {
            assetBusinessRelationDao.deleteByUniqueIdAndAssetId(request);
        }
    }

    public void addAssetOfBusiness(AssetBusinessRequest request) throws Exception {
        List<AssetBusinessRelationRequest> assetRelaList = request.getAddAssetList();
        List<AssetBusinessRelation> assetRelationList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(assetRelaList)) {
            for (AssetBusinessRelationRequest item : assetRelaList) {
                String assetId = item.getAssetId();
                AssetBusinessRelation assetRelation = assetBusinessRelationDao
                    .getByUniqueIdAndAssetId(request.getUniqueId(), Integer.valueOf(item.getAssetId()));
                if (assetRelation != null) {
                    throw new BusinessException("不能重复关联资产！");
                }
                Asset asset = assetDao.getById(item.getAssetId());
                if (!(AssetStatusEnum.NET_IN.getCode().equals(asset.getAssetStatus())
                      || AssetStatusEnum.IN_CHANGE.getCode().equals(asset.getAssetStatus()))) {
                    throw new BusinessException("资产状态不合符流程！");
                }
                AssetBusinessRelation assetBusinessRelation = new AssetBusinessRelation();
                assetBusinessRelation.setAssetId(item.getAssetId());
                assetBusinessRelation.setBusinessInfluence(item.getBusinessInfluence());
                assetBusinessRelation.setGmtCreate(System.currentTimeMillis());
                assetBusinessRelation.setGmtModified(System.currentTimeMillis());
                assetBusinessRelation.setAssetBusinessId(request.getId());
                assetBusinessRelation.setUniqueId(request.getUniqueId());
                assetRelationList.add(assetBusinessRelation);
            }
            assetBusinessRelationDao.insertBatch(assetRelationList);
        }
    }

    @Override
    public List<AssetBusinessResponse> queryListAssetBusiness(AssetBusinessQuery query) throws Exception {
        List<AssetBusiness> assetBusinessList = assetBusinessDao.findQuery(query);
        return responseConverter.convert(assetBusinessList, AssetBusinessResponse.class);
    }

    @Override
    public PageResult<AssetBusinessResponse> queryPageAssetBusiness(AssetBusinessQuery query) throws Exception {
        return new PageResult<AssetBusinessResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.queryListAssetBusiness(query));
    }

    @Override
    public AssetBusinessResponse queryAssetBusinessById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetBusinessResponse assetBusinessResponse = responseConverter
            .convert(assetBusinessDao.getById(queryCondition.getPrimaryKey()), AssetBusinessResponse.class);
        return assetBusinessResponse;
    }

    @Override
    public String deleteAssetBusinessById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        // 更新缓存
        assetBaseDataCache.remove(AssetBaseDataCache.ASSET_BUSINESS, baseRequest.getId());
        return assetBusinessDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public PageResult<AssetResponse> queryAsset(AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception {
        List<String> areaId = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
        String[] strings = areaId.toArray(new String[0]);
        assetAddOfBusinessQuery.setAreaIds(strings);
        //获取计算设备的品类型号
       // List<String> categoryModels = assetCategoryModelDao.getCategoryModelsByParentName(AssetCategoryEnum.COMPUTER.getName());
        Integer id = assetCategoryModelDao.getByName(AssetCategoryEnum.COMPUTER.getName()).getId();
        List<Integer> ids = assetLinkRelationService.getCategoryNodeList(Arrays.asList(id));
        List<String> categoryModels = ids.stream().map(t -> t.toString()).collect(Collectors.toList());
        assetAddOfBusinessQuery.setCategoryModels(categoryModels);
        Integer count = assetDao.countQueryAsset(assetAddOfBusinessQuery);
        if (count > 0) {
            List<AssetResponse> assetList = assetDao.queryAsset(assetAddOfBusinessQuery);
            return new PageResult<>(assetAddOfBusinessQuery.getPageSize(), count,
                assetAddOfBusinessQuery.getCurrentPage(), assetList);
        }
        return new PageResult<>(assetAddOfBusinessQuery.getPageSize(), 0, assetAddOfBusinessQuery.getCurrentPage(),
            Lists.newArrayList());
    }

    @Override
    public List<AssetBusinessRelationResponse> queryAssetByBusinessId(AssetAddOfBusinessQuery assetAddOfBusinessQuery) throws Exception {
        List<String> areaIdsOfCurrentUser = LoginTool.getLoginUser().getAreaIdsOfCurrentUser();
        String[] strings = areaIdsOfCurrentUser.toArray(new String[0]);
        assetAddOfBusinessQuery.setAreaIds(strings);
        List<AssetBusinessRelationResponse> assetList = assetBusinessRelationDao
            .queryAssetByBusinessId(assetAddOfBusinessQuery);
        return assetList;
    }

    @Override
    public AssetBusinessResponse getByUniqueId(String uniqueId) {
        AssetBusiness assetBusiness = assetBusinessDao.getByUniqueId(uniqueId);
        AssetBusinessResponse assetBusinessResponse = responseConverter.convert(assetBusiness,
            AssetBusinessResponse.class);
        return assetBusinessResponse;
    }

    @Override
    public Integer updateStatusByUniqueId(String uniqueId) {
        AssetBusiness business = assetBusinessDao.getByUniqueId(uniqueId);
        Integer result = assetBusinessDao.updateStatusByUniqueId(uniqueId);
        LogUtils.recordOperLog(
                new BusinessData("删除业务信息", business.getId(), business.getName(),
                        uniqueId, BusinessModuleEnum.BUSINESS_MANAGE, BusinessPhaseEnum.BUSINESS_DELETE)
        );
        return result;
    }
}
