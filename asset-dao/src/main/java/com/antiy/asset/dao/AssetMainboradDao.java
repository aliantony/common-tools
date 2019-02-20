package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetHardDisk;
import com.antiy.asset.entity.AssetMainborad;
import com.antiy.asset.vo.query.AssetMainboradQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;

/**
 * <p>
 * 主板表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetMainboradDao extends IBaseDao<AssetMainborad> {

    List<AssetMainborad> findListAssetMainborad(AssetMainboradQuery query) throws Exception;

    /**
     * 批量更新
     * @param assetMainborad
     * @return
     */
    Integer updateBatch(List<AssetMainborad> assetMainborad);

    /**
     * 批量插入
     * @param assetMainborad
     * @return
     */
    Integer insertBatch(List<AssetMainborad> assetMainborad);

    /**
     * 根据资产id删除主板
     * @param assetId
     * @return
     */
    Integer deleteByAssetId(Integer assetId);

    List<AssetMainborad> findMainboardByAssetId(Integer assestId);
}
