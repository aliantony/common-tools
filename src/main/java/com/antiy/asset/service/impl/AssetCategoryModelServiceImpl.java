package com.antiy.asset.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import java.util.List;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.BaseConverter;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.dao.AssetCategoryModelDao;
import com.antiy.asset.service.IAssetCategoryModelService;
import com.antiy.asset.entity.dto.AssetCategoryModelDTO;
import com.antiy.asset.entity.vo.request.AssetCategoryModelRequest;
import com.antiy.asset.entity.vo.response.AssetCategoryModelResponse;
import com.antiy.asset.entity.vo.query.AssetCategoryModelQuery;


import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 品类型号表 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
@Service
@Slf4j
public class AssetCategoryModelServiceImpl extends BaseServiceImpl<AssetCategoryModel> implements IAssetCategoryModelService{


        @Resource
        private AssetCategoryModelDao assetCategoryModelDao;
        @Resource
        private BaseConverter<AssetCategoryModelRequest, AssetCategoryModel>  requestConverter;
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
        public List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
            List<AssetCategoryModelDTO> assetCategoryModelDTO = assetCategoryModelDao.findListAssetCategoryModel(query);
            //TODO
            //需要将assetCategoryModelDTO转达成AssetCategoryModelResponse
            List<AssetCategoryModelResponse> assetCategoryModelResponse = new ArrayList<AssetCategoryModelResponse>();
            return assetCategoryModelResponse;
        }

        public Integer findCountAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
            return assetCategoryModelDao.findCount(query);
        }

        @Override
        public PageResult<AssetCategoryModelResponse> findPageAssetCategoryModel(AssetCategoryModelQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCountAssetCategoryModel(query),query.getCurrentPage(), this.findListAssetCategoryModel(query));
        }
}
