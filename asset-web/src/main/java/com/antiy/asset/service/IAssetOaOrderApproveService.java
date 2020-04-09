package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOaOrderApprove;
import com.antiy.asset.vo.query.AssetOaOrderApproveQuery;
import com.antiy.asset.vo.request.AssetOaOrderApproveRequest;
import com.antiy.asset.vo.response.AssetOaOrderApproveResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 订单审批信息表 服务类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface IAssetOaOrderApproveService extends IBaseService<AssetOaOrderApprove> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetOaOrderApprove(AssetOaOrderApproveRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetOaOrderApprove(AssetOaOrderApproveRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetOaOrderApproveResponse> findListAssetOaOrderApprove(AssetOaOrderApproveQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetOaOrderApproveResponse> findPageAssetOaOrderApprove(AssetOaOrderApproveQuery query) throws Exception;

}
