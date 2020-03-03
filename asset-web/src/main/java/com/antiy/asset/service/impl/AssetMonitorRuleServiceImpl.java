package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.antiy.asset.component.WrappedRedisUtil;
import com.antiy.asset.dao.AssetMonitorRuleDao;
import com.antiy.asset.dao.AssetMonitorRuleRelationDao;
import com.antiy.asset.entity.AssetMonitorRule;
import com.antiy.asset.entity.AssetMonitorRuleRelation;
import com.antiy.asset.factory.ConditionFactory;
import com.antiy.asset.login.LoginTool;
import com.antiy.asset.service.IAssetMonitorRuleService;
import com.antiy.asset.util.SnowFlakeUtil;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.StatusEnum;
import com.antiy.asset.vo.query.AssetMonitorRuleQuery;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;

/**
 * <p> 资产监控规则表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
@Service
public class AssetMonitorRuleServiceImpl extends BaseServiceImpl<AssetMonitorRule> implements IAssetMonitorRuleService {

    private Logger                                                    logger = LogUtils.get(this.getClass());

    @Resource
    private AssetMonitorRuleDao                                       assetMonitorRuleDao;
    @Resource
    private AssetMonitorRuleRelationDao                               monitorRuleRelationDao;
    @Resource
    private BaseConverter<AssetMonitorRuleRequest, AssetMonitorRule>  requestConverter;
    @Resource
    private BaseConverter<AssetMonitorRule, AssetMonitorRuleResponse> responseConverter;
    @Resource
    private WrappedRedisUtil                                          wrappedRedisUtil;

    @Override
    public String saveAssetMonitorRule(AssetMonitorRuleRequest request) throws Exception {

        AssetMonitorRule assetMonitorRule = requestConverter.convert(request, AssetMonitorRule.class);
        assetMonitorRule.setAreaId(LoginTool.getLoginUser().getAreaId());
        assetMonitorRule.setGmtCreate(System.currentTimeMillis());
        assetMonitorRule.setCreateUser(LoginTool.getLoginUser().getId());
        assetMonitorRule.setUniqueId(SnowFlakeUtil.getSnowId());
        assetMonitorRule.setUnit(JSON.toJSONString(request.getRuntimeExceptionThreshold()));
        logger.info(AssetEventEnum.ADD_ASSET_MONITOR_RULE.getName(), JSON.toJSONString(request));
        assetMonitorRuleDao.insert(assetMonitorRule);

        // 插入关联资产数据
        List<String> assetIdList = request.getRelatedAsset();
        logger.info(AssetEventEnum.ADD_ASSET_MONITOR_RULE.getName(), JSON.toJSONString(assetIdList));
        if (CollectionUtils.isNotEmpty(assetIdList)) {
            for (String assetId : assetIdList) {
                AssetMonitorRuleRelation monitorRuleRelation = new AssetMonitorRuleRelation();
                monitorRuleRelation.setUniqueId(SnowFlakeUtil.getSnowId());
                monitorRuleRelation.setRuleUniqueId(assetMonitorRule.getUniqueId());
                monitorRuleRelation.setGmtCreate(System.currentTimeMillis());
                monitorRuleRelation.setAssetId(assetId);
                monitorRuleRelationDao.insert(monitorRuleRelation);
            }
        }

        return assetMonitorRule.getUniqueId();
    }

    @Override
    public String updateAssetMonitorRule(AssetMonitorRuleRequest request) throws Exception {
        AssetMonitorRule assetMonitorRule = requestConverter.convert(request, AssetMonitorRule.class);
        return assetMonitorRuleDao.update(assetMonitorRule).toString();
    }

    @Override
    public List<AssetMonitorRuleResponse> queryListAssetMonitorRule(AssetMonitorRuleQuery query) throws Exception {
        ConditionFactory.createAreaQuery(query);
        List<AssetMonitorRule> assetMonitorRuleList = assetMonitorRuleDao.findQuery(query);
        return responseConverter.convert(assetMonitorRuleList, AssetMonitorRuleResponse.class);
    }

    @Override
    public PageResult<AssetMonitorRuleResponse> queryPageAssetMonitorRule(AssetMonitorRuleQuery query) throws Exception {
        Integer num = this.findCount(query);
        if (num > 0) {
            List<AssetMonitorRuleResponse> monitorRuleResponseList = this.queryListAssetMonitorRule(query);
            for (AssetMonitorRuleResponse assetMonitorRuleResponse : monitorRuleResponseList) {
                // TODO setAlarmLevelName
                assetMonitorRuleResponse.setAlarmLevelName("");
                assetMonitorRuleResponse
                    .setAreaName(wrappedRedisUtil.bindAreaName(assetMonitorRuleResponse.getAreaId()));
                assetMonitorRuleResponse
                    .setRuleStatusName(StatusEnum.getEnumByCode(assetMonitorRuleResponse.getRuleStatus()).getName());

                // 统计关系资产数量
                String uniqueId = assetMonitorRuleResponse.getUniqueId();
                AssetMonitorRuleRelationQuery ruleRelationQuery = new AssetMonitorRuleRelationQuery();
                ruleRelationQuery.setRuleUniqueId(uniqueId);
                ConditionFactory.createAreaQuery(ruleRelationQuery);
                assetMonitorRuleResponse
                    .setRelatedAssetAmount(monitorRuleRelationDao.assetAmountRelatedRule(ruleRelationQuery));
            }
            return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
                monitorRuleResponseList);
        } else {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
    }

    @Override
    public AssetMonitorRuleResponse queryAssetMonitorRuleById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetMonitorRuleResponse assetMonitorRuleResponse = responseConverter
            .convert(assetMonitorRuleDao.getById(queryCondition.getPrimaryKey()), AssetMonitorRuleResponse.class);
        return assetMonitorRuleResponse;
    }

    @Override
    public String deleteAssetMonitorRuleById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetMonitorRuleDao.deleteById(baseRequest.getStringId()).toString();
    }
}
