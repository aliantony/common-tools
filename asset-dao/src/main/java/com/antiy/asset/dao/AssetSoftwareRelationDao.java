package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareInstall;
import com.antiy.asset.entity.AssetSoftwareRelation;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.asset.vo.query.InstallQuery;
import com.antiy.asset.vo.response.AssetSoftwareInstallResponse;
import com.antiy.asset.vo.response.SoftwareResponse;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产软件关系信息 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSoftwareRelationDao extends IBaseDao<AssetSoftwareRelation> {

    /**
     * 通过资产ID查询关联软件信息
     *
     * @param assetId
     * @return
     */
    List<AssetSoftware> getSoftByAssetId(Integer assetId);

    /**
     * 通过资产id查询关联软件简要信息 id,名称,品类型号,软件大小,厂商,发布时间,端口,许可秘钥
     * @param query
     * @return
     */
    List<SoftwareResponse> getSimpleSoftwareByAssetId(AssetSoftwareQuery query);

    /**
     * 根据assetId资产id统计数量
     * @param assetId
     * @return
     */
    Integer countSoftwareByAssetId(Integer assetId);

    /**
     * 通过软件ID统计资产数量
     *
     * @param id
     * @return
     */
    Integer countAssetBySoftId(Integer id);

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return
     */
    List<String> findOS(@Param(value = "areaIds") List<Integer> areaIds) throws Exception;

    /**
     * 查询软件关联的硬件
     * @param softwareIds
     * @return
     */
    List<Map<String, Object>> countSoftwareRelAsset(@Param("softwareIds") List<Integer> softwareIds);

    /**
     * 
     * @param assetId
     * @param softwareId
     * @return
     */
    Integer deleteSoftwareRelAsset(@Param("assetId") Integer assetId, @Param("softwareId") Integer softwareId);

    /**
     * 获取关联表的Id 列表
     * @param assetId 资产Id信息
     * @param softwareId 软件资产Id
     * @return
     */
    List<Integer> getAllReleationId(@Param("assetId") Integer assetId, @Param("softwareId") Integer softwareId);

    /**
     * 删除资产下的软件
     * @param id
     * @return
     */
    Integer deleteByAssetId(Integer id);

    /**
     * 新资产软件关系
     * @param assetSoftwareRelationList
     * @return
     */
    Integer insertBatch(List<AssetSoftwareRelation> assetSoftwareRelationList);

    /**
     * 批量修改软件状态
     *
     * @param map
     * @return
     */
    Integer changeSoftwareStatus(Map<String, Object> map) throws Exception;

    /**
     * 查询资产软件关系表
     * @param id
     * @return
     */
    List<AssetSoftwareRelation> getReleationByAssetId(Integer id);

    /**
     * 安装列表查询
     * @param query
     * @return
     */
    List<AssetSoftwareInstall> queryInstallList(InstallQuery query);

    /**
     * 安装列表数量查询
     * @param query
     * @return
     */
    Integer queryInstallCount(InstallQuery query);

    /**
     * 查询资产上安装的软件，用于下发智甲
     * @param assetId
     * @return
     */
    List<AssetSoftware> findInstalledSoft(@Param("assetId") String assetId);

    /**
     * 等判断软件是否已经执行过安装操作
     * @param softwareId
     * @param assetIds
     * @return
     */
    Integer checkInstalled(@Param("softwareId") String softwareId, @Param("list") List<String> assetIds);

    /**
     * 通过软件ID和资产ID获取配置状态
     * @param query
     * @return
     */
    Integer findInstallStatus(AssetSoftwareQuery query);

    /**
     * 查询资产已关联的软件列表
     * @param query
     * @return
     */
    List<AssetSoftwareInstallResponse> queryInstalledList(InstallQuery query);

    /**
     * 查询基准模板名单类型
     * @param query
     * @return
     */
    Integer queryNameListType(InstallQuery query);

    /**
     * 查询基准模板中的软件
     * @param query
     * @return
     */
    List<Long> querySoftwareIds(InstallQuery query);

    /**
     * 查询可关联软件列表
     * @param query
     * @param nameListType
     * @param softwareIds
     * @return
     */
    List<AssetSoftwareInstallResponse> queryInstallableList(@Param("query") InstallQuery query,
                                                            @Param("nameListType") Integer nameListType,
                                                            @Param("softwareIds") List<Long> softwareIds,
                                                            @Param("installedSoftIds") List<String> installedSoftIds);

    /**
     * 查询可关联软件数量
     * @param query
     * @param nameListType
     * @param softwareIds
     * @return
     */
    Integer queryInstallableCount(@Param("query") InstallQuery query, @Param("nameListType") Integer nameListType,
                                  @Param("softwareIds") List<Long> softwareIds,
                                  @Param("installedSoftIds") List<String> installedSoftIds);

    /**
     * 删除资产与软件的关联关系
     * @param id
     */
    void deleteSoftRealtion(@Param("id") String id);
}
