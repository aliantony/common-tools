package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetAssemblySoftRelationQuery;
import com.antiy.asset.vo.request.AssetAssemblySoftRelationRequest;
import com.antiy.asset.vo.response.AssetAssemblySoftRelationResponse;
import com.antiy.asset.entity.AssetAssemblySoftRelation;

/**
 * <p> 组件与软件关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetAssemblySoftRelationService extends IBaseService<AssetAssemblySoftRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetAssemblySoftRelation(AssetAssemblySoftRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetAssemblySoftRelation(AssetAssemblySoftRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetAssemblySoftRelationResponse> queryListAssetAssemblySoftRelation(AssetAssemblySoftRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetAssemblySoftRelationResponse> queryPageAssetAssemblySoftRelation(AssetAssemblySoftRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetAssemblySoftRelationResponse queryAssetAssemblySoftRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetAssemblySoftRelationById(BaseRequest baseRequest) throws Exception;

}
