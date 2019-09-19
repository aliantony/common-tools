package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetSoftServiceRelationQuery;
import com.antiy.asset.vo.request.AssetSoftServiceRelationRequest;
import com.antiy.asset.vo.response.AssetSoftServiceRelationResponse;
import com.antiy.asset.entity.AssetSoftServiceRelation;

/**
 * <p> 软件依赖的服务 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetSoftServiceRelationService extends IBaseService<AssetSoftServiceRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetSoftServiceRelation(AssetSoftServiceRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetSoftServiceRelation(AssetSoftServiceRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetSoftServiceRelationResponse> queryListAssetSoftServiceRelation(AssetSoftServiceRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetSoftServiceRelationResponse> queryPageAssetSoftServiceRelation(AssetSoftServiceRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetSoftServiceRelationResponse queryAssetSoftServiceRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetSoftServiceRelationById(BaseRequest baseRequest) throws Exception;

}
