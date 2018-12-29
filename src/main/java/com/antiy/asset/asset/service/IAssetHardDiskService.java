package com.antiy.asset.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetHardDiskQuery;
import com.antiy.asset.asset.entity.vo.request.AssetHardDiskRequest;
import com.antiy.asset.asset.entity.vo.response.AssetHardDiskResponse;
import com.antiy.asset.asset.entity.AssetHardDisk;


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
         * @param assetHardDiskRequest
         * @return
         */
        Integer saveAssetHardDisk(AssetHardDiskRequest assetHardDiskRequest) throws Exception;

        /**
         * 修改
         * @param assetHardDiskRequest
         * @return
         */
        Integer updateAssetHardDisk(AssetHardDiskRequest assetHardDiskRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetHardDiskQuery
         * @return
         */
        List<AssetHardDiskResponse> findListAssetHardDisk(AssetHardDiskQuery assetHardDiskQuery) throws Exception;

        /**
         * 批量查询
         * @param assetHardDiskQuery
         * @return
         */
        PageResult<AssetHardDiskResponse> findPageAssetHardDisk(AssetHardDiskQuery assetHardDiskQuery) throws Exception;

}
