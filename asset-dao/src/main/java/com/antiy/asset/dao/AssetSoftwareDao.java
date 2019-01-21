package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetSoftwareResponse;
import javafx.beans.binding.ObjectExpression;
import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetSoftware;
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
    List<Map<String, Long>> countManufacturer(@Param("areaIds") List<Integer> areaIds);

    /**
     * 统计状态数量
     *
     * @return
     */
    List<Map<String, Long>> countStatus(@Param("areaIds") List<Integer> areaIds);

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

    List<AssetSoftware> findInstallList(AssetSoftwareQuery softwareQuery);
}
