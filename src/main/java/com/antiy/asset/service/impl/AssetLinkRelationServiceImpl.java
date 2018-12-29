package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.entity.dto.AssetLinkRelationDTO;
import com.antiy.asset.entity.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.entity.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.entity.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.service.IAssetLinkRelationService;
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
 * 通联关系表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetLinkRelationServiceImpl extends BaseServiceImpl<AssetLinkRelation> implements IAssetLinkRelationService{


        @Resource
        private AssetLinkRelationDao assetLinkRelationDao;
        @Resource
        private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
        @Resource
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
            List<AssetLinkRelationDTO> assetLinkRelationDTO = assetLinkRelationDao.findListAssetLinkRelation(query);
            //TODTO;
            //需要将assetLinkRelationDTO转达成AssetLinkRelationResponse
            List<AssetLinkRelationResponse> assetLinkRelationResponse = new ArrayList<AssetLinkRelationResponse>();
            return assetLinkRelationResponse;
        }

        public Integer findCountAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
            return assetLinkRelationDao.findCount(query);
        }

        @Override
        public PageResult<AssetLinkRelationResponse> findPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetLinkRelation(query),query.getCurrentPage(), this.findListAssetLinkRelation(query));
        }
}
