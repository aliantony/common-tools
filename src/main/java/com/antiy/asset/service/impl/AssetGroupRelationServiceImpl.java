package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.dao.AssetGroupRelationDao;
import com.antiy.asset.service.IAssetGroupRelationService;
import com.antiy.asset.entity.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.entity.vo.response.AssetGroupRelationResponse;
import com.antiy.asset.entity.vo.query.AssetGroupRelationQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产与资产组关系表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetGroupRelationServiceImpl extends BaseServiceImpl<AssetGroupRelation> implements IAssetGroupRelationService{


        @Resource
        private AssetGroupRelationDao assetGroupRelationDao;

        private BaseConverter<AssetGroupRelationRequest, AssetGroupRelation>  requestConverter;
        
        private BaseConverter<AssetGroupRelation, AssetGroupRelationResponse> responseConverter;

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
            return assetGroupRelationDao.findListAssetGroupRelation(query);
        }

        public Integer findCountAssetGroupRelation(AssetGroupRelationQuery query) throws Exception {
            return assetGroupRelationDao.findCount(query);
        }

        @Override
        public PageResult<AssetGroupRelationResponse> findPageAssetGroupRelation(AssetGroupRelationQuery query) throws Exception {
            return new PageResult<>(this.findCountAssetGroupRelation(query),this.findListAssetGroupRelation(query));
        }
}
