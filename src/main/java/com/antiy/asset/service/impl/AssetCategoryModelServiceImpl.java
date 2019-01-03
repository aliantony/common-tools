package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.vo.response.AssetCategoryModelResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 品类型号表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
@Service
@Slf4j
public class AssetCategoryModelServiceImpl extends BaseServiceImpl<AssetCategoryModel> implements IAssetCategoryModelService {


    @Resource
    private AssetCategoryModelDao assetCategoryModelDao;
    @Resource
    private BaseConverter<AssetCategoryModelRequest, AssetCategoryModel> requestConverter;
    @Resource
    private BaseConverter<AssetCategoryModel, AssetCategoryModelResponse> responseConverter;

    @Override
    public Integer saveAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        return assetCategoryModelDao.insert(assetCategoryModel);
    }

    @Override
    public Integer updateAssetCategoryModel(AssetCategoryModelRequest request) throws Exception {
        AssetCategoryModel assetCategoryModel = requestConverter.convert(request, AssetCategoryModel.class);
        return assetCategoryModelDao.update(assetCategoryModel);
    }

    @Override
    public List<AssetCategoryModel> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        List<AssetCategoryModel> assetCategoryModel = assetCategoryModelDao.findListAssetCategoryModel(query);
        //TODO


        return assetCategoryModel;
    }

    public Integer findCountAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return assetCategoryModelDao.findCount(query);
    }

    @Override
    public PageResult<AssetCategoryModel> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCountAssetCategoryModel(query), query.getCurrentPage(), this.findListAssetCategoryModel(query));
    }
}
