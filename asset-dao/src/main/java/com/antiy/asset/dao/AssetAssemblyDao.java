package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetAssembly;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产组件关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetAssemblyDao extends IBaseDao<AssetAssembly> {
    /**
     * 删除资产与组件关系
     * @param id
     */
    void deleteAssemblyRelation(String id);
}
