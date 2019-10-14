package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.ActionResponse;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.vo.query.AssetSysServiceLibQuery;
import com.antiy.asset.vo.request.AssetSysServiceLibRequest;
import com.antiy.asset.vo.response.AssetSysServiceLibResponse;
import com.antiy.asset.entity.AssetSysServiceLib;

/**
 * <p> 服务表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface IAssetSysServiceLibService extends IBaseService<AssetSysServiceLib> {



    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetSysServiceLibResponse> queryListAssetSysServiceLib(AssetSysServiceLibQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetSysServiceLibResponse> queryPageAssetSysServiceLib(AssetSysServiceLibQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetSysServiceLibResponse queryAssetSysServiceLibById(QueryCondition queryCondition) throws Exception;

}
