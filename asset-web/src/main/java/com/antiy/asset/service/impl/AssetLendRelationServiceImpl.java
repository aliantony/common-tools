package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetLendRelationDao;
import com.antiy.asset.entity.Asset;
import com.antiy.asset.entity.AssetLendRelation;
import com.antiy.asset.entity.AssetOaOrderHandle;
import com.antiy.asset.service.IAssetLendRelationService;
import com.antiy.asset.util.SnowFlakeUtil;
import com.antiy.asset.vo.query.ApproveListQuery;
import com.antiy.asset.vo.query.AssetLendRelationQuery;
import com.antiy.asset.vo.request.AssetLendRelationRequest;
import com.antiy.asset.vo.request.AssetLendInfoRequest;
import com.antiy.asset.vo.response.ApproveListResponse;
import com.antiy.asset.vo.response.AssetLendRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.LoginUserUtil;
import com.antiy.common.utils.ParamterExceptionUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangyajun
 * @since 2020-04-07
 */
@Service
public class AssetLendRelationServiceImpl extends BaseServiceImpl<AssetLendRelation> implements IAssetLendRelationService {

    private Logger logger = LogUtils.get(this.getClass());

    @Resource
    private AssetLendRelationDao assetLendRelationDao;
    @Resource
    private BaseConverter<AssetLendRelationRequest, AssetLendRelation> requestConverter;
    @Resource
    private BaseConverter<AssetLendRelation, AssetLendRelationResponse> responseConverter;
    @Resource
    private BaseConverter<AssetLendInfoRequest, AssetLendRelation> lendRelationBaseConverter;

    @Override
    public String saveAssetLendRelation(AssetLendRelationRequest request) throws Exception {
        AssetLendRelation assetLendRelation = requestConverter.convert(request, AssetLendRelation.class);
        assetLendRelationDao.insert(assetLendRelation);
        return assetLendRelation.getStringId();
    }

    @Override
    public String updateAssetLendRelation(AssetLendRelationRequest request) throws Exception {
        AssetLendRelation assetLendRelation = requestConverter.convert(request, AssetLendRelation.class);
        return assetLendRelationDao.update(assetLendRelation).toString();
    }

    @Override
    public List<AssetLendRelationResponse> queryListAssetLendRelation(AssetLendRelationQuery query) throws Exception {
        List<AssetLendRelation> assetLendRelationList = assetLendRelationDao.findQuery(query);
        return responseConverter.convert(assetLendRelationList, AssetLendRelationResponse.class);
    }

    @Override
    public PageResult<AssetLendRelationResponse> queryPageAssetLendRelation(AssetLendRelationQuery query) throws Exception {
        return new PageResult<AssetLendRelationResponse>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.queryListAssetLendRelation(query));
    }

    @Override
    public AssetLendRelationResponse queryAssetLendRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetLendRelationResponse assetLendRelationResponse = responseConverter
                .convert(assetLendRelationDao.getById(queryCondition.getPrimaryKey()), AssetLendRelationResponse.class);
        return assetLendRelationResponse;
    }

    @Override
    public String deleteAssetLendRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetLendRelationDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public AssetLendRelationResponse queryInfo(String uniqueId) {

        return assetLendRelationDao.queryInfo(uniqueId);
    }

    @Override
    public Integer returnConfirm(AssetLendRelationRequest assetLendRelationRequest) {

        return assetLendRelationDao.returnConfirm(assetLendRelationRequest);
    }

    @Override
    public PageResult<AssetLendRelationResponse> queryHistory(ObjectQuery objectQuery) {
        int count=assetLendRelationDao.countHistory(objectQuery);
        if(count>0){
            List<AssetLendRelationResponse> assetLendRelationResponses=assetLendRelationDao.queryHistory(objectQuery);
            return new PageResult<>(objectQuery.getPageSize(),count,objectQuery.getCurrentPage(),assetLendRelationResponses);
        }
        return new PageResult<>(objectQuery.getPageSize(),0,objectQuery.getCurrentPage(), Lists.newArrayList());
    }

    @Override
    public Integer saveLendInfo(AssetLendInfoRequest request) throws Exception {
        ParamterExceptionUtils.isBlank(String.valueOf(request.getAssetId()), "资产Id不能为空");
        AssetLendRelation assetLendRelation = lendRelationBaseConverter.convert(request, AssetLendRelation.class);

        assetLendRelation.setUniqueId(Long.valueOf(SnowFlakeUtil.getSnowId()));
        assetLendRelation.setGmtCreate(System.currentTimeMillis());
        assetLendRelation.setCreateUser(LoginUserUtil.getLoginUser().getId());
        assetLendRelation.setStatus(Integer.valueOf(1));

        //保存资产、订单关联关系
        AssetOaOrderHandle assetOaOrderHandle = new AssetOaOrderHandle();
        assetOaOrderHandle.setAssetId(request.getAssetId());
        assetOaOrderHandle.setOrderNumber(String.valueOf(request.getOrderNumber()));
        assetLendRelationDao.insertOrderHandle(assetOaOrderHandle);

        return assetLendRelationDao.insert(assetLendRelation);
    }

    @Override
    public PageResult<ApproveListResponse> queryApproveList(ApproveListQuery query) {
        Integer count = assetLendRelationDao.queryApproveListCount(query);

        List<ApproveListResponse> assetLendRelationResponses = assetLendRelationDao.queryApproveList(query);

        return new PageResult<ApproveListResponse>(query.getPageSize(),assetLendRelationDao.queryApproveListCount(query), query.getCurrentPage(), assetLendRelationResponses);

    }
}
