package com.antiy.asset.dao;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.Topology;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.common.base.IBaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资产主表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetDao extends IBaseDao<Asset> {

    List<Asset> findListAsset(AssetQuery query) throws Exception;

    List<Asset> checkRepeatAsset(List<String[]> ipMac);

    /**
     * 批量修改
     *
     * @param map
     * @return
     */
    Integer changeStatus(Map<String, Integer[]> map) throws Exception;


    /**
     * 网络拓扑查询
     * @param query
     * @return
     * @throws Exception
     */
    List<Topology> findTopologyList(AssetQuery query);

}
