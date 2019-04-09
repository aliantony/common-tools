package com.antiy.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.common.base.BusinessData;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
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
import com.antiy.common.utils.LoginUserUtil;
import org.springframework.transaction.annotation.Transactional;

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
    private static final Logger                                           logger = LogUtils
        .get(AssetLabelRelationServiceImpl.class);

    @Override
    @Transactional
    public Integer saveAssetLabelRelation(AssetLabelRelationRequest request) throws Exception {
        AssetLabelRelation assetLabelRelation = requestConverter.convert(request, AssetLabelRelation.class);
        assetLabelRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLabelRelation.setGmtCreate(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LABEL_RELATION_INSERT.getName(),
            assetLabelRelation.getId(), null, assetLabelRelation, BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_LABEL_RELATION_INSERT.getName() + "{}", assetLabelRelation);
        return assetLabelRelationDao.insert(assetLabelRelation);
    }

    @Override
    @Transactional
    public Integer updateAssetLabelRelation(AssetLabelRelationRequest request) throws Exception {
        AssetLabelRelation assetLabelRelation = requestConverter.convert(request, AssetLabelRelation.class);
        assetLabelRelation.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetLabelRelation.setGmtModified(System.currentTimeMillis());
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.ASSET_LABEL_RELATION_UPDATE.getName(),
                assetLabelRelation.getId(), null, assetLabelRelation, BusinessModuleEnum.HARD_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.ASSET_LABEL_RELATION_UPDATE.getName() + "{}", assetLabelRelation);

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
