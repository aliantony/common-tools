package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetCpeFilterQuery;
import com.antiy.asset.vo.request.AssetCpeFilterRequest;
import com.antiy.asset.vo.response.AssetCpeFilterResponse;
import com.antiy.asset.entity.AssetCpeFilter;

/**
 * <p> 过滤显示表（筛选指定的数据给用户） 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetCpeFilterService extends IBaseService<AssetCpeFilter> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetCpeFilter(AssetCpeFilterRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetCpeFilter(AssetCpeFilterRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetCpeFilterResponse> queryListAssetCpeFilter(AssetCpeFilterQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetCpeFilterResponse> queryPageAssetCpeFilter(AssetCpeFilterQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetCpeFilterResponse queryAssetCpeFilterById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetCpeFilterById(BaseRequest baseRequest) throws Exception;

}
