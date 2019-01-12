package com.antiy.asset.dao;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.Topology;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.SelectResponse;
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
     *
     * @param query
     * @return
     * @throws Exception
     */
    List<Topology> findTopologyList(AssetQuery query);


    /**
     * 查询下拉的厂商信息
     *
     * @return
     */
    List<String> pulldownManufacturer() throws Exception;

    /**
     * 通过品类型号查询资产数量
     *
     * @param query
     * @return
     */
    Integer findCountByCategoryModel(AssetCategoryModelQuery query) throws Exception;

    /**
     * 通过品类型号查询资产列表
     *
     * @param query
     * @return
     */
    List<Asset> findListAssetByCategoryModel(AssetCategoryModelQuery query) throws Exception;

    /**
     * 统计厂商数量
     *
     * @return
     */
    List<Map<String, Long>> countManufacturer();

    /**
     * 统计状态数量
     *
     * @return
     */
    List<Map<String, Long>> countStatus();
    /**
     * 通过ID列表查询资产列表
     *
     * @param ids
     * @return actionResponse
     */
    List<Asset> queryAssetByIds(Integer[] ids);
}
