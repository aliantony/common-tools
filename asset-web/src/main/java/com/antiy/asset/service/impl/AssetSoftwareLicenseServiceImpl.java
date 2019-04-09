package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.antiy.asset.dao.AssetSoftwareLicenseDao;
import com.antiy.asset.entity.AssetSoftwareLicense;
import com.antiy.asset.service.IAssetSoftwareLicenseService;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.query.AssetSoftwareLicenseQuery;
import com.antiy.asset.vo.request.AssetSoftwareLicenseRequest;
import com.antiy.asset.vo.response.AssetSoftwareLicenseResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.BusinessData;
import com.antiy.common.base.PageResult;
import com.antiy.common.enums.BusinessModuleEnum;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 软件许可表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
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
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFTWARE_LICENSE_INSERT.getName(),
            assetSoftwareLicense.getId(), null, assetSoftwareLicense, BusinessModuleEnum.SOFTWARE_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.SOFTWARE_LICENSE_INSERT.getName() + " {}", assetSoftwareLicense);
        return assetSoftwareLicense.getId();
    }

    @Override
    public Integer updateAssetSoftwareLicense(AssetSoftwareLicenseRequest request) throws Exception {
        AssetSoftwareLicense assetSoftwareLicense = requestConverter.convert(request, AssetSoftwareLicense.class);
        assetSoftwareLicense.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetSoftwareLicense.setGmtModified(System.currentTimeMillis());
        // 记录操作日志和运行日志
        LogUtils.recordOperLog(new BusinessData(AssetEventEnum.SOFTWARE_LICENSE_UPDATE.getName(),
            assetSoftwareLicense.getId(), null, assetSoftwareLicense, BusinessModuleEnum.SOFTWARE_ASSET, null));
        LogUtils.info(logger, AssetEventEnum.SOFTWARE_LICENSE_UPDATE.getName() + " {}", assetSoftwareLicense);
        return assetSoftwareLicenseDao.update(assetSoftwareLicense);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AssetSoftwareLicenseResponse> findListAssetSoftwareLicense(AssetSoftwareLicenseQuery assetSoftwareLicenseQuery) throws Exception {
        List<AssetSoftwareLicense> assetSoftwareLicenseList = assetSoftwareLicenseDao
            .findQuery(assetSoftwareLicenseQuery);
        List<AssetSoftwareLicenseResponse> assetSoftwareLicenseResponse = responseConverter
            .convert(assetSoftwareLicenseList, AssetSoftwareLicenseResponse.class);
        return assetSoftwareLicenseResponse;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageResult<AssetSoftwareLicenseResponse> findPageAssetSoftwareLicense(AssetSoftwareLicenseQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.findListAssetSoftwareLicense(query));
    }
}
