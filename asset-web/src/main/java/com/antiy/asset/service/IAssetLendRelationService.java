package com.antiy.asset.service;

import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.request.AssetLendRelationRequest;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
public interface IAssetLendRelationService extends IBaseService<AssetLendRelation> {

        /**
         * 保存
         * @param request
         * @return
         */
        String saveAssetLendRelation(AssetLendRelationRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        String updateAssetLendRelation(AssetLendRelationRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetLendRelationResponse> queryListAssetLendRelation(AssetLendRelationQuery query) throws Exception;

        /**
         * 分页查询
         * @param query
         * @return
         */
        PageResult<AssetLendRelationResponse> queryPageAssetLendRelation(AssetLendRelationQuery query) throws Exception;

        /**
         * 通过ID查询
         * @param queryCondition
         * @return
         */
        AssetLendRelationResponse queryAssetLendRelationById(QueryCondition queryCondition) throws Exception;

        /**
         * 通过ID删除
         * @param baseRequest
         * @return
         */
        String deleteAssetLendRelationById(BaseRequest baseRequest) throws Exception;

        /**
         * 查询详情
         * @param uniqueId
         * @return
         */
    AssetLendRelationResponse queryInfo(String uniqueId);

        /**
         * 归还确认
         * @param uniqueId
         * @return
         */
        Integer returnConfirm(AssetLendRelationRequest uniqueId);

        List<AssetLendRelationResponse> queryHistory(String uniqueId);
}
