package com.antiy.asset.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.antiy.asset.entity.Asset;
import com.antiy.asset.util.BeanConvert;
import com.antiy.asset.vo.enums.AssetStatusEnum;
import com.antiy.asset.vo.query.AssetQuery;
import com.antiy.asset.vo.response.AssetResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.antiy.asset.dao.AssetLinkRelationDao;
import com.antiy.asset.entity.AssetLinkRelation;
import com.antiy.asset.service.IAssetLinkRelationService;
import com.antiy.asset.vo.query.AssetLinkRelationQuery;
import com.antiy.asset.vo.request.AssetLinkRelationRequest;
import com.antiy.asset.vo.response.AssetLinkRelationResponse;
import com.antiy.common.base.*;
import com.antiy.common.utils.DataTypeUtils;
import com.antiy.common.utils.LogUtils;
import com.antiy.common.utils.ParamterExceptionUtils;

/**
 * <p> 通联关系表 服务实现类 </p>
 *
 * @author zhangyajun
 * @since 2019-04-02
 */
@Service
public class AssetLinkRelationServiceImpl extends BaseServiceImpl<AssetLinkRelation>
                                          implements IAssetLinkRelationService {

    private static final Logger                                         logger = LogUtils.get();

    @Resource
    private AssetLinkRelationDao                                        assetLinkRelationDao;
    @Resource
    private BaseConverter<AssetLinkRelationRequest, AssetLinkRelation>  requestConverter;
    @Resource
    private BaseConverter<AssetLinkRelation, AssetLinkRelationResponse> responseConverter;

    @Override
    public String saveAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        assetLinkRelationDao.insert(assetLinkRelation);
        return assetLinkRelation.getStringId();
    }

    @Override
    public String updateAssetLinkRelation(AssetLinkRelationRequest request) throws Exception {
        AssetLinkRelation assetLinkRelation = requestConverter.convert(request, AssetLinkRelation.class);
        return assetLinkRelationDao.update(assetLinkRelation).toString();
    }

    @Override
    public List<AssetLinkRelationResponse> queryListAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        List<AssetLinkRelation> assetLinkRelationList = assetLinkRelationDao.findQuery(query);
        // TODO
        return responseConverter.convert(assetLinkRelationList, AssetLinkRelationResponse.class);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryPageAssetLinkRelation(AssetLinkRelationQuery query) throws Exception {
        return new PageResult<AssetLinkRelationResponse>(query.getPageSize(), this.findCount(query),
            query.getCurrentPage(), this.queryListAssetLinkRelation(query));
    }

    @Override
    public AssetLinkRelationResponse queryAssetLinkRelationById(QueryCondition queryCondition) throws Exception {
        ParamterExceptionUtils.isBlank(queryCondition.getPrimaryKey(), "主键Id不能为空");
        AssetLinkRelationResponse assetLinkRelationResponse = responseConverter.convert(
            assetLinkRelationDao.getById(DataTypeUtils.stringToInteger(queryCondition.getPrimaryKey())),
            AssetLinkRelationResponse.class);
        return assetLinkRelationResponse;
    }

    @Override
    public String deleteAssetLinkRelationById(BaseRequest baseRequest) throws Exception {
        ParamterExceptionUtils.isBlank(baseRequest.getStringId(), "主键Id不能为空");
        return assetLinkRelationDao.deleteById(baseRequest.getStringId()).toString();
    }

    @Override
    public PageResult<AssetResponse> queryAssetPage(AssetQuery assetQuery) {
        // 已入网的资产
        assetQuery.setAssetStatus(AssetStatusEnum.NET_IN.getCode());
        if (StringUtils.isNotBlank(assetQuery.getPrimaryKey())) {
            // 查询已关联（并绑定了端口）的资产id及其自身,查询时需要排除
            List<String> assetIds = assetLinkRelationDao.queryLinkedAssetById(assetQuery.getPrimaryKey());
            if (CollectionUtils.isNotEmpty(assetIds)) {
                assetIds.add(assetQuery.getPrimaryKey());
                String[] ids = assetIds.toArray(new String[assetIds.size()]);
                assetQuery.setIds(ids);
            } else {
                assetQuery.setIds(new String[] { assetQuery.getPrimaryKey() });
            }
        } else {
            // 查询已经在关系表中存在的资产,将其排除
            List<String> assetIds = assetLinkRelationDao.queryLinkedAssetWithoutId();
            if (CollectionUtils.isNotEmpty(assetIds)) {
                assetQuery.setIds(assetIds.toArray(new String[assetIds.size()]));
            }
        }
        List<AssetResponse> assetList = this.queryAssetList(assetQuery);
        if (assetList.size() <= 0) {
            return new PageResult<AssetResponse>(assetQuery.getPageSize(), 0, assetQuery.getCurrentPage(),
                Lists.newArrayList());
        }
        return new PageResult<AssetResponse>(assetQuery.getPageSize(), assetList.size(), assetQuery.getCurrentPage(),
            this.queryAssetList(assetQuery));
    }

    @Override
    public List<AssetResponse> queryAssetList(AssetQuery assetQuery) {
        List<Asset> assetResponseList = assetLinkRelationDao.queryAssetList(assetQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetResponse.class);
    }

    @Override
    public PageResult<AssetLinkRelationResponse> queryLinekedRelationPage(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelationResponse> assetLinkRelationResponseList = this
            .queryLinekedRelationList(assetLinkRelationQuery);
        if (assetLinkRelationResponseList.size() <= 0) {
            return new PageResult<AssetLinkRelationResponse>(assetLinkRelationQuery.getPageSize(), 0,
                assetLinkRelationQuery.getCurrentPage(), Lists.newArrayList());
        }
        return new PageResult<AssetLinkRelationResponse>(assetLinkRelationQuery.getPageSize(),
            assetLinkRelationResponseList.size(), assetLinkRelationQuery.getCurrentPage(),
            this.queryLinekedRelationList(assetLinkRelationQuery));
    }

    @Override
    public List<AssetLinkRelationResponse> queryLinekedRelationList(AssetLinkRelationQuery assetLinkRelationQuery) {
        List<AssetLinkRelation> assetResponseList = assetLinkRelationDao
            .queryLinekedRelationList(assetLinkRelationQuery);
        if (CollectionUtils.isEmpty(assetResponseList)) {
            return Lists.newArrayList();
        }
        return BeanConvert.convert(assetResponseList, AssetLinkRelationResponse.class);
    }
}
