package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetServiceProtocolRelationQuery;
import com.antiy.asset.vo.request.AssetServiceProtocolRelationRequest;
import com.antiy.asset.vo.response.AssetServiceProtocolRelationResponse;
import com.antiy.asset.entity.AssetServiceProtocolRelation;

/**
 * <p> 服务与协议表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetServiceProtocolRelationService extends IBaseService<AssetServiceProtocolRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetServiceProtocolRelation(AssetServiceProtocolRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetServiceProtocolRelation(AssetServiceProtocolRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetServiceProtocolRelationResponse> queryListAssetServiceProtocolRelation(AssetServiceProtocolRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetServiceProtocolRelationResponse> queryPageAssetServiceProtocolRelation(AssetServiceProtocolRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetServiceProtocolRelationResponse queryAssetServiceProtocolRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetServiceProtocolRelationById(BaseRequest baseRequest) throws Exception;

}
