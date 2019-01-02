package com.antiy.asset.dao;

import java.util.List;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.dto.AssetHardDiskDTO;
import com.antiy.asset.vo.query.AssetHardDiskQuery;

/**
 * <p>
 * 硬盘表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2018-12-29
 */
public interface AssetHardDiskDao extends IBaseDao<AssetHardDisk> {

    List<AssetHardDiskDTO> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception;
}
