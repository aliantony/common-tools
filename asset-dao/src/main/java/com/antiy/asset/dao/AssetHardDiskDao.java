package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetCpu;
import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.vo.query.AssetHardDiskQuery;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 硬盘表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetHardDiskDao extends IBaseDao<AssetHardDisk> {

    List<AssetHardDisk> findListAssetHardDisk(AssetHardDiskQuery query) throws Exception;

    Integer updateBatch(@Param(value = "list") List<AssetHardDisk> assetHardDiskList) throws Exception;

    Integer insertBatch(List<AssetHardDisk> assetHardDiskList);

    Integer deleteByAssetId(Integer id);

    List<AssetHardDisk> findHardDiskByAssetId(Integer assestId);
}
