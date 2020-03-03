package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetMonitorRuleRelation;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRelationRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleRelationResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 资产监控规则与资产关系表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
public interface IAssetMonitorRuleRelationService extends IBaseService<AssetMonitorRuleRelation> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetMonitorRuleRelation(AssetMonitorRuleRelationRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetMonitorRuleRelation(AssetMonitorRuleRelationRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetMonitorRuleRelationResponse> queryListAssetMonitorRuleRelation(AssetMonitorRuleRelationQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetMonitorRuleRelationResponse> queryPageAssetMonitorRuleRelation(AssetMonitorRuleRelationQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetMonitorRuleRelationResponse queryAssetMonitorRuleRelationById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetMonitorRuleRelationById(BaseRequest baseRequest) throws Exception;

}
