package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetLabelRelationDO;
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
         * @param request
         * @return
         */
        Integer saveAssetLabelRelation(AssetLabelRelationRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetLabelRelation(AssetLabelRelationRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetLabelRelationResponse> findListAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetLabelRelationResponse> findPageAssetLabelRelation(AssetLabelRelationQuery query) throws Exception;

}
