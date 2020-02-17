package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetBusiness;
import com.antiy.common.base.IBaseDao;

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
}
