package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetMacRelation;
import com.antiy.asset.dao.AssetMacRelationDao;
import com.antiy.asset.service.IAssetMacRelationService;
import com.antiy.asset.vo.request.AssetMacRelationRequest;
import com.antiy.asset.vo.response.AssetMacRelationResponse;
import com.antiy.asset.vo.query.AssetMacRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 资产-MAC关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetMacRelationServiceImpl extends BaseServiceImpl<AssetMacRelation> implements IAssetMacRelationService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetMacRelationDao                                       assetMacRelationDao;
    @Resource
    private BaseConverter<AssetMacRelationRequest, AssetMacRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetMacRelation, AssetMacRelationResponse> responseConverter;

    @Override
    public String saveAssetMacRelation(AssetMacRelationRequest request) throws Exception {
        AssetMacRelation assetMacRelation = requestConverter.convert(request, AssetMacRelation.class);
        assetMacRelationDao.insert(assetMacRelation);
        return assetMacRelation.getStringId();
    }

    @Override
    public String updateAssetMacRelation(AssetMacRelationRequest request) throws Exception {
        AssetMacRelation assetMacRelation = requestConverter.convert(request, AssetMacRelation.class);
        return assetMacRelationDao.update(assetMacRelation).toString();
    }

    @Override
    public List<AssetMacRelationResponse> queryListAssetMacRelation(AssetMacRelationQuery query) throws Exception {
        List<AssetMacRelation> assetMacRelationList = assetMacRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetMacRelationList, AssetMacRelationResponse.class);
    }

    @Override
    public PageResult<AssetMacRelationResponse> queryPageAssetMacRelation(AssetMacRelationQuery query) throws Exception {
        return new PageResult<AssetMacRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetMacRelation(query));
    }

    @Override
    public AssetMacRelationResponse queryAssetMacRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetMacRelationResponse assetMacRelationResponse = responseConverter
            .convert(assetMacRelationDao.getById(queryCondition.getPrimaryKey()), AssetMacRelationResponse.class);
        return assetMacRelationResponse;
    }

    @Override
    public String deleteAssetMacRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetMacRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
