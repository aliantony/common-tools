package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetGroup;
import com.antiy.asset.vo.query.AssetGroupQuery;
import com.antiy.asset.vo.request.AssetGroupRequest;
import com.antiy.asset.vo.response.AssetGroupResponse;
import com.antiy.asset.vo.response.GroupValueResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

/**
 * <p> 资产组表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface IAssetGroupService extends IBaseService<AssetGroup> {

    /**
     * 保存
     *
     * @param request
     * @return
     */
    Integer saveAssetGroup(AssetGroupRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Integer updateAssetGroup(AssetGroupRequest request) throws Exception;

    /**
     * 查询对象集合
     *
     * @param query
     * @return
     */
    List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception;

    /**
     * 批量查询
     *
     * @param query
     * @return
     */
    PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception;

    /**
     * 查询下拉项的资产组信息
     *
     * @return
     */
    List<SelectResponse> queryGroupInfo() throws Exception;

}
