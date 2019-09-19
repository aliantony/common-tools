package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetIpRelationQuery;
import com.antiy.asset.vo.request.AssetIpRelationRequest;
import com.antiy.asset.vo.response.AssetIpRelationResponse;
import com.antiy.asset.entity.AssetIpRelation;

/**
 * <p> 资产-IP关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetIpRelationService extends IBaseService<AssetIpRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetIpRelation(AssetIpRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetIpRelation(AssetIpRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetIpRelationResponse> queryListAssetIpRelation(AssetIpRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetIpRelationResponse> queryPageAssetIpRelation(AssetIpRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetIpRelationResponse queryAssetIpRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetIpRelationById(BaseRequest baseRequest) throws Exception;

}
