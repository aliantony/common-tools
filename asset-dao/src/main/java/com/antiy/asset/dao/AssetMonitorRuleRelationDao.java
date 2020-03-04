package com.antiy.asset.dao;

import com.antiy.asset.entity.AssetMonitorRuleRelation;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.common.base.IBaseDao;

/**
 * <p> 资产监控规则与资产关系表 Mapper 接口 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
public interface AssetMonitorRuleRelationDao extends IBaseDao<AssetMonitorRuleRelation> {

    Integer assetAmountRelatedRule(AssetMonitorRuleRelationQuery query) throws Exception;


}
