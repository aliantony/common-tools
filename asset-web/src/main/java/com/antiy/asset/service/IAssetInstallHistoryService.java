package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetInstallHistoryQuery;
import com.antiy.asset.vo.request.AssetInstallHistoryRequest;
import com.antiy.asset.vo.response.AssetInstallHistoryResponse;
import com.antiy.asset.entity.AssetInstallHistory;

/**
 * <p> 安装记录表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetInstallHistoryService extends IBaseService<AssetInstallHistory> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetInstallHistory(AssetInstallHistoryRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetInstallHistory(AssetInstallHistoryRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetInstallHistoryResponse> queryListAssetInstallHistory(AssetInstallHistoryQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetInstallHistoryResponse> queryPageAssetInstallHistory(AssetInstallHistoryQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetInstallHistoryResponse queryAssetInstallHistoryById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetInstallHistoryById(BaseRequest baseRequest) throws Exception;

}
