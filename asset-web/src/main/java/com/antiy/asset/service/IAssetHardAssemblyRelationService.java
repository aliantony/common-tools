package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetHardAssemblyRelationQuery;
import com.antiy.asset.vo.request.AssetHardAssemblyRelationRequest;
import com.antiy.asset.vo.response.AssetHardAssemblyRelationResponse;
import com.antiy.asset.entity.AssetHardAssemblyRelation;

/**
 * <p> 硬件与组件关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetHardAssemblyRelationService extends IBaseService<AssetHardAssemblyRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetHardAssemblyRelation(AssetHardAssemblyRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetHardAssemblyRelation(AssetHardAssemblyRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetHardAssemblyRelationResponse> queryListAssetHardAssemblyRelation(AssetHardAssemblyRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetHardAssemblyRelationResponse> queryPageAssetHardAssemblyRelation(AssetHardAssemblyRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetHardAssemblyRelationResponse queryAssetHardAssemblyRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetHardAssemblyRelationById(BaseRequest baseRequest) throws Exception;

}
