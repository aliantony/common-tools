package com.antiy.asset.service;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 资产软件关系信息 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetSoftwareRelationService extends IBaseService<AssetSoftwareRelation> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;

    /**
     * 通过资产ID查询关联软件信息
     *
     * @param assetId
     * @return
     */
    List<AssetSoftwareResponse> getSoftByAssetId(Integer assetId);

    /**
     * 通过软件ID统计资产数量
     *
     * @param id
     * @return
     */
    Integer countAssetBySoftId(Integer id);

}
