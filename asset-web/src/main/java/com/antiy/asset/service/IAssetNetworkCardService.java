package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetNetworkCard;
import com.antiy.asset.vo.query.AssetNetworkCardQuery;
import com.antiy.asset.vo.request.AssetNetworkCardRequest;
import com.antiy.asset.vo.response.AssetNetworkCardResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 网卡信息表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetNetworkCardService extends IBaseService<AssetNetworkCard> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetNetworkCard(AssetNetworkCardRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetNetworkCard(AssetNetworkCardRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetNetworkCardResponse> findListAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetNetworkCardResponse> findPageAssetNetworkCard(AssetNetworkCardQuery query) throws Exception;

}
