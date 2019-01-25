package com.antiy.asset.dao;

import com.antiy.asset.entity.Scheme;
import com.antiy.common.base.IBaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 方案表 Mapper 接口
 * </p>
 *
 * @author zhangyajun
 * @since 2019-01-02
 */
public interface SchemeDao extends IBaseDao<Scheme> {
    /**
     * 通过资产ID和状态查询方案信息
     *
     * @param map
     * @return
     */
    List<Scheme> findSchemeByAssetId(Map<String,Object> map);

}
