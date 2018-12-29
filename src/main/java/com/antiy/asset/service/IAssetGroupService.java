package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetGroupDO;
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
 * @since 2018-12-29
 */
public interface IAssetGroupService extends IBaseService<AssetGroup> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetGroup(AssetGroupRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetGroup(AssetGroupRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetGroupResponse> findListAssetGroup(AssetGroupQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetGroupResponse> findPageAssetGroup(AssetGroupQuery query) throws Exception;

}
