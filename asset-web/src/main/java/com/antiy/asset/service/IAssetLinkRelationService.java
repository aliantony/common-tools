package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 通联关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
public interface IAssetLinkRelationService extends IBaseService<AssetLinkRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetLinkRelationResponse> queryListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetLinkRelationResponse> queryPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetLinkRelationResponse queryAssetLinkRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetLinkRelationById(BaseRequest baseRequest) throws Exception;

    /**
     * 分页查询资产列表
     * @param assetQuery
     * @return
     */
    PageResult<AssetResponse> queryAssetPage(AssetQuery assetQuery);

    /**
     * 查询资产列表
     * @param assetQuery
     * @return
     */
    List<AssetResponse> queryAssetList(AssetQuery assetQuery);

    /**
     * 分页查询已关联资产关系列表
     * @param assetLinkRelationQuery
     * @return
     */
    PageResult<AssetLinkRelationResponse> queryLinekedRelationPage(AssetLinkRelationQuery assetLinkRelationQuery);

    /**
     * 查询已关联资产关系列表
     * @param assetLinkRelationQuery
     * @return
     */
    List<AssetLinkRelationResponse> queryLinekedRelationList(AssetLinkRelationQuery assetLinkRelationQuery);
}
