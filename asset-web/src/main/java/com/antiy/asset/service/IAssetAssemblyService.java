package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetAssemblyQuery;
import com.antiy.asset.vo.request.AssetAssemblyRequest;
import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.asset.entity.AssetAssembly;

/**
 * <p> 资产组件关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetAssemblyService extends IBaseService<AssetAssembly> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetAssembly(AssetAssemblyRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetAssembly(AssetAssemblyRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetAssemblyResponse> queryListAssetAssembly(AssetAssemblyQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetAssemblyResponse> queryPageAssetAssembly(AssetAssemblyQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetAssemblyResponse queryAssetAssemblyById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetAssemblyById(BaseRequest baseRequest) throws Exception;

}
