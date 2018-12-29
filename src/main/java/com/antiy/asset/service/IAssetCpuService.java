package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetCpuDTO;;
import com.antiy.asset.entity.vo.query.AssetCpuQuery;
import com.antiy.asset.entity.vo.request.AssetCpuRequest;
import com.antiy.asset.entity.vo.response.AssetCpuResponse;
import com.antiy.asset.entity.AssetCpu;


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
         * @param request
         * @return
         */
        Integer saveAssetCpu(AssetCpuRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetCpu(AssetCpuRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        List<AssetCpuResponse> findListAssetCpu(AssetCpuQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetCpuResponse> findPageAssetCpu(AssetCpuQuery query) throws Exception;

}
