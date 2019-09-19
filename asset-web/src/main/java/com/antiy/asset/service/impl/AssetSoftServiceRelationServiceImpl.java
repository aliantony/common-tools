package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetSoftServiceRelation;
import com.antiy.asset.dao.AssetSoftServiceRelationDao;
import com.antiy.asset.service.IAssetSoftServiceRelationService;
import com.antiy.asset.vo.request.AssetSoftServiceRelationRequest;
import com.antiy.asset.vo.response.AssetSoftServiceRelationResponse;
import com.antiy.asset.vo.query.AssetSoftServiceRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 软件依赖的服务 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetSoftServiceRelationServiceImpl extends BaseServiceImpl<AssetSoftServiceRelation>
                                                 implements IAssetSoftServiceRelationService {

    private Logger                                                                    logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetSoftServiceRelationDao                                               assetSoftServiceRelationDao;
    @Resource
    private BaseConverter<AssetSoftServiceRelationRequest, AssetSoftServiceRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftServiceRelation, AssetSoftServiceRelationResponse> responseConverter;

    @Override
    public String saveAssetSoftServiceRelation(AssetSoftServiceRelationRequest request) throws Exception {
        AssetSoftServiceRelation assetSoftServiceRelation = requestConverter.convert(request,
            AssetSoftServiceRelation.class);
        assetSoftServiceRelationDao.insert(assetSoftServiceRelation);
        return assetSoftServiceRelation.getStringId();
    }

    @Override
    public String updateAssetSoftServiceRelation(AssetSoftServiceRelationRequest request) throws Exception {
        AssetSoftServiceRelation assetSoftServiceRelation = requestConverter.convert(request,
            AssetSoftServiceRelation.class);
        return assetSoftServiceRelationDao.update(assetSoftServiceRelation).toString();
    }

    @Override
    public List<AssetSoftServiceRelationResponse> queryListAssetSoftServiceRelation(AssetSoftServiceRelationQuery query) throws Exception {
        List<AssetSoftServiceRelation> assetSoftServiceRelationList = assetSoftServiceRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetSoftServiceRelationList, AssetSoftServiceRelationResponse.class);
    }

    @Override
    public PageResult<AssetSoftServiceRelationResponse> queryPageAssetSoftServiceRelation(AssetSoftServiceRelationQuery query) throws Exception {
        return new PageResult<AssetSoftServiceRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetSoftServiceRelation(query));
    }

    @Override
    public AssetSoftServiceRelationResponse queryAssetSoftServiceRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetSoftServiceRelationResponse assetSoftServiceRelationResponse = responseConverter.convert(
            assetSoftServiceRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetSoftServiceRelationResponse.class);
        return assetSoftServiceRelationResponse;
    }

    @Override
    public String deleteAssetSoftServiceRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetSoftServiceRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
