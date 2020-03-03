package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetMonitorRuleRelationDao;
import com.antiy.asset.entity.AssetMonitorRuleRelation;
import com.antiy.asset.service.IAssetMonitorRuleRelationService;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRelationRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 资产监控规则与资产关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
@Service
public class AssetMonitorRuleRelationServiceImpl extends BaseServiceImpl<AssetMonitorRuleRelation>
                                                 implements IAssetMonitorRuleRelationService {

    private Logger                                                                    logger = LogUtils
        .get(this.getClass());

    @Resource
    private AssetMonitorRuleRelationDao                                               assetMonitorRuleRelationDao;
    @Resource
    private BaseConverter<AssetMonitorRuleRelationRequest, AssetMonitorRuleRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetMonitorRuleRelation, AssetMonitorRuleRelationResponse> responseConverter;

    @Override
    public String saveAssetMonitorRuleRelation(AssetMonitorRuleRelationRequest request) throws Exception {
        AssetMonitorRuleRelation assetMonitorRuleRelation = requestConverter.convert(request,
            AssetMonitorRuleRelation.class);
        assetMonitorRuleRelation.setGmtCreate(System.currentTimeMillis());
        assetMonitorRuleRelationDao.insert(assetMonitorRuleRelation);
        return assetMonitorRuleRelation.getStringId();
    }

    @Override
    public String updateAssetMonitorRuleRelation(AssetMonitorRuleRelationRequest request) throws Exception {
        AssetMonitorRuleRelation assetMonitorRuleRelation = requestConverter.convert(request,
            AssetMonitorRuleRelation.class);
        return assetMonitorRuleRelationDao.update(assetMonitorRuleRelation).toString();
    }

    @Override
    public List<AssetMonitorRuleRelationResponse> queryListAssetMonitorRuleRelation(AssetMonitorRuleRelationQuery query) throws Exception {
        List<AssetMonitorRuleRelation> assetMonitorRuleRelationList = assetMonitorRuleRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetMonitorRuleRelationList, AssetMonitorRuleRelationResponse.class);
    }

    @Override
    public PageResult<AssetMonitorRuleRelationResponse> queryPageAssetMonitorRuleRelation(AssetMonitorRuleRelationQuery query) throws Exception {
        return new PageResult<AssetMonitorRuleRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetMonitorRuleRelation(query));
    }

    @Override
    public AssetMonitorRuleRelationResponse queryAssetMonitorRuleRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetMonitorRuleRelationResponse assetMonitorRuleRelationResponse = responseConverter.convert(
            assetMonitorRuleRelationDao.getById(queryCondition.getPrimaryKey()),
            AssetMonitorRuleRelationResponse.class);
        return assetMonitorRuleRelationResponse;
    }

    @Override
    public String deleteAssetMonitorRuleRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetMonitorRuleRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
