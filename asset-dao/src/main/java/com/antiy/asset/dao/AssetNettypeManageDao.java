package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetNettypeManage;
import com.antiy.common.base.IBaseDao;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangqian
 * @since 2020-04-07
 */
public interface AssetNettypeManageDao extends IBaseDao<AssetNettypeManage> {

    /**
     * 根据名称和id查询数量
     * @param id
     * @param netTypeName
     * @return
     */
    public Integer getCountByNetTypeName(@Param("id") Integer id, @Param("netTypeName") String netTypeName);

    public Integer findIdsByName(String name);
}
