package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetMainboradDO;
import com.antiy.asset.entity.vo.query.AssetMainboradQuery;
import com.antiy.asset.entity.vo.request.AssetMainboradRequest;
import com.antiy.asset.entity.vo.response.AssetMainboradResponse;
import com.antiy.asset.entity.AssetMainborad;


/**
 * <p>
 * 主板表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetMainboradService extends IBaseService<AssetMainborad> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetMainborad(AssetMainboradRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetMainborad(AssetMainboradRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetMainboradResponse> findListAssetMainborad(AssetMainboradQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetMainboradResponse> findPageAssetMainborad(AssetMainboradQuery query) throws Exception;

}
