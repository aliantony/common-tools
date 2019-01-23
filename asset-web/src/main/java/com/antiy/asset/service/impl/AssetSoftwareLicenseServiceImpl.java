package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 软件许可表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetSoftwareLicenseServiceImpl extends BaseServiceImpl<AssetSoftwareLicense>
                                             implements IAssetSoftwareLicenseService {

    @Resource
    private AssetSoftwareLicenseDao                                           assetSoftwareLicenseDao;
    @Resource
    private BaseConverter<AssetSoftwareLicenseRequest, AssetSoftwareLicense>  requestConverter;
    @Resource
    private BaseConverter<AssetSoftwareLicense, AssetSoftwareLicenseResponse> responseConverter;
    private static final Logger logger = LogUtils.get(AssetServiceImpl.class);
    @Override
    public Integer saveAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception {
        AssetSoftwareLicense assetSoftwareLicense = requestConverter.convert(request, AssetSoftwareLicense.class);
        assetSoftwareLicense.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetSoftwareLicense.setGmtCreate(System.currentTimeMillis());
        assetSoftwareLicenseDao.insert(assetSoftwareLicense);
        LogHandle.log(request, AssetEventEnum.ASSET_MODIFY.getName(), AssetEventEnum.ASSET_MODIFY.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum .ASSET_MODIFY.getName() + " {}", request.toString());
        return assetSoftwareLicense.getId();
    }

    @Override
    public Integer updateAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception {
        AssetSoftwareLicense assetSoftwareLicense = requestConverter.convert(request, AssetSoftwareLicense.class);
        assetSoftwareLicense.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetSoftwareLicense.setGmtModified(System.currentTimeMillis());
        return assetSoftwareLicenseDao.update(assetSoftwareLicense);
    }

    @Override
    public List<AssetSoftwareLicenseResponse> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery assetSoftwareLicenseQuery) throws Exception {
        List<AssetSoftwareLicense> assetSoftwareLicenseList = assetSoftwareLicenseDao
            .findQuery(assetSoftwareLicenseQuery);
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponse = responseConverter
            .convert(assetSoftwareLicenseList, AssetSoftwareLicenseResponse.class);
        return assetSoftwareLicenseResponse;
    }

    @Override
    public PageResult<AssetSoftwareLicenseResponse> findPageAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSoftwareLicense(query));
    }
}
