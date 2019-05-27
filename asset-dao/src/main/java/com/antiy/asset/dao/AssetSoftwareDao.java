package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.entity.AssetSoftwareInstall;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 软件信息表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSoftwareDao extends IBaseDao<AssetSoftware> {

    List<AssetSoftware> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;

    int checkRepeatSoftware(AssetSoftware software);

    /**
     * 模糊查询所有厂商
     * @param manufacturerName
     * @return
     */
    List<String> findManufacturerName(@Param(value = "manufacturerName") String manufacturerName,
                                      @Param("areaIds") List<Integer> areaIds);

    /**
     * 统计厂商数量
     *
     * @return
     */
    List<Map<String, Object>> countManufacturer(@Param("softwareStatusList") List softwareStatusList);

    /**
     * 统计状态数量
     *
     * @return
     */
    List<Map<String, Object>> countStatus();

    Long findCountByCategoryModel(AssetSoftwareQuery assetSoftwareQuery);

    /**
     * 通过ID修改软件状态
     *
     * @param map
     * @return
     */
    Integer changeStatusById(Map<String, Object> map) throws Exception;

    List<String> pulldownManufacturer();

    Integer findCount(AssetSoftwareQuery assetSoftwareQuery);

    Integer findCountCheck(AssetSoftwareQuery assetSoftwareQuery);

    List<AssetSoftware> findInstallList(AssetSoftwareQuery softwareQuery);

    List<AssetSoftwareInstall> findAssetInstallList(AssetSoftwareQuery softwareQuery);

    Integer findAssetInstallCount(AssetSoftwareQuery softwareQuery);

    Integer findCountInstall(AssetSoftwareQuery query);

    Integer insertBatch(List<AssetSoftware> assetList);

    Integer isExsit(@Param(value = "name") String name, @Param(value = "version") String version);

    /**
     * 通过软件ID获取文件路径
     * @param id
     * @return
     */
    String getPath(String id);

    /**
     * 通过软件ID和资产ID获取配置状态
     * @param query
     * @return
     */
    Integer findInstallStatus(AssetSoftwareQuery query);
}
