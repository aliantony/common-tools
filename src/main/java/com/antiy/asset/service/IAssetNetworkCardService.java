package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.entity.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkCardResponse;
import com.antiy.asset.entity.AssetNetworkCard;


/**
 * <p>
 * 网卡信息表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface IAssetNetworkCardService extends IBaseService<AssetNetworkCard> {

        /**
         * 保存
         * @param assetNetworkCardRequest
         * @return
         */
        Integer saveAssetNetworkCard(AssetNetworkCardRequest assetNetworkCardRequest) throws Exception;

        /**
         * 修改
         * @param assetNetworkCardRequest
         * @return
         */
        Integer updateAssetNetworkCard(AssetNetworkCardRequest assetNetworkCardRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetNetworkCardQuery
         * @return
         */
        List<AssetNetworkCardResponse> findListAssetNetworkCard(AssetNetworkCardQuery assetNetworkCardQuery) throws Exception;

        /**
         * 批量查询
         * @param assetNetworkCardQuery
         * @return
         */
        PageResult<AssetNetworkCardResponse> findPageAssetNetworkCard(AssetNetworkCardQuery assetNetworkCardQuery) throws Exception;

}
