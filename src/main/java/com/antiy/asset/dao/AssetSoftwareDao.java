package com.antiy.asset.dao;

import com.antiy.asset.base.IBaseDao;
import com.antiy.asset.entity.AssetSoftware;

import java.util.List;


/**
 * <p>
 * 软件信息表 Mapper 接口
 * </p>
 *
 * @author xxxxxxx
 * @since 2018-12-25
 */
public interface AssetSoftwareDao extends IBaseDao<AssetSoftware> {

        List<AssetSoftware> getByAssetId(int i);
}
