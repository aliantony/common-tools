package com.antiy.asset.service.impl;

import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import org.springframework.stereotype.Service;
import com.antiy.asset.entity.AssetStorageMedium;
import com.antiy.asset.dao.AssetStorageMediumDao;
import com.antiy.asset.service.IAssetStorageMediumService;
import com.antiy.asset.vo.request.AssetStorageMediumRequest;
import com.antiy.asset.vo.response.AssetStorageMediumResponse;
import com.antiy.asset.vo.query.AssetStorageMediumQuery;
import javax.annotation.Resource;

/**
 * <p> 服务实现类 </p>
 *
 * @author lvliang
 * @since 2019-01-17
 */
@Service
public class AssetStorageMediumServiceImpl extends BaseServiceImpl<AssetStorageMedium>
                                           implements IAssetStorageMediumService {

    private static final Logger                                           logger = LogUtils.get();

    @Resource
    private AssetStorageMediumDao                                         assetStorageMediumDao;
    @Resource
    private BaseConverter<AssetStorageMediumRequest, AssetStorageMedium>  requestConverter;
    @Resource
    private BaseConverter<AssetStorageMedium, AssetStorageMediumResponse> responseConverter;

    @Override
    public Integer saveAssetStorageMedium(AssetStorageMediumRequest request) throws Exception {
        AssetStorageMedium assetStorageMedium = requestConverter.convert(request, AssetStorageMedium.class);
        assetStorageMediumDao.insert(assetStorageMedium);
        return assetStorageMedium.getId();
    }

    @Override
    public Integer updateAssetStorageMedium(AssetStorageMediumRequest request) throws Exception {
        AssetStorageMedium assetStorageMedium = requestConverter.convert(request, AssetStorageMedium.class);
        return assetStorageMediumDao.update(assetStorageMedium);
    }

    @Override
    public List<AssetStorageMediumResponse> queryListAssetStorageMedium(AssetStorageMediumQuery query) throws Exception {
        List<AssetStorageMedium> assetStorageMediumList = assetStorageMediumDao.findQuery(query);
        // TODO
        List<AssetStorageMediumResponse> assetStorageMediumResponse = responseConverter.convert(assetStorageMediumList,
            AssetStorageMediumResponse.class);
        return assetStorageMediumResponse;
    }

    @Override
    public PageResult<AssetStorageMediumResponse> queryPageAssetStorageMedium(AssetStorageMediumQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
            this.queryListAssetStorageMedium(query));
    }
}
