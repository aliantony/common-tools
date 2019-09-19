package com.antiy.asset.service.impl;

import com.antiy.common.base.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;

import com.antiy.common.utils.LogUtils;
import org.springframework.stereotype.Service;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.antiy.asset.entity.AssetHardAssemblyRelation;
import com.antiy.asset.dao.AssetHardAssemblyRelationDao;
import com.antiy.asset.service.IAssetHardAssemblyRelationService;
import com.antiy.asset.vo.request.AssetHardAssemblyRelationRequest;
import com.antiy.asset.vo.response.AssetHardAssemblyRelationResponse;
import com.antiy.asset.vo.query.AssetHardAssemblyRelationQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> 硬件与组件关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
@Service
public class AssetHardAssemblyRelationServiceImpl extends BaseServiceImpl<AssetHardAssemblyRelation>
                                                  implements IAssetHardAssemblyRelationService {

    private Logger                                                                      logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetHardAssemblyRelationDao                                                assetHardAssemblyRelationDao;
    @Resource
    private BaseConverter<AssetHardAssemblyRelationRequest, AssetHardAssemblyRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetHardAssemblyRelation, AssetHardAssemblyRelationResponse> responseConverter;

    @Override
    public String saveAssetHardAssemblyRelation(AssetHardAssemblyRelationRequest request) throws Exception {
        AssetHardAssemblyRelation assetHardAssemblyRelation = requestConverter.convert(request,
            AssetHardAssemblyRelation.class);
        assetHardAssemblyRelationDao.insert(assetHardAssemblyRelation);
        return assetHardAssemblyRelation.getStringId();
    }

    @Override
    public String updateAssetHardAssemblyRelation(AssetHardAssemblyRelationRequest request) throws Exception {
        AssetHardAssemblyRelation assetHardAssemblyRelation = requestConverter.convert(request,
            AssetHardAssemblyRelation.class);
        return assetHardAssemblyRelationDao.update(assetHardAssemblyRelation).toString();
    }

    @Override
    public List<AssetHardAssemblyRelationResponse> queryListAssetHardAssemblyRelation(AssetHardAssemblyRelationQuery query) throws Exception {
        List<AssetHardAssemblyRelation> assetHardAssemblyRelationList = assetHardAssemblyRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetHardAssemblyRelationList, AssetHardAssemblyRelationResponse.class);
    }

    @Override
    public PageResult<AssetHardAssemblyRelationResponse> queryPageAssetHardAssemblyRelation(AssetHardAssemblyRelationQuery query) throws Exception {
        return new PageResult<AssetHardAssemblyRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetHardAssemblyRelation(query));
    }

    @Override
    public AssetHardAssemblyRelationResponse queryAssetHardAssemblyRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetHardAssemblyRelationResponse assetHardAssemblyRelationResponse = responseConverter.convert(
            assetHardAssemblyRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetHardAssemblyRelationResponse.class);
        return assetHardAssemblyRelationResponse;
    }

    @Override
    public String deleteAssetHardAssemblyRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetHardAssemblyRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
