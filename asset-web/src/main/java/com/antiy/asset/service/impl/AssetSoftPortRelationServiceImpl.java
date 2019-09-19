package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetSoftPortRelation;
import com.antiy.asset.dao.AssetSoftPortRelationDao;
import com.antiy.asset.service.IAssetSoftPortRelationService;
import com.antiy.asset.vo.request.AssetSoftPortRelationRequest;
import com.antiy.asset.vo.response.AssetSoftPortRelationResponse;
import com.antiy.asset.vo.query.AssetSoftPortRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 软件与端口表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetSoftPortRelationServiceImpl extends BaseServiceImpl<AssetSoftPortRelation>
                                              implements IAssetSoftPortRelationService {

    private Logger                                                              logger = LogUtils.get(this.getClass());

    @Resource
    private AssetSoftPortRelationDao                                            assetSoftPortRelationDao;
    @Resource
    private BaseConverter<AssetSoftPortRelationRequest, AssetSoftPortRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftPortRelation, AssetSoftPortRelationResponse> responseConverter;

    @Override
    public String saveAssetSoftPortRelation(AssetSoftPortRelationRequest request) throws Exception {
        AssetSoftPortRelation assetSoftPortRelation = requestConverter.convert(request, AssetSoftPortRelation.class);
        assetSoftPortRelationDao.insert(assetSoftPortRelation);
        return assetSoftPortRelation.getStringId();
    }

    @Override
    public String updateAssetSoftPortRelation(AssetSoftPortRelationRequest request) throws Exception {
        AssetSoftPortRelation assetSoftPortRelation = requestConverter.convert(request, AssetSoftPortRelation.class);
        return assetSoftPortRelationDao.update(assetSoftPortRelation).toString();
    }

    @Override
    public List<AssetSoftPortRelationResponse> queryListAssetSoftPortRelation(AssetSoftPortRelationQuery query) throws Exception {
        List<AssetSoftPortRelation> assetSoftPortRelationList = assetSoftPortRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetSoftPortRelationList, AssetSoftPortRelationResponse.class);
    }

    @Override
    public PageResult<AssetSoftPortRelationResponse> queryPageAssetSoftPortRelation(AssetSoftPortRelationQuery query) throws Exception {
        return new PageResult<AssetSoftPortRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetSoftPortRelation(query));
    }

    @Override
    public AssetSoftPortRelationResponse queryAssetSoftPortRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetSoftPortRelationResponse assetSoftPortRelationResponse = responseConverter.convert(
            assetSoftPortRelationDao.getById(queryCondition.getPrimaryKey()), AssetSoftPortRelationResponse.class);
        return assetSoftPortRelationResponse;
    }

    @Override
    public String deleteAssetSoftPortRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetSoftPortRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
