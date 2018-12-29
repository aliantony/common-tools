package com.antiy.asset.service;

import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.entity.vo.query.AssetHardDiskQuery;
import com.antiy.asset.entity.vo.request.AssetHardDiskRequest;
import com.antiy.asset.entity.vo.response.AssetHardDiskResponse;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

;


/**
 * <p>
 * 硬盘表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetHardDiskService extends IBaseService<AssetHardDisk> {

        /**
         * 保存
         * @param request
         * @return
         */
        Integer saveAssetHardDisk(AssetHardDiskRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetHardDisk(AssetHardDiskRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetHardDiskResponse> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetHardDiskResponse> findPageAssetHardDisk(AssetHardDiskQuery query) throws Exception;

}
