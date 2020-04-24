package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOaOrder;
import com.antiy.asset.vo.query.AssetOaOrderQuery;
import com.antiy.asset.vo.request.AssetOaOrderRequest;
import com.antiy.asset.vo.response.AssetOaOrderResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * OA订单表 服务类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface IAssetOaOrderService extends IBaseService<AssetOaOrder> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetOaOrder(AssetOaOrderRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetOaOrder(AssetOaOrderRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetOaOrderResponse> findListAssetOaOrder(AssetOaOrderQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetOaOrderResponse> findPageAssetOaOrder(AssetOaOrderQuery query) throws Exception;


        /**
         * 查询单个详情
         */
        AssetOaOrderResponse getDetailById(Integer id) throws Exception;


        /**
         * 查询单个详情
         */
        boolean getStatus(Integer id) throws Exception;
}
