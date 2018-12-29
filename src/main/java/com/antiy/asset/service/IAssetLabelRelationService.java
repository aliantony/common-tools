package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.entity.vo.query.AssetLabelRelationQuery;
import com.antiy.asset.entity.vo.request.AssetLabelRelationRequest;
import com.antiy.asset.entity.vo.response.AssetLabelRelationResponse;
import com.antiy.asset.entity.AssetLabelRelation;


/**
 * <p>
 * 资产标签关系表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetLabelRelationService extends IBaseService<AssetLabelRelation> {

        /**
         * 保存
         * @param assetLabelRelationRequest
         * @return
         */
        Integer saveAssetLabelRelation(AssetLabelRelationRequest assetLabelRelationRequest) throws Exception;

        /**
         * 修改
         * @param assetLabelRelationRequest
         * @return
         */
        Integer updateAssetLabelRelation(AssetLabelRelationRequest assetLabelRelationRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetLabelRelationQuery
         * @return
         */
        List<AssetLabelRelationResponse> findListAssetLabelRelation(AssetLabelRelationQuery assetLabelRelationQuery) throws Exception;

        /**
         * 批量查询
         * @param assetLabelRelationQuery
         * @return
         */
        PageResult<AssetLabelRelationResponse> findPageAssetLabelRelation(AssetLabelRelationQuery assetLabelRelationQuery) throws Exception;

}
