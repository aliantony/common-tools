package com.antiy.asset.dao;

import java.util.List;

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

    /**
     * 根据唯一键删除所有关联 资产
     * @param query
     * @return
     * @throws Exception
     */
    Integer deleteAllById(String query) throws Exception;

    /**
     * 批量插入关联资产
     * @param assetList
     * @return
     * @throws Exception
     */
    Integer insertBatch(List<AssetMonitorRuleRelation> assetList) throws Exception;

}
