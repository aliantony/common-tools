package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetSoftProtocolRelation;
import com.antiy.asset.dao.AssetSoftProtocolRelationDao;
import com.antiy.asset.service.IAssetSoftProtocolRelationService;
import com.antiy.asset.vo.request.AssetSoftProtocolRelationRequest;
import com.antiy.asset.vo.response.AssetSoftProtocolRelationResponse;
import com.antiy.asset.vo.query.AssetSoftProtocolRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 软件与协议表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetSoftProtocolRelationServiceImpl extends BaseServiceImpl<AssetSoftProtocolRelation>
                                                  implements IAssetSoftProtocolRelationService {

    private Logger                                                                      logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetSoftProtocolRelationDao                                                assetSoftProtocolRelationDao;
    @Resource
    private BaseConverter<AssetSoftProtocolRelationRequest, AssetSoftProtocolRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftProtocolRelation, AssetSoftProtocolRelationResponse> responseConverter;

    @Override
    public String saveAssetSoftProtocolRelation(AssetSoftProtocolRelationRequest request) throws Exception {
        AssetSoftProtocolRelation assetSoftProtocolRelation = requestConverter.convert(request,
            AssetSoftProtocolRelation.class);
        assetSoftProtocolRelationDao.insert(assetSoftProtocolRelation);
        return assetSoftProtocolRelation.getStringId();
    }

    @Override
    public String updateAssetSoftProtocolRelation(AssetSoftProtocolRelationRequest request) throws Exception {
        AssetSoftProtocolRelation assetSoftProtocolRelation = requestConverter.convert(request,
            AssetSoftProtocolRelation.class);
        return assetSoftProtocolRelationDao.update(assetSoftProtocolRelation).toString();
    }

    @Override
    public List<AssetSoftProtocolRelationResponse> queryListAssetSoftProtocolRelation(AssetSoftProtocolRelationQuery query) throws Exception {
        List<AssetSoftProtocolRelation> assetSoftProtocolRelationList = assetSoftProtocolRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetSoftProtocolRelationList, AssetSoftProtocolRelationResponse.class);
    }

    @Override
    public PageResult<AssetSoftProtocolRelationResponse> queryPageAssetSoftProtocolRelation(AssetSoftProtocolRelationQuery query) throws Exception {
        return new PageResult<AssetSoftProtocolRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetSoftProtocolRelation(query));
    }

    @Override
    public AssetSoftProtocolRelationResponse queryAssetSoftProtocolRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetSoftProtocolRelationResponse assetSoftProtocolRelationResponse = responseConverter.convert(
            assetSoftProtocolRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetSoftProtocolRelationResponse.class);
        return assetSoftProtocolRelationResponse;
    }

    @Override
    public String deleteAssetSoftProtocolRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetSoftProtocolRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
