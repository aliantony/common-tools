package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetCpeFilter;
import com.antiy.asset.dao.AssetCpeFilterDao;
import com.antiy.asset.service.IAssetCpeFilterService;
import com.antiy.asset.vo.request.AssetCpeFilterRequest;
import com.antiy.asset.vo.response.AssetCpeFilterResponse;
import com.antiy.asset.vo.query.AssetCpeFilterQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 过滤显示表（筛选指定的数据给用户） 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetCpeFilterServiceImpl extends BaseServiceImpl<AssetCpeFilter> implements IAssetCpeFilterService {

    private Logger                                                logger = LogUtils.get(this.getClass());

    @Resource
    private AssetCpeFilterDao                                     assetCpeFilterDao;
    @Resource
    private BaseConverter<AssetCpeFilterRequest, AssetCpeFilter>  requestConverter;
    @Resource
    private BaseConverter<AssetCpeFilter, AssetCpeFilterResponse> responseConverter;

    @Override
    public String saveAssetCpeFilter(AssetCpeFilterRequest request) throws Exception {
        AssetCpeFilter assetCpeFilter = requestConverter.convert(request, AssetCpeFilter.class);
        assetCpeFilterDao.insert(assetCpeFilter);
        return assetCpeFilter.getStringId();
    }

    @Override
    public String updateAssetCpeFilter(AssetCpeFilterRequest request) throws Exception {
        AssetCpeFilter assetCpeFilter = requestConverter.convert(request, AssetCpeFilter.class);
        return assetCpeFilterDao.update(assetCpeFilter).toString();
    }

    @Override
    public List<AssetCpeFilterResponse> queryListAssetCpeFilter(AssetCpeFilterQuery query) throws Exception {
        List<AssetCpeFilter> assetCpeFilterList = assetCpeFilterDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetCpeFilterList, AssetCpeFilterResponse.class);
    }

    @Override
    public PageResult<AssetCpeFilterResponse> queryPageAssetCpeFilter(AssetCpeFilterQuery query) throws Exception {
        return new PageResult<AssetCpeFilterResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetCpeFilter(query));
    }

    @Override
    public AssetCpeFilterResponse queryAssetCpeFilterById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetCpeFilterResponse assetCpeFilterResponse = responseConverter
            .convert(assetCpeFilterDao.getById(queryCondition.getPrimaryKey()), AssetCpeFilterResponse.class);
        return assetCpeFilterResponse;
    }

    @Override
    public String deleteAssetCpeFilterById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetCpeFilterDao.deleteById(baseRequest.getStringId()).toString();
    }
}
