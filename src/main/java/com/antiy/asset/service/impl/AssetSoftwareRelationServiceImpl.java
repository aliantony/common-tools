package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.dao.AssetSoftwareRelationDao;
import com.antiy.asset.service.IAssetSoftwareRelationService;
import com.antiy.asset.entity.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.entity.vo.query.AssetSoftwareRelationQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产软件关系信息 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetSoftwareRelationServiceImpl extends BaseServiceImpl<AssetSoftwareRelation> implements IAssetSoftwareRelationService{


        @Resource
        private AssetSoftwareRelationDao assetSoftwareRelationDao;

        private BaseConverter<AssetSoftwareRelationRequest, AssetSoftwareRelation>  requestConverter;
        
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
            return assetSoftwareRelationDao.findListAssetSoftwareRelation(query);
        }

        public Integer findCountAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
            return assetSoftwareRelationDao.findCount(query);
        }

        @Override
        public PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetSoftwareRelation(query),query.getCurrentPage(), this.findListAssetSoftwareRelation(query));
        }
}
