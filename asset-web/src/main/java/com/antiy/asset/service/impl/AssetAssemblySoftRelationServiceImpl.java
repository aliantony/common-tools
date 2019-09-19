package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetAssemblySoftRelation;
import com.antiy.asset.dao.AssetAssemblySoftRelationDao;
import com.antiy.asset.service.IAssetAssemblySoftRelationService;
import com.antiy.asset.vo.request.AssetAssemblySoftRelationRequest;
import com.antiy.asset.vo.response.AssetAssemblySoftRelationResponse;
import com.antiy.asset.vo.query.AssetAssemblySoftRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 组件与软件关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetAssemblySoftRelationServiceImpl extends BaseServiceImpl<AssetAssemblySoftRelation>
                                                  implements IAssetAssemblySoftRelationService {

    private Logger                                                                      logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetAssemblySoftRelationDao                                                assetAssemblySoftRelationDao;
    @Resource
    private BaseConverter<AssetAssemblySoftRelationRequest, AssetAssemblySoftRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetAssemblySoftRelation, AssetAssemblySoftRelationResponse> responseConverter;

    @Override
    public String saveAssetAssemblySoftRelation(AssetAssemblySoftRelationRequest request) throws Exception {
        AssetAssemblySoftRelation assetAssemblySoftRelation = requestConverter.convert(request,
            AssetAssemblySoftRelation.class);
        assetAssemblySoftRelationDao.insert(assetAssemblySoftRelation);
        return assetAssemblySoftRelation.getStringId();
    }

    @Override
    public String updateAssetAssemblySoftRelation(AssetAssemblySoftRelationRequest request) throws Exception {
        AssetAssemblySoftRelation assetAssemblySoftRelation = requestConverter.convert(request,
            AssetAssemblySoftRelation.class);
        return assetAssemblySoftRelationDao.update(assetAssemblySoftRelation).toString();
    }

    @Override
    public List<AssetAssemblySoftRelationResponse> queryListAssetAssemblySoftRelation(AssetAssemblySoftRelationQuery query) throws Exception {
        List<AssetAssemblySoftRelation> assetAssemblySoftRelationList = assetAssemblySoftRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetAssemblySoftRelationList, AssetAssemblySoftRelationResponse.class);
    }

    @Override
    public PageResult<AssetAssemblySoftRelationResponse> queryPageAssetAssemblySoftRelation(AssetAssemblySoftRelationQuery query) throws Exception {
        return new PageResult<AssetAssemblySoftRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetAssemblySoftRelation(query));
    }

    @Override
    public AssetAssemblySoftRelationResponse queryAssetAssemblySoftRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetAssemblySoftRelationResponse assetAssemblySoftRelationResponse = responseConverter.convert(
            assetAssemblySoftRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetAssemblySoftRelationResponse.class);
        return assetAssemblySoftRelationResponse;
    }

    @Override
    public String deleteAssetAssemblySoftRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetAssemblySoftRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
