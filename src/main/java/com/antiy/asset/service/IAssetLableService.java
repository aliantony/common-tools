package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetLableDTO;;
import com.antiy.asset.entity.vo.query.AssetLableQuery;
import com.antiy.asset.entity.vo.request.AssetLableRequest;
import com.antiy.asset.entity.vo.response.AssetLableResponse;
import com.antiy.asset.entity.AssetLable;


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
         * @param request
         * @return
         */
        Integer saveAssetLable(AssetLableRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetLable(AssetLableRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetLableResponse> findListAssetLable(AssetLableQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetLableResponse> findPageAssetLable(AssetLableQuery query) throws Exception;

}
