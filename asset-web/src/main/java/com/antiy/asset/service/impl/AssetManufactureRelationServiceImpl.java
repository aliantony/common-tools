package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetManufactureRelationDao;
import com.antiy.asset.entity.AssetManufactureRelation;
import com.antiy.asset.service.IAssetManufactureRelationService;
import com.antiy.asset.vo.query.AssetManufactureRelationQuery;
import com.antiy.asset.vo.request.AssetManufactureRelationRequest;
import com.antiy.asset.vo.response.AssetManufactureRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 厂商与资产关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */
@Service
public class AssetManufactureRelationServiceImpl extends BaseServiceImpl<AssetManufactureRelation>
                                                 implements IAssetManufactureRelationService {

    private Logger                                                                    logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetManufactureRelationDao                                               assetManufactureRelationDao;
    @Resource
    private BaseConverter<AssetManufactureRelationRequest, AssetManufactureRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetManufactureRelation, AssetManufactureRelationResponse> responseConverter;

    @Override
    public String saveAssetManufactureRelation(AssetManufactureRelationRequest request) throws Exception {
        AssetManufactureRelation assetManufactureRelation = requestConverter.convert(request,
            AssetManufactureRelation.class);
        assetManufactureRelationDao.insert(assetManufactureRelation);
        return assetManufactureRelation.getStringId();
    }

    @Override
    public String updateAssetManufactureRelation(AssetManufactureRelationRequest request) throws Exception {
        AssetManufactureRelation assetManufactureRelation = requestConverter.convert(request,
            AssetManufactureRelation.class);
        return assetManufactureRelationDao.update(assetManufactureRelation).toString();
    }

    @Override
    public List<AssetManufactureRelationResponse> queryListAssetManufactureRelation(AssetManufactureRelationQuery query) throws Exception {
        List<AssetManufactureRelation> assetManufactureRelationList = assetManufactureRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetManufactureRelationList, AssetManufactureRelationResponse.class);
    }

    @Override
    public PageResult<AssetManufactureRelationResponse> queryPageAssetManufactureRelation(AssetManufactureRelationQuery query) throws Exception {
        return new PageResult<AssetManufactureRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetManufactureRelation(query));
    }

    @Override
    public AssetManufactureRelationResponse queryAssetManufactureRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetManufactureRelationResponse assetManufactureRelationResponse = responseConverter.convert(
            assetManufactureRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetManufactureRelationResponse.class);
        return assetManufactureRelationResponse;
    }

    @Override
    public String deleteAssetManufactureRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetManufactureRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
