package com.antiy.asset.service.impl;

import com.antiy.asset.dao.AssetOaOrderResultDao;
import com.antiy.asset.entity.AssetOaOrderResult;
import com.antiy.asset.service.IAssetOaOrderResultService;
import com.antiy.asset.vo.query.AssetOaOrderResultQuery;
import com.antiy.asset.vo.request.AssetOaOrderResultRequest;
import com.antiy.asset.vo.response.AssetOaOrderResultResponse;
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
 * 出借订单拒绝表 服务实现类
 * </p>
 *
 * @author shenliang
 * @since 2020-04-09
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AssetOaOrderResultServiceImpl extends BaseServiceImpl<AssetOaOrderResult> implements IAssetOaOrderResultService {

        private static final Logger logger = LogUtils.get();

        @Resource
        private AssetOaOrderResultDao assetOaOrderRefuseDao;
        @Resource
        private BaseConverter<AssetOaOrderResultRequest, AssetOaOrderResult>  requestConverter;
        @Resource
        private BaseConverter<AssetOaOrderResult, AssetOaOrderResultResponse> responseConverter;

        @Override
        public Integer saveAssetOaOrderRefuse(AssetOaOrderResultRequest request) throws Exception {
            AssetOaOrderResult assetOaOrderRefuse = requestConverter.convert(request, AssetOaOrderResult.class);
            assetOaOrderRefuseDao.insert(assetOaOrderRefuse);
            return assetOaOrderRefuse.getId();
        }

        @Override
        public Integer updateAssetOaOrderRefuse(AssetOaOrderResultRequest request) throws Exception {
            AssetOaOrderResult assetOaOrderRefuse = requestConverter.convert(request, AssetOaOrderResult.class);
            return assetOaOrderRefuseDao.update(assetOaOrderRefuse);
        }

        @Override
        public List<AssetOaOrderResultResponse> findListAssetOaOrderRefuse(AssetOaOrderResultQuery query) throws Exception {
            List<AssetOaOrderResult> assetOaOrderRefuseList = assetOaOrderRefuseDao.findQuery(query);
            //TODO
            List<AssetOaOrderResultResponse> assetOaOrderRefuseResponse = responseConverter.convert(assetOaOrderRefuseList, AssetOaOrderResultResponse.class );
            return assetOaOrderRefuseResponse;
        }

        @Override
        public PageResult<AssetOaOrderResultResponse> findPageAssetOaOrderRefuse(AssetOaOrderResultQuery query) throws Exception {
            return new PageResult<>(query.getPageSize(), this.findCount(query),query.getCurrentPage(), this.findListAssetOaOrderRefuse(query));
        }
}
