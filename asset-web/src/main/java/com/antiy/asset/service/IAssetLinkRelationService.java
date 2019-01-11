package com.antiy.asset.service;

import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 通联关系表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetLinkRelationService extends IBaseService<AssetLinkRelation> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetLinkRelationResponse> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetLinkRelationResponse> findPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

}
