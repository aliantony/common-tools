package com.antiy.asset.vo.query;

/**
 * <p> AssetMonitorRuleRelation 查询条件 </p>
 *
 * @author zhangyajun
 * @since 2018-12-27
 */

public class AssetMonitorRuleRelationQuery extends AssetBaseQuery {
    private String ruleUniqueId;

    public String getRuleUniqueId() {
        return ruleUniqueId;
    }

    public void setRuleUniqueId(String ruleUniqueId) {
        this.ruleUniqueId = ruleUniqueId;
    }
}