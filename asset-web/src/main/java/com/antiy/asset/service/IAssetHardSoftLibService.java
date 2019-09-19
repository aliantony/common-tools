package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetHardSoftLibQuery;
import com.antiy.asset.vo.request.AssetHardSoftLibRequest;
import com.antiy.asset.vo.response.AssetHardSoftLibResponse;
import com.antiy.asset.entity.AssetHardSoftLib;

/**
 * <p> CPE表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetHardSoftLibService extends IBaseService<AssetHardSoftLib> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetHardSoftLib(AssetHardSoftLibRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetHardSoftLibResponse> queryListAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetHardSoftLibResponse> queryPageAssetHardSoftLib(AssetHardSoftLibQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetHardSoftLibResponse queryAssetHardSoftLibById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetHardSoftLibById(BaseRequest baseRequest) throws Exception;

}
