package com.antiy.asset.service;

import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.entity.vo.query.AssetSoftwareRelationQuery;
import com.antiy.asset.entity.vo.request.AssetSoftwareRelationRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareRelationResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

;


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
         * @param request
         * @return
         */
        Integer saveAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetSoftwareRelation(AssetSoftwareRelationRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetSoftwareRelationResponse> findListAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetSoftwareRelationResponse> findPageAssetSoftwareRelation(AssetSoftwareRelationQuery query) throws Exception;

}
