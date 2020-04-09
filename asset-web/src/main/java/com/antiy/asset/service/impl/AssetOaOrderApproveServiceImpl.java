package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOaOrderApproveDao;
import com.antiy.asset.entity.AssetOaOrderApprove;
import com.antiy.asset.service.IAssetOaOrderApproveService;
import com.antiy.asset.vo.query.AssetOaOrderApproveQuery;
import com.antiy.asset.vo.request.AssetOaOrderApproveRequest;
import com.antiy.asset.vo.response.AssetOaOrderApproveResponse;
import com.antiy.common.base.BaseConverter;
import com.antiy.common.base.BaseServiceImpl;
import com.antiy.common.base.PageResult;
import com.antiy.common.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单审批信息表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-07
 */
@Service
public class AssetOaOrderApproveServiceImpl extends BaseServiceImpl<AssetOaOrderApprove> implements IAssetOaOrderApproveService {

    private static final Logger logger = LogUtils.get();

    @Resource
    private AssetOaOrderApproveDao assetOaOrderApproveDao;
    @Resource
    private BaseConverter<AssetOaOrderApproveRequest, AssetOaOrderApprove> requestConverter;
    @Resource
    private BaseConverter<AssetOaOrderApprove, AssetOaOrderApproveResponse> responseConverter;

    @Override
    public Integer saveAssetOaOrderApprove(AssetOaOrderApproveRequest request) throws Exception {
        AssetOaOrderApprove assetOaOrderApprove = requestConverter.convert(request, AssetOaOrderApprove.class);
        assetOaOrderApproveDao.insert(assetOaOrderApprove);
        return assetOaOrderApprove.getId();
    }

    @Override
    public Integer updateAssetOaOrderApprove(AssetOaOrderApproveRequest request) throws Exception {
        AssetOaOrderApprove assetOaOrderApprove = requestConverter.convert(request, AssetOaOrderApprove.class);
        return assetOaOrderApproveDao.update(assetOaOrderApprove);
    }

    @Override
    public List<AssetOaOrderApproveResponse> findListAssetOaOrderApprove(AssetOaOrderApproveQuery query) throws Exception {
        List<AssetOaOrderApprove> assetOaOrderApproveList = assetOaOrderApproveDao.findQuery(query);
        //TODO
        List<AssetOaOrderApproveResponse> assetOaOrderApproveResponse = responseConverter.convert(assetOaOrderApproveList, AssetOaOrderApproveResponse.class);
        return assetOaOrderApproveResponse;
    }

    @Override
    public PageResult<AssetOaOrderApproveResponse> findPageAssetOaOrderApprove(AssetOaOrderApproveQuery query) throws Exception {
        return new PageResult<>(query.getPageSize(), this.findCount(query), query.getCurrentPage(), this.findListAssetOaOrderApprove(query));
    }
}
