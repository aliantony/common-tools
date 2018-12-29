package com.antiy.asset.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetLableQuery;
import com.antiy.asset.asset.entity.vo.request.AssetLableRequest;
import com.antiy.asset.asset.entity.vo.response.AssetLableResponse;
import com.antiy.asset.asset.entity.AssetLable;


/**
 * <p>
 * 标签信息表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetLableService extends IBaseService<AssetLable> {

        /**
         * 保存
         * @param assetLableRequest
         * @return
         */
        Integer saveAssetLable(AssetLableRequest assetLableRequest) throws Exception;

        /**
         * 修改
         * @param assetLableRequest
         * @return
         */
        Integer updateAssetLable(AssetLableRequest assetLableRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetLableQuery
         * @return
         */
        List<AssetLableResponse> findListAssetLable(AssetLableQuery assetLableQuery) throws Exception;

        /**
         * 批量查询
         * @param assetLableQuery
         * @return
         */
        PageResult<AssetLableResponse> findPageAssetLable(AssetLableQuery assetLableQuery) throws Exception;

}
