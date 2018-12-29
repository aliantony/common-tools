package com.antiy.asset.service;

import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.entity.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.entity.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.entity.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

;


/**
 * <p>
 * 网卡信息表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetNetworkCardService extends IBaseService<AssetNetworkCard> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetNetworkCard(AssetNetworkCardRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetNetworkCard(AssetNetworkCardRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetNetworkCardResponse> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetNetworkCardResponse> findPageAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;

}
