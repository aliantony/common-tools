package com.antiy.asset.dao;

import com.antiy.asset.dto.AssetCategoryModelDTO;
import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 品类型号表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetCategoryModelDao extends IBaseDao<AssetCategoryModel> {

    List<AssetCategoryModelDTO> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;
}
