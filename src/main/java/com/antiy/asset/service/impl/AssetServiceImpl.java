package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.service.IAssetService;
import com.antiy.asset.asset.entity.vo.request.AssetRequest;
import com.antiy.asset.asset.entity.vo.response.AssetResponse;
import com.antiy.asset.asset.entity.vo.query.AssetQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 资产主表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetServiceImpl extends BaseServiceImpl<Asset> implements IAssetService{


        @Resource
        private AssetDao assetDao;

        private BaseConverter<AssetRequest, Asset>  requestConverter;
        
        private BaseConverter<Asset, AssetResponse> responseConverter;

        @Override
        public Integer saveAsset(AssetRequest request) throws Exception {
            Asset asset = requestConverter.convert(request, Asset.class);
            return assetDao.insert(asset);
        }

        @Override
        public Integer updateAsset(AssetRequest request) throws Exception {
            Asset asset = requestConverter.convert(request, Asset.class);
            return assetDao.update(asset);
        }

        @Override
        public List<AssetResponse> findListAsset(AssetQuery query) throws Exception {
            return assetDao.findListAsset(query);
        }

        public Integer findCountAsset(AssetQuery query) throws Exception {
            return assetDao.findCount(query);
        }

        @Override
        public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAsset(query),query.getCurrentPage(), this.findListAsset(query));
        }
}
