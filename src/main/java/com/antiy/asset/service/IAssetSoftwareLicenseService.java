package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;

import java.util.List;

import com.antiy.asset.entity.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.entity.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.asset.entity.AssetSoftwareLicense;


/**
 * <p>
 * 软件许可表 服务类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface IAssetSoftwareLicenseService extends IBaseService<AssetSoftwareLicense> {

        /**
         * 保存
         * @param assetSoftwareLicenseRequest
         * @return
         */
        Integer saveAssetSoftwareLicense(AssetSoftwareLicenseRequest assetSoftwareLicenseRequest) throws Exception;

        /**
         * 修改
         * @param assetSoftwareLicenseRequest
         * @return
         */
        Integer updateAssetSoftwareLicense(AssetSoftwareLicenseRequest assetSoftwareLicenseRequest) throws Exception;

        /**
         * 查询对象集合
         * @param assetSoftwareLicenseQuery
         * @return
         */
        List<AssetSoftwareLicenseResponse> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery assetSoftwareLicenseQuery) throws Exception;

        /**
         * 批量查询
         * @param assetSoftwareLicenseQuery
         * @return
         */
        PageResult<AssetSoftwareLicenseResponse> findPageAssetSoftwareLicense(AssetSoftwareLicenseQuery assetSoftwareLicenseQuery) throws Exception;

}
