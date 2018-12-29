package com.antiy.asset.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.asset.entity.AssetHardDisk;
import com.antiy.asset.asset.entity.vo.query.AssetHardDiskQuery;
import com.antiy.asset.asset.entity.vo.response.AssetHardDiskResponse;

/**
 * <p>
 * 硬盘表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetHardDiskDao extends IBaseDao<AssetHardDisk> {

    List<AssetHardDiskResponse> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception;
}
