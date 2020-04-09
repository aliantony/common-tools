package com.antiy.asset.service;

import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.asset.vo.query.AssetOaOrderApplyQuery;
import com.antiy.asset.vo.request.AssetOaOrderApplyRequest;
import com.antiy.asset.vo.response.AssetOaOrderApplyResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 订单申请信息表 服务类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
public interface IAssetOaOrderApplyService extends IBaseService<AssetOaOrderApply> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetOaOrderApply(AssetOaOrderApplyRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetOaOrderApply(AssetOaOrderApplyRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetOaOrderApplyResponse> findListAssetOaOrderApply(AssetOaOrderApplyQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetOaOrderApplyResponse> findPageAssetOaOrderApply(AssetOaOrderApplyQuery query) throws Exception;

}
