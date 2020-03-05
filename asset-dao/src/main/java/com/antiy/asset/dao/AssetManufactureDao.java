package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetManufacture;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 安全厂商表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2020-03-05
 */
public interface AssetManufactureDao extends IBaseDao<AssetManufacture> {

    List<AssetManufacture> getManufacture();

    List<AssetManufacture> initManufacture();

}
