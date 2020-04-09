package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOaOrderLend;
import com.antiy.asset.vo.query.AssetOaOrderLendQuery;
import com.antiy.asset.vo.request.AssetOaOrderLendRequest;
import com.antiy.asset.vo.response.AssetOaOrderLendResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 出借订单拒绝表 服务类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-09
 */
public interface IAssetOaOrderLendService extends IBaseService<AssetOaOrderLend> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetOaOrderRefuse(AssetOaOrderLendRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetOaOrderRefuse(AssetOaOrderLendRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetOaOrderLendResponse> findListAssetOaOrderRefuse(AssetOaOrderLendQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetOaOrderLendResponse> findPageAssetOaOrderRefuse(AssetOaOrderLendQuery query) throws Exception;

}
