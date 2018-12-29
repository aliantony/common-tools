package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.dto.AssetDTO;
import com.antiy.asset.entity.vo.query.AssetQuery;
import com.antiy.asset.entity.vo.request.AssetRequest;
import com.antiy.asset.entity.vo.response.AssetResponse;
import com.antiy.asset.service.IAssetService;
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
        @Resource
        private BaseConverter<AssetRequest, Asset>  requestConverter;
        @Resource
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
            List<AssetDTO> assetDTO = assetDao.findListAsset(query);
            //TODTO;
            //需要将assetDTO转达成AssetResponse
            List<AssetResponse> assetResponse = new ArrayList<AssetResponse>();
            return assetResponse;
        }

        public Integer findCountAsset(AssetQuery query) throws Exception {
            return assetDao.findCount(query);
        }

        @Override
        public PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAsset(query),query.getCurrentPage(), this.findListAsset(query));
        }
}
