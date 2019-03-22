package com.antiy.asset.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.util.LogHandle;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.common.enums.ModuleEnum;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetMainboradDao;
import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.service.IAssetMainboradService;
import com.antiy.asset.vo.query.AssetMainboradQuery;
import com.antiy.asset.vo.request.AssetMainboradRequest;
import com.antiy.asset.vo.response.AssetMainboradResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LoginUserUtil;

/**
 * <p> 主板表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetMainboradServiceImpl extends BaseServiceImpl<AssetMainborad> implements IAssetMainboradService {
    private static Logger                                         logger = LogUtils.get(AssetMainboradServiceImpl.class);

    @Resource
    private AssetMainboradDao                                     assetMainboradDao;
    @Resource
    private BaseConverter<AssetMainboradRequest, AssetMainborad>  requestConverter;
    @Resource
    private BaseConverter<AssetMainborad, AssetMainboradResponse> responseConverter;

    @Override
    public Integer saveAssetMainborad(AssetMainboradRequest request) throws Exception {
        AssetMainborad assetMainborad = requestConverter.convert(request, AssetMainborad.class);
        assetMainborad.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetMainborad.setGmtCreate(System.currentTimeMillis());
        assetMainboradDao.insert(assetMainborad);
        LogHandle.log(request, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName(),
            AssetEventEnum.ASSET_MAINBORAD_INSERT.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_INSERT.getName() + " {}", request.toString());
        return assetMainborad.getId();
    }

    @Override
    public Integer updateAssetMainborad(AssetMainboradRequest request) throws Exception {
        AssetMainborad assetMainborad = requestConverter.convert(request, AssetMainborad.class);
        assetMainborad.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetMainborad.setGmtModified(System.currentTimeMillis());
        LogHandle.log(request, AssetEventEnum.ASSET_MAINBORAD_UPDATE.getName(),
            AssetEventEnum.ASSET_MAINBORAD_UPDATE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_UPDATE.getName() + " {}", request.toString());
        return assetMainboradDao.update(assetMainborad);
    }

    @Override
    public List<AssetMainboradResponse> findListAssetMainborad(AssetMainboradQuery query) throws Exception {
        List<AssetMainborad> assetMainborad = assetMainboradDao.findListAssetMainborad(query);
        // TODO
        List<AssetMainboradResponse> assetMainboradResponse = responseConverter.convert(assetMainborad,
            AssetMainboradResponse.class);
        return assetMainboradResponse;
    }

    public Integer findCountAssetMainborad(AssetMainboradQuery query) throws Exception {
        return assetMainboradDao.findCount(query);
    }

    @Override
    public PageResult<AssetMainboradResponse> findPageAssetMainborad(AssetMainboradQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetMainborad(query), query.getCurrentPage(),
            this.findListAssetMainborad(query));
    }

    @Override
    public Integer deleteById(Serializable id) throws Exception {
        LogHandle.log(id, AssetEventEnum.ASSET_MAINBORAD_DELETE.getName(),
            AssetEventEnum.ASSET_MAINBORAD_DELETE.getStatus(), ModuleEnum.ASSET.getCode());
        LogUtils.info(logger, AssetEventEnum.ASSET_MAINBORAD_DELETE.getName() + " {}", id);
        return super.deleteById(id);
    }
}
