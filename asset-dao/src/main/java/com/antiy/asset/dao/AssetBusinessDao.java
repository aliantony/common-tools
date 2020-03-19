package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetBusiness;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2020-02-17
 */



public interface AssetBusinessDao extends IBaseDao<AssetBusiness> {

    AssetBusiness getByName(String name);

    AssetBusiness getByUniqueId(String uniqueId);

    Integer updateStatusByUniqueId(List<String> uniqueId);

    Integer updateByUniqueId(AssetBusiness assetBusiness);
}
