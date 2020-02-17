package com.antiy.asset.service;

import com.antiy.asset.entity.AssetBusinessRelation;
import com.antiy.asset.vo.query.AssetBusinessRelationQuery;
import com.antiy.asset.vo.request.AssetBusinessRelationRequest;
import com.antiy.asset.vo.response.AssetBusinessRelationResponse;
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
 * @since 2020-02-17
 */
public interface IAssetBusinessRelationService extends IBaseService<AssetBusinessRelation> {

        /**
         * 保存
         * @param request
         * @return
         */
        String saveAssetBusinessRelation(AssetBusinessRelationRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        String updateAssetBusinessRelation(AssetBusinessRelationRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetBusinessRelationResponse> queryListAssetBusinessRelation(AssetBusinessRelationQuery query) throws Exception;

        /**
         * 分页查询
         * @param query
         * @return
         */
        PageResult<AssetBusinessRelationResponse> queryPageAssetBusinessRelation(AssetBusinessRelationQuery query) throws Exception;

        /**
         * 通过ID查询
         * @param queryCondition
         * @return
         */
        AssetBusinessRelationResponse queryAssetBusinessRelationById(QueryCondition queryCondition) throws Exception;

        /**
         * 通过ID删除
         * @param baseRequest
         * @return
         */
        String deleteAssetBusinessRelationById(BaseRequest baseRequest) throws Exception;

}
