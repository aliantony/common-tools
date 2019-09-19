package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetServiceDependQuery;
import com.antiy.asset.vo.request.AssetServiceDependRequest;
import com.antiy.asset.vo.response.AssetServiceDependResponse;
import com.antiy.asset.entity.AssetServiceDepend;

/**
 * <p> 服务依赖的服务表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetServiceDependService extends IBaseService<AssetServiceDepend> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetServiceDepend(AssetServiceDependRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetServiceDepend(AssetServiceDependRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetServiceDependResponse> queryListAssetServiceDepend(AssetServiceDependQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetServiceDependResponse> queryPageAssetServiceDepend(AssetServiceDependQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetServiceDependResponse queryAssetServiceDependById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetServiceDependById(BaseRequest baseRequest) throws Exception;

}
