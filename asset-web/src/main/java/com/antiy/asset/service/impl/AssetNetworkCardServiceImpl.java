package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetNetworkCardDao;
import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.service.IAssetNetworkCardService;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 网卡信息表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetNetworkCardServiceImpl extends BaseServiceImpl<AssetNetworkCard> implements IAssetNetworkCardService {

    @Resource
    private AssetNetworkCardDao                                       assetNetworkCardDao;
    @Resource
    private BaseConverter<AssetNetworkCardRequest, AssetNetworkCard>  requestConverter;
    @Resource
    private BaseConverter<AssetNetworkCard, AssetNetworkCardResponse> responseConverter;

    @Override
    public Integer saveAssetNetworkCard(AssetNetworkCardRequest request) throws Exception {
        AssetNetworkCard assetNetworkCard = requestConverter.convert(request, AssetNetworkCard.class);
        assetNetworkCard.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkCard.setGmtCreate(System.currentTimeMillis());
        assetNetworkCardDao.insert(assetNetworkCard);
        return assetNetworkCard.getId();
    }

    @Override
    public Integer updateAssetNetworkCard(AssetNetworkCardRequest request) throws Exception {
        AssetNetworkCard assetNetworkCard = requestConverter.convert(request, AssetNetworkCard.class);
        assetNetworkCard.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetNetworkCard.setGmtModified(System.currentTimeMillis());
        return assetNetworkCardDao.update(assetNetworkCard);
    }

    @Override
    public List<AssetNetworkCardResponse> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
        List<AssetNetworkCard> assetNetworkCard = assetNetworkCardDao.findListAssetNetworkCard(query);
        // TODO
        List<AssetNetworkCardResponse> assetNetworkCardResponse = responseConverter.convert(assetNetworkCard,
            AssetNetworkCardResponse.class);
        return assetNetworkCardResponse;
    }

    public Integer findCountAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
        return assetNetworkCardDao.findCount(query);
    }

    @Override
    public PageResult<AssetNetworkCardResponse> findPageAssetNetworkCard(AssetNetworkCardQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetNetworkCard(query), query.getCurrentPage(),
            this.findListAssetNetworkCard(query));
    }
}
