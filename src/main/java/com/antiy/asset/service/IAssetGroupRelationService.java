package com.antiy.asset.service;

import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.vo.response.AssetGroupRelationResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 资产与资产组关系表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetGroupRelationService extends IBaseService<AssetGroupRelation> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetGroupRelation(AssetGroupRelationRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetGroupRelation(AssetGroupRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetGroupRelationResponse> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetGroupRelationResponse> findPageAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;

}
