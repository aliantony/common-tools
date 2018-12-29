package com.antiy.asset.service;

import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import java.io.Serializable;
import java.util.List;

import com.antiy.asset.entity.dto.AssetSoftwareLicenseDO;
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
         * @param request
         * @return
         */
        Integer saveAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception;

        /**
         * 修改
         * @param request
         * @return
         */
        Integer updateAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception;

        /**
         * 查询对象集合
         * @param query
         * @return
         */
        @Override
        public List<AssetSoftwareLicenseResponse> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception;

        /**
         * 批量查询
         * @param query
         * @return
         */
        PageResult<AssetSoftwareLicenseResponse> findPageAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception;

}
