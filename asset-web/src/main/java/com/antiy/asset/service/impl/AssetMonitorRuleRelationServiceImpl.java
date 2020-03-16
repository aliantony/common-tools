package com.antiy.asset.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.antiy.asset.util.AreaUtils;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetMonitorRuleRelationDao;
import com.antiy.asset.entity.AssetMonitorRuleRelation;
import com.antiy.asset.service.IAssetMonitorRuleRelationService;
import com.antiy.asset.vo.query.AssetMonitorRuleRelationQuery;
import com.antiy.asset.vo.request.AssetMonitorRuleRelationRequest;
import com.antiy.asset.vo.response.AssetMonitorRuleRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 资产监控规则与资产关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2020-03-02
 */
@Service
public class AssetMonitorRuleRelationServiceImpl extends BaseServiceImpl<AssetMonitorRuleRelation>
        implements IAssetMonitorRuleRelationService {

    private Logger logger = LogUtils
            .get(this.getClass());

    @Resource
    private AssetMonitorRuleRelationDao assetMonitorRuleRelationDao;
    @Resource
    private BaseConverter<AssetMonitorRuleRelationRequest, AssetMonitorRuleRelation> requestConverter;
    @Resource
    private BaseConverter<AssetMonitorRuleRelation, AssetMonitorRuleRelationResponse> responseConverter;

    @Override
    public String saveAssetMonitorRuleRelation(AssetMonitorRuleRelationRequest request) throws Exception {
        AssetMonitorRuleRelation assetMonitorRuleRelation = requestConverter.convert(request,
                AssetMonitorRuleRelation.class);
        assetMonitorRuleRelation.setGmtCreate(System.currentTimeMillis());
        assetMonitorRuleRelationDao.insert(assetMonitorRuleRelation);
        return assetMonitorRuleRelation.getStringId();
    }

    @Override
    public String updateAssetMonitorRuleRelation(AssetMonitorRuleRelationRequest request) throws Exception {
        AssetMonitorRuleRelation assetMonitorRuleRelation = requestConverter.convert(request,
                AssetMonitorRuleRelation.class);
        return assetMonitorRuleRelationDao.update(assetMonitorRuleRelation).toString();
    }

    @Override
    public List<AssetMonitorRuleRelationResponse> queryListAssetMonitorRuleRelation(AssetMonitorRuleRelationQuery query) throws Exception {
        return assetMonitorRuleRelationDao.queryAsset(query);
    }

    @Override
    public PageResult<AssetMonitorRuleRelationResponse> queryPageAssetMonitorRuleRelation(AssetMonitorRuleRelationQuery query) throws Exception {
        if (CollectionUtils.isEmpty(query.getAreaList())) {
            LoginUser loginUser=LoginUserUtil.getLoginUser();
            if (Objects.isNull(loginUser)){
                throw new BusinessException("获取当前用户信息失败");
            }
            query.setAreaList(loginUser.getAreaIdsOfCurrentUser());

        }else if(query.getAreaList().size()==1){
            //获取所有的下级区域
            query.setAreaList(AreaUtils.getWholeNextArea(query.getAreaList().get(0)));
        }
        Integer count = this.findCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), Collections.emptyList());
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                this.queryListAssetMonitorRuleRelation(query));
    }

    @Override
    public AssetMonitorRuleRelationResponse queryAssetMonitorRuleRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetMonitorRuleRelationResponse assetMonitorRuleRelationResponse = responseConverter.convert(
                assetMonitorRuleRelationDao.getById(queryCondition.getPrimaryKey()),
                AssetMonitorRuleRelationResponse.class);
        return assetMonitorRuleRelationResponse;
    }

    @Override
    public String deleteAssetMonitorRuleRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetMonitorRuleRelationDao.deleteById(baseRequest.getStringId()).toString();
    }
}
