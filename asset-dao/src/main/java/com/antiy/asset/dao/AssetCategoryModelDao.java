package com.antiy.asset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.entity.CategoryValiEntity;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 品类型号表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetCategoryModelDao extends IBaseDao<AssetCategoryModel> {

    List<AssetCategoryModel> findListAssetCategoryModel(AssetCategoryModelQuery query) throws Exception;

    Integer delete(List<AssetCategoryModel> list);

    List<AssetCategoryModel> getNextLevelCategoryByName(String name) throws Exception;

    List<AssetCategoryModel> getNextLevelCategoryByNameArray(@Param("names") String[] names) throws Exception;

    Integer findRepeatName(@Param("id") Integer id, @Param("name") String name);

    /**
     * 查找全部分类
     *
     * @return
     */
    List<AssetCategoryModel> findAllCategory();

    AssetCategoryModel getByName(@Param("name") String name);

    CategoryValiEntity getNameByAssetId(int id);

    CategoryValiEntity getNameByCtegoryId(int id);

    Integer hasChild(@Param("id") String categoryModel);
}
