package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetAssembly;
import com.antiy.common.base.IBaseDao;
import com.antiy.asset.entity.AssetAssemblyLib;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 组件表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2019-09-19
 */
public interface AssetAssemblyLibDao extends IBaseDao<AssetAssemblyLib> {
    List<AssetAssembly> queryAssemblyByHardSoftId(@Param("id") String id);

}
