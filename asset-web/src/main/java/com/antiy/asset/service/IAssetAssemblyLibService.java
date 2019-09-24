package com.antiy.asset.service;

import com.antiy.asset.vo.response.AssetAssemblyResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetAssemblyLibQuery;
import com.antiy.asset.vo.request.AssetAssemblyLibRequest;
import com.antiy.asset.vo.response.AssetAssemblyLibResponse;
import com.antiy.asset.entity.AssetAssemblyLib;

/**
 * <p> 组件表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetAssemblyLibService extends IBaseService<AssetAssemblyLib> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetAssemblyLib(AssetAssemblyLibRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetAssemblyLib(AssetAssemblyLibRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetAssemblyLibResponse> queryListAssetAssemblyLib(AssetAssemblyLibQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetAssemblyLibResponse> queryPageAssetAssemblyLib(AssetAssemblyLibQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetAssemblyLibResponse queryAssetAssemblyLibById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetAssemblyLibById(BaseRequest baseRequest) throws Exception;

    List<AssetAssemblyResponse> queryAssemblyByHardSoftId(QueryCondition query);

}
