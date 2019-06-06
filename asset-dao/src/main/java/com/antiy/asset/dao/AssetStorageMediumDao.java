package com.antiy.asset.dao;

import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetStorageMedium;

import java.util.List;

/**
 * <p> Mapper 接口 </p>
 *
 * @author lvliang
 * @since 2019-01-17
 */
public interface AssetStorageMediumDao extends IBaseDao<AssetStorageMedium> {

    Integer insertBatch(List<AssetStorageMedium> assetStorageMedia);
}
