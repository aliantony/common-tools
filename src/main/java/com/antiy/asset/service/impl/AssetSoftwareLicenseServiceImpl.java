package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.entity.dto.AssetSoftwareLicenseDTO;
import com.antiy.asset.entity.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.entity.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.asset.entity.vo.query.AssetSoftwareLicenseQuery;


import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 软件许可表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetSoftwareLicenseServiceImpl extends BaseServiceImpl<AssetSoftwareLicense> implements IAssetSoftwareLicenseService{


        @Resource
        private AssetSoftwareLicenseDao assetSoftwareLicenseDao;
        @Resource
        private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense>  requestConverter;
        @Resource
        private BaseConverter<AssetSoftwareLicense, AssetSoftwareLicenseResponse> responseConverter;

        @Override
        public Integer saveAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception {
            AssetSoftwareLicense assetSoftwareLicense = requestConverter.convert(request, AssetSoftwareLicense.class);
            return assetSoftwareLicenseDao.insert(assetSoftwareLicense);
        }

        @Override
        public Integer updateAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception {
            AssetSoftwareLicense assetSoftwareLicense = requestConverter.convert(request, AssetSoftwareLicense.class);
            return assetSoftwareLicenseDao.update(assetSoftwareLicense);
        }

        @Override
        public List<AssetSoftwareLicenseResponse> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception {
            List<AssetSoftwareLicenseDTO> assetSoftwareLicenseDTO = assetSoftwareLicenseDao.findListAssetSoftwareLicense(query);
            //TODO
            //需要将assetSoftwareLicenseDTO转达成AssetSoftwareLicenseResponse
            List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponse = new ArrayList<AssetSoftwareLicenseResponse>();
            return assetSoftwareLicenseResponse;
        }

        public Integer findCountAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception {
            return assetSoftwareLicenseDao.findCount(query);
        }

        @Override
        public PageResult<AssetSoftwareLicenseResponse> findPageAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetSoftwareLicense(query),query.getCurrentPage(), this.findListAssetSoftwareLicense(query));
        }
}
