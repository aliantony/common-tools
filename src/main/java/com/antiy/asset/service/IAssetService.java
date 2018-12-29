package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetDO;
import com.antiy.asset.entity.vo.query.AssetQuery;
import com.antiy.asset.entity.vo.request.AssetRequest;
import com.antiy.asset.entity.vo.response.AssetResponse;
import com.antiy.asset.entity.Asset;


/**
 * <p>
 * 资产主表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetService extends IBaseService<Asset> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAsset(AssetRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAsset(AssetRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetResponse> findListAsset(AssetQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetResponse> findPageAsset(AssetQuery query) throws Exception;

}
