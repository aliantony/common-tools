package com.antiy.asset.service;

import com.antiy.asset.entity.AssetUser;
import com.antiy.asset.vo.query.AssetUserQuery;
import com.antiy.asset.vo.request.AssetUserRequest;
import com.antiy.asset.vo.response.AssetUserResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 资产用户信息 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetUserService extends IBaseService<AssetUser> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetUser(AssetUserRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetUser(AssetUserRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetUserResponse> findListAssetUser(AssetUserQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetUserResponse> findPageAssetUser(AssetUserQuery query) throws Exception;

}
