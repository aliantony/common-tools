package com.antiy.asset.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.antiy.asset.dao.*;
import com.antiy.asset.entity.*;
import com.antiy.asset.service.IAssetPortProtocolService;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.service.IAssetSoftwareService;
import com.antiy.asset.util.ArrayTypeUtil;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetPortProtocolQuery;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.SoftwareQuery;
import com.antiy.asset.vo.request.AssetPortProtocolRequest;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetPortProtocolResponse;
import com.antiy.asset.vo.response.AssetSoftwareDetailResponse;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.biz.util.LoginUserUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.Constants;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 软件信息表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSoftwareServiceImpl extends BaseServiceImpl<AssetSoftware> implements IAssetSoftwareService {

    @Resource
    private AssetSoftwareDao                                                 assetSoftwareDao;
    @Resource
    private AssetSoftwareRelationDao                                         assetSoftwareRelationDao;
    @Resource
    private AssetSoftwareLicenseDao                                          assetSoftwareLicenseDao;
    @Resource
    private AssetPortProtocolDao                                             assetPortProtocolDaoDao;
    @Resource
    private AssetCategoryModelDao                                            assetCategoryModelDao;
    @Resource
    private BaseConverter<AssetSoftwareRequest, AssetSoftware>               requestConverter;

    @Resource
    private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense> assetSoftwareLicenseBaseConverter;

    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareDetailResponse>        assetSoftwareDetailConverter;

    @Resource
    private IAssetPortProtocolService                                        iAssetPortProtocolService;

    @Resource
    private IAssetSoftwareLicenseService                                     iAssetSoftwareLicenseService;

    @Resource
    private TransactionTemplate                                              transactionTemplate;
    private static final Logger                                              LOGGER = LogUtils
        .get(AssetSoftwareServiceImpl.class);

    @Transactional
    @Override
    public Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception {
        AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
        AssetSoftwareLicense license = new BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense>()
            .convert(request.getSoftwareLicenseRequest(), AssetSoftwareLicense.class);
        AssetPortProtocol protocol = new BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>()
            .convert(request.getAssetPortProtocolRequest(), AssetPortProtocol.class);

        assetSoftwareDao.insert(assetSoftware);
        Integer sid = assetSoftware.getId();
        license.setSoftwareId(sid);
        protocol.setAssetSoftId(sid);
        assetSoftwareLicenseDao.insert(license);
        assetPortProtocolDaoDao.insert(protocol);
        if (ArrayUtils.isNotEmpty(request.getAssetIds())) {
            String[] assetIds = request.getAssetIds();
            for (String s : assetIds) {
                AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                assetSoftwareRelation.setSoftwareId(sid);
                assetSoftwareRelation.setAssetId(Integer.parseInt(s));
                assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                assetSoftwareRelationDao.insert(assetSoftwareRelation);
            }
        }
        // TODO: 2019/1/14 工作流
        return sid;
    }

    @Override
    @Transactional
    public Integer batchSave(List<AssetSoftware> assetSoftwareList) throws Exception {
        int i = 0;
        for (; i < assetSoftwareList.size(); i++) {
            assetSoftwareDao.insert(assetSoftwareList.get(i));
        }
        return i + 1;
    }

    @Override
    @Transactional
    public Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception {
        Integer count = transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus transactionStatus) {
                try {
                    // 1.更新软件信息
                    request.setSoftwareStatus(3); // 软件变更需要改状态到带配置
                    AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
                    assetSoftware.setId(DataTypeUtils.stringToInteger(request.getId()));
                    int assetSoftwareCount = assetSoftwareDao.update(assetSoftware);

                    // 2.更新license表
                    if (null != request.getSoftwareLicenseRequest()
                        && StringUtils.isNotBlank(request.getSoftwareLicenseRequest().getId())) {
                        updateLicense(request);
                    }

                    // 3.是否存在关联表Id和状态，如果存在，则更新关联表即可(更新某一个实例)
                    if (StringUtils.isNotBlank(request.getAssetSoftwareRelationId())
                        && request.getSoftwareStatus() != null) {
                        AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                        assetSoftwareRelation
                            .setId(DataTypeUtils.stringToInteger(request.getAssetSoftwareRelationId()));
                        assetSoftwareRelation.setSoftwareStatus(request.getSoftwareStatus());
                        assetSoftwareRelationDao.update(assetSoftwareRelation);
                    } else if (ArrayUtils.isNotEmpty(request.getAssetIds())) { // 更新一批实例
                        // 4.移除端口和关联表的关系
                        List<Integer> releationIds = assetSoftwareRelationDao.getAllReleationId(null,
                            DataTypeUtils.stringToInteger(request.getId()));
                        if (CollectionUtils.isNotEmpty(releationIds)) {
                            assetPortProtocolDaoDao.deletePortProtocol(releationIds);
                        }

                        // 5.移除关系表
                        assetSoftwareRelationDao.deleteSoftwareRelAsset(null,
                            DataTypeUtils.stringToInteger(request.getId()));

                        // 5.插入关系表，并且插入端口数据
                        for (String assetId : request.getAssetIds()) {
                            AssetSoftwareRelation assetSoftwareRelation = new AssetSoftwareRelation();
                            assetSoftwareRelation.setSoftwareId(DataTypeUtils.stringToInteger(request.getId()));
                            assetSoftwareRelation.setAssetId(DataTypeUtils.stringToInteger(assetId));
                            assetSoftwareRelation.setSoftwareStatus(request.getSoftwareStatus());
                            assetSoftwareRelation.setGmtCreate(System.currentTimeMillis());
                            assetSoftwareRelationDao.insert(assetSoftwareRelation);

                            ParamterExceptionUtils.isNull(assetSoftwareRelation.getId(), "更新软件失败");
                            // TODO 目前产品端口信息没有关联某一个硬件资产和软件资产，所以目前没有更新单个实例的端口信息
                            AssetPortProtocol protocol = new BaseConverter<AssetPortProtocolRequest, AssetPortProtocol>()
                                .convert(request.getAssetPortProtocolRequest(), AssetPortProtocol.class);
                            protocol.setAssetSoftId(assetSoftwareRelation.getId());
                            // 插入端口信息
                            assetPortProtocolDaoDao.insert(protocol);
                        }
                    }
                    return assetSoftwareCount;
                } catch (Exception e) {
                    LOGGER.error("修改软件信息失败", e);
                }
                return 0;
            }
        });

        // TODO 调用工作流，给配置管理员
        return count;
    }

    /**
     * 更新lincense
     * @param request
     */
    private void updateLicense(AssetSoftwareRequest request) throws Exception {
        AssetSoftwareLicense assetSoftwareLicense = assetSoftwareLicenseBaseConverter
            .convert(request.getSoftwareLicenseRequest(), AssetSoftwareLicense.class);
        assetSoftwareLicense.setSoftwareId(DataTypeUtils.stringToInteger(request.getId()));
        assetSoftwareLicenseDao.update(assetSoftwareLicense);
    }

    private Map<Integer, Long> handleSoftCount(List<Map<String, Object>> softObjectList) {
        Map<Integer, Long> map = new HashMap<>();
        for (Map<String, Object> objectMap : softObjectList) {
            Integer id = objectMap.get("id") != null ? Integer.valueOf(objectMap.get("id").toString()) : 0;
            Long count = objectMap.get("name") != null ? Long.parseLong(objectMap.get("name").toString()) : 0;
            map.put(id, count);
        }
        return map;
    }

    @Override
    public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception {
        List<AssetSoftware> assetSoftware = assetSoftwareDao.findListAssetSoftware(query);
        Map<Integer, Long> softAssetCount = null;
        if (query.getQueryAssetCount()) {
            List<Integer> allSoftwareIds = new ArrayList<>();
            assetSoftware.stream().forEach(assetSoftwareDO -> allSoftwareIds.add(assetSoftwareDO.getId()));
            softAssetCount = handleSoftCount(assetSoftwareRelationDao.countSoftwareRelAsset(allSoftwareIds));
        }

        Map<Integer, Long> finalSoftAssetCount = softAssetCount;
        BaseConverter baseConverter = new BaseConverter<AssetSoftware, AssetSoftwareResponse>() {

            @Override
            protected void convert(AssetSoftware assetSoftware, AssetSoftwareResponse assetSoftwareResponse) {
                super.convert(assetSoftware, assetSoftwareResponse);
                if (MapUtils.isNotEmpty(finalSoftAssetCount)) {
                    assetSoftwareResponse.setAssetCount(finalSoftAssetCount.get(assetSoftware.getId()) != null
                        ? finalSoftAssetCount.get(assetSoftware.getId()).intValue()
                        : 0);
                }
            }
        };
        return baseConverter.convert(assetSoftware, AssetSoftwareResponse.class);
    }

    public Integer findCountAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return assetSoftwareDao.findCount(query);
    }

    @Override
    public PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetSoftware(query), query.getCurrentPage(),
            this.findListAssetSoftware(query));
    }

    @Override
    public List<String> getManufacturerName(String manufacturerName) throws Exception {
        List<Integer> list = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        return assetSoftwareDao.findManufacturerName(manufacturerName, list);
    }

    /**
     * 查询出品类和其子品类
     *
     * @param id 查询的品类id
     */
    public List<AssetCategoryModel> recursionSearch(Integer id) throws Exception {
        List<AssetCategoryModel> list = assetCategoryModelDao.getAll();
        List<AssetCategoryModel> result = new ArrayList();
        for (AssetCategoryModel AssetCategoryModel : list) {
            if (AssetCategoryModel.getId().equals(id)) {
                result.add(AssetCategoryModel);
            }
        }
        recursion(result, list, id);
        return result;
    }

    /**
     * 递归查询出所有的品类和其子品类
     *
     * @param result 查询的结果集
     * @param list 查询的数据集
     * @param id 递归的参数
     */
    private void recursion(List<AssetCategoryModel> result, List<AssetCategoryModel> list, Integer id) {
        for (AssetCategoryModel AssetCategoryModel : list) {
            if (Objects.equals(AssetCategoryModel.getParentId(), id)) {
                result.add(AssetCategoryModel);
                recursion(result, list, AssetCategoryModel.getId());
            }
        }
    }

    @Override
    public Map<String, Long> countManufacturer() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetSoftwareDao.countManufacturer(ids);
        Map result = new HashMap();
        for (Map map : list) {
            result.put(map.get("key"), map.get("value"));
        }
        return result;
    }

    @Override
    public Map<String, Long> countStatus() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        List<Map<String, Long>> list = assetSoftwareDao.countStatus(ids);
        Map<String, Long> result = new HashMap();
        for (Map map : list) {
            result.put(AssetStatusEnum.getAssetByCode((Integer) map.get("key")) + "", (Long) map.get("value"));
        }
        return result;
    }

    @Override
    public Map<String, Long> countCategory() throws Exception {
        List<Integer> ids = LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser();
        HashMap<String, Object> map = new HashMap();
        map.put("name", "软件");
        map.put("parentId", 0);
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getByWhere(map);
        if (CollectionUtils.isNotEmpty(categoryModelList)) {
            Integer id = categoryModelList.get(0).getId();
            map.clear();
            map.put("parentId", id);
            List<AssetCategoryModel> categoryModelList1 = assetCategoryModelDao.getByWhere(map);
            HashMap<String, Long> result = new HashMap<>();
            for (AssetCategoryModel a : categoryModelList1) {
                List<AssetCategoryModel> search = recursionSearch(a.getId());
                List list = new ArrayList();
                for (AssetCategoryModel b : search) {
                    list.add(b.getId());
                }
                AssetSoftwareQuery assetSoftwareQuery = new AssetSoftwareQuery();
                assetSoftwareQuery.setCategoryModels(ArrayTypeUtil.ObjectArrayToIntegerArray(list.toArray()));
                assetSoftwareQuery.setAreaIds(ArrayTypeUtil.ObjectArrayToIntegerArray(ids.toArray()));
                Long sum = assetSoftwareDao.findCountByCategoryModel(assetSoftwareQuery);
                result.put(a.getName(), sum);
            }
            return result;
        }
        return null;
    }

    @Override
    public AssetSoftwareDetailResponse querySoftWareDetail(SoftwareQuery softwareQuery) throws Exception {

        // 1 获取软件资产详情
        AssetSoftware assetSoftware = this.getById(DataTypeUtils.stringToInteger(softwareQuery.getPrimaryKey()));
        AssetSoftwareDetailResponse assetSoftwareDetailResponse = assetSoftwareDetailConverter.convert(assetSoftware,
            AssetSoftwareDetailResponse.class);

        // TODO 软件资产码表信息

        // 2 是否需要查询端口信息
        if (softwareQuery.getQueryPort()) {
            querySoftwarePort(softwareQuery, assetSoftwareDetailResponse);
        }

        // 3 是否需要查询license信息
        if (softwareQuery.getQueryLicense()) {
            querySoftwareLicense(softwareQuery, assetSoftwareDetailResponse);
        }

        return assetSoftwareDetailResponse;
    }

    /**
     * 查询软件端口信息
     * @param softwareQuery
     * @param assetSoftwareDetailResponse
     * @throws Exception
     */
    private void querySoftwarePort(SoftwareQuery softwareQuery,
                                   AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        AssetPortProtocolQuery assetPortProtocolQuery = new AssetPortProtocolQuery();
        assetPortProtocolQuery.setAssetSoftId(softwareQuery.getPrimaryKey());
        assetPortProtocolQuery.setPageSize(Constants.MAX_PAGESIZE);
        List<AssetPortProtocolResponse> assetPortProtocolResponses = iAssetPortProtocolService
            .findListAssetPortProtocol(assetPortProtocolQuery);
        assetSoftwareDetailResponse.setSoftwarePort(assetPortProtocolResponses);
    }

    /**
     * 查询软件license 信息
     * @param softwareQuery
     * @param assetSoftwareDetailResponse
     * @throws Exception
     */
    private void querySoftwareLicense(SoftwareQuery softwareQuery,
                                      AssetSoftwareDetailResponse assetSoftwareDetailResponse) throws Exception {
        AssetSoftwareLicenseQuery assetSoftwareLicenseQuery = new AssetSoftwareLicenseQuery();
        assetSoftwareLicenseQuery.setSoftwareId(DataTypeUtils.stringToInteger(softwareQuery.getPrimaryKey()));
        assetSoftwareLicenseQuery.setPageSize(Constants.MAX_PAGESIZE);
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponses = iAssetSoftwareLicenseService
            .findListAssetSoftwareLicense(assetSoftwareLicenseQuery);
        assetSoftwareDetailResponse.setSoftwareLicense(assetSoftwareLicenseResponses);
    }

}
