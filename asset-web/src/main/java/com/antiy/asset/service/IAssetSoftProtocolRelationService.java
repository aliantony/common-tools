package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetSoftProtocolRelationQuery;
import com.antiy.asset.vo.request.AssetSoftProtocolRelationRequest;
import com.antiy.asset.vo.response.AssetSoftProtocolRelationResponse;
import com.antiy.asset.entity.AssetSoftProtocolRelation;

/**
 * <p> 软件与协议表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetSoftProtocolRelationService extends IBaseService<AssetSoftProtocolRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetSoftProtocolRelation(AssetSoftProtocolRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetSoftProtocolRelation(AssetSoftProtocolRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetSoftProtocolRelationResponse> queryListAssetSoftProtocolRelation(AssetSoftProtocolRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetSoftProtocolRelationResponse> queryPageAssetSoftProtocolRelation(AssetSoftProtocolRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetSoftProtocolRelationResponse queryAssetSoftProtocolRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetSoftProtocolRelationById(BaseRequest baseRequest) throws Exception;

}
