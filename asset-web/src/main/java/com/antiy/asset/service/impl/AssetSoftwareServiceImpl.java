package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.antiy.common.base.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.dao.AssetSoftwareDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.AssetSoftware;
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
import com.antiy.asset.vo.request.AssetSoftwareRequest;
import com.antiy.asset.vo.response.AssetPortProtocolResponse;
import com.antiy.asset.vo.response.AssetSoftwareDetailResponse;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

/**
 * <p> 软件信息表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSoftwareServiceImpl extends BaseServiceImpl<AssetSoftware> implements IAssetSoftwareService {

    @Resource
    private AssetSoftwareDao                                          assetSoftwareDao;
    @Resource
    private AssetCategoryModelDao                                     assetCategoryModelDao;
    @Resource
    private BaseConverter<AssetSoftwareRequest, AssetSoftware>        requestConverter;
    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareResponse>       responseConverter;

    @Resource
    private BaseConverter<AssetSoftware, AssetSoftwareDetailResponse> assetSoftwareDetailConverter;

    @Resource
    private IAssetPortProtocolService                                 iAssetPortProtocolService;

    @Resource
    private IAssetSoftwareLicenseService                              iAssetSoftwareLicenseService;

    @Override
    public Integer saveAssetSoftware(AssetSoftwareRequest request) throws Exception {
        AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
        assetSoftwareDao.insert(assetSoftware);
        return assetSoftware.getId();
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
    public Integer updateAssetSoftware(AssetSoftwareRequest request) throws Exception {
        AssetSoftware assetSoftware = requestConverter.convert(request, AssetSoftware.class);
        return assetSoftwareDao.update(assetSoftware);
    }

    @Override
    public List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery query) throws Exception {
        List<AssetSoftware> assetSoftware = assetSoftwareDao.findListAssetSoftware(query);
        List<AssetSoftwareResponse> assetSoftwareResponse = responseConverter.convert(assetSoftware,
            AssetSoftwareResponse.class);
        return assetSoftwareResponse;
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

        // TODO 从ThreadLocal里面获取区域Id
        return assetSoftwareDao.findManufacturerName(manufacturerName, null);
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
            if (AssetCategoryModel.getParentId().equals(id)) {
                result.add(AssetCategoryModel);
                recursion(result, list, AssetCategoryModel.getId());
            }
        }
    }

    @Override
    public Map<String, Long> countManufacturer() throws Exception {
        // todo 添加区域id
        List<Map<String, Long>> list = assetSoftwareDao.countManufacturer();
        Map result = new HashMap();
        for (Map map : list) {
            result.put(map.get("key"), map.get("value"));
        }
        return result;
    }

    @Override
    public Map<String, Long> countStatus() throws Exception {
        // todo 添加区域id
        List<Map<String, Long>> list = assetSoftwareDao.countStatus();
        Map<String, Long> result = new HashMap();
        for (Map map : list) {
            result.put(AssetStatusEnum.getAssetByCode((Integer) map.get("key")) + "", (Long) map.get("value"));
        }
        return result;
    }

    @Override
    public Map<String, Long> countCategory() throws Exception {
        // todo 添加区域id
        HashMap<String, Object> map = new HashMap();
        map.put("name", "软件");
        map.put("parentId", 0);
        List<AssetCategoryModel> categoryModelList = assetCategoryModelDao.getByWhere(map);
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
            Long sum = assetSoftwareDao.findCountByCategoryModel(assetSoftwareQuery);
            result.put(a.getName(), sum);
        }
        return result;
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
        assetPortProtocolQuery.setAssetSoftId(DataTypeUtils.stringToInteger(softwareQuery.getPrimaryKey()));
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
