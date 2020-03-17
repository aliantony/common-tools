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
import com.antiy.asset.vo.enums.AlarmLevelEnum;
import com.antiy.asset.vo.enums.AssetEventEnum;
import com.antiy.asset.vo.enums.StatusEnum;
import com.antiy.asset.vo.query.AssetMonitorRuleQuery;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.asset.vo.query.BaseQuery;
import com.antiy.asset.vo.query.UniqueKeyQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleResponse;
import com.antiy.asset.vo.response.AssetResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseRequest;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
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
        // 去重判断
        nameNoRepeat(request.getName());

        AssetMonitorRule assetMonitorRule = requestConverter.convert(request, AssetMonitorRule.class);
        assetMonitorRule.setAreaId(LoginTool.getLoginUser().getAreaId());
        assetMonitorRule.setGmtCreate(System.currentTimeMillis());
        assetMonitorRule.setCreateUser(LoginTool.getLoginUser().getId());
        assetMonitorRule.setUniqueId(SnowFlakeUtil.getSnowId());
        assetMonitorRule.setRuntimeExceptionThreshold(JSON.toJSONString(request.getRuntimeExceptionThreshold()));
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

    /**
     * 名称去重
     * @param name
     * @throws Exception
     */
    private void nameNoRepeat(String name) throws Exception {
        Boolean existName = assetMonitorRuleDao.nameNoRepeat(name);
        if (existName) {
            throw new BusinessException("规则名称重复");
        }
    }

    @Override
    public String updateAssetMonitorRule(AssetMonitorRuleRequest request) throws Exception {
        // 幂等判断
        Boolean isDelete = assetMonitorRuleDao.idempotent(request.getUniqueId());
        if (isDelete) {
            throw new BusinessException("资产已被删除");
        }
        // 去重判断
        String originalName = assetMonitorRuleDao.getById(request.getUniqueId()).getName();
        String acceptName = request.getName();
        if (!acceptName.equals(originalName)) {
            nameNoRepeat(acceptName);
        }
        AssetMonitorRule assetMonitorRule = requestConverter.convert(request, AssetMonitorRule.class);
        assetMonitorRule.setModifyUser(LoginTool.getLoginUser().getId());
        assetMonitorRule.setGmtModified(System.currentTimeMillis());
        assetMonitorRule.setRuntimeExceptionThreshold(JSON.toJSONString(request.getRuntimeExceptionThreshold()));
        // 更新关联资产
        List<String> relatedAssetList = request.getRelatedAsset();
        List<AssetMonitorRuleRelation> assetList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(relatedAssetList)) {
            monitorRuleRelationDao.deleteAllById(request.getUniqueId());
            // 重建规则关联的资产
            for (String assetId : relatedAssetList) {
                AssetMonitorRuleRelation monitorRuleRelation = new AssetMonitorRuleRelation();
                monitorRuleRelation.setUniqueId(SnowFlakeUtil.getSnowId());
                monitorRuleRelation.setRuleUniqueId(request.getUniqueId());
                monitorRuleRelation.setGmtCreate(System.currentTimeMillis());
                monitorRuleRelation.setAssetId(assetId);
                assetList.add(monitorRuleRelation);
            }
            monitorRuleRelationDao.insertBatch(assetList);
        }

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
            for (AssetMonitorRuleResponse ruleResponse : monitorRuleResponseList) {
                ruleResponse.setAlarmLevelName(AlarmLevelEnum.getEnumByCode(ruleResponse.getAlarmLevel()).getName());
                ruleResponse.setAreaName(wrappedRedisUtil.bindAreaName(ruleResponse.getAreaId()));
                ruleResponse.setRuleStatusName(StatusEnum.getEnumByCode(ruleResponse.getRuleStatus()).getName());
            }
            if (!query.getRelatedAssetSort()) {

                for (AssetMonitorRuleResponse assetMonitorRuleResponse : monitorRuleResponseList) {
                    // 统计关系资产数量
                    String uniqueId = assetMonitorRuleResponse.getUniqueId();
                    AssetMonitorRuleRelationQuery ruleRelationQuery = new AssetMonitorRuleRelationQuery();
                    ruleRelationQuery.setRuleUniqueId(uniqueId);
                    ConditionFactory.createAreaQuery(ruleRelationQuery);
                    assetMonitorRuleResponse
                        .setRelatedAssetAmount(monitorRuleRelationDao.assetAmountRelatedRule(ruleRelationQuery));
                }
            }

            return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(),
                monitorRuleResponseList);
        } else {
            return new PageResult<>(query.getPageSize(), 0, query.getCurrentPage(), Lists.newArrayList());
        }
    }

    @Override
    public AssetMonitorRuleResponse queryAssetMonitorRuleById(BaseQuery query) throws Exception {
        ParamterExceptionUtils.isBlank(query.getUniqueId(), "主键Id不能为空");
        AssetMonitorRuleResponse assetMonitorRuleResponse = responseConverter
            .convert(assetMonitorRuleDao.getById(query.getUniqueId()), AssetMonitorRuleResponse.class);
        assetMonitorRuleResponse.setAreaName(wrappedRedisUtil.bindAreaName(assetMonitorRuleResponse.getAreaId()));
        return assetMonitorRuleResponse;
    }

    @Override
    public String deleteAssetMonitorRuleById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetMonitorRuleDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public Integer editRuleStatus(String uniqueId, Boolean useFlag) {
        if (useFlag) {
            return assetMonitorRuleDao.editRuleStatusByUI(uniqueId, 1);
        } else {
            return assetMonitorRuleDao.editRuleStatusByUI(uniqueId, 0);
        }
    }

    @Override
    public Integer deleteByUniqueId(String uniqueId) {

        return  assetMonitorRuleDao.deleteByUniqueId(uniqueId);
    }

    @Override
    public AssetMonitorRuleResponse queryByUniqueId(String uniqueId) throws Exception {
        AssetMonitorRule assetMonitorRule = assetMonitorRuleDao.queryByUniqueId(uniqueId);
        AssetMonitorRuleResponse responseAssetRule = responseConverter.convert(assetMonitorRule, AssetMonitorRuleResponse.class);
        responseAssetRule.setAlarmLevelName(AlarmLevelEnum.getEnumByCode(responseAssetRule.getAlarmLevel()).getName());
        responseAssetRule.setAreaName(wrappedRedisUtil.bindAreaName(responseAssetRule.getAreaId()));
        responseAssetRule.setRuleStatusName(StatusEnum.getEnumByCode(responseAssetRule.getRuleStatus()).getName());
        return  responseAssetRule;
    }

    @Override
    public PageResult<AssetResponse> queryAssetByUniqueId(UniqueKeyQuery uniqueKeyQuery) {
        uniqueKeyQuery.setAreaList(LoginUserUtil.getLoginUser().getAreaIdsOfCurrentUser());
        Integer count=assetMonitorRuleDao.countAssetByUniqueId(uniqueKeyQuery);
        if(count>0){
            List<AssetResponse> asetResponseList=assetMonitorRuleDao.queryAssetByUniqueId(uniqueKeyQuery);
            return new PageResult(uniqueKeyQuery.getPageSize(),0,uniqueKeyQuery.getCurrentPage(),asetResponseList);
        }
        return new PageResult<>(uniqueKeyQuery.getPageSize(), 0, uniqueKeyQuery.getCurrentPage(), Lists.newArrayList());
    }
}
