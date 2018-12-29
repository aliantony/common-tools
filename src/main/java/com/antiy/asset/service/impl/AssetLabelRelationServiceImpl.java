package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.dao.AssetLabelRelationDao;
import com.antiy.asset.service.IAssetLabelRelationService;
import com.antiy.asset.entity.vo.request.AssetLabelRelationRequest;
import com.antiy.asset.entity.vo.response.AssetLabelRelationResponse;
import com.antiy.asset.entity.vo.query.AssetLabelRelationQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产标签关系表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetLabelRelationServiceImpl extends BaseServiceImpl<AssetLabelRelation> implements IAssetLabelRelationService{


        @Resource
        private AssetLabelRelationDao assetLabelRelationDao;

        private BaseConverter<AssetLabelRelationRequest, AssetLabelRelation>  requestConverter;
        
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
            return assetLabelRelationDao.findListAssetLabelRelation(query);
        }

        public Integer findCountAssetLabelRelation(AssetLabelRelationQuery query) throws Exception {
            return assetLabelRelationDao.findCount(query);
        }

        @Override
        public PageResult<AssetLabelRelationResponse> findPageAssetLabelRelation(AssetLabelRelationQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetLabelRelation(query),query.getCurrentPage(), this.findListAssetLabelRelation(query));
        }
}
