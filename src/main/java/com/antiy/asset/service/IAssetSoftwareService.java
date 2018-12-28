package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.vo.query.AssetSoftwareQuery;
import com.antiy.asset.entity.vo.request.AssetSoftwareRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareResponse;
import com.antiy.asset.entity.AssetSoftware;


/**
 * <p>
 * 软件信息表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-28
 */
public interface IAssetSoftwareService extends IBaseService<AssetSoftware> {

        /**
         * 保存
         * @param assetSoftwareRequest
         * @return
         */
        Integer saveAssetSoftware(AssetSoftwareRequest assetSoftwareRequest) throws Exception;

        /**
         * 修改
         * @param assetSoftwareRequest
         * @return
         */
        Integer updateAssetSoftware(AssetSoftwareRequest assetSoftwareRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetSoftwareQuery
         * @return
         */
        List<AssetSoftwareResponse> findListAssetSoftware(AssetSoftwareQuery assetSoftwareQuery) throws Exception;

        /**
         * 批量查询
         * @param assetSoftwareQuery
         * @return
         */
        PageResult<AssetSoftwareResponse> findPageAssetSoftware(AssetSoftwareQuery assetSoftwareQuery) throws Exception;

}
