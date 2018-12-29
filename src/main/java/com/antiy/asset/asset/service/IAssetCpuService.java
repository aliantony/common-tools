package com.antiy.asset.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.asset.entity.vo.query.AssetCpuQuery;
import com.antiy.asset.asset.entity.vo.request.AssetCpuRequest;
import com.antiy.asset.asset.entity.vo.response.AssetCpuResponse;
import com.antiy.asset.asset.entity.AssetCpu;


/**
 * <p>
 * 处理器表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetCpuService extends IBaseService<AssetCpu> {

        /**
         * 保存
         * @param assetCpuRequest
         * @return
         */
        Integer saveAssetCpu(AssetCpuRequest assetCpuRequest) throws Exception;

        /**
         * 修改
         * @param assetCpuRequest
         * @return
         */
        Integer updateAssetCpu(AssetCpuRequest assetCpuRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetCpuQuery
         * @return
         */
        List<AssetCpuResponse> findListAssetCpu(AssetCpuQuery assetCpuQuery) throws Exception;

        /**
         * 批量查询
         * @param assetCpuQuery
         * @return
         */
        PageResult<AssetCpuResponse> findPageAssetCpu(AssetCpuQuery assetCpuQuery) throws Exception;

}
