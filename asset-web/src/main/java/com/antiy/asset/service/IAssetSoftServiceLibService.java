package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetSoftServiceLibQuery;
import com.antiy.asset.vo.request.AssetSoftServiceLibRequest;
import com.antiy.asset.vo.response.AssetSoftServiceLibResponse;
import com.antiy.asset.entity.AssetSoftServiceLib;

/**
 * <p> 软件提供的服务 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetSoftServiceLibService extends IBaseService<AssetSoftServiceLib> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetSoftServiceLib(AssetSoftServiceLibRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetSoftServiceLib(AssetSoftServiceLibRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetSoftServiceLibResponse> queryListAssetSoftServiceLib(AssetSoftServiceLibQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetSoftServiceLibResponse> queryPageAssetSoftServiceLib(AssetSoftServiceLibQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetSoftServiceLibResponse queryAssetSoftServiceLibById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetSoftServiceLibById(BaseRequest baseRequest) throws Exception;

}
