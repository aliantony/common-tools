package com.antiy.asset.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.entity.AssetMemory;
import com.antiy.asset.vo.query.AssetHardDiskQuery;
import com.antiy.common.base.IBaseDao;

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

    Integer insertBatchWithId(@Param(value = "list") List<AssetHardDisk> assetCpuList, @Param(value = "aid") Integer aid);

    Integer deleteByAssetId(Integer id);

    List<AssetHardDisk> findHardDiskByAssetId(Integer assestId);
}
