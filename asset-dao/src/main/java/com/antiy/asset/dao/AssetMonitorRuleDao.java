package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetMonitorRule;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产监控规则表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
public interface AssetMonitorRuleDao extends IBaseDao<AssetMonitorRule> {

    /**
     * 去重
     * @param name
     * @return
     * @throws Exception
     */
    Boolean nameNoRepeat(String name) throws Exception;

    /**
     * 幂等
     * @param uniqueId
     * @return
     * @throws Exception
     */
    Boolean idempotent(String uniqueId) throws Exception;
}