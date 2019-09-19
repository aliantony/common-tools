package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetIpRelation;
import com.antiy.asset.dao.AssetIpRelationDao;
import com.antiy.asset.service.IAssetIpRelationService;
import com.antiy.asset.vo.request.AssetIpRelationRequest;
import com.antiy.asset.vo.response.AssetIpRelationResponse;
import com.antiy.asset.vo.query.AssetIpRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 资产-IP关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetIpRelationServiceImpl extends BaseServiceImpl<AssetIpRelation> implements IAssetIpRelationService {

    private Logger                                                  logger = LogUtils.get(this.getClass());

    @Resource
    private AssetIpRelationDao                                      assetIpRelationDao;
    @Resource
    private BaseConverter<AssetIpRelationRequest, AssetIpRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetIpRelation, AssetIpRelationResponse> responseConverter;

    @Override
    public String saveAssetIpRelation(AssetIpRelationRequest request) throws Exception {
        AssetIpRelation assetIpRelation = requestConverter.convert(request, AssetIpRelation.class);
        assetIpRelationDao.insert(assetIpRelation);
        return assetIpRelation.getStringId();
    }

    @Override
    public String updateAssetIpRelation(AssetIpRelationRequest request) throws Exception {
        AssetIpRelation assetIpRelation = requestConverter.convert(request, AssetIpRelation.class);
        return assetIpRelationDao.update(assetIpRelation).toString();
    }

    @Override
    public List<AssetIpRelationResponse> queryListAssetIpRelation(AssetIpRelationQuery query) throws Exception {
        List<AssetIpRelation> assetIpRelationList = assetIpRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetIpRelationList, AssetIpRelationResponse.class);
    }

    @Override
    public PageResult<AssetIpRelationResponse> queryPageAssetIpRelation(AssetIpRelationQuery query) throws Exception {
        return new PageResult<AssetIpRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetIpRelation(query));
    }

    @Override
    public AssetIpRelationResponse queryAssetIpRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetIpRelationResponse assetIpRelationResponse = responseConverter
            .convert(assetIpRelationDao.getById(queryCondition.getPrimaryKey()), AssetIpRelationResponse.class);
        return assetIpRelationResponse;
    }

    @Override
    public String deleteAssetIpRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetIpRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
