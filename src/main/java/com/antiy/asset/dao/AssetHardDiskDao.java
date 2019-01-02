package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.vo.query.AssetHardDiskQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 硬盘表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetHardDiskDao extends IBaseDao<AssetHardDisk> {

    List<AssetHardDisk> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception;
}
