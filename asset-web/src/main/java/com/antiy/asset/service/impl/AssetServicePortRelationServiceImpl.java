package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetServicePortRelation;
import com.antiy.asset.dao.AssetServicePortRelationDao;
import com.antiy.asset.service.IAssetServicePortRelationService;
import com.antiy.asset.vo.request.AssetServicePortRelationRequest;
import com.antiy.asset.vo.response.AssetServicePortRelationResponse;
import com.antiy.asset.vo.query.AssetServicePortRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 服务与端口表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetServicePortRelationServiceImpl extends BaseServiceImpl<AssetServicePortRelation>
                                                 implements IAssetServicePortRelationService {

    private Logger                                                                    logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetServicePortRelationDao                                               assetServicePortRelationDao;
    @Resource
    private BaseConverter<AssetServicePortRelationRequest, AssetServicePortRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetServicePortRelation, AssetServicePortRelationResponse> responseConverter;

    @Override
    public String saveAssetServicePortRelation(AssetServicePortRelationRequest request) throws Exception {
        AssetServicePortRelation assetServicePortRelation = requestConverter.convert(request,
            AssetServicePortRelation.class);
        assetServicePortRelationDao.insert(assetServicePortRelation);
        return assetServicePortRelation.getStringId();
    }

    @Override
    public String updateAssetServicePortRelation(AssetServicePortRelationRequest request) throws Exception {
        AssetServicePortRelation assetServicePortRelation = requestConverter.convert(request,
            AssetServicePortRelation.class);
        return assetServicePortRelationDao.update(assetServicePortRelation).toString();
    }

    @Override
    public List<AssetServicePortRelationResponse> queryListAssetServicePortRelation(AssetServicePortRelationQuery query) throws Exception {
        List<AssetServicePortRelation> assetServicePortRelationList = assetServicePortRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetServicePortRelationList, AssetServicePortRelationResponse.class);
    }

    @Override
    public PageResult<AssetServicePortRelationResponse> queryPageAssetServicePortRelation(AssetServicePortRelationQuery query) throws Exception {
        return new PageResult<AssetServicePortRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetServicePortRelation(query));
    }

    @Override
    public AssetServicePortRelationResponse queryAssetServicePortRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetServicePortRelationResponse assetServicePortRelationResponse = responseConverter.convert(
            assetServicePortRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetServicePortRelationResponse.class);
        return assetServicePortRelationResponse;
    }

    @Override
    public String deleteAssetServicePortRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetServicePortRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
