package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.entity.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.entity.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.entity.vo.query.AssetLinkRelationQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 通联关系表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetLinkRelationServiceImpl extends BaseServiceImpl<AssetLinkRelation> implements IAssetLinkRelationService{


        @Resource
        private AssetLinkRelationDao assetLinkRelationDao;

        private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
        
        private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;

        @Override
        public Integer saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
            AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
            return assetLinkRelationDao.insert(assetLinkRelation);
        }

        @Override
        public Integer updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
            AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
            return assetLinkRelationDao.update(assetLinkRelation);
        }

        @Override
        public List<AssetLinkRelationResponse> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
            return assetLinkRelationDao.findListAssetLinkRelation(query);
        }

        public Integer findCountAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
            return assetLinkRelationDao.findCount(query);
        }

        @Override
        public PageResult<AssetLinkRelationResponse> findPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
            return new PageResult<>(this.findCountAssetLinkRelation(query),this.findListAssetLinkRelation(query));
        }
}
