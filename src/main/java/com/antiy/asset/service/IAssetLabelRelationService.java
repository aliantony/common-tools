package com.antiy.asset.service;

import com.antiy.asset.entity.AssetLabelRelation;
import com.antiy.asset.vo.query.AssetLabelRelationQuery;
import com.antiy.asset.vo.request.AssetLabelRelationRequest;
import com.antiy.asset.vo.response.AssetLabelRelationResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 资产标签关系表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetLabelRelationService extends IBaseService<AssetLabelRelation> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetLabelRelation(AssetLabelRelationRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetLabelRelation(AssetLabelRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetLabelRelationResponse> findListAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetLabelRelationResponse> findPageAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;

}
