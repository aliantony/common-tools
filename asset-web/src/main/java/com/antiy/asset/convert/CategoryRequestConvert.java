package com.antiy.asset.convert;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.request.AssetCategoryModelRequest;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CategoryRequestConvert extends BaseConverter<AssetCategoryModelRequest, AssetCategoryModel> {
    Logger logger = LogUtils.get(CategoryRequestConvert.class);

    @Override
    protected void convert(AssetCategoryModelRequest assetCategoryModelRequest, AssetCategoryModel assetCategoryModel) {
        try {
            assetCategoryModel.setId(Integer.parseInt(assetCategoryModelRequest.getId()));
        } catch (Exception e) {
            logger.error("String转Integer出错");
        }
        super.convert(assetCategoryModelRequest, assetCategoryModel);
    }
}