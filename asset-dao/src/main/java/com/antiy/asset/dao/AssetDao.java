package com.antiy.asset.dao;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetAssembly;
import com.antiy.asset.entity.IdCount;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.request.AlarmAssetRequest;
import com.antiy.asset.vo.response.SelectResponse;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * mac查重复
     *
     * @param mac
     * @return
     */
    Integer findCountMac(@Param("mac") String mac) throws Exception;

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
    List<Map<String, Object>> countStatus(@Param("areaIds") List<Integer> areaIds);

    /**
     * 通过ID列表查询资产列表
     *
     * @param ids
     * @return actionResponse
     */
    List<Asset> queryAssetByIds(Integer[] ids);

    /**
     * 通过区域Id列表查询当前区域是否存在资产
     *
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
     *
     * @param
     * @return
     */
    Integer updateAssetAreaId(@Param("areaId") String areaId, @Param("areaIds") List<String> areaIds);

    /**
     * 查询资产漏洞数
     *
     * @param areaIds
     * @return
     */
    List<IdCount> queryAssetVulCount(@Param(value = "areaIds") List<Integer> areaIds,
                                     @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    /**
     * 查询资产补丁数
     *
     * @param areaIds
     * @return
     */
    List<IdCount> queryAssetPatchCount(@Param(value = "areaIds") List<Integer> areaIds,
                                       @Param("pageSize") Integer pageSize, @Param("pageOffset") Integer pageOffset);

    /**
     * 查询补丁的资产总数
     *
     * @return
     */
    Integer queryAllAssetPatchCount(@Param(value = "areaIds") List<Integer> areaIds);

    /**
     * 查询漏洞的资产总数
     *
     * @return
     */
    Integer queryAllAssetVulCount(@Param(value = "areaIds") List<Integer> areaIds);

    /**
     * 流程引擎返回的的id排序
     *
     * @param activitiIds
     * @return
     */
    List<String> sortAssetIds(@Param("ids") Set<String> activitiIds, @Param("assetStatus") Integer assetStatus);

    /**
     * @param alarmAssetRequest
     * @return
     */
    List<Asset> queryAlarmAssetList(AlarmAssetRequest alarmAssetRequest);

    /**
     * 通过资产ID获取UUID
     *
     * @param assetId
     * @return
     */
    String getUUIDByAssetId(String assetId);

    /**
     * 通过资产ID获取资产编号
     *
     * @param assetId
     * @return
     */
    String getNumberById(String assetId);

    /**
     * 通过资产ID获取区域ID
     *
     * @param assetId
     * @return
     */
    String getAreaIdById(String assetId);

    /**
     * 判断当前品类下是否有资产
     *
     * @param categoryModeId
     * @return
     */
    Integer existAssetByCategoryModelId(String categoryModeId);

    /**
     * 修改资产状态
     *
     * @param asset
     * @return
     * @throws Exception
     */
    Integer updateStatus(Asset asset) throws Exception;

    List<String> findAssetIds(@Param("areaIds") List<Integer> areaIds);

    Integer insertBatch(List<Asset> assets);

    /**
     * 工作台待登记数量
     *
     * @param assetStatus
     * @param areaIds
     * @return
     */
    Integer queryWaitRegistCount(@Param(value = "assetStatus") Integer assetStatus,
                                 @Param(value = "areaIds") List<Integer> areaIds);

    Integer changeAsset(Asset asset);

    Integer selectRepeatNumber(@Param("number") String number, @Param("id") String id);

    Integer selectRepeatName(@Param("name") String name, @Param("id") String id);

    /**
     * 统计资产数量
     * @return
     */
    Integer countAsset();

    /**
     * 统计正常资产数量
     * @return
     */
    Integer queryNormalCount(AssetQuery query);

    List<IdCount> queryAlarmCountByAssetIds(AssetQuery query);

    /**
     * 根据区域ID返回资产UUID
     * @param areaIds
     * @return
     * @throws Exception
     */
    List<String> findUuidByAreaIds(List<String> areaIds) throws Exception;

    List<Integer> findAlarmAssetId(AssetQuery query);

    int findAlarmAssetCount(AssetQuery query);

    /**
     * 通过资产id查询对应资产信息<br>
     * 结果包含id、当前状态assetStatus、资产编号number、首次入网时间
     *
     * @param ids
     * @return
     */
    List<Asset> findByIds(@Param("ids") List<Integer> ids);

    /**
     * 流程变更时批量更新资产信息
     * @param assetList
     * @return
     */
    Integer updateAssetBatch(@Param("assetList") List<Asset> assetList);

    /**
     * 使用上一次状态lastAssetStatus和id更新资产状态asset_status字段
     * @param newAsset
     * @param lastAssetStatus
     * @return
     */
    Integer updateAssetStatusWithLock(@Param("asset") Asset newAsset, @Param("lastAssetStatus") Integer lastAssetStatus);

    /**
     *
     */
    Asset getByAssetId(@Param("id") String id);

    List<AssetAssembly> getAssemblyInfoById(@Param("id") String id);

    /**
     * 统计品类
     * @param areaIdsOfCurrentUser    当前登录用户对应的区域
     * @param status      资产状体（不包含已退役资产状态）
     * @return
     */

    List<Map<String, Object>> countCategoryModel(@Param("areaIds") List<Integer> areaIdsOfCurrentUser, @Param("status") List<Integer> status);

    Integer findCountAssetNumber(@Param("number") String number);

    List<Asset> getAssetStatusListByIds(@Param("ids") String[]ids);

    List<SelectResponse> queryBaselineTemplate();
}
