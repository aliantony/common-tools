package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.service.IAssetNetworkCardService;
import com.antiy.asset.entity.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkCardResponse;
import com.antiy.asset.entity.vo.query.AssetNetworkCardQuery;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 网卡信息表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
@Service
@Slf4j
public class AssetNetworkCardServiceImpl extends BaseServiceImpl<AssetNetworkCard> implements IAssetNetworkCardService{


        @Resource
        private AssetNetworkCardDao assetNetworkCardDao;

        private BaseConverter<AssetNetworkCardRequest, AssetNetworkCard>  requestConverter;
        
        private BaseConverter<AssetNetworkCard, AssetNetworkCardResponse> responseConverter;

        @Override
        public Integer saveAssetNetworkCard(AssetNetworkCardRequest request) throws Exception {
            AssetNetworkCard assetNetworkCard = requestConverter.convert(request, AssetNetworkCard.class);
            return assetNetworkCardDao.insert(assetNetworkCard);
        }

        @Override
        public Integer updateAssetNetworkCard(AssetNetworkCardRequest request) throws Exception {
            AssetNetworkCard assetNetworkCard = requestConverter.convert(request, AssetNetworkCard.class);
            return assetNetworkCardDao.update(assetNetworkCard);
        }

        @Override
        public List<AssetNetworkCardResponse> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
            return assetNetworkCardDao.findListAssetNetworkCard(query);
        }

        public Integer findCountAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
            return assetNetworkCardDao.findCount(query);
        }

        @Override
        public PageResult<AssetNetworkCardResponse> findPageAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkCard(query),query.getCurrentPage(), this.findListAssetNetworkCard(query));
        }
}
