package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetLinkRelationDTO;;
import com.antiy.asset.entity.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.entity.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.entity.vo.response.AssetLinkRelationResponse;
import com.antiy.asset.entity.AssetLinkRelation;


/**
 * <p>
 * 通联关系表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetLinkRelationService extends IBaseService<AssetLinkRelation> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetLinkRelationResponse> findListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetLinkRelationResponse> findPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception;

}
