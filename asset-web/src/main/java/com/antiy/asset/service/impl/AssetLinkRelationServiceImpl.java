package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.biz.util.LoginUserUtil;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetLinkRelationServiceImpl extends BaseServiceImpl<AssetLinkRelation>
                                          implements IAssetLinkRelationService {

    @Resource
    private AssetLinkRelationDao                                        assetLinkRelationDao;
    @Resource
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;

    @Override
    public Integer saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        assetLinkRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLinkRelation.setGmtCreate(System.currentTimeMillis());
        return assetLinkRelationDao.insert(assetLinkRelation);
    }

    @Override
    public Integer updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        assetLinkRelation.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetLinkRelation.setGmtModified(System.currentTimeMillis());
        return assetLinkRelationDao.update(assetLinkRelation);
    }

    @Override
    public List<AssetLinkRelationResponse> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        List<AssetLinkRelation> assetLinkRelation = assetLinkRelationDao.findListAssetLinkRelation(query);
        // TODO
        List<AssetLinkRelationResponse> assetLinkRelationResponse = new ArrayList<AssetLinkRelationResponse>();
        return assetLinkRelationResponse;
    }

    public Integer findCountAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        return assetLinkRelationDao.findCount(query);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> findPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetLinkRelation(query), query.getCurrentPage(),
            this.findListAssetLinkRelation(query));
    }
}
