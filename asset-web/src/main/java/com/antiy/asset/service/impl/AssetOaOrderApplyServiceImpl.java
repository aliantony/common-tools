package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOaOrderApplyDao;
import com.antiy.asset.entity.AssetOaOrderApply;
import com.antiy.asset.service.IAssetOaOrderApplyService;
import com.antiy.asset.vo.query.AssetOaOrderApplyQuery;
import com.antiy.asset.vo.request.AssetOaOrderApplyRequest;
import com.antiy.asset.vo.response.AssetOaOrderApplyResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单申请信息表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AssetOaOrderApplyServiceImpl extends BaseServiceImpl<AssetOaOrderApply> implements IAssetOaOrderApplyService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderApplyDao assetOaOrderApplyDao;
    @Resource
    private BaseConverter<AssetOaOrderApplyRequest, AssetOaOrderApply> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrderApply, AssetOaOrderApplyResponse> responseConverter;

    @Override
    public Integer saveAssetOaOrderApply(AssetOaOrderApplyRequest request) throws Exception {
        AssetOaOrderApply assetOaOrderApply = requestConverter.convert(request, AssetOaOrderApply.class);
        assetOaOrderApplyDao.insert(assetOaOrderApply);
        return assetOaOrderApply.getId();
    }

    @Override
    public Integer updateAssetOaOrderApply(AssetOaOrderApplyRequest request) throws Exception {
        AssetOaOrderApply assetOaOrderApply = requestConverter.convert(request, AssetOaOrderApply.class);
        return assetOaOrderApplyDao.update(assetOaOrderApply);
    }

    @Override
    public List<AssetOaOrderApplyResponse> findListAssetOaOrderApply(AssetOaOrderApplyQuery query) throws Exception {
        List<AssetOaOrderApply> assetOaOrderApplyList = assetOaOrderApplyDao.findQuery(query);
        //TODO
        List<AssetOaOrderApplyResponse> assetOaOrderApplyResponse = responseConverter.convert(assetOaOrderApplyList, AssetOaOrderApplyResponse.class);
        return assetOaOrderApplyResponse;
    }

    @Override
    public PageResult<AssetOaOrderApplyResponse> findPageAssetOaOrderApply(AssetOaOrderApplyQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListAssetOaOrderApply(query));
    }
}
