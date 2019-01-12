package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLabelRelationDao;
import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.service.IAssetLabelRelationService;
import com.antiy.asset.vo.query.AssetLabelRelationQuery;
import com.antiy.asset.vo.request.AssetLabelRelationRequest;
import com.antiy.asset.vo.response.AssetLabelRelationResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

/**
 * <p> 资产标签关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetLabelRelationServiceImpl extends BaseServiceImpl<AssetLabelRelation>
                                           implements IAssetLabelRelationService {

    @Resource
    private AssetLabelRelationDao                                         assetLabelRelationDao;
    @Resource
    private BaseConverter<AssetLabelRelationRequest, AssetLabelRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetLabelRelation, AssetLabelRelationResponse> responseConverter;

    @Override
    public Integer saveAssetLabelRelation(AssetLabelRelationRequest request) throws Exception {
        AssetLabelRelation assetLabelRelation = requestConverter.convert(request, AssetLabelRelation.class);
        return assetLabelRelationDao.insert(assetLabelRelation);
    }

    @Override
    public Integer updateAssetLabelRelation(AssetLabelRelationRequest request) throws Exception {
        AssetLabelRelation assetLabelRelation = requestConverter.convert(request, AssetLabelRelation.class);
        return assetLabelRelationDao.update(assetLabelRelation);
    }

    @Override
    public List<AssetLabelRelationResponse> findListAssetLabelRelation(AssetLabelRelationQuery query) throws Exception {
        List<AssetLabelRelation> assetLabelRelation = assetLabelRelationDao.findListAssetLabelRelation(query);
        // TODO
        List<AssetLabelRelationResponse> assetLabelRelationResponse = new ArrayList<AssetLabelRelationResponse>();
        return assetLabelRelationResponse;
    }

    public Integer findCountAssetLabelRelation(AssetLabelRelationQuery query) throws Exception {
        return assetLabelRelationDao.findCount(query);
    }

    @Override
    public PageResult<AssetLabelRelationResponse> findPageAssetLabelRelation(AssetLabelRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetLabelRelation(query), query.getCurrentPage(),
            this.findListAssetLabelRelation(query));
    }
}
