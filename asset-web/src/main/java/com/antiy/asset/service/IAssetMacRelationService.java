package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetMacRelationQuery;
import com.antiy.asset.vo.request.AssetMacRelationRequest;
import com.antiy.asset.vo.response.AssetMacRelationResponse;
import com.antiy.asset.entity.AssetMacRelation;

/**
 * <p> 资产-MAC关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetMacRelationService extends IBaseService<AssetMacRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetMacRelation(AssetMacRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetMacRelation(AssetMacRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetMacRelationResponse> queryListAssetMacRelation(AssetMacRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetMacRelationResponse> queryPageAssetMacRelation(AssetMacRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetMacRelationResponse queryAssetMacRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetMacRelationById(BaseRequest baseRequest) throws Exception;

}
