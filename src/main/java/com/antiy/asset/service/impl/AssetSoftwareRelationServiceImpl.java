package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资产软件关系信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Slf4j
public class AssetSoftwareRelationServiceImpl extends BaseServiceImpl<AssetSoftwareRelation> implements IAssetSoftwareRelationService {


    @Resource
    private AssetSoftwareRelationDao assetSoftwareRelationDao;
    @Resource
    private BaseConverter<AssetSoftwareRelationRequest, AssetSoftwareRelation> requestConverter;
    @Resource
    private BaseConverter<AssetSoftwareRelation, AssetSoftwareRelationResponse> responseConverter;

    @Override
    public Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        return assetSoftwareRelationDao.insert(assetSoftwareRelation);
    }

    @Override
    public Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception {
        AssetSoftwareRelation assetSoftwareRelation = requestConverter.convert(request, AssetSoftwareRelation.class);
        return assetSoftwareRelationDao.update(assetSoftwareRelation);
    }

    @Override
    public List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        List<AssetSoftwareRelation> assetSoftwareRelation = assetSoftwareRelationDao.findListAssetSoftwareRelation(query);
        //TODO
        List<AssetSoftwareRelationResponse> assetSoftwareRelationResponse = new ArrayList<AssetSoftwareRelationResponse>();
        return assetSoftwareRelationResponse;
    }

    public Integer findCountAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        return assetSoftwareRelationDao.findCount(query);
    }

    @Override
    public PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetSoftwareRelation(query), query.getCurrentPage(), this.findListAssetSoftwareRelation(query));
    }
}
