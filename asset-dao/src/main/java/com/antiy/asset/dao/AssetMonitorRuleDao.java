package com.antiy.asset.dao;

import java.util.List;

import com.antiy.asset.entity.AssetMonitorRule;
import com.antiy.asset.entity.AssetMonitorRuleRelation;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产监控规则表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
public interface AssetMonitorRuleDao extends IBaseDao<AssetMonitorRule> {
    /**
     * 资产数量排序数量统计
     * @param query
     * @return
     * @throws Exception
     */
    List<AssetMonitorRuleRelation> assetAmountSort(AssetMonitorRuleRelationQuery query) throws Exception;
}
