package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetServicePortRelationQuery;
import com.antiy.asset.vo.request.AssetServicePortRelationRequest;
import com.antiy.asset.vo.response.AssetServicePortRelationResponse;
import com.antiy.asset.entity.AssetServicePortRelation;

/**
 * <p> 服务与端口表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetServicePortRelationService extends IBaseService<AssetServicePortRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetServicePortRelation(AssetServicePortRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetServicePortRelation(AssetServicePortRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetServicePortRelationResponse> queryListAssetServicePortRelation(AssetServicePortRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetServicePortRelationResponse> queryPageAssetServicePortRelation(AssetServicePortRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetServicePortRelationResponse queryAssetServicePortRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetServicePortRelationById(BaseRequest baseRequest) throws Exception;

}
