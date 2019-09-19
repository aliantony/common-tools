package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetServiceProtocolRelation;
import com.antiy.asset.dao.AssetServiceProtocolRelationDao;
import com.antiy.asset.service.IAssetServiceProtocolRelationService;
import com.antiy.asset.vo.request.AssetServiceProtocolRelationRequest;
import com.antiy.asset.vo.response.AssetServiceProtocolRelationResponse;
import com.antiy.asset.vo.query.AssetServiceProtocolRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 服务与协议表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetServiceProtocolRelationServiceImpl extends BaseServiceImpl<AssetServiceProtocolRelation>
                                                     implements IAssetServiceProtocolRelationService {

    private Logger                                                                            logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetServiceProtocolRelationDao                                                   assetServiceProtocolRelationDao;
    @Resource
    private BaseConverter<AssetServiceProtocolRelationRequest, AssetServiceProtocolRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetServiceProtocolRelation, AssetServiceProtocolRelationResponse> responseConverter;

    @Override
    public String saveAssetServiceProtocolRelation(AssetServiceProtocolRelationRequest request) throws Exception {
        AssetServiceProtocolRelation assetServiceProtocolRelation = requestConverter.convert(request,
            AssetServiceProtocolRelation.class);
        assetServiceProtocolRelationDao.insert(assetServiceProtocolRelation);
        return assetServiceProtocolRelation.getStringId();
    }

    @Override
    public String updateAssetServiceProtocolRelation(AssetServiceProtocolRelationRequest request) throws Exception {
        AssetServiceProtocolRelation assetServiceProtocolRelation = requestConverter.convert(request,
            AssetServiceProtocolRelation.class);
        return assetServiceProtocolRelationDao.update(assetServiceProtocolRelation).toString();
    }

    @Override
    public List<AssetServiceProtocolRelationResponse> queryListAssetServiceProtocolRelation(AssetServiceProtocolRelationQuery query) throws Exception {
        List<AssetServiceProtocolRelation> assetServiceProtocolRelationList = assetServiceProtocolRelationDao
            .findQuery(query);
        // TODO
        return responseConverter.convert(assetServiceProtocolRelationList, AssetServiceProtocolRelationResponse.class);
    }

    @Override
    public PageResult<AssetServiceProtocolRelationResponse> queryPageAssetServiceProtocolRelation(AssetServiceProtocolRelationQuery query) throws Exception {
        return new PageResult<AssetServiceProtocolRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetServiceProtocolRelation(query));
    }

    @Override
    public AssetServiceProtocolRelationResponse queryAssetServiceProtocolRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetServiceProtocolRelationResponse assetServiceProtocolRelationResponse = responseConverter.convert(
            assetServiceProtocolRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetServiceProtocolRelationResponse.class);
        return assetServiceProtocolRelationResponse;
    }

    @Override
    public String deleteAssetServiceProtocolRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetServiceProtocolRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
