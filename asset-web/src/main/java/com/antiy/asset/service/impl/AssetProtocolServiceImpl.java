package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetProtocol;
import com.antiy.asset.dao.AssetProtocolDao;
import com.antiy.asset.service.IAssetProtocolService;
import com.antiy.asset.vo.request.AssetProtocolRequest;
import com.antiy.asset.vo.response.AssetProtocolResponse;
import com.antiy.asset.vo.query.AssetProtocolQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 协议表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetProtocolServiceImpl extends BaseServiceImpl<AssetProtocol> implements IAssetProtocolService {

    private Logger                                              logger = LogUtils.get(this.getClass());

    @Resource
    private AssetProtocolDao                                    assetProtocolDao;
    @Resource
    private BaseConverter<AssetProtocolRequest, AssetProtocol>  requestConverter;
    @Resource
    private BaseConverter<AssetProtocol, AssetProtocolResponse> responseConverter;

    @Override
    public String saveAssetProtocol(AssetProtocolRequest request) throws Exception {
        AssetProtocol assetProtocol = requestConverter.convert(request, AssetProtocol.class);
        assetProtocolDao.insert(assetProtocol);
        return assetProtocol.getStringId();
    }

    @Override
    public String updateAssetProtocol(AssetProtocolRequest request) throws Exception {
        AssetProtocol assetProtocol = requestConverter.convert(request, AssetProtocol.class);
        return assetProtocolDao.update(assetProtocol).toString();
    }

    @Override
    public List<AssetProtocolResponse> queryListAssetProtocol(AssetProtocolQuery query) throws Exception {
        List<AssetProtocol> assetProtocolList = assetProtocolDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetProtocolList, AssetProtocolResponse.class);
    }

    @Override
    public PageResult<AssetProtocolResponse> queryPageAssetProtocol(AssetProtocolQuery query) throws Exception {
        return new PageResult<AssetProtocolResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.queryListAssetProtocol(query));
    }

    @Override
    public AssetProtocolResponse queryAssetProtocolById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetProtocolResponse assetProtocolResponse = responseConverter
            .convert(assetProtocolDao.getById(queryCondition.getPrimaryKey()), AssetProtocolResponse.class);
        return assetProtocolResponse;
    }

    @Override
    public String deleteAssetProtocolById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetProtocolDao.deleteById(baseRequest.getStringId()).toString();
    }
}
