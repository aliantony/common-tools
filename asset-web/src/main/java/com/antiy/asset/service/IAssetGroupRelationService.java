package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetGroupRelation;
import com.antiy.asset.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.vo.response.AssetGroupRelationResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 资产与资产组关系表 服务类 </p>
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

    /**
     * 资产组详情查询
     *
     * @param query
     * @return
     */
    PageResult<AssetGroupRelationResponse> findPageAssetByAssetGroupId(AssetGroupRelationQuery query) throws Exception;

    /**
     * 通过资产组ID查询资产信息
     * @param query
     * @return
     */
    List<AssetGroupRelationResponse> findAssetDetailByAssetGroupId(AssetGroupRelationQuery query) throws Exception;

    /**
     * 通过资产组ID统计资产信息
     * @param query
     * @return
     */
    Integer findCountDetailByGroupId(AssetGroupRelationQuery query) throws Exception;

    /**
     * 资产组是否存在绑定资产
     * @param primaryKey
     * @return
     */
    Integer hasRealtionAsset(String primaryKey);
}
