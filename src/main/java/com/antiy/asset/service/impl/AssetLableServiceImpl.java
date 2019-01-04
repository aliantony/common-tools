package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLableDao;
import com.antiy.asset.entity.AssetLable;
import com.antiy.asset.service.IAssetLableService;
import com.antiy.asset.vo.query.AssetLableQuery;
import com.antiy.asset.vo.request.AssetLableRequest;
import com.antiy.asset.vo.response.AssetLableResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 标签信息表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
public class AssetLableServiceImpl extends BaseServiceImpl<AssetLable> implements IAssetLableService {


    @Resource
    private AssetLableDao assetLableDao;
    @Resource
    private BaseConverter<AssetLableRequest, AssetLable> requestConverter;
    @Resource
    private BaseConverter<AssetLable, AssetLableResponse> responseConverter;

    @Override
    public Integer saveAssetLable(AssetLableRequest request) throws Exception {
        AssetLable assetLable = requestConverter.convert(request, AssetLable.class);
        return assetLableDao.insert(assetLable);
    }

    @Override
    public Integer updateAssetLable(AssetLableRequest request) throws Exception {
        AssetLable assetLable = requestConverter.convert(request, AssetLable.class);
        return assetLableDao.update(assetLable);
    }

    @Override
    public List<AssetLableResponse> findListAssetLable(AssetLableQuery query) throws Exception {
        List<AssetLable> assetLable = assetLableDao.findListAssetLable(query);
        //TODO
        List<AssetLableResponse> assetLableResponse = responseConverter.convert(assetLable, AssetLableResponse.class);
        return assetLableResponse;
    }

    public Integer findCountAssetLable(AssetLableQuery query) throws Exception {
        return assetLableDao.findCount(query);
    }

    @Override
    public PageResult<AssetLableResponse> findPageAssetLable(AssetLableQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetLable(query), query.getCurrentPage(), this.findListAssetLable(query));
    }
}
