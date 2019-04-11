package com.antiy.asset.dao;

import java.util.List;
import java.util.Map;

import com.antiy.asset.entity.Scheme;
import com.antiy.asset.vo.query.AssetIDAndSchemeTypeQuery;
import com.antiy.asset.vo.query.SchemeQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 方案表 Mapper 接口 </p>
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
    List<Scheme> findSchemeByAssetIdAndStatus(Map<String, Object> map);

    /**
     * 通过资产ID和方案类型查询方案信息
     *
     * @param query
     * @return
     */
    Scheme findSchemeByAssetIdAndType(AssetIDAndSchemeTypeQuery query);

    /**
     * 通过资产ID查询上一个状态的备注信息
     * @param query
     * @return
     */
    Scheme findMemoById(SchemeQuery query);

}
