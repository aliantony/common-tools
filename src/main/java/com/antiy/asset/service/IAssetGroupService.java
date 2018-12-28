package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.vo.query.AssetGroupQuery;
import com.antiy.asset.entity.vo.request.AssetGroupRequest;
import com.antiy.asset.entity.vo.response.AssetGroupResponse;
import com.antiy.asset.entity.AssetGroup;


/**
 * <p>
 * 资产组表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface IAssetGroupService extends IBaseService<AssetGroup> {

        /**
         * 保存
         * @param assetGroupRequest
         * @return
         */
        Integer saveAssetGroup(AssetGroupRequest assetGroupRequest) throws Exception;

        /**
         * 修改
         * @param assetGroupRequest
         * @return
         */
        Integer updateAssetGroup(AssetGroupRequest assetGroupRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetGroupQuery
         * @return
         */
        List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery assetGroupQuery) throws Exception;

        /**
         * 批量查询
         * @param assetGroupQuery
         * @return
         */
        PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery assetGroupQuery) throws Exception;

}
