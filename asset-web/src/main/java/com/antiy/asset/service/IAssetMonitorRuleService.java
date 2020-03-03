package com.antiy.asset.service;

import java.util.List;

import com.antiy.asset.entity.AssetMonitorRule;
import com.antiy.asset.vo.query.AssetMonitorRuleQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleResponse;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.IBaseService;
import com.antiy.common.base.PageResult;
import com.antiy.common.base.QueryCondition;

/**
 * <p> 资产监控规则表 服务类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
public interface IAssetMonitorRuleService extends IBaseService<AssetMonitorRule> {

    /**
     * 保存
     * @param request
     * @return
     */
    String saveAssetMonitorRule(AssetMonitorRuleRequest request) throws Exception;

    /**
     * 修改
     * @param request
     * @return
     */
    String updateAssetMonitorRule(AssetMonitorRuleRequest request) throws Exception;

    /**
     * 查询对象集合
     * @param query
     * @return
     */
    List<AssetMonitorRuleResponse> queryListAssetMonitorRule(AssetMonitorRuleQuery query) throws Exception;

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<AssetMonitorRuleResponse> queryPageAssetMonitorRule(AssetMonitorRuleQuery query) throws Exception;

    /**
     * 通过ID查询
     * @param queryCondition
     * @return
     */
    AssetMonitorRuleResponse queryAssetMonitorRuleById(QueryCondition queryCondition) throws Exception;

    /**
     * 通过ID删除
     * @param baseRequest
     * @return
     */
    String deleteAssetMonitorRuleById(BaseRequest baseRequest) throws Exception;

}
