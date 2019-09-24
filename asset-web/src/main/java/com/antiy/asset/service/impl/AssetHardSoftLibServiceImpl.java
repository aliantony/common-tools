package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetHardSoftLibDao;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetHardSoftLib;
import com.antiy.asset.service.IAssetHardSoftLibService;
import com.antiy.asset.util.DataTypeUtils;
import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.query.AssetPulldownQuery;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.OsQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p> CPE表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetHardSoftLibServiceImpl extends BaseServiceImpl<AssetHardSoftLib> implements IAssetHardSoftLibService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetHardSoftLibDao                                       assetHardSoftLibDao;
    @Resource
    private BaseConverter<AssetHardSoftLibRequest, AssetHardSoftLib>  requestConverter;
    @Resource
    private BaseConverter<AssetHardSoftLib, AssetHardSoftLibResponse> responseConverter;
    @Resource
    private AssetSoftwareRelationDao                                  assetSoftwareRelationDao;

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
    public PageResult<AssetHardSoftLibResponse> queryPageAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception {
        return new PageResult<AssetHardSoftLibResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetHardSoftLib(query));
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
    public List<SelectResponse> pullDownOs(OsQuery query) {
        query.setType("o");
        query.setIsfilter(true);
        List<SelectResponse> osList = assetHardSoftLibDao.pullDownOs(query);
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
    public List<SelectResponse> pulldownVersion(AssetPulldownQuery query) throws UnsupportedEncodingException {
        List<SelectResponse> result = new ArrayList<>();
        if (query.getVersion() != null) {
            String posStr = query.getName() + query.getSupplier();
            query.setPos(posStr.length() + 9);
            query.setVersion(query.getVersion().replace(" ", ":"));
        }
        List<AssetHardSoftLib> assetHardSoftLibs = assetHardSoftLibDao.queryHardSoftLibByVersion(query);
        for (AssetHardSoftLib assetHardSoftLib : assetHardSoftLibs) {
            SelectResponse response = new SelectResponse();
            // 特殊处理
            // 版本为 cpe_uri 除前缀厂商名产品名的部分， cpe:/a:厂商名:产品名:后面的部分 且：用空格代替
            String m = assetHardSoftLib.getSupplier() + ":" + assetHardSoftLib.getProductName() + ":";
            response.setValue(assetHardSoftLib.getCpeUri().substring(7 + m.length()));
            response.setId(Objects.toString(assetHardSoftLib.getBusinessId()));
            result.add(response);
        }
        return result;
    }

}