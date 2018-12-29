package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.entity.vo.response.AssetCategoryModelResponse;

/**
 * <p>
 * 品类型号表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetCategoryModelDao extends IBaseDao<AssetCategoryModel> {

    List<AssetCategoryModelResponse> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;
}
