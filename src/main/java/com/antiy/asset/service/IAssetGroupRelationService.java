package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.asset.entity.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.asset.entity.vo.response.AssetGroupRelationResponse;
import com.antiy.asset.entity.AssetGroupRelation;


/**
 * <p>
 * 资产与资产组关系表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetGroupRelationService extends IBaseService<AssetGroupRelation> {

        /**
         * 保存
         * @param assetGroupRelationRequest
         * @return
         */
        Integer saveAssetGroupRelation(AssetGroupRelationRequest assetGroupRelationRequest) throws Exception;

        /**
         * 修改
         * @param assetGroupRelationRequest
         * @return
         */
        Integer updateAssetGroupRelation(AssetGroupRelationRequest assetGroupRelationRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetGroupRelationQuery
         * @return
         */
        List<AssetGroupRelationResponse> findListAssetGroupRelation(AssetGroupRelationQuery assetGroupRelationQuery) throws Exception;

        /**
         * 批量查询
         * @param assetGroupRelationQuery
         * @return
         */
        PageResult<AssetGroupRelationResponse> findPageAssetGroupRelation(AssetGroupRelationQuery assetGroupRelationQuery) throws Exception;

}
