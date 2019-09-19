package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetProtocolQuery;
import com.antiy.asset.vo.request.AssetProtocolRequest;
import com.antiy.asset.vo.response.AssetProtocolResponse;
import com.antiy.asset.entity.AssetProtocol;

/**
 * <p> 协议表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetProtocolService extends IBaseService<AssetProtocol> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetProtocol(AssetProtocolRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetProtocol(AssetProtocolRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetProtocolResponse> queryListAssetProtocol(AssetProtocolQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetProtocolResponse> queryPageAssetProtocol(AssetProtocolQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetProtocolResponse queryAssetProtocolById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetProtocolById(BaseRequest baseRequest) throws Exception;

}
