package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetGroupRelationDO;
import com.antiy.asset.entity.vo.query.AssetGroupRelationQuery;
import com.antiy.asset.entity.vo.request.AssetGroupRelationRequest;
import com.antiy.asset.entity.vo.response.AssetGroupRelationResponse;
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
         * @param request
         * @return
         */
        Integer saveAssetGroupRelation(AssetGroupRelationRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetGroupRelation(AssetGroupRelationRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetGroupRelationResponse> findListAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetGroupRelationResponse> findPageAssetGroupRelation(AssetGroupRelationQuery query) throws Exception;

}
