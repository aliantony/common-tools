package com.antiy.asset.service;

import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.vo.query.AssetLableQuery;
import com.antiy.asset.vo.request.AssetLableRequest;
import com.antiy.asset.vo.response.AssetLableResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 标签信息表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetLableService extends IBaseService<AssetLable> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetLable(AssetLableRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetLable(AssetLableRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetLableResponse> findListAssetLable(AssetLableQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetLableResponse> findPageAssetLable(AssetLableQuery query) throws Exception;

}
