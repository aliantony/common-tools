package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.convert.AssetGroupRelationQueryToDetailQueryConverter;
import com.antiy.asset.convert.AssetGroupRelationToResponseConverter;
import com.antiy.asset.convert.IDRequestConverter;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.vo.query.AssetGroupRelationDetailQuery;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.vo.response.AssetGroupRelationResponse;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.encoder.AesEncoder;

/**
 * <p> 资产与资产组关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetGroupRelationServiceImpl extends BaseServiceImpl<AssetGroupRelation>
                                           implements IAssetGroupRelationService {

    @Resource
    private AesEncoder                                                    aesEncoder;
    @Resource
    private AssetGroupRelationDao                                         assetGroupRelationDao;
    @Resource
    private BaseConverter<AssetGroupRelationRequest, AssetGroupRelation>  requestConverter;
    @Resource
    private AssetGroupRelationQueryToDetailQueryConverter                 assetGroupRelationQueryToDetailQueryConverter;
    @Resource
    private BaseConverter<AssetGroupRelation, AssetGroupRelationResponse> responseConverter;
    @Resource
    private AssetGroupRelationToResponseConverter                         assetGroupRelationToResponseConverter;
    @Resource
    private BaseConverter<AssetNetworkCard, AssetNetworkCardResponse>     assetNetworkCardToResponseConverter;
    @Resource
    private AssetNetworkCardDao                                           assetNetworkCardDao;
    @Resource
    private IDRequestConverter                                            idRequestConverter;

    @Override
    public Integer saveAssetGroupRelation(AssetGroupRelationRequest request) throws Exception {
        AssetGroupRelation assetGroupRelation = requestConverter.convert(request, AssetGroupRelation.class);
        return assetGroupRelationDao.insert(assetGroupRelation);
    }

    @Override
    public Integer updateAssetGroupRelation(AssetGroupRelationRequest request) throws Exception {
        AssetGroupRelation assetGroupRelation = requestConverter.convert(request, AssetGroupRelation.class);
        return assetGroupRelationDao.update(assetGroupRelation);
    }

    @Override
    public List<AssetGroupRelationResponse> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception {
        List<AssetGroupRelation> assetGroupRelation = assetGroupRelationDao.findListAssetGroupRelation(query);
        List<AssetGroupRelationResponse> assetGroupRelationResponse = new ArrayList<>();
        return assetGroupRelationResponse;
    }

    public Integer findCountAssetGroupRelation(AssetGroupRelationQuery query) throws Exception {
        return assetGroupRelationDao.findCount(query);
    }

    @Override
    public PageResult<AssetGroupRelationResponse> findPageAssetGroupRelation(AssetGroupRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetGroupRelation(query), query.getCurrentPage(),
            this.findListAssetGroupRelation(query));
    }

    @Override
    public List<AssetGroupRelationResponse> findAssetDetailByAssetGroupId(AssetGroupRelationQuery query) {
        AssetGroupRelationDetailQuery assetGroupRelationDetailQuery = assetGroupRelationQueryToDetailQueryConverter
            .convert(query, AssetGroupRelationDetailQuery.class);
        assetGroupRelationDetailQuery.setAssetGroupId(assetGroupRelationDetailQuery.getAssetGroupId());

        List<AssetGroupRelation> assetGroupRelationList = assetGroupRelationDao.findAssetDetailByAssetGroupId(query);
        List<AssetGroupRelationResponse> assetGroupRelationResponseList = assetGroupRelationToResponseConverter
            .convert(assetGroupRelationList, AssetGroupRelationResponse.class);
        for (AssetGroupRelationResponse assetGroupRelationResponse : assetGroupRelationResponseList) {
            List<AssetNetworkCardResponse> assetNetworkCardResponseList = assetNetworkCardToResponseConverter.convert(
                assetNetworkCardDao.findNetworkCardByAssetId(Integer.valueOf(assetGroupRelationResponse.getStringId())),
                AssetNetworkCardResponse.class);
            assetGroupRelationResponse.setNetworkCardResponseList(assetNetworkCardResponseList);
        }

        return assetGroupRelationResponseList;
    }

    @Override
    public PageResult<AssetGroupRelationResponse> findPageAssetByAssetGroupId(AssetGroupRelationQuery query) throws Exception {
        return new PageResult<AssetGroupRelationResponse>(query.getPageSize(), this.findCountDetailByGroupId(query),
            query.getCurrentPage(), this.findAssetDetailByAssetGroupId(query));
    }

    @Override
    public Integer findCountDetailByGroupId(AssetGroupRelationQuery query) {
        return assetGroupRelationDao.findCountDetailByGroupId(idRequestConverter.convert(query.getAssetGroupId(),Integer.class));
    }
}
