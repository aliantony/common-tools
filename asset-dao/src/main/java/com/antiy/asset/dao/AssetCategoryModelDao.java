package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetCategoryModel;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
