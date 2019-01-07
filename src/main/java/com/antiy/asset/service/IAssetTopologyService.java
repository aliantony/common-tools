package com.antiy.asset.service;

import com.antiy.asset.entity.AssetTopology;
import com.antiy.asset.vo.query.AssetTopologyQuery;
import com.antiy.asset.vo.request.AssetTopologyRequest;
import com.antiy.asset.vo.response.AssetTopologyResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;


/**
 * <p>
 * 资产拓扑表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-07
 */
public interface IAssetTopologyService extends IBaseService<AssetTopology> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetTopology(AssetTopologyRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetTopology(AssetTopologyRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetTopologyResponse> findListAssetTopology(AssetTopologyQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetTopologyResponse> findPageAssetTopology(AssetTopologyQuery query) throws Exception;

}
