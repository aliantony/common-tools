package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.antiy.asset.entity.AssetSoftware;
import com.antiy.asset.vo.query.AssetSoftwareQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p>
 * 软件信息表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface AssetSoftwareDao extends IBaseDao<AssetSoftware> {

    List<AssetSoftware> findListAssetSoftware(AssetSoftwareQuery query) throws Exception;

    int checkRepeatSoftware(AssetSoftware software);

    /**
     * 查询下拉项的资产操作系统信息
     *
     * @return
     */
    List<String> findOS() throws Exception;

    /**
     * 模糊查询所有厂商
     * @param manufacturerName
     * @return
     */
    List<String> findManufacturerName(@Param(value = "manufacturerName") String manufacturerName,@Param("areaIds") List<String> areaIds);
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

    Long findCountByCategoryModel(AssetSoftwareQuery assetSoftwareQuery);
}
