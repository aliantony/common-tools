package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOaOrderResult;
import com.antiy.asset.vo.query.AssetOaOrderResultQuery;
import com.antiy.asset.vo.request.AssetOaOrderResultRequest;
import com.antiy.asset.vo.response.AssetOaOrderResultResponse;
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
public interface IAssetOaOrderResultService extends IBaseService<AssetOaOrderResult> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetOaOrderRefuse(AssetOaOrderResultRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetOaOrderRefuse(AssetOaOrderResultRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetOaOrderResultResponse> findListAssetOaOrderRefuse(AssetOaOrderResultQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetOaOrderResultResponse> findPageAssetOaOrderRefuse(AssetOaOrderResultQuery query) throws Exception;

}
