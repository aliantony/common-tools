package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

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
        return assetMainborad.getId();
    }

    @Override
    public Integer updateAssetMainborad(AssetMainboradRequest request) throws Exception {
        AssetMainborad assetMainborad = requestConverter.convert(request, AssetMainborad.class);
        assetMainborad.setModifyUser(LoginUserUtil.getLoginUser().getId());
        assetMainborad.setGmtModified(System.currentTimeMillis());
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
}
