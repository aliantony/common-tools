package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.entity.dto.AssetNetworkCardDTO;
import com.antiy.asset.entity.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.entity.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkCardResponse;
import com.antiy.asset.service.IAssetNetworkCardService;
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
 * 网卡信息表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetNetworkCardServiceImpl extends BaseServiceImpl<AssetNetworkCard> implements IAssetNetworkCardService{


        @Resource
        private AssetNetworkCardDao assetNetworkCardDao;
        @Resource
        private BaseConverter<AssetNetworkCardRequest, AssetNetworkCard>  requestConverter;
        @Resource
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
            List<AssetNetworkCardDTO> assetNetworkCardDTO = assetNetworkCardDao.findListAssetNetworkCard(query);
            //TODTO;
            //需要将assetNetworkCardDTO转达成AssetNetworkCardResponse
            List<AssetNetworkCardResponse> assetNetworkCardResponse = new ArrayList<AssetNetworkCardResponse>();
            return assetNetworkCardResponse;
        }

        public Integer findCountAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
            return assetNetworkCardDao.findCount(query);
        }

        @Override
        public PageResult<AssetNetworkCardResponse> findPageAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkCard(query),query.getCurrentPage(), this.findListAssetNetworkCard(query));
        }
}
