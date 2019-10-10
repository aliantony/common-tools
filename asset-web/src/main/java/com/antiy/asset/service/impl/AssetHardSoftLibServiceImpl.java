package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.service.IAssetInstallTemplateService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetPulldownQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.BusinessSelectResponse;
import com.antiy.asset.vo.response.OsSelectResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p> CPE表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetHardSoftLibServiceImpl extends BaseServiceImpl<AssetHardSoftLib> implements IAssetHardSoftLibService {

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetHardSoftLibDao assetHardSoftLibDao;
    @Resource
    private BaseConverter<AssetHardSoftLibRequest, AssetHardSoftLib> requestConverter;
    @Resource
    private BaseConverter<AssetHardSoftLib, AssetHardSoftLibResponse> responseConverter;
    @Resource
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Resource
    private IAssetInstallTemplateService iAssetInstallTemplateService;

    @Override
    public String saveAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception {
        AssetHardSoftLib assetHardSoftLib = requestConverter.convert(request, AssetHardSoftLib.class);
        assetHardSoftLibDao.insert(assetHardSoftLib);
        return assetHardSoftLib.getStringId();
    }

    @Override
    public String updateAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception {
        AssetHardSoftLib assetHardSoftLib = requestConverter.convert(request, AssetHardSoftLib.class);
        return assetHardSoftLibDao.update(assetHardSoftLib).toString();
    }

    @Override
    public List<AssetHardSoftLibResponse> queryListAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception {
        List<AssetHardSoftLib> assetHardSoftLibList = assetHardSoftLibDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetHardSoftLibList, AssetHardSoftLibResponse.class);
    }

    @Override
    public List<AssetHardSoftLibResponse> queryHardSoftLibList(AssetHardSoftLibQuery query) throws Exception {
        List<AssetHardSoftLib> hardSoftLibList = assetHardSoftLibDao.queryHardSoftLibList(query);
        return responseConverter.convert(hardSoftLibList, AssetHardSoftLibResponse.class);
    }

    @Override
    public Integer queryHardSoftLibCount(AssetHardSoftLibQuery query) throws Exception {
        return assetHardSoftLibDao.queryHardSoftLibCount(query);
    }

    @Override
    public PageResult<AssetHardSoftLibResponse> queryPageAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception {
        if (!Objects.isNull(query.getBusinessId()) && StringUtils.isNotBlank(query.getSourceType())) {
            List<Long> ids = assetHardSoftLibDao.exceptIds(query.getBusinessId(), query.getSourceType(),
                    query.getAssetType().getName());
            query.setExceptIds(ids);
        }
        Integer count = this.queryHardSoftLibCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        if (count < query.getPageSize() * query.getCurrentPage()) {
            query.setCurrentPage((int) Math.ceil((double) count / query.getPageSize()));
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), this.queryHardSoftLibList(query));
    }

    @Override
    public AssetHardSoftLibResponse queryAssetHardSoftLibById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetHardSoftLibResponse assetHardSoftLibResponse = responseConverter
                .convert(assetHardSoftLibDao.getById(queryCondition.getPrimaryKey()), AssetHardSoftLibResponse.class);
        return assetHardSoftLibResponse;
    }

    @Override
    public String deleteAssetHardSoftLibById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetHardSoftLibDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public PageResult<SoftwareResponse> getPageSoftWareList(AssetSoftwareQuery query) {
        int count = assetSoftwareRelationDao.countSoftwareByAssetId(DataTypeUtils.stringToInteger(query.getAssetId()));
        if (count == 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), null);
        }
        List<SoftwareResponse> assetSoftwareRelationList = assetSoftwareRelationDao.getSimpleSoftwareByAssetId(query);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), assetSoftwareRelationList);
    }

    @Override
    public List<OsSelectResponse> pullDownOs(OsQuery query) {
        query.setType("o");
        query.setIsfilter(true);
        List<OsSelectResponse> osList = assetHardSoftLibDao.pullDownOs(query);
        return osList;
    }

    @Override
    public List<String> pulldownSupplier(AssetPulldownQuery query) throws Exception {
        return assetHardSoftLibDao.pulldownSupplier(query);
    }

    @Override
    public List<String> pulldownName(AssetPulldownQuery query) {
        return assetHardSoftLibDao.pulldownName(query);
    }

    @Override
    public List<BusinessSelectResponse> pulldownVersion(AssetPulldownQuery query) throws UnsupportedEncodingException {
        List<BusinessSelectResponse> result = new ArrayList<>();
        if (query.getVersion() != null) {
            String posStr = query.getName() + query.getSupplier();
            query.setPos(posStr.length() + 10);
            query.setVersion(query.getVersion().replace(" ", ":"));
        }
        List<AssetHardSoftLib> assetHardSoftLibs = assetHardSoftLibDao.queryHardSoftLibByVersion(query);
        for (AssetHardSoftLib assetHardSoftLib : assetHardSoftLibs) {
            BusinessSelectResponse response = new BusinessSelectResponse();
            // 特殊处理
            // 版本为 cpe_uri 除前缀厂商名产品名的部分， cpe:/a:厂商名:产品名:后面的部分 且：用空格代替
            String m = Optional.ofNullable(assetHardSoftLib.getSupplier()).orElse("")
                    + Optional.ofNullable(assetHardSoftLib.getProductName()).orElse("");
            response.setValue(
                    Optional.ofNullable(assetHardSoftLib.getCpeUri()).map(str -> str.substring(9 + m.length())).orElse(""));
            response.setId(Objects.toString(assetHardSoftLib.getBusinessId()));
            result.add(response);
        }
        return result;
    }

    @Override
    public PageResult<AssetHardSoftLibResponse> queryPageSoft(AssetSoftwareQuery query) {
        query.setOperationSystem(iAssetInstallTemplateService.queryOs(query.getOperationSystem()).get(0).getOsName());
        Integer count = assetHardSoftLibDao.queryCountSoftWares(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
        if (count < query.getPageSize() * query.getCurrentPage()) {
            query.setCurrentPage((int) Math.ceil((double) count / query.getPageSize()));
        }
        List<AssetHardSoftLib> softWares = assetHardSoftLibDao.querySoftWares(query);
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                responseConverter.convert(softWares, AssetHardSoftLibResponse.class));
    }

    @Override
    public List<AssetHardSoftLibResponse> querySoftsRelations(String templateId) {
        return responseConverter.convert(assetHardSoftLibDao.querySoftsRelations(templateId),
                AssetHardSoftLibResponse.class);
    }
}
