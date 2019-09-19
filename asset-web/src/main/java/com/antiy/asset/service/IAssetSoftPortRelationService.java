package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetSoftPortRelationQuery;
import com.antiy.asset.vo.request.AssetSoftPortRelationRequest;
import com.antiy.asset.vo.response.AssetSoftPortRelationResponse;
import com.antiy.asset.entity.AssetSoftPortRelation;

/**
 * <p> 软件与端口表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetSoftPortRelationService extends IBaseService<AssetSoftPortRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetSoftPortRelation(AssetSoftPortRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetSoftPortRelation(AssetSoftPortRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetSoftPortRelationResponse> queryListAssetSoftPortRelation(AssetSoftPortRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetSoftPortRelationResponse> queryPageAssetSoftPortRelation(AssetSoftPortRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetSoftPortRelationResponse queryAssetSoftPortRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetSoftPortRelationById(BaseRequest baseRequest) throws Exception;

}
