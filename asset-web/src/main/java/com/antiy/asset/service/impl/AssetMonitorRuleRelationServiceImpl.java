package com.antiy.asset.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.antiy.asset.util.AreaUtils;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetCategoryModelQuery;
import com.antiy.common.exception.BusinessException;
import com.antiy.common.utils.LoginUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
    @Resource
    private AssetCategoryModelServiceImpl categoryModelService;
    @Resource
    private AssetEntryServiceImpl entryService;

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
        //转化综合查询
        if (StringUtils.isNotBlank(query.getMultipleQuery())) {
            query.setMultipleQuery(query.getMultipleQuery().replace(':','-'));
        }
        //设置资产状态
        query.setStatusList(Arrays.asList(AssetStatusEnum.NET_IN.getCode(),AssetStatusEnum.WAIT_RETIRE.getCode()));
        //设置计算设备类型
        query.setCategoryIds(getCategoryIdsOfComputer());
        Integer count = this.findCount(query);
        if (count <= 0) {
            return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(), Collections.emptyList());
        }
        return new PageResult<>(query.getPageSize(), count, query.getCurrentPage(),
                this.queryListAssetMonitorRuleRelation(query));
    }
    public List<String> getCategoryIdsOfComputer() throws Exception {
        AssetCategoryModelQuery modelQuery = new AssetCategoryModelQuery();
        modelQuery.setName("计算设备");
        return categoryModelService.queryCategoryWithOutRootNode(modelQuery)
                .stream().collect(
                        LinkedList::new, (list, v) -> entryService.getCategoryIds(v, list), List::addAll);
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
