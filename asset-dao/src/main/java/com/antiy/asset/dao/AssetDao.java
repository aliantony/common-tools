package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.entity.Topology;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AlarmAssetRequest;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产主表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetDao extends IBaseDao<Asset> {

    List<Asset> findListAsset(AssetQuery query) throws Exception;

    List<Asset> checkRepeatAsset(List<String[]> ipMac);

    /**
     * 批量修改资产状态
     *
     * @param map
     * @return
     */
    Integer changeStatus(Map<String, Object> map) throws Exception;

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
     * 通过品类型号和资产状态查询资产数量
     *
     * @param query
     * @return
     */
    Integer findCountByCategoryModel(AssetQuery query) throws Exception;

    /**
     * 通过品类型号查询资产列表
     *
     * @param query
     * @return
     */
    List<Asset> findListAssetByCategoryModel(AssetQuery query) throws Exception;

    /**
     * ip查重复
     *
     * @param query
     * @return
     */
    Integer findCountIp(AssetQuery query) throws Exception;

    /**
     * 统计厂商数量
     *
     * @return
     */
    List<Map<String, Object>> countManufacturer(@Param("areaIds") List<Integer> areaIds,
                                                @Param("assetStatusList") List<Integer> statusList);

    /**
     * 统计状态数量
     *
     * @return
     */
    List<Map<String, Long>> countStatus(@Param("areaIds") List<Integer> areaIds);

    /**
     * 通过ID列表查询资产列表
     *
     * @param ids
     * @return actionResponse
     */
    List<Asset> queryAssetByIds(Integer[] ids);

    /**
     * 通过区域Id列表查询当前区域是否存在资产
     * @param areaIds
     * @return
     */
    Integer queryAssetCountByAreaIds(@Param(value = "areaIds") List<Integer> areaIds);

    /**
     * 通过ID更新资产组字段名称
     *
     * @param map
     * @return Integer
     */
    Integer updateAssetGroupNameWithAssetId(Map<String, Object> map);


    /**
     * 更新资产的区域id
     * @param
     * @return
     */
    Integer updateAssetAreaId(@Param("areaId") String areaId, @Param("areaIds") List<String> areaIds);

    /**
     * 查询资产漏洞数
     * @param areaIds
     * @return
     */
    List<IdCount> queryAssetVulCount(@Param(value = "areaIds") List<Integer> areaIds,
                                     @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    /**
     * 查询资产补丁数
     * @param areaIds
     * @return
     */
    List<IdCount> queryAssetPatchCount(@Param(value = "areaIds") List<Integer> areaIds,
                                       @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    /**
     * 查询补丁的资产总数
     * @return
     */
    Integer queryAllAssetPatchCount(@Param(value = "areaIds") List<Integer> areaIds);

    /**
     * 查询漏洞的资产总数
     * @return
     */
    Integer queryAllAssetVulCount(@Param(value = "areaIds") List<Integer> areaIds);

    /**
     * 流程引擎返回的的id排序
     * @param activitiIds
     * @return
     */
    List<String> sortAssetIds(@Param("ids") Set<String> activitiIds, @Param("assetStatus") Integer assetStatus);

    /**
     * 
     * @param alarmAssetRequest
     * @return
     */
    List<Asset> queryAlarmAssetList(AlarmAssetRequest alarmAssetRequest);

    /**
     * 通过资产ID获取UUID
     * @param assetId
     * @return
     */
    String getUUIDByAssetId(String assetId);

    /**
     * 通过资产ID获取资产编号
     * @param assetId
     * @return
     */
    String getNumberById(String assetId);

    /**
     * 通过资产ID获取区域ID
     * @param assetId
     * @return
     */
    String getAreaIdById(String assetId);

    /**
     * 判断当前品类下是否有资产
     * @param categoryModeId
     * @return
     */
    Integer existAssetByCategoryModelId(String categoryModeId);

    /**
     * 修改资产状态
     * @param asset
     * @return
     * @throws Exception
     */
    Integer updateStatus(Asset asset) throws Exception;

    List<String> findAssetIds(@Param("areaIds") List<Integer> areaIds);
}
