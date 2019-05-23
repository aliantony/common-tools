package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetStatusTask;
import com.antiy.asset.vo.query.AssetStatusTaskQuery;
import com.antiy.asset.vo.request.AssetStatusTaskRequest;
import com.antiy.asset.vo.response.AssetStatusTaskResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 资产状态任务表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-05-22
 */
public interface IAssetStatusTaskService extends IBaseService<AssetStatusTask> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetStatusTask(AssetStatusTaskRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetStatusTask(AssetStatusTaskRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetStatusTaskResponse> queryListAssetStatusTask(AssetStatusTaskQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetStatusTaskResponse> queryPageAssetStatusTask(AssetStatusTaskQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetStatusTaskResponse queryAssetStatusTaskById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetStatusTaskById(BaseRequest baseRequest) throws Exception;

}
