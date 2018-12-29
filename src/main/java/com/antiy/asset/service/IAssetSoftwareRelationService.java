package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.entity.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.entity.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareRelationResponse;
import com.antiy.asset.entity.AssetSoftwareRelation;


/**
 * <p>
 * 资产软件关系信息 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetSoftwareRelationService extends IBaseService<AssetSoftwareRelation> {

        /**
         * 保存
         * @param assetSoftwareRelationRequest
         * @return
         */
        Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception;

        /**
         * 修改
         * @param assetSoftwareRelationRequest
         * @return
         */
        Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest assetSoftwareRelationRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetSoftwareRelationQuery
         * @return
         */
        List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery assetSoftwareRelationQuery) throws Exception;

        /**
         * 批量查询
         * @param assetSoftwareRelationQuery
         * @return
         */
        PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery assetSoftwareRelationQuery) throws Exception;

}
