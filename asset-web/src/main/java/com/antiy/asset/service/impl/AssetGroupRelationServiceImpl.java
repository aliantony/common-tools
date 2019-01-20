package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.query.AssetGroupRelationDetailQuery;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.vo.response.AssetGroupRelationResponse;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
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
    private AesEncoder            aesEncoder;
    @Resource
    private AssetGroupRelationDao assetGroupRelationDao;
    @Resource
    private AssetNetworkCardDao   assetNetworkCardDao;

    @Override
    public Integer saveAssetGroupRelation(AssetGroupRelationRequest request) throws Exception {
        AssetGroupRelation assetGroupRelation = (AssetGroupRelation) BeanConvert.convert(request,
            AssetGroupRelation.class);
        return assetGroupRelationDao.insert(assetGroupRelation);
    }

    @Override
    public Integer updateAssetGroupRelation(AssetGroupRelationRequest request) throws Exception {
        AssetGroupRelation assetGroupRelation = (AssetGroupRelation) BeanConvert.convert(request,
            AssetGroupRelation.class);
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
    public List<AssetGroupRelationResponse> findAssetDetailByAssetGroupId(AssetGroupRelationQuery query) throws Exception {
        AssetGroupRelationDetailQuery assetGroupRelationDetailQuery = (AssetGroupRelationDetailQuery) BeanConvert
            .convert(query, AssetGroupRelationDetailQuery.class);
        assetGroupRelationDetailQuery.setAssetGroupId(assetGroupRelationDetailQuery.getAssetGroupId());

        List<AssetGroupRelation> assetGroupRelationList = assetGroupRelationDao.findAssetDetailByAssetGroupId(query);
        List<AssetGroupRelationResponse> assetGroupRelationResponseList = BeanConvert.convert(assetGroupRelationList,
            AssetGroupRelationResponse.class);
        for (AssetGroupRelationResponse assetGroupRelationResponse : assetGroupRelationResponseList) {
            List<AssetNetworkCardResponse> assetNetworkCardResponseList = BeanConvert.convert(
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
    public Integer findCountDetailByGroupId(AssetGroupRelationQuery query) throws Exception {
        return assetGroupRelationDao
            .findCountDetailByGroupId((Integer) BeanConvert.convert(query.getAssetGroupId(), Integer.class));
    }
}
